package ma.example.schoolsystem.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ma.example.schoolsystem.Database;

public class Module {
    private long id;
    private String moduleName;
    private String description;
    private int credits;
    private String filliere;

    public Module(long id, String moduleName, String description, int credits, String filliere) {
        this.id = id;
        this.moduleName = moduleName;
        this.description = description;
        this.credits = credits;
        this.filliere = filliere;
    }

    public static List<Module> getAllModulesFromDB() {
        List<Module> moduleList = new ArrayList<>();
        String fillierQuery = "SELECT * FROM fillieres WHERE id = ?";
        try (
                Connection connection = Database.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM modules");
                ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String moduleName = resultSet.getString("moduleName");
                String description = resultSet.getString("description");
                int credits = resultSet.getInt("credits");
                long filliereId = resultSet.getLong("filliere_id");
                String filliereName = "";
                try (PreparedStatement filliereStatement = connection.prepareStatement(fillierQuery)) {
                    filliereStatement.setLong(1, filliereId);
                    ResultSet filliereResultSet = filliereStatement.executeQuery();
                    if (filliereResultSet.next()) {
                        filliereName = filliereResultSet.getString("filliereName");
                    }
                    filliereResultSet.close();
                } catch (SQLException e) {
                    System.out.println("err"+e.getMessage());
                }
                moduleList.add(new Module(id, moduleName, description, credits, filliereName));
            }
        } catch (SQLException e) {
            System.out.println("err"+e.getMessage());
            return List.of();
        }
        return moduleList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getFilliere() {
        return filliere;
    }

    public void setFilliere(String filliere) {
        this.filliere = filliere;
    }
}
