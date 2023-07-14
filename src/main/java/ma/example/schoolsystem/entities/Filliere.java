package ma.example.schoolsystem.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ma.example.schoolsystem.Database;

public class Filliere {
    long id;
    String filliereName;
    String description;

    public Filliere(long id, String filliereName, String description) {
        this.id = id;
        this.filliereName = filliereName;
        this.description = description;
    }

    public static List<Filliere> getAllFillieresFromDB() {
        List<Filliere> filliereList = new ArrayList<>();
        try (
                Connection connection = Database.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM fillieres");
                ResultSet resultSet = statement.executeQuery();) {
            System.out.println("Connected to the database.");
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String filliereName = resultSet.getString("filliereName");
                String description = resultSet.getString("description");
                filliereList.add(new Filliere(id, filliereName, description));
            }

        } catch (SQLException e) {
            System.out.println("err");
            return List.of();
        }
        return filliereList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilliereName() {
        return filliereName;
    }

    public void setFilliereName(String filliereName) {
        this.filliereName = filliereName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
