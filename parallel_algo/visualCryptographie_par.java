//fileex: to run the code type in these commands in the command line:
//fileex: javac VisualCrpytographie_par.java
//fileex: java VisualCrpytographie_par.java

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException; 

public class VisualCrpytographie_par {
    static int image_height;
    static int image_width;

    //this code only works for black and white images!
    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("input_file.png"));

            image_height = image.getHeight();
            image_width = image.getWidth();

            //output images
            BufferedImage outputImage1 = new BufferedImage(image_width * 2, image_height * 2, BufferedImage.TYPE_INT_RGB);
            BufferedImage outputImage2 = new BufferedImage(image_width * 2, image_height * 2, BufferedImage.TYPE_INT_RGB);

            //"algo"
            for (int y = 0; y < image_height; y++) {
                for (int x = 0; x < image_width; x++) {
                    int pixel = image.getRGB(x, y);

                    if (pixel < -8388608) { //pixel is considered black (RGB value for black == -16777216)
                        //set 2x2 pixels in outputImage1
                        outputImage1.setRGB(x * 2, y * 2, -1); // white
                        outputImage1.setRGB(x * 2 + 1, y * 2, -16777216); // black
                        outputImage1.setRGB(x * 2, y * 2 + 1, -16777216); // black
                        outputImage1.setRGB(x * 2 + 1, y * 2 + 1, -1); // white

                        //set 2x2 pixels in outputImage2
                        outputImage2.setRGB(x * 2, y * 2, -16777216); // black
                        outputImage2.setRGB(x * 2 + 1, y * 2, -1); // white
                        outputImage2.setRGB(x * 2, y * 2 + 1, -1); // white
                        outputImage2.setRGB(x * 2 + 1, y * 2 + 1, -16777216); // black
                    } else { //pixel is considered white (RGB value for white == -1)
                        //set 2x2 pixels in outputImage1
                        outputImage1.setRGB(x * 2, y * 2, -16777216); // black
                        outputImage1.setRGB(x * 2 + 1, y * 2, -1); // white
                        outputImage1.setRGB(x * 2, y * 2 + 1, -1); // white
                        outputImage1.setRGB(x * 2 + 1, y * 2 + 1, -16777216); // black
                        
                        //set 2x2 pixels in outputImage2
                        outputImage2.setRGB(x * 2, y * 2, -16777216); // black
                        outputImage2.setRGB(x * 2 + 1, y * 2, -1); // white
                        outputImage2.setRGB(x * 2, y * 2 + 1, -1); // white
                        outputImage2.setRGB(x * 2 + 1, y * 2 + 1, -16777216); // black
                    }
                }
            }

            //save output images (if already file exists with this name, it will be overwritten)
            ImageIO.write(outputImage1, "png", new File("output1.png"));
            ImageIO.write(outputImage2, "png", new File("output2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}