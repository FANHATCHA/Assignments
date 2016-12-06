/**
 * @author William Fiset, Drew Chaboyer
 * Object Oriented Design - COMP 3721
 * Tick Attack milestone #5
 **/

import java.util.*;

public class Inventory implements Iterable <Item> {

  // private Map <Integer, Item> inventoryMap = new TreeMap<>();
  private List <Item> items = new LinkedList<>();

  public boolean contains(int itemID) {
    for (Item item : items)
      if (item.getID() == itemID)
        return true;
    return false;
  }

  public boolean contains(Item item) {
    if (item == null) return false;
    return contains(item.getID());
  }

  public Item getItem(int itemID) {
    for (Item item : items)
      if (item.getID() == itemID)
        return item;
    return null;
  }

  // Add any given number of items to the inventory
  public void add(Item ... newItems) {
    for (Item item : newItems) {
      if (item != null) {
        items.add(item);
        // inventoryMap.put(item.getID(), item);
      }
    }
  }

  // Delete a specific item from the inventory
  public Item remove(int itemID) {
    int removeIndex = -1;
    for (int i = 0; i < items.size(); i++) {
      Item item = items.get(i);
      if (item.getID() == itemID)
        return items.remove(i);
    }
    return null;
  }

  public int size() {
    return items.size();
  }

  public String getItemNameFromID(int itemID) {
    for (Item item : items)
      if (item.getID() == itemID)
        return item.getName();
    return "";
  }  


  // An inventory can act as an iterable when you use a for-each loop
  @Override public Iterator <Item> iterator() {
    return new Iterator <Item> () {
      
      int index = 0;

      @Override public boolean hasNext() {
        return index < items.size();
      }

      @Override public Item next() {
        return items.get(index++);
      }

    };
  }

  @Override public String toString() {

    // Count item occurences
    Map <Item, Integer> counts = new TreeMap<>();
    for (Item item : items) {
      if (counts.containsKey(item)) {
        counts.put(item, counts.get(item) + 1);
      } else {
        counts.put(item, 1);
      }
    }

    // Create printable inventory sting
    StringBuilder sb = new StringBuilder();
    for (Item item : counts.keySet()) {
      int quantity = counts.get(item);
      sb.append( quantity + " x " + item.getName() + " - " + item.getItemValue() + "$" + "\n" );
    }

    return sb.toString();
  }

}

