
/*
 * @author William Fiset, Finn Lidbetter
 * Lightning week 2 project
 * Object Oriented Design - COMP 3721
 */

import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;

// Created a new local Beast class for custom testing purposes
class CustomBeast extends Beast {
  public CustomBeast(String name) {
    super(name);
  }
  @Override public void update(Tree tree) {
    System.out.println(tree);
  }
}

public class ObserverAndSubjectTests {

  // Tests that attaching observers and subjects works
  @Test public void testAttachObversersAndSubjects() {

    Tree tree1 = TreeFactory.createFranklinTree();
    Tree tree2 = TreeFactory.createCrabappleTree();
    Tree tree3 = TreeFactory.createBlueMistShrub();

    Beast beast1 = BeastFactory.createAbeille("Thomas");
    Beast beast2 = BeastFactory.createLibellule("Will");

    // Create a complete bipartite graph between trees and beasts:
    tree1.attach(beast1);
    tree1.attach(beast2);

    tree2.attach(beast1);
    tree2.attach(beast2);

    tree3.attach(beast1);
    tree3.attach(beast2);

    assertEquals( tree1.getObserverCount(), 2 );
    assertEquals( tree2.getObserverCount(), 2 );
    assertEquals( tree3.getObserverCount(), 2 );

  }

  // Tests that detaching observers and subjects works
  @Test public void testObversersAndSubjectDetach() {

    Tree tree1 = TreeFactory.createFranklinTree();
    Tree tree2 = TreeFactory.createCrabappleTree();
    Tree tree3 = TreeFactory.createBlueMistShrub();

    Beast beast1 = new CustomBeast("Happy-Beast");
    Beast beast2 = new CustomBeast("Angry-Beast");

    // Create a complete bipartite graph between trees and beasts:
    tree1.attach(beast1);
    tree1.attach(beast2);

    tree2.attach(beast1);
    tree2.attach(beast2);

    tree3.attach(beast1);
    tree3.attach(beast2);

    // Remove all communication
    tree1.detach(beast1);
    tree1.detach(beast2);

    tree2.detach(beast1);
    tree2.detach(beast2);

    tree3.detach(beast1);
    tree3.detach(beast2);

    assertEquals( tree1.getObserverCount(), 0 );
    assertEquals( tree2.getObserverCount(), 0 );
    assertEquals( tree3.getObserverCount(), 0 );    

    // This will notify all the beasts that they
    // have a notification from the tree.
    // For this test we are expecting no output ay all
    tree1.broadcast();
    tree2.broadcast();
    tree3.broadcast();

  }

  @Test public void testObversersAndSubjectCommunication() {

    Tree tree1 = TreeFactory.createFranklinTree();
    Tree tree2 = TreeFactory.createCrabappleTree();
    Tree tree3 = TreeFactory.createBlueMistShrub();

    Beast beast1 = new CustomBeast("Happy-Beast");
    Beast beast2 = new CustomBeast("Angry-Beast");

    // Create a complete bipartite graph between trees and beasts:
    tree1.attach(beast1);
    tree1.attach(beast2);

    tree2.attach(beast1);
    tree2.attach(beast2);

    tree3.attach(beast1);
    tree3.attach(beast2);

    // This will notify all the beasts that they
    // have a notification from the tree. We should
    // see something printed to the console for each beast
    tree1.broadcast();
    tree2.broadcast();
    tree3.broadcast();

  }

}
