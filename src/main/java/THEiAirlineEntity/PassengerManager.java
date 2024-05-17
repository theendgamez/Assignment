/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package THEiAirlineEntity;

import javax.persistence.*;
import java.util.List;

/**
 *
 * @author lamyu
 */
public class PassengerManager {

    private EntityManagerFactory emf;

    public PassengerManager() {
        emf = Persistence.createEntityManagerFactory("my_persistence_unit");
    }

    public void createPassenger(Passenger passenger) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(passenger);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void updatePassenger(Passenger passenger) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(passenger);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void deletePassenger(Long id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Passenger passenger = em.find(Passenger.class, id);
            if (passenger != null) {
                em.remove(passenger);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Passenger findPassenger(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Passenger.class, id);
        } finally {
            em.close();
        }
    }

    public List<Passenger> getAllPassengers() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Passenger p", Passenger.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void close() {
        emf.close();
    }
}
