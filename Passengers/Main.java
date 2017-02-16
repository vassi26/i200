import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    Passengers passengers = new Passengers(100);
    public TextField passengerNameInput = new TextField();
    Label priceLabel = new Label();
    ChoiceBox<String> passengerDestinationChoise = new ChoiceBox<>();
    Integer currentPage = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane pane = new Pane();
        Scene scene = new Scene(pane, 700, 500);
        passengerDestinationChoise.getItems().addAll("Narva", "Keila", "Tartu");
        passengerDestinationChoise.setValue("Narva");
        getChoice(passengerDestinationChoise);

        //Lables
        Label passengerName = new Label("Passenger Name");
        passengerName.setTranslateX(10);
        passengerName.setTranslateY(15);
        Label passengerDestination = new Label("Destination");
        passengerDestination.setTranslateX(180);
        passengerDestination.setTranslateY(15);
        Label priceTag = new Label("Price: ");
        priceTag.setTranslateX(260);
        priceTag.setTranslateY(15);

        Label mostPopularDestinationLabel = new Label();
        mostPopularDestinationLabel.setVisible(false);
        Label totalPrice = new Label();
        totalPrice.setVisible(false);
        Label passengersList = new Label();
        passengersList.setVisible(false);

        //Fields
        passengerNameInput.setTranslateX(10);
        passengerNameInput.setTranslateY(40);
        this.passengerDestinationChoise.setTranslateX(180);
        this.passengerDestinationChoise.setTranslateY(40);
        priceLabel.setTranslateX(260);
        priceLabel.setTranslateY(45);

        //Buttons
        Button sold = new Button("Sold");
        sold.setTranslateX(320);
        sold.setTranslateY(40);
        sold.setDefaultButton(true);
        sold.setOnAction(event -> {
            //Empty fields validation
            if(passengerNameInput.getText().isEmpty()){
                Alert.display("Input Validation", "Please fill in passengers' name", false);
            } else if(passengers.getPassengers().size() == 100){
                boolean restart = Alert.display("Maximum reached", "Only 100 passengers are accepted", true);
                if(restart){
                    passengers.getPassengers().clear();
                    clearInputs();
                    Alert.display("Successful clearing", "Previous information is cleared, You can restart", false);
                }
            }
            else {

                String nameInput = passengerNameInput.getText();
                String destinationInput = this.passengerDestinationChoise.getValue();
                Double price = Double.parseDouble(priceLabel.getText());

                clearInputs();

                Passenger passenger = new Passenger(nameInput, destinationInput, price);
                passengers.getPassengers().add(passenger);
                // Debug
                // System.out.println(passengers);
            }
        });

        Button nextPart = new Button(">>");
        nextPart.setTranslateX(240);
        nextPart.setTranslateY(95);
        nextPart.setVisible(false);

        Button previousPart = new Button("<<");
        previousPart.setTranslateX(200);
        previousPart.setTranslateY(95);
        previousPart.setVisible(false);

        nextPart.setOnAction(event -> {
            currentPage += 1;
            if(currentPage > 0){previousPart.setDisable(false);}
            else if(passengers.getPassengers().size() / 20 == currentPage){nextPart.setDisable(true);}
        });
        previousPart.setOnAction(event -> {
            currentPage -= 1;
            if(currentPage == 0){previousPart.setDisable(true);}
        });

        Button generate = new Button("Generate Stats");
        generate.setTranslateX(380);
        generate.setTranslateY(40);
        generate.setOnAction(event -> {
            //Fields
            mostPopularDestinationLabel.setText("Most popular destination: " + passengers.mpDestination());
            mostPopularDestinationLabel.setTranslateX(10);
            mostPopularDestinationLabel.setTranslateY(70);
            mostPopularDestinationLabel.setVisible(true);
            totalPrice.setText("Total money earned: " + passengers.totalEarned());
            totalPrice.setTranslateX(180);
            totalPrice.setTranslateY(70);
            totalPrice.setVisible(true);
            mostPopularDestinationLabel.setText("Most popular destination: " + passengers.mpDestination());
            mostPopularDestinationLabel.setTranslateX(10);
            mostPopularDestinationLabel.setTranslateY(70);
            mostPopularDestinationLabel.setVisible(true);
            passengersList.setText("All travelers: " + passengers.allPassengers(currentPage));
            passengersList.setTranslateX(10);
            passengersList.setTranslateY(95);
            passengersList.setVisible(true);
            nextPart.setVisible(true);
            previousPart.setVisible(true);
            previousPart.setDisable(true);

        });


        pane.getChildren().addAll(passengerName, passengerDestinationChoise,
                priceTag, priceLabel, passengerNameInput, passengerDestination,
                mostPopularDestinationLabel, totalPrice, passengersList, sold,
                generate, nextPart, previousPart);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void clearInputs(){
        passengerNameInput.clear();
        passengerNameInput.requestFocus();
    }

    private void getChoice(ChoiceBox<String> passengerDestination){
        if(passengerDestination.getValue() == "Narva"){
            priceLabel.setText("15.50");
        }else if(passengerDestination.getValue() == "Keila"){
            priceLabel.setText("5.00");
        }else if(passengerDestination.getValue() == "Tartu"){
            priceLabel.setText("20.50");
        }

    }
}
