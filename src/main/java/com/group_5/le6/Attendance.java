package com.group_5.le6;

// Authors: Agapito, Virtucio

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Attendance {
    private final IntegerProperty studentId;
    private final StringProperty studentName;
    private final StringProperty date;
    private final StringProperty status;

    public Attendance(int studentId, String studentName, String date, String status) {
        this.studentId = new SimpleIntegerProperty(studentId);
        this.studentName = new SimpleStringProperty(studentName);
        this.date = new SimpleStringProperty(date);
        this.status = new SimpleStringProperty(status);
    }

    // Student ID Property
    public IntegerProperty studentIdProperty() {
        return studentId;
    }

    public int getStudentId() {
        return studentId.get();
    }

    public void setStudentId(int studentId) {
        this.studentId.set(studentId);
    }

    // Student Name Property
    public StringProperty studentNameProperty() {
        return studentName;
    }

    public String getStudentName() {
        return studentName.get();
    }

    public void setStudentName(String studentName) {
        this.studentName.set(studentName);
    }

    // Date Property
    public StringProperty dateProperty() {
        return date;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    // Status Property
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
