import java.awt.Color;

/**
 * This class contains class (static) methods that will help you test the
 * Picture class methods. Uncomment the methods and the code in the main to
 * test. This is a great lesson for learning about 2D arrays and the Color
 * class.
 * 
 * @author Barbara Ericson
 */
public class PictureTester {

	private static final double MAX_WID = 1200;

	/**
	 * Main method for testing. Every class can have a main method in Java
	 */
	public static void main(String[] args) {
		/*
		 * You will write the methods that do the following
		 * 
		 */
     System.out.println("here!");
		/*testKeepOnlyBlue();
		testKeepOnlyRed();
		testKeepOnlyGreen();
		testNegate();
		testGrayscale();
		testEdgeDetection();
		testFixUnderwater();

		testChromakey();
		 testEncodeAndDecode(); // use png, gif or bmp because of compression
		 testGetCountRedOverValue(250);
		 testSetRedToHalfValueInTopHalf();
		 testClearBlueOverValue(200);
		 Picture pic = new Picture("images/beach.jpg");
		 testGetAverageForColumn(pic, 30);*/
	}

	private static void testKeepOnlyBlue() {
		// should get a fairly blue pic
		// this method will look a lot like testZeroBlue method
		Picture beach = new Picture("images/beach.jpg");
		beach.explore();// shows the picture in a windo

		Pixel[][] pixels = beach.getPixels2D();
		for (Pixel[] rowArray : pixels) {
			for (Pixel p : rowArray) {
				p.setRed(0);
        		p.setGreen(0);
			}
		}

		// shows the current version of the pic in a new window
		beach.explore();
	}

	private static void testKeepOnlyGreen() {
		Picture beach = new Picture("images/beach.jpg");
		beach.explore();

		Pixel[][] pixels = beach.getPixels2D();
		for (Pixel[] rowArray : pixels) {
			for (Pixel p : rowArray) {
				p.setRed(0);
				p.setBlue(0);
			}
		}
		beach.explore();
	}

	private static void testKeepOnlyRed() {
		Picture beach = new Picture("images/beach.jpg");
		beach.explore();

		Pixel[][] pixels = beach.getPixels2D();
		for (Pixel[] rowArray : pixels) {
			for (Pixel p : rowArray) {
				p.setBlue(0);
				p.setGreen(0);
			}
		}
		beach.explore();
	}

	/**
	 * Because this is a task commonly invoked on a Picture, it makes sense to add
	 * this method to the Picture class. If we are doing things that won't likely be
	 * used often, we can write these algorithms in this class.
	 */
	private static void testNegate() {
		Picture swan = new Picture("images/house.jpeg");
		// write this in Picture class
		swan.negate();
		swan.explore();

	}

	/**
	 * Again, like the method above, this is pretty common, so let's add this method
	 * to the Picture class.
	 */
	private static void testGrayscale() {
		Picture swan = new Picture("images/me.jpg");
		// write this method in Picture class
		swan.grayScale();
		swan.explore();
	}

	/** Method to test edgeDetection */
	public static void testEdgeDetection() {
		Picture swan = new Picture("images/house.jpeg");
		// written in Picture class
		swan.edgeDetection(10);// bigger number means fewer edges
		swan.explore();
		//swan.write("swan outline.jpg");// writes the new picture to a new file
	}

	/*
	 * so, you have a choice for this one and the methods that follow. You can write
	 * the code in the methods below or you can add functionality to the picture
	 * class. Sometimes it makes sense to add it to the class for reusability
	 * reasons. Sometimes it is too unique a need to add to the class.
	 */

	// So, you can create a Picture Object and find the average value of
	// the component in that column
	private static Color testGetAverageForColumn(Picture pic, int col) {
		Color avg = null;
		int totalRed = 0, totalGreen=0, totalBlue=0, count=0;
		Pixel[][] pixels = pic.getPixels2D();
		for (Pixel[] rowArray : pixels) {
			totalRed += rowArray[col].getRed();
			totalGreen += rowArray[col].getGreen();
			totalBlue += rowArray[col].getBlue();
			count++;
		}
		totalRed /= count;
		totalGreen /= count;
		totalBlue /= count;
		avg = new Color(totalRed, totalGreen, totalBlue);

		return avg;
	}

	// so for this one, any pixels that have blue over a certain value are set
	// to no blue at all. Or for a different effect, have those pixels set to black.
	private static void testClearBlueOverValue(int i) {
		Picture beach = new Picture("images/beach.jpg");
		Pixel[][] pixels = beach.getPixels2D();
		for (Pixel[] rowArray : pixels) {
			for (Pixel p : rowArray) {
				if (p.getBlue() > i) {
					p.setBlue(0);
				}
			}
		}

		// shows the current version of the pic in a new window
		beach.explore();
	}

	// goes to each pixel in the top half and cuts the red component in half
	// So, bottom half of pic should look normal
	private static void testSetRedToHalfValueInTopHalf() {
		Picture beach = new Picture("images/beach.jpg");

		Pixel[][] pixels = beach.getPixels2D();
		for (Pixel[] rowArray : pixels) {
			for (Pixel p : rowArray) {
				p.setRed(p.getRed() / 2);
				}
			}
		beach.explore();
		}


