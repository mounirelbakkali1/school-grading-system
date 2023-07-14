package ma.example.schoolsystem.entities;

import ma.example.schoolsystem.Database;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Student {
    long id;
    String firstName;
    String lastName;
    String email;
    LocalDate dateOfBirth;
    String filliere;

    public Student(long id, String firstName, String lastName, String email, LocalDate dateOfBirth, String filliere) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.filliere = filliere;
    }

    public Student(int id2, String firstName2, String lastName2, String email2, LocalDate dateOfBirth2,
            String filliere2) {
    }

    public static List<Student> getAllStudentsFromDB() {
        List<Student> studentList = new ArrayList<>();
        String studentQuery = "SELECT * FROM students";
        String filliereQuery = "SELECT * FROM fillieres WHERE id = ?";

        try (Connection connection = Database.getConnection();
                PreparedStatement studentStatement = connection.prepareStatement(studentQuery);
                ResultSet studentResultSet = studentStatement.executeQuery()) {
            System.out.println("Connected to the database.");

            while (studentResultSet.next()) {
                long studentId = studentResultSet.getLong("id");
                String firstName = studentResultSet.getString("firstName");
                String lastName = studentResultSet.getString("lastName");
                String email = studentResultSet.getString("email");
                LocalDate dateOfBirth = studentResultSet.getDate("dateOfBirth").toLocalDate();
                long filliereId = studentResultSet.getLong("filliere_id");
                String filliereName = "";

                try (PreparedStatement filliereStatement = connection.prepareStatement(filliereQuery)) {
                    filliereStatement.setLong(1, filliereId);
                    ResultSet filliereResultSet = filliereStatement.executeQuery();
                    if (filliereResultSet.next()) {
                        filliereName = filliereResultSet.getString("filliereName");
                    }
                }

                // Filliere filliere = new Filliere(filliereId, filliereName, description);
                Student student = new Student(studentId, firstName, lastName, email, dateOfBirth, filliereName);
                studentList.add(student);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return List.of();
        }

        return studentList;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFilliere() {
        return filliere;
    }

    public void setFilliere(String filliere) {
        this.filliere = filliere;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", filliere='" + filliere + '\'' +
                '}';
    }
}
