/*
 * @author William Fiset, Finn Lidbetter
 * Lightning week 2 project
 * Object Oriented Design - COMP 3721
 */

import java.util.*;

public abstract class Tree implements Subject {

  private static int treeId = 1;

  private int id;
  private String name;
  private int age, pollenQuantity;
  private boolean inBloom;
  private Set <Observer> pollenHarvestors;

  public Tree(String name, int age, int pollenQuantity, boolean inBloom) {
    
    if (name == null || age < 0 || pollenQuantity < 0)
      throw new IllegalArgumentException();

    this.age = age;
    this.name = name;
    this.pollenQuantity = pollenQuantity;
    this.inBloom = inBloom;

    pollenHarvestors = new HashSet<>();

    id = treeId;
    treeId++;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setInBloom(boolean isInBloom) {
    this.inBloom = isInBloom;
  }

  public boolean isInBloom() {
    return inBloom;
  }

  public int getObserverCount() {
    return pollenHarvestors.size();
  }

  @Override public void broadcast() {
    for (Observer observer : pollenHarvestors) {
      observer.update(this);
    }
  }
  
  @Override public void attach(Observer observer) {
    pollenHarvestors.add(observer);
  }

  @Override public void detach(Observer observer) {
    pollenHarvestors.remove(observer);
  }

  @Override public int hashCode() {
    return id;
  }

  @Override public boolean equals(Object obj) {
    if (obj == null) return false;
    Tree other = (Tree) obj;
    return id == other.getId();
  }

  @Override public String toString() {
    return getName();
  }

}
