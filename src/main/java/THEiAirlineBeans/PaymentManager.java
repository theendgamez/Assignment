/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package THEiAirlineBeans;

import THEiAirlineEntity.PaymentRecord;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Named(value = "paymentManager")
@SessionScoped
public class PaymentManager implements Serializable {

    private String paymentType;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("my_persistence_unit");

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void addPaymentRecord(PaymentRecord paymentRecord) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(paymentRecord);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Method to update an existing payment record
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

    // Method to delete a payment record
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

    // Method to retrieve a payment record by ID
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

    // Method to retrieve all payment records
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
