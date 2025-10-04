package com.group_5.le6;

// Authors: Agapito, Virtucio

import java.io.IOException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
    @FXML
    private TextField searchField;

    private final ObservableList<Student> students = FXCollections.observableArrayList();
    private FilteredList<Student> filteredStudents;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));

        // Set up filtered list for search functionality
        filteredStudents = new FilteredList<>(students, p -> true);
        studentTable.setItems(filteredStudents);

        // Add search listener
        if (searchField != null) {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredStudents.setPredicate(student -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    return student.getId().toLowerCase().contains(lowerCaseFilter)
                            || student.getName().toLowerCase().contains(lowerCaseFilter)
                            || student.getEmail().toLowerCase().contains(lowerCaseFilter)
                            || student.getCourse().toLowerCase().contains(lowerCaseFilter);
                });
            });
        }

        // Load saved data
        loadStudentsFromFile();
        
        // If no saved data, add sample data
        if (students.isEmpty()) {
            students.add(new Student("001", "John Doe", "john@example.com", "Computer Science"));
            students.add(new Student("002", "Jane Smith", "jane@example.com", "Information Technology"));
            saveStudentsToFile();
        }
    }

    @FXML
    private void handleAdd() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String course = courseField.getText().trim();

        // Comprehensive validation
        StringBuilder errors = new StringBuilder();
        
        if (id.isEmpty() || name.isEmpty() || email.isEmpty() || course.isEmpty()) {
            errors.append("All fields are required.\n");
        }
        
        if (!Student.isValidId(id)) {
            errors.append("ID must be 3-10 alphanumeric characters.\n");
        } else if (isDuplicateId(id)) {
            errors.append("A student with this ID already exists.\n");
        }
        
        if (!Student.isValidName(name)) {
            errors.append("Name must be 2-50 characters, letters and spaces only.\n");
        }
        
        if (!Student.isValidEmail(email)) {
            errors.append("Please enter a valid email address.\n");
        }
        
        if (course.trim().isEmpty()) {
            errors.append("Course cannot be empty.\n");
        }

        if (errors.length() > 0) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", errors.toString().trim());
            return;
        }

        Student student = new Student(id, name, email, course);
        students.add(student);
        saveStudentsToFile();
        showAlert(Alert.AlertType.INFORMATION, "Success", "Student added successfully!");
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

            // Add validation before closing dialog
            dialogPane.lookupButton(ButtonType.OK).addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
                if (!controller.validateInput()) {
                    event.consume(); // Prevent dialog from closing
                    return;
                }
                
                // Check for duplicate ID (excluding current student)
                String newId = selectedStudent.getId();
                String originalId = controller.getOriginalId();
                
                if (!newId.equals(originalId) && isDuplicateId(newId)) {
                    showAlert(Alert.AlertType.ERROR, "Duplicate ID", "A student with this ID already exists.");
                    event.consume(); // Prevent dialog from closing
                }
            });

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                studentTable.refresh();
                saveStudentsToFile();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Student updated successfully!");
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
            saveStudentsToFile();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Student deleted successfully!");
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
        if (searchField != null) {
            searchField.clear();
        }
    }

    private boolean isDuplicateId(String id) {
        return students.stream().anyMatch(student -> student.getId().equalsIgnoreCase(id));
    }

    private void saveStudentsToFile() {
        DataManager.saveStudents(students);
    }

    private void loadStudentsFromFile() {
        students.clear();
        students.addAll(DataManager.loadStudents());
    }

    public ObservableList<Student> getStudents() {
        return students;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
