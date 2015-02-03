import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;

public class BitmapGUI extends JFrame implements ActionListener {

	private static final int DEFAULT_CANVAS_HEIGHT = 550; 
	private static final int DEFAULT_CANVAS_WIDTH =  700;
	private static final int PADDING = 75;

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
		
		frame.addMenu();
		frame.addButtons();
		frame.setTitle("BitMapHacker!");
		frame.pack();
		frame.setVisible(true);

	}

	public BitmapGUI () {

		Canvas canvas = new Canvas();

	 	Container cPane = this.getContentPane();
	 	cPane.add( canvas, BorderLayout.CENTER );

	}

	/** 
	 * Opens up a JFileChooser for the user to choose a file from their file system 
	 * @return - a file that the user selected on their computer, or null if they didn't choose anything
	 */
	private static File selectFile (String prompt) {

		// Prompt user to select a file 
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle(prompt);
		int returnVal = fileChooser.showOpenDialog(null);

		// User has finished selecting a file 
		if(returnVal == JFileChooser.APPROVE_OPTION) 
			return fileChooser.getSelectedFile();

		// User did not select a file (pressed cancel) 
		return null;
		
	} 

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

		menuItem = new JMenuItem("Save");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Save");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Save As");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Save As");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu.addSeparator();

		menuItem = new JMenuItem("Close");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Close");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Quit");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Quit");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		this.setJMenuBar(menuBar);

	}

	public void actionPerformed(ActionEvent e) {

	 	switch (e.getActionCommand()) {

	 		case "Save":
	 			
	 			break;

	 		case "Save As":
	 			
	 			break;

	 		case "Open":
	 			
	 			break;

	 		case "Close":

	 			break;

	 		case "Quit":
	 			System.exit(0);
	 			break;

	 	}
	 }


	 private void addButtons() {

	 	Container cPane = this.getContentPane();

		JPanel buttonPanel = new JPanel();

		JButton flipButton = new JButton("Flip");
		JButton blurButton = new JButton("Blur");
		JButton enhanceButton = new JButton("Enhance");
		JButton combineButton = new JButton("Combine");

		buttonPanel.add(flipButton);
		buttonPanel.add(blurButton);
		buttonPanel.add(enhanceButton);
		buttonPanel.add(combineButton);

		cPane.add(buttonPanel, BorderLayout.SOUTH );

		flipButton.addActionListener(
			new ActionListener () {
				public void actionPerformed( ActionEvent e) {

					System.out.println("Flip");

				}
			}
		);

		blurButton.addActionListener(
			new ActionListener () {
				public void actionPerformed( ActionEvent e) {

					System.out.println("blur");

				}
			}
		);

		enhanceButton.addActionListener(
			new ActionListener () {
				public void actionPerformed( ActionEvent e) {

					System.out.println("enhanceButton");

				}
			}
		);

		combineButton.addActionListener(
			new ActionListener () {
				public void actionPerformed( ActionEvent e) {

					System.out.println("combine");

				}
			}
		);

	 }

	 private class Canvas extends JPanel {

	 	public Canvas () {
	 		setVisible(true);
	 	}

	 	@Override public Dimension getPreferredSize() {
	 		
	 		return bmp == null 	? new Dimension(DEFAULT_CANVAS_WIDTH, DEFAULT_CANVAS_HEIGHT)
	 							: new Dimension(bmp.getWidth(), bmp.getHeight());
	 	
	 	}

	 	@Override protected void paintComponent( Graphics g ) {
	 		super.paintComponent(g);
	 		System.out.println("paintComponent");
	 		g.setColor(Color.BLACK);
	 		g.fillRect( 100, 100, DEFAULT_CANVAS_WIDTH - 100, DEFAULT_CANVAS_HEIGHT - 100 );

	 	}
	 }

}


