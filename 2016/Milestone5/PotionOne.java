/**
 * @author William Fiset, Drew Chaboyer
 * Object Oriented Design - COMP 3721
 * Tick Attack milestone #5
 **/

public class PotionOne extends Item implements IPotion {

  private static final int POTION_ID = 99;
  private static final int HEALTH_BOOST = 250;

  private static int[] requiredIngredientIDs = {
    ItemFactory.WOLFS_BANE,
    ItemFactory.UNICORN_HAIR,
    ItemFactory.STUDENTS_TEARS
  };

  public PotionOne() {
    super(POTION_ID, "Potion 1", "This potion boosts your health, it requires Wolfsbane, Unicorn Hair, and Students' Tears\n");
  }

  public int [] getRequirements() {
    return requiredIngredientIDs;
  }

  public void usePotion(Player player) {
    player.obtainHealth(HEALTH_BOOST);
  }
}

