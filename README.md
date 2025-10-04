# Student Management System

Authors: Agapito, Virtucio

## Project Overview
A JavaFX-based Student Management System with CRUD operations and an Attendance tracking feature.

## Features

### 1. Student CRUD Operations
- **View Students**: Display students in a table with ID, Name, Email, and Course
- **Add Student**: Add new students using the input form
- **Edit Student**: Edit existing student information using a dialog with bidirectional binding
- **Delete Student**: Remove selected students with confirmation dialog

### 2. Attendance System
- Separate view for managing student attendance
- Login functionality with Alert dialogs for errors
- Display attendance records in a table
- Scene switching between Student Management and Attendance views

## Technical Details
- **Group ID**: `com.group_5.le6`
- **Package**: `com.group_5.le6`
- **Build Tool**: Maven
- **JavaFX Version**: 17.0.2
- **Java Version**: 11

## How to Build and Run

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Build the Project
```bash
mvn clean compile
```

### Package the Application
```bash
mvn package
```

### Run the Application
```bash
mvn javafx:run
```

## Usage Instructions

### Student Management
1. Launch the application - you'll see the Student Management System
2. Use the input fields (ID, Name, Email, Course) to add new students
3. Click "Add Student" to add a student to the table
4. Select a student from the table and click "Edit" to modify their information
5. Select a student from the table and click "Delete" to remove them
6. Click "Go to Attendance" to switch to the Attendance System

### Attendance System
1. From the Student Management view, click "Go to Attendance"
2. Login credentials for testing: username: `admin`, password: `admin`
3. Invalid credentials will show an error Alert dialog
4. View attendance records in the table
5. Click "Back to Students" to return to Student Management

## Project Structure
```
src/
├── main/
│   ├── java/com/group_5/le6/
│   │   ├── App.java                    - Main application class
│   │   ├── Student.java                - Student model with JavaFX properties
│   │   ├── StudentController.java      - Controller for Student CRUD operations
│   │   ├── Attendance.java             - Attendance model
│   │   └── AttendanceController.java   - Controller for Attendance system
│   └── resources/com/group_5/le6/
│       ├── student-view.fxml           - Student Management UI
│       └── attendance-view.fxml        - Attendance System UI
```