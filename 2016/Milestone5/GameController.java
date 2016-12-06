/**
 * @author William Fiset, Drew Chaboyer
 * Object Oriented Design - COMP 3721
 * Tick Attack milestone #5
 **/

import java.util.*;

public class GameController {

  private Player player;
  private IView view, headerView, inputView, announcementView;
  private QuestController questController;
  private ShopController shopController;
  private RecipeController recipeController;
  private PotionController potionController;
  private int numQuestsCompleted = 0;

  private static final int TICK_DAMAGE = -175;

  public GameController() {
    startGame();
  }

  public void resetGame() {

    player = new Player();
    questController = new QuestController(player);
    shopController = new ShopController(player);
    recipeController = new RecipeController(player);
    potionController = new PotionController(player);
    view = new View();
    headerView = new HeaderView(view, "MENU: ");
    inputView = new HeaderView(view, "ENTER QUEST ID: ");
    announcementView = new AnnouncementView(view);

  }

  public void startGame() {

    resetGame();
    displayIntro();

    // Kickstart game
    while (player.isAlive()) {

      // View gets input
      String userInput = headerView.readLine();

      // Game controller processes input
      executeUserCommand(userInput);

    }

    announcementView.display("you have died. game over.");

  }

  private void displayIntro() {
    announcementView.display("Welcome to Tick Attack!");
    headerView.display("Use one of the following commands to navigate through the game\n");
    displayHelp();
  }

  public void executeUserCommand(String command) {
    
    if (command == null) return;
    command = command.trim().toLowerCase();
    if (command.equals("\n") || command.equals("")) return;
    
    if (command.equals("help")) {

      displayHelp();

    } else if (command.equals("reset") || command.equals("restart")) {

      resetGame();
      headerView.display("Game has been reset!\n\n");

    } else if (command.equals("exit") || command.equals("quit")) {
    	headerView.display("Goodbye!");
      quitGame();

    // Display the player status
    } else if (command.equals("status")) {
      
      displayPlayerStatus();

    } else if (command.equals("quests") || command.equals("quest")) {
      
      // Display all the available quests and their names
      selectQuest();

    } else if ( command.equals("tickcheck") ) {

      doTickCheck();

    } else if (command.equals("recipes") || command.equals("recipe")) {
    	
    	recipeController.invoke();
    	
    } else if (command.equals("potions") || command.equals("potion")) {

      potionController.invoke();

    } else if ( command.equals("shop") ) {

      shopController.invoke();

    } else if ( command.equals("sell") ) {
      shopController.sellItems();

    } else if ( command.equals("buy") ) {
      shopController.buyItems();

    } else {
      headerView.display("Unknown command: '" + command + "'\n");
    }
  }

  // Prints all available quests displaying each of the quests id numbers
  private boolean displayAvailbleQuests() {

    Set <Integer> questIds = questController.getVisitableQuests();
    if (questIds.size() == 0) return false;

    view.display("\nAVAILABLE QUESTS:\n");
    for (Integer id : questIds)
      view.display("  " + id + ") " + questController.getQuestName(id) + "\n" );
    view.display("\n");
    headerView.display("Select one of the quests by typing the quest number\n");

    return true;

  }

  public void selectQuest() {

    if (displayAvailbleQuests()) {
      String line = inputView.readLine().replaceAll("[\"')]", "").trim();
      try {

        int id = Integer.parseInt(line);

        headerView.display("You selected quest #" + id + "\n");
        if (questController.hasQuest(id)) {
          if (questController.questIsVisitable(id)) {
            
            checkForTicks();
            if (player.isAlive()) {
              questController.playQuest(id);
              numQuestsCompleted++;
            } else return;
            player.setDidTickCheck(false);

          } else {
            headerView.display("Quest " +id+ " has not been unlocked yet!\n");
            headerView.display("\n");
          }
        } else {
          headerView.display("Quest " +id+ " does not exist.\n");
        }

      } catch (NumberFormatException e) {
        headerView.display("Error. Please type in a number.\n");
      }
    } else {
      headerView.display("No quests are available, sorry! Make sure there are *.quest files in the quests folder.\n");
    }

  }

  private void doTickCheck() {
    
    player.setDidTickCheck(true);
    headerView.display("You did a tickcheck and removed any ticks that were found on your body.\n");

  }

  private void checkForTicks() {

    if (numQuestsCompleted > 0) {
      if (!player.didTickCheck()) {

        headerView.display("You forgot to do a tick check! (type 'tickcheck' before starting any quest)\n");

        if (player.hasTicks()) {
          headerView.display("Ticks were found on your body! You lose "+Math.abs(TICK_DAMAGE)+" ♥\n");
          player.obtainHealth(TICK_DAMAGE);
          headerView.display("New player health is " + player.getHealth() +"♥\n" );
        } else {
          headerView.display("Luckily no Ticks were found on your body, but never forget to do tickchecks.\n");
        }

      } else {
        headerView.display("Good job! You performed a tickcheck after doing the last quest\n");
      }
    }

  }

  private void displayPlayerStatus() {
    view.display( player );
  }

  private void quitGame() {
    System.exit(0);
  }

  public void displayHelp() {
    
    headerView.display("'help'           - display this help menu\n");
    headerView.display("'exit/quit'      - exit game\n");
    headerView.display("'status'         - display player status\n");
    headerView.display("'tickcheck'      - perform a tickcheck (Do this after every quest!)\n");
    headerView.display("'quests'         - display and select a quest you wish to do\n");
    headerView.display("'shop/buy/sell'  - Go to the shop and buy and sell Items\n");
    headerView.display("'recipes'        - Use your potions recipe book to create potions\n");
    headerView.display("'potions'        - Consume the potions you have crafted\n");
    headerView.display("'reset'          - reset game\n");

  }

  public static void main(String[] args) {
    new GameController();
  }

}
