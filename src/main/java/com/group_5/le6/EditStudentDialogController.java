package com.group_5.le6;

// Authors: Agapito, Virtucio

import javafx.fxml.FXML;
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

    public void setStudent(Student student) {
        this.student = student;
        
        // Use bindBidirectional to link fields to student's properties
        idField.textProperty().bindBidirectional(student.idProperty());
        nameField.textProperty().bindBidirectional(student.nameProperty());
        emailField.textProperty().bindBidirectional(student.emailProperty());
        courseField.textProperty().bindBidirectional(student.courseProperty());
    }
}
