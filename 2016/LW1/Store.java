import java.math.*;

public class Store {

  private Inventory inventory;
  private ShoppingCart shoppingCart;
  private Address shippingAddress;

  public Store() {
    inventory = new Inventory();
    shoppingCart = new EmptyShoppingCart();
    shippingAddress = null;
  }

  /** Adds item to the shopping cart. */
  public void addItemToCart(Item item) {
    shoppingCart = new AddItem(shoppingCart, item);
  }

  /** Removes all items from the shopping cart. */
  public void emptyShoppingCart() {
    shoppingCart = new EmptyShoppingCart();
  }

  /** Sets the shipping address. */
  public void enterShippingAddress(Address shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  /** Gets the total cost of the shopping cart. */
  public BigInteger getTotalCost() {
    return shoppingCart.getTotalCost();
  }

}