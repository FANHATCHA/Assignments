import java.util.*;

public class PotionThree extends Item implements IPotion {

  private ArrayList<Integer> requiredIngredientIDs;
  
  public PotionThree() {
    super(111, "Potion 3", "[INSERT DESCRIPTION HERE]");
    requiredIngredientIDs = new ArrayList<Integer>();
    requiredIngredientIDs.add(108);
    requiredIngredientIDs.add(109);
    requiredIngredientIDs.add(110);
  }

  public ArrayList<Integer> getRequirements() {
    return requiredIngredientIDs;
  }
}

