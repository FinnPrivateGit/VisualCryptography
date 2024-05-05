//fileex: to run the code type in these commands in the command line:
//fileex: javac visualCryptographie_seq.java
//fileex: java visualCryptographie_seq.java

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException; 
import java.util.Random;

public class visualCryptographie_seq {
    static int image_height;
    static int image_width;

    //this code only works for black and white images!
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        
        try {
            BufferedImage image = ImageIO.read(new File("input_file.png"));

            Random rand = new Random();

            image_height = image.getHeight();
            image_width = image.getWidth();

            //output images
            BufferedImage outputImage1 = new BufferedImage(image_width * 2, image_height * 2, BufferedImage.TYPE_INT_RGB);
            BufferedImage outputImage2 = new BufferedImage(image_width * 2, image_height * 2, BufferedImage.TYPE_INT_RGB);

            //"algo"
            for (int y = 0; y < image_height; y++) {
                for (int x = 0; x < image_width; x++) {
                    int pixel = image.getRGB(x, y);

                    //get random number (1 or 2)
                    int randomNumber = rand.nextInt(2);

                    if (pixel < -8388608) { //pixel is considered black (RGB value for black == -16777216)
                        if (randomNumber == 0) {
                            //set 2x2 pixels in outputImage1
                            outputImage1.setRGB(x * 2, y * 2, -1); // white
                            outputImage1.setRGB(x * 2 + 1, y * 2, -16777216); // black
                            outputImage1.setRGB(x * 2, y * 2 + 1, -16777216); // black
                            outputImage1.setRGB(x * 2 + 1, y * 2 + 1, -1); // white
                        } else {
                            //set 2x2 pixels in outputImage2
                            outputImage2.setRGB(x * 2, y * 2, -16777216); // black
                            outputImage2.setRGB(x * 2 + 1, y * 2, -1); // white
                            outputImage2.setRGB(x * 2, y * 2 + 1, -1); // white
                            outputImage2.setRGB(x * 2 + 1, y * 2 + 1, -16777216); // black
                        }
                    } else { //pixel is considered white (RGB value for white == -1)
                        if (randomNumber == 0) {
                            //set 2x2 pixels in outputImage1
                            outputImage1.setRGB(x * 2, y * 2, -16777216); // black
                            outputImage1.setRGB(x * 2 + 1, y * 2, -1); // white
                            outputImage1.setRGB(x * 2, y * 2 + 1, -1); // white
                            outputImage1.setRGB(x * 2 + 1, y * 2 + 1, -16777216); // black
                        } else {
                            //set 2x2 pixels in outputImage2
                            outputImage2.setRGB(x * 2, y * 2, -16777216); // black
                            outputImage2.setRGB(x * 2 + 1, y * 2, -1); // white
                            outputImage2.setRGB(x * 2, y * 2 + 1, -1); // white
                            outputImage2.setRGB(x * 2 + 1, y * 2 + 1, -16777216); // black
                        }
                    }
                }
            }

            //save output images (if already file exists with this name, it will be overwritten)
            ImageIO.write(outputImage1, "png", new File("output1.png"));
            ImageIO.write(outputImage2, "png", new File("output2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("Execution time in milliseconds: " + elapsedTime);
    }
}