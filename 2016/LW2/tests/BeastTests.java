/*
 * @author William Fiset, Finn Lidbetter
 * Lightning week 2 project
 * Object Oriented Design - COMP 3721
 */

import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;

public class BeastTests {

  // Test creating Beasts with custom names
  @Test public void testLegalBeastCreationCustom() {

    new Abeille("Thomas");
    new Libellule("Finn");

  }

  // Test creating Beasts using factory
  @Test public void testLegalBeastCreationFactory() {

    BeastFactory.createAbeille("Thomas");
    BeastFactory.createLibellule("Will");

  }

  // Try creating illegally named Abeille
  @Test(expected=Exception.class)
  public void testIllegalAbeilleCreation() {
    new Abeille(null);
  }

  // Try creating illegally named Libellule
  @Test(expected=Exception.class)
  public void testIllegalLibelluleCreation() {
    new Libellule(null);
  }

  // Tests to make sure that ids of beast are unique
  @Test public void testIdUniqueness() {

    Set <Integer> nums = new HashSet<>();

    Abeille a1 = BeastFactory.createAbeille("Thomas");
    Abeille a2 = BeastFactory.createAbeille("Thomas1");
    Abeille a3 = BeastFactory.createAbeille("Thomas2");
    Abeille a4 = BeastFactory.createAbeille("Thomas3");

    nums.add(a1.getId());
    nums.add(a2.getId());
    nums.add(a3.getId());
    nums.add(a4.getId());

    assertEquals( nums.size(), 4 );

  }

  // Test getName method in beast class
  @Test public void testGetName() {

    Abeille a1 = BeastFactory.createAbeille ("Thomas");
    Abeille a2 = BeastFactory.createAbeille ("Thomas1");
    Abeille a3 = BeastFactory.createAbeille ("Thomas2");
    Abeille a4 = BeastFactory.createAbeille ("Thomas3");

    assertEquals(a1.getName(), "Thomas");
    assertEquals(a2.getName(), "Thomas1");
    assertEquals(a3.getName(), "Thomas2");
    assertEquals(a4.getName(), "Thomas3");
    
  } 

  // Test Beast class .equals method
  @Test public void testEquality() {

    Libellule a1 = BeastFactory.createLibellule("Thomas");
    Libellule a2 = BeastFactory.createLibellule("Thomas1");
    Libellule a3 = BeastFactory.createLibellule("Thomas2");
    Libellule a4 = BeastFactory.createLibellule("Thomas3");

    assertTrue( a1.equals(a1) );
    assertTrue( a2.equals(a2) );
    assertTrue( a3.equals(a3) );
    assertTrue( a4.equals(a4) );
    
    assertFalse( a1.equals(a3) );
    assertFalse( a2.equals(a3) );
    assertFalse( a3.equals(a1) );
    assertFalse( a4.equals(a2) );

  } 

}

