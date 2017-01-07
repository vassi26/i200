package main;

import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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

import static oracle.jrockit.jfr.events.Bits.doubleValue;

public class Main extends Application {

    @FXML
    StackPane space;

    @FXML
    ToggleButton startButton;

    @FXML
    ToggleButton directionButton;

    @FXML
    Slider slider;

    @FXML
    Label speed;

    Planets planets;

    Duration duration = new Duration(300.0);

    public Double rate;

    List<TranslateTransition> transactions = new ArrayList<TranslateTransition>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/solarSystem.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Solar System");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add( new Image( Main.class.getResourceAsStream( "../resources/icon.png" )));

        loadPlanets();

        space.getChildren().addAll(planets.getPlanets().stream().map(Planet::createView).collect(Collectors.toList()));
        space.getChildren().addAll(planets.getPlanets().stream().map(Planet::createTitle).collect(Collectors.toList()));
        space.setStyle("-fx-background-image: url(/resources/space.jpg);");
        space.setBorder(new Border(new BorderStroke(Color.PURPLE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        //https://www.youtube.com/watch?v=wWjgsepyE8I — Last Dawn ( Copyright and Royalty Free )
        String path = Main.class.getResource("/resources/Last-Dawn.mp3").toString();
        Media media = new Media(path);
        MediaPlayer mp = new MediaPlayer(media);
        //http://stackoverflow.com/questions/23498376/ahow-to-make-a-mp3-repeat-in-javafx
        mp.setOnEndOfMedia(new Runnable() {
            public void run() {
                mp.seek(Duration.ZERO);
            }
        });

        Timeline time = new Timeline();
        time.setCycleCount(Animation.INDEFINITE);
        KeyFrame motion = new KeyFrame(duration,
                event -> {
                    int j = space.getChildren().size();
                    for(int i = j/2 - 1; i > -1 ;i--){
                        Node planet = space.getChildren().get(i);
                        Node title = space.getChildren().get(j-1);
                        j--;
                        TranslateTransition tPlanet = new TranslateTransition(duration, planet);
                        TranslateTransition tTitle = new TranslateTransition(duration, title);
                        tPlanet.setToX(planets.getPlanets().get(i).coordinates.getX());
                        tPlanet.setToY(planets.getPlanets().get(i).coordinates.getY());
                        tTitle.setToX(planets.getPlanets().get(i).coordinates.getX()+25);
                        tTitle.setToY(planets.getPlanets().get(i).coordinates.getY()+25);
                        //helps make transitions smooth
                        tPlanet.setInterpolator(Interpolator.LINEAR);
                        tTitle.setInterpolator(Interpolator.LINEAR);
                        transactions.add(tPlanet);
                        transactions.add(tTitle);
                        planets.getPlanets().get(i).updateLocation();
                    }
                    transactions.forEach(Animation::play);
                });

        time.getKeyFrames().add(motion);

        /* ORIGINAL:
        *   for(Node object : space.getChildren() ){
            if(object.getId() != null) {
                object.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    planets.getPlanetByName(object.getId()).toggleTitle();});
            }
        }*/
        space.getChildren().stream().filter(object -> object.getId() != null).forEach(object -> {
            object.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                planets.getPlanetByName(object.getId()).toggleTitle();
            });
        });

        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    if(startButton.isSelected() == true) {
                        time.play();
                        mp.play();
                        startButton.setText("Pause");
                    } else {
                        time.stop();
                        mp.pause();
                        startButton.setText("Resume");
                    }
                });

        directionButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    if(directionButton.isSelected() == true) {
                        for(Planet p : planets.getPlanets()){
                            p.direction = -1;
                        }
                        directionButton.setText("Forward");
                    } else {
                        for(Planet p : planets.getPlanets()){
                            p.direction = 1;

                        }
                        directionButton.setText("Reverse");
                    }
                });
        //http://stackoverflow.com/questions/22780369/make-a-label-update-while-dragging-a-slider
        slider.valueProperty().addListener((arg0, arg1, arg2) -> {

            rate = slider.getValue();
            for(Planet p : planets.getPlanets()){p.speedRate = rate;}
            Double musicRate = 1.3 * Math.pow(rate, 2) - 0.4 * rate + 0.5;
            mp.setRate(musicRate);
            speed.textProperty().setValue(String.valueOf("1 min : " + Math.round( rate * 3650.0 )) + " days");

        });

        primaryStage.show();

        //https://blog.idrsolutions.com/2012/11/adding-a-window-resize-listener-to-javafx-scene/
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                double change = space.getWidth() + doubleValue(newSceneWidth) - doubleValue(oldSceneWidth);
                space.prefWidthProperty().setValue(change);
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                double spaceChange = space.getHeight() + doubleValue(newSceneHeight) - doubleValue(oldSceneHeight);
                space.prefHeightProperty().setValue(spaceChange);
            }
        });

    }

    //loading planets from XML file
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