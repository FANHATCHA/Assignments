import java.util.*;

public class PotionThree extends Item implements IPotion {

  private ArrayList<Integer> requiredIngredientIDs;
  
  public PotionThree() {
    super(111, "Potion 3", "This potion boosts your health and money, it requires Dragon Scale, Hipster Coffe, and Mountie Pride\n");
    requiredIngredientIDs = new ArrayList<Integer>();
    requiredIngredientIDs.add(108);
    requiredIngredientIDs.add(109);
    requiredIngredientIDs.add(110);
  }

  public ArrayList<Integer> getRequirements() {
    return requiredIngredientIDs;
  }

  public void usePotion(Player player) {
    player.obtainHealth(500);
    player.obtainMoney(1000);
  }
}

