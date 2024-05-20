package THEiAirlineBeans;

import THEiAirlineEntity.PaymentRecord;
import THEiAirlineEntity.Trips;
import THEiAirlineEntity.Passenger;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Named(value = "paymentManager")
@SessionScoped
public class PaymentManager implements Serializable {

    private EntityManagerFactory emf;

    private String paymentType;

    public PaymentManager() {
        emf = Persistence.createEntityManagerFactory("my_persistence_unit");
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public PaymentRecord createPayment(double amountPaid, String paymentType, Trips trip, Passenger passenger) {
        EntityManager em = emf.createEntityManager();
        PaymentRecord paymentRecord = new PaymentRecord();
        try {
            em.getTransaction().begin();

            paymentRecord.setPaymentDate(new Date()); // Automatically set the current date
            paymentRecord.setAmountPaid(amountPaid);
            paymentRecord.setPaymentType(paymentType);
            paymentRecord.setTrip(trip);
            paymentRecord.setPassenger(passenger);

            em.persist(paymentRecord);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return paymentRecord;
    }

    public void updatePaymentRecord(PaymentRecord paymentRecord) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(paymentRecord);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void deletePaymentRecord(Long paymentRecordId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            PaymentRecord paymentRecord = em.find(PaymentRecord.class, paymentRecordId);
            if (paymentRecord != null) {
                em.remove(paymentRecord);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public PaymentRecord getPaymentRecordById(Long paymentRecordId) {
        EntityManager em = emf.createEntityManager();
        PaymentRecord paymentRecord = null;
        try {
            paymentRecord = em.find(PaymentRecord.class, paymentRecordId);
        } finally {
            em.close();
        }
        return paymentRecord;
    }

    public List<PaymentRecord> getAllPaymentRecords() {
        EntityManager em = emf.createEntityManager();
        List<PaymentRecord> paymentRecords = null;
        try {
            paymentRecords = em.createQuery("SELECT pr FROM PaymentRecord pr", PaymentRecord.class).getResultList();
        } finally {
            em.close();
        }
        return paymentRecords;
    }
}
