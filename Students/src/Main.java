import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    Results studentsResults = new Results(70);
    public TextField studentNameInput = new TextField();
    TextField studentGradeInput = new TextField();

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane pane = new Pane();
        Scene scene = new Scene(pane, 480, 500);

        //Lables
        Label studentName = new Label("Student Name");
        studentName.setTranslateX(10);
        studentName.setTranslateY(15);
        Label studentGrade = new Label("Grade");
        studentGrade.setTranslateX(180);
        studentGrade.setTranslateY(15);
        Label bestStudentName = new Label();
        bestStudentName.setVisible(false);
        Label bestStudentGrade = new Label("Grade: ");
        bestStudentGrade.setVisible(false);

        //Fields
        studentNameInput.setTranslateX(10);
        studentNameInput.setTranslateY(40);
        studentGradeInput.setTranslateX(180);
        studentGradeInput.setTranslateY(40);

        // Numbers only
        studentGradeInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    studentGradeInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        //Buttons
        Button add = new Button("Add");
        add.setTranslateX(340);
        add.setTranslateY(40);
        add.setDefaultButton(true);
        add.setOnAction(event -> {
            //Empty fields validation
            if(studentNameInput.getText().isEmpty() || studentGradeInput.getText().isEmpty()){
                AlertBox.display("Input Validation", "Please fill in student's name and grade", false);
            } else if(studentsResults.getResults().size() == 70){
                boolean restart = AlertBox.display("Maximum reached", "Only 70 students are accepted", true);
                if(restart){
                    studentsResults.getResults().clear();
                    clearInputs();
                    AlertBox.display("Successful clearing", "Previous information is cleared, You can restart", false);
                }
            }
            else {

                String nameInput = studentNameInput.getText();
                Integer gradeInput = Integer.parseInt(studentGradeInput.getText());

                clearInputs();

                Student student = new Student(nameInput, gradeInput);
                studentsResults.getResults().add(student);
                // Debug
                // System.out.println(studentsResults);
            }
        });

        Button generate = new Button("Generate");
        generate.setTranslateX(400);
        generate.setTranslateY(40);
        generate.setOnAction(event -> {
            //Fields
            bestStudentName.setText("Best Student Name: " + studentsResults.studentMax().getName());
            bestStudentName.setTranslateX(10);
            bestStudentName.setTranslateY(70);
            bestStudentName.setVisible(true);
            bestStudentGrade.setText("Grade: " + studentsResults.studentMax().getGrade());
            bestStudentGrade.setTranslateX(180);
            bestStudentGrade.setTranslateY(70);
            bestStudentGrade.setVisible(true);
        });

        pane.getChildren().addAll(studentName, studentGrade, studentNameInput, studentGradeInput, bestStudentName, bestStudentGrade, add, generate);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void clearInputs(){
        studentGradeInput.clear();
        studentNameInput.clear();
        studentNameInput.requestFocus();
    }
}
