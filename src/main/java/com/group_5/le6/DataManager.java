package com.group_5.le6;

// Authors: Agapito, Virtucio

import javafx.collections.ObservableList;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String STUDENTS_FILE = "students.txt";
    private static final String ATTENDANCE_FILE = "attendance.txt";

    // Student data management
    public static void saveStudents(ObservableList<Student> students) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENTS_FILE))) {
            for (Student student : students) {
                writer.println(student.getId() + "|" + student.getName() + "|" + 
                              student.getEmail() + "|" + student.getCourse());
            }
        } catch (IOException e) {
            System.err.println("Error saving students: " + e.getMessage());
        }
    }

    public static List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        File file = new File(STUDENTS_FILE);
        
        if (!file.exists()) {
            return students; // Return empty list if file doesn't exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    students.add(new Student(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading students: " + e.getMessage());
        }
        
        return students;
    }

    // Attendance data management
    public static void saveAttendance(ObservableList<Attendance> attendanceRecords) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ATTENDANCE_FILE))) {
            for (Attendance attendance : attendanceRecords) {
                writer.println(attendance.getStudentId() + "|" + attendance.getStudentName() + "|" + 
                              attendance.getDate() + "|" + attendance.getStatus());
            }
        } catch (IOException e) {
            System.err.println("Error saving attendance: " + e.getMessage());
        }
    }

    public static List<Attendance> loadAttendance() {
        List<Attendance> attendanceList = new ArrayList<>();
        File file = new File(ATTENDANCE_FILE);
        
        if (!file.exists()) {
            return attendanceList; // Return empty list if file doesn't exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    attendanceList.add(new Attendance(
                        parts[0], 
                        parts[1], 
                        LocalDate.parse(parts[2]), 
                        parts[3]
                    ));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading attendance: " + e.getMessage());
        }
        
        return attendanceList;
    }
}