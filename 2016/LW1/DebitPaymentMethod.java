/**
 * Micah Stairs and Finn Lidbetter
 * Shopping Penguin
 * COMP 3721
 * Dr. Ricker
 * November 21, 2016
 **/

import java.math.*;

public abstract class DebitPaymentMethod implements PaymentMethod {

  private BigInteger balance;

  public DebitPaymentMethod(BigInteger balance) {
    this.balance = balance;
  }

  /** Returns true if the transaction went through. */
  @Override public boolean charge(BigInteger cost) {
    if (balance.compareTo(cost) >= 0) {
      balance = balance.subtract(cost);
      return true;
    } else {
      return false;
    }
  }

  public BigInteger getBalance() {
    return balance;
  }

}