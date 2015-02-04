import java.awt.*;
import java.io.*;
import java.awt.image.*;

public class Bitmap implements Cloneable {

		/* Constants */

	private static final int DIMENSIONS_INDEX = 18;
	private static final int MAX_RGB_VALUE = 255;
	private static final int MIN_RGB_VALUE = 0;
	private static final int DATA_OFFSET = 54;

		/* Instance Variables */

	private int width;
	private int height;
	private int numPaddingBytes;
	private int[] header;
	private Color[][] pixels;
	
	public Bitmap(File file) throws IOException {
		readBitmap(file);
	}
	
	/**
	* Get the width of the image.
	* @return width
	*/
	public int getWidth() {
		return width;
	}
	
	/**
	* Get the height of the image.
	* @return height
	*/
	public int getHeight() {
		return height;
	}
	
	/**
	* Read in the .BMP from file, storing all needed information.
	* @param file - the input file
	*/
	public void readBitmap(File file) throws IOException {

		RandomAccessFile input = new RandomAccessFile(file, "r");

			/* Import header information */

		header = new int[DATA_OFFSET];
		for (int i = 0; i < DATA_OFFSET; i++)
			header[i] = input.read();

			/* Retrieve dimension information */
		
		input.seek(DIMENSIONS_INDEX);
		width           = getInt(input, 4);
		height          = getInt(input, 4);
		numPaddingBytes = width % 4;
		
			/* Read in pixel information */

		pixels = new Color[height][width];

		input.seek(DATA_OFFSET);

		for (int y = 0; y < height; y++) {
			
			// Read in the row of pixels
			for (int x = 0; x < width; x++) {

				int blue  = getInt(input, 1);
				int green = getInt(input, 1);
				int red   = getInt(input, 1);

				pixels[y][x] = makeValidColor(red, green, blue);
			}
			
			// Skip past padding
			for (int i = 0; i < numPaddingBytes; i++)
				input.read();
			
		}

		input.close();

	}

	/**
	* Output image into a .BMP file.
	* @param file - the output file
	*/
	public void writeBitmap(File file) throws IOException {

		RandomAccessFile output = new RandomAccessFile(file, "rw");

			/* Write header information */
		
		for (int i = 0; i < DATA_OFFSET; i++)
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
	
	/**
	* Flip the image vertically.
	*/
	public void flip() {

		Color[][] newPixels = new Color[height][width];
		
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				newPixels[y][x] = pixels[height - 1 - y][x];

		pixels = newPixels;

	}

	/**
	* Flip the image horizontally.
	*/
	public void flipHorizontally() {

		Color[][] newPixels = new Color[height][width];
		
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				newPixels[y][x] = pixels[y][width - x - 1];

		pixels = newPixels;

	}
	
	/**
	* Blur the image.
	* @param range - a positive integer (strength of the blur)
	*/
	public void blur(int range) {

		Color[][] newPixels = new Color[height][width];

		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				
				int count = 0;
				int red   = 0, green = 0, blue = 0;

					/* Calculate blurred pixel from neighbors */

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
				
					/* Create the blurred pixel */
				
				newPixels[y][x] = new Color(red/count, green/count, blue/count);

			}

		pixels = newPixels;

	}
	
	public void enhanceColor(String color) {
		
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {

					/* Get original values */

				int red = pixels[y][x].getRed();
				int green = pixels[y][x].getGreen();
				int blue = pixels[y][x].getBlue();
				
					/* Modify preferred color component */

				if (color.equals("red"))
					red *= 1.5;
				else if (color.equals("green"))
					green *= 1.5;
				else if (color.equals("blue"))
					blue *= 1.5;

					/* Create new pixel */

				pixels[y][x] = makeValidColor(red, green, blue);
				
			}

	}
	
	/**
	* Generate a BufferedImage of the bitmap.
	* @return the generated BufferedImage
	*/
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

	/**
	* Given RGB values, create a Color (ensuring no bad values will be passed through,
	* which would cause an error).
	* @param red - the red component
	* @param green - the green component
	* @param blue - the blue component
	* @return color
	*/
	private static Color makeValidColor(int red, int green, int blue) {

		red = Math.max(MIN_RGB_VALUE, Math.min(red, MAX_RGB_VALUE));
		green = Math.max(MIN_RGB_VALUE, Math.min(green, MAX_RGB_VALUE));
		blue = Math.max(MIN_RGB_VALUE, Math.min(blue, MAX_RGB_VALUE));

		return new Color(red, green, blue);

	}

    @Override protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
