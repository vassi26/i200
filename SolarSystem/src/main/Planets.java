package main;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement( name = "planets" )
public class Planets {
    List<Planet> planets;

    public List<Planet> getPlanets()
    {
        return planets;
    }

    @XmlElement( name = "planet" )
    public void setPlanets( List<Planet> planets ) {
        this.planets = planets;
    }

    public void add( Planet planet )
    {
        if( this.planets == null )
        {
            this.planets = new ArrayList<Planet>();
        }
        this.planets.add( planet );

    }

    public Planet getPlanetByName(String name) {
        for (int i = 0; i < this.planets.size(); i++){
            Planet planet = this.planets.get(i);
            if(planet.name == name) {
                return planet;}
        }
        return null;
    }
}
