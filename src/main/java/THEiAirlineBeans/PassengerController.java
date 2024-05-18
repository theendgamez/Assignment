package THEiAirlineBeans;

import THEiAirlineEntity.Passenger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author lamyu
 */
@Named
@RequestScoped
public class PassengerController implements Serializable {

    private List<Passenger> passengers = new ArrayList<>();
    private Passenger passenger = new Passenger();
    private boolean passengerAdded; // Boolean property to indicate if passenger is added

    // Method to create a new passenger
    public void createPassenger() {
        passengers.add(passenger);
        passengerAdded = true; // Set passengerAdded to true after successful addition
        passenger = new Passenger(); // Reset the passenger for the next entry
    }



    public List<Passenger> getAllPassengers() {
        return passengers;
    }

    // Getter and Setter for the current passenger
    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    // Getter and Setter for passengerAdded
    public boolean isPassengerAdded() {
        return passengerAdded;
    }

    public void setPassengerAdded(boolean passengerAdded) {
        this.passengerAdded = passengerAdded;
    }
}
