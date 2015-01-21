/**
 *
 * @author William Fiset & Micah Stairs
 * COMP 2631 (Winter 2015) Data Structures and Algorithms II
 * Assignment #1
 * Due: Feburary, 2, 2015 
 * 
 **/

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class MoreFileStuff {

	/* Opens up a JFileChooser for the user to choose a file from their file system */
	public static File selectFile () {

		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(null);

		if(returnVal == JFileChooser.APPROVE_OPTION) 
			return fileChooser.getSelectedFile();
		return null;
		
	}

	/** 
	 * This method:
	 * - Prompts the user for the name of an input file
	 * - Prompts the user for the index of a byte to be modified in the file they chose
	 * - Then prompts user for a new byte value to be placed in that location
	 * @return void 
	 */
	public static void modifyBtyes() {

	}

	public static void main(String[] args) {

		modifyBtyes();

	}

}
