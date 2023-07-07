package ma.example.schoolsystem;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class SystemController {
    @FXML
    private AnchorPane studentsPage;

    @FXML
    private AnchorPane programsPage;

    @FXML
    private AnchorPane modulesPage;

    @FXML
    private AnchorPane gradesPage;

    @FXML
    void showStudentsPage() {
        studentsPage.setVisible(true);
        programsPage.setVisible(false);
        modulesPage.setVisible(false);
        gradesPage.setVisible(false);
    }

    @FXML
    void showProgramsPage() {
        studentsPage.setVisible(false);
        programsPage.setVisible(true);
        modulesPage.setVisible(false);
        gradesPage.setVisible(false);
    }

    @FXML
    void showGradesPage() {
        studentsPage.setVisible(false);
        programsPage.setVisible(false);
        modulesPage.setVisible(false);
        gradesPage.setVisible(true);
    }

    @FXML
    void showModulesPage() {
        studentsPage.setVisible(false);
        programsPage.setVisible(false);
        modulesPage.setVisible(true);
        gradesPage.setVisible(false);
    }


}
