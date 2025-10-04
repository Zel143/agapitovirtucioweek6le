package com.group_5.le6;

// Authors: Agapito, Virtucio

import java.io.IOException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StudentController {
    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, String> idColumn;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, String> emailColumn;
    @FXML
    private TableColumn<Student, String> courseColumn;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField courseField;

    private final ObservableList<Student> students = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));

        studentTable.setItems(students);

        // Add some sample data
        students.add(new Student("001", "John Doe", "john@example.com", "Computer Science"));
        students.add(new Student("002", "Jane Smith", "jane@example.com", "Information Technology"));
    }

    @FXML
    private void handleAdd() {
        String id = idField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String course = courseField.getText();

        if (id.isEmpty() || name.isEmpty() || email.isEmpty() || course.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill in all fields.");
            return;
        }

        Student student = new Student(id, name, email, course);
        students.add(student);

        clearFields();
    }

    @FXML
    private void handleEdit() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a student to edit.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit-student-dialog.fxml"));
            DialogPane dialogPane = loader.load();

            EditStudentDialogController controller = loader.getController();
            controller.setStudent(selectedStudent);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Edit Student");
            dialog.initModality(Modality.APPLICATION_MODAL);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                studentTable.refresh();
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to open edit dialog.");
        }
    }

    @FXML
    private void handleDelete() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a student to delete.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText("Delete Student");
        confirmAlert.setContentText("Are you sure you want to delete " + selectedStudent.getName() + "?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            students.remove(selectedStudent);
        }
    }

    @FXML
    private void handleAttendance() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("attendance-view.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);
            Stage stage = App.getPrimaryStage();
            stage.setScene(scene);
            stage.setTitle("Attendance System");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load attendance view.");
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
