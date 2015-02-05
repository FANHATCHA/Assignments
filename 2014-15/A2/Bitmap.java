import java.awt.*;
import java.io.*;
import java.awt.image.*;
import java.util.*;

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
	
	private float luminance(int r, int g, int b) {
		return (r+g+b)/3.0f;
	}

	/** Returns the pixels in a certain Rectangle of the image  **/
	private ArrayList<Color> getPixelsInRange( Color [][] arr, int x1, int y1, int x2, int y2 ) {

		ArrayList<Color> newArr = new ArrayList<Color>();

		for (int y = y1; y < y2; y++) 
			for (int x = x1; x < x2; x++) 
				if (x < width && y < height) 
					newArr.add( arr[y][x] );

		return newArr;

	}

	/**
	* Edge Detection.
	* Default: precision = ≈10.0f;
	*/
	public void edgeDetection ( final float precision ) {
		
		Color[][] newPixels = new Color[height][width];

		for (int y = 0; y < height; y++ ) {
			for (int x = 0; x < width; x++) {
				
				if (y < height-1 && x < width-1) {
					
					// Get Neighboring pixels
					Color px      = pixels[y][x];
					Color rightPx = pixels[y][x+1];
					Color belowPx = pixels[y+1][x];

					// find luminance
					float lumCur   = luminance(px.getRed(), px.getGreen(), px.getBlue());
					float lumRight = luminance(rightPx.getRed(), rightPx.getGreen(), rightPx.getBlue());
					float lumBelow = luminance(belowPx.getRed(), belowPx.getGreen(), belowPx.getBlue());

					// Check for edge detection
					if ( Math.abs(lumRight - lumCur) > precision || Math.abs(lumBelow - lumCur) > precision ) {
						newPixels[y][x] = Color.BLACK;
					} else {
						newPixels[y][x] = Color.WHITE;
					}
				
				} else {
					newPixels[y][x] = Color.WHITE;
				}


			}
		}

		pixels = newPixels;
	}
	
	/**
	 *    
	 * Creates a Mosaic picture effect.
	 * If the cellSize is large than the image then the original image is not touched.   
	 * 
	 * @param cellSize - the cellsize of each pixel cluster
	 */
	public void mosaic( int cellSize ) {

		if ( cellSize < Math.max(height, width) && cellSize != 1 ) {

			// This nested loop treats each cell as though it were its own image
			for (int y = 0; y < height; y += cellSize) {
				for (int x = 0; x < width; x += cellSize) {

					int avgRed = 0, avgGreen = 0, avgBlue = 0;
					ArrayList <Color> cellPixels = getPixelsInRange( pixels, x, y, x+cellSize, y+cellSize );

					for (Color color : cellPixels) {
						avgRed   += color.getRed(); 	
						avgGreen += color.getGreen();
						avgBlue  += color.getBlue();
					} 

					avgRed   /= cellPixels.size();
					avgGreen /= cellPixels.size();
					avgBlue  /= cellPixels.size();

					Color avgColor = new Color(avgRed, avgGreen, avgBlue);
					
					// Average each cell 
					for ( int cy = y; cy < y + cellSize; cy++) {
						for ( int cx = x; cx < x + cellSize ; cx++ ) {
							
							// Accounts for non evenly divisible cell sizes
							if (cx < width && cy < height) 
								pixels[cy][cx] = avgColor;

						}
					}
					

				} // outer x
			} // outer y

		} 

	} // mosaic

	/**
	* Flip the image vertically.
	*/
	public void flipVertically() {

		Color[][] newPixels = new Color[height][width];
		
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				newPixels[y][x] = pixels[height - 1 - y][x];

		pixels = newPixels;

	}

	/**
	* Add a grayscale effect to the image.
	* @param factor - intensity of the grayscale, value should be in the range [0.0f, 1.0f]
	*/
	public void grayscale(float factor) {

		Color[][] newPixels = new Color[height][width];

		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {

					/* Calculate grayscale (based on specified factor) */
				
				int total = pixels[y][x].getRed() + pixels[y][x].getGreen() + pixels[y][x].getBlue();
				float avg = total/3.0f;

				float red   = (avg * factor) + (pixels[y][x].getRed()   * (1.0f - factor));
				float green = (avg * factor) + (pixels[y][x].getGreen() * (1.0f - factor));
				float blue  = (avg * factor) + (pixels[y][x].getBlue()  * (1.0f - factor));
				
					/* Create the grayscaled pixel */
				
				newPixels[y][x] = makeValidColor((int) red, (int) green, (int) blue);

			}

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
	* Rotate the image by 90 degrees counter-clockwise.
	*/
	public void rotateCounterClockwise() {

			/* Update image properties */

		int oldHeight = height;
		int oldWidth = width;

		width = oldHeight;
		height = oldWidth;
		numPaddingBytes = width % 4;

			/* Update header information */

		// Switch height and width values
		for (int i = 0; i < 4; i++) {
			int temp = header[DIMENSIONS_INDEX + i];
			header[DIMENSIONS_INDEX + i] = header[DIMENSIONS_INDEX + i + 4];
			header[DIMENSIONS_INDEX + 4 + i] = temp;
		}

			/* Rotate image */

		Color[][] newPixels = new Color[height][width];
		
		for (int y = 0; y < oldHeight; y++)
			for (int x = 0; x < oldWidth; x++)
				newPixels[x][y] = pixels[oldHeight - y - 1][x];

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
				
				newPixels[y][x] = makeValidColor(red/count, green/count, blue/count);

			}

		pixels = newPixels;

	}
	
	/**
	* Ehances the specified color.
	* @param color - "red", "green", or "blue"
	* @param factor - 0.0f would be 0% (no preferred color remaining)
	*               - 1.0f would be 100% (no change)
	*               - 1.5f would be 150% (50% enhancement)
	*               - >255.0f would be redundant since all values would be 0 or 255 by that point
	*/
	public void enhanceColor(String color, float factor) {
		
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {

					/* Get original values */

				int red = pixels[y][x].getRed();
				int green = pixels[y][x].getGreen();
				int blue = pixels[y][x].getBlue();
				
					/* Modify preferred color component */

				if (color.equals("red"))
					red   = (int) (red * factor);
				else if (color.equals("green"))
					green = (int) (green * factor);
				else if (color.equals("blue"))
					blue  = (int) (blue  * factor);

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

        Bitmap clonedBitmap = (Bitmap) super.clone();

        Color[][] copyOfPixels = new Color[height][width];

        for (int y = 0; y < height; y++)
        	for (int x = 0; x < width; x++)
        		copyOfPixels[y][x] = new Color(pixels[y][x].getRGB());

        clonedBitmap.pixels = copyOfPixels;

        return (Object) clonedBitmap;

    }

}