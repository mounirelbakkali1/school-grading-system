package ma.example.schoolsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    private Validator validator = new Validator();
    @FXML
    private Label welcomeText;

    @FXML
    private Button loginButton;

    @FXML
    private TextField password;

    @FXML
    private TextField username;

    @FXML
    void signIn(ActionEvent event) throws SQLException {
       /* Connection connection = Database.getConnection();
        System.out.println("Connected to the database.");

        Statement statement = connection.createStatement();
        String query = "SELECT * FROM users";
        ResultSet resultSet = statement.executeQuery(query);
        boolean validLogin = false ;

        while (resultSet.next()) {
            String usr = resultSet.getString("username");
            String pwd = resultSet.getString("password");
            if (username.equals(usr) && password.equals(pwd)){
                validLogin = true ;
                break;
            }
        }
        resultSet.close();
        statement.close();
        connection.close();

*/
        System.out.println("clicked");
        System.out.println("username = "+username.getText() +" pwd : "+password.getText());

        if (username.getText().equals("root") && password.getText().equals("admin")){
            moveToSystem();
        }
    }

    private void moveToSystem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ma/example/schoolsystem/system.fxml"));
            Scene newScene = new Scene(loader.load());
            Stage currentStage = (Stage) password.getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.setTitle("School Grading  System");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    protected void moveToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ma/example/schoolsystem/login-page.fxml"));
            Scene newScene = new Scene(loader.load());
            Stage currentStage = (Stage) welcomeText.getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.setTitle("Login Page");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}