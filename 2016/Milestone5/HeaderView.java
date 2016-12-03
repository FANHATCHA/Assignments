
import java.util.Scanner;
import java.io.*;

public class HeaderView extends ViewDecorator {

  private String header;

  public HeaderView( IView view, String header) {

    super(view);
    if (header == null)
      throw new IllegalArgumentException("Header cannot be null");
    this.header = header;
  }

  public void setHeader(String newHeader) {
    if (newHeader == null)
      throw new IllegalArgumentException("Header cannot be null");
    header = newHeader;
  }

  // Added decorator method
  public void displayHeader() {
    super.display(header);
  }

  @Override public void display(Object obj) {
    displayHeader();
    super.display(obj);
  }

  @Override public String readLine() {
    displayHeader();
    return super.readLine();
  }

}


