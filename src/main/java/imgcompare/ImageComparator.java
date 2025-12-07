package imgcompare;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Utilities for comparing two images:
 * - countDifferentPixels: counts per-pixel differences (including size mismatch penalty).
 * - generateDiffImage: produces an ARGB image highlighting differing pixels in red.
 * Comparison is performed over the overlapping region when sizes differ.
 */
public class ImageComparator {

    /**
     * Counts the number of differing pixels between two images.
     * Compares the overlapping area and adds the difference in total pixel counts
     * when image sizes differ.
     *
     * @param img1 first image (ARGB or any BufferedImage type)
     * @param img2 second image (ARGB or any BufferedImage type)
     * @return number of differing pixels, including size mismatch penalty
     */
    public int countDifferentPixels(BufferedImage img1, BufferedImage img2) {
        int width = Math.min(img1.getWidth(), img2.getWidth());
        int height = Math.min(img1.getHeight(), img2.getHeight());

        int diffCount = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                    diffCount++;
                }
            }
        }

        diffCount += Math.abs(img1.getWidth() * img1.getHeight() - img2.getWidth() * img2.getHeight());

        return diffCount;
    }

    /**
     * Generates an ARGB diff image where differing pixels are marked in opaque red,
     * and identical pixels are transparent. The output size matches the overlapping area.
     *
     * @param img1 first image
     * @param img2 second image
     * @return an ARGB BufferedImage highlighting differences
     */
    public BufferedImage generateDiffImage(BufferedImage img1, BufferedImage img2) {
        int width = Math.min(img1.getWidth(), img2.getWidth());
        int height = Math.min(img1.getHeight(), img2.getHeight());

        BufferedImage diff = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Color sameColor = new Color(0, 0, 0, 0);       // transparent
        Color diffColor = new Color(255, 0, 0, 255);   // rouge

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                    diff.setRGB(x, y, diffColor.getRGB());
                } else {
                    diff.setRGB(x, y, sameColor.getRGB());
                }
            }
        }

        return diff;
    }
}
