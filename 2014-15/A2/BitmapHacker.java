/**
 * BitmapHacker - Flips, enhances color, blurs, or combines images
 * @author Micah Stairs, William Fiset
 * Winter 2015
 * Assignment 2
 * Dr. Keliher
 */

import java.util.*;
import javax.swing.*;
import java.io.*;    

public class BitmapHacker {

	/**
	 * Combine two images of identical proportions together.
	 * @throws IOException
	 */
	private static void combine() throws IOException {
		
			/* Get input files */
		
		File inputFile1 = pickFile("Select input file #1");
		RandomAccessFile input1 = new RandomAccessFile(inputFile1, "r");
		File inputFile2 = pickFile("Select input file #2");
		RandomAccessFile input2 = new RandomAccessFile(inputFile2, "r");
		
			/* Read input files */

		Pixel[][] arr1 = getPixelArray(input1);
		int height1 = arr1.length;
		int width1 = arr1[0].length;
		Pixel[][] arr2 = getPixelArray(input2);
		int height2 = arr2.length;
		int width2 = arr2[0].length;
		
		if (width1 != width2 || height1 != height2) {
			System.out.println("The two files did not have the same dimensions.");
			return;
		}
		
			/* Get output file */

		System.out.println("Enter full path of output file:");
		String outputName = sc.nextLine();
		
			/* Create array for new image */

		Pixel[][] arr3 = new Pixel[height1][width1];
		
			/* Combine images */

		for (int y = 0; y < height1; y++)
			for (int x = 0; x < width1; x++) {
				
				int red = (arr1[y][x].getRed() + arr2[y][x].getRed())/2;
				int green = (arr1[y][x].getGreen() + arr2[y][x].getGreen())/2;
				int blue = (arr1[y][x].getBlue() + arr2[y][x].getBlue())/2;
				
				arr3[y][x] = new Pixel(blue, green, red);
			}
		
			/* Write the output */

		RandomAccessFile output = new RandomAccessFile(outputName, "rw");
		
		// Copy header information
		copyHeaderInfo(input1, output);
		
		// Copy new pixels in
		copyPixelArray(output, arr3);
		
		output.close();
		
	}
	
}
