/**
* BitmapGUI
*
* Data Structures II : Assignment 2
* @author Micah Stairs and William Fiset
*/

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.geom.*;

public class BitmapGUI extends JFrame implements ActionListener {

		/* Constants */

	private static final String TITLE = "BitmapHacker";
	
	private enum BUTTON {
		NONE, FLIP, BLUR, ENHANCE, COMBINE, ROTATE, GRAYSCALE, EDGE, MOSAIC, INVERT, SWIRL
	}

		/* Variables */

	private File mostRecentInputFile;
	private boolean modified;
	private Bitmap bmp;

	private Stack<Bitmap> undoStack = new Stack<Bitmap>(), redoStack = new Stack<Bitmap>();

		/* Components */

	private JMenuItem closeMenuItem, saveMenuItem, saveAsMenuItem, undoAllMenuItem, undoMenuItem, redoMenuItem;
	
	private JButton undoButton, redoButton,
					applyButton;

	private Canvas canvas;

	private EffectOption sliderOne, sliderTwo, sliderThree;
	private JPanel optionPanel;

	private ButtonGroup buttonGroup;
	private BUTTON selectedButton = BUTTON.NONE;
	private JRadioButton flipButton, blurButton, enhanceButton, combineButton, rotateButton,
						 grayscaleButton, edgeButton, mosaicButton, invertButton, swirlButton;


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

