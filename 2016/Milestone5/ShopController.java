/**
 * @author William Fiset, Drew Chaboyer
 * Object Oriented Design - COMP 3721
 * Tick Attack milestone #5
 **/

import java.util.*;

public class ShopController {

  private Player player;
  private IView view, headerView;

  private static Item [] SHOP_ITEMS;
  private static ItemFactory factory;

  static {
    factory = new ItemFactory();
    SHOP_ITEMS = new Item[ItemFactory.FACTORY_ITEMS.length];
    for (int i = 0; i < ItemFactory.FACTORY_ITEMS.length; i++ )
      SHOP_ITEMS[i] = factory.createItemById(ItemFactory.FACTORY_ITEMS[i]);
  }

  public ShopController (Player player) {
    this.player = player;
    view = new View();
    headerView = new HeaderView(view, "SHOP: ");
  }

  public void buyItems() {

    displayShopItems();

    headerView.display("Please enter the id of the item you wish to buy or 'exit'\n");
    String userInput = view.readLine();

  }

  private void displayShopItems() {
    
    headerView.display("The shop currently contains the following items:\n");
    for (int i = 0; i < SHOP_ITEMS.length; i++) {
      Item item = SHOP_ITEMS[i];
      view.display(item.getID() + ") " + item.getName());
    }

  }

  public void sellItems() {

    headerView.display("You currently have the following items in your inventory:\n");
    view.display(player.getInventoryStr());

    // display player inventory
    // There should alreday be an easy way to do this, right?

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

