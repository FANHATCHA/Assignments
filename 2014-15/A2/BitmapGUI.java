import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;

/*

	TO-DO:

	- Swirl Effect?
	- Refactor
	- Celebrate, we're done!

*/

public class BitmapGUI extends JFrame implements ActionListener {

		/* Constants */

	private static final String TITLE = "BitmapHacker";
	
	private enum BUTTON {
		FLIP, BLUR, ENHANCE, COMBINE, ROTATE, GRAYSCALE, EDGE, MOSAIC
	}

		/* Variables */

	private File mostRecentInputFile;
	private boolean modified;
	private Bitmap bmp;

		/* Components */

	private JMenuItem closeMenuItem, saveMenuItem, saveAsMenuItem, undoAllMenuItem, undoMenuItem, redoMenuItem;
	
	private JButton undoButton, redoButton,
					applyButton;

	private JRadioButton flipButton, blurButton, enhanceButton, combineButton, rotateButton, grayscaleButton, edgeButton, mosaicButton;

	private Canvas canvas;

	private Stack<Bitmap> 	undoStack = new Stack<Bitmap>(),
							redoStack = new Stack<Bitmap>();

	private JSlider sliderOne, sliderTwo, sliderThree;
	private JPanel optionPanel;
	private BUTTON selectedButton = BUTTON.FLIP;

	public static void main(String[] args) {
		
		BitmapGUI frame = new BitmapGUI();

	}

	public BitmapGUI () {

			/* Add a canvas to the center of the window */

		canvas = new Canvas();
	 	add(canvas, BorderLayout.CENTER);

	 		/* Add the menu, buttons, and update the window's title */

	 	addMenu();
		addOptionPanel();
		addButtons();

		trackWindowSize();

			/* Show the user our wonderful GUI */
		
		pack();
		setGUIproperties();

	}

	/**
	* Check to see if an image exists on the canvas.
	* @return true if there is an image on the canvas
	*/
	private boolean imageExists () {
		return bmp != null;
	}

	/**
	* Set some default GUI Properties
	*/
	private void setGUIproperties() {
		
		// Ensure our application will be closed when the user presses the "X" */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Sets screen location in the center of the screen (only works after calling pack)
		setLocationRelativeTo(null);

		// Update Title
		updateTitle();

		// Show Screen
		setVisible(true);

	}

	private void addOptionPanel () {

		// New grid layout with Four rows and One column
		GridLayout optionPanelLayout = new GridLayout(4, 1);

		optionPanel = new JPanel( optionPanelLayout );

		// Create Apply Button
		applyButton = new JButton("Apply Effect");
		applyButton.setFont( new Font("Verdana", Font.BOLD + Font.ITALIC, 14) );


		// By default slider value is set to 50 in an interval of [1, 100]
		sliderOne   = new JSlider(1, 100);
		sliderTwo   = new JSlider(1, 100);
		sliderThree = new JSlider(1, 100);
		sliderOne.setValue(50);
		sliderTwo.setValue(50);
		sliderThree.setValue(50);

		// Add components right side of the screen
		optionPanel.add(sliderOne);
		optionPanel.add(sliderTwo);
		optionPanel.add(sliderThree );
		optionPanel.add(applyButton);

		setEnabledSliders(false);

		// Add Panel to the right side of the screen
		add(optionPanel, BorderLayout.EAST);

	}

	/**
	* Enable/disable the sliders as requested.
	* @param enabled - true if the sliders should be enabled
	*/
	private void setEnabledSliders(boolean enabled) {

		sliderOne.setEnabled(enabled);
		sliderTwo.setEnabled(enabled);
		sliderThree.setEnabled(enabled);

	}

	/**
	* Enable/disable the radio buttons as requested.
	* @param enabled - true if the radio buttons should be enabled
	*/
	private void setEnabledButtons(boolean enabled) {

		flipButton.setEnabled(enabled);
		blurButton.setEnabled(enabled);
		enhanceButton.setEnabled(enabled);
		combineButton.setEnabled(enabled);
		rotateButton.setEnabled(enabled);
		grayscaleButton.setEnabled(enabled);
		edgeButton.setEnabled(enabled);
		mosaicButton.setEnabled(enabled);
		applyButton.setEnabled(enabled);

		updateUndoRedoButtons();

	}

