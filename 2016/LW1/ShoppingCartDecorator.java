/**
 * Micah Stairs and Finn Lidbetter
 * Shopping Penguin
 * COMP 3721
 * Dr. Ricker
 * November 21, 2016
 **/

import java.math.*;
import java.util.*;

public abstract class ShoppingCartDecorator implements ShoppingCart {

  private final ShoppingCart shoppingCart;

  public ShoppingCartDecorator(ShoppingCart shoppingCart) {
    this.shoppingCart = shoppingCart;
  }

  @Override public BigInteger getTotalCost() {
    return shoppingCart.getTotalCost();
  }

  @Override public List<Item> getContents() {
    return shoppingCart.getContents();
  }

}