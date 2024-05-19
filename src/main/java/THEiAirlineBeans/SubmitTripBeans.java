package THEiAirlineBeans;

import THEiAirlineEntity.Passenger;
import THEiAirlineEntity.Trip;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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

        String paymentType = paymentManager.getPaymentType();
        if (paymentType == null || paymentType.isEmpty()) {
            throw new IllegalArgumentException("Payment type cannot be null or empty.");
        }

        double amountPaid = "Installment".equals(paymentType) ? installmentAmount : trip.getTotalAmount();
        if ("Installment".equals(paymentType) && installmentAmount <= 0) {
            throw new IllegalArgumentException("The installment amount must be greater than zero.");
        }

        paymentManager.createPayment(amountPaid, paymentType, trip, passenger);

        // Redirect to the welcome page on success
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.redirect("welcome.xhtml");
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
