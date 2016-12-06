/**
 * @author William Fiset, Drew Chaboyer
 * Object Oriented Design - COMP 3721
 * Tick Attack milestone #5
 **/

public abstract class ViewDecorator implements IView {

  // This is the view that is to be decorated
  protected IView view;

  public ViewDecorator(IView view) {
    this.view = view;
  }

  // Delegate reading line to the view
  @Override public String readLine() {
    return view.readLine();
  }

  // Delegate displaying to the view
  @Override public void display(Object obj) {
    view.display(obj);
  }

}


