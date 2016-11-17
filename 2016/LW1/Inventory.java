import java.util.*;

public class Inventory {

  private final Map<Item, Integer> itemToStockCount;

  public Inventory() {
    itemToStockCount = new HashMap<Item, Integer>();
  }

  /** Check to see if the specified item is contained in the inventory. */
  public boolean contains(Item item) {
    return itemToStockCount.containsKey(item);
  }

  /** Adds the specified item to the inventory. */
  public void add(Item item) {
    Integer prevCount = itemToStockCount.get(item);
    if (prevCount == null) {
      prevCount = 0;
    }
    itemToStockCount.put(item, prevCount + 1);
  }

  /** Removes item from inventory, returning false if it was not present. */
  public boolean remove(Item item) {

    Integer count = itemToStockCount.get(item);

    // Item was not in inventory
    if (count == null) {
      return false;
    }

    // Remove item from inventory
    if (count == 1) {
      itemToStockCount.remove(item);
    } else {
      itemToStockCount.put(item, count - 1);
    }
    return true;

  }

}