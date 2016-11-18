import java.math.*;
import java.time.*;
import static org.junit.Assert.*;
import org.junit.*;

public class TestStore {
  
  // Sample address
  private static final Address ADDRESS
    = new Address("62 York Street", "Sackville", "NB", "Canada", "E4L 1E2");

  // Sample items
  private static final Item ITEM_1 = Item.CLEO_LOLLIPOP;
  private static final Item ITEM_2 = Item.BOOGIE_WONDERLAND_SLIPPERS;

  // Sample credit card
  private static final String CARD_NUMBER = "4111 1111 1111 1111";
  private static final String NAME = "John Smith";
  private static final int SECURITY_CODE = 901;
  private static final YearMonth EXPIRATION_DATE = YearMonth.of(18, 7);
  private static final PaymentMethod PAYMENT_METHOD
    = new CreditCard(CARD_NUMBER, ADDRESS, NAME, SECURITY_CODE, EXPIRATION_DATE);

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
    assertEquals(store.getTotalCost(), expectedTotal);

    // Add one item and check total cost
    store.addItemToCart(ITEM_1);
    expectedTotal = expectedTotal.add(ITEM_1.getCost());
    assertEquals(store.getTotalCost(), expectedTotal);

    // Add another item and check total cost
    store.addItemToCart(ITEM_2);
    expectedTotal = expectedTotal.add(ITEM_2.getCost());
    assertEquals(store.getTotalCost(), expectedTotal);

  }

  /** Tests adding the same item more than once. */
  @Test public void testAddingRepeatedItems() {

    BigInteger expectedTotal = BigInteger.ZERO;

    // Add an item to the cart
    store.addItemToCart(ITEM_1);
    expectedTotal = expectedTotal.add(ITEM_1.getCost());
    assertEquals(store.getTotalCost(), expectedTotal);

    // Add the same item to the cart again
    store.addItemToCart(ITEM_1);
    expectedTotal = expectedTotal.add(ITEM_1.getCost());
    assertEquals(store.getTotalCost(), expectedTotal);

  }

  /** Tests placing an order which did not specify a payment method. */
  @Test public void testOrderingWithoutPaymentMethod() {

    store.addItemToCart(ITEM_1);
    store.enterShippingAddress(ADDRESS);
    assertNull(store.placeOrder());

  }

  /** Tests placing an order which did not specify a shipping address. */
  @Test public void testOrderingWithoutShippingAddress() {

    store.addItemToCart(ITEM_1);
    store.enterPaymentMethod(PAYMENT_METHOD);
    assertNull(store.placeOrder());

  }

  // test ordering with no payment method

  // test ordering with no shipping address  

  // test ordering no items

  // test ordering items
  // test ordering repeated items



  // test ordering with bad payment method (low balance)


}