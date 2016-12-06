import java.util.*;

public class PotionTwo extends Item implements IPotion {

  private ArrayList<Integer> requiredIngredientIDs;
  
  public PotionTwo() {
    super(107, "Potion 2", "[INSERT DECRIPTION HERE]");
    requiredIngredientIDs = new ArrayList<Integer>();
    requiredIngredientIDs.add(104);
    requiredIngredientIDs.add(105);
    requiredIngredientIDs.add(106);
  }

  public ArrayList<Integer> getRequirements() {
    return requiredIngredientIDs;
  }
}

