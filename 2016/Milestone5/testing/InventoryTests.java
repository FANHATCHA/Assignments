/**
 * @author William Fiset, Drew Chaboyer
 * Object Oriented Design - COMP 3721
 * Tick Attack milestone #5
 **/

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

  // Test adding multiple items to the inventory
  // Also test removing multiple items from the inventory
  @Test public void testAddingMultipleItems() {

    Inventory inv = new Inventory();
    
    inv.add(item1,item1,item1);
    assertEquals(inv.size(), 3);
    inv.remove(item1.getID());
    inv.remove(item1.getID());
    inv.remove(item1.getID());

    assertEquals(inv.size(), 0);

    inv.add(item1, item2, item3, item4, item5);
    assertEquals(inv.size(), 5);
    inv.remove(item1.getID());
    inv.remove(item2.getID());
    inv.remove(item3.getID());
    inv.remove(item4.getID());
    inv.remove(item5.getID());

    assertEquals(inv.size(), 0);

  }

  // Test getting names from ID numbers
  @Test public void testGetNameFromID() {

    Inventory inv = new Inventory();

    inv.add(item1, item2, item3, item4, item5, item6);
    inv.add(item1, item2, item3, item4, item5, item6);
    inv.add(item1, item2, item3, item4, item5, item6);
    inv.add(item1, item2, item3, item4, item5, item6);

    String name = inv.getItemNameFromID( item1.getID() );
    assertEquals( name, item1.getName() );

    name = inv.getItemNameFromID( item2.getID() );
    assertEquals( name, item2.getName() );

    name = inv.getItemNameFromID( item3.getID() );
    assertEquals( name, item3.getName() );
    
    name = inv.getItemNameFromID( item4.getID() );
    assertEquals( name, item4.getName() );
    
    name = inv.getItemNameFromID( item5.getID() );
    assertEquals( name, item5.getName() );
    
    name = inv.getItemNameFromID( item6.getID() );
    assertEquals( name, item6.getName() );

  }

}

