import java.io.FileNotFoundException;
import java.nio.Buffer;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        //Задача 11
        String filename = "C:\\Users\\user\\IdeaProjects\\java_HW99\\src\\trains.txt";
        List<Train> allTrains = readAllTrains(filename);
        System.out.println(allTrains.toString());

        // Задача 2
        List<Train> trainsWithinThreeDays = readTrainsWithinThreeDays(filename);

        // Задача 3
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите город:");
        String city = scanner.nextLine();
        System.out.println("Введите дату и время в формате yyyy-MM-ddTHH:mm:ss:");
        LocalDateTime dateTime = LocalDateTime.parse(scanner.nextLine());
        List<Train> departingTrains = readDepartingTrains(filename, city, dateTime);

        // Задача 4
        Map<String, List<Train>> trainsByDepartureCity = groupTrainsByDepartureCity(filename);

        // Задача 5
        Map<String, Set<String>> reachableCities = groupReachableCities(filename);

        System.out.println("Все поезда:");
        allTrains.forEach(System.out::println);
        System.out.println("Поезда, находящиеся в пути не более трех дней:");
        trainsWithinThreeDays.forEach(System.out::println);
        System.out.println("Поезда, уходящие из определенного города не позже заданной даты:");
        departingTrains.forEach(System.out::println);
        System.out.println("Поезда сгруппированные по городу отправления:");
        trainsByDepartureCity.forEach((cityName, trains) -> {
            System.out.println(cityName + ": " + trains);
        });
        System.out.println("Доступные города из каждого города отправления:");
        reachableCities.forEach((cityName, reachable) -> {
            System.out.println(cityName + ": " + reachable);
        });




    }

    //Задача 1
    public static List<Train> readAllTrains(String filename){
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
            return reader.lines().map(Train::parse).collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // Задача 2
    public static List<Train> readTrainsWithinThreeDays(String filename) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeDaysLater = now.plusDays(3);
        return readAllTrains(filename).stream()
                .filter(train -> Duration.between(train.getDeparture(), train.getArrival()).toDays() <= 3)
                .collect(Collectors.toList());
    }

    // Задача 3
    public static List<Train> readDepartingTrains(String filename, String city, LocalDateTime dateTime) {
        return readAllTrains(filename).stream()
                .filter(train -> train.getFrom().equals(city) && train.getDeparture().isBefore(dateTime))
                .collect(Collectors.toList());
    }

    // Задача 4
    public static Map<String, List<Train>> groupTrainsByDepartureCity(String filename) {
        return readAllTrains(filename).stream()
                .collect(Collectors.groupingBy(Train::getFrom));
    }

    // Задача 5
    public static Map<String, Set<String>> groupReachableCities(String filename) {
        Map<String, Set<String>> reachableCities = new HashMap<>();
        List<Train> allTrains = readAllTrains(filename);
        allTrains.forEach(train -> {
            reachableCities.computeIfAbsent(train.getFrom(), city -> new HashSet<>()).add(train.getTo());
        });
        return reachableCities;
    }


}