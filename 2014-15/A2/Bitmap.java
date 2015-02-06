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

	// Creates an empty Bitmap file of the correct dimension.
	public Bitmap(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		pixels = new Color[height][width];

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
	* Apply an edge detection effect on the image.
	* @param precision - the precision you want on your edge detection (default is 10)
	*/
	public void edgeDetection (final int precision) {
		
		Color[][] newPixels = new Color[height][width];

		for (int y = 0; y < height; y++ ) {
			for (int x = 0; x < width; x++) {
				
				if (y < height-1 && x < width-1) {
					
					// Get Neighboring pixels
					Color px      = pixels[y][x];
					Color rightPx = pixels[y][x+1];
					Color belowPx = pixels[y+1][x];

					// Find luminance
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
	 * Applies a pixelizing effect to the image.
	 * @param cellSize - the cell size of each pixel cluster
	 */
	public void mosaic(int cellSize) {

		if ( cellSize < Math.max(height, width) && cellSize != 1 ) {

			// This nested loop treats each cell as though it were its own image
			for (int y = 0; y < height; y += cellSize) {
				for (int x = 0; x < width; x += cellSize) {

					int avgRed = 0, avgGreen = 0, avgBlue = 0;
					ArrayList <Color> cellPixels = getPixelsInRange( pixels, x, y, x+cellSize, y+cellSize );

					// Average each Color
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
	 * @para p1 - first image you wish to combine 
	 * @para p2 - the second image you wish to combine 
	 * @return - A combined image of the rgb values
	 **/
	public static Bitmap combine(Bitmap p1, Bitmap p2) {

		int newWidth  = Math.max( p1.getWidth(), p2.getWidth() );
		int newHeight = Math.max( p1.getHeight(), p2.getHeight() );

		Bitmap newBitmap = new Bitmap(newWidth, newHeight);

		// Adding an alpha channel to this black background makes it
		// comparable against other black pixel using the .equals method
		final Color backgroundColor = new Color(0,0,0,1);

		// Fill background 
		for (int y = 0; y < newHeight; y++) 
			for (int x = 0; x < newWidth; x++) 
				newBitmap.pixels[y][x] = backgroundColor;
		
		// Create new Header
		int [] newHeader = p1.header.clone();

		// Change dimension in header
		newHeader[DIMENSIONS_INDEX] = newWidth;
		newHeader[DIMENSIONS_INDEX+1] = newHeight;

		newBitmap.header = newHeader;

		// Add first image
		for (int y = 0; y < p1.getHeight(); y++) 
			for (int x = 0; x < p1.getWidth(); x++) 
				newBitmap.pixels[y][x] = p1.pixels[y][x];

		// Combine other image
		for (int y = 0; y < p2.getHeight(); y++) {
			for (int x = 0; x < p2.getWidth(); x++) {
				
				Color px = newBitmap.pixels[y][x];
				
				// Writes on top of background
				if (px.equals(backgroundColor)) {
					newBitmap.pixels[y][x] = p2.pixels[y][x];

				// Average pixel colors
				} else {
					
					int r = 0, g = 0, b = 0;
					Color px2 = p2.pixels[y][x];
					
					r = (px2.getRed() + px.getRed()) / 2;
					g = (px2.getGreen() + px.getGreen()) / 2;
					b = (px2.getBlue() + px.getBlue()) / 2;
					
					newBitmap.pixels[y][x] = makeValidColor(r, g, b);

				}

			}
		}

		return newBitmap;
	}

	/**
	* Add a grayscale effect to the image.
	* @param factor - intensity of the grayscale, value should be in the range [0.0f, 1.0f]
	*/
	public void grayscale(float factor) {

		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {

					/* Calculate grayscale (based on specified factor) */
				
				int total = pixels[y][x].getRed() + pixels[y][x].getGreen() + pixels[y][x].getBlue();
				float avg = total/3.0f;

				float red   = (avg * factor) + (pixels[y][x].getRed()   * (1.0f - factor));
				float green = (avg * factor) + (pixels[y][x].getGreen() * (1.0f - factor));
				float blue  = (avg * factor) + (pixels[y][x].getBlue()  * (1.0f - factor));
				
					/* Create the grayscaled pixel */
				
				pixels[y][x] = makeValidColor((int) red, (int) green, (int) blue);

			}

	}

	/**
	* Add a invert effect to the image.
	*/
	public void invert() {

		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {

					/* Calculate inverted values */
				
				int red = 255 - pixels[y][x].getRed();
				int green = 255 - pixels[y][x].getGreen();
				int blue = 255 - pixels[y][x].getBlue();

					/* Create the inverted pixel */
				
				pixels[y][x] = makeValidColor(red, green, blue);

			}

	}

	/**
	* Add a swirl effect to the image.
	*/
	public void swirl() {
		double a = 5.0;
		double b = 100.0;

		/*
			a = amount of rotation
			b = size of effect

			angle = a*exp(-(x*x+y*y)/(b*b));
			u = cos(angle)*x + sin(angle)*y;
			v = -sin(angle)*x + cos(angle)*y;
		*/

		int cx = width/2;
		int cy = height/2;

		Color[][] newPixels = new Color[height][width];

		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {

				// int centerX = x + 30;
				//int centerY = y + height/2;

				// x = 0, y = height - 1
				// x = width/2, y = height/2

				double r = Math.sqrt( (cx-x)*(cx-x) + (cy-y)*(cy-y) );


				double angle = ((width + height) - r)/1000.0;//a*Math.exp(-(x*x + y*y)/(b*b));
				//double angle = //a*Math.exp(-(x*x + y*y)/(b*b));
				double u = Math.cos(angle)*x + Math.sin(angle)*y + cx;
				double v = -Math.sin(angle)*x + Math.cos(angle)*y + cy;

					/* Create the inverted pixel */
				v = Math.max(0, Math.min(height - 1, v + width/2));
				u = Math.max(0, Math.min(width - 1, u + width/2));
				newPixels[y][x] = pixels[(int)v][(int)u];

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
						red   += pixels[y + yOffset][x + xOffset].getRed();
						green += pixels[y + yOffset][x + xOffset].getGreen();
						blue  += pixels[y + yOffset][x + xOffset].getBlue();
						count++;

					}
				
					/* Create the blurred pixel */
				
				newPixels[y][x] = makeValidColor(red/count, green/count, blue/count);

			}

		pixels = newPixels;

	}
	
	/**
	* Enhances the specified color.
	* @param redFactor - The amount of % you wish to increase the red component by
	* @param greenFactor - The amount of % you wish to increase the green component by
	* @param blueFactor - The amount of % you wish to increase the blue component by
	*/
	public void enhanceColor( float redFactor, float greenFactor, float blueFactor ) {
		
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {

					/* Modify preferred color component */

				int red   = (int) ( pixels[y][x].getRed()   * redFactor );
				int green = (int) ( pixels[y][x].getGreen() * greenFactor );
				int blue  = (int) ( pixels[y][x].getBlue()  * blueFactor );

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
