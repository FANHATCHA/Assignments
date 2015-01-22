/**
 *
 * @author William Fiset & Micah Stairs
 * COMP 2631 (Winter 2015) Data Structures and Algorithms II
 * Assignment #1
 * Due: February, 2, 2015 
 * 
 **/

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class MoreFileStuff {

	/* Opens up a JFileChooser for the user to choose a file from their file system */
	private static File selectFile () {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Please choose a file");
		int returnVal = fileChooser.showOpenDialog(null);

		// User has finished selecting file
		if(returnVal == JFileChooser.APPROVE_OPTION) 
			return fileChooser.getSelectedFile();
		return null;
		
	} // selectFile


	/** 
	 * This method:
	 * - Prompts the user for the name of an input file
	 * - Prompts the user for the index of a byte to be modified in the file they chose
	 * - Then prompts user for a new byte value to be placed in that location
	 * @return void 
	 */
	public static void modifyBtyes() {

		File selectedFile = selectFile();

		if (selectedFile != null) {
			while (1 > 0) {
				
				long byteIndexSelected = -2; 
				long bytesInFile = selectedFile.length();
				
				// Gets the byte the user wishes the change in the file they selected
				while ( byteIndexSelected < 0 || byteIndexSelected > bytesInFile ) {
					
					String userInput = JOptionPane.showInputDialog( null, "Please enter the index of the byte you wish to manipulate in the range: [0, bytesInFile-1] " );

					try {
						byteIndexSelected = Integer.valueOf(userInput);
					} catch (Exception e) {
						// User entered invalid input
						byteIndexSelected = -2;
					}

				} // while

				if (byteIndexSelected == -1) break
				System.out.println("Byte to change: "+byteIndexSelected);
				
								

			} // outer while loop
		} 

	} // modifyBtyes

	public static void main(String[] args) {

		modifyBtyes();

	} // main

} // MoreFileStuff



















