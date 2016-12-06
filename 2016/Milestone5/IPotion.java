/**
 * @author William Fiset, Drew Chaboyer
 * Object Oriented Design - COMP 3721
 * Tick Attack milestone #5
 **/

import java.util.List;

public interface IPotion {

  public int [] getRequirements();
  public void usePotion(Player player);
  public String effectDescription();

}
  
 
