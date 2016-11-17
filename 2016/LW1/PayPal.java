import java.math.*;

public class PayPal extends DebitPaymentMethod {

  private final String emailAddress;

  public PayPal(String emailAddress, BigInteger balance) {
    super(balance);
    this.emailAddress = emailAddress;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

}