	// displays the number of pixels in the pic that have a red component
	// greater than the specifies int.
	private static void testGetCountRedOverValue(int i) {
		Picture beach = new Picture("images/beach.jpg");
		int count = 0;

		Pixel[][] pixels = beach.getPixels2D();
		for (Pixel[] rowArray : pixels) {
			for (Pixel p : rowArray) {
				if (p.getRed() > i) {
					count++;
				}
			}
		}
		System.out.println(count);
	}

	/**
	 * The method below is really cool!! Use the following approach: go through the
	 * entire Picture (the one to carry the message), and make the red component of
	 * every pixel an even number. To do this, mod by 2 and see if remainder>0. If
	 * so, add or subtract one (subtracting is safer. Why?) Then, using a Picture of
	 * a message (one color on white background), any pixel from Picture of message
	 * that is not white causes you to add one to the corresponding pixel's red
	 * component of the encoded picture (the one with all even red components).
	 * 
	 * Then you can send along the encoded picture to someone else and they should
	 * be able to decode it by looking for Pixels with odd red components.
	 */
	private static void testEncodeAndDecode() {
		Picture moto = new Picture("images/blueMotorcycle.jpg"),
				message = new Picture("images/msg3.PNG");

		moto.explore();
		message.explore();

		normalize(moto); // Ensure all red components are even
		hide(moto, message); // Embed the message within the image

		moto.explore(); // Verify the encoded image
		decode(moto).explore(); // Reveal the hidden message
	}

	private static SimplePicture decode(Picture moto) {
		Picture pic = new Picture(moto);
		Pixel[][] pixels = pic.getPixels2D();

		for (Pixel[] row : pixels) {
			for (Pixel p : row) {
				p.setColor((p.getRed() % 2 == 1) ? Color.BLACK : Color.WHITE); // Black for message, white otherwise
			}
		}
		return pic;
	}

	private static void hide(Picture pic, Picture msg) {
		Pixel[][] img = pic.getPixels2D(), msgp = msg.getPixels2D();

		for (int r = 0; r < Math.min(img.length, msgp.length); r++) {
			for (int c = 0; c < Math.min(img[0].length, msgp[0].length); c++) {
				Pixel imgPixel = img[r][c];
				Pixel msgPixel = msgp[r][c];

				imgPixel.setRed((msgPixel.getColor().getRed() < 200) ? 255 : (imgPixel.getRed() & ~1)); // Force max red for message pixels

			}
		}
	}

	private static void normalize(Picture moto) {
		Pixel[][] pixels = moto.getPixels2D();
		for (Pixel[] row : pixels) {
			for (Pixel p : row) {
				p.setRed(p.getRed() & ~50); // Ensure red component is even
			}
		}
	}


	/**
	 * chroma key means to superimpose one image over another. The image to be drawn
	 * over the other one, only draws the pixels that aren't the chroma key The
	 * common name for this is green screen
	 */

	private static void testChromakey() {
		Picture background = new Picture("images/butterfly1.jpg"),
		        foreground = new Picture("images/spidey.jpg");

		foreground = foreground.scale(.3, .3);
		// foreground.copy(foreground, 0, 100);

		background.explore();
		foreground.explore();
		Pixel[][] img = background.getPixels2D(), fgd = foreground.getPixels2D();
		int row = 245, col = 360, offsetR = 0, offsetC = 100;
		for (int r = 0; r < fgd.length - offsetR - 1; r++) {
			for (int c = 0; c < fgd[0].length - offsetC - 1; c++) {
				Pixel p = fgd[r + offsetR][c + offsetC];
				if (p.colorDistance(Color.green) > 60) {
					try {
						img[row + r][col + c].setColor(p.getColor());
					} catch (Exception e) {

					}
				}
			}
		}
		background.explore();
	}

	/**
	 * This method is an effort to make a Picture taken underwater look more like it
	 * would be if the water were drained
	 */
	private static void testFixUnderwater() {
		Picture pic = new Picture("images/water.jpg");
		Pixel[][] pixels = pic.getPixels2D();
		long total = 0;
		int num = 0;
		for (Pixel[] rowArray : pixels)
		{
			for (Pixel pixelObj : rowArray)
			{
				int ave = (pixelObj.getBlue() + pixelObj.getRed() + pixelObj.getGreen()) / 3;
				total += ave;
				num++;
			}
		}
		int ave = (int) (total /= num);
		for (Pixel[] rowArray : pixels)
		{
			for (Pixel pixelObj : rowArray)
			{
				pixelObj.setBlue(2 * (pixelObj.getBlue() - ave));
				pixelObj.setRed(2 * (pixelObj.getRed() - ave));
				pixelObj.setGreen(2 * (pixelObj.getGreen() - ave));
			}
		}
		// shows the current version of the pic in a new window
		pic.explore();
	}
}