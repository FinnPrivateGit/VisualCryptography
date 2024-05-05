//fileex: to run the code type in these commands in the command line:
//fileex: javac visualCryptographie_par.java
//fileex: java visualCryptographie_par.java

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class visualCryptographie_par {
    static int image_height;
    static int image_width;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        try {
            BufferedImage image = ImageIO.read(new File("input_file.png"));

            image_height = image.getHeight();
            image_width = image.getWidth();

            BufferedImage outputImage1 = new BufferedImage(image_width * 2, image_height * 2, BufferedImage.TYPE_INT_RGB);
            BufferedImage outputImage2 = new BufferedImage(image_width * 2, image_height * 2, BufferedImage.TYPE_INT_RGB);

            ExecutorService executor = Executors.newFixedThreadPool(12);
            CompletableFuture<?>[] futures = new CompletableFuture[12];

            for (int i = 0; i < 12; i++) {
                final int start = i * image_height / 12;
                final int end = (i + 1) * image_height / 12;

                futures[i] = CompletableFuture.runAsync(() -> {
                    for (int y = start; y < end; y++) {
                        for (int x = 0; x < image_width; x++) {
                            int pixel = image.getRGB(x, y);

                            if (pixel < -8388608) {
                                //set 2x2 pixels in outputImage1
                                outputImage1.setRGB(x * 2, y * 2, -1); // white
                                outputImage1.setRGB(x * 2 + 1, y * 2, -16777216); // black
                                outputImage1.setRGB(x * 2, y * 2 + 1, -16777216); // black
                                outputImage1.setRGB(x * 2 + 1, y * 2 + 1, -1); // white

                                //set 2x2 pixels in outputImage2
                                outputImage2.setRGB(x * 2, y * 2, -16777216); // black
                                outputImage2.setRGB(x * 2 + 1, y * 2, -1); // white
                                outputImage2.setRGB(x * 2, y * 2 + 1, -1); // white
                                outputImage2.setRGB(x * 2 + 1, y * 2 + 1, -16777216); // black pixel
                            } else {
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
                }, executor);
            }

            CompletableFuture.allOf(futures).join();

            executor.shutdown();

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