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


	/**
	 * Hexadecimal to decimal conversion for two digit hex numbers
	 * @param hexStr - A two digit hexadecimal string
	 * @return - the decimal value in base ten of that hexadecimal
	 **/
	private static int hexToByte(final String hexStr) {
		
		if (isValidHexString(hexStr)) {
			
			int left  = hexDigitToIntVal(hexStr.charAt(0)) ;
			int right = hexDigitToIntVal(hexStr.charAt(1)) ;
			
			return 16*left + right;
		}
		return -1;
	}

	/** 
	 * Determines whether or not hexStr is a valid two digit hexadecimal value 
	 * @param hexStr - A hexadecimal String
	 * @return true if valid 2 digit hex string, false otherwise
	 **/
	private static boolean isValidHexString(final String hexStr) {

		if ( hexStr != null && hexStr.length() == 2) {

			// Grabs the ASCII values for both characters 
			int left = (int) hexStr.charAt(0);
			int right = (int) hexStr.charAt(1);
			
			//             0-9                       A-F                       a-f  
			if ( (left>=48 && left<= 57) || (left>=65 && left<=70) || (left>=97 && left<=102) ) {
				if ( (right>=48 && right<= 57) || (right>=65 && right<=70) || (right>=97 && right<=102) ) {
					return true;
				}
			}

		} // outer if

		return false;
	}

	/** 
	 * Opens up a JFileChooser for the user to choose a file from their file system 
	 * @return A file the user selected on there computer and null if they didn't choose anything
	 */
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
	 * - Then prompts user for a new byte value to be replaced at that location
	 * @return void 
	 */
	public static void modifyBytes() {

		File selectedFile = selectFile();

		if (selectedFile != null) {

			outerLoop:
			while (1 > 0) {
				
				long byteIndexSelected = -2; 
				long bytesInFile = selectedFile.length();
				
				// Gets the byte the user wishes the change in the file they selected
				while ( byteIndexSelected < 0 || byteIndexSelected > bytesInFile ) {
					
					String userInput = JOptionPane.showInputDialog( null, "Enter the index of the byte you wish to manipulate in the range: [0, bytesInFile-1] " );
					if (userInput == null || userInput.equals("-1")) break outerLoop;

					try {
						byteIndexSelected = Integer.valueOf(userInput);
					} catch (Exception e) { }

				} // while


				System.out.println("Change byte at index: "+byteIndexSelected);
				String hexStr = null;

				// Gets a two digit hexadecimal number from the user
				while ( !isValidHexString(hexStr) ) {
					hexStr = JOptionPane.showInputDialog( null, "Enter a two digit hexadecimal number:" );
					if (hexStr == null || hexStr.equals("-1")) break outerLoop;
				}

				int newByteValue = hexToByte(hexStr);
				System.out.println("Hex Number entered: "+ hexStr + " = " + newByteValue );

				try {
					// Change Byte value in file
					RandomAccessFile byteChanger = new RandomAccessFile(selectedFile, "rw");

					byteChanger.seek(byteIndexSelected);
					byteChanger.write(newByteValue);
					byteChanger.close();

				} catch (Exception error) {
					error.printStackTrace();
				} // try block end

			} // outer while loop
		} 

	} // modifyBtyes

	/**
	 * Converts a single hex digit to decimal
	 * @param - a single hex digit
	 * @return - The decimal representation of that digit, -1 if this fails
	 **/
	private static int hexDigitToIntVal(final char digit) {
		
		switch (digit) {

			// Digits
			case '0': return 0;
			case '1': return 1;
			case '2': return 2;
			case '3': return 3;
			case '4': return 4;
			case '5': return 5;
			case '6': return 6;
			case '7': return 7;
			case '8': return 8;
			case '9': return 9;

			// Lowercase
			case 'a': return 10;
			case 'b': return 11;
			case 'c': return 12;
			case 'd': return 13;
			case 'e': return 14;
			case 'f': return 15;

			// Uppercase
			case 'A': return 10;
			case 'B': return 11;
			case 'C': return 12;
			case 'D': return 13;
			case 'E': return 14;
			case 'F': return 15;

		}

		return -1;
	}

	/* Runs tests against methods created */
	public static void runTests() {

			/* hexToByte tests */

		// Valid input
		System.out.println( hexToByte("4A") == 74 ? "PASS" : "FAIL" );
		System.out.println( hexToByte("4a") == 74 ? "PASS" : "FAIL" );
		System.out.println( hexToByte("00") == 0 ? "PASS" : "FAIL" );
		System.out.println( hexToByte("13") == 19 ? "PASS" : "FAIL" );
		System.out.println( hexToByte("AA") == 170 ? "PASS" : "FAIL" );
		System.out.println( hexToByte("aA") == 170 ? "PASS" : "FAIL" );
		System.out.println( hexToByte("F5") == 245 ? "PASS" : "FAIL" );
		System.out.println( hexToByte("FF") == 255 ? "PASS" : "FAIL" );
		System.out.println( hexToByte("56") == 86 ? "PASS" : "FAIL" );

		// Invalid input
		System.out.println( hexToByte("") == -1 ? "PASS" : "FAIL" );
		System.out.println( hexToByte("FFF") == -1 ? "PASS" : "FAIL" );
		System.out.println( hexToByte("F%") == -1 ? "PASS" : "FAIL" );
		System.out.println( hexToByte("004") == -1 ? "PASS" : "FAIL" );

			/* isPrime tests */

		// Primes
		System.out.println( isPrime(2) ? "PASS" : "FAIL" );
		System.out.println( isPrime(3) ? "PASS" : "FAIL" );
		System.out.println( isPrime(5) ? "PASS" : "FAIL" );
		System.out.println( isPrime(7) ? "PASS" : "FAIL" );
		System.out.println( isPrime(5915587277L) ? "PASS" : "FAIL" );
		System.out.println( isPrime(9576890767L) ? "PASS" : "FAIL" );
		System.out.println( isPrime(3367900313L) ? "PASS" : "FAIL" );
		System.out.println( isPrime(2860486313L) ? "PASS" : "FAIL" );

		// Non-Primes
		System.out.println( !isPrime(1) ? "PASS" : "FAIL" );
		System.out.println( !isPrime(4)? "PASS" : "FAIL" );
		System.out.println( !isPrime(6) ? "PASS" : "FAIL" );
		System.out.println( !isPrime(3367900323L) ? "PASS" : "FAIL" );
		System.out.println( !isPrime(3367902313L) ? "PASS" : "FAIL" );
		System.out.println( !isPrime(9367902313L) ? "PASS" : "FAIL" );
		System.out.println( !isPrime(8102412317L) ? "PASS" : "FAIL" );

	}

	/* Deterministic primality test */
	public static boolean isPrime(final long n) {

			/* Simple cases (which covers 2/3 of possible factors of n) */

		if (n < 2)
			return false;
		if (n == 2 || n == 3)
			return true;
		if (n % 2 == 0 || n % 3 == 0)
			return false;

			/* Check the remaining 1/3 possible factors of n */
		
		int limit = (int) Math.sqrt(n);

		for (int i = 5; i <= limit; i += 6)
			if (n % i == 0 || n % (i + 2) == 0)
				return false;

		return true;

	} 

	public static void main(String[] args) {

		// UNCOMMENT TO RUN TESTS
		// runTests();

		modifyBytes();

	} // main

} // MoreFileStuff



















