/*
 * @author William Fiset, Finn Lidbetter
 * Lightning week 2 project
 * Object Oriented Design - COMP 3721
 */

import static org.junit.Assert.*;
import org.junit.*;

import java.util.*;

public class TreeTests {

  // Test creating Trees ith custom parameters
  @Test public void testLegalTreeCustomCreation() {
    
    new FranklinTree(3, 3, false);
    new CrabappleTree(4, 55, true);
    new BlueMistShrub(77, 7, false);

  }

  // Test creating trees with a factory
  @Test public void testLegalTreeFactoryCreation() {

    TreeFactory.createFranklinTree();
    TreeFactory.createCrabappleTree();
    TreeFactory.createBlueMistShrub();

  }

  // Test illegal age tree creation
  @Test(expected=Exception.class)
  public void testIllegalTreeCreation1() {
    new FranklinTree(-3, 3, false); 
  }

  // Test illegal pollen count tree creation
  @Test(expected=Exception.class)
  public void testIllegalTreeCreation2() {
    new FranklinTree(3, -3, false);
  }

  // Test both illegal pollen count and age
  @Test(expected=Exception.class)
  public void testIllegalTreeCreation3() {
    new FranklinTree(-1, -1, true);
  }

  // Tests to make sure tree ids are unique
  @Test public void testIdUniqeness() {

    Set <Integer> nums = new HashSet<>();

    FranklinTree tree1 = TreeFactory.createFranklinTree();
    FranklinTree tree2 = TreeFactory.createFranklinTree();
    FranklinTree tree3 = TreeFactory.createFranklinTree();
    FranklinTree tree4 = TreeFactory.createFranklinTree();

    nums.add(tree1.getId());
    nums.add(tree2.getId());
    nums.add(tree3.getId());
    nums.add(tree4.getId());

    assertEquals( nums.size(), 4 );

  }

  // Test that the setInBloom method is working
  @Test public void testSetInBloom() {
    
    FranklinTree tree = TreeFactory.createFranklinTree();
    tree.setInBloom(false);
    assertFalse(tree.isInBloom());
    tree.setInBloom(true);
    assertTrue(tree.isInBloom());

  }

}
