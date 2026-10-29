import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Main {

    static ArrayList<Course> courses = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    static final String FILE_NAME = "courses.txt";

    public static void main(String[] args) {
        showMenu();
    }

    // Display menu recursively
    public static void showMenu() {
        System.out.println("\n==================================");
        System.out.println(" STUDENT COURSE MANAGEMENT SYSTEM");
        System.out.println("==================================");
        System.out.println("1. Add Course");
        System.out.println("2. View All Courses");
        System.out.println("3. Search Course by Code");
        System.out.println("4. Compute Total Units");
        System.out.println("5. Save to File");
        System.out.println("6. Load from File");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");

        try {
            int choice = Integer.parseInt(input.nextLine().trim());

            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    viewCourses();
                    break;
                case 3:
                    searchCourse();
                    break;
                case 4:
                    computeTotalUnits();
                    break;
                case 5:
                    saveToFile();
                    break;
                case 6:
                    loadFromFile();
                    break;
                case 7:
                    System.out.println("Thank you for using the system!");
                    input.close();
                    return;
                default:
                    System.out.println("Invalid choice. Enter 1-7.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }

        showMenu();
    }

    // Add a new course
    public static void addCourse() {
        try {
            System.out.print("Enter course code: ");
            String code = input.nextLine().trim().toUpperCase();

            if (code.isEmpty()) {
                System.out.println("Course code cannot be empty.");
                return;
            }

            if (findCourse(code) != null) {
                System.out.println("This course code already exists.");
                return;
            }

            System.out.print("Enter course title: ");
            String title = input.nextLine().trim();

            if (title.isEmpty()) {
                System.out.println("Course title cannot be empty.");
                return;
            }

            System.out.print("Enter credit units: ");
            int units = Integer.parseInt(input.nextLine().trim());

            if (units <= 0) {
                System.out.println("Credit units must be greater than zero.");
                return;
            }

            courses.add(new Course(code, title, units));
            System.out.println("Course added successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Invalid units. Please enter a whole number.");
        }
    }

    // Display all courses
    public static void viewCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses recorded yet.");
            return;
        }

        System.out.printf("%-15s %-30s %s%n",
                "COURSE CODE", "COURSE TITLE", "UNITS");
        System.out.println("----------------------------------------------------------");

        for (Course course : courses) {
            course.displayCourse();
        }
    }

    // Search for a course by code
    public static Course findCourse(String code) {
        for (Course course : courses) {
            if (course.getCourseCode().equalsIgnoreCase(code)) {
                return course;
            }
        }
        return null;
    }

    public static void searchCourse() {
        System.out.print("Enter course code to search: ");
        String code = input.nextLine().trim();

        Course course = findCourse(code);

        if (course != null) {
            System.out.println("Course found:");
            course.displayCourse();
        } else {
            System.out.println("Course not found.");
        }
    }

    // Calculate total credit units
    public static void computeTotalUnits() {
        int total = 0;

        for (Course course : courses) {
            total += course.getCreditUnits();
        }

        System.out.println("Total credit units: " + total);
    }

    // Save courses to a file
    public static void saveToFile() {
        try (PrintWriter writer = new PrintWriter(
                new FileWriter(FILE_NAME))) {

            for (Course course : courses) {
                writer.println(course.getCourseCode() + "|" +
                        course.getCourseTitle() + "|" +
                        course.getCreditUnits());
            }

            System.out.println("Courses saved successfully to " + FILE_NAME);

        } catch (IOException e) {
            System.out.println("Error saving courses: " + e.getMessage());
        }
    }

    // Load courses from a file
    public static void loadFromFile() {
        ArrayList<Course> loadedCourses = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {
                try {
                    String[] details = line.split("\\|", -1);

                    if (details.length != 3 ||
                            details[0].trim().isEmpty() ||
                            details[1].trim().isEmpty()) {
                        System.out.println("Skipping invalid record.");
                        continue;
                    }

                    String code = details[0].trim().toUpperCase();
                    String title = details[1].trim();
                    int units = Integer.parseInt(details[2].trim());

                    if (units <= 0 || containsCourse(loadedCourses, code)) {
                        System.out.println("Skipping invalid or duplicate course.");
                        continue;
                    }

                    loadedCourses.add(new Course(code, title, units));

                } catch (NumberFormatException e) {
                    System.out.println("Skipping a record with invalid units.");
                }
            }

            courses.clear();
            courses.addAll(loadedCourses);

            System.out.println("Courses loaded successfully. Total: "
                    + courses.size());

        } catch (FileNotFoundException e) {
            System.out.println("No saved course file found yet.");
        } catch (IOException e) {
            System.out.println("Error loading courses: " + e.getMessage());
        }
    }

    // Check for duplicate course codes in a list
    public static boolean containsCourse(
            ArrayList<Course> list, String code) {

        for (Course course : list) {
            if (course.getCourseCode().equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }
}