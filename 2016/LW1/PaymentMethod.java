/**
 * Micah Stairs and Finn Lidbetter
 * Shopping Penguin
 * COMP 3721
 * Dr. Ricker
 * November 21, 2016
 **/

import java.math.*;

public interface PaymentMethod {

  public abstract boolean charge(BigInteger cost);

}