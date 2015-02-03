import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;


/*

	TO-DO:

	-For "Save As" button, the user should be able to type in a new file name
	(currently the user is only able to overwrite another file (at least on Mac OS))
	-Application needs to start in the center of the screen
	-Options for the effects need to be made available for the user
	-4 buttons at the bottom need to be enabled/disabled appropriately

*/

public class BitmapGUI extends JFrame implements ActionListener {

		/* Constants */

	private static final int DEFAULT_CANVAS_HEIGHT = 550; 
	private static final int DEFAULT_CANVAS_WIDTH =  700;
	private static final int PADDING = 75;
	private static final String TITLE = "BitmapHacker";

		/* Variables */

	private File mostRecentInputFile;
	private Bitmap bmp;
	private boolean modified;

		/* Components */

	private JMenuItem closeMenuItem, saveMenuItem, saveAsMenuItem;
	private JButton flipButton, blurButton, enhanceButton, combineButton;

	public static void main(String[] args) {
		
		BitmapGUI frame = new BitmapGUI();
		// frame.setResizable(false);

		// frame.setSize(500, 500);		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		// frame.setLocation(	(dim.width/2) - (frame.getSize().width/2),
		// 					(dim.height/2) - (frame.getSize().height/2) );
		
	}

	public BitmapGUI () {

			/* Ensure our application will be closed when the user presses the "X" */

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			/* Add a canvas to the center of the window */

		Canvas canvas = new Canvas();

	 	Container cPane = this.getContentPane();
	 	cPane.add( canvas, BorderLayout.CENTER);

	 		/* Add the menu, buttons, and update the window's title */

	 	addMenu();
		addButtons();
		updateTitle();

			/* Show the user our wonderful GUI */

		pack();
		setVisible(true);

	}

	/**
	* Adds the menu system to the application, hooking up all of the proper
	* keyboard short-cuts, and disabling menu items as needed.
	*/
	private void addMenu () {

		JMenuBar menuBar = new JMenuBar();
		JMenuItem menuItem;
		JMenu menu;

		menu = new JMenu("File");
		menu.getAccessibleContext().setAccessibleDescription("File");
		menuBar.add(menu);

		menuItem = new JMenuItem("Open");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Load");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveMenuItem.getAccessibleContext().setAccessibleDescription("Save");
		saveMenuItem.addActionListener(this);
		saveMenuItem.setEnabled(false);
		menu.add(saveMenuItem);

		saveAsMenuItem = new JMenuItem("Save As");
		saveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		saveAsMenuItem.getAccessibleContext().setAccessibleDescription("Save As");
		saveAsMenuItem.addActionListener(this);
		saveAsMenuItem.setEnabled(false);
		menu.add(saveAsMenuItem);

		menu.addSeparator();

		closeMenuItem = new JMenuItem("Close");
		closeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
		closeMenuItem.getAccessibleContext().setAccessibleDescription("Close");
		closeMenuItem.addActionListener(this);
		closeMenuItem.setEnabled(false);
		menu.add(closeMenuItem);

		menuItem = new JMenuItem("Quit");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Quit");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		this.setJMenuBar(menuBar);

	}

	/**
	* This method handles all of the actions triggered when the user interacts
	* with the main menu, or presses keyboard shortcuts to trigger those events.
	* @param event - the triggered event
	*/
	public void actionPerformed(ActionEvent event) {

		try {

		 	switch (event.getActionCommand()) {

		 		case "Save As":
		 			
		 			File newFile = selectFile("Pick your .BMP");

		 			if (newFile == null)
		 				break;
		 			else
		 				mostRecentInputFile = newFile;

		 		case "Save":

		 			bmp.writeBitmap(mostRecentInputFile);
		 			modified = false;
		 			
		 			break;

		 		case "Open":
		 			
		 			File openedFile = selectFile("Pick your .BMP");

		 			if (openedFile != null) {
		 				
		 				bmp = new Bitmap(mostRecentInputFile = openedFile);
		 				modified = false;

		 			}

		 			break;

		 		case "Close":

		 			bmp = null;
		 			break;

		 		case "Quit":

		 			System.exit(0);
		 			break;

		 	}

		 } catch(IOException e) {

		 }

		 	/* Refresh screen */

		 pack();
		 repaint();

		 	/* Update menu items */

		 if (bmp == null) {
		 	saveMenuItem.setEnabled(false);
		 	saveAsMenuItem.setEnabled(false);
		 	closeMenuItem.setEnabled(false);
		 } else {
			saveMenuItem.setEnabled(true);
		 	saveAsMenuItem.setEnabled(true);
		 	closeMenuItem.setEnabled(true);
		 }

		 	/* Update the title to reflect possible file changes */

		 updateTitle();

	 }

