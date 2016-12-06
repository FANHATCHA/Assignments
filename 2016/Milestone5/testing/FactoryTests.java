
/**
 * @author William Fiset, Drew Chaboyer
 * Object Oriented Design - COMP 3721
 * Tick Attack milestone #5
 **/

import static org.junit.Assert.*;
import org.junit.*;

public class FactoryTests {

  ItemFactory factory;
  Item item1, item2, item3, item4, item5;
  Item item6, item7, item8, item9, item10, item11;

  @Before public void reset() {
    factory = new ItemFactory();
  }

  // Test creating all the items in out factory!
  @Test public void testItemCreation() {

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
    item11 = factory.createPotionThree();

  }

  // Test getting names from ID numbers
  @Test public void testCreationWithID() {

    for (int id : ItemFactory.FACTORY_ITEMS ) {
      Item item = factory.createItemById(id);
      assertNotNull(item);
    }

  }

}

