package main;

import javafx.scene.Node;
import javafx.scene.shape.Sphere;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType( propOrder = { "name", "radius", "color" } )
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

    String color;
    public String getColor()

    {
        return color;
    }
    @XmlElement( name = "color" )
    public void setColor(String color){

        this.color = color;
    }

    public Node createView() {

        Sphere sphere = new Sphere(radius);
        return sphere;
    }

}
