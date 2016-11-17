import java.math.*;
import java.time.*;

public class CreditCard implements PaymentMethod {

  private final Address billingAddress;
  private final String name;
  private final int securityCode;
  private final YearMonth expirationDate;

  public CreditCard(Address billingAddress, String name, int securityCode, YearMonth expirationDate) {
    this.billingAddress = billingAddress;
    this.name = name;
    this.securityCode = securityCode;
    this.expirationDate = expirationDate;
  }

  @Override public boolean charge(BigInteger cost) {
    return true;
  }

  public Address getBillingAddress() {
    return billingAddress;
  }

  public String getName() {
    return name;
  }

  public int getSecurityCode() {
    return securityCode;
  }

  public YearMonth getExpirationDate() {
    return expirationDate;
  }

}