/**
 * Micah Stairs and Finn Lidbetter
 * Shopping Penguin
 * COMP 3721
 * Dr. Ricker
 * November 21, 2016
 **/

public class Lollipop extends Product {

  private final String flavour;

  public Lollipop(String name, String description, String flavour) {
    super(name, description);
    this.flavour = flavour;
  }

  public String getFlavour() {
    return flavour;
  }

}