import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Alert {

    static boolean restart = false;

    public static boolean display(String title, String message, Boolean clear) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(150);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("OK");
        closeButton.setOnAction((e -> {restart = false; window.close();}));

        Button clearButton = new Button("Clear history and restart");
        //Second button is optional
        if (clear) {clearButton.setVisible(true);} else clearButton.setVisible(false);
        clearButton.setOnAction((e -> {restart = true; window.close();}));

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton, clearButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return restart;
    }
}