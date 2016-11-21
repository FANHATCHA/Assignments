/**
 * Micah Stairs and Finn Lidbetter
 * Shopping Penguin
 * COMP 3721
 * Dr. Ricker
 * November 21, 2016
 **/

import java.math.*;

public class GiftCard extends DebitPaymentMethod {

  private final String cardNumber;

  public GiftCard(String cardNumber, BigInteger balance) {
    super(balance);
    this.cardNumber = cardNumber;
  }

  public String getCardNumber() {
    return cardNumber;
  }

}