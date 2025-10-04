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
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.io.IOException;

public class StudentController {

    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField courseField;

    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, Integer> idColumn;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, String> emailColumn;
    @FXML
    private TableColumn<Student, String> courseColumn;

    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));

        // Set items to table
        studentTable.setItems(studentList);

        // Add sample data
        studentList.add(new Student(1, "John Doe", "john@example.com", "Computer Science"));
        studentList.add(new Student(2, "Jane Smith", "jane@example.com", "Information Technology"));
    }

    @FXML
    private void addStudent() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String email = emailField.getText();
            String course = courseField.getText();

            if (name.isEmpty() || email.isEmpty() || course.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Input Error", "Please fill all fields");
                return;
            }

            Student newStudent = new Student(id, name, email, course);
            studentList.add(newStudent);

            clearFields();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "ID must be a number");
        }
    }

    @FXML
    private void editStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select a student to edit");
            return;
        }

        showEditDialog(selectedStudent);
    }

    @FXML
    private void deleteStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select a student to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete Student");
        confirmAlert.setContentText("Are you sure you want to delete this student?");

        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                studentList.remove(selectedStudent);
            }
        });
    }

    private void showEditDialog(Student student) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Student");
        dialog.setHeaderText("Edit Student Information");

        // Create dialog content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idEdit = new TextField();
        TextField nameEdit = new TextField();
        TextField emailEdit = new TextField();
        TextField courseEdit = new TextField();

        // Bind bidirectionally to student properties
        idEdit.textProperty().bindBidirectional(student.idProperty(), new javafx.util.converter.NumberStringConverter());
        nameEdit.textProperty().bindBidirectional(student.nameProperty());
        emailEdit.textProperty().bindBidirectional(student.emailProperty());
        courseEdit.textProperty().bindBidirectional(student.courseProperty());

        grid.add(new Label("ID:"), 0, 0);
        grid.add(idEdit, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameEdit, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailEdit, 1, 2);
        grid.add(new Label("Course:"), 0, 3);
        grid.add(courseEdit, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                studentTable.refresh();
            } else {
                // Unbind if cancelled (changes will revert)
                idEdit.textProperty().unbindBidirectional(student.idProperty());
                nameEdit.textProperty().unbindBidirectional(student.nameProperty());
                emailEdit.textProperty().unbindBidirectional(student.emailProperty());
                courseEdit.textProperty().unbindBidirectional(student.courseProperty());
            }
        });
    }

    @FXML
    private void switchToAttendance() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("attendance-view.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) studentTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Attendance System");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load attendance view");
        }
    }

    private void clearFields() {
        idField.clear();
        nameField.clear();
        emailField.clear();
        courseField.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
