import java.math.*;
import java.time.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;

public class TestStore {
  
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

  // Reference to the store
  private Store store;

  /** This resets the store before each test case. */
  @Before public void setup() {
    store = new Store();
  }

  /** Tests adding a couple items to the shopping cart and getting the total cost. */
  @Test public void testAddingItemsAndGettingCost() {

    // Check total cost before adding any items
    BigInteger expectedTotal = BigInteger.ZERO;
    assertEquals(expectedTotal, store.getTotalCost());

    // Add one item and check total cost
    store.addItemToCart(ITEM_1);
    expectedTotal = expectedTotal.add(ITEM_1.getCost());
    assertEquals(expectedTotal, store.getTotalCost());

    // Add another item and check total cost
    store.addItemToCart(ITEM_2);
    expectedTotal = expectedTotal.add(ITEM_2.getCost());
    assertEquals(expectedTotal, store.getTotalCost());

  }

  /** Tests adding the same item more than once. */
  @Test public void testAddingRepeatedItems() {

    BigInteger expectedTotal = BigInteger.ZERO;

    // Add an item to the cart
    store.addItemToCart(ITEM_1);
    expectedTotal = expectedTotal.add(ITEM_1.getCost());
    assertEquals(expectedTotal, store.getTotalCost());

    // Add the same item to the cart again
    store.addItemToCart(ITEM_1);
    expectedTotal = expectedTotal.add(ITEM_1.getCost());
    assertEquals(expectedTotal, store.getTotalCost());

  }

  /** Tests placing an order which did not specify a payment method. */
  @Test public void testOrderingWithoutPaymentMethod() {

    store.addItemToCart(ITEM_1);
    store.enterShippingAddress(ADDRESS);
    assertNull(store.placeOrder());

  }

  /** Tests placing an empty order which did not specify a payment method. */
  @Test public void testEmptyOrderWithoutPaymentMethod() {

    store.enterShippingAddress(ADDRESS);
    assertNotNull(store.placeOrder());

  }

  /** Tests placing an order which did not specify a shipping address. */
  @Test public void testOrderingWithoutShippingAddress() {

    store.enterPaymentMethod(CREDIT_CARD);
    assertNull(store.placeOrder());

  }

  /** Tests placing an order with a payment method that has a low balance. */
  @Test public void testPaymentMethodWithLowBalance() {

    store.addItemToCart(ITEM_1);
    store.enterShippingAddress(ADDRESS);
    store.enterPaymentMethod(EMPTY_GIFT_CARD);
    assertNull(store.placeOrder());

  }

  /** Tests placing an order with a payment method that has a balance equal to the total cost. */
  @Test public void testPaymentMethodBalanceEqualToCost() {

    assertEquals(ITEM_1.getCost(), PAYPAL_ACCOUNT.getBalance());

    store.addItemToCart(ITEM_1);
    store.enterShippingAddress(ADDRESS);
    store.enterPaymentMethod(PAYPAL_ACCOUNT);
    assertNotNull(store.placeOrder());

  }

  /** Tests placing an order with no items in the shopping cart. */
  @Test public void testOrderingNoItems() {

    store.enterShippingAddress(ADDRESS);
    store.enterPaymentMethod(BIT_COIN_ACCOUNT);
    List<Product> products = store.placeOrder();
    assertNotNull(products);
    assertEquals(0, products.size());

  }

  /** Tests emptying the shopping cart. */
  @Test public void testEmptyingShoppingCart() {

    // Add item to shopping cart
    store.addItemToCart(ITEM_1);
    assertNotEquals(BigInteger.ZERO, store.getTotalCost());

    // Empty shopping cart
    store.emptyShoppingCart();
    assertEquals(BigInteger.ZERO, store.getTotalCost());

    // Place order and ensure 0 products are recieved
    store.enterShippingAddress(ADDRESS);
    List<Product> products = store.placeOrder();
    assertNotNull(products);
    assertEquals(0, products.size());

  }

  /** Tests placing an order with a few items in the shopping cart. */
  @Test public void testOrderingItems() {

    // Add items to cart
    store.addItemToCart(LOLLIPOP_ITEM);
    store.addItemToCart(SLIPPERS_ITEM);

    // Place order
    store.enterShippingAddress(ADDRESS);
    store.enterPaymentMethod(BIT_COIN_ACCOUNT);
    List<Product> products = store.placeOrder();
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
    store.addItemToCart(LOLLIPOP_ITEM);
    store.addItemToCart(LOLLIPOP_ITEM);

    // Place order
    store.enterShippingAddress(ADDRESS);
    store.enterPaymentMethod(BIT_COIN_ACCOUNT);
    List<Product> products = store.placeOrder();
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