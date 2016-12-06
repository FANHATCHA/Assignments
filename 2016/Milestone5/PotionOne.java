import java.util.*;
public class PotionOne extends Item implements IPotion {

  private ArrayList<Integer> requiredIngredientIDs;
  
  public PotionOne() {
    super(104, "Potion 1", "[INSERT DESCRIPTION HERE]");
    requiredIngredientIDs = new ArrayList<Integer>();
    requiredIngredientIDs.add(101);
    requiredIngredientIDs.add(102);
    requiredIngredientIDs.add(103);
  }

  public ArrayList<Integer> getRequirements() {
    return requiredIngredientIDs;
  }
}

