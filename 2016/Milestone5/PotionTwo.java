/**
 * @author William Fiset, Drew Chaboyer
 * Object Oriented Design - COMP 3721
 * Tick Attack milestone #5
 **/

public class PotionTwo extends Item implements IPotion {

  private static final int POTION_ID = 96;
  private static final int MONEY_BOOST = 5000;

  private static int[] requiredIngredientIDs = {
    ItemFactory.EEL_EYE,
    ItemFactory.NIGHT_SHADE
  };

  public PotionTwo() {
    super(POTION_ID, "Potion 2", "This potion boosts your money, it requires Potion 1, Eel's Eye, and NightShade.\n");
  }

  public int [] getRequirements() {
    return requiredIngredientIDs;
  }

  public void usePotion(Player player) {
    player.obtainMoney(MONEY_BOOST);
  }
}

