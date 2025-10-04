package com.group_5.le6;

// Authors: Agapito, Virtucio

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AttendanceController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private TableView<Attendance> attendanceTable;
    @FXML
    private TableColumn<Attendance, Integer> studentIdColumn;
    @FXML
    private TableColumn<Attendance, String> studentNameColumn;
    @FXML
    private TableColumn<Attendance, String> dateColumn;
    @FXML
    private TableColumn<Attendance, String> statusColumn;

    private ObservableList<Attendance> attendanceList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up table columns
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set items to table
        attendanceTable.setItems(attendanceList);

        // Add sample attendance data
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        attendanceList.add(new Attendance(1, "John Doe", today, "Present"));
        attendanceList.add(new Attendance(2, "Jane Smith", today, "Present"));
    }

    @FXML
    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Login Error", "Please enter both username and password");
            return;
        }

        // Simple authentication (for demonstration)
        if (username.equals("admin") && password.equals("admin")) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome to the Attendance System!");
            clearFields();
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Error", "Invalid username or password. Please try again.");
        }
    }

    @FXML
    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
    }

    @FXML
    private void switchToStudents() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("student-view.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) attendanceTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Student Management System");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load student view");
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
