package THEiAirlineBeans;

import THEiAirlineEntity.Passenger;
import THEiAirlineEntity.PassengerManager;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class PassengerController implements Serializable {

    @Inject
    private PassengerManager passengerManager;
    private Passenger passenger = new Passenger();
    private boolean passengerAdded; // Boolean property to indicate if passenger is added
    
    public boolean isPassengerAdded() {
        return passengerAdded;
    }

    public void setPassengerAdded(boolean passengerAdded) {
        this.passengerAdded = passengerAdded;
    }
       public void createPassenger() {
        passengerManager.createPassenger(passenger);
        passengerAdded = true; // Set passengerAdded to true after successful addition
        passenger = new Passenger(); // Reset the passenger for the next entry
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public void updatePassenger(Passenger passenger) {
        passengerManager.updatePassenger(passenger);
    }

    public void deletePassenger(Long id) {
        passengerManager.deletePassenger(id);
    }

    public Passenger findPassenger(Long id) {
        return passengerManager.findPassenger(id);
    }

    public List<Passenger> getAllPassengers() {
        return passengerManager.getAllPassengers();
    }
}
