package THEiAirlineBeans;

import THEiAirlineEntity.Passenger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "passengerManager")
@SessionScoped
public class PassengerManager implements Serializable {

    private EntityManagerFactory emf;
    private Passenger passenger; // Add a Passenger property

    public PassengerManager() {
        emf = Persistence.createEntityManagerFactory("my_persistence_unit");
        passenger = new Passenger(); // Initialize the Passenger property
    }

    // Getter and setter for the Passenger property
    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Passenger createPassenger(String firstName, String lastName, String title, String phoneNumber, String email, String idNumber) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        passenger.setFirstName(firstName);
        passenger.setLastName(lastName);
        passenger.setTitle(title);
        passenger.setPhoneNumber(phoneNumber);
        passenger.setEmail(email);
        passenger.setIdNumber(idNumber);

        em.persist(passenger);
        em.getTransaction().commit();
        em.close();

        return passenger;
    }

    // Additional methods for read, update, and delete operations can be added here
}
