import java.util.ArrayList;
import java.util.List;

public class Passengers {
    List<Passenger> passengers;

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public Passengers(Integer qty){
        this.passengers = new ArrayList<Passenger>(qty);
    }

    public String mpDestination() {
        String destination = "test";
        return destination;
    }

    public Double totalEarned() {
        Double total = 0.0;
        for (int i = 0; i < passengers.size() ; i++) {
            total += passengers.get(i).getPrice();
        }
        return total;
    }

    public String allPassengers(int page){
        String all = "";
        for (int i = page * 20; i < passengers.size(); i++) {
            all = all + "\n" + passengers.get(i).getName();
        }
        return all;
    }
}
