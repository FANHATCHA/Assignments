 /*
 * @author William Fiset, Jonathan Whitaker
 * Tick Attack milestone #3
 * Object Oriented Design - COMP 3721
 */

import java.util.Scanner;
import java.io.*;

interface IView {

  public String readLine();
  public void display(Object obj);

}

class View implements IView {

  protected Scanner scanner;
  protected Writer writer;  

  public View () {
    this(System.in, System.out);
  }

  public View(InputStream in, OutputStream out) {
    scanner = new Scanner(in);
    writer = new PrintWriter(out);
  }  

  public void display(Object obj) {
    try {
      if (obj != null) 
        writer.write(obj.toString());
      else
        writer.write("null\n");
      writer.flush();
    } catch (IOException e) { e.printStackTrace(); }
  }

  // Grab a single line of input from the user
  public String readLine() {
    return scanner.nextLine();
  }  

}

abstract class ViewDecorator implements IView {

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

class HeaderView extends ViewDecorator {

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

class AnnouncementView extends ViewDecorator {

  static final int PADDING = 12;
  static final char PADDING_CHAR = '*';

  public AnnouncementView(IView view) {
    super(view);
  }

  public String generateBorder(int len) {
    return new String(new char[len]).replace('\0', PADDING_CHAR);
  }

  @Override public void display(Object obj) {

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

