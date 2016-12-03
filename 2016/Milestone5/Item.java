
import java.util.*;
import java.util.Random;

public class Item implements Comparable <Item> {

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