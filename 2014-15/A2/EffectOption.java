/**
* Effect Option - GUI element allowing you to contain both a slider (and its associated label),
* as well as a button. You can easily toggle between these two modes.
*
* Data Structures II : Assignment 2
* @author Micah Stairs and William Fiset
*/

import javax.swing.*;
import java.awt.*;

public class EffectOption extends JPanel {

	public JSlider slider;
	public JLabel label;
	public JButton button;
	
	public EffectOption() {

		label = new JLabel("");
		slider = new JSlider();
		button = new JButton("");

		label.setFont(new Font("Verdana", Font.BOLD, 12));
		button.setFont(new Font("Verdana", Font.BOLD, 12));

	}

	public void switchToButton() {

		removeAll();
		add(button);

	}

	public void switchToSlider() {

		removeAll();
		add(label);
		add(slider);

	}

}