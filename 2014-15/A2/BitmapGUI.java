import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;

/*

	TO-DO:

	-For "Save As" button, the user should be able to type in a new file name
	(currently the user is only able to overwrite another file (at least on Mac OS))
	-Options for the effects need to be made available for the user

	-Effect ideas:
		-Combine
		-Swirl??

*/

public class BitmapGUI extends JFrame implements ActionListener {

		/* Constants */

	private static final String TITLE = "BitmapHacker";
	
		/* Variables */

	private File mostRecentInputFile;
	private boolean modified;
	private Bitmap bmp;

		/* Components */

	private JMenuItem closeMenuItem, saveMenuItem, saveAsMenuItem, undoAllMenuItem, undoMenuItem, redoMenuItem;
	private JButton flipButton, blurButton, enhanceButton, combineButton, rotateButton, grayscaleButton, edgeButton, mosaicButton,
					undoButton, redoButton,
					applyButton;

	private Canvas canvas;

	private Stack<Bitmap> 	undoStack = new Stack<Bitmap>(),
							redoStack = new Stack<Bitmap>();

	private JPanel optionPanel;
	private JSlider sliderOne, sliderTwo, sliderThree;

	public static void main(String[] args) {
		
		BitmapGUI frame = new BitmapGUI();

	}

	public BitmapGUI () {

			/* Add a canvas to the center of the window */

		canvas = new Canvas();

	 	Container cPane = this.getContentPane();
	 	cPane.add(canvas, BorderLayout.CENTER);

	 		/* Add the menu, buttons, and update the window's title */

	 	addMenu();
		addButtons();
		addOptionPanel();

		trackWindowSize();

			/* Show the user our wonderful GUI */
		
		pack();
		setGUIproperties();
		cleanupBeforeProgramQuits( );

	}

	private void addOptionPanel () {

		// New grid layout with Four rows and One column
		GridLayout optionPanelLayout = new GridLayout(4, 1);

		optionPanel = new JPanel( optionPanelLayout );

		applyButton = new JButton("Apply Effect");

		// By default slider value is set to 50 in an interval of [0, 100]
		sliderOne   = new JSlider();
		sliderTwo   = new JSlider();
		sliderThree = new JSlider();

		// Place Sliders on the right side of the screen
		optionPanel.add(sliderOne );
		optionPanel.add(sliderTwo );
		optionPanel.add(sliderThree );
		optionPanel.add(applyButton);

		add(optionPanel, BorderLayout.EAST);

		sliderOne.addChangeListener(
			new ChangeListener() {
				@Override public void stateChanged(ChangeEvent e) {
					System.out.println("1");
				}
			}
		);

		sliderTwo.addChangeListener(
			new ChangeListener() {
				@Override public void stateChanged(ChangeEvent e) {
					System.out.println("2");
				}
			}
		);

		sliderThree.addChangeListener(
			new ChangeListener() {
				@Override public void stateChanged(ChangeEvent e) {
					System.out.println("3");
				}
			}
		);

	}

	/** Set some default GUI Properties **/
	private void setGUIproperties() {
		
		// Ensure our application will be closed when the user presses the "X" */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Sets screen location in the center of the screen (Works only after calling pack)
		setLocationRelativeTo(null);

		// Update Title
		updateTitle();

		// Show Screen
		setVisible(true);

	}

	private void enableButtons() {

		flipButton.setEnabled(true);
		blurButton.setEnabled(true);
		enhanceButton.setEnabled(true);
		combineButton.setEnabled(true);
		rotateButton.setEnabled(true);
		grayscaleButton.setEnabled(true);
		edgeButton.setEnabled(true);
		mosaicButton.setEnabled(true);

		updateUndoRedoButtons();

	}

	private void disableButtons() {

		flipButton.setEnabled(false);
		blurButton.setEnabled(false);
		enhanceButton.setEnabled(false);
		combineButton.setEnabled(false);
		rotateButton.setEnabled(false);
		grayscaleButton.setEnabled(false);
		edgeButton.setEnabled(false);
		mosaicButton.setEnabled(false);

		updateUndoRedoButtons();

	}

