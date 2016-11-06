package main;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.stream.Collectors;

public class Main extends Application {

    @FXML
    StackPane space;

    @FXML
    ToggleButton startButton;

    @FXML
    ToggleButton directionButton;

    Planets planets;

    TranslateTransition transition1;
    TranslateTransition transition2;
    TranslateTransition transition3;
    TranslateTransition transition4;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/solarSystem.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Solar System");
        primaryStage.getIcons().add( new Image( Main.class.getResourceAsStream( "../resources/icon.png" )));

        loadPlanets();

        space.getChildren().addAll(planets.getPlanets().stream().map(Planet::createView).collect(Collectors.toList()));
        space.setStyle("-fx-background-color: BLACK;");
        space.setBorder(new Border(new BorderStroke(Color.ORANGE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        transition1 = new TranslateTransition();
        transition1.setToX(50);
        transition1.setToY(-50);
        transition1.setDuration(Duration.seconds(5));
        transition1.setNode(space.getChildren().get(1));

        transition2 = new TranslateTransition();
        transition2.setToX(-75);
        transition2.setToY(-220);
        transition2.setDuration(Duration.seconds(2));
        transition2.setNode(space.getChildren().get(2));

        transition3 = new TranslateTransition();
        transition3.setToX(40);
        transition3.setToY(130);
        transition3.setDuration(Duration.seconds(4));
        transition3.setNode(space.getChildren().get(3));

        transition4 = new TranslateTransition();
        transition4.setToX(-180);
        transition4.setToY(150);
        transition4.setDuration(Duration.seconds(3));
        transition4.setNode(space.getChildren().get(4));

        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    if(startButton.isSelected() == true) {
                        startMotion();
                        startButton.setText("Pause");
                    } else {
                        pauseMotion();
                        startButton.setText("Resume");
                    }
                });


        primaryStage.show();

    }

    public void startMotion(){
                transition1.play();
                transition2.play();
                transition3.play();
                transition4.play();
    }

    public void pauseMotion(){
                transition1.pause();
                transition2.pause();
                transition3.pause();
                transition4.pause();
    }


    public void loadPlanets() {

        // JAXB unmarshalling is made basing on this example
        // https://examples.javacodegeeks.com/core-java/xml/bind/jaxb-unmarshal-example/

        try {

            File file = new File("src/resources/planets.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Planets.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            planets = (Planets) jaxbUnmarshaller.unmarshal(file);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}