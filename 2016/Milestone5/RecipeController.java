import java.util.*;

public class RecipeController {
  private IView view, headerView;
  private Player player;
  
  public RecipeController(Player player) {
    this.player = player;
    view = new View();
    headerView = new HeaderView(view, "Recipe Book: ");
  }

  public boolean hasPotionOneIngredients() {
  return false;
  }
  public void craftPotionOne() {
    if(player.hasItem(101)) {
    
    } else {
      view.display("Temp");
    }
  }

  public void craftPotionTwo() {
  }

  public void craftPotionThree() {
  }

  public void invoke() {

    view.display("\n\nWELCOME TO THE RECIPE BOOK!\n\n");
    headerView.display("Enter 1 to craft potion 1, 2 to craft potion 2, or 3 to craft potion 3:");
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
    else {
      headerView.display("This is not a valid potion option");
    }
  }
}
