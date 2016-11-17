public class SlipperFactory {

  public static Slipper create(String type) {
    switch (type) {
      case "BOOGIE_WONDERLAND": return createBoogieWonderlandSlippers();
      case "SOMEBODY_TO_LOVE": return createSomebodyToLoveSlippers();
      default: return null;
    }
  }

  private static Slipper createBoogieWonderlandSlippers() {
    return null;
  }

  private static Slipper createSomebodyToLoveSlippers() {
    return null;
  }

}