import java.math.*;

public enum Item {

  NEMO_LOLLIPOP("Nemo Lollipop", "", BigInteger.ZERO, FactoryType.LOLLIPOPS, "NEMO");

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