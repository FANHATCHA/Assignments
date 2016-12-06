/**
 * @author William Fiset, Drew Chaboyer
 * Object Oriented Design - COMP 3721
 * Tick Attack milestone #5
 **/

import java.util.*;
import java.util.Random;

public class Item implements Comparable <Item> {

  private int itemId;
  private int itemValue;
  private String itemName;
  private String description;
  
  private static Random rand = new Random();
  private static Map <String, Integer> itemPriceMap = new HashMap<>();

  public Item(int price, int id, String itemName, String description) {
    
    // Invalid arguments
    if (itemName == null || description == null || price < 0)
      throw new IllegalArgumentException();
    
    // Cache price
    if (itemPriceMap.containsKey(itemName))
      price = itemPriceMap.get(itemName);
    else itemPriceMap.put(itemName, price);

    this.description = description;
    this.itemName = itemName;
    this.itemValue = price;
    this.itemId = id;
    
  }

  public Item(int id, String itemName, String description) {
    this( rand.nextInt(500), id, itemName, description );
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

  // Hash Items by their id
  @Override public int hashCode() {
    return itemId;
  }

  // Two Items are equal if their names are equal
  @Override public int compareTo(Item other) {
    return itemName.compareTo(other.getName());
  }

  @Override public String toString() {
    return getName();
  }

}