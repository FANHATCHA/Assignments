/**
 * @author William Fiset, Drew Chaboyer
 * Object Oriented Design - COMP 3721
 * Tick Attack milestone #5
 **/

import java.util.*;

public class RecipeController {

  private IView view, headerView;
  private Player player;

  private static ItemFactory factory;
  private static PotionOne potion1;
  private static PotionTwo potion2;
  private static PotionThree potion3;
  
  static {
    factory = new ItemFactory();
    potion1 = factory.createPotionOne();
    potion2 = factory.createPotionTwo();
    potion3 = factory.createPotionThree();    
  }

  // Init class
  public RecipeController(Player player) {
    this.player = player;
    factory = new ItemFactory();
    view = new View();
    headerView = new HeaderView(view, "Recipe Book: ");
  }

  // Check if we have all the ingredients to create this potion
  private boolean canCreatePotion(int[] ingredients) {
    for (int ingredient : ingredients)
      if (!player.hasItem(ingredient))
        return false;
    return true;
  }

  // Remove all the ingredients
  private void removeIngredients(int[] ingredients) {
    for (int ingredient : ingredients)
      player.removeItem(ingredient);
  }

  // Create the potion!
  public void craftPotion( Item potion ) {

    IPotion _potion = (IPotion) potion;
    int [] ingredients = _potion.getRequirements();

    if (!canCreatePotion(ingredients)) {
      view.display("Sorry, you do not have all the required ingredients\n");
    } else {
      removeIngredients(ingredients);
      player.obtainTempItem(potion);
      headerView.display("Congrats! You successfully crafted the potion!\n");
      headerView.display("Your potion will become available once you complete your next quest.\n");

    }

  }

  // Kickstart this controller
  public void invoke() {

    view.display("\n\nWELCOME TO THE RECIPE BOOK!\n\n");

    headerView.display("There are three potions in this game!\n");
    headerView.display("Potion 1 Description:\n");
    headerView.display(potion1.getDescription());
    headerView.display("Potion 2 Description:\n");
    headerView.display(potion2.getDescription());
    headerView.display("Potion 3 Description:\n");
    headerView.display(potion3.getDescription());
    headerView.display("Enter '1' to craft potion one,\n");
    headerView.display("Enter '2' to craft potion two,\n");
    headerView.display("Enter '3' to craft potion three,\n");
    headerView.display("or 'exit' to return to the main menu\n:");

    String userInput = view.readLine().trim();
    
    if(userInput.equals("1")) {
      craftPotion( factory.createPotionOne() );
    } else if(userInput.equals("2")) {
      craftPotion( factory.createPotionTwo() );
    } else if(userInput.equals("3")) {
      craftPotion( factory.createPotionThree() );
    } else if(userInput.equals("exit")) {
      return;
    } else {
      headerView.display("This is not a valid potion option.\n");
    }
  }

}
