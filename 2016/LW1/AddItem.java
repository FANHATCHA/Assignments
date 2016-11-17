import java.math.*;
import java.util.*;

public class AddItem extends ShoppingCartDecorator {

  private Item item;

  public AddItem(ShoppingCart shoppingCart, Item item) {
    super(shoppingCart);
    this.item = item;
  }

  @Override public BigInteger getTotalCost() {
    return super.getTotalCost().add(item.getCost());
  }

  @Override public List<Item> getContents() {
    List<Item> list = super.getContents();
    list.add(item);
    return list;
  }

}