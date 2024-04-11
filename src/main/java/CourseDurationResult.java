import java.time.Duration;

public class CourseDurationResult {
    private final boolean isCompleted;
    private final Integer hoursLeftToComplete;
    private final Integer hoursSinceCompleted;

    public CourseDurationResult(boolean isCompleted, Integer hoursLeftToComplete, Integer hoursSinceCompleted) {
        this.isCompleted = isCompleted;
        this.hoursLeftToComplete = hoursLeftToComplete;
        this.hoursSinceCompleted = hoursSinceCompleted;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public Integer getHoursLeftToComplete() {
        return hoursLeftToComplete;
    }

    public Integer getHoursSinceCompleted() {
        return hoursSinceCompleted;
    }
}
