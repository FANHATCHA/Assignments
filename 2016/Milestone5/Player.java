/*
 * @author William Fiset, Jonathan Whitaker
 * Tick Attack milestone #3
 * Object Oriented Design - COMP 3721
 */

import java.util.*;
import java.util.Random;

class Item implements Comparable <Item> {

  private int itemId;
  private int itemValue;
  private String itemName;
  private String description;
  private static Random rand = new Random();

  public Item(int id, String itemName, String description) {
    if (itemName == null || description == null)
      throw new IllegalArgumentException();
    this.itemId = id;
    this.itemValue = rand.nextInt(250*(id+1));
    this.itemName = itemName;
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public int getID() {
    return itemId;
  }

  public int getItemValue() {
    return itemValue;
  }

  public String getName() {
    return itemName;
  }

  @Override public int compareTo(Item other) {
    return itemName.compareTo(other.getName());
  }

  @Override public String toString() {
    return getName();
  }

}

class Inventory implements Iterable <Item> {

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

public class Player {

  private static final long MAX_HEALTH  = 1500L;
  private static final long START_MONEY = 2500L;

  private long health;
  private long money;
  private boolean hasTicks = true;
  private boolean didTickCheck = false;;
  private Inventory inv = new Inventory();

  public Player() {
    this.health = MAX_HEALTH;
    this.money  = START_MONEY;
  }

  public boolean isAlive() {
    return health > 0;
  }

  // You are allowed to add a negative amount of health
  public void obtainHealth(long amount) {
    
    health = (health + amount);
    
    // Negative health doesn't make sense..
    if (health < 0) health = 0;

  }

  public long getHealth() {
    return health;
  }

  public long getMoney() {
    return money;
  }

  public boolean hasTicks() {
    return hasTicks;
  }

  public void setHasTicks(boolean hasTicks) {
    this.hasTicks = hasTicks;
  }

  public boolean didTickCheck() {
    return didTickCheck;
  }

  public void setDidTickCheck(boolean val) {
    didTickCheck = val;
  }

  // Return a copy of the list of item ids the player has
  public List <Integer> getItems() {

    List<Integer> ids = new ArrayList<>();
    Iterator <Item> iter = getItemsIterator();
    while(iter.hasNext()) {
      Item item = iter.next();
      ids.add(item.getID());
    }
    return ids;
  }

  public Iterator <Item> getItemsIterator () {
    return inv.iterator();
  }

  public void obtainItem(Item item) {
    inv.add(item);
  }

  // remove an item from the player's inventory
  public Item removeItem(int itemId) {
    if (inv.contains(itemId))
      return inv.remove(itemId);
    return null;
  }

  // Sell a specific item
  public Item sellItem(int itemId) {
    Item item = inv.getItem(itemId);
    int itemValue = item.getItemValue();
    obtainMoney(itemValue);
    removeItem(itemId);
    return item;
  }

  // Sell all the items the player has
  public int sellAllItems() {

    int total = 0;
    for (Integer itemID : getItems()) {
      Item item = sellItem(itemID);
      total += item.getItemValue();
    }
    return total;

  }

  // Return true/false on whether the user has an item
  public boolean hasItem(int itemId) {
    return inv.contains(itemId);
  }

  // You are allowed to add a negative amount of money
  public void obtainMoney(long amount) {
    money += amount;
  }

  @Override public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append("Health hp = " + getHealth() + "\n" );
    sb.append("Money $ = " + getMoney() + "\n" );
    return sb.toString();

  }

}
