import java.awt.*;
import javax.swing.*;
import java.io.*;

public class BitmapGUI extends JFrame {

	private File mostRecentInputFile;
	private Bitmap bmp;
	private boolean modified;

	public static void main(String[] args) {
		
		BitmapGUI frame = new BitmapGUI();
		// frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(500, 500);
		// Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		//frame.setLocation(	(dim.width/2) - (frame.getSize().width/2),
		//					(dim.height/2) - (frame.getSize().height/2) );
		
		frame.setTitle("This is the title");
		frame.setVisible(true);

	}

	/**
	 * Prompt the user to pick a file.
	 * @param title - a title to give the window
	 * @return
	 */
	private static File pickFile(String title) {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle(title);
		
		// Repeatedly prompt user for file until they decide to co-operate
		while (true)
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				return fileChooser.getSelectedFile();
		
	}

}
