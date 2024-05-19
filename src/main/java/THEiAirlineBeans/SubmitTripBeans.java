package THEiAirlineBeans;

import THEiAirlineEntity.Passenger;
import THEiAirlineEntity.PaymentRecord;
import THEiAirlineEntity.Trip;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import javax.inject.Inject;

@Named(value = "submitTripBeans")
@SessionScoped
public class SubmitTripBeans implements Serializable {
    private double installmentAmount;

    @Inject
    private TripsBeans tripsBeans;

    @Inject
    private PassengerManager passengerManager;

    @Inject
    private PaymentManager paymentManager;

    public void submit() {
        try {
            Trip trip = tripsBeans.getTrip();
            Passenger passenger = passengerManager.getPassenger();
            trip.setPassenger(passenger);

            PaymentRecord paymentRecord = new PaymentRecord();
            paymentRecord.setTrip(trip);
            paymentRecord.setPaymentDate(new Date());

            String paymentType = paymentManager.getPaymentType();
            if (paymentType != null && !paymentType.isEmpty()) {
                paymentRecord.setPaymentType(paymentType);
            } else {
                throw new IllegalArgumentException("Payment type cannot be null or empty.");
            }

            if ("Installment".equals(paymentType)) {
                if (installmentAmount > 0) {
                    paymentRecord.setAmountPaid(installmentAmount);
                } else {
                    throw new IllegalArgumentException("The installment amount must be greater than zero.");
                }
            } else {
                paymentRecord.setAmountPaid(trip.getTotalAmount());
            }

            paymentManager.addPaymentRecord(paymentRecord);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public String confirmation() {
        return "confirmationPage?faces-redirect=true";
    }
}
