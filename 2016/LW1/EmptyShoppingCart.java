/**
 * Micah Stairs and Finn Lidbetter
 * Shopping Penguin
 * COMP 3721
 * Dr. Ricker
 * November 21, 2016
 **/

import java.math.*;
import java.util.*;

public class EmptyShoppingCart implements ShoppingCart {

  @Override public BigInteger getTotalCost() {
    return BigInteger.ZERO;
  }

  @Override public List<Item> getContents() {
    return new ArrayList<Item>();
  }

}