


public class AnnouncementView extends ViewDecorator {

  static final int PADDING = 12;
  static final char PADDING_CHAR = '*';

  public AnnouncementView(IView view) {
    super(view);
  }

  public String generateBorder(int len) {
    return new String(new char[len]).replace('\0', PADDING_CHAR);
  }

  @Override public void display(Object obj) {
    
    if (obj != null) {
      String str = obj.toString();
      int len = str.length();

      String top = "\n" + generateBorder(len + 2*PADDING) + "\n";
      String strOutput = generateBorder(PADDING) + obj.toString().toUpperCase() + generateBorder(PADDING) + "\n";
      String bottom = generateBorder(len + 2*PADDING) + "\n\n";
    
      super.display(top);
      super.display(strOutput);
      super.display(bottom);
    }

  }

}


