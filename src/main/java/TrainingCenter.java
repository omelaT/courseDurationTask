import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static java.time.temporal.ChronoUnit.WEEKS;

public class TrainingCenter {

    private static Map<String, Curriculum> curriculums = Map.of(
            "Ivanov Ivan", new Curriculum(
                    "Ivanov Ivan",
                    "Java Developer",
                    LocalDate.of(2020, 7, 1),
                    List.of(
                            new Course("Java", 16),
                            new Course("JDBC", 24),
                            new Course("Spring", 16)
                    )
            ),
            "Sidorov Ivan", new Curriculum(
                    "Sidorov Ivan",
                    "AQE",
                    LocalDate.of(2020, 7, 1),
                    List.of(
                            new Course("Test design", 10),
                            new Course("Page Object", 16),
                            new Course("Selenium", 16)
                    )

            )
    );

    private static final Set<DayOfWeek> workingDays = Set.of(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY
    );

    public static void main(String[] args) {
        System.out.println("Please enter date like in this example - 2007-12-03T10:15:30");
        Scanner scanner = new Scanner(System.in);
        String inputDate = scanner.nextLine();
        LocalDateTime date = LocalDateTime.parse(inputDate);


        curriculums.forEach((key, value) -> {
            int hours = value.getCourses().stream().map(Course::getDuration).reduce(0, Integer::sum);
            CourseDurationResult result = calculateCourseDurationResult(value.getStartDate(), date, hours);
            System.out.println(key + "(" + value.getName() + ")" + " - " + formatShortDurationResult(result));
        });


    }

    public static CourseDurationResult calculateCourseDurationResult(
            LocalDate curriculumStartDate,
            LocalDateTime currentDate,
            int courseHours
    ) {
        int courseDaysLeft = courseHours / 8;
        LocalDateTime courseEndDateTime = LocalDateTime.from(curriculumStartDate.atStartOfDay());

        if (courseDaysLeft > 0) {
            int hoursRemains = courseHours % 8;
            LocalDateTime dateTime = curriculumStartDate.atStartOfDay();
            int daysLeft = courseDaysLeft;
            while (daysLeft > 0) {
                if (isWorkingDay(dateTime.getDayOfWeek())) {
                    dateTime = dateTime.plusDays(1);
                    daysLeft = daysLeft - 1;
                }
                dateTime = dateTime.plusDays(1);
            }
            courseEndDateTime = dateTime.plusHours(hoursRemains);
        } else {
            courseEndDateTime = courseEndDateTime.plusHours(10 + courseHours);
        }

        CourseDurationResult result;

        if (currentDate.isBefore(curriculumStartDate.atStartOfDay())) {
            int hours = calculateWorkingHours(curriculumStartDate.atStartOfDay(), courseEndDateTime);
            result = new CourseDurationResult(false,  hours, null);
        } else if (currentDate.isAfter(courseEndDateTime)) {
            int hoursSinceCompleted = calculateWorkingHours(courseEndDateTime, currentDate);
            result = new CourseDurationResult(true, null, hoursSinceCompleted);
        } else {
            int hours = calculateWorkingHours(currentDate, courseEndDateTime);
            result = new CourseDurationResult(false, hours, null);
        }

        return result;
    }

    private static boolean isWorkingDay(DayOfWeek dayOfWeek) {
        return workingDays.contains(dayOfWeek);
    }

    private static int calculateWorkingHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (endDateTime.isBefore(startDateTime)) {
            return -1;
        }
        int workingHours = 0;
        LocalDateTime dateTime = startDateTime;
        while (endDateTime.isAfter(dateTime)) {
            if (isWorkingDay(startDateTime.getDayOfWeek())) {
                int hour = dateTime.getHour();
                if (hour < 10) {
                    workingHours = workingHours + 8;
                } else if (hour < 18) {
                    workingHours = workingHours + (18 - hour);
                }
            }
            dateTime = dateTime.plusDays(1);
        }

        return workingHours;
    }

    private static String formatWorkingHours(int hours) {
        int workingDays = hours / 8;
        int remainingHours = hours % 8;

        if (workingDays > 0) {
            return workingDays + " d " + remainingHours + " hours";
        }
        return hours + " hours";
    }

    private static String formatShortDurationResult(CourseDurationResult result) {
        if (result.isCompleted()) {
            return "Training completed." + formatWorkingHours(result.getHoursSinceCompleted()) + " 3 hours have passed since the end.";
        }

        return "Training is not finished. " + formatWorkingHours(result.getHoursLeftToComplete()) + " are left until the end.";
    }

}
