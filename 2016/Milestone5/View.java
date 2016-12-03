 /*
 * @author William Fiset, Jonathan Whitaker
 * Tick Attack milestone #3
 * Object Oriented Design - COMP 3721
 */

import java.util.Scanner;
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

  // Grab a single line of input from the user
  public String readLine() {
    return scanner.nextLine();
  }  

}

