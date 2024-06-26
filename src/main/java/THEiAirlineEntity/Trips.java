/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package THEiAirlineEntity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 *
 * @author lamyu
 */
@Entity
@Table(name = "trips")
public class Trips implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Temporal(TemporalType.DATE)
    @Column(name = "departure_date", nullable = false)
    private Date departureDate;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @Column(name = "payment_overdue_flag", nullable = false)
    private boolean paymentOverdueFlag;

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<PaymentRecord> paymentRecords;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "passenger_id_fk") // Assuming there's a foreign key column in the Trips table
    private Passenger passenger;

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    // Constructors, getters, and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isPaymentOverdueFlag() {
        return paymentOverdueFlag;
    }

    public void setPaymentOverdueFlag(boolean paymentOverdueFlag) {
        this.paymentOverdueFlag = paymentOverdueFlag;
    }

    public List<PaymentRecord> getPaymentRecords() {
        return paymentRecords;
    }

    public void setPaymentRecords(List<PaymentRecord> paymentRecords) {
        this.paymentRecords = paymentRecords;
    }

    public int getPassengerIdFk() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
