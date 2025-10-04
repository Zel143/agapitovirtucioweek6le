# Student Management System - Key Features

## Authors
Agapito, Virtucio

## Feature Highlights

### 1. Project Structure
- **GroupId**: `com.group_5.le6`
- **Package**: `com.group_5.le6`
- **Build Tool**: Maven
- **Framework**: JavaFX 17.0.2

### 2. Student CRUD Operations

#### Add Student
- Input form with fields: ID, Name, Email, Course
- Validation to ensure all fields are filled
- Add button adds student to the table

#### Edit Student (with Bidirectional Binding)
```java
// StudentController.java - Lines 129-133
idEdit.textProperty().bindBidirectional(student.idProperty(), new javafx.util.converter.NumberStringConverter());
nameEdit.textProperty().bindBidirectional(student.nameProperty());
emailEdit.textProperty().bindBidirectional(student.emailProperty());
courseEdit.textProperty().bindBidirectional(student.courseProperty());
```
- Click "Edit" button to open a dialog
- Fields are bidirectionally bound to student properties
- Changes update immediately in the model
- OK button confirms changes
- Cancel button reverts changes

#### Delete Student
```java
// StudentController.java - Lines 99-110
Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
confirmAlert.setTitle("Confirm Delete");
confirmAlert.setHeaderText("Delete Student");
confirmAlert.setContentText("Are you sure you want to delete this student?");

confirmAlert.showAndWait().ifPresent(response -> {
    if (response == ButtonType.OK) {
        studentList.remove(selectedStudent);
    }
});
```
- Click "Delete" button
- Confirmation dialog appears
- Student is removed only after confirmation

### 3. Attendance System

#### Login with Alert Dialogs
```java
// AttendanceController.java - Lines 62-73
if (username.isEmpty() || password.isEmpty()) {
    showAlert(Alert.AlertType.ERROR, "Login Error", "Please enter both username and password");
    return;
}

if (username.equals("admin") && password.equals("admin")) {
    showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome to the Attendance System!");
    clearFields();
} else {
    showAlert(Alert.AlertType.ERROR, "Login Error", "Invalid username or password. Please try again.");
}
```
- Login form with username and password fields
- Alert dialogs for:
  - Empty credentials (ERROR)
  - Invalid credentials (ERROR)
  - Successful login (INFORMATION)

#### Scene Switching
```java
// StudentController.java - switchToAttendance()
FXMLLoader loader = new FXMLLoader(getClass().getResource("attendance-view.fxml"));
Parent root = loader.load();
Stage stage = (Stage) studentTable.getScene().getWindow();
stage.setScene(new Scene(root));
stage.setTitle("Attendance System");
```
- "Go to Attendance" button in Student view
- "Back to Students" button in Attendance view
- Seamless scene transitions

### 4. JavaFX Properties Usage

#### Student Model
```java
// Student.java
private final IntegerProperty id;
private final StringProperty name;
private final StringProperty email;
private final StringProperty course;

public Student(int id, String name, String email, String course) {
    this.id = new SimpleIntegerProperty(id);
    this.name = new SimpleStringProperty(name);
    this.email = new SimpleStringProperty(email);
    this.course = new SimpleStringProperty(course);
}
```
- All fields use JavaFX properties
- Enables reactive UI updates
- Supports bidirectional binding

### 5. User Interface

#### Student Management View
- Header with title and navigation button
- Input form for adding students
- TableView displaying all students
- Action buttons (Edit, Delete)

#### Attendance View
- Header with title and back button
- Login form section
- Attendance records table
- Sample attendance data

## Sample Data

### Students
1. John Doe - john@example.com - Computer Science
2. Jane Smith - jane@example.com - Information Technology

### Attendance
- Student ID 1: John Doe - Present (current date)
- Student ID 2: Jane Smith - Present (current date)

### Login Credentials
- Username: admin
- Password: admin

## How to Run

```bash
# Compile the project
mvn clean compile

# Package the application
mvn package

# Run the application
mvn javafx:run
```

## Implementation Checklist

✅ pom.xml with groupId com.group_5.le6
✅ Package com.group_5.le6
✅ Author comments in all files
✅ Student model with JavaFX properties
✅ Student CRUD operations
✅ Edit button with bidirectional binding
✅ Delete button with confirmation
✅ Attendance FXML and controller
✅ Scene switching functionality
✅ Alert dialogs for login errors
✅ Complete working application
