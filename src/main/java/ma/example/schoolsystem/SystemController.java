package ma.example.schoolsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import ma.example.schoolsystem.entities.Filliere;
import ma.example.schoolsystem.entities.Grade;
import ma.example.schoolsystem.entities.Module;
import ma.example.schoolsystem.entities.Student;
import ma.example.schoolsystem.entities.Student;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.xml.transform.Result;

public class SystemController implements Initializable {

    List<Student> studentList;
    List<Filliere> filliereList;
    List<Module> moduleList;
    List<Grade> gradeList;

    private Long currentStudent;
    private Long currentProgram;
    private Long currentGrade;
    private Long currentModule;

    @FXML
    private ComboBox<String> grade_module;

    @FXML
    private ComboBox<String> grade_student;
    @FXML
    private ComboBox<String> module_program;
    @FXML
    private ComboBox<String> student_filliere;

    @FXML
    private TextField grade_value;

    @FXML
    private AnchorPane gradesPage;

    @FXML
    private AnchorPane main;

    @FXML
    private TextField module_credits;

    @FXML
    private TextArea module_description;

    @FXML
    private TextField module_name;

    @FXML
    private AnchorPane modulesPage;

    @FXML
    private TextArea program_description;

    @FXML
    private TextField program_name;

    @FXML
    private AnchorPane programsPage;

    @FXML
    private TextField student_first_name;

    @FXML
    private TextField student_last_name;
    @FXML
    private DatePicker student_birth_day;
    @FXML
    private TextField student_email;

    @FXML
    private AnchorPane studentsPage;

    @FXML
    private TableColumn<Grade, Void> table_grade_action;

    @FXML
    private TableColumn<?, ?> table_grade_id;

    @FXML
    private TableColumn<?, ?> table_grade_module_name;

    @FXML
    private TableColumn<?, ?> table_grade_student_name;

    @FXML
    private TableColumn<?, ?> table_grade_value;

    @FXML
    private TableColumn<Module, Void> table_module_action;

    @FXML
    private TableColumn<?, ?> table_module_credits;

    @FXML
    private TableColumn<?, ?> table_module_description;

    @FXML
    private TableColumn<?, ?> table_module_id;

    @FXML
    private TableColumn<?, ?> table_module_name;

    @FXML
    private TableColumn<?, ?> table_module_program;

    @FXML
    private TableColumn<Filliere, Void> table_program_action;

    @FXML
    private TableColumn<?, ?> table_program_description;

    @FXML
    private TableColumn<?, ?> table_program_id;

    @FXML
    private TableColumn<String, String> table_program_name;

    @FXML
    private TableColumn<Student, Void> table_student_action;

    @FXML
    private TableColumn<Date, Date> table_student_birth_day;

    @FXML
    private TableColumn<String, String> table_student_email;

    @FXML
    private TableColumn<String, String> table_student_filliere;

    @FXML
    private TableColumn<String, String> table_student_first_name;

    @FXML
    private TableColumn<Long, Long> table_student_id;

    @FXML
    private TableColumn<String, String> table_student_last_name;

    // tables :
    @FXML
    private TableView<Grade> table_grades;

    @FXML
    private TableView<Student> table_students;

    @FXML
    private TableView<Filliere> table_programs;

    @FXML
    private TableView<Module> table_modules;

    @FXML
    private Button newStudentBtn;

    @FXML
    private Button newProgramBtn;

    @FXML
    private Button newModuleBtn;

    @FXML
    private Button newGradeBtn;

    @FXML
    private Button calculer_score;

    @FXML
    private Label score;

    @FXML
    private TextField student_search_input;

    @FXML
    private TextField program_search_input;

    @FXML
    private TextField grade_search_input;

    @FXML
    private TextField module_search_input;

    @FXML
    void showStudentsPage() {
        getData();
        studentsPage.setVisible(true);
        programsPage.setVisible(false);
        modulesPage.setVisible(false);
        gradesPage.setVisible(false);
    }

