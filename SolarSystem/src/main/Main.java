package main;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main extends Application {

    @FXML
    StackPane space;

    @FXML
    ToggleButton startButton;

    @FXML
    ToggleButton directionButton;

    Planets planets;

    Duration duration = new Duration(100.0);

    List<TranslateTransition> transactions = new ArrayList<TranslateTransition>();

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

/*        for(Planet p : planets.getPlanets()){
            p.updateLocation();
        }*/

        space.getChildren().addAll(planets.getPlanets().stream().map(Planet::createView).collect(Collectors.toList()));
        space.setStyle("-fx-background-color: BLACK;");
        space.setBorder(new Border(new BorderStroke(Color.ORANGE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        Timeline time = new Timeline();
        time.setCycleCount(Animation.INDEFINITE);
        KeyFrame motion = new KeyFrame(duration,
                event -> {
                    for(int i = space.getChildren().size(); i > 0 ;i--){
                        Node planet = space.getChildren().get(i-1);
                        TranslateTransition t = new TranslateTransition(duration, planet);
                        t.setToX(planets.getPlanets().get(i-1).coordinates.getX());
                        t.setToY(planets.getPlanets().get(i-1).coordinates.getY());
                        transactions.add(t);
                        planets.getPlanets().get(i-1).updateLocation();
                    }
                    transactions.forEach(Animation::play);
                    /*for(TranslateTransition t : transactions){
                        t.play();
                    }*/
                });

        time.getKeyFrames().add(motion);

        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    if(startButton.isSelected() == true) {
                        time.play();
                        startButton.setText("Pause");
                    } else {
                        time.stop();
                        startButton.setText("Resume");
                    }
                });

        primaryStage.show();

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