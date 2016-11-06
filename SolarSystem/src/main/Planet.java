package main;

import javafx.scene.Node;
import javafx.scene.shape.Sphere;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType( propOrder = { "name", "radius", "orbit", "color" } )
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

    String color;
    public String getColor()

    {
        return color;
    }
    @XmlElement( name = "color" )
    public void setColor(String color){

        this.color = color;
    }

    Coordinates coordinates = new Coordinates(0,radius);

    Node sphere = createView();
    public Node createView() {

        Sphere sphere = new Sphere(radius);
        sphere.setTranslateY(0);
        sphere.setTranslateX(orbit);
        return sphere;
    }

    public void updateLocation() {
        double x = this.coordinates.getX();
        double y = this.coordinates.getY();

        this.coordinates.setX(100);
        this.coordinates.setY(-200);

        sphere.relocate(this.coordinates.getX(),this.coordinates.getY());

    }

}