	/**
	 * Saves the current BitMap file into a temporary image folder
	 */
	private void saveTempImage( ) {

		// ∃ an image to save
		if (bmp != null) {
			try {

				undoStack.push((Bitmap) bmp.clone());

			} catch (CloneNotSupportedException e) {
				System.err.println("Houston we've got a problem. Temp bmp file could not be cloned.");

			} catch (OutOfMemoryError e) {
				JOptionPane.showMessageDialog(null, "Memory exceeded, clearing all undo/redos", "Warning", JOptionPane.WARNING_MESSAGE);
				undoStack.clear();
				redoStack.clear();
				updateUndoRedoButtons();
			}
		}

	} // saveTempImage

	/**
	 * Currently not being used because we are trying to find a better way to center the canvas on the screen
	 */
	 private void trackWindowSize() {

		this.addComponentListener(new ComponentAdapter() {
		    @Override public void componentResized(ComponentEvent e) {

		    	canvas.refreshSize();

		        pack();
		        repaint();

		    }

		});

	}

	/**
	* Adds the menu system to the application, hooking up all of the proper
	* keyboard short-cuts, and disabling menu items as needed.
	*/
	private void addMenu () {

		JMenuBar menuBar = new JMenuBar();
		JMenuItem menuItem;
		JMenu menu;

			/* File Menu */

		menu = new JMenu("File");
		menu.getAccessibleContext().setAccessibleDescription("File");
		menuBar.add(menu);

		menuItem = new JMenuItem("Open...");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Open...");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveMenuItem.getAccessibleContext().setAccessibleDescription("Save");
		saveMenuItem.addActionListener(this);
		saveMenuItem.setEnabled(false);
		menu.add(saveMenuItem);

		saveAsMenuItem = new JMenuItem("Save As...");
		saveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		saveAsMenuItem.getAccessibleContext().setAccessibleDescription("Save As...");
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

			/* Edit Menu */

		menu = new JMenu("Edit");
		menu.getAccessibleContext().setAccessibleDescription("Edit");
		menuBar.add(menu);

		undoMenuItem = new JMenuItem("Undo");
		undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		undoMenuItem.getAccessibleContext().setAccessibleDescription("Undo");
		undoMenuItem.addActionListener(this);
		undoMenuItem.setEnabled(false);
		menu.add(undoMenuItem);

		undoAllMenuItem = new JMenuItem("Undo All");
		undoAllMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		undoAllMenuItem.getAccessibleContext().setAccessibleDescription("Undo All");
		undoAllMenuItem.addActionListener(this);
		undoAllMenuItem.setEnabled(false);
		menu.add(undoAllMenuItem);

		menu.addSeparator();

		redoMenuItem = new JMenuItem("Redo");
		redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		redoMenuItem.getAccessibleContext().setAccessibleDescription("Redo");
		redoMenuItem.addActionListener(this);
		redoMenuItem.setEnabled(false);
		menu.add(redoMenuItem);

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

		 		case "Save As...":
		 			
		 			File newFile = selectFile("Pick your .BMP");

		 			if (newFile == null)
		 				break;
		 			else
		 				mostRecentInputFile = newFile;

		 		case "Save":

		 			bmp.writeBitmap(mostRecentInputFile);
		 			modified = false;
		 			break;

				case "Open...":
				 
				 File openedFile = selectFile("Pick your .BMP");

				 if (openedFile != null) {

				 	// Method for this?
				 	redoStack.clear();
				 	undoStack.clear();
				 	saveTempImage();
					bmp = new Bitmap(mostRecentInputFile = openedFile);
					enableButtons();
					modified = false;
					canvas.refreshSize();	

				 }

		 			break;

		 		case "Close":

		 			// Method for this?
		 			redoStack.clear();
				 	undoStack.clear();
		 			disableButtons();
		 			bmp = null;
		 			break;

		 		case "Quit":

		 			System.exit(0);
		 			break;

		 		case "Undo":

		 			undo();
		 			break;

		 		case "Undo All":

		 			undoAll();
		 			break;

		 		case "Redo":

		 			redo();
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
		
		flipButton      = new JButton("Flip");
		blurButton      = new JButton("Blur");
		enhanceButton   = new JButton("Enhance");
		combineButton   = new JButton("Combine");
		rotateButton    = new JButton("Rotate");
		grayscaleButton = new JButton("Grayscale");
		edgeButton      = new JButton("Edge");
		mosaicButton    = new JButton("Mosaic");
		

		addUndoRedoButtons();

		// Set all disabled since there is not image yet
		disableButtons();

		// Add buttons to the screen
		buttonPanel.add(flipButton);
		buttonPanel.add(blurButton);
		buttonPanel.add(enhanceButton);
		buttonPanel.add(combineButton);
		buttonPanel.add(rotateButton);
		buttonPanel.add(grayscaleButton);
		buttonPanel.add(edgeButton);
		buttonPanel.add(mosaicButton);


		cPane.add(buttonPanel, BorderLayout.SOUTH);


		edgeButton.addActionListener(
			new ActionListener () {
				public void actionPerformed( ActionEvent e) {

					if (bmp != null) {
						
						saveTempImage();
						bmp.edgeDetection( 10 ); 

						imageWasModified();
						redoStack.clear();
						updateUndoRedoButtons();
						repaint();
					} 

				}
			}
		);
		

		mosaicButton.addActionListener(
			new ActionListener () {
				public void actionPerformed( ActionEvent e) {

					if (bmp != null) {
						
						saveTempImage();
						bmp.mosaic( 13 );

						imageWasModified();
						redoStack.clear();
						updateUndoRedoButtons();
						repaint();
					} 

				}
			}
		);		

		flipButton.addActionListener(
			new ActionListener () {
				public void actionPerformed( ActionEvent e) {

					if (bmp != null) {
						
						saveTempImage();
						bmp.flipVertically();

						imageWasModified();
						redoStack.clear();
						updateUndoRedoButtons();
						repaint();
					} 

				}
			}
		);

		blurButton.addActionListener(
			new ActionListener () {
				public void actionPerformed( ActionEvent e) {

					if (bmp != null) {
						
						saveTempImage();

						bmp.blur(1);

						imageWasModified();
						redoStack.clear();
						updateUndoRedoButtons();
						repaint();

					} 

				}
			}
		);

		enhanceButton.addActionListener(
			new ActionListener () {
				public void actionPerformed( ActionEvent e) {

					if (bmp != null ) {
						
						saveTempImage();

						bmp.enhanceColor( 0.75f, 1.5f , 2f );

						imageWasModified();
						redoStack.clear();
						updateUndoRedoButtons();
						repaint();

					} 

				}
			}
		);

		combineButton.addActionListener(
			new ActionListener () {
				public void actionPerformed( ActionEvent e) {

					// saveTempImage(); 

					System.out.println("Combine - not yet implemented");

					// redoStack.clear();
					// updateUndoRedoButtons();
					// imageWasModified();
					// repaint();

				}
			}
		);

		rotateButton.addActionListener(
			new ActionListener () {
				public void actionPerformed( ActionEvent e) {

					if (bmp != null ) {
						
						saveTempImage();

						bmp.rotateCounterClockwise();

						canvas.refreshSize();
						pack();

						imageWasModified();
						redoStack.clear();
						updateUndoRedoButtons();
						repaint();

					} 

				}
			}
		);

		grayscaleButton.addActionListener(
			new ActionListener () {
				public void actionPerformed( ActionEvent e) {

					if (bmp != null ) {
						
						saveTempImage();

						bmp.grayscale(1.0f);

						imageWasModified();
						redoStack.clear();
						updateUndoRedoButtons();
						repaint();

					} 

				}
			}
		);

	 }

