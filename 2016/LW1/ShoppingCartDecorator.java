import java.math.*;
import java.util.*;

public abstract class ShoppingCartDecorator implements ShoppingCart {

  protected final ShoppingCart shoppingCart;

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