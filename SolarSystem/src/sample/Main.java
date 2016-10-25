package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Scene scene = new Scene(new Group());
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Solar System");
        DropShadow shadow = new DropShadow();
        VBox vbox = new VBox();
        vbox.setLayoutX(900);
        vbox.setLayoutY(575);
        HBox hbox1 = new HBox();
        Button actionButton = new Button("Start/Stop");
//        actionButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override public void handle(ActionEvent e) {
//
//            }
//        });
        actionButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> actionButton.setEffect(shadow));

        actionButton.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> actionButton.setEffect(null));
				
        hbox1.setSpacing(10);
        hbox1.setAlignment(Pos.BOTTOM_CENTER);
        hbox1.getChildren().add(actionButton);
        vbox.getChildren().add(hbox1);
        ((Group)scene.getRoot()).getChildren().add(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
		
    }

    public static void main(String[] args) {
        launch(args);
    }
}
