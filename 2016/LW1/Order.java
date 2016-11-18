import java.math.*;
import java.util.*;

public class Order {

  private ShoppingCart shoppingCart;
  private Address shippingAddress;
  private PaymentMethod paymentMethod;

  public Order() {
    shoppingCart = new EmptyShoppingCart();
    shippingAddress = null;
    paymentMethod = null;
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

  /** Sets the shipping address. */
  public void enterPaymentMethod(PaymentMethod paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  /** Gets the total cost of the shopping cart. */
  public BigInteger getTotalCost() {
    return shoppingCart.getTotalCost();
  }

  /** Attempts to order the items in the shopping cart, create the products, and deliver them. */
  public List<Product> placeOrder() {

    // Return null if no shipping address was specified
    if (shippingAddress == null) {
      return null;
    }

    // Compute total cost
    BigInteger totalCost = getTotalCost();

    // Return null if the total cost is nonzero and no payment method was specified
    if (!totalCost.equals(BigInteger.ZERO) && paymentMethod == null) {
      return null;
    }

    // Return null if the payment did not go through 
    // NOTE: No charge is made if the total cost is zero
    if (!totalCost.equals(BigInteger.ZERO) && !paymentMethod.charge(totalCost)) {
      return null;
    }

    // Get list of ordered items
    List<Item> orderedItems = shoppingCart.getContents();

    // Produce items
    List<Product> products = new ArrayList<>();
    for (Item item : orderedItems) {
      Product product = AbstractFactory.create(item.getFactoryType(), item.getVariationType());
      products.add(product);
    }

    // Return products
    return products;

  }

}