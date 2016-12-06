/*
 * @author William Fiset, Jonathan Whitaker
 * Tick Attack milestone #3
 * Object Oriented Design - COMP 3721
 */

import static org.junit.Assert.*;
import org.junit.*;

public class InventoryTests {

  ItemFactory factory;
  Item item1, item2, item3, item4, item5;
  Item item6, item7, item8, item9, item10, item11;

  @Before public void reset() {

    factory = new ItemFactory();
    
    item1 = factory.createWolfsbane();
    item2 = factory.createUnicornHair();
    item3 = factory.createStudentsTears();
    item4 = factory.createPotionOne();
    item5 = factory.createEelEye();
    item6 = factory.createNightShade();
    item7 = factory.createPotionTwo();
    item8 = factory.createDragonScale();
    item9 = factory.createHipsterCoffee();
    item10 = factory.createMountiePride();
    item11 = factory.createPotion3();

  }

  // Create an inventory
  @Test public void testInventoryCreation() {
    new Inventory();
  }

  // test adding items to the inventory and check if they're actually in there
  @Test public void testAddingItems() {

    Inventory inv = new Inventory();
    inv.add(item1, item2, item3);

    assertFalse(inv.contains(null));
    assertTrue(inv.contains(item1));
    assertTrue(inv.contains(item2));
    assertTrue(inv.contains(item3));
    assertFalse(inv.contains(item4));

  }

  // Test whether or not we can actaully get items 
  // we placed inside out inventory
  @Test public void testGettingItems() {

    Inventory inv = new Inventory();
    inv.add(item1, item2, item3);

    Item i1 = inv.getItem(item1.getID());
    Item i2 = inv.getItem(item2.getID());
    Item i3 = inv.getItem(item3.getID());

    assertNotNull(i1);
    assertNotNull(i2);
    assertNotNull(i3);

  }

  @Test public void testAddingMultipleItems() {

    Inventory inv = new Inventory();
    // Item item1 = 

  }

}

