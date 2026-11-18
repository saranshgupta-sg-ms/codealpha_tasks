import java.io.*;
import java.util.*;

public class Main {
    private static final String FILE = "students.csv";
    private static final Scanner sc = new Scanner(System.in);
    private static final List<Student> students = new ArrayList<Student>();

    static class Student {
        String name;
        double score;
        Student(String name, double score) { this.name = name; this.score = score; }
    }

    public static void main(String[] args) {
        loadStudents();
        while (true) {
            System.out.println("\n===== STUDENT GRADE TRACKER =====");
            System.out.println("1. Add student grade");
            System.out.println("2. View all students");
            System.out.println("3. View class summary");
            System.out.println("4. Exit");
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1: addStudent(); break;
                case 2: showStudents(); break;
                case 3: showSummary(); break;
                case 4: saveStudents(); System.out.println("Data saved. Goodbye!"); return;
                default: System.out.println("Please choose 1 to 4.");
            }
        }
    }

    private static void addStudent() {
        System.out.print("Enter student name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty() || name.contains(",")) {
            System.out.println("Name cannot be empty or contain a comma.");
            return;
        }
        double score = readDouble("Enter score (0-100): ");
        if (score < 0 || score > 100) {
            System.out.println("Score must be between 0 and 100.");
            return;
        }
        students.add(new Student(name, score));
        saveStudents();
        System.out.println("Student grade added successfully.");
    }

    private static void showStudents() {
        if (students.isEmpty()) { System.out.println("No student records found."); return; }
        System.out.println("\n--- STUDENT REPORT ---");
        System.out.printf("%-5s %-25s %-10s%n", "No.", "Name", "Score");
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.printf("%-5d %-25s %-10.2f%n", i + 1, s.name, s.score);
        }
    }

    private static void showSummary() {
        if (students.isEmpty()) { System.out.println("Add student records to view the summary."); return; }
        double total = 0, highest = -1, lowest = 101;
        String topStudent = "", lowStudent = "";
        for (Student s : students) {
            total += s.score;
            if (s.score > highest) { highest = s.score; topStudent = s.name; }
            if (s.score < lowest) { lowest = s.score; lowStudent = s.name; }
        }
        System.out.printf("%nNumber of students: %d%n", students.size());
        System.out.printf("Average score: %.2f%n", total / students.size());
        System.out.printf("Highest score: %.2f (%s)%n", highest, topStudent);
        System.out.printf("Lowest score: %.2f (%s)%n", lowest, lowStudent);
    }

    private static void loadStudents() {
        File file = new File(FILE);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    try { students.add(new Student(parts[0], Double.parseDouble(parts[1]))); }
                    catch (NumberFormatException ignored) { }
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load saved data: " + e.getMessage());
        }
    }

    private static void saveStudents() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE))) {
            for (Student s : students) out.println(s.name + "," + s.score);
        } catch (IOException e) {
            System.out.println("Could not save data: " + e.getMessage());
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { int value = Integer.parseInt(sc.nextLine().trim()); return value; }
            catch (NumberFormatException e) { System.out.println("Enter a valid whole number."); }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Double.parseDouble(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Enter a valid number."); }
        }
    }
}
