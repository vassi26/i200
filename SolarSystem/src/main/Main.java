package main;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.stream.Collectors;

public class Main extends Application {

    @FXML
    StackPane space;

    Planets planets;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/solarSystem.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Solar System");


//        actionButton.setOnAction(MouseEvent.MOUSE_ENTERED, e -> actionButton.setEffect(shadow));
//        actionButton.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> actionButton.setEffect(shadow));
//        actionButton.addEventHandler(MouseEvent.MOUSE_EXITED, e -> actionButton.setEffect(null));

        loadPlanets();
        space.getChildren().addAll(planets.getPlanets().stream().map(Planet::createView).collect(Collectors.toList()));
        space.setStyle("-fx-background-color: BLACK;");

        space.setBorder(new Border(new BorderStroke(Color.ORANGE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        primaryStage.show();

    }
    public void loadPlanets() {

        try {

            File file = new File("src/resources/planets.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Planets.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            planets = (Planets) jaxbUnmarshaller.unmarshal(file);
            System.out.println(planets);


        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}