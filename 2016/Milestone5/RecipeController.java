import java.util.*;

public class RecipeController {
  private IView view, headerView;
  private Player player;
  private ItemFactory factory;

  public RecipeController(Player player) {
    this.player = player;
    factory = new ItemFactory();
    view = new View();
    headerView = new HeaderView(view, "Recipe Book: ");
  }

  private boolean canCreatePotion(int[] ingredients) {
    for (int ingredient : ingredients)
      if (!player.hasItem(ingredient))
        return false;
    return true;
  }

  private void removeIngredients(int[] ingredients) {
    for (int ingredient : ingredients)
      player.removeItem(ingredient);
  }


  public void craftPotion( Item potion ) {

    IPotion _potion = (IPotion) potion;
    int [] ingredients = _potion.getRequirements();

    if (!canCreatePotion(ingredients)) {
      view.display("Sorry, you do not have all the required ingredients\n");
    } else {
      removeIngredients(ingredients);
      player.obtainItem(potion);
    }

  }

  public void invoke() {

    view.display("\n\nWELCOME TO THE RECIPE BOOK!\n\n");
    headerView.display("There are three potions in this game!\n");
    headerView.display("Potion 1 Description:\n");
    headerView.display(factory.createPotionOne().getDescription());
    headerView.display("Potion 2 Description:\n");
    headerView.display(factory.createPotionTwo().getDescription());
    headerView.display("Potion 3 Description:\n");
    headerView.display(factory.createPotionThree().getDescription());
    headerView.display("Enter '1' to craft potion one,\n");
    headerView.display("'2' to craft potion two,\n");
    headerView.display("'3' to craft potion three,\n");
    headerView.display("or 'exit' to return to the main menu\n:");

    String userInput = view.readLine();
    
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
