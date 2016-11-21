/**
 * Micah Stairs and Finn Lidbetter
 * Shopping Penguin
 * COMP 3721
 * Dr. Ricker
 * November 21, 2016
 **/

import java.math.*;
import java.util.*;

public interface ShoppingCart {

  public BigInteger getTotalCost();
  public List<Item> getContents();

}