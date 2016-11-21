/**
 * Micah Stairs and Finn Lidbetter
 * Shopping Penguin
 * COMP 3721
 * Dr. Ricker
 * November 21, 2016
 **/

public class LollipopFactory {

  public static Lollipop create(String type) {
    switch (type) {
      case "NEMO": return createNemoLollipop();
      case "BLINKY": return createBlinkyLollipop();
      case "CLEO": return createCleoLollipop();
      default: return null;
    }
  }

  private static Lollipop createNemoLollipop() {
    String name = Item.NEMO_LOLLIPOP.getName();
    String description = Item.NEMO_LOLLIPOP.getDescription();
    String flavour = Item.NEMO_LOLLIPOP.getVariationType();
    return new Lollipop(name, description, flavour);
  }

  private static Lollipop createBlinkyLollipop() {
    String name = Item.BLINKY_LOLLIPOP.getName();
    String description = Item.BLINKY_LOLLIPOP.getDescription();
    String flavour = Item.BLINKY_LOLLIPOP.getVariationType();
    return new Lollipop(name, description, flavour);
  }

  private static Lollipop createCleoLollipop() {
    String name = Item.BLINKY_LOLLIPOP.getName();
    String description = Item.BLINKY_LOLLIPOP.getDescription();
    String flavour = Item.BLINKY_LOLLIPOP.getVariationType();
    return new Lollipop(name, description, flavour);
  }  

}