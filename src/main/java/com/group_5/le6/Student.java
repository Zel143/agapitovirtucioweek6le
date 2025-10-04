package com.group_5.le6;

// Authors: Agapito, Virtucio

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.regex.Pattern;

public class Student {
    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty email;
    private final StringProperty course;

    public Student(String id, String name, String email, String course) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.course = new SimpleStringProperty(course);
    }

    // ID property
    public StringProperty idProperty() {
        return id;
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    // Name property
    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    // Email property
    public StringProperty emailProperty() {
        return email;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    // Course property
    public StringProperty courseProperty() {
        return course;
    }

    public String getCourse() {
        return course.get();
    }

    public void setCourse(String course) {
        this.course.set(course);
    }

    // Validation methods
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.compile(emailRegex).matcher(email.trim()).matches();
    }

    public static boolean isValidId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        // ID should be 3-10 characters, alphanumeric
        String idRegex = "^[A-Za-z0-9]{3,10}$";
        return Pattern.compile(idRegex).matcher(id.trim()).matches();
    }

    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        // Name should be 2-50 characters, letters and spaces only
        String nameRegex = "^[A-Za-z\\s]{2,50}$";
        return Pattern.compile(nameRegex).matcher(name.trim()).matches();
    }
}
