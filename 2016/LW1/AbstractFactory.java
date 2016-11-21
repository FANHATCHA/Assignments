/**
 * Micah Stairs and Finn Lidbetter
 * Shopping Penguin
 * COMP 3721
 * Dr. Ricker
 * November 21, 2016
 **/

public abstract class AbstractFactory {

  public static Product create(FactoryType factoryType, String type) {
    switch (factoryType) {
      case LOLLIPOPS: return LollipopFactory.create(type);
      case SLIPPERS: return SlipperFactory.create(type);
      default: return null;
    }
  }

}