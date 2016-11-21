package main;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import static java.lang.Math.*;

@XmlType( propOrder = { "name", "radius", "orbit", "omega", "color" } )
@XmlRootElement( name = "planet" )
public class Planet {

    Node view = createView();

    String name;
    public String getName()
    {
        return name;
    }
    @XmlElement( name = "name" )
    public void setName(String name){

        this.name = name;
    }

    double radius;
    public double getRadius()

    {
        return radius;
    }
    @XmlElement( name = "radius" )
    public void setRadius(double radius){

        this.radius = radius;
    }

    double orbit;
    public double getOrbit()

    {
        return orbit;
    }
    @XmlElement( name = "orbit" )
    public void setOrbit(double orbit){

        this.orbit = orbit;
    }

    double omega;
    public double getOmega()

    {
        return omega;
    }
    @XmlElement( name = "omega" )
    public void setOmega(double omega){

        this.omega = omega;
    }

    String color;
    public String getColor()

    {
        return color;
    }
    @XmlElement( name = "color" )
    public void setColor(String color){

        this.color = color;
    }

    double alpha = 0;
    int direction = 1;

    Coordinates coordinates = new Coordinates(0,0,0);

    Node sphere = createView();
    public Node createView() {

        Sphere sphere = new Sphere(radius);
        PhongMaterial m = new PhongMaterial();
        m.setDiffuseColor(Color.ALICEBLUE);
        sphere.setMaterial(m);
        return sphere;
    }

    public void updateLocation() {

        alpha += omega * direction;
        coordinates.setX(orbit * sin(toRadians(alpha)));
        coordinates.setY(orbit * cos(toRadians(alpha)));

    }

}
