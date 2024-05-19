/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package THEiAirlineBeans;

import javax.inject.Named;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import THEiAirlineEntity.Flight;
import DBconnecter.dBConnection;
import javax.enterprise.context.SessionScoped;



/**
 *
 * @author lamyu
 */
@Named(value = "loadFlightsBeans")
@SessionScoped
public class LoadFlightsBeans implements Serializable {

    private List<Flight> flights;
    private String selectedFlightNumber;
    private Double selectedFlightAmount;

    public LoadFlightsBeans() {
        loadFlights(); // Load flights on bean initialization
    }

    public void loadFlights() {
        try (Connection conn = dBConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Flights"); 
             ResultSet rs = stmt.executeQuery()) {

            flights = new ArrayList<>();
            while (rs.next()) {
                Flight flight = new Flight();
                flight.setFlightNumber(rs.getString("FlightNumber"));
                flight.setDepartureAirport(rs.getString("DepartureAirport"));
                flight.setArrivalAirport(rs.getString("ArrivalAirport"));
                flight.setAmount(rs.getDouble("Amount"));
                flights.add(flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, log it, or display an error message to the user
        }
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public String getSelectedFlightNumber() {
        return selectedFlightNumber;
    }

    public void setSelectedFlightNumber(String selectedFlightNumber) {
        this.selectedFlightNumber = selectedFlightNumber;
        updateSelectedFlightAmount();
    }

    public Double getSelectedFlightAmount() {
        return selectedFlightAmount;
    }

    public void setSelectedFlightAmount(Double selectedFlightAmount) {
        this.selectedFlightAmount = selectedFlightAmount;
    }

    private void updateSelectedFlightAmount() {
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(selectedFlightNumber)) {
                selectedFlightAmount = flight.getAmount();
                return;
            }
        }
        selectedFlightAmount = null; // Reset if no flight is selected
    }
}
