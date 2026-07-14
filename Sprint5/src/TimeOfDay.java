import java.util.Objects;

public class TimeOfDay implements Comparable<TimeOfDay> {
    private final int hours;
    private final int minutes;

    public TimeOfDay(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TimeOfDay timeOfDay)) return false;
        return hours == timeOfDay.hours && minutes == timeOfDay.minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes);
    }

    @Override
    public int compareTo(TimeOfDay o) {
        if (hours != o.hours) {
            return Integer.compare(hours, o.hours);
        }
        return Integer.compare(minutes, o.minutes);
    }
}