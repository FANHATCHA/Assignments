/**

@author William Fiset, Micah Stairs

Each time you render the mandelbrot set, you will iterate though
all the pixels on the drawing surface (x,y) and convert those
to positions on the complex plane.
	
c = a + bi

*/

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;

public class MandelbrotGUI extends JFrame implements ActionListener, KeyListener, MouseListener {

		/* Constants */

	private static int  IMAGE_SIZE 		= 600,
						MAX_ITERATIONS 	= 150;

	private static double  	DEFAULT_ZOOM		= 100,
							DEFAULT_TOP_LEFT_X	= -3.0,
							DEFAULT_TOP_LEFT_Y	= +3.0;

		/* Instance variables */

	private Canvas canvas;
	private JMenuItem returnToDefaultMenuItem;
	private BufferedImage fractalImage = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_RGB);


	private double 	ZOOM_FACTOR = 100,
					TOP_LEFT_X  = -3.0,
					TOP_LEFT_Y  = 3.0;

    public static void main(String[] args) {

		MandelbrotGUI gui = new MandelbrotGUI();
	}

	public MandelbrotGUI() {

			/* Add a canvas to the center of the window */

		canvas = new Canvas();
	 	add(canvas, BorderLayout.CENTER);

	 		/* Add the menu */

	 	addMenu();

			/* Show the user our wonderful GUI */
		
		pack();
		setGUIproperties();

			/* Add listeners */

		addKeyListener(this);
		addMouseListener(this);

			/* Update fractal initally */

		updateFractal();

	}

	/**
	* Set some default GUI Properties
	*/
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

		menuItem = new JMenuItem("Quit");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

			/* Edit Menu */

		menu = new JMenu("View");
		menu.getAccessibleContext().setAccessibleDescription("View");
		menuBar.add(menu);

		returnToDefaultMenuItem = new JMenuItem("Zoom In");
		returnToDefaultMenuItem.addActionListener(this);
		menu.add(returnToDefaultMenuItem);

		returnToDefaultMenuItem = new JMenuItem("Zoom Out");
		returnToDefaultMenuItem.addActionListener(this);
		menu.add(returnToDefaultMenuItem);

		menu.addSeparator();

		returnToDefaultMenuItem = new JMenuItem("Return to Default");
		returnToDefaultMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		returnToDefaultMenuItem.addActionListener(this);
		returnToDefaultMenuItem.setEnabled(false);
		menu.add(returnToDefaultMenuItem);

		this.setJMenuBar(menuBar);

	}

	/**
	* This method handles all of the actions triggered when the user interacts
	* with the main menu, or presses keyboard shortcuts to trigger those events.
	* @param event - the triggered event
	*/
	public void actionPerformed(ActionEvent event) {

	 	switch (event.getActionCommand()) {

	 		case "Quit":

	 			System.exit(0);
	 			break;

	 		case "Return to Default":

	 			System.out.println("Returning to default view...");
	 			returnToDefaultMenuItem.setEnabled(false);
	 			ZOOM_FACTOR = DEFAULT_ZOOM;
	 			TOP_LEFT_X = DEFAULT_TOP_LEFT_X;
	 			TOP_LEFT_Y = DEFAULT_TOP_LEFT_Y;
	 			break;

	 		case "Zoom In":

	 			returnToDefaultMenuItem.setEnabled(true);
	 			ZOOM_FACTOR *= 2;
	 			
	 			break;

	 		case "Zoom Out":

	 			returnToDefaultMenuItem.setEnabled(true);
	 			ZOOM_FACTOR /= 2;
	 			
	 			break;
	 	}

	 	updateFractal();

	 }

	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {

			case KeyEvent.VK_LEFT:
				TOP_LEFT_X -= 1.0/(ZOOM_FACTOR/100);
				returnToDefaultMenuItem.setEnabled(true);
				break;

			case KeyEvent.VK_RIGHT:
				TOP_LEFT_X += 1.0/(ZOOM_FACTOR/100);
				returnToDefaultMenuItem.setEnabled(true);
				break;

			case KeyEvent.VK_UP:
				TOP_LEFT_Y += 1.0/(ZOOM_FACTOR/100);
				returnToDefaultMenuItem.setEnabled(true);
				break;

			case KeyEvent.VK_DOWN:
				TOP_LEFT_Y -= 1.0/(ZOOM_FACTOR/100);
				returnToDefaultMenuItem.setEnabled(true);
				break;
		}

		updateFractal();

	} 

	public void keyReleased(KeyEvent e) { } 
	public void keyTyped(KeyEvent e) { } 

	public void mousePressed(MouseEvent e) {

		// // Left click to zoom in
		// if (e.getButton == MouseEvent.BUTTON1) {
		// 	returnToDefaultMenuItem.setEnabled(true);
	 // 		ZOOM_FACTOR *= 2;
		// } else if (e.getButton == MouseEvent.BUTTON2) {
		// 	returnToDefaultMenuItem.setEnabled(true);
	 // 		ZOOM_FACTOR /= 2;
		// }

	} 

	public void mouseReleased(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { } 
	public void mouseEntered(MouseEvent e) { } 
	public void mouseClicked(MouseEvent e) { } 

    private int doIterations(Complex c) {

    	/*

		if this sequence goes to infinity, then it is NOT in the mandelbrot set
		otherwise it is in the mandelbrot. 

    	z_0 = 0
    	z_1 = (z_0)^2 + c = c
    	...
		z_n = (z_n-1)^2 + c

		Simplifications:
		- If sequence ever leaves a circle of radius two, then 
		sequence will go to infinity, so stop iterating.

		- If it never leaves, (MAX_ITER ~ 150) then original point
		c is in the mandelbrot set.

		NOTE ON COLOR: 
		- Use distance formula to calculate the distance from the
		origin: dist = (a^2 + b^2) <= 2.
		- Set ranges for the # of iterations, ex:
		0-10 purple, 10-20 pink, 20-30 red.... 
		
		^^^ 0-10 is the background color

    	*/

    	Complex z = new Complex(0, 0);
    	
    	for (int i = 0; i < MAX_ITERATIONS; i++) {
    		z.multiply(z);
    		z.add(c);
    		//System.out.println(z);
    		if (z.modulus() > 2) {
    			// System.out.println(i);
    			return i;
    		}
    	}

    	return MAX_ITERATIONS;

    }

    private void updateFractal() {

    	for (int y = 0; y < IMAGE_SIZE; y++)
			for (int x = 0; x < IMAGE_SIZE; x++) {
				fractalImage.setRGB(x, y, getColor(doIterations(getComplexPoint(x, y))));
			}

		canvas.repaint();

    }

    private Complex getComplexPoint(double x, double y) {

    	return new Complex(x/ZOOM_FACTOR + TOP_LEFT_X, y/ZOOM_FACTOR - TOP_LEFT_Y);

    }

    private int getColor(int iterations) {

    	// iterations = (int) (Math.random()*150);

    	double percentageOfIterations = ((double) iterations) / ((double) MAX_ITERATIONS);

    	Color color = new Color(
    		(int) (255.0*percentageOfIterations),
    		(int) (255.0*percentageOfIterations),
    		0
    	);

		return color.getRGB();
    }

	/** PRIVATE INNER CLASS **/

	private class Canvas extends JPanel {

			/* Constants */
		
		private final int DEFAULT_HEIGHT  = 600;
		private final int DEFAULT_WIDTH   = 600;
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

	 		return new Dimension(DEFAULT_WIDTH + horizontal_padding*2, DEFAULT_HEIGHT + vertical_padding*2);
	 	
	 	}

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

	 	}

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