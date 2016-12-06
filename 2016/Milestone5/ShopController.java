/**
 * @author William Fiset, Drew Chaboyer
 * Object Oriented Design - COMP 3721
 * Tick Attack milestone #5
 **/

import java.util.*;

public class ShopController {

  private Player player;
  private HeaderView headerView;
  private View view;

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

    headerView.display("You currently have " + player.getMoney() + "$ you can spend!\n");
    headerView.display("Please enter the id of the item you wish to buy or 'exit'\n");
    view.display("Item id: ");

    String userInput = view.readLine().toLowerCase().replaceAll("[\"')]", "").trim();
  
    if (userInput.equals("exit")) return;

    try {

      boolean foundId = false;
      Integer id = Integer.parseInt(userInput) + 100;

      for (Item shopItem : SHOP_ITEMS) {
        if (shopItem.getID() == id) {

          int itemPrice = shopItem.getItemValue();
          long playerMoney = player.getMoney();

          // Check if player can actually buy this item
          if ( playerMoney - itemPrice >= 0 ) {

            // Add item to player inventory and
            // charge them for it
            player.obtainItem(shopItem);
            player.obtainMoney(-itemPrice);
            headerView.display("You purchased '%s' for %d$\n", shopItem.getName(), itemPrice );
            headerView.display("Your new total is " + player.getMoney() + "\n" );

          } else {

            headerView.display("Looks like you don't have enough money to buy that\n");

          }

        }
      }

    } catch (NumberFormatException e) {
      view.display("Error, please enter a valid ID!\n");
    }

  }

  // Print the shop items and their prices
  private void displayShopItems() {
    
    headerView.display("The shop currently contains the following items:\n");
    for (int i = 0; i < SHOP_ITEMS.length; i++) {
      Item item = SHOP_ITEMS[i];
      view.display("%d) %s - %d$ - %s\n", item.getID() % 100, item.getName(), item.getItemValue(), item.getDescription());
    }

  }

  public void sellItems() {

    int sellId = -1;
    Item item = null;

    headerView.display("You currently have the following items in your inventory:\n");
    view.display(player.getInventoryStr());

    // Get the name of the item you want to sell
    view.display("Please enter the name of the item to sell\n");
    String userInput = view.readLine().toLowerCase().trim();

    // Loop through all the players items
    Iterator <Item> iter = player.getItemsIterator();
    while (iter.hasNext()) {
      item = iter.next();
      if (item.getName().toLowerCase().equals(userInput)) {
        sellId = item.getID();
        break;
      }
    }

    // Sell item if it existed
    if (sellId != -1) {
      player.sellItem(sellId);
      headerView.display("You have sold '%s' for %d$\n", item.getName(), item.getItemValue() );
    } else {
      headerView.display( "'%s' is not an item in your inventory\n", userInput );
    }

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
      headerView.display("Unknown command '" + userInput + "'\n");
    }

  }

}