	 /**
	 * Handle all actions involving the undo/redo buttons
	 **/
	private void addUndoRedoButtons() {

		Container cPane = this.getContentPane();

		JPanel buttonPanel = new JPanel( );

		undoButton = new JButton("Undo");
		redoButton = new JButton("Redo");

		undoButton.setEnabled(false);
		redoButton.setEnabled(false);

		buttonPanel.add(undoButton);
		buttonPanel.add(redoButton);

		cPane.add(buttonPanel, BorderLayout.NORTH);


		undoButton.addActionListener(
			new ActionListener () {
				public void actionPerformed( ActionEvent event) {
					
					undo();

				}
			}
		);


		redoButton.addActionListener(
			new ActionListener () {
				public void actionPerformed(ActionEvent event) {

					redo();

				}
			}
		);		


	} // addUndoRedoButtons

	private void undo() {

		redoStack.push(bmp);
		
		bmp = undoStack.pop();

		imageWasModified();
		repaint();

	}

	private void undoAll() {

		while (!undoStack.isEmpty()) {
			redoStack.push(bmp);
			bmp = undoStack.pop();
		}

		imageWasModified();
		repaint();

	}

	private void redo() {

		undoStack.push(bmp);

		bmp = redoStack.pop();

		imageWasModified();
		repaint();

	}