	/**
	* Add a panel to the side of the screen with effect options.
	*/
	private void addOptionPanel () {

		// New grid layout with Four rows and One column
		GridLayout optionPanelLayout = new GridLayout(4, 1);

		optionPanel = new JPanel(optionPanelLayout);
		optionPanel.setBorder(new EmptyBorder(10, 10, 10, 10) );

		// Create sliders
		sliderOne   = new EffectOption();
		sliderOne.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				saveTempImage();

				switch (selectedButton) {

					case FLIP:

						bmp.flipHorizontally();
						break;
					
					case ROTATE:

						// Rotate clockwise 90 degrees
						bmp.rotateCounterClockwise();
						bmp.rotateCounterClockwise();
						bmp.rotateCounterClockwise();

						canvas.refreshSize();
						pack();
						break;

				}

				
				redoStack.clear();
				updateUndoRedoButtons();
				imageWasModified();

			} 

		});


		sliderTwo   = new EffectOption();
		sliderTwo.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				saveTempImage();

				switch (selectedButton) {

					case FLIP:

						bmp.flipVertically();
						break;
					
					case ROTATE:

						// Rotate 180 degrees
						bmp.rotateCounterClockwise();
						bmp.rotateCounterClockwise();

						canvas.refreshSize();
						pack();
						break;

				}

				
				redoStack.clear();
				updateUndoRedoButtons();
				imageWasModified();

			} 

		});

		sliderThree = new EffectOption();
		sliderThree.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				saveTempImage();

				switch (selectedButton) {
					
					case ROTATE:

						// Rotate counter-clockwise 90 degrees
						bmp.rotateCounterClockwise();
						
						canvas.refreshSize();
						pack();
						break;

				}

				
				redoStack.clear();
				updateUndoRedoButtons();
				imageWasModified();

			} 

		});


		// Create Apply Button
		applyButton = new JButton("Apply Effect");
		applyButton.setFont(new Font("Verdana", Font.BOLD, 14));
		applyButton.setPreferredSize(new Dimension(300, 100));
		applyButton.setEnabled(false);

		// Add components right side of the screen
		optionPanel.add(sliderOne);
		optionPanel.add(sliderTwo);
		optionPanel.add(sliderThree );
		optionPanel.add(applyButton);
		add(optionPanel, BorderLayout.EAST);

	}


	/**
	* Hide sliders, and activate buttons
	*/
	private void switchToButtons() {

		applyButton.setEnabled(false);

		sliderOne.switchToButton();
		sliderTwo.switchToButton();
		sliderThree.switchToButton();

	}

	/**
	* Hide buttons, and activate sliders
	*/
	private void switchToSliders() {

		applyButton.setEnabled(true);

		sliderOne.switchToSlider();
		sliderTwo.switchToSlider();
		sliderThree.switchToSlider();

	}

	/**
	* Show/hide the sliders as requested.
	* @param visible - true if the sliders should be made visible
	*/
	private void setVisibleEffectOptions(boolean visible) {

		sliderOne.setVisible(visible);
		sliderTwo.setVisible(visible);
		sliderThree.setVisible(visible);

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
		invertButton.setEnabled(enabled);
		swirlButton.setEnabled(enabled);

		updateUndoRedoButtons();

	}

	/**
	 * Saves the current BitMap file into a temporary image folder
	 */
	private void saveTempImage( ) {

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
		 			setVisibleEffectOptions(false);
		 			applyButton.setEnabled(false);
		 			selectedButton = BUTTON.NONE;
		 			buttonGroup.clearSelection();

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

		JPanel buttonPanel = new JPanel(new GridLayout(2, 5));
		buttonGroup = new ButtonGroup();

		flipButton      = new JRadioButton("Flip");
		blurButton      = new JRadioButton("Blur");
		enhanceButton   = new JRadioButton("Enhance");
		combineButton   = new JRadioButton("Combine");
		rotateButton    = new JRadioButton("Rotate");
		grayscaleButton = new JRadioButton("Grayscale");
		edgeButton      = new JRadioButton("Edge Detection");
		mosaicButton    = new JRadioButton("Mosaic");
		invertButton    = new JRadioButton("Invert");
		swirlButton     = new JRadioButton("Swirl");

		buttonGroup.add(flipButton);
		buttonGroup.add(blurButton);
		buttonGroup.add(enhanceButton);
		buttonGroup.add(combineButton);
		buttonGroup.add(rotateButton);
		buttonGroup.add(grayscaleButton);
		buttonGroup.add(edgeButton);
		buttonGroup.add(mosaicButton);
		buttonGroup.add(invertButton);
		buttonGroup.add(swirlButton);

		addUndoRedoButtons();

		// Set all disabled since there is not image yet
		setEnabledButtons(false);
		setVisibleEffectOptions(false);

		// Add buttons to panel
		buttonPanel.add(flipButton);
		buttonPanel.add(blurButton);
		buttonPanel.add(enhanceButton);
		buttonPanel.add(combineButton);
		buttonPanel.add(rotateButton);
		buttonPanel.add(grayscaleButton);
		buttonPanel.add(edgeButton);
		buttonPanel.add(mosaicButton);
		buttonPanel.add(invertButton);
		buttonPanel.add(swirlButton);

		add(buttonPanel, BorderLayout.SOUTH);

		// -------------------------------------------------------------------------------- //
		// -------------------------------------------------------------------------------- //
		/*                              Add Action Listeners                                */
		// -------------------------------------------------------------------------------- //
		// -------------------------------------------------------------------------------- //

		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				saveTempImage();

				switch (selectedButton) {

					case BLUR:

						// Blur can range from 1 to 11
						bmp.blur(sliderOne.slider.getValue());
						break;
					
					case ENHANCE:

						// RGB values can range from 0-200%
						float redVal   = (sliderOne.slider.getValue())   / 100f;
						float greenVal = (sliderTwo.slider.getValue())   / 100f;
						float blueVal  = (sliderThree.slider.getValue()) / 100f;

						bmp.enhanceColor(redVal, greenVal , blueVal);
						break;

					case COMBINE:

						File openedFile = selectFile("Pick your .BMP");

						if (openedFile != null) {
							try {
								Bitmap newBmp = new Bitmap(openedFile);
								bmp = Bitmap.combine(bmp, newBmp);
								pack();							
							} catch (IOException event) {
								System.err.println("Ooops! IOException has occured while combining images.");
							}
						}

						break;
					
					case GRAYSCALE:

						// Grayscale percentage between 0-100%
						float grayValue = sliderOne.slider.getValue() / 100f;
						bmp.grayscale(grayValue);
						break;
					
					case EDGE:

						// Precision between 0-20
						bmp.edgeDetection(20 - sliderOne.slider.getValue()); 
						break;
					
					case MOSAIC:

						// Mosiac cellsize value between 2-52
						bmp.mosaic(sliderOne.slider.getValue());
						break;

					case INVERT:

						bmp.invert();
						break;

					case SWIRL:

						// Values can range from 0-100%
						float x = (sliderOne.slider.getValue()) / 100f;
						float y = (sliderTwo.slider.getValue()) / 100f;

						bmp.swirl(x, y, sliderThree.slider.getValue());
						break;

				} // switch

				
				redoStack.clear();
				updateUndoRedoButtons();
				imageWasModified();

			} 

		});

		flipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				switchToButtons();

				sliderOne.button.setText("Flip Horizontally");
				sliderTwo.button.setText("Flip Vertically");

				setVisibleEffectOptions(true);
				sliderThree.setVisible(false);
				
				selectedButton = BUTTON.FLIP;

				repaint();

			}
		});

		blurButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				switchToSliders();
					
				// Set enabled
				setVisibleEffectOptions(false);
				sliderOne.setVisible(true);

				// Set title
				sliderOne.label.setText("Range:");
				
				// Set values
				sliderOne.slider.setMinimum(1);
				sliderOne.slider.setMaximum(11);
				sliderOne.slider.setValue(6);

				// Add ticks
				sliderOne.slider.setSnapToTicks(true);
				sliderOne.slider.setMinorTickSpacing(1);
				sliderOne.slider.setMajorTickSpacing(0);
				sliderOne.slider.setPaintTicks(true);

				// Add labels
				sliderOne.slider.setPaintLabels(true);
				Hashtable<Integer, JLabel> dict = new Hashtable<Integer, JLabel>();
			    dict.put(new Integer(1), new JLabel("Less"));
			    dict.put(new Integer(11), new JLabel("More"));
			    sliderOne.slider.setLabelTable(dict);

				selectedButton = BUTTON.BLUR;

				pack();
				repaint();

			}
		});

		enhanceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				switchToSliders();

				// Set enabled
				setVisibleEffectOptions(true);

				// Set titles
				sliderOne.label.setText("Red:");
				sliderTwo.label.setText("Blue:");
				sliderThree.label.setText("Green:");
				
				// Set values
				sliderOne.slider.setMinimum(0);
				sliderOne.slider.setMaximum(200);
				sliderOne.slider.setValue(100);
				sliderTwo.slider.setMinimum(0);
				sliderTwo.slider.setMaximum(200);
				sliderTwo.slider.setValue(100);
				sliderThree.slider.setMinimum(0);
				sliderThree.slider.setMaximum(200);
				sliderThree.slider.setValue(100);

				// Add ticks
				sliderOne.slider.setSnapToTicks(true);
				sliderOne.slider.setMinorTickSpacing(10);
				sliderOne.slider.setMajorTickSpacing(50);
				sliderOne.slider.setPaintTicks(true);
				sliderTwo.slider.setSnapToTicks(true);
				sliderTwo.slider.setMinorTickSpacing(10);
				sliderTwo.slider.setMajorTickSpacing(50);
				sliderTwo.slider.setPaintTicks(true);
				sliderThree.slider.setSnapToTicks(true);
				sliderThree.slider.setMinorTickSpacing(10);
				sliderThree.slider.setMajorTickSpacing(50);
				sliderThree.slider.setPaintTicks(true);

				// Add labels
				sliderOne.slider.setPaintLabels(true);
				sliderTwo.slider.setPaintLabels(true);
				sliderThree.slider.setPaintLabels(true);
				Hashtable<Integer, JLabel> dict = new Hashtable<Integer, JLabel>();
				for (int i = 0; i <= 5; i++)
					dict.put(new Integer(i * 50), new JLabel(String.valueOf(i * 50) + "%"));
			    sliderOne.slider.setLabelTable(dict);
			    sliderTwo.slider.setLabelTable(dict);
			    sliderThree.slider.setLabelTable(dict);

				selectedButton = BUTTON.ENHANCE;

				pack();
				repaint();

			}
		});

		combineButton.addActionListener( new ActionListener () {
			public void actionPerformed( ActionEvent e) {

				switchToSliders();

				setVisibleEffectOptions(false);
				selectedButton = BUTTON.COMBINE;

				repaint();

			}
		});

		rotateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				switchToButtons();

				sliderOne.button.setText("Rotate 90 degrees");
				sliderTwo.button.setText("Rotate 180 degrees");
				sliderThree.button.setText("Rotate 270 degrees");

				setVisibleEffectOptions(true);
				selectedButton = BUTTON.ROTATE;

				repaint();

			}
		});

		grayscaleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				switchToSliders();

				// Set enabled
				setVisibleEffectOptions(false);
				sliderOne.setVisible(true);

				// Set title
				sliderOne.label.setText("Factor:");
				
				// Set values
				sliderOne.slider.setMinimum(0);
				sliderOne.slider.setMaximum(100);
				sliderOne.slider.setValue(100);

				// Add ticks
				sliderOne.slider.setSnapToTicks(true);
				sliderOne.slider.setMinorTickSpacing(10);
				sliderOne.slider.setMajorTickSpacing(0);
				sliderOne.slider.setPaintTicks(true);

				// Add labels
				sliderOne.slider.setPaintLabels(true);
				Hashtable<Integer, JLabel> dict = new Hashtable<Integer, JLabel>();
			    dict.put(new Integer(1), new JLabel("Color"));
			    dict.put(new Integer(100), new JLabel("Grayscale"));
			    sliderOne.slider.setLabelTable(dict);

				selectedButton = BUTTON.GRAYSCALE;

				pack();
				repaint();

			}
		});


		edgeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				switchToSliders();

				// Set enabled
				setVisibleEffectOptions(false);
				sliderOne.setVisible(true);

				// Set title
				sliderOne.label.setText("Factor:");
				
				// Set values
				sliderOne.slider.setMinimum(0);
				sliderOne.slider.setMaximum(20);
				sliderOne.slider.setValue(10);

				// Add ticks
				sliderOne.slider.setSnapToTicks(true);
				sliderOne.slider.setMinorTickSpacing(2);
				sliderOne.slider.setMajorTickSpacing(0);
				sliderOne.slider.setPaintTicks(true);

				// Add labels
				sliderOne.slider.setPaintLabels(true);
				Hashtable<Integer, JLabel> dict = new Hashtable<Integer, JLabel>();
			    dict.put(new Integer(0), new JLabel("Less"));
			    dict.put(new Integer(20), new JLabel("More"));
			    sliderOne.slider.setLabelTable(dict);

				selectedButton = BUTTON.EDGE;

				pack();
				repaint();

			}
		});
		

		mosaicButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				switchToSliders();

				// Set enabled
				setVisibleEffectOptions(false);
				sliderOne.setVisible(true);

				// Set title
				sliderOne.label.setText("Factor:");
				
				// Set values
				sliderOne.slider.setMinimum(2);
				sliderOne.slider.setMaximum(52);
				sliderOne.slider.setValue(5);

				// Add ticks
				sliderOne.slider.setSnapToTicks(false);
				sliderOne.slider.setMinorTickSpacing(5);
				sliderOne.slider.setMajorTickSpacing(0);
				sliderOne.slider.setPaintTicks(true);

				// Add labels
				sliderOne.slider.setPaintLabels(true);
				Hashtable<Integer, JLabel> dict = new Hashtable<Integer, JLabel>();
			    dict.put(new Integer(2), new JLabel("Less"));
			    dict.put(new Integer(52), new JLabel("More"));
			    sliderOne.slider.setLabelTable(dict);

				selectedButton = BUTTON.MOSAIC;

				pack();
				repaint();

			}
		});

		invertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				switchToSliders();

				setVisibleEffectOptions(false);
				selectedButton = BUTTON.INVERT;

				repaint();

			}
		});

		swirlButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				switchToSliders();

				// Set enabled
				setVisibleEffectOptions(true);

				// Set titles
				sliderOne.label.setText("X:");
				sliderTwo.label.setText("Y:");
				sliderThree.label.setText("Range:");
				
				// Set values
				sliderOne.slider.setMinimum(0);
				sliderOne.slider.setMaximum(100);
				sliderOne.slider.setValue(50);
				sliderTwo.slider.setMinimum(0);
				sliderTwo.slider.setMaximum(100);
				sliderTwo.slider.setValue(50);
				sliderThree.slider.setMinimum(0);
				sliderThree.slider.setMaximum(10000);
				sliderThree.slider.setValue(5000);

				// Add ticks
				sliderOne.slider.setSnapToTicks(false);
				sliderOne.slider.setMinorTickSpacing(10);
				sliderOne.slider.setMajorTickSpacing(0);
				sliderOne.slider.setPaintTicks(true);
				sliderTwo.slider.setSnapToTicks(false);
				sliderTwo.slider.setMinorTickSpacing(10);
				sliderTwo.slider.setMajorTickSpacing(0);
				sliderTwo.slider.setPaintTicks(true);
				sliderThree.slider.setSnapToTicks(false);
				sliderThree.slider.setMinorTickSpacing(1000);
				sliderThree.slider.setMajorTickSpacing(0);
				sliderThree.slider.setPaintTicks(true);

				// Add labels
				sliderOne.slider.setPaintLabels(true);
				Hashtable<Integer, JLabel> dict1 = new Hashtable<Integer, JLabel>();
				dict1.put(0, new JLabel("Left"));
				dict1.put(100, new JLabel("Right"));
			    sliderOne.slider.setLabelTable(dict1);

			    sliderTwo.slider.setPaintLabels(true);
				Hashtable<Integer, JLabel> dict2 = new Hashtable<Integer, JLabel>();
				dict2.put(0, new JLabel("Bottom"));
				dict2.put(100, new JLabel("Top"));
			    sliderTwo.slider.setLabelTable(dict2);

			    sliderThree.slider.setPaintLabels(true);
				Hashtable<Integer, JLabel> dict3 = new Hashtable<Integer, JLabel>();
				dict3.put(0, new JLabel("Less"));
				dict3.put(10000, new JLabel("More"));
			    sliderThree.slider.setLabelTable(dict3);

				selectedButton = BUTTON.SWIRL;

				pack();
				repaint();

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

		undoButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					undo();
				}
			}
		);

		redoButton.addActionListener(new ActionListener() {
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

	}

	/**
	* Redo the last image manipulation that was un-done.
	*/
	private void redo() {

		undoStack.push(bmp);

		bmp = redoStack.pop();

		imageWasModified();

	}

	/**
	* This method should be called whenever a picture manipulation occurs.
	* The title of the window changes to indicate to the user that the image has changed.
	*/
	private void imageWasModified() {

		modified = true;
		updateTitle();

		updateUndoRedoButtons();

		repaint();

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
	 	* @return preferred dimension
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

	 			// Draw canvas
	 			g.setColor(Color.LIGHT_GRAY);
		 		g.fillRect(horizontal_padding, vertical_padding, DEFAULT_WIDTH, DEFAULT_HEIGHT);

		 		// Draw message telling the user to open a .BMP
		 		g.setColor(Color.BLACK);
		 		g.setFont(new Font("Verdana", Font.BOLD, 14));
		 		String str1 = "Please open a bitmap image.";
		 		String str2 = "(Ctrl + O)";

		 		FontMetrics fm1 = g.getFontMetrics();
		 		FontMetrics fm2 = g.getFontMetrics();

   				Rectangle2D rect1 = fm1.getStringBounds(str1, g);
   				Rectangle2D rect2 = fm2.getStringBounds(str2, g);
  
    			g.drawString(str1, (int) ((getWidth() - rect1.getWidth())/2), (int) ((getHeight() - rect1.getHeight())/2));
    			g.drawString(str2, (int) ((getWidth() - rect2.getWidth())/2), (int) ((getHeight() + rect2.getHeight())/2));
	 		
	 		}

	 			/* Draw image */

	 		else
	 			g.drawImage(bmp.getImage(), horizontal_padding, vertical_padding, null);

	 	}

	 	/**
	 	* Refreshes the canvas size, properly padding it
	 	*/
	 	public void refreshSize() {
	 		
	 		int width  = (bmp == null) ? DEFAULT_WIDTH  : bmp.getWidth();
		    int height = (bmp == null) ? DEFAULT_HEIGHT : bmp.getHeight();

	 		setSize(Math.max(width, getWidth()), Math.max(height, getHeight()));

			horizontal_padding = (getWidth()  - width)  / 2;
			vertical_padding   = (getHeight() - height) / 2;

	 	}
	 } // Canvas Class

}