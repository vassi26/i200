package main;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    public Sphere sun;
    public Sphere earth;
    public Sphere moon;
    @FXML
    StackPane stackPaneOne;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/solarSystem.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Solar System");

        final PhongMaterial redMaterial = new PhongMaterial();
        final PhongMaterial blueMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.YELLOW);
        blueMaterial.setDiffuseColor(Color.ALICEBLUE);

        sun = new Sphere(30);
        sun.setMaterial(redMaterial);
        earth = new Sphere(10);
        earth.setMaterial(blueMaterial);
        moon = new Sphere(2);

        Ellipse orbitEarth = new Ellipse();
        //TODO: change orbit radius to be more realistic
        orbitEarth.setRadiusX(sun.getBoundsInLocal().getWidth() + 90 );
        orbitEarth.setRadiusY(sun.getBoundsInLocal().getHeight() + 90 );

        PathTransition transitionEarth = new PathTransition();
        transitionEarth.setPath(orbitEarth);
        transitionEarth.setNode(earth);
        transitionEarth.setInterpolator(Interpolator.LINEAR);
        transitionEarth.setDuration(Duration.seconds(10.000017421));
        transitionEarth.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        transitionEarth.setCycleCount(Timeline.INDEFINITE);
        transitionEarth.play();

        Ellipse orbitMoon = new Ellipse();
        //TODO: change orbit radius to be more realistic
        orbitMoon.setRadiusX(earth.getBoundsInLocal().getWidth() + 20);
        orbitMoon.setRadiusY(earth.getBoundsInLocal().getHeight() + 20);

        PathTransition transitionMoon = new PathTransition();
        transitionMoon.setPath(orbitMoon);
        transitionMoon.setNode(moon);
        transitionMoon.setInterpolator(Interpolator.LINEAR);
        transitionMoon.setDuration(Duration.seconds(1.000017421));
        transitionMoon.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        transitionMoon.setCycleCount(Timeline.INDEFINITE);
        transitionMoon.play();

        orbitMoon.setVisible(false);
        orbitEarth.setVisible(false);

        StackPane moonPane = new StackPane();
        moonPane.translateXProperty().bind(earth.translateXProperty());
        moonPane.translateYProperty().bind(earth.translateYProperty());
        moonPane.setMaxSize(100, 100);


//        actionButton.setOnAction(MouseEvent.MOUSE_ENTERED, e -> actionButton.setEffect(shadow));
//        actionButton.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> actionButton.setEffect(shadow));
//        actionButton.addEventHandler(MouseEvent.MOUSE_EXITED, e -> actionButton.setEffect(null));

        stackPaneOne.setStyle("-fx-background-color: BLACK;");
        stackPaneOne.getChildren().add(sun);
        moonPane.getChildren().add(moon);
        stackPaneOne.getChildren().add(moonPane);
        stackPaneOne.getChildren().add(orbitEarth);
        stackPaneOne.getChildren().add(earth);
        stackPaneOne.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        primaryStage.show();
		
    }

    public static void main(String[] args) {
        launch(args);
    }
}
