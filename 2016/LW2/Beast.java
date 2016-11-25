/*
 * @author William Fiset, Finn Lidbetter
 * Lightning week 2 project
 * Object Oriented Design - COMP 3721
 */

import java.util.*;

public abstract class Beast implements Observer {

  static private int beastId = 1;

  private int id;
  private String name;

  private Set <Tree> treesInBlooms;

  public Beast (String name) {
    
    if (name == null)
      throw new IllegalArgumentException();

    this.name = name;

    treesInBlooms = new HashSet<>();

    id = beastId;
    beastId++;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override public void update(Tree tree) {
    
    if (tree.isInBloom()) {
      treesInBlooms.add(tree);
    } else {
      treesInBlooms.remove(tree);
    }

  }

  @Override public int hashCode() {
    return id;
  }

  @Override public boolean equals(Object obj) {
    if (obj == null) return false;
    Beast other = (Beast) obj;
    return id == other.getId();
  }

}
