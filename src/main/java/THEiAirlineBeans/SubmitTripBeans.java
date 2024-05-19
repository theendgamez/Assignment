/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package THEiAirlineBeans;

import THEiAirlineEntity.Passenger;
import THEiAirlineEntity.PaymentRecord;
import THEiAirlineEntity.Trip;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import javax.inject.Inject;

/**
 *
 * @author lamyu
 */
@Named(value = "submitTripBeans")
@SessionScoped
public class SubmitTripBeans implements Serializable {

    private String paymentType; // "Full" or "Installment"
    private double installmentAmount;

    @Inject
    private TripsBeans tripsBeans;

    @Inject
    private PassengerManager passengerManager;

    @Inject
    private PaymentManager paymentManager;

    public void submit() {
        Trip trip = tripsBeans.getTrip(); // Get the trip from TripsBeans

        Passenger passenger = passengerManager.getPassenger();
        trip.setPassenger(passenger);

        PaymentRecord paymentRecord = new PaymentRecord();
        paymentRecord.setTrip(trip);
        paymentRecord.setPaymentDate(new Date());

        if ("Installment".equals(paymentType)) {
            paymentRecord.setAmountPaid(getInstallmentAmount());
        } else {
            paymentRecord.setAmountPaid(trip.getTotalAmount());
        }

        paymentManager.addPaymentRecord(paymentRecord);
    }

    public double getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }
}
