import java.awt.*;
import java.io.*;
import java.awt.image.*;

public class Bitmap {

	static final int HEADER_SIZE = 54;
	static final int DIMENSION_INDEX = 18;
	static final int MAX_RGB_VALUE = 255;
	
	private int dataOffset;
	private int width;
	private int height;
	private int numPaddingBytes;
	private int[] header;
	private Color[][] pixels;
	
	public Bitmap(File file) throws IOException {
		
		readBitmap(file);

	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void readBitmap(File file) throws IOException {

		RandomAccessFile input = new RandomAccessFile(file, "r");

			/* Import header information */

		header = new int[HEADER_SIZE];
		for (int i = 0; i < HEADER_SIZE; i++)
			header[i] = input.read();

			/* Retrieve dimension information */
		
		input.seek(DIMENSION_INDEX);
		width = getInt(input, 4);
		height = getInt(input, 4);
		numPaddingBytes = width % 4;
		
			/* Read in pixel information */

		pixels = new Color[height][width];

		input.seek(HEADER_SIZE);

		for (int y = 0; y < height; y++) {
			
			// Read in the row of pixels
			for (int x = 0; x < width; x++) {
				int blue = getInt(input, 1);
				int green = getInt(input, 1);
				int red = getInt(input, 1);
				pixels[y][x] = new Color(red, green, blue);
			}
			
			// Skip past padding
			for (int i = 0; i < numPaddingBytes; i++)
				input.read();
			
		}

		input.close();

	}

	public void writeBitmap(File file) throws IOException {

		RandomAccessFile output = new RandomAccessFile(file, "rw");

			/* Write header information */
		
		for (int i = 0; i < HEADER_SIZE; i++)
			output.write(header[i]);

			/* Write pixel information */
		
		for (int y = 0; y < height; y++) {
			
			// Write pixel by pixel
			for (int x = 0; x < width; x++) {
				output.write(pixels[y][x].getBlue());
				output.write(pixels[y][x].getGreen());
				output.write(pixels[y][x].getRed());
			}
			
			// Skip past padding
			for (int i = 0; i < numPaddingBytes; i++)
				output.write(0);
						
		}

		output.close();

	}
	
	public void flip() {

		Color[][] newPixels = new Color[height][width];
		
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				newPixels[y][x] = pixels[height - 1 - y][x];

		pixels = newPixels;

	}
	
	public void blur(int range) {

		Color[][] newPixels = new Color[height][width];

		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				
				int count = 0;
				int red = 0, green = 0, blue = 0;

				for (int yOffset = -range; yOffset <= range; yOffset++)
					for (int xOffset = -range; xOffset <= range; xOffset++) {
						
						// We don't want to go out of bounds
						if (y + yOffset < 0 || x + xOffset < 0 || y + yOffset >= height || x + xOffset >= width)
							continue;

						// We only want to take the neighbors in consideration
						if (yOffset == 0 && xOffset == 0)
							continue;
						
						// Add the color values to our running totals
						red += pixels[y + yOffset][x + xOffset].getRed();
						green += pixels[y + yOffset][x + xOffset].getGreen();
						blue += pixels[y + yOffset][x + xOffset].getBlue();
						count++;

					}
				
				// Create the averaged pixel
				newPixels[y][x] = new Color(red/count, green/count, blue/count);

			}

		pixels = newPixels;

	}
	
	public void enhanceColor(String color) {
		
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {

				// Get original values
				int red = pixels[y][x].getRed();
				int green = pixels[y][x].getGreen();
				int blue = pixels[y][x].getBlue();
				
				// Modify preferred color component
				if (color.equals("red"))
					red *= 1.5;
				else if (color.equals("green"))
					green *= 1.5;
				else if (color.equals("blue"))
					blue *= 1.5;

				// Create new pixel
				pixels[y][x] = new Color(
					Math.min(red, MAX_RGB_VALUE),
					Math.min(green, MAX_RGB_VALUE),
					Math.min(blue, MAX_RGB_VALUE)
				);
				
			}

	}
	
	public BufferedImage getImage() {

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				image.setRGB(x, y, pixels[height - y - 1][x].getRGB());

		return image;
	}

	/**
	 * Read in an integer, given a file (and indicating how large the integer is in terms of bytes)
	 * @param raf - the input file
	 * @param nBytes - the number of bytes that the integer has
	 * @return the integer value of the bytes read
	 * @throws IOException
	 */
	private static int getInt(RandomAccessFile raf, int nBytes) throws IOException {
		
		int factor = 1;
		int total = 0;
		
		for (int i = 0; i < nBytes; i++) {
			total += raf.read()*factor;
			factor *= 256;
		}
		
		return total;
		
	}

}
