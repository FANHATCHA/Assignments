/**

COMP 2631: Data Structures and Algorithms II (Winter 2015)
Mandelbrot Fractal Assignment
@author William Fiset, Micah Stairs
@professor Dr. Keliher

TABLE OF CONTENTS:
-Constants
-Instance Variables
-Main
-Setup
-User Interaction
-View Adjustments
-Fractal Computations
-Fractal Helper Methods

DEPENDENCIES:
-Complex.java: Used to hold both the real and imaginary part of a complex number, and enables basic arithmetic operations.

**/

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.*;
import java.util.*;

public class MandelbrotGUI extends JFrame implements ActionListener, /*KeyListener,*/ MouseListener {

/*** CONSTANTS ***/

	public static int  	IMAGE_SIZE 		= 600,
						MAX_ITERATIONS 	= 150,
						DEFAULT_DEGREE  = 1;

	private static double  	DEFAULT_ZOOM		= 100.0,
							DEFAULT_TOP_LEFT_X	= -3.0,
							DEFAULT_TOP_LEFT_Y	= +3.0;

/*** INSTANCE VARIABLES ***/

	private static final String iconName = "fractalIcon.png";

	// Current view variables
	private double 	zoomFactor = DEFAULT_ZOOM,
					topLeftX  = DEFAULT_TOP_LEFT_X,
					topLeftY  = DEFAULT_TOP_LEFT_Y;

	// 'Degree' of fractal (normal Mandelbrot fractal has a degree of 1)
	private int currentDegree = DEFAULT_DEGREE;
	
	// UI components
	private JMenuBar menuBar = new JMenuBar();
	private JButton leftButton, rightButton, topButton, bottomButton;
	private JSlider adjustZoomSlider;
	private JPanel canvas;
	private BufferedImage fractalImage = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_RGB);

	// Fractal Color data fields
    private static int[] totalIterations;
    private static int[] summedTotalIterations;
    private static int[][] numIterations = new int[IMAGE_SIZE][IMAGE_SIZE];

/*** MAIN ***/

    public static void main(String[] args) {
		new MandelbrotGUI();
	}

