package ma.example.schoolsystem.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ma.example.schoolsystem.Database;

public class Grade {
    private long id;
    private String student;
    private String module;
    private float gradeValue;

    public Grade(long id, String student, String module, float gradeValue) {
        this.id = id;
        this.student = student;
        this.module = module;
        this.gradeValue = gradeValue;
    }

    public static List<Grade> getAllGradesFromDB() {
        List<Grade> gradeList = new ArrayList<>();
        String studentQuery = "SELECT * FROM students WHERE id = ?";
        String moduleQuery = "SELECT * FROM modules WHERE id = ?";

        try (
                Connection connection = Database.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM grades");
                ResultSet resultSet = statement.executeQuery();) {
            System.out.println("Connected to the database.");
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long studentId = resultSet.getLong("student_id");
                long moduleId = resultSet.getLong("module_id");
                float gradeValue = resultSet.getFloat("gradeValue");
                String studentFirstName = "";
                String studentLastName = "";
                String moduleModuleName = "";

                try (PreparedStatement studentStatement = connection.prepareStatement(studentQuery)) {
                    studentStatement.setLong(1, studentId);
                    ResultSet studentResultSet = studentStatement.executeQuery();

                    if (studentResultSet.next()) {
                        studentFirstName = studentResultSet.getString("firstName");
                        studentLastName = studentResultSet.getString("lastName");
                    }

                    studentResultSet.close();
                }

                try (PreparedStatement moduleStatement = connection.prepareStatement(moduleQuery)) {
                    moduleStatement.setLong(1, moduleId);
                    ResultSet moduleResultSet = moduleStatement.executeQuery();

                    if (moduleResultSet.next()) {
                        moduleModuleName = moduleResultSet.getString("moduleName");
                    }

                    moduleResultSet.close();
                }

                gradeList.add(new Grade(id,
                        String.join(studentFirstName, studentLastName),
                        moduleModuleName,
                        gradeValue));
            }
        } catch (SQLException e) {
            System.out.println("err");
        }
        return gradeList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public float getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(float gradeValue) {
        this.gradeValue = gradeValue;
    }
}
