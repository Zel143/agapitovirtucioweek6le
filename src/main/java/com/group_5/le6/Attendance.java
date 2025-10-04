package com.group_5.le6;

// Authors: Agapito, Virtucio

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Attendance {
    private final StringProperty studentId;
    private final StringProperty studentName;
    private final StringProperty date;
    private final StringProperty status;

    public Attendance(String studentId, String studentName, LocalDate date, String status) {
        this.studentId = new SimpleStringProperty(studentId);
        this.studentName = new SimpleStringProperty(studentName);
        this.date = new SimpleStringProperty(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        this.status = new SimpleStringProperty(status);
    }

    // Student ID property
    public StringProperty studentIdProperty() {
        return studentId;
    }

    public String getStudentId() {
        return studentId.get();
    }

    public void setStudentId(String studentId) {
        this.studentId.set(studentId);
    }

    // Student Name property
    public StringProperty studentNameProperty() {
        return studentName;
    }

    public String getStudentName() {
        return studentName.get();
    }

    public void setStudentName(String studentName) {
        this.studentName.set(studentName);
    }

    // Date property
    public StringProperty dateProperty() {
        return date;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    // Status property
    public StringProperty statusProperty() {
        return status;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}