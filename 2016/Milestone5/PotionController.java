/**
 * @author William Fiset, Drew Chaboyer
 * Object Oriented Design - COMP 3721
 * Tick Attack milestone #5
 **/

import java.util.*;

public class PotionController {

  private Player player;
  private IView view, headerView;

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

  public PotionController(Player player) {

    this.player = player;
    view = new View();
    headerView = new HeaderView(view, "POTION MENU: ");

  }

  public void usePotion(Item potion) {

    IPotion pot = (IPotion) potion;
    if(player.hasItem(potion.getID())) {
      pot.usePotion(player);
      player.removeItem(potion.getID());
    } else {
      view.display("Sorry, you do not possess that potion.\n");
    }

  }

  private boolean isPotion(Item item) {
    int id = item.getID();
    return id == potion1.getID() || id == potion2.getID() || id == potion3.getID();
  }

  public List <Item> getPotions() {

    int count = 0;
    List <Item> potions = new LinkedList<>();
    Iterator <Item> iter = player.getItemsIterator();

    while(iter.hasNext()) {
      Item item = iter.next();
      if (isPotion(item)) potions.add(item);
    }

    return potions;
  }

  public void invoke() {
    
    view.display("\n\nWELCOME TO THE POTION USE MENU!\n\n");
    List <Item> potions = getPotions();

    if (potions.size() == 0) {

      headerView.display("You do not have any potions\n");

    } else {

      headerView.display("You currently have the following potions in your inventory:\n");
      
      // Sort potions by ID
      Collections.sort( potions, (x,y) -> (Integer.compare(x.getID(), y.getID())));

      for (Item item : potions)
        headerView.display(potion1.getName() + "\n");

      headerView.display("Enter '1' to consume potion one,\n");
      headerView.display("Enter '2' to consume potion two,\n");
      headerView.display("Enter '3' to consume potion three,\n");
      headerView.display("or 'exit' to return to the main menu\n");

      String userInput = view.readLine();

      if(userInput.equals("1")) {
        usePotion(potion1);  
      } else if(userInput.equals("2")) {
        usePotion(potion2);
      } else if(userInput.equals("3")) {
        usePotion(potion3);
      } else if(userInput.equals("exit")) {
        return;
      } else {
        headerView.display("This is not a valid potion option\n");
      }

    }

  }
}  