	 /** 
	 * Opens up a JFileChooser for the user to choose a file from their file system.
	 * @return - a file that the user selected on their computer, or null if they didn't choose anything
	 */

	private File selectFile (String title) {

			/* Set up the file chooser */

		JFileChooser fileChooser = new JFileChooser();

			/* Filter .BMP files */

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Bitmap Files", "bmp", "BMP");
		fileChooser.setFileFilter(filter);

			/* Begin at the most recently accessed directory */

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
	* The list of images accessible by pressing "Redo" are also cleared.
	*/
	private void imageWasModified() {

		modified = true;
		updateTitle();

		updateUndoRedoButtons();

	}

	private void updateUndoRedoButtons() {

		undoButton.setEnabled(undoStack.size() != 0);
		redoButton.setEnabled(redoStack.size() != 0);

		undoMenuItem.setEnabled(undoStack.size() != 0);
		undoAllMenuItem.setEnabled(undoStack.size() != 0);
		redoMenuItem.setEnabled(redoStack.size() != 0);

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

	/** Make a new thread that monitors when the program quits **/
	private static void cleanupBeforeProgramQuits() {

	    // The code within this will execute when the program exits for good
	    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() { 
	        public void run() {

				System.out.println("Program exited");	            

	        }
	    }));
	}

	/** PRIVATE INNER CLASS **/
	private class Canvas extends JPanel {


			/* Constants */
		
		private final int DEFAULT_HEIGHT  = 300;
		private final int DEFAULT_WIDTH   = 300;
		private final int DEFAULT_PADDING = 75; 

			/* Variables */

		private int horizontal_padding, vertical_padding;

	 	public Canvas () {

	 		setVisible(true);
	 		horizontal_padding = DEFAULT_PADDING;
	 		vertical_padding = DEFAULT_PADDING;

	 	}

	 	/**
	 	* Returns the dimensions that the canvas should be, taking into consideration
	 	* the image size and padding.
	 	*/
	 	@Override public Dimension getPreferredSize() {

	 		return bmp == null 	? new Dimension(DEFAULT_WIDTH + horizontal_padding*2, DEFAULT_HEIGHT + vertical_padding*2)
	 							: new Dimension(bmp.getWidth() + horizontal_padding*2, bmp.getHeight() + vertical_padding*2);
	 	
	 	}

	 	/**
	 	* Updates the canvas, drawing the image (or blank canvas) in the center,
	 	* with pre-defined padding around it.
	 	* @param g - Graphics object (which is passed through by Java)
	 	*/
	 	@Override protected void paintComponent(Graphics g) {

	 		super.paintComponent(g);

	 		refreshSize();

	 			/* Draw blank canvas */
	 		
	 		if (bmp == null) {
	 			g.setColor(Color.LIGHT_GRAY);
		 		g.fillRect(horizontal_padding, vertical_padding, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	 		}

	 			/* Draw image */

	 		else
	 			g.drawImage(bmp.getImage(), horizontal_padding, vertical_padding, null);

	 	}

	 	public void refreshSize() {
	 		
	 		int width  = (bmp == null) ? DEFAULT_WIDTH  : bmp.getWidth();
		    int height = (bmp == null) ? DEFAULT_HEIGHT : bmp.getHeight();

	 		setSize(Math.max(width, getWidth()), Math.max(height, getHeight()));

			horizontal_padding = (getWidth()  - width)  / 2;
			vertical_padding   = (getHeight() - height) / 2;

	 	}
	 }

}