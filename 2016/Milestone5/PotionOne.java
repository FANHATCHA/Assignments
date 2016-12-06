import java.util.*;
public class PotionOne extends Item implements IPotion {

  private ArrayList<Integer> requiredIngredientIDs;
  
  public PotionOne() {
    super(104, "Potion 1", "This potion boosts your health, it requires Wolfsbane, Unicorn Hair, and Students' Tears\n");
    requiredIngredientIDs = new ArrayList<Integer>();
    requiredIngredientIDs.add(101);
    requiredIngredientIDs.add(102);
    requiredIngredientIDs.add(103);
  }

  public ArrayList<Integer> getRequirements() {
    return requiredIngredientIDs;
  }

  public void usePotion(Player player) {
    player.obtainHealth(1000);
  }
}

