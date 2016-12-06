import java.util.ArrayList;

public class PotionThree extends Item implements IPotion {

  private static final int POTION_ID = 97;

  private static final int HEALTH_BOOST = 500;
  private static final int MONEY_BOOST = 1000;

  private static int[] requiredIngredientIDs = {
    ItemFactory.DRAGON_SCALE,
    ItemFactory.HIPSTER_COFFEE,
    ItemFactory.MOUNTIE_PRIDE
  };

  public PotionThree() {
    super(POTION_ID, "Potion 3", "This potion boosts your health and money, it requires Dragon Scale, Hipster Coffe, and Mountie Pride\n");
  }

  public int [] getRequirements() {
    return requiredIngredientIDs;
  }

  public void usePotion(Player player) {
    player.obtainHealth(HEALTH_BOOST);
    player.obtainMoney(MONEY_BOOST);
  }

}

