package imgcompare;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageComparator {
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
