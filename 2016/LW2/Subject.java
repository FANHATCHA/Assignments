/*
 * @author William Fiset, Finn Lidbetter
 * Lightning week 2 project
 * Object Oriented Design - COMP 3721
 */

public interface Subject {

  public void broadcast();
  public void attach(Observer observer);
  public void detach(Observer observer);

}
