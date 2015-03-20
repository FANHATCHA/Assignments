/**

COMP 2631: Data Structures and Algorithms II (Winter 2015)
@author William Fiset, Micah Stairs
Fractal Assignment

*/

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.*;
import javax.imageio.*;
import java.util.*;
import java.io.*;

public class MandelbrotGUI extends JFrame implements ActionListener, KeyListener, MouseListener {

		/* Constants */

	public static int  	IMAGE_SIZE 		= 600,
						MAX_ITERATIONS 	= 150,
						DEFAULT_DEGREE  = 1;

	private static double  	DEFAULT_ZOOM		= 100.0,
							DEFAULT_TOP_LEFT_X	= -3.0,
							DEFAULT_TOP_LEFT_Y	= +3.0;

		/* Instance variables */

	private static final String iconName = "fractalIcon.png";

	// Current view variables
	private double 	ZOOM_FACTOR = DEFAULT_ZOOM,
					TOP_LEFT_X  = DEFAULT_TOP_LEFT_X,
					TOP_LEFT_Y  = DEFAULT_TOP_LEFT_Y;

	// 'Degree' of fractal (normal Mandelbrot fractal has a degree of 1)
	private int currentDegree = DEFAULT_DEGREE;
	
	// UI components
	private JMenuBar menuBar = new JMenuBar();
	private JButton leftButton, rightButton, topButton, bottomButton;
	private JSlider adjustZoomSlider;
	private Canvas canvas;
	private BufferedImage fractalImage = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_RGB);

	// Fractal Color data fields
    private static int[] totalIterations;
    private static int[] summedTotalIterations;
    private static int[][] numIterations = new int[IMAGE_SIZE][IMAGE_SIZE];

    public static void main(String[] args) {
		new MandelbrotGUI();
	}

	public MandelbrotGUI() {

		addIconImage();

			/* Add a canvas to the center of the window */

		canvas = new Canvas();
	 	add(canvas, BorderLayout.CENTER);

	 		/* Add the menu */

	 	addMenu();

			/* Adds buttons to screen */

		addButtons();
		addButtonListeners();

			/* Show the user our wonderful GUI */
		
		pack();
		setGUIproperties();

			/* Add listeners */

		addKeyListener(this);
		addMouseListener(this);

			/* Update fractal initially */

		updateFractal();

	}

	public void addIconImage() {

		try {

			setIconImage(new ImageIcon(iconName).getImage());

		} catch(Exception e) {
			System.err.println("Could Not set Icon Image. Make sure fractalIcon.png is within the directory.");
			e.printStackTrace();
		}

	}

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

	}

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
			    //put(new Integer(3), new JLabel("1/64x"));
			    put(new Integer(5), new JLabel("1/16x"));
			    //put(new Integer(7), new JLabel("1/4x"));
			    put(new Integer(9), new JLabel("1x"));
			    //put(new Integer(11), new JLabel("4x"));
			    put(new Integer(13), new JLabel("16x"));
			    //put(new Integer(15), new JLabel("64x"));
			    put(new Integer(17), new JLabel("256x"));
			    //put(new Integer(19), new JLabel("1024x"));
			    put(new Integer(21), new JLabel("4096x"));
			    //put(new Integer(23), new JLabel("16384x"));
			    put(new Integer(25), new JLabel(">32768x"));
			}
		} );
		adjustZoomSlider.setPaintLabels(true);
		adjustZoomSlider.setFocusable(false);

	    this.add(adjustZoomSlider, BorderLayout.SOUTH);

			/* Create buttons to let the user shift the view in one of four directions */

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
				shiftLeft();
			}}
		);

		rightButton.addActionListener(
			new ActionListener() { public void actionPerformed(ActionEvent action) {
				shiftRight();
			}}
		);

		topButton.addActionListener(
			new ActionListener() { public void actionPerformed(ActionEvent action) {
				shiftUp();
			}}
		);

		bottomButton.addActionListener(
			new ActionListener() { public void actionPerformed(ActionEvent action) {
				shiftDown();
			}}
		);

		adjustZoomSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				// ...8 = 50%, 9 = 100%, 10 = 200%...
				double newZoomFactor = 100.0 * Math.pow(2, adjustZoomSlider.getValue() - 9);
				adjustZoom(newZoomFactor);
			}
		});

	} // addButtonActionListeners

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
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

			/* Settings Menu */

		menu = new JMenu("Settings");
		menuBar.add(menu);

		subMenu = new JMenu("Change Degree of Fractal");
		menu.add(subMenu);

		menuItem = new JMenuItem("0");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("1");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("2");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("3");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("4");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		subMenu.add(menuItem);

		this.setJMenuBar(menuBar);

	} // addMenu

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

	 		case "Zoom In":

	 			adjustZoom(ZOOM_FACTOR*2);
	 			updateZoomSliderValue();

	 			break;

	 		case "Zoom Out":

	 			adjustZoom(ZOOM_FACTOR/2);
	 			updateZoomSliderValue();

	 			break;

	 		// Adjust degree of fractal
	 		default:
	 			currentDegree = Integer.valueOf(event.getActionCommand());	
	 		
	 		case "Return to Default":

	 			ZOOM_FACTOR = DEFAULT_ZOOM;
	 			TOP_LEFT_X = DEFAULT_TOP_LEFT_X;
	 			TOP_LEFT_Y = DEFAULT_TOP_LEFT_Y;
	 			updateFractal();
	 			updateZoomSliderValue();

	 			break;

	 	} // switch

	 } // actionPerformed

	@Override public void keyPressed(KeyEvent e) {
	
		switch (e.getKeyCode()) {

			case KeyEvent.VK_LEFT:
				shiftLeft();
				break;

			case KeyEvent.VK_RIGHT:
				shiftRight();
				break;

			case KeyEvent.VK_UP:
				shiftUp();
				break;

			case KeyEvent.VK_DOWN:
				shiftDown();
				break;

		} // switch  

	} // keyPressed

	@Override public void keyReleased(KeyEvent e) { } 
	@Override public void keyTyped(KeyEvent e) { } 

	@Override public void mousePressed(MouseEvent mouse) {

		// Calculate mouse position on canvas
		double mx = (double) mouse.getX();
		double my = (double) (mouse.getY() - menuBar.getHeight() - getInsets().top);
 		
 		// Only change zoom if the click was on the canvas
		if (mx >= 0 && my >= 0  && mx <= IMAGE_SIZE && my <= IMAGE_SIZE) {

			// Left click to zoom in
			if (mouse.getButton() == MouseEvent.BUTTON1) {
				adjustZoom(mx, my, ZOOM_FACTOR*2);
				updateZoomSliderValue();
			}

			// Right click to zoom out
			else if (mouse.getButton() == MouseEvent.BUTTON3) {
				adjustZoom(mx, my, ZOOM_FACTOR/2);
				updateZoomSliderValue();
			}

		} // if

	} // mousePressed

	@Override public void mouseReleased(MouseEvent e) { }
	@Override public void mouseExited(MouseEvent e) { } 
	@Override public void mouseEntered(MouseEvent e) { } 
	@Override public void mouseClicked(MouseEvent e) { } 

	/* Default method, zooms into/out of the center of the image */
	private void adjustZoom(double newZoomFactor) {
		adjustZoom(IMAGE_SIZE/2, IMAGE_SIZE/2, newZoomFactor);
	}

	/* Zooms into/out of the specified part of the screen */
	private void adjustZoom(double centerX, double centerY, double newZoomFactor) {

		TOP_LEFT_X += centerX/ZOOM_FACTOR;
		TOP_LEFT_Y -= centerY/ZOOM_FACTOR;

 		ZOOM_FACTOR = newZoomFactor;

 		TOP_LEFT_X -= (IMAGE_SIZE/2)/ZOOM_FACTOR;
 		TOP_LEFT_Y += (IMAGE_SIZE/2)/ZOOM_FACTOR;

 		updateFractal();

	}

	/* Make sure the slider matches the new zoom level */
	private void updateZoomSliderValue() {

		// Calculate the index the slider should be at 
		double temp = ZOOM_FACTOR;
		int zoomIndex = 1;
		while (temp >= 0.78125) {
			temp /= 2;
			zoomIndex++;
		}

		// Adjust slider
		adjustZoomSlider.setValue(zoomIndex);

	}

	/**
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

    		// Default is a degree of 1
    		for (int j = 0; j < currentDegree; j++)
    			z.multiply(new Complex(z));
    		
    		z.add(c);

    		// Not within Mandelbrot set
    		if (z.modulus() > 2.0) return i;

    	} // for

    	// Within Mandelbrot set
	    return MAX_ITERATIONS;

    }

    /** Methods to shift the fractal in one of 4 directions **/

    private void shiftLeft() {
		TOP_LEFT_X -= 100.0/ZOOM_FACTOR;
		updateFractal(-IMAGE_SIZE/6, 0);
    }

    private void shiftRight() {
		TOP_LEFT_X += 100.0/ZOOM_FACTOR;
    	updateFractal(+IMAGE_SIZE/6, 0);
    }

    private void shiftDown() {
		TOP_LEFT_Y -= 100.0/ZOOM_FACTOR;
		updateFractal(0, -IMAGE_SIZE/6);
    }

    private void shiftUp() {
		TOP_LEFT_Y += 100.0/ZOOM_FACTOR;
		updateFractal(0, +IMAGE_SIZE/6);
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
		for (int i = 1; i < MAX_ITERATIONS; i++) {
			summedTotalIterations[i] += summedTotalIterations[i - 1] + totalIterations[i - 1];
		}

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

    	// Shift left
		if (dx < 0) {

			startUnchangedX = IMAGE_SIZE/6;

			// Transpose the unchanged pixels
			for (int y = startUnchangedY; y < endUnchangedY; y++)
				for (int x = endUnchangedX - 1; x >= startUnchangedX; x--)
					fractalImage.setRGB(x, y, fractalImage.getRGB(x + dx, y));

		// Shift right
		} else if (dx > 0) {

			endUnchangedX = IMAGE_SIZE - IMAGE_SIZE/6;

			// Transpose the unchanged pixels
			for (int y = startUnchangedY; y < endUnchangedY; y++)
				for (int x = startUnchangedX; x < endUnchangedX; x++)
					fractalImage.setRGB(x, y, fractalImage.getRGB(x + dx, y));

		// Shift up
		} else if (dy > 0) {

			startUnchangedY = IMAGE_SIZE/6;

			// Transpose the unchanged pixels
			for (int y = endUnchangedY - 1; y >= startUnchangedY; y--)
				for (int x = startUnchangedX; x < endUnchangedX; x++)
					fractalImage.setRGB(x, y, fractalImage.getRGB(x, y - dy));

		// Shift down
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

    /* Given an (x,y) co-ordinate of the image, return the corresponding co-ordinate in the current scope of the complex plane */
    private Complex getComplexPoint(double x, double y) {

    	return new Complex(x/ZOOM_FACTOR + TOP_LEFT_X, y/ZOOM_FACTOR - TOP_LEFT_Y);

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

    		/* Build and return color */

    	return new Color(
	    		lowR + (int) ( ((double) (highR-lowR)) * (rLowToHigh ? percentage : 1.0 - percentage) ),
	    		lowG + (int) ( ((double) (highG-lowG)) * (gLowToHigh ? percentage : 1.0 - percentage) ),
	    		lowB + (int) ( ((double) (highB-lowB)) * (bLowToHigh ? percentage : 1.0 - percentage) )
	    	);

    } // fadeBetweenColors

	/** PRIVATE INNER CLASS **/

	private class Canvas extends JPanel {

			/* Constants */
		
		public final static int DEFAULT_HEIGHT  = 600;
		public final static int DEFAULT_WIDTH   = 600;
		public final static int DEFAULT_PADDING = 0; 

			/* Variables */

		private int horizontal_padding, vertical_padding;

	 	public Canvas () {

	 		setVisible(true);
	 		horizontal_padding = DEFAULT_PADDING;
	 		vertical_padding = DEFAULT_PADDING;

	 	} // Canvas

	 	/**
	 	* Returns the dimensions that the canvas should be, taking into consideration
	 	* the image size and padding.
	 	* @return preferred dimension
	 	*/
	 	@Override public Dimension getPreferredSize() {

	 		return new Dimension(DEFAULT_WIDTH + horizontal_padding*2, DEFAULT_HEIGHT + vertical_padding*2);
	 	
	 	} // getPreferredSize

	 	/**
	 	* Updates the canvas, drawing the image (or blank canvas) in the center,
	 	* with pre-defined padding around it.
	 	* @param g - Graphics object (which is passed through by Java)
	 	*/
	 	@Override protected void paintComponent(Graphics g) {

	 		super.paintComponent(g);

	 		refreshSize();

 			// Draw canvas
 			g.setColor(Color.LIGHT_GRAY);
	 		g.fillRect(horizontal_padding, vertical_padding, DEFAULT_WIDTH, DEFAULT_HEIGHT);

	 		g.drawImage(fractalImage, horizontal_padding, vertical_padding, null);

	 	} // paintComponent

	 	/**
	 	* Refreshes the canvas size, properly padding it
	 	*/
	 	public void refreshSize() {
	 		
	 		int width  = DEFAULT_WIDTH;
		    int height = DEFAULT_HEIGHT;

	 		setSize(Math.max(width, getWidth()), Math.max(height, getHeight()));

			horizontal_padding = (getWidth()  - width)  / 2;
			vertical_padding   = (getHeight() - height) / 2;

	 	}
	 } // Canvas Class

}