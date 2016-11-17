public abstract class AbstractFactory {

  public static Product create(FactoryType factoryType, String type) {
    switch (factoryType) {
      case LOLLIPOPS: return LollipopFactory.create(type);
      case SLIPPERS: return SlipperFactory.create(type);
      default: return null;
    }
  }

}