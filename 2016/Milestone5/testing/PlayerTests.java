/**
 * @author William Fiset, Drew Chaboyer
 * Object Oriented Design - COMP 3721
 * Tick Attack milestone #5
 **/

import static org.junit.Assert.*;
import org.junit.*;

public class PlayerTests {

  @Test public void testPlayerCreation() {
    new Player();
  }

  // Tests whether the player can die
  @Test public void testPlayerAlive() {
    
    Player p = new Player();
    assertTrue(p.isAlive());
    
    p.obtainHealth(Integer.MIN_VALUE);
    assertFalse(p.isAlive());

  }

  // Test if the player can correctly obtain money
  @Test public void testPlayerMoney() {
    
    Player p = new Player();
    long cash = p.getMoney();

    assertTrue(cash >= 0);
    p.obtainMoney(-cash);

    assertEquals(p.getMoney(), 0);
    
  }

  @Test public void testGettingPotions() {

    Player p = new Player();
    PotionOne p1 = new PotionOne();

    assertEquals(p.getItemCount(),0);
    p.obtainTempItem(p1);
    assertEquals(p.getItemCount(),0);
    p.transferPotions();
    assertEquals(p.getItemCount(),1);


  }

  @Test public void testItemsAquiring() {
    
    Player p = new Player();
    Item torch = new Item(0, "Torch", "Shines in the dark");
    p.obtainItem(torch);

    assertTrue(p.hasItem(0));
    assertFalse(p.hasItem(-1));
    assertFalse(p.hasItem(1));
    
  }

  // Test that the player inventory is working correctly
  @Test public void testCountingItems() {
    
    ItemFactory f = new ItemFactory();
    Item item1 = f.createWolfsbane();
    Item item2 = f.createUnicornHair();
    Item item3 = f.createStudentsTears();

    Player p = new Player();
    p.obtainItem(item1);
    p.obtainItem(item2);
    p.obtainItem(item3);

    assertEquals( p.getItemCount(), 3 );

  }

  // Make sure the aplyer is able to check whether or not they
  // have an item on them
  @Test public void testHasItem() {

    ItemFactory f = new ItemFactory();

    Item item1 = f.createWolfsbane();
    Item item2 = f.createUnicornHair();
    Item item3 = f.createStudentsTears();
    Item item4 = f.createDragonScale();
    Item item5 = null;

    Player p = new Player();
    p.obtainItem(item1);
    p.obtainItem(item2);
    p.obtainItem(item3);

    assertTrue(p.hasItem(item1));
    assertTrue(p.hasItem(item2));
    assertTrue(p.hasItem(item3));

    assertFalse(p.hasItem(item4));
    assertFalse(p.hasItem(item5));

  }

  // Test setting ticks
  @Test public void testPlayerTicks() {

    Player p = new Player();

    p.setHasTicks(false);
    assertFalse(p.hasTicks());
    p.setHasTicks(true);
    assertTrue(p.hasTicks());

    p.setDidTickCheck(false);
    assertFalse(p.didTickCheck());
    p.setDidTickCheck(true);
    assertTrue(p.didTickCheck());

  }

}

