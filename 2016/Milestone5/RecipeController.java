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

  public void craftPotionOne() {
    PotionOne potion = factory.createPotionOne();

    ArrayList<Integer> ingredientIDs = potion.getRequirements();
    for(Integer id : ingredientIDs) {
      if(!player.hasItem(id)) {
        view.display("Sorry, you do not have all the required ingredients\n");
        return;
      }
    }
    for(Integer id : ingredientIDs) {
      player.removeItem(id);
    }
    player.obtainItem(potion);
  }

  public void craftPotionTwo() {
    PotionTwo potion = factory.createPotionTwo();

    ArrayList<Integer> ingredientIDs = potion.getRequirements();
    for(Integer id : ingredientIDs) {
      if(!player.hasItem(id)) {
        view.display("Sorry, you do not have all the required ingredients\n");
        return;
      }
    }
    for(Integer id : ingredientIDs) {
      player.removeItem(id);
    }
    player.obtainItem(potion);
 
  }

  public void craftPotionThree() {

    PotionThree potion = factory.createPotionThree();
    ArrayList<Integer> ingredientIDs = potion.getRequirements();
  
    for(Integer id : ingredientIDs) {
      if(!player.hasItem(id)) {
        view.display("Sorry, you do not have all the required ingredients\n");
        return;
      }
    }
    for(Integer id : ingredientIDs) {
      player.removeItem(id);
    }
    player.obtainItem(potion);
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
      craftPotionOne();
    }
    else if(userInput.equals("2")) {
      craftPotionTwo();
    } 
    else if(userInput.equals("3")) {
      craftPotionThree();
    }
    else if(userInput.equals("exit")) {
      return;
    }
    else {
      headerView.display("This is not a valid potion option.\n");
    }
  }
}
