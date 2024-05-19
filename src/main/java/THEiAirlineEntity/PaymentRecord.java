package THEiAirlineEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "paymentrecord")
public class PaymentRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "payment_date", nullable = false)
    private Date paymentDate;

    @Column(name = "amount_paid", nullable = false)
    private double amountPaid;

    @Column(name = "paymentType", nullable = false)
    private String paymentType;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "trip_id_fk", nullable = false)
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "passenger_id_fk", nullable = false)
    private Passenger passenger;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
}
