public class Course {
    private String courseCode;
    private String courseTitle;
    private int creditUnits;

    public Course(String courseCode, String courseTitle, int creditUnits) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.creditUnits = creditUnits;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public int getCreditUnits() {
        return creditUnits;
    }

    public void displayCourse() {
        System.out.printf("%-15s %-30s %d%n",
                courseCode, courseTitle, creditUnits);
    }
}