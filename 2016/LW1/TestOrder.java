import java.math.*;
import java.time.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;

public class TestOrder {
  
  // Sample address
  private static final Address ADDRESS
    = new Address("62 York Street", "Sackville", "NB", "Canada", "E4L 1E2");

  // Sample items
  private static final Item ITEM_1 = Item.CLEO_LOLLIPOP;
  private static final Item ITEM_2 = Item.BOOGIE_WONDERLAND_SLIPPERS;
  private static final Item LOLLIPOP_ITEM = Item.NEMO_LOLLIPOP;
  private static final Item SLIPPERS_ITEM = Item.SOMEBODY_TO_LOVE_SLIPPERS;

  // Sample credit card
  private static final String CARD_NUMBER = "4111 1111 1111 1111";
  private static final String NAME = "John Smith";
  private static final int SECURITY_CODE = 901;
  private static final YearMonth EXPIRATION_DATE = YearMonth.of(18, 7);
  private static final PaymentMethod CREDIT_CARD
    = new CreditCard(CARD_NUMBER, ADDRESS, NAME, SECURITY_CODE, EXPIRATION_DATE);

  // Empty gift card
  private static final PaymentMethod EMPTY_GIFT_CARD
    = new GiftCard(CARD_NUMBER, BigInteger.ZERO);

  // Loaded bitcoin account
  private static final String WALLET_ADDRESS = "1BvBMSEYstWetqTFn5Au4m4GFg7xJaNVN2";
  private static final PaymentMethod BIT_COIN_ACCOUNT
    = new BitCoin(WALLET_ADDRESS, BigInteger.valueOf(Integer.MAX_VALUE));

  // PayPal account with balance equal to cost of ITEM_1
  private static final String EMAIL_ADDRESS = "john.smith@email.com";
  private static final PayPal PAYPAL_ACCOUNT = new PayPal(EMAIL_ADDRESS, ITEM_1.getCost());

  // Reference to the order
  private Order order;

  /** This resets the order before each test case. */
  @Before public void setup() {
    order = new Order();
  }

  /** Tests adding a couple items to the shopping cart and getting the total cost. */
  @Test public void testAddingItemsAndGettingCost() {

    // Check total cost before adding any items
    BigInteger expectedTotal = BigInteger.ZERO;
    assertEquals(expectedTotal, order.getTotalCost());

    // Add one item and check total cost
    order.addItemToCart(ITEM_1);
    expectedTotal = expectedTotal.add(ITEM_1.getCost());
    assertEquals(expectedTotal, order.getTotalCost());

    // Add another item and check total cost
    order.addItemToCart(ITEM_2);
    expectedTotal = expectedTotal.add(ITEM_2.getCost());
    assertEquals(expectedTotal, order.getTotalCost());

  }

  /** Tests adding the same item more than once. */
  @Test public void testAddingRepeatedItems() {

    BigInteger expectedTotal = BigInteger.ZERO;

    // Add an item to the cart
    order.addItemToCart(ITEM_1);
    expectedTotal = expectedTotal.add(ITEM_1.getCost());
    assertEquals(expectedTotal, order.getTotalCost());

    // Add the same item to the cart again
    order.addItemToCart(ITEM_1);
    expectedTotal = expectedTotal.add(ITEM_1.getCost());
    assertEquals(expectedTotal, order.getTotalCost());

  }

  /** Tests placing an order which did not specify a payment method. */
  @Test public void testOrderingWithoutPaymentMethod() {

    order.addItemToCart(ITEM_1);
    order.enterShippingAddress(ADDRESS);
    assertNull(order.placeOrder());

  }

  /** Tests placing an empty order which did not specify a payment method. */
  @Test public void testEmptyOrderWithoutPaymentMethod() {

    order.enterShippingAddress(ADDRESS);
    assertNotNull(order.placeOrder());

  }

  /** Tests placing an order which did not specify a shipping address. */
  @Test public void testOrderingWithoutShippingAddress() {

    order.enterPaymentMethod(CREDIT_CARD);
    assertNull(order.placeOrder());

  }

  /** Tests placing an order with a payment method that has a low balance. */
  @Test public void testPaymentMethodWithLowBalance() {

    order.addItemToCart(ITEM_1);
    order.enterShippingAddress(ADDRESS);
    order.enterPaymentMethod(EMPTY_GIFT_CARD);
    assertNull(order.placeOrder());

  }

  /** Tests placing an order with a payment method that has a balance equal to the total cost. */
  @Test public void testPaymentMethodBalanceEqualToCost() {

    assertEquals(ITEM_1.getCost(), PAYPAL_ACCOUNT.getBalance());

    order.addItemToCart(ITEM_1);
    order.enterShippingAddress(ADDRESS);
    order.enterPaymentMethod(PAYPAL_ACCOUNT);
    assertNotNull(order.placeOrder());

  }

  /** Tests placing an order with no items in the shopping cart. */
  @Test public void testOrderingNoItems() {

    order.enterShippingAddress(ADDRESS);
    order.enterPaymentMethod(BIT_COIN_ACCOUNT);
    List<Product> products = order.placeOrder();
    assertNotNull(products);
    assertEquals(0, products.size());

  }

  /** Tests emptying the shopping cart. */
  @Test public void testEmptyingShoppingCart() {

    // Add item to shopping cart
    order.addItemToCart(ITEM_1);
    assertNotEquals(BigInteger.ZERO, order.getTotalCost());

    // Empty shopping cart
    order.emptyShoppingCart();
    assertEquals(BigInteger.ZERO, order.getTotalCost());

    // Place order and ensure 0 products are recieved
    order.enterShippingAddress(ADDRESS);
    List<Product> products = order.placeOrder();
    assertNotNull(products);
    assertEquals(0, products.size());

  }

  /** Tests placing an order with a few items in the shopping cart. */
  @Test public void testOrderingItems() {

    // Add items to cart
    order.addItemToCart(LOLLIPOP_ITEM);
    order.addItemToCart(SLIPPERS_ITEM);

    // Place order
    order.enterShippingAddress(ADDRESS);
    order.enterPaymentMethod(BIT_COIN_ACCOUNT);
    List<Product> products = order.placeOrder();
    assertNotNull(products);
    assertEquals(2, products.size());

    // These will be equal iff we get back one lollipop and one pair of slippers
    Product product1 = products.get(0);
    Product product2 = products.get(1);
    assertEquals(product1 instanceof Lollipop, product2 instanceof Slipper);

  }

  /** Tests placing an order with repeated items in the shopping cart. */
  @Test public void testOrderingRepeatedItems() {

    // Add items to cart
    order.addItemToCart(LOLLIPOP_ITEM);
    order.addItemToCart(LOLLIPOP_ITEM);

    // Place order
    order.enterShippingAddress(ADDRESS);
    order.enterPaymentMethod(BIT_COIN_ACCOUNT);
    List<Product> products = order.placeOrder();
    assertNotNull(products);
    assertEquals(2, products.size());

    // Ensure that both products are lollipops
    Product product1 = products.get(0);
    Product product2 = products.get(1);
    assertTrue(product1 instanceof Lollipop);
    assertTrue(product2 instanceof Lollipop);

    // Asser that the name and description match
    assertEquals(LOLLIPOP_ITEM.getName(), product1.getName());
    assertEquals(LOLLIPOP_ITEM.getName(), product2.getName());
    assertEquals(LOLLIPOP_ITEM.getDescription(), product1.getDescription());
    assertEquals(LOLLIPOP_ITEM.getDescription(), product2.getDescription());

  }

}