    @FXML
    void showProgramsPage() {
        getData();
        studentsPage.setVisible(false);
        programsPage.setVisible(true);
        modulesPage.setVisible(false);
        gradesPage.setVisible(false);
    }

    @FXML
    void showGradesPage() {
        getData();
        studentsPage.setVisible(false);
        programsPage.setVisible(false);
        modulesPage.setVisible(false);
        gradesPage.setVisible(true);
    }

    @FXML
    void showModulesPage() {
        getData();
        studentsPage.setVisible(false);
        programsPage.setVisible(false);
        modulesPage.setVisible(true);
        gradesPage.setVisible(false);
    }

    // search functionality
    @FXML
    void rechercherStudent(KeyEvent event) {
        String pattern = student_search_input.getText().trim();
        ObservableList<Student> students = FXCollections.observableArrayList();
        for (Student student : studentList) {
            if (student.getFirstName().contains(pattern) || student.getLastName().contains(pattern)
                    || student.getEmail().contains(pattern) || student.getDateOfBirth().toString().contains(pattern)
                    || student.getFilliere().contains(pattern)) {
                students.add(student);
            }
        }
        table_students.setItems(students);

    }

    @FXML
    void rechercherModule(KeyEvent event) {
        String pattern = module_search_input.getText().trim();

        ObservableList<Module> modules = FXCollections.observableArrayList();
        for (Module module : moduleList) {
            if (module.getModuleName().contains(pattern) || module.getDescription().contains(pattern)
                    || String.valueOf(module.getCredits()).contains(pattern)
                    || module.getFilliere().contains(pattern)) {
                modules.add(module);
            }
        }
        table_modules.setItems(modules);

    }

    @FXML
    void rechercherProgram(KeyEvent event) {
        String pattern = program_search_input.getText().trim();
        ObservableList<Filliere> programs = FXCollections.observableArrayList();
        for (Filliere filliere : filliereList) {
            if (filliere.getFilliereName().contains(pattern) || filliere.getDescription().contains(pattern)) {
                programs.add(filliere);
            }
        }
        table_programs.setItems(programs);
    }

    @FXML
    void rechercherGrade(KeyEvent event) {
        String pattern = grade_search_input.getText().trim();
        ObservableList<Grade> grades = FXCollections.observableArrayList();
        for (Grade grade : gradeList) {
            if (grade.getModule().contains(pattern) || grade.getStudent().contains(pattern)
                    || String.valueOf(grade.getGradeValue()).contains(pattern)) {
                grades.add(grade);
            }
        }
        table_grades.setItems(grades);
    }

    @FXML
    void calculerScore(ActionEvent ev) {
        // select all grades for the student
        String query = "SELECT * FROM grades WHERE student_id = ?;";
        try (Connection connection = Database.getConnection();
                PreparedStatement gradeStatement = connection.prepareStatement(query);) {
            System.out.println("Connected to the database.");
            gradeStatement.setLong(1, currentStudent);
            ResultSet resultSet = gradeStatement.executeQuery();
            int score = 0;
            int numGrades = 0;
            while (resultSet.next()) {
                score += resultSet.getInt("gradeValue");
                numGrades++;
            }
            if(numGrades>0) {
                score = score / numGrades;
                this.score.setText("score is : " + String.valueOf(score) + " / 20");
            }else{
                this.score.setText("no grade entered for the student ! ");
            }
        } catch (SQLException exception) {
            System.out.println("err" + exception.getMessage());
        }

    }

