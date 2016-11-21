/**
 * Micah Stairs and Finn Lidbetter
 * Shopping Penguin
 * COMP 3721
 * Dr. Ricker
 * November 21, 2016
 **/

public class SlipperFactory {

  public static Slipper create(String type) {
    switch (type) {
      case "BOOGIE_WONDERLAND": return createBoogieWonderlandSlippers();
      case "SOMEBODY_TO_LOVE": return createSomebodyToLoveSlippers();
      default: return null;
    }
  }

  private static Slipper createBoogieWonderlandSlippers() {
    String name = Item.BOOGIE_WONDERLAND_SLIPPERS.getName();
    String description = Item.BOOGIE_WONDERLAND_SLIPPERS.getDescription();
    String soundtrack = Item.BOOGIE_WONDERLAND_SLIPPERS.getVariationType();
    return new Slipper(name, description, soundtrack);
  }

  private static Slipper createSomebodyToLoveSlippers() {
    String name = Item.SOMEBODY_TO_LOVE_SLIPPERS.getName();
    String description = Item.SOMEBODY_TO_LOVE_SLIPPERS.getDescription();
    String soundtrack = Item.SOMEBODY_TO_LOVE_SLIPPERS.getVariationType();
    return new Slipper(name, description, soundtrack);
  }

}