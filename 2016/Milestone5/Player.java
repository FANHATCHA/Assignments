/**
 * @author William Fiset, Drew Chaboyer
 * Object Oriented Design - COMP 3721
 * Tick Attack milestone #5
 **/

import java.util.*;

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

  public String getInventoryStr() {
    if (getItemCount() == 0)
      return "You have no items!\n";
    else return inv.toString();
  }

  public int getItemCount() {
    return inv.size();
  }

  @Override public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append("Health hp = " + getHealth() + "\n" );
    sb.append("Money $ = " + getMoney() + "\n" );
    sb.append(getInventoryStr());
    return sb.toString();

  }

}
