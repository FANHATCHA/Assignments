import java.util.*;

public class PotionController {
  private IView view, headerView;
  private Player player;
  private ItemFactory factory;
  private PotionOne potion1;
  private PotionTwo potion2;
  private PotionThree potion3;
  
  public PotionController(Player player) {
    this.player = player;
    factory = new ItemFactory();
    view = new View();
    headerView = new HeaderView(view, "Potion Menu: ");
    potion1 = factory.createPotionOne();
    potion2 = factory.createPotionTwo();
    potion3 = factory.createPotionThree();
  }

  public void usePotionOne() {
    if(player.hasItem(potion1.getID())) {
      PotionOne pot1 = (PotionOne) player.removeItem(potion1.getID());
      pot1.usePotion(player);
    }
    else {
      view.display("Sorry, you do not possess that potion.\n");
    }
  }

  public void usePotionTwo() {
    if(player.hasItem(potion2.getID())) {
      PotionTwo pot2 = (PotionTwo) player.removeItem(potion2.getID());
      pot2.usePotion(player);
    }
    else {
      view.display("Sorry, you do not possess that potion.\n");
    }
  }

  public void usePotionThree() {
    if(player.hasItem(potion3.getID())) {
      PotionThree pot3 = (PotionThree) player.removeItem(potion3.getID());
      pot3.usePotion(player);
    }
    else {
      view.display("Sorry, you do not possess that potion.\n");
    }
  }

  public void invoke() {
    view.display("\n\nWELCOME TO THE POTION USE MENU!\n\n");
    headerView.display("You currently have the following potions in your inventory:\n");
    if(player.hasItem(potion1.getID())) {
      headerView.display(potion1.getName() + "\n");
    }
    if(player.hasItem(potion2.getID())) {
      headerView.display(potion2.getName() + "\n");
    }
    if(player.hasItem(potion3.getID())) {
      headerView.display(potion3.getName() + "\n");
    }
    headerView.display("Enter '1' to consume potion one,\n");
    headerView.display("'2' to consume potion two,\n");
    headerView.display("'3' to consume potion three,\n");
    headerView.display("or 'exit' to return to the main menu\n");
    String userInput = view.readLine();
    if(userInput.equals("1")) {
      usePotionOne();  
    }
    else if(userInput.equals("2")) {
      usePotionTwo();
    }
    else if(userInput.equals("3")) {
      usePotionThree();
    } 
    else if(userInput.equals("exit")) {
      return;
    } else {
      headerView.display("This is not a valid potion option\n");
    }
  }
}  
