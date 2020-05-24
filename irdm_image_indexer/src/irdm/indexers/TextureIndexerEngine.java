package irdm.indexers;

import irdm.descriptors.TextureDescriptor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class TextureIndexerEngine implements IndexerEngine {

    public static final int T = 32;
    public static TextureIndexerEngine instance = null;

    public static TextureIndexerEngine getInstance() {
        if (instance == null)
            instance = new TextureIndexerEngine();
        return instance;
    }

    /**
     * Compute the TextureDescriptor of an image
     *
     * @param image bufferedImage, can iterate on pixels
     * @return
     */
    @Override
    public TextureDescriptor getDescriptor(BufferedImage image) {
        int n = image.getHeight(), m = image.getWidth();

        double[][] grays = new double[n][m];

        // a.Calcul niveaux de gris et réduction
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Color c = new Color(image.getRGB(j, i));    // x is j , y is i
                grays[i][j] = (c.getRed() * 0.299 + c.getGreen() * 0.587 + c.getBlue() * 0.114) * T / 255;
            }
        }

        // matrices de cooccurence :
        double[][] coMat0 = cMatrix0(grays);
        double[][] coMat45 = cMatrix45(grays);
        double[][] coMat90 = cMatrix90(grays);
        double[][] coMat135 = cMatrix135(grays);

        // calcul de parametres

        double energy = (energie(coMat0) + energie(coMat45) + energie(coMat90) + energie(coMat135)) / 4;
        double inertia = (inertie(coMat0) + inertie(coMat45) + inertie(coMat90) + inertie(coMat135)) / 4;
        double entropie = (entropie(coMat0) + entropie(coMat45) + entropie(coMat90) + entropie(coMat135)) / 4;
        double moment = (mdi(coMat0) + mdi(coMat45) + mdi(coMat90) + mdi(coMat135)) / 4;

        return new TextureDescriptor(energy, inertia, entropie, moment);
    }

    @Override
    public ArrayList<IndexedImage> fetchImagesBySimilarity(IndexedImage queryImage) {
        ArrayList<IndexedImage> indexedImages = IndexedImage.fetchAllImages();

        indexedImages.sort(Comparator.comparingDouble(o -> queryImage.getTextureDescriptor().distance(o.getTextureDescriptor())));

        return indexedImages;
    }

    //Co-occurence matrix
    public double[][] cMatrix0(double[][] grays) {
        double[][] cMat = new double[T][T];
        for (int i = 0; i < cMat.length; i++) {
            Arrays.fill(cMat[i], 0);
        }

        for (int i = 0; i < grays.length; i++) {
            for (int j = 0; j < grays[0].length - 1; j++) {
                int x = (int) grays[i][j];
                int y = (int) grays[i][j + 1];
                cMat[x][y]++;
            }
        }

        return cMat;
    }

    public double[][] cMatrix45(double[][] grays) {
        double[][] cMat = new double[T][T];
        for (int i = 0; i < cMat.length; i++) {
            Arrays.fill(cMat[i], 0);
        }

        for (int i = 1; i < grays.length; i++) {
            for (int j = 0; j < grays[0].length - 1; j++) {
                int x = (int) grays[i][j];
                int y = (int) grays[i - 1][j + 1];
                cMat[x][y]++;
            }
        }

        return cMat;
    }

    public double[][] cMatrix90(double[][] grays) {
        double[][] cMat = new double[T][T];
        for (int i = 0; i < cMat.length; i++) {
            Arrays.fill(cMat[i], 0);
        }

        for (int i = 1; i < grays.length; i++) {
            for (int j = 0; j < grays[0].length; j++) {
                int x = (int) grays[i][j];
                int y = (int) grays[i - 1][j];
                cMat[x][y]++;
            }
        }

        return cMat;
    }

    public double[][] cMatrix135(double[][] grays) {
        double[][] cMat = new double[T][T];
        for (int i = 0; i < cMat.length; i++) {
            Arrays.fill(cMat[i], 0);
        }

        for (int i = 1; i < grays.length; i++) {
            for (int j = 1; j < grays[0].length; j++) {
                int x = (int) grays[i][j];
                int y = (int) grays[i - 1][j - 1];
                cMat[x][y]++;
            }
        }

        return cMat;
    }

    //Matrix's parameters
    public static double energie(double[][] grays) {
        double sum = 0;
        for (int i = 0; i < grays.length; i++) {
            for (int j = 0; j < grays.length; j++) {
                sum += grays[i][j] * grays[i][j];
            }
        }
        return sum;
    }

    public static double inertie(double[][] grays) {
        double sum = 0;

        for (int j = 0; j < T; j++) {
            for (int i = 0; i < T; i++) {
                sum += (i - j) * (i - j) * grays[i][j];
            }
        }
        return sum;
    }

    public static double entropie(double[][] grays) {
        double sum = 0;
        for (int i = 0; i < T; i++) {
            for (int j = 0; j < T; j++) {
                double p = grays[i][j] == 0? 0.00001 : grays[i][j];
                sum += grays[i][j] * Math.log(p);
            }
        }
        return -1 * sum;
    }

    public static double mdi(double[][] grays) {
        double sum = 0;
        for (int i = 0; i < T; i++) {
            for (int j = 0; j < T; j++) {
                double p = grays[i][j] == 0? 0.00001 : grays[i][j];
                sum += 1 / ((1 + (i - j) * (i - j)) * p);
            }
        }
        return sum;
    }

}
