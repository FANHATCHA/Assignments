/**
 * @author William Fiset, Drew Chaboyer
 * Object Oriented Design - COMP 3721
 * Tick Attack milestone #5
 **/

import java.util.Scanner;
import java.util.Formatter;
import java.io.*;

public class View implements IView {

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

  public void display(String format, Object... args) {
    if (format != null && args != null) {
      display( String.format(format, args) );
    }
  }

  // Grab a single line of input from the user
  @Override public String readLine() {
    return scanner.nextLine();
  }

  // This is a more user friendly readLine which strips 
  // quotes and characters a user might accidentally type
  public String softReadLine() {
    return readLine().replaceAll("[\"')]", "").trim();
  }

}

