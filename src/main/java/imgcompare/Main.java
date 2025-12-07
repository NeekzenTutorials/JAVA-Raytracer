package imgcompare;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Command-line tool to compare two images and produce a diff PNG.
 * Usage: java imgcompare.Main <image1> <image2> <imageDiffOut.png>
 * Prints the number of differing pixels and writes the visual diff image.
 */
public class Main {
    /**
     * Entry point.
     *
     * @param args CLI arguments:
     *             args[0] path to first image,
     *             args[1] path to second image,
     *             args[2] output path for the diff PNG.
     * Exits with code 1 on invalid usage or IO errors.
     */
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Usage : java imgcompare.Main <image1> <image2> <imageDiffOut.png>");
            System.exit(1);
        }

        String imgPath1 = args[0];
        String imgPath2 = args[1];
        String diffOutPath = args[2];

        try {
            BufferedImage img1 = ImageIO.read(new File(imgPath1));
            BufferedImage img2 = ImageIO.read(new File(imgPath2));

            if (img1 == null || img2 == null) {
                System.err.println("Erreur : impossible de lire une des images.");
                System.exit(1);
            }

            ImageComparator comparator = new ImageComparator();

            int diffCount = comparator.countDifferentPixels(img1, img2);
            BufferedImage diffImage = comparator.generateDiffImage(img1, img2);

            System.out.println("Nombre de pixels différents : " + diffCount);

            ImageIO.write(diffImage, "png", new File(diffOutPath));
            System.out.println("Image différentielle écrite dans : " + diffOutPath);

        } catch (IOException e) {
            System.err.println("Erreur de lecture/écriture : " + e.getMessage());
            System.exit(1);
        }
    }
}