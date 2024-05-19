package THEiAirlineBeans;

import DBconnecter.dBConnection;
import THEiAirlineEntity.Trip;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.annotation.PostConstruct;

@Named(value = "tripsBeans")
@SessionScoped
public class TripsBeans implements Serializable {

    private Trip trip;
    @PostConstruct
    public void init() {
        trip = new Trip();
    }

    public void saveTrip() {
        try (Connection conn = dBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement("INSERT INTO trips (departure_date, total_amount, payment_overdue_flag, passenger_id_fk) VALUES (?, ?, ?, ?)")) {

            // Removed the order_date setting as it's now handled by the database
            stmt.setDate(1, new java.sql.Date(trip.getDepartureDate().getTime()));
            stmt.setDouble(2, trip.getTotalAmount());
            stmt.setBoolean(3, trip.isPaymentOverdueFlag());
            stmt.setInt(4, trip.getPassengerIdFk());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String navigateToConfirmation() {
        return "confirmationPage.xhtml?faces-redirect=true"; // Navigate to confirmation page
    }

    // Getters and Setters for trip, flights, selectedFlightNumber, passengers, paymentType, and installmentAmount
    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

}
