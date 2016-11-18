public class Shop {

  private Inventory inventory;
  private ShoppingCart shoppingCart;
  private Address shippingAddress;

  public Shop() {
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

  public void enterShippingAddress(Address shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

}