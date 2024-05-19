package THEiAirlineBeans;

import THEiAirlineEntity.Passenger;
import THEiAirlineEntity.Trip;
import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
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
    private LoadFlightsBeans loadFlightsBean;

    @Inject
    private PassengerManager passengerManager;

    @Inject
    private PaymentManager paymentManager;

    public void submit() {
        try {
            Trip trip = tripsBeans.getTrip();
            Passenger passenger = passengerManager.getPassenger();
            trip.setPassenger(passenger);
            trip.setTotalAmount(loadFlightsBean.getSelectedFlightAmount());

            String paymentType = paymentManager.getPaymentType();
            double amountPaid = ("Installment".equals(paymentType)) ? installmentAmount : loadFlightsBean.getSelectedFlightAmount();

            paymentManager.createPayment(amountPaid, paymentType, trip, passenger);

            tripsBeans.setTrip(new Trip());
            passengerManager.setPassenger(new Passenger());
            setInstallmentAmount(0);
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
        String paymentType = paymentManager.getPaymentType();
        if ("Installment".equals(paymentType)) {
            double flightAmount = loadFlightsBean.getSelectedFlightAmount();
            if (installmentAmount <= 0 || installmentAmount > flightAmount) {
                FacesMessage msg = new FacesMessage("Installment amount must be greater than zero and less than or equal to the flight amount.");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null; // Stop processing if validation fails
            }
        }
        return "confirmationPage?faces-redirect=true";
    }
}
