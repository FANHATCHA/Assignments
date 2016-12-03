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
    StringBuilder sb = new StringBuilder();
    for (Item item : items)
      sb.append(item.getName() + " ");
    return sb.toString();
  }

}

