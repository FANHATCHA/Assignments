import java.math.*;
import java.util.*;

public interface ShoppingCart {

  public BigInteger getTotalCost();
  public List<Item> getContents();

}