/*** SETUP ***/

	/* Constructor used to set up our Mandelbrot GUI */
	public MandelbrotGUI() {

		addIconImage();

			/* Add components to the screen */

		addCanvas();
	 	addMenu();
		addButtons();

			/* Set up listeners */
		
		addButtonListeners();
		addMouseListener(this);

			/* Show the user our wonderful GUI */
		
		pack();
		setGUIproperties();

			/* Update fractal initially */

		updateFractal();

	} // MandelbrotGUI

	/* Change the program's icon to something more suitable than the default Java icon */
	private void addIconImage() {

		try {

			setIconImage(new ImageIcon(iconName).getImage());

		} catch(Exception e) {

			System.err.println("Could not set the icon's image. Please ensure fractalIcon.png is within the directory.");
			e.printStackTrace();

		}

	}

	/* Adds a canvas to the screen, where the fractal will be drawn */
	private void addCanvas() {

		// Create an anonymous class for our canvas
		canvas = new JPanel() {

		 	@Override public Dimension getPreferredSize() {
		 		return new Dimension(IMAGE_SIZE, IMAGE_SIZE);
		 	}

		 	@Override protected void paintComponent(Graphics g) {
		 		super.paintComponent(g);
		 		g.drawImage(fractalImage, 0, 0, null);
		 	}
		 };
		 canvas.setVisible(true);

		// Add the canvas to the center of the window
	 	add(canvas, BorderLayout.CENTER);

	}

	/* Adds the menu system to the application, hooking up all of the proper keyboard short-cuts  */
	private void addMenu () {

		JMenuItem menuItem;
		JMenu menu, subMenu;

			/* File Menu */

		menu = new JMenu("File");
		menuBar.add(menu);

		menuItem = new JMenuItem("Quit");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

			/* View Menu */

		menu = new JMenu("View");
		menuBar.add(menu);

		menuItem = new JMenuItem("Move Left");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Move Right");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Move Up");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Move Down");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu.addSeparator();

		menuItem = new JMenuItem("Zoom In");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Zoom Out");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, 0));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu.addSeparator();

		menuItem = new JMenuItem("Return to Default");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0));
		menuItem.addActionListener(this);
		menu.add(menuItem);

			/* Settings Menu */

		menu = new JMenu("Settings");
		menuBar.add(menu);

		subMenu = new JMenu("Change Degree of Fractal");
		menu.add(subMenu);

		menuItem = new JMenuItem("0");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, 0));
		menuItem.addActionListener(this);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("1");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0));
		menuItem.addActionListener(this);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("2");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0));
		menuItem.addActionListener(this);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("3");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0));
		menuItem.addActionListener(this);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("4");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, 0));
		menuItem.addActionListener(this);
		subMenu.add(menuItem);

		this.setJMenuBar(menuBar);

	} // addMenu

	/* Adds buttons to the user interface, allowing the user to navigate around the fractal */
	private void addButtons() {

			/* Create a slider to adjust the zoom level */
		
		adjustZoomSlider = new JSlider();
		
		// Set properties
		adjustZoomSlider.setMinimum(1);
		adjustZoomSlider.setMaximum(25);
		adjustZoomSlider.setValue(9);
		adjustZoomSlider.setSnapToTicks(true);
		adjustZoomSlider.setMinorTickSpacing(1);
		adjustZoomSlider.setPaintTicks(true);

		// Add labels
	    adjustZoomSlider.setLabelTable(new Hashtable<Integer, JLabel>() {
	    	{
			    put(new Integer(1), new JLabel("<1/128x"));
			    put(new Integer(5), new JLabel("1/16x"));
			    put(new Integer(9), new JLabel("1x"));
			    put(new Integer(13), new JLabel("16x"));
			    put(new Integer(17), new JLabel("256x"));
			    put(new Integer(21), new JLabel("4096x"));
			    put(new Integer(25), new JLabel(">32768x"));
			}
		} );
		adjustZoomSlider.setPaintLabels(true);
		adjustZoomSlider.setFocusable(false);

	    this.add(adjustZoomSlider, BorderLayout.SOUTH);

			/* Create buttons to let the user move the view in one of four directions */

		this.add(leftButton = new BasicArrowButton(BasicArrowButton.WEST), BorderLayout.WEST);
		this.add(rightButton = new BasicArrowButton(BasicArrowButton.EAST), BorderLayout.EAST);
		this.add(topButton = new BasicArrowButton(BasicArrowButton.NORTH), BorderLayout.NORTH);
		bottomButton = new BasicArrowButton(BasicArrowButton.SOUTH);

			/* Finish adding things to the screen */

		Container southContainer = new Container();
        southContainer.setLayout(new BorderLayout());
        southContainer.add(bottomButton, BorderLayout.CENTER);
        southContainer.add(adjustZoomSlider, BorderLayout.SOUTH);
        this.add(southContainer, BorderLayout.SOUTH);

	} // addButtons

	/* Add the listeners for the buttons */
	private void addButtonListeners() {

		leftButton.addActionListener(
			new ActionListener() { public void actionPerformed(ActionEvent action) {
				moveLeft();
			}}
		);

		rightButton.addActionListener(
			new ActionListener() { public void actionPerformed(ActionEvent action) {
				moveRight();
			}}
		);

		topButton.addActionListener(
			new ActionListener() { public void actionPerformed(ActionEvent action) {
				moveUp();
			}}
		);

		bottomButton.addActionListener(
			new ActionListener() { public void actionPerformed(ActionEvent action) {
				moveDown();
			}}
		);

		// Adjust the zoom whenever the user moves the slider
		adjustZoomSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				// ...8 = 50%, 9 = 100%, 10 = 200%...
				double newZoomFactor = 100.0 * Math.pow(2, adjustZoomSlider.getValue() - 9);
				adjustZoom(newZoomFactor);
			}
		});

	} // addButtonActionListeners

	/** Set some default GUI Properties **/
	private void setGUIproperties() {
		
		// Ensure our application will be closed when the user presses the "X" */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		// Sets screen location in the center of the screen (only works after calling pack)
		setLocationRelativeTo(null);

		// Update Title
		setTitle("Mandelbrot Fractal Explorer");

		// Show Screen
		setVisible(true);

	} // setGUIproperties

/*** USER INTERACTION ***/

	/**
	* This method handles all of the actions triggered when the user interacts
	* with the main menu, or presses keyboard shortcuts to trigger those events.
	* @param event - the triggered event
	*/
	@Override public void actionPerformed(ActionEvent event) {

	 	switch (event.getActionCommand()) {

	 		case "Quit":

	 			System.exit(0);
	 			break;

	 		case "Move Left":

	 			moveLeft();
	 			break;

	 		case "Move Right":

	 			moveRight();
	 			break;

	 		case "Move Up":

	 			moveUp();
	 			break;

	 		case "Move Down":

	 			moveDown();
	 			break;

	 		case "Zoom In":

	 			adjustZoom(zoomFactor*2);
	 			updateZoomSliderValue();
	 			break;

	 		case "Zoom Out":

	 			adjustZoom(zoomFactor/2);
	 			updateZoomSliderValue();
	 			break;

	 		// Adjust degree of fractal, then reset to the default location/zoom
	 		default:
	 			currentDegree = Integer.valueOf(event.getActionCommand());	
	 		
	 		case "Return to Default":

	 			zoomFactor = DEFAULT_ZOOM;
	 			topLeftX = DEFAULT_TOP_LEFT_X;
	 			topLeftY = DEFAULT_TOP_LEFT_Y;
	 			updateFractal();
	 			updateZoomSliderValue();
	 			break;

	 	} // switch

	 } // actionPerformed

	/* Adjust the zoom of the image based on where the mouse click occured */
	@Override public void mousePressed(MouseEvent mouse) {

		// Calculate mouse position on canvas
		double mx = (double) mouse.getX();
		double my = (double) (mouse.getY() - menuBar.getHeight() - getInsets().top);
 		
 		// Only change zoom if the click was on the canvas
		if (mx >= 0 && my >= 0  && mx <= IMAGE_SIZE && my <= IMAGE_SIZE) {

			// Left click to zoom in
			if (mouse.getButton() == MouseEvent.BUTTON1) {
				adjustZoom(mx, my, zoomFactor*2);
				updateZoomSliderValue();
			}

			// Right click to zoom out
			else if (mouse.getButton() == MouseEvent.BUTTON3) {
				adjustZoom(mx, my, zoomFactor/2);
				updateZoomSliderValue();
			}

		} // if

	} // mousePressed

	// Unused methods from MouseListener
	@Override public void mouseReleased(MouseEvent e) { }
	@Override public void mouseExited(MouseEvent e) { } 
	@Override public void mouseEntered(MouseEvent e) { } 
	@Override public void mouseClicked(MouseEvent e) { } 

/*** VIEW ADJUSTMENTS ***/

	/* Default method for zooming (into/out of the center of the image) */
	private void adjustZoom(double newZoomFactor) {

		adjustZoom(IMAGE_SIZE/2, IMAGE_SIZE/2, newZoomFactor);

	} // adjustZoom

	/* Zooms into/out of the specified part of the screen */
	private void adjustZoom(double centerX, double centerY, double newZoomFactor) {

		topLeftX += centerX/zoomFactor;
		topLeftY -= centerY/zoomFactor;

 		zoomFactor = newZoomFactor;

 		topLeftX -= (IMAGE_SIZE/2)/zoomFactor;
 		topLeftY += (IMAGE_SIZE/2)/zoomFactor;

 		updateFractal();

	} // adjustZoom

	/* Make sure the slider matches the new zoom level */
	private void updateZoomSliderValue() {

		// Calculate the index the slider should be at 
		double temp = zoomFactor;
		int zoomIndex = 1;
		while (temp >= 0.78125) {
			temp /= 2;
			zoomIndex++;
		}

		// Adjust slider
		adjustZoomSlider.setValue(zoomIndex);

	} // updateZoomSliderValue

	/** Methods to move the view in one of 4 directions **/

    private void moveLeft() {
		topLeftX -= 100.0/zoomFactor;
		updateFractal(-IMAGE_SIZE/6, 0);
    }

    private void moveRight() {
		topLeftX += 100.0/zoomFactor;
    	updateFractal(+IMAGE_SIZE/6, 0);
    }

    private void moveDown() {
		topLeftY -= 100.0/zoomFactor;
		updateFractal(0, -IMAGE_SIZE/6);
    }

    private void moveUp() {
		topLeftY += 100.0/zoomFactor;
		updateFractal(0, +IMAGE_SIZE/6);
    }

/*** FRACTAL COMPUTATIONS ***/

	/*
		- If this sequence goes to infinity, then it is NOT in the mandelbrot set.
		- Otherwise it is in the mandelbrot set. 
	
		Reccurence Relation:
			z_0 = 0
			z_1 = (z_0)^2 + c = c
			...
			z_n = (z_n-1)^2 + c

		Simplifications:
		- If sequence ever leaves a circle of radius two, then 
		sequence will go to infinity, so stop iterating.
		- If it never leaves, (MAX_ITERERATIONS ~ 150) then the original point
		c is in the mandelbrot set.

		@return the number of iterations
    */
    private int doIterations(Complex c) {

		Complex z = new Complex(0, 0);
	    	
    	for (int i = 0; i < MAX_ITERATIONS; i++) {

    		// Default is a degree of 1 (changing the degree results in a different-looking fractal!)
    		for (int j = 0; j < currentDegree; j++)
    			z.multiply(new Complex(z));
    		
    		z.add(c);

    		// Not within Mandelbrot set
    		if (z.modulus() > 2.0) return i;

    	} // for

    	// Within the Mandelbrot set
	    return MAX_ITERATIONS;

    }

    /* Default method used when the zoom level is being changed
    /* (Sets the color of each pixel based on data that we will gather for this zoom level) */
    private void updateFractal() {
    	
    	// Compute the number of iterations for each pixel in the image
		totalIterations = new int[MAX_ITERATIONS+1];
    	for (int y = 0; y < IMAGE_SIZE; y++) {
			for (int x = 0; x < IMAGE_SIZE; x++) {
				
				int iterations = doIterations(getComplexPoint(x, y));
				totalIterations[iterations]++;

				// Store these values so that they don't need to be computed a second time
				numIterations[y][x] = iterations;

			} // for
		} // for

		// Using dynamic programming, store some totals we will use later to calculate how much each color should be weighted
		summedTotalIterations = new int[MAX_ITERATIONS+1];
		for (int i = 1; i < MAX_ITERATIONS; i++)
			summedTotalIterations[i] += summedTotalIterations[i - 1] + totalIterations[i - 1];

		// Set the color of each pixel based on the data that was gathered
    	for (int y = 0; y < IMAGE_SIZE; y++) {
			for (int x = 0; x < IMAGE_SIZE; x++) {
				int color = makeColor(numIterations[y][x]);
				fractalImage.setRGB(x, y, color);
			} // for 
		} // for

		canvas.repaint();

    } // updateFractal

    /* This method is used when we are shifting the image, and do not need to re-calculate all of the pixel
    /* (Also uses data that was previously gathered for that zoom level) */
    private void updateFractal(int dx, int dy) {

    	// Variables to denote the unchanged region of pixels (which do not need to re-calculated)
    	int startUnchangedX = 0, endUnchangedX = IMAGE_SIZE, startUnchangedY = 0, endUnchangedY = IMAGE_SIZE;

    		/* Reduce computation time by a significant amount (~6x) by simply copying the RGB values for 5/6 of the pixels (instead of re-computing) */

    	// Move left
		if (dx < 0) {

			startUnchangedX = IMAGE_SIZE/6;

			// Transpose the unchanged pixels
			for (int y = startUnchangedY; y < endUnchangedY; y++)
				for (int x = endUnchangedX - 1; x >= startUnchangedX; x--)
					fractalImage.setRGB(x, y, fractalImage.getRGB(x + dx, y));

		// Move right
		} else if (dx > 0) {

			endUnchangedX = IMAGE_SIZE - IMAGE_SIZE/6;

			// Transpose the unchanged pixels
			for (int y = startUnchangedY; y < endUnchangedY; y++)
				for (int x = startUnchangedX; x < endUnchangedX; x++)
					fractalImage.setRGB(x, y, fractalImage.getRGB(x + dx, y));

		// Move up
		} else if (dy > 0) {

			startUnchangedY = IMAGE_SIZE/6;

			// Transpose the unchanged pixels
			for (int y = endUnchangedY - 1; y >= startUnchangedY; y--)
				for (int x = startUnchangedX; x < endUnchangedX; x++)
					fractalImage.setRGB(x, y, fractalImage.getRGB(x, y - dy));

		// Move down
		} else if (dy < 0) {

			endUnchangedY = IMAGE_SIZE - IMAGE_SIZE/6;

			// Transpose the unchanged pixels
			for (int y = startUnchangedY; y < endUnchangedY; y++)
				for (int x = startUnchangedX; x < endUnchangedX; x++)
					fractalImage.setRGB(x, y, fractalImage.getRGB(x, y - dy));
		}

		// Calculate the new pixels (1/6 of the image)
		for (int y = 0; y < IMAGE_SIZE; y++) {

			for (int x = 0; x < IMAGE_SIZE; x++) {

				// Skip past pixels we do not need to re-calculate
				if (y >= startUnchangedY && y < endUnchangedY && x >= startUnchangedX && x < endUnchangedX)
				 	continue;

				int color = makeColor(doIterations(getComplexPoint(x, y)));
				fractalImage.setRGB(x, y, color);
			} // for 
		} // for


		canvas.repaint();

    } // updateFractal

/*** FRACTAL HELPER METHODS ***/

    /* Given an (x,y) co-ordinate of the image, return the corresponding co-ordinate in the current scope of the complex plane */
    private Complex getComplexPoint(double x, double y) {

    	return new Complex(x/zoomFactor + topLeftX, y/zoomFactor - topLeftY);

    } // getComplexPoint

    /* Given the number of iterations, calculate the appropriate color of the pixel */
    private int makeColor(int iterations) {

    	// Use data gathered from all pixels in the current scope of the image to determine what color this pixel should be 
    	double percentage = (double) summedTotalIterations[iterations] / (double) (IMAGE_SIZE*IMAGE_SIZE);

    	Color color;

    	// Black
    	if (iterations == MAX_ITERATIONS)
    		color = new Color(0, 0, 0);
    	
    	// Pick an appropriate color for the pixel
    	else if (percentage < 0.5)
			color = fadeBetweenColors(0, 16, 95, percentage*2, 171, 9, 0);    	
		else   
			color = fadeBetweenColors(171, 9, 0, (percentage - 0.5)*2, 255, 192, 0);     		

    	return color.getRGB();
    
    } // makeColor

    /* Given two colors and a percentage, return a blended version of the pixel */
    private Color fadeBetweenColors(int r1, int g1, int b1, double percentage, int r2, int g2, int b2) {

    		/* Setup */

    	boolean rLowToHigh = r1 < r2;
    	boolean gLowToHigh = g1 < g2;
    	boolean bLowToHigh = b1 < b2;

    	int lowR = Math.min(r1, r2);
    	int highR = Math.max(r1, r2);

    	int lowG = Math.min(g1, g2);
    	int highG = Math.max(g1, g2);

    	int lowB = Math.min(b1, b2);
    	int highB = Math.max(b1, b2);

    		/* Build and return blended color */

    	return new Color(
	    		lowR + (int) ( ((double) (highR-lowR)) * (rLowToHigh ? percentage : 1.0 - percentage) ),
	    		lowG + (int) ( ((double) (highG-lowG)) * (gLowToHigh ? percentage : 1.0 - percentage) ),
	    		lowB + (int) ( ((double) (highB-lowB)) * (bLowToHigh ? percentage : 1.0 - percentage) )
	    	);

    } // fadeBetweenColors

}