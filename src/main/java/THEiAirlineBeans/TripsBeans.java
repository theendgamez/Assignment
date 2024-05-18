package THEiAirlineBeans;

import DBconnecter.dBConnection;
import THEiAirlineEntity.Trip;
import THEiAirlineEntity.Passenger;
import THEiAirlineEntity.PaymentRecord;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.Date;
import javax.inject.Inject;

@Named(value = "tripsBeans")
@SessionScoped
public class TripsBeans implements Serializable {

    private Trip trip;
    private List<Passenger> passengers;
    private boolean passengerAdded;

    @Inject
    private PassengerController passengerController;

    @Inject
    private PaymentManager paymentManager;

    @PostConstruct
    public void init() {
        trip = new Trip();
        passengers = new ArrayList<>();
    }

    public void saveTrip() {
        try (Connection conn = dBConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Trips (OrderDate, DepartureDate, TotalAmount) VALUES (?, ?, ?)")) {

            // Set OrderDate to current date and time
            Timestamp orderTimestamp = new Timestamp(new Date().getTime());

            stmt.setTimestamp(1, orderTimestamp);
            stmt.setDate(2, new java.sql.Date(trip.getDepartureDate().getTime()));
            stmt.setDouble(3, trip.getTotalAmount());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void submit() {
        saveTrip();
        passengerController.createPassenger();
        PaymentRecord paymentRecord = new PaymentRecord(); // Create a new PaymentRecord instance
        paymentManager.addPaymentRecord(paymentRecord);
    }

    // Getters and Setters for trip, flights, selectedFlightNumber, and passengers
    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public boolean isPassengerAdded() {
        return passengerAdded;
    }

    public void setPassengerAdded(boolean passengerAdded) {
        this.passengerAdded = passengerAdded;
    }

}
