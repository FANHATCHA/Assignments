/*
 * @author William Fiset, Finn Lidbetter
 * Lightning week 2 project
 * Object Oriented Design - COMP 3721
 */

public class BeastFactory {

  public static Abeille createAbeille(String name) {
    return new Abeille(name);
  }

  public static Libellule createLibellule(String name) {
    return new Libellule(name);
  }

}