	/**
	 * Saves the current BitMap file into a temporary image folder
	 */
	private void saveTempImage( ) {

		// ∃ an image to save
		if (imageExists()) {
			try {

				undoStack.push((Bitmap) bmp.clone());

			} catch (CloneNotSupportedException e) {
				System.err.println("Houston we've got a problem. The Bitmap could not be cloned!");

			} catch (OutOfMemoryError e) {
				JOptionPane.showMessageDialog(null, "Memory exceeded! Clearing all undo/redos.", "Warning", JOptionPane.WARNING_MESSAGE);
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
		 			
		 			File newFile = saveFile("Choose name for new image");

		 			if (newFile != null)
		 				mostRecentInputFile = newFile;

		 		case "Save":

		 			bmp.writeBitmap(mostRecentInputFile);
		 			modified = false;
		 			break;

				case "Open...":
				 
					File openedFile = selectFile("Pick your .BMP");

					if (openedFile != null) {

					 	redoStack.clear();
					 	undoStack.clear();

						setEnabledButtons(true);

						bmp = new Bitmap(mostRecentInputFile = openedFile);
						modified = false;
						canvas.refreshSize();	

					}

		 			break;

		 		case "Close":

		 			redoStack.clear();
				 	undoStack.clear();
		 			
		 			setEnabledButtons(false);
		 			setEnabledSliders(false);

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
	 * Adds many buttons to the screen, allowing the user to apply
	 * various visual effects to the image.
	 */
	
	private void addButtons() {

		JPanel buttonPanel = new JPanel();
		ButtonGroup buttonGroup = new ButtonGroup();

		flipButton      = new JRadioButton("Flip");
		blurButton      = new JRadioButton("Blur");
		enhanceButton   = new JRadioButton("Enhance");
		combineButton   = new JRadioButton("Combine");
		rotateButton    = new JRadioButton("Rotate");
		grayscaleButton = new JRadioButton("Grayscale");
		edgeButton      = new JRadioButton("Edge");
		mosaicButton    = new JRadioButton("Mosaic");

		// By default the first button (flipButton) will be selected
		flipButton.setSelected(true);

		buttonGroup.add(flipButton);
		buttonGroup.add(blurButton);
		buttonGroup.add(enhanceButton);
		buttonGroup.add(combineButton);
		buttonGroup.add(rotateButton);
		buttonGroup.add(grayscaleButton);
		buttonGroup.add(edgeButton);
		buttonGroup.add(mosaicButton);

		addUndoRedoButtons();

		// Set all disabled since there is not image yet
		setEnabledButtons(false);
		setEnabledSliders(false);

		// Add buttons to panel
		buttonPanel.add(flipButton);
		buttonPanel.add(blurButton);
		buttonPanel.add(enhanceButton);
		buttonPanel.add(combineButton);
		buttonPanel.add(rotateButton);
		buttonPanel.add(grayscaleButton);
		buttonPanel.add(edgeButton);
		buttonPanel.add(mosaicButton);

		add(buttonPanel, BorderLayout.SOUTH);

		// -------------------------------------------------------------------------------- //
		// -------------------------------------------------------------------------------- //
		/*                              Add Action Listeners                                */
		// -------------------------------------------------------------------------------- //
		// -------------------------------------------------------------------------------- //

		applyButton.addActionListener( new ActionListener () {
			public void actionPerformed( ActionEvent e) {

				if (imageExists()) {
					
					saveTempImage();

					switch (selectedButton) {

						case FLIP:
							bmp.flipVertically();
							break;

						case BLUR:

							// Blur range between 1-10
							int blurValue = sliderOne.getValue() / 10;
							bmp.blur(blurValue);
							break;
						
						case ENHANCE:

							// RGB values can range from 0-200%
							float redVal   = (sliderOne.getValue()*2)   / 100f;
							float greenVal = (sliderTwo.getValue()*2)   / 100f;
							float blueVal  = (sliderThree.getValue()*2) / 100f;

							bmp.enhanceColor(redVal, greenVal , blueVal);
							break;

						case COMBINE:
	
							File openedFile = selectFile("Pick your .BMP");

							if (openedFile != null) {
								try {
									Bitmap newBmp = new Bitmap( openedFile );
									bmp = Bitmap.combine(bmp , newBmp );
									pack();							
								} catch (IOException event) {
									System.err.println("Ooops! IOError has occured while combining images");
								}
							}

							break;
						
						case ROTATE:

							bmp.rotateCounterClockwise();
							canvas.refreshSize();
							pack();
							break;
						
						case GRAYSCALE:

							// Grayscale percentage between 0-100%
							float grayValue = sliderOne.getValue() / 100f;
							bmp.grayscale(grayValue);
							break;
						
						case EDGE:

							// Precision between 0-20
							float edgeValue = sliderOne.getValue() / 5.0f;
							bmp.edgeDetection( edgeValue ); 
							break;
						
						case MOSAIC:

							// Mosiac cellsize value between 1-50
							int mosaicValue = Math.max( 1, sliderOne.getValue()/2 );
							bmp.mosaic( mosaicValue );
							break;

					}

					imageWasModified();
					redoStack.clear();
					updateUndoRedoButtons();
					repaint();
				} 

			}
		});



		flipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (imageExists()) {
					
					setEnabledSliders(false);
					selectedButton = BUTTON.FLIP;

				} 

			}
		});

		blurButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (imageExists()) {
					
					setEnabledSliders(false);
					sliderOne.setEnabled(true);
					sliderOne.setValue(5);
					selectedButton = BUTTON.BLUR;

				} 

			}
		});

		enhanceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (imageExists() ) {
					
					setEnabledSliders(true);

					sliderOne.setValue(50);
					sliderTwo.setValue(50);
					sliderThree.setValue(50);

					selectedButton = BUTTON.ENHANCE;

				} 

			}
		});

		combineButton.addActionListener( new ActionListener () {
			public void actionPerformed( ActionEvent e) {

				setEnabledSliders(false);
				selectedButton = BUTTON.COMBINE;

			}
		});

		rotateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (imageExists() ) {
					
					setEnabledSliders(false);
					selectedButton = BUTTON.ROTATE;

				} 

			}
		});

		grayscaleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (imageExists() ) {
					
					setEnabledSliders(false);
					sliderOne.setEnabled(true);
					sliderOne.setValue(100); 

					selectedButton = BUTTON.GRAYSCALE;
				} 

			}
		});


		edgeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (imageExists()) {
					
					setEnabledSliders(false);
					sliderOne.setEnabled(true);
					sliderOne.setValue(50);
					selectedButton = BUTTON.EDGE;

				} 

			}
		});
		

		mosaicButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (imageExists()) {
					
					setEnabledSliders(false);
					sliderOne.setEnabled(true);
					sliderOne.setValue(50);
					selectedButton = BUTTON.MOSAIC;
				} 

			}
		});	


	 }

	 /**
	 * Create the undo/redo buttons and all corresponding actions.
	 **/
	private void addUndoRedoButtons() {

		JPanel buttonPanel = new JPanel( );

		undoButton = new JButton("Undo");
		redoButton = new JButton("Redo");

		undoButton.setEnabled(false);
		redoButton.setEnabled(false);

		buttonPanel.add(undoButton);
		buttonPanel.add(redoButton);

		add(buttonPanel, BorderLayout.NORTH);

		undoButton.addActionListener(
			new ActionListener() {

				public void actionPerformed(ActionEvent event) {
					undo();

				}
			}
		);


		redoButton.addActionListener(
			new ActionListener() {

				public void actionPerformed(ActionEvent event) {
					redo();

				}
			}
		);		


	} // addUndoRedoButtons

	/**
	* Undo the last image manipulation.
	*/
	private void undo() {

		redoStack.push(bmp);
		
		bmp = undoStack.pop();

		imageWasModified();
		repaint();

	}

	/**
	* Undo all image manipulations.
	*/
	private void undoAll() {

		while (!undoStack.isEmpty()) {
			redoStack.push(bmp);
			bmp = undoStack.pop();
		}

		imageWasModified();
		repaint();

	}

	/**
	* Redo the last image manipulation that was un-done.
	*/
	private void redo() {

		undoStack.push(bmp);

		bmp = redoStack.pop();

		imageWasModified();
		repaint();

	}

	/**
	* This method should be called whenever a picture manipulation occurs.
	* The title of the window changes to indicate to the user that the image has changed.
	*/
	private void imageWasModified() {

		modified = true;
		updateTitle();

		updateUndoRedoButtons();

	}

	/**
	* Enables/disables the undo/redo buttons and menu items appropriately.
	*/
	private void updateUndoRedoButtons() {

		undoButton.setEnabled(undoStack.size() != 0);
		redoButton.setEnabled(redoStack.size() != 0);

		undoMenuItem.setEnabled(undoStack.size() != 0);
		undoAllMenuItem.setEnabled(undoStack.size() != 0);
		redoMenuItem.setEnabled(redoStack.size() != 0);

	}

	/**
	* Updates the title of the window depending on whether or not an image
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

/** FILE SELECTION **/

	/**
	 * Prompts the user to name and specify a file they wish to save
	 * @return - A File object to which data can be saved
	 */
	private File saveFile(String title) {

			/* Set up the file chooser */

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle(title);

			/* Begin at the most recently accessed directory */

		if (mostRecentInputFile != null)
			fileChooser.setCurrentDirectory(mostRecentInputFile.getParentFile());

			/* Prompt user to select a file */

		fileChooser.showSaveDialog(null);

			/* User pressed cancel, so there was no file */

		if (fileChooser.getSelectedFile() == null)
			return null;

			/* Add .BMP extension if the user didn't put it there */

		String name = fileChooser.getSelectedFile().getName();

		// Remove anything after the period
		if (name.indexOf(".") != -1)
			name = name.substring(0, name.indexOf("."));

		return new File(fileChooser.getSelectedFile().getParentFile() + "/" + name + ".bmp");
		
	}

	 /** 
	 * Opens up a JFileChooser for the user to choose a file from their file system.
	 * @return - a file that the user selected on their computer, or null if they didn't choose anything
	 */
	private File selectFile (String title) {

			/* Set up the file chooser */

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle(title);

			/* Filter .BMP files */

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Bitmap Files", "bmp", "BMP");
		fileChooser.setFileFilter(filter);

			/* Begin at the most recently accessed directory */

		if (mostRecentInputFile != null)
			fileChooser.setCurrentDirectory(mostRecentInputFile.getParentFile());

			/* Prompt user to select a file, returning it */

		fileChooser.showOpenDialog(null);

		return fileChooser.getSelectedFile();
		
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