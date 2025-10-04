package com.group_5.le6;

// Authors: Agapito, Virtucio

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AttendanceController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private VBox loginPane;
    @FXML
    private VBox attendancePane;
    @FXML
    private ComboBox<Student> studentComboBox;
    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<Attendance> attendanceTable;
    @FXML
    private TableColumn<Attendance, String> studentIdColumn;
    @FXML
    private TableColumn<Attendance, String> studentNameColumn;
    @FXML
    private TableColumn<Attendance, String> dateColumn;
    @FXML
    private TableColumn<Attendance, String> statusColumn;

    private final ObservableList<Attendance> attendanceRecords = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize attendance table if components exist
        if (attendanceTable != null) {
            studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
            studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
            attendanceTable.setItems(attendanceRecords);
        }

        // Initialize status combo box
        if (statusComboBox != null) {
            statusComboBox.setItems(FXCollections.observableArrayList("Present", "Absent", "Late", "Excused"));
            statusComboBox.setValue("Present");
        }

        // Initialize date picker
        if (datePicker != null) {
            datePicker.setValue(LocalDate.now());
        }

        // Load attendance data
        loadAttendanceFromFile();

        // Show login pane initially
        showLoginPane();
    }

    private void showLoginPane() {
        if (loginPane != null && attendancePane != null) {
            loginPane.setVisible(true);
            loginPane.setManaged(true);
            attendancePane.setVisible(false);
            attendancePane.setManaged(false);
        }
    }

    private void showAttendancePane() {
        if (loginPane != null && attendancePane != null) {
            loginPane.setVisible(false);
            loginPane.setManaged(false);
            attendancePane.setVisible(true);
            attendancePane.setManaged(true);
            loadStudents();
        }
    }

    private void loadStudents() {
        // Load students from data file
        if (studentComboBox != null) {
            ObservableList<Student> students = FXCollections.observableArrayList();
            students.addAll(DataManager.loadStudents());
            
            // If no saved students, add some defaults
            if (students.isEmpty()) {
                students.add(new Student("001", "John Doe", "john@example.com", "Computer Science"));
                students.add(new Student("002", "Jane Smith", "jane@example.com", "Information Technology"));
                students.add(new Student("003", "Mike Johnson", "mike@example.com", "Engineering"));
            }
            
            studentComboBox.setItems(students);
            studentComboBox.setConverter(new javafx.util.StringConverter<Student>() {
                @Override
                public String toString(Student student) {
                    return student != null ? student.getId() + " - " + student.getName() : "";
                }

                @Override
                public Student fromString(String string) {
                    return null;
                }
            });
        }
    }

    private void saveAttendanceToFile() {
        DataManager.saveAttendance(attendanceRecords);
    }

    private void loadAttendanceFromFile() {
        attendanceRecords.clear();
        attendanceRecords.addAll(DataManager.loadAttendance());
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Login Error", "Please enter both username and password.");
            return;
        }

        // Simple validation - in real app, this would check against a database
        if (username.equals("admin") && password.equals("admin")) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome to the Attendance System!");
            showAttendancePane();
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Error", "Invalid username or password.");
        }
    }

    @FXML
    private void handleMarkAttendance() {
        Student selectedStudent = studentComboBox.getValue();
        String status = statusComboBox.getValue();
        LocalDate date = datePicker.getValue();

        if (selectedStudent == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a student.");
            return;
        }

        if (status == null || status.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No Status", "Please select attendance status.");
            return;
        }

        if (date == null) {
            showAlert(Alert.AlertType.WARNING, "No Date", "Please select a date.");
            return;
        }

        // Check if attendance already marked for this student on this date
        boolean alreadyMarked = attendanceRecords.stream()
                .anyMatch(record -> record.getStudentId().equals(selectedStudent.getId()) 
                    && record.getDate().equals(date.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

        if (alreadyMarked) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Attendance Already Marked");
            confirmAlert.setHeaderText("Update Attendance");
            confirmAlert.setContentText("Attendance for " + selectedStudent.getName() + " on " + date + " is already marked. Do you want to update it?");

            if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                // Remove existing record
                attendanceRecords.removeIf(record -> record.getStudentId().equals(selectedStudent.getId()) 
                    && record.getDate().equals(date.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            } else {
                return;
            }
        }

        Attendance attendance = new Attendance(selectedStudent.getId(), selectedStudent.getName(), date, status);
        attendanceRecords.add(attendance);
        saveAttendanceToFile();
        
        showAlert(Alert.AlertType.INFORMATION, "Success", 
            "Attendance marked for " + selectedStudent.getName() + " as " + status + " on " + date);
        
        // Reset form
        studentComboBox.setValue(null);
        statusComboBox.setValue("Present");
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    private void handleLogout() {
        showLoginPane();
        // Clear sensitive data
        usernameField.clear();
        passwordField.clear();
    }

    @FXML
    private void handleBackToStudents() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("student-view.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            Stage stage = App.getPrimaryStage();
            stage.setScene(scene);
            stage.setTitle("Student Management System");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load student view.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