    @FXML
    void saveNewStudent(ActionEvent event) {
        String firstName = student_first_name.getText();
        String lastName = student_last_name.getText();
        String email = student_email.getText();
        LocalDate dateOfBirth = student_birth_day.getValue();
        String filliere = student_filliere.getValue();

        System.out.println("fn " + firstName);
        System.out.println("e " + email);
        System.out.println("d " + dateOfBirth);
        System.out.println("f " + filliere);

        if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && dateOfBirth != null && filliere != null
                && filliere.contains("-")) {
            if (newStudentBtn.getText().equals("Update")) {
                System.out.println("updating student ...");
                String query = "UPDATE students SET firstName = ?, lastName = ?, email = ?, dateOfBirth = ?, filliere_id = ? WHERE id = ?;";
                try (Connection connection = Database.getConnection();
                        PreparedStatement studentStatement = connection.prepareStatement(query);) {
                    System.out.println("Connected to the database.");
                    studentStatement.setString(1, firstName);
                    studentStatement.setString(2, lastName);
                    studentStatement.setString(3, email);
                    studentStatement.setDate(4,
                            java.sql.Date.valueOf(dateOfBirth));
                    studentStatement.setInt(5, Integer.valueOf(filliere.split("-")[0]));
                    studentStatement.setLong(6, currentStudent);
                    studentStatement.executeUpdate();
                } catch (SQLException exception) {
                    System.out.println("err" + exception.getMessage());
                }
                // change button text to new student
                newStudentBtn.setText("New Student");

                // clear fields
                student_first_name.setText("");
                student_last_name.setText("");
                student_email.setText("");
                student_birth_day.setValue(null);
                student_filliere.setValue(null);

                score.setText("");
                calculer_score.setVisible(false);

                // refresh
                getData();
                return;
            } else {
                String query = "INSERT INTO students (firstName,lastName,email,dateOfBirth,filliere_id) values (?,?,?,?,?);";
                try (Connection connection = Database.getConnection();
                        PreparedStatement studentStatement = connection.prepareStatement(query);) {
                    System.out.println("Connected to the database.");
                    studentStatement.setString(1, firstName);
                    studentStatement.setString(2, lastName);
                    studentStatement.setString(3, email);
                    studentStatement.setDate(4,
                            java.sql.Date.valueOf(dateOfBirth));
                    studentStatement.setInt(5, Integer.valueOf(filliere.split("-")[0]));
                    studentStatement.executeUpdate();
                } catch (SQLException exception) {
                    System.out.println("err" + exception.getMessage());
                }
                // clear fields
                student_first_name.setText("");
                student_last_name.setText("");
                student_email.setText("");
                student_birth_day.setValue(null);
                student_filliere.setValue(null);
                // refresh
                getData();
            }
        }
    }

    @FXML
    void saveNewProgram(ActionEvent ev) {
        String filliereName = program_name.getText();
        String description = program_description.getText();
        if (!filliereName.isEmpty() && !description.isEmpty()) {
            if (newProgramBtn.getText().equals("Update")) {
                System.out.println("updating filliere ...");
                String query = "UPDATE fillieres SET filliereName = ?, description = ? WHERE id = ?;";
                try (Connection connection = Database.getConnection();
                        PreparedStatement filliereStatement = connection.prepareStatement(query);) {
                    System.out.println("Connected to the database.");
                    filliereStatement.setString(1, filliereName);
                    filliereStatement.setString(2, description);
                    filliereStatement.setLong(3, currentProgram);
                    filliereStatement.executeUpdate();
                } catch (SQLException exception) {
                    System.out.println("err " + exception.getMessage());
                }
                // change button text to new student
                newProgramBtn.setText("New Program");

                // clean fields
                program_name.setText("");
                program_description.setText("");

                // refresh
                getData();
                return;
            } else {
                String query = "INSERT INTO fillieres (filliereName,description) values (?,?);";
                try (Connection connection = Database.getConnection();
                        PreparedStatement filliereStatement = connection.prepareStatement(query);) {
                    System.out.println("Connected to the database.");
                    filliereStatement.setString(1, filliereName);
                    filliereStatement.setString(2, description);
                    filliereStatement.executeUpdate();
                } catch (SQLException exception) {
                    System.out.println("err " + exception.getMessage());
                }
                // clear fields
                program_name.setText("");
                program_description.setText("");
                // refresh
                getData();
            }
        }
    }

    @FXML
    void saveNewModule(ActionEvent ev) {
        String moduleName = module_name.getText();
        String description = module_description.getText();
        String credits = module_credits.getText();
        String filliere = module_program.getValue();
        if (!moduleName.isEmpty() && !description.isEmpty() && !credits.isEmpty() && filliere != null
                && filliere.contains("-")) {
            if (newModuleBtn.getText().equals("Update")) {
                System.out.println("updating module ...");
                String query = "UPDATE modules SET moduleName = ?, description = ?, credits = ?, filliere_id = ? WHERE id = ?;";
                try (Connection connection = Database.getConnection();
                        PreparedStatement moduleStatement = connection.prepareStatement(query);) {
                    System.out.println("Connected to the database.");
                    moduleStatement.setString(1, moduleName);
                    moduleStatement.setString(2, description);
                    moduleStatement.setInt(3, Integer.valueOf(credits));
                    moduleStatement.setInt(4, Integer.valueOf(filliere.split("-")[0]));
                    moduleStatement.setLong(5, currentModule);
                    moduleStatement.executeUpdate();
                } catch (SQLException exception) {
                    System.out.println("err " + exception.getMessage());
                }
                // change button text to new student
                newModuleBtn.setText("New Module");
                // refresh
                getData();
                return;
            } else {
                String query = "INSERT INTO modules (moduleName,description,credits,filliere_id) values (?,?,?,?);";
                try (Connection connection = Database.getConnection();
                        PreparedStatement moduleStatement = connection.prepareStatement(query);) {
                    System.out.println("Connected to the database.");
                    moduleStatement.setString(1, moduleName);
                    moduleStatement.setString(2, description);
                    moduleStatement.setInt(3, Integer.valueOf(credits));
                    moduleStatement.setInt(4, Integer.valueOf(filliere.split("-")[0]));
                    moduleStatement.executeUpdate();
                } catch (SQLException exception) {
                    System.out.println("err " + exception.getMessage());
                }
                // clear fileds
                module_name.setText("");
                module_description.setText("");
                module_credits.setText("");
                module_program.setValue(null);
                // refresh
                getData();
            }
        }

    }

    @FXML
    void saveNewGrade(ActionEvent ev) {
        String student = grade_student.getValue();
        String module = grade_module.getValue();
        String value = grade_value.getText();
        if (student != null && student.contains("-") && module != null && module.contains("-") && !value.isEmpty()) {
            if (newGradeBtn.getText().equals("Update")) {
                System.out.println("updating grade ...");
                String query = "UPDATE grades SET student_id = ?, module_id = ?, gradeValue = ? WHERE id = ?;";
                try (Connection connection = Database.getConnection();
                        PreparedStatement gradeStatement = connection.prepareStatement(query);) {
                    System.out.println("Connected to the database.");
                    gradeStatement.setInt(1, Integer.valueOf(student.split("-")[0]));
                    gradeStatement.setInt(2, Integer.valueOf(module.split("-")[0]));
                    gradeStatement.setInt(3, Integer.valueOf(value));
                    gradeStatement.setLong(4, currentGrade);
                    gradeStatement.executeUpdate();
                } catch (SQLException exception) {
                    System.out.println("err " + exception.getMessage());
                }
                // change button text to new student
                newGradeBtn.setText("New Grade");

                // clear fields
                grade_student.setValue(null);
                grade_module.setValue(null);
                grade_value.setText("");

                // refresh
                getData();
                return;
            } else {
                String query = "INSERT INTO grades (student_id,module_id,gradeValue) values (?,?,?);";
                try (Connection connection = Database.getConnection();
                        PreparedStatement gradeStatement = connection.prepareStatement(query);) {
                    System.out.println("Connected to the database.");
                    gradeStatement.setInt(1, Integer.valueOf(student.split("-")[0]));
                    gradeStatement.setInt(2, Integer.valueOf(module.split("-")[0]));
                    gradeStatement.setInt(3, Integer.valueOf(value));
                    gradeStatement.executeUpdate();
                } catch (SQLException exception) {
                    System.out.println("err " + exception.getMessage());
                }
                // clear fields
                grade_student.setValue(null);
                grade_module.setValue(null);
                grade_value.setText("");
                // refresh
                getData();
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getData();

    }

    void getData() {
        studentList = Student.getAllStudentsFromDB();
        filliereList = Filliere.getAllFillieresFromDB();
        moduleList = Module.getAllModulesFromDB();
        gradeList = Grade.getAllGradesFromDB();

        ObservableList<Student> students = FXCollections.observableArrayList(studentList);
        ObservableList<Grade> grades = FXCollections.observableArrayList(gradeList);
        ObservableList<Filliere> programs = FXCollections.observableArrayList(filliereList);
        ObservableList<Module> modules = FXCollections.observableArrayList(moduleList);
        table_students.setItems(students);
        table_grades.setItems(grades);
        table_programs.setItems(programs);
        table_modules.setItems(modules);

        // Map the model properties to the TableView columns
        table_student_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_student_first_name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        table_student_last_name.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        table_student_birth_day.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        table_student_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        table_student_filliere.setCellValueFactory(new PropertyValueFactory<>("filliere"));

        table_program_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_program_name.setCellValueFactory(new PropertyValueFactory<>("filliereName"));
        table_program_description.setCellValueFactory(new PropertyValueFactory<>("description"));

        table_module_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_module_name.setCellValueFactory(new PropertyValueFactory<>("moduleName"));
        table_module_credits.setCellValueFactory(new PropertyValueFactory<>("credits"));
        table_module_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        table_module_program.setCellValueFactory(new PropertyValueFactory<>("filliere"));

        table_grade_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_grade_student_name.setCellValueFactory(new PropertyValueFactory<>("student"));
        table_grade_module_name.setCellValueFactory(new PropertyValueFactory<>("module"));
        table_grade_value.setCellValueFactory(new PropertyValueFactory<>("gradeValue"));

        table_student_action.setCellFactory(param -> extractedStudentAction());
        table_program_action.setCellFactory(param -> extractedProgramAction());
        table_grade_action.setCellFactory(param -> extractedGradeAction());
        table_module_action.setCellFactory(param -> extractedModuleAction());

        // intantiate combobox
        ObservableList<String> filliereNames = FXCollections.observableArrayList();
        for (Filliere filliere : filliereList) {
            filliereNames.add(filliere.getId() + "-" + filliere.getFilliereName());
        }
        ObservableList<String> moduleNames = FXCollections.observableArrayList();
        for (Module module : moduleList) {
            moduleNames.add(module.getId() + "-" + module.getModuleName());
        }
        ObservableList<String> studentNames = FXCollections.observableArrayList();
        for (Student student : studentList) {
            studentNames.add(student.getId() + "-" + student.getFirstName() + " " + student.getLastName());
        }

        // fill comboboxes :
        student_filliere.setItems(filliereNames);
        module_program.setItems(filliereNames);
        grade_module.setItems(moduleNames);
        grade_student.setItems(studentNames);
    }

    private TableCell<Filliere, Void> extractedProgramAction() {
        return new TableCell<Filliere, Void>() {
            private final Button deleteButton = new Button("Delete");
            private final Button updateButton = new Button("Update");

            {
                deleteButton.setOnAction(event -> extracted());

                updateButton.setOnAction(event -> {
                    // Handle update button action
                    Filliere filliere = getTableRow().getItem();
                    if (filliere != null) {
                        // Perform update operation
                        updateProgram(filliere);
                    }
                });
            }

            private void extracted() {
                // Handle delete button action
                Filliere filliere = getTableRow().getItem();
                if (filliere != null) {
                    // Perform delete operation
                    deleteProgram(filliere);
                }
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttonsContainer = new HBox(5);
                    buttonsContainer.getChildren().addAll(deleteButton, updateButton);
                    setGraphic(buttonsContainer);
                }
            }
        };
    }

    void updateProgram(Filliere filliere) {
        System.out.println("updating filliere ...");
        // set fields values
        currentProgram = filliere.getId();
        program_name.setText(filliere.getFilliereName());
        program_description.setText(filliere.getDescription());
        // change new student button to update button
        newProgramBtn.setText("Update");
    }

    void deleteProgram(Filliere filliere) {
        System.out.println("deleting filliere");
        String query = "DELETE FROM fillieres WHERE id = ?;";
        try (Connection connection = Database.getConnection();
                PreparedStatement filliereStatement = connection.prepareStatement(query);) {
            System.out.println("Connected to the database.");
            filliereStatement.setLong(1, filliere.getId());
            filliereStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("err" + exception.getMessage());
        }
        // refresh
        getData();
    }

    private TableCell<Module, Void> extractedModuleAction() {
        return new TableCell<Module, Void>() {
            private final Button deleteButton = new Button("Delete");
            private final Button updateButton = new Button("Update");

            {
                deleteButton.setOnAction(event -> extracted());

                updateButton.setOnAction(event -> {
                    Module module = getTableRow().getItem();
                    if (module != null) {
                        updateModule(module);
                    }
                });
            }

            private void extracted() {
                // Handle delete button action
                Module module = getTableRow().getItem();
                if (module != null) {
                    // Perform delete operation
                    deleteModule(module);
                }
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttonsContainer = new HBox(5);
                    buttonsContainer.getChildren().addAll(deleteButton, updateButton);
                    setGraphic(buttonsContainer);
                }
            }
        };
    }

    void updateModule(Module module) {
        System.out.println("updating module ...");
        // set fields values
        currentModule = module.getId();
        module_name.setText(module.getModuleName());
        module_description.setText(module.getDescription());
        module_credits.setText(String.valueOf(module.getCredits()));

        // intantiate combobox
        ObservableList<String> filliereNames = FXCollections.observableArrayList();
        for (Filliere filliere : Filliere.getAllFillieresFromDB()) {
            filliereNames.add(filliere.getId() + "-" + filliere.getFilliereName());
            if (filliere.equals(module_program.getValue())) {
                module_program.setValue(filliere.getId() + "-" + module.getFilliere());
            }

        }
        module_program.setItems(filliereNames);

        // change new student button to update button
        newModuleBtn.setText("Update");
    }

    void deleteModule(Module module) {
        System.out.println("deleting module");
        String query = "DELETE FROM modules WHERE id = ?;";
        try (Connection connection = Database.getConnection();
                PreparedStatement moduleStatement = connection.prepareStatement(query);) {
            System.out.println("Connected to the database.");
            moduleStatement.setLong(1, module.getId());
            moduleStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("err" + exception.getMessage());
        }
        // refresh
        getData();
    }

    private TableCell<Grade, Void> extractedGradeAction() {
        return new TableCell<Grade, Void>() {
            private final Button deleteButton = new Button("Delete");
            private final Button updateButton = new Button("Update");

            {
                deleteButton.setOnAction(event -> extracted());

                updateButton.setOnAction(event -> {
                    // Handle update button action
                    Grade grade = getTableRow().getItem();
                    if (grade != null) {
                        // Perform update operation
                        updateGrade(grade);
                    }
                });
            }

            private void extracted() {
                // Handle delete button action
                Grade grade = getTableRow().getItem();
                if (grade != null) {
                    // Perform delete operation
                    deleteGrade(grade);
                }
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttonsContainer = new HBox(5);
                    buttonsContainer.getChildren().addAll(deleteButton, updateButton);
                    setGraphic(buttonsContainer);
                }
            }
        };
    }

    void updateGrade(Grade grade) {
        System.out.println("updating grade ...");
        currentGrade = grade.getId();
        // set fields values
        grade_value.setText(String.valueOf(grade.getGradeValue()));

        // intantiate combobox
        ObservableList<String> moduleNames = FXCollections.observableArrayList();
        for (Module module : Module.getAllModulesFromDB()) {
            moduleNames.add(module.getId() + "-" + module.getModuleName());
            if (Objects.equals(module.getModuleName(), (grade.getModule()))) {
                grade_module.setValue(module.getId() + "-" + grade.getModule());
            }
        }
        ObservableList<String> studentNames = FXCollections.observableArrayList();
        for (Student student : Student.getAllStudentsFromDB()) {
            studentNames.add(student.getId() + "-" + student.getFirstName() + " " + student.getLastName());
            if (Objects.equals(student.getFirstName() + " " + student.getLastName(), grade.getStudent())) {
                grade_student.setValue(student.getId() + "-" + grade.getStudent());
            }
        }

        // fill comboboxes :
        grade_module.setItems(moduleNames);
        grade_student.setItems(studentNames);

        // change new student button to update button
        newGradeBtn.setText("Update");

    }

    void deleteGrade(Grade grade) {
        System.out.println("deleting grade");
        String query = "DELETE FROM grades WHERE id = ?;";
        try (Connection connection = Database.getConnection();
                PreparedStatement gradeStatement = connection.prepareStatement(query);) {
            System.out.println("Connected to the database.");
            gradeStatement.setLong(1, grade.getId());
            gradeStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("err" + exception.getMessage());
        }
        // refresh
        getData();
    }

    private TableCell<Student, Void> extractedStudentAction() {
        return new TableCell<Student, Void>() {
            private final Button deleteButton = new Button("Delete");
            private final Button updateButton = new Button("Update");

            {
                deleteButton.setOnAction(event -> extracted());

                updateButton.setOnAction(event -> {
                    // Handle update button action
                    Student student = getTableRow().getItem();
                    if (student != null) {
                        // Perform update operation
                        updateStudent(student);
                    }
                });
            }

            private void extracted() {
                // Handle delete button action
                Student student = getTableRow().getItem();
                if (student != null) {
                    // Perform delete operation
                    deleteStudent(student);
                }
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttonsContainer = new HBox(5);
                    buttonsContainer.getChildren().addAll(deleteButton, updateButton);
                    setGraphic(buttonsContainer);
                }
            }
        };
    }

    void updateStudent(Student student) {
        System.out.println("updating student ...  " + student);
        // set fields values
        currentStudent = student.getId();
        student_first_name.setText(student.getFirstName());
        student_last_name.setText(student.getLastName());
        student_email.setText(student.getEmail());
        student_birth_day.setValue(student.getDateOfBirth());

        // intantiate combobox
        ObservableList<String> filliereNames = FXCollections.observableArrayList();
        for (Filliere filliere : Filliere.getAllFillieresFromDB()) {
            filliereNames.add(filliere.getId() + "-" + filliere.getFilliereName());
            if (filliere.equals(student_filliere.getValue())) {
                student_filliere.setValue(filliere.getId() + "-" + student.getFilliere());
            }
        }
        // fill comboboxes :
        student_filliere.setItems(filliereNames);

        // change new student button to update button
        newStudentBtn.setText("Update");
        calculer_score.setVisible(true);

    }

    void deleteStudent(Student student) {
        System.out.println("deleting student");
        String query = "DELETE FROM students WHERE id = ?;";
        try (Connection connection = Database.getConnection();
                PreparedStatement studentStatement = connection.prepareStatement(query);) {
            System.out.println("Connected to the database.");
            studentStatement.setLong(1, student.getId());
            studentStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("err" + exception.getMessage());
        }
        // refresh
        getData();
    }
}