	 /**
	 * Adds the 4 buttons to the screen which allow the user to apply
	 * various visual effects to the image.
	 */
	 private void addButtons() {

	 	Container cPane = this.getContentPane();

		JPanel buttonPanel = new JPanel();

		flipButton = new JButton("Flip");
		blurButton = new JButton("Blur");
		enhanceButton = new JButton("Enhance");
		combineButton = new JButton("Combine");

		buttonPanel.add(flipButton);
		buttonPanel.add(blurButton);
		buttonPanel.add(enhanceButton);
		buttonPanel.add(combineButton);

		cPane.add(buttonPanel, BorderLayout.SOUTH);

		flipButton.addActionListener(
			new ActionListener () {
				public void actionPerformed( ActionEvent e) {

					bmp.flip();
					imageWasModified();
					repaint();

				}
			}
		);

		blurButton.addActionListener(
			new ActionListener () {
				public void actionPerformed( ActionEvent e) {

					bmp.blur(1);
					imageWasModified();
					repaint();

				}
			}
		);

		enhanceButton.addActionListener(
			new ActionListener () {
				public void actionPerformed( ActionEvent e) {

					bmp.enhanceColor("red");
					imageWasModified();
					repaint();

				}
			}
		);

		combineButton.addActionListener(
			new ActionListener () {
				public void actionPerformed( ActionEvent e) {

					System.out.println("Combine - not yet implemented");
					// imageWasModified();

				}
			}
		);

	 }

	 /** 
	 * Opens up a JFileChooser for the user to choose a file from their file system.
	 * @return - a file that the user selected on their computer, or null if they didn't choose anything
	 */
	private File selectFile (String title) {

			/* Set up the file chooser, beginning at the proper directory */

		JFileChooser fileChooser = new JFileChooser();

		if (mostRecentInputFile != null)
			fileChooser.setCurrentDirectory(mostRecentInputFile.getParentFile());

		fileChooser.setDialogTitle(title);

			/* Prompt user to select a file, returning it */

		fileChooser.showOpenDialog(null);

		return fileChooser.getSelectedFile();
		
	} 

	/**
	* This method should be called whenever a picture manipulation occurs.
	* The title of the window changes to indicate to the user that the image has changed.
	*/
	private void imageWasModified() {

		modified = true;
		updateTitle();

	}

	/**
	* This method updates the title of the window depending on whether or not an image
	* is open, and whether the image has been modified.
	*/
	private void updateTitle() {

		if (bmp == null)
			setTitle(TITLE);
		else if (modified)
			setTitle(String.format("%s (%s*)", TITLE, mostRecentInputFile.getName()));
		else
			setTitle(String.format("%s (%s)", TITLE, mostRecentInputFile.getName()));

	}

	/** PRIVATE INNER CLASS **/
	private class Canvas extends JPanel {

	 	public Canvas () {
	 		setVisible(true);
	 	}

	 	/**
	 	* Returns the dimensions that the canvas should be, taking into consideration
	 	* the image size and padding.
	 	*/
	 	@Override public Dimension getPreferredSize() {

	 		return bmp == null 	? new Dimension(DEFAULT_CANVAS_WIDTH + PADDING*2, DEFAULT_CANVAS_HEIGHT + PADDING*2)
	 							: new Dimension(bmp.getWidth() + PADDING*2, bmp.getHeight() + PADDING*2);
	 	
	 	}

	 	/**
	 	* Updates the canvas, drawing the image (or blank canvas) in the center,
	 	* with pre-defined padding around it.
	 	* @param g - Graphics object (which is passed through by Java)
	 	*/
	 	@Override protected void paintComponent(Graphics g) {

	 		super.paintComponent(g);

	 			/* Draw blank canvas */

	 		if (bmp == null) {
	 			g.setColor(Color.LIGHT_GRAY);
		 		g.fillRect(
		 			PADDING,
		 			PADDING,
		 			bmp == null ? DEFAULT_CANVAS_WIDTH : bmp.getWidth(),
		 			bmp == null ? DEFAULT_CANVAS_HEIGHT : bmp.getHeight()
		 		);
	 		}

	 			/* Draw image */

	 		else
	 			g.drawImage(bmp.getImage(), PADDING, PADDING, null);

	 	}
	 }



}