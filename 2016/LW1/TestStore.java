import java.math.*;
import static org.junit.Assert.*;
import org.junit.*;

public class TestStore {
  
  private Store store;

  /** This is called before each test case. */
  @Before public void setup() {
    store = new Store();
  }

  /** Tests adding a couple items to the shopping cart and getting the total cost. */
  @Test public void testAddingItemsAndGettingCost() {

    // Check total cost before adding any items
    BigInteger expectedTotal = BigInteger.ZERO;
    assertEquals(store.getTotalCost(), expectedTotal);

    // Add one item and check total cost
    store.addItemToCart(Item.NEMO_LOLLIPOP);
    expectedTotal = expectedTotal.add(Item.NEMO_LOLLIPOP.getCost());
    assertEquals(store.getTotalCost(), expectedTotal);

    // Add another item and check total cost
    store.addItemToCart(Item.BOOGIE_WONDERLAND_SLIPPERS);
    expectedTotal = expectedTotal.add(Item.BOOGIE_WONDERLAND_SLIPPERS.getCost());
    assertEquals(store.getTotalCost(), expectedTotal);

  }

  /** Tests adding the same item more than once. */
  @Test public void testAddingRepeatedItems() {

    BigInteger expectedTotal = BigInteger.ZERO;

    // Add an item to the cart
    store.addItemToCart(Item.NEMO_LOLLIPOP);
    expectedTotal = expectedTotal.add(Item.NEMO_LOLLIPOP.getCost());
    assertEquals(store.getTotalCost(), expectedTotal);

    // Add the same item to the cart again
    store.addItemToCart(Item.NEMO_LOLLIPOP);
    expectedTotal = expectedTotal.add(Item.NEMO_LOLLIPOP.getCost());
    assertEquals(store.getTotalCost(), expectedTotal);

  }

  // test ordering no items

  // test ordering with no shipping address  

  // test ordering with bad payment method (low balance)


}