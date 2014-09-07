/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes
 *  a PixImage object.  Descriptions of the methods you must implement appear
 *  below.  They include constructors of the form
 *
 *      public RunLengthEncoding(int width, int height);
 *      public RunLengthEncoding(int width, int height, int[] red, int[] green,
 *                               int[] blue, int[] runLengths) {
 *      public RunLengthEncoding(PixImage image) {
 *
 *  that create a run-length encoding of a PixImage having the specified width
 *  and height.
 *
 *  The first constructor creates a run-length encoding of a PixImage in which
 *  every pixel is black.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts a PixImage object into a run-length encoding of that image.
 *
 *  See the README file accompanying this project for additional details.
 */

import java.util.Arrays;
import java.util.Iterator;

public class RunLengthEncoding implements Iterable {

	/**
	 *  Define any variables associated with a RunLengthEncoding object here.
	 *  These variables MUST be private.
	 */


	private int width;
	private int height;
	private DList list;


	/**
	 *  The following methods are required for Part II.
	 */

	/**
	 *  RunLengthEncoding() (with two parameters) constructs a run-length
	 *  encoding of a black PixImage of the specified width and height, in which
	 *  every pixel has red, green, and blue intensities of zero.
	 *
	 *  @param width the width of the image.
	 *  @param height the height of the image.
	 */

	public RunLengthEncoding(int width, int height) {
		list=new DList();
		this.width=width;
		this.height=height;
		list.insertBack(width*height, 0, 0, 0);
	}



	/**
	 *  RunLengthEncoding() (with six parameters) constructs a run-length
	 *  encoding of a PixImage of the specified width and height.  The runs of
	 *  the run-length encoding are taken from four input arrays of equal length.
	 *  Run i has length runLengths[i] and RGB intensities red[i], green[i], and
	 *  blue[i].
	 *
	 *  @param width the width of the image.
	 *  @param height the height of the image.
	 *  @param red is an array that specifies the red intensity of each run.
	 *  @param green is an array that specifies the green intensity of each run.
	 *  @param blue is an array that specifies the blue intensity of each run.
	 *  @param runLengths is an array that specifies the length of each run.
	 *
	 *  NOTE:  All four input arrays should have the same length (not zero).
	 *  All pixel intensities in the first three arrays should be in the range
	 *  0...255.  The sum of all the elements of the runLengths array should be
	 *  width * height.  (Feel free to quit with an error message if any of these
	 *  conditions are not met--though we won't be testing that.)
	 */

	public RunLengthEncoding(int width, int height, int[] red, int[] green,
			int[] blue, int[] runLengths) {
		list=new DList();
		this.width=width;
		this.height=height;
		for(int i=0;i<runLengths.length;i++){
			list.insertBack(runLengths[i],red[i],green[i],blue[i]);
		}


	}

	/**
	 *  getWidth() returns the width of the image that this run-length encoding
	 *  represents.
	 *
	 *  @return the width of the image that this run-length encoding represents.
	 */

	public int getWidth() {
		// Replace the following line with your solution.
		return this.width;
	}

	/**
	 *  getHeight() returns the height of the image that this run-length encoding
	 *  represents.
	 *
	 *  @return the height of the image that this run-length encoding represents.
	 */
	public int getHeight() {
		// Replace the following line with your solution.
		return this.height;
	}

	/**
	 *  iterator() returns a newly created RunIterator that can iterate through
	 *  the runs of this RunLengthEncoding.
	 *
	 *  @return a newly created RunIterator object set to the first run of this
	 *  RunLengthEncoding.
	 */
	public RunIterator iterator() {
		RunIterator i=new RunIterator(list);
		return i;

		// You'll want to construct a new RunIterator, but first you'll need to
		// write a constructor in the RunIterator class.
	}

	/**
	 *  toPixImage() converts a run-length encoding of an image into a PixImage
	 *  object.
	 *
	 *  @return the PixImage that this RunLengthEncoding encodes.
	 */
	public PixImage toPixImage() {
		PixImage pix= new PixImage(width, height);
		int[][] pixelarr=new int[width*height][3];
		int[] codearr;

		RunIterator iter=this.iterator();
		int walker=0;
		while(iter.hasNext()){
			codearr=iter.next();
			for(int j=0;j<codearr[0];j++){
				for(int k=0;k<=2;k++){
					pixelarr[j+walker][k]=codearr[k+1];	
				}
			}
			walker=walker+codearr[0];

			//  System.out.println(Arrays.toString(codearr));
		}
		//until the first run's runLength is down to 0, then move to the next run

		for(int m=0;m<height;m++){
			for(int n=0;n<width;n++){
				pix.setPixel(n,m,(short)pixelarr[m*width+n][0],(short)pixelarr[m*width+n][1],(short)pixelarr[m*width+n][2]);
			}
		}
		//Start at 0,0 and keep using setPixel(int x, int y, short red, short green, short blue)
		//System.out.println(pix.toString());
		return pix;
	}


	/**
	 *  toString() returns a String representation of this RunLengthEncoding.
	 *
	 *  This method isn't required, but it should be very useful to you when
	 *  you're debugging your code.  It's up to you how you represent
	 *  a RunLengthEncoding as a String.
	 *
	 *  @return a String representation of this RunLengthEncoding.
	 */
	public String toString() {
		String a="";
		RunIterator iter=this.iterator();
		while(iter.hasNext()){
			a=a+Arrays.toString(iter.next());
		}
		return a;
	}



	/**
	 *  The following methods are required for Part III.
	 */

	/**
	 *  RunLengthEncoding() (with one parameter) is a constructor that creates
	 *  a run-length encoding of a specified PixImage.
	 * 
	 *  Note that you must encode the image in row-major format, i.e., the second
	 *  pixel should be (1, 0) and not (0, 1).
	 *
	 *  @param image is the PixImage to run-length encode.
	 */
	public RunLengthEncoding(PixImage image) {
		list=new DList();
		height=image.getHeight();
		width=image.getWidth();

		for(int k=0;k<height;k++){
			for(int j=0;j<width;j++){
				list.insertBack(1,image.getRed(j,k),image.getGreen(j,k),image.getBlue(j,k));
			}
		}
		list.compact();
		check();
		//		System.out.println(image.toString());
	}

	/**
	 *  check() walks through the run-length encoding and prints an error message
	 *  if two consecutive runs have the same RGB intensities, or if the sum of
	 *  all run lengths does not equal the number of pixels in the image.
	 */
	public void check() {
		RunIterator iter=this.iterator();
		int[] prevObj = iter.next();
		int totalLength = prevObj[0];
		if (prevObj[0] < 1) {
			System.err.println("A run has a length less than 1 and it is:"+prevObj[0]);
		}
		while(iter.hasNext()) {
			int[] currObj = iter.next();
			totalLength += currObj[0];
			if (currObj[0] < 1) {
				System.err.println("A run has a length less than 1 and it is:"+currObj[0]);
			}
			if (currObj[1] == prevObj[1] && currObj[2] == prevObj[2] && currObj[3] == prevObj[3]) {
				System.err.println("two consecutive runs have the same RGB intensities!");
			}
			prevObj = currObj;
		}
		if(totalLength != width*height){
			System.err.println("The sum of all the run lengths does not equal the size (in pixels) of the PixImage!\nAnd the wrong value is "
				+totalLength);
		}
	}

	/**
	 *  The following method is required for Part IV.
	 */

	/**
	 *  setPixel() modifies this run-length encoding so that the specified color
	 *  is stored at the given (x, y) coordinates.  The old pixel value at that
	 *  coordinate should be overwritten and all others should remain the same.
	 *  The updated run-length encoding should be compressed as much as possible;
	 *  there should not be two consecutive runs with exactly the same RGB color.
	 *
	 *  @param x the x-coordinate of the pixel to modify.
	 *  @param y the y-coordinate of the pixel to modify.
	 *  @param red the new red intensity to store at coordinate (x, y).
	 *  @param green the new green intensity to store at coordinate (x, y).
	 *  @param blue the new blue intensity to store at coordinate (x, y).
	 */
	public void setPixel(int x, int y, short red, short green, short blue) {
		int num = y * width + x;
		int[] node;
		int sum = 0;

		RunIterator spot = this.iterator();
		do {
			node = spot.next();
			sum += node[0];
		} while(sum <= num);


		if (node[0]==1) { // runLength == 1
			list.insert(1,red,green,blue,spot.getPointer());
			list.remove(spot.getPointer());
		} else if ((sum-1) == num) {
			list.insert(1,red, green, blue,spot.getPointer());
			list.insert(node[0]-1,node[1],node[2],node[3],spot.getPointer());
			list.remove(spot.getPointer());
		} else if (num==sum-node[0]) { 
			list.insert(node[0]-1,node[1],node[2],node[3],spot.getPointer());	
			list.insert(1,red, green, blue,spot.getPointer());	
			list.remove(spot.getPointer());
		} else {
			int a=sum-num-1;
			list.insert(a,node[1],node[2],node[3],spot.getPointer());
			list.insert(1,red, green, blue,spot.getPointer());
			list.insert(node[0]-a-1,node[1],node[2],node[3],spot.getPointer());
			list.remove(spot.getPointer());
		}

		list.compact();
		check();
	}


	/**
	 * TEST CODE:  YOU DO NOT NEED TO FILL IN ANY METHODS BELOW THIS POINT.
	 * You are welcome to add tests, though.  Methods below this point will not
	 * be tested.  This is not the autograder, which will be provided separately.
	 */


	/**
	 * doTest() checks whether the condition is true and prints the given error
	 * message if it is not.
	 *
	 * @param b the condition to check.
	 * @param msg the error message to print if the condition is false.
	 */
	private static void doTest(boolean b, String msg) {
		if (b) {
			System.out.println("Good.");
		} else {
			System.err.println(msg);
		}
	}

	/**
	 * array2PixImage() converts a 2D array of grayscale intensities to
	 * a grayscale PixImage.
	 *
	 * @param pixels a 2D array of grayscale intensities in the range 0...255.
	 * @return a new PixImage whose red, green, and blue values are equal to
	 * the input grayscale intensities.
	 */
	private static PixImage array2PixImage(int[][] pixels) {
		int width = pixels.length;
		int height = pixels[0].length;
		PixImage image = new PixImage(width, height);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setPixel(x, y, (short) pixels[x][y], (short) pixels[x][y],
						(short) pixels[x][y]);
			}
		}
		//		System.out.println(image.toString());
		return image;
	}

	/**
	 * setAndCheckRLE() sets the given coordinate in the given run-length
	 * encoding to the given value and then checks whether the resulting
	 * run-length encoding is correct.
	 *
	 * @param rle the run-length encoding to modify.
	 * @param x the x-coordinate to set.
	 * @param y the y-coordinate to set.
	 * @param intensity the grayscale intensity to assign to pixel (x, y).
	 */
	private static void setAndCheckRLE(RunLengthEncoding rle,
			int x, int y, int intensity) {
		rle.setPixel(x, y,
				(short) intensity, (short) intensity, (short) intensity);
		rle.check();
	}

	/**
	 * main() runs a series of tests of the run-length encoding code.
	 */
	public static void main(String[] args) {
		// Be forwarned that when you write arrays directly in Java as below,
		// each "row" of text is a column of your image--the numbers get
		// transposed.

		PixImage image1 = array2PixImage(new int[][] { { 0, 3, 6 },
				{ 1, 4, 7 },
				{ 2, 5, 8 } });

		System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
				"on a 3x3 image.  Input image:");
		System.out.print(image1);
		RunLengthEncoding rle1 = new RunLengthEncoding(image1);
		rle1.check();
		System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
		doTest(rle1.getWidth() == 3 && rle1.getHeight() == 3,
				"RLE1 has wrong dimensions");

		System.out.println("Testing toPixImage() on a 3x3 encoding.");
		doTest(image1.equals(rle1.toPixImage()),
				"image1 -> RLE1 -> image does not reconstruct the original image");

		System.out.println("Testing setPixel() on a 3x3 encoding.");
		setAndCheckRLE(rle1, 0, 0, 42);
		image1.setPixel(0, 0, (short) 42, (short) 42, (short) 42);
		doTest(rle1.toPixImage().equals(image1),
				/*
					   array2PixImage(new int[][] { { 42, 3, 6 },
													{ 1, 4, 7 },
													{ 2, 5, 8 } })),
				 */
				"Setting RLE1[0][0] = 42 fails.");

		System.out.println("Testing setPixel() on a 3x3 encoding.");
		setAndCheckRLE(rle1, 1, 0, 42);
		image1.setPixel(1, 0, (short) 42, (short) 42, (short) 42);
		doTest(rle1.toPixImage().equals(image1),
				"Setting RLE1[1][0] = 42 fails.");

		System.out.println("Testing setPixel() on a 3x3 encoding.");
		setAndCheckRLE(rle1, 0, 1, 2);
		image1.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
		doTest(rle1.toPixImage().equals(image1),
				"Setting RLE1[0][1] = 2 fails.");

		System.out.println("Testing setPixel() on a 3x3 encoding.");
		setAndCheckRLE(rle1, 0, 0, 0);
		image1.setPixel(0, 0, (short) 0, (short) 0, (short) 0);
		doTest(rle1.toPixImage().equals(image1),
				"Setting RLE1[0][0] = 0 fails.");

		System.out.println("Testing setPixel() on a 3x3 encoding.");
		setAndCheckRLE(rle1, 2, 2, 7);
		image1.setPixel(2, 2, (short) 7, (short) 7, (short) 7);
		doTest(rle1.toPixImage().equals(image1),
				"Setting RLE1[2][2] = 7 fails.");

		System.out.println("Testing setPixel() on a 3x3 encoding.");
		setAndCheckRLE(rle1, 2, 2, 42);
		image1.setPixel(2, 2, (short) 42, (short) 42, (short) 42);
		doTest(rle1.toPixImage().equals(image1),
				"Setting RLE1[2][2] = 42 fails.");

		System.out.println("Testing setPixel() on a 3x3 encoding.");
		setAndCheckRLE(rle1, 1, 2, 42);
		image1.setPixel(1, 2, (short) 42, (short) 42, (short) 42);
		doTest(rle1.toPixImage().equals(image1),
				"Setting RLE1[1][2] = 42 fails.");


		PixImage image2 = array2PixImage(new int[][] { { 2, 3, 5 },
				{ 2, 4, 5 },
				{ 3, 4, 6 } });

		System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
				"on another 3x3 image.  Input image:");
		System.out.print(image2);
		RunLengthEncoding rle2 = new RunLengthEncoding(image2);
		rle2.check();
		System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
		doTest(rle2.getWidth() == 3 && rle2.getHeight() == 3,
				"RLE2 has wrong dimensions");

		System.out.println("Testing toPixImage() on a 3x3 encoding.");
		doTest(rle2.toPixImage().equals(image2),
				"image2 -> RLE2 -> image does not reconstruct the original image");

		System.out.println("Testing setPixel() on a 3x3 encoding.");
		setAndCheckRLE(rle2, 0, 1, 2);
		image2.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
		doTest(rle2.toPixImage().equals(image2),
				"Setting RLE2[0][1] = 2 fails.");

		System.out.println("Testing setPixel() on a 3x3 encoding.");
		setAndCheckRLE(rle2, 2, 0, 2);
		image2.setPixel(2, 0, (short) 2, (short) 2, (short) 2);
		doTest(rle2.toPixImage().equals(image2),
				"Setting RLE2[2][0] = 2 fails.");


		PixImage image3 = array2PixImage(new int[][] { { 0, 5 },
				{ 1, 6 },
				{ 2, 7 },
				{ 3, 8 },
				{ 4, 9 } });

		System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
				"on a 5x2 image.  Input image:");
		System.out.print(image3);
		RunLengthEncoding rle3 = new RunLengthEncoding(image3);
		rle3.check();
		System.out.println("Testing getWidth/getHeight on a 5x2 encoding.");
		doTest(rle3.getWidth() == 5 && rle3.getHeight() == 2,
				"RLE3 has wrong dimensions");

		System.out.println("Testing toPixImage() on a 5x2 encoding.");
		doTest(rle3.toPixImage().equals(image3),
				"image3 -> RLE3 -> image does not reconstruct the original image");

		System.out.println("Testing setPixel() on a 5x2 encoding.");
		setAndCheckRLE(rle3, 4, 0, 6);
		image3.setPixel(4, 0, (short) 6, (short) 6, (short) 6);
		doTest(rle3.toPixImage().equals(image3),
				"Setting RLE3[4][0] = 6 fails.");

		System.out.println("Testing setPixel() on a 5x2 encoding.");
		setAndCheckRLE(rle3, 0, 1, 6);
		image3.setPixel(0, 1, (short) 6, (short) 6, (short) 6);
		doTest(rle3.toPixImage().equals(image3),
				"Setting RLE3[0][1] = 6 fails.");

		System.out.println("Testing setPixel() on a 5x2 encoding.");
		setAndCheckRLE(rle3, 0, 0, 1);
		image3.setPixel(0, 0, (short) 1, (short) 1, (short) 1);
		doTest(rle3.toPixImage().equals(image3),
				"Setting RLE3[0][0] = 1 fails.");


		PixImage image4 = array2PixImage(new int[][] { { 0, 3 },
				{ 1, 4 },
				{ 2, 5 } });

		System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
				"on a 3x2 image.  Input image:");
		System.out.print(image4);
		RunLengthEncoding rle4 = new RunLengthEncoding(image4);
		rle4.check();
		System.out.println("Testing getWidth/getHeight on a 3x2 encoding.");
		doTest(rle4.getWidth() == 3 && rle4.getHeight() == 2,
				"RLE4 has wrong dimensions");

		System.out.println("Testing toPixImage() on a 3x2 encoding.");
		doTest(rle4.toPixImage().equals(image4),
				"image4 -> RLE4 -> image does not reconstruct the original image");

		System.out.println("Testing setPixel() on a 3x2 encoding.");
		setAndCheckRLE(rle4, 2, 0, 0);
		image4.setPixel(2, 0, (short) 0, (short) 0, (short) 0);
		doTest(rle4.toPixImage().equals(image4),
				"Setting RLE4[2][0] = 0 fails.");

		System.out.println("Testing setPixel() on a 3x2 encoding.");
		setAndCheckRLE(rle4, 1, 0, 0);
		image4.setPixel(1, 0, (short) 0, (short) 0, (short) 0);
		doTest(rle4.toPixImage().equals(image4),
				"Setting RLE4[1][0] = 0 fails.");

		System.out.println("Testing setPixel() on a 3x2 encoding.");
		setAndCheckRLE(rle4, 1, 0, 1);
		image4.setPixel(1, 0, (short) 1, (short) 1, (short) 1);
		doTest(rle4.toPixImage().equals(image4),
				"Setting RLE4[1][0] = 1 fails.");
	}
}
