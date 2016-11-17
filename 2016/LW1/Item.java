import java.math.*;

public enum Item {

  NEMO_LOLLIPOP("Nemo Lollipop", "This orange and white lollipop is sure to remind of your friends at the bottom of the ocean.", BigInteger.valueOf(2), FactoryType.LOLLIPOPS, "NEMO"),
  BLINKY_LOLLIPOP("Blinky Lollipop", "This orange lollipop might be radioactive. Be careful.", BigInteger.valueOf(4), FactoryType.LOLLIPOPS, "BLINKY"),
  CLEO_LOLLIPOP("Cleo Lollipop", "Keep this gold lollipop away from Figaro.", BigInteger.valueOf(3), FactoryType.LOLLIPOPS, "CLEO"),
  BOOGIE_WONDERLAND_SLIPPERS("Boogie Wonderland Slippers", "A pair of these slippers are sure to keep you dnacing.", BigInteger.valueOf(10), FactoryType.SLIPPERS, "BOOGIE_WONDERLAND"),
  SOMEBODY_TO_LOVE_SLIPPERS("Somebody To Love", "These slippers are not meant to be worn outside of the house. You don't want people knowing that you're a Justin Bieber fan.", BigInteger.valueOf(15), FactoryType.SLIPPERS, "SOMEBODY_TO_LOVE");

  private final String name;
  private final String description;
  private final BigInteger cost;
  private final FactoryType factoryType;
  private final String variationType;

  Item(String name, String description, BigInteger cost, FactoryType factoryType, String variationType) {
    this.name = name;
    this.description = description;
    this.cost = cost;
    this.factoryType = factoryType;
    this.variationType = variationType;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public BigInteger getCost() {
    return cost;
  }

  public FactoryType getFactoryType() {
    return factoryType;
  }

  public String getVariationType() {
    return variationType;
  }

}