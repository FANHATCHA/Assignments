/*
 * @author William Fiset, Jonathan Whitaker
 * Tick Attack milestone #3
 * Object Oriented Design - COMP 3721
 */

import java.util.*;

public class ShopController {

  private Player player;
  private IView view, headerView;

  public ShopController (Player player) {
    this.player = player;
    view = new View();
    headerView = new HeaderView(view, "SHOP: ");
  }

  public void buyItems() {



  }

  private void displayShopItems() {
    
  }

  public void sellItems() {

    

  }

  public void invoke() {

    view.display("\n\nWELCOME TO THE SHOP!\n\n");

    view.display("Would you like to buy or sell items?\n");
    headerView.display("buy/sell: ");

    String userInput = view.readLine().toLowerCase();

    if (userInput.equals("buy")) {
      buyItems();
    } else if (userInput.equals("sell")) {
      sellItems();
    } else {
      headerView.display("Unknown command '" + userInput + "'");
    }

  }

}

