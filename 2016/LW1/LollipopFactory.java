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
    return null;
  }

  private static Lollipop createBlinkyLollipop() {
    return null;
  }

  private static Lollipop createCleoLollipop() {
    return null;
  }  

}