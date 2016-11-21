/**
 * Micah Stairs and Finn Lidbetter
 * Shopping Penguin
 * COMP 3721
 * Dr. Ricker
 * November 21, 2016
 **/

public class Slipper extends Product {

  private final String soundtrack;

  public Slipper(String name, String description, String soundtrack) {
    super(name, description);
    this.soundtrack = soundtrack;
  }

  public String getSoundtrack() {
    return soundtrack;
  }

}