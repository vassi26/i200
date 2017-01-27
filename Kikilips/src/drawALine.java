import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

    public class drawALine extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception {
            Pane pane = new Pane();
            Scene scene = new Scene(pane, 500, 500);

            TextField textfield = new TextField();
            Button submit = new Button("Joonista!");
            submit.setDefaultButton(true);
            submit.setTranslateX(170);
            submit.setOnAction(event -> {

                String input = textfield.getText();

                String[] k = input.split("-");
                int[] intK = new int[k.length];
                for (int i = 0; i < k.length; i++) {
                    intK[i] = Integer.parseInt(k[i]);
                }
                Line line = new Line(intK[0], intK[1], intK[2], intK[3]);
                pane.getChildren().add(line);
            });

            pane.getChildren().addAll(textfield, submit);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

