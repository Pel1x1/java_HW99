import java.time.LocalDateTime;

public class Train {
    private long number;
    private String from;
    private String to;
    private LocalDateTime departure;
    private LocalDateTime arrival;

    public Train(long number, String from, String to, LocalDateTime departure, LocalDateTime arrival) {
        this.number = number;
        this.from = from;
        this.to = to;
        this.departure = departure;
        this.arrival = arrival;
    }
    public long getNumber() {
        return number;
    }
    public void setNumber(long number) {
        this.number = number;
    }
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public LocalDateTime getDeparture() {
        return departure;
    }
    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }
    public LocalDateTime getArrival() {
        return arrival;
    }
    public void setArrival(LocalDateTime arrival) {
        this.arrival = arrival;
    }

    public static Train parse(String line) {
        String[] parts = line.split("; ");
        long number = Long.parseLong(parts[0].substring(parts[0].indexOf(' ') + 1, parts[0].indexOf('{')));
        String from = parts[1].substring(parts[1].indexOf(':') + 2, parts[1].indexOf(';'));
        String to = parts[2].substring(parts[2].indexOf(':') + 2, parts[2].indexOf(';'));
        LocalDateTime departure = LocalDateTime.parse(parts[3].substring(parts[3].indexOf(':') + 2, parts[3].indexOf(';')));
        LocalDateTime arrival = LocalDateTime.parse(parts[4].substring(parts[4].indexOf(':') + 2, parts[4].indexOf(';')));
        return new Train(number, from, to, departure, arrival);
    }
    @Override
    public String toString() {
        return "Train{" +
                "number=" + number +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", departure=" + departure +
                ", arrival=" + arrival +
                '}';
    }

}
