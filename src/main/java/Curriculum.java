import java.time.LocalDate;
import java.util.List;

public class Curriculum {
    private final String studentName;
    private final String name;
    private final LocalDate startDate;

   private final List<Course> courses;

    public Curriculum(String studentName, String name, LocalDate startDate, List<Course> courses) {
        this.studentName = studentName;
        this.name = name;
        this.startDate = startDate;
        this.courses = courses;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
