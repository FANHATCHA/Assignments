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