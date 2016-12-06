/**
 * @author William Fiset, Drew Chaboyer
 * Object Oriented Design - COMP 3721
 * Tick Attack milestone #5
 **/

import static org.junit.Assert.*;
import org.junit.*;

public class ViewTests {

  private IView baseView = new View();

  @Test(expected=Exception.class)
  public void testIllegalHeaderViewCreation() {
    new HeaderView(baseView, null);
  }

  @Test
  public void testViewCreation() {
    
    IView view = new View();
    IView headerView = new HeaderView(view, "A Header");
    IView announcementView = new AnnouncementView(view);

  }

  @Test
  public void testDisplay() {
    
    IView view = new View();
    IView headerView = new HeaderView(view, "A Header");
    IView announcementView = new AnnouncementView(view);

    view.display(null);
    headerView.display(null);
    announcementView.display(null );

    view.display("Hello!\n");
    headerView.display("headerView!\n");
    announcementView.display("announcementView!");

  }

}

