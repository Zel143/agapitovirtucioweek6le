package com.group_5.le6;

// Authors: Agapito, Virtucio

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class EditStudentDialogController {
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField courseField;

    private Student student;
    private Student originalStudent; // Keep track of original values

    @FXML
    public void initialize() {
        // This will be called when the FXML is loaded
    }

    public void setStudent(Student student) {
        this.student = student;
        this.originalStudent = new Student(student.getId(), student.getName(), student.getEmail(), student.getCourse());
        
        // Use bindBidirectional to link fields to student's properties
        idField.textProperty().bindBidirectional(student.idProperty());
        nameField.textProperty().bindBidirectional(student.nameProperty());
        emailField.textProperty().bindBidirectional(student.emailProperty());
        courseField.textProperty().bindBidirectional(student.courseProperty());
    }

    public Student getStudent() {
        return student;
    }

    public boolean validateInput() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String course = courseField.getText().trim();

        StringBuilder errors = new StringBuilder();
        
        if (id.isEmpty() || name.isEmpty() || email.isEmpty() || course.isEmpty()) {
            errors.append("All fields are required.\n");
        }
        
        if (!Student.isValidId(id)) {
            errors.append("ID must be 3-10 alphanumeric characters.\n");
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText(null);
            alert.setContentText(errors.toString().trim());
            alert.showAndWait();
            return false;
        }
        
        return true;
    }

    public String getOriginalId() {
        return originalStudent.getId();
    }
}
