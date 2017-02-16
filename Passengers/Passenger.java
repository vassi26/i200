
public class Passenger {
    String name;
    String destination;
    Double price;

    public String getName() {
        return name;
    }

    public Double getPrice(){return price;}

    public String getDestination() {
        return destination;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    // Constructor
    public Passenger(String name, String destination, Double price) {

        this.name = name;
        this.destination = destination;
        this.price = price;
    }
}
