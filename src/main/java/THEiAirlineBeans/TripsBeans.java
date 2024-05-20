package THEiAirlineBeans;

import THEiAirlineEntity.Passenger;
import THEiAirlineEntity.Trips;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Named(value = "tripsBeans")
@SessionScoped
public class TripsBeans implements Serializable {

    
    private EntityManagerFactory emf;
    private Trips trip;

    public Trips getTrip() {
        return trip;
    }

    public void setTrip(Trips trip) {
        this.trip = trip;
    }

    public TripsBeans() {
        emf = Persistence.createEntityManagerFactory("my_persistence_unit");
        trip = new Trips(); // Initialize the Passenger property
    }

    public Trips createTrip(Date departureDate, double totalAmount, boolean paymentOverdueFlag, Passenger passenger) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        trip.setDepartureDate(departureDate);
        trip.setTotalAmount(totalAmount);
        trip.setPaymentOverdueFlag(paymentOverdueFlag);
        trip.setPassenger(passenger);
        em.persist(trip);
        em.getTransaction().commit();
        em.close();
        return trip;
    }

    /**
     * Select all trips from the database.
     *
     * @return A list of all trips.
     */
    public List<Trips> getAllTrips() {
        EntityManager em = emf.createEntityManager();
        List<Trips> trips = null;
        try {
            trips = em.createQuery("SELECT t FROM Trips t", Trips.class).getResultList();
        } finally {
            em.close();
        }
        return trips;
    }

    
    public void updateTrips(Trips trips) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge( trips);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}
