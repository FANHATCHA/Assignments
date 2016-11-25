/*
 * @author William Fiset, Finn Lidbetter
 * Lightning week 2 project
 * Object Oriented Design - COMP 3721
 */

public class TreeFactory {

  public static FranklinTree createFranklinTree() {
    return new FranklinTree(10, 45, false);
  }

  public static CrabappleTree createCrabappleTree() {
    return new CrabappleTree(30, 60, false);
  }

  public static BlueMistShrub createBlueMistShrub() {
    return new BlueMistShrub(50, 50, false);
  }

}



