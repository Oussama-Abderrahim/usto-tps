package irdm.indexers;

import irdm.descriptors.TextureDescriptor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class TextureIndexerEngine implements IndexerEngine {

    public static final int L = 32;
    public static TextureIndexerEngine instance = null;

    public static TextureIndexerEngine getInstance() {
        if (instance == null)
            instance = new TextureIndexerEngine();
        return instance;
    }

    /**
     * Compute the TextureDescriptor of an image
     * @param image bufferedImage, can iterate on pixels
     * @return
     */
    @Override
    public TextureDescriptor getDescriptor(BufferedImage image) {
        int n = image.getWidth(), m = image.getHeight();
        double[][] grays = new double[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Color c = new Color(image.getRGB(i, j));
                grays[i][j] = (c.getRed() * 0.299 + c.getGreen() * 0.587 + c.getBlue() * 0.114) * L / 255;
            }
        }
        return new TextureDescriptor();
    }

    @Override
    public ArrayList<IndexedImage> fetchImagesBySimilarity(IndexedImage queryImage) {
        ArrayList<IndexedImage> indexedImages = IndexedImage.fetchAllImages();

        indexedImages.sort(Comparator.comparingDouble(o -> queryImage.getTextureDescriptor().distance(o.getTextureDescriptor())));

        return indexedImages;
    }

    //Co-occurence matrix
    public double[][] cMatrix0 (double grays[][]){
        double [][] cGrays = new double[grays.length][grays[0].length];
        Arrays.fill(cGrays, 0);

        for (int x = 0 ; x < L ; x++){
            for (int i = 0 ; i < grays.length ; i++){
                for (int j = 0 ; j < grays[0].length ; j++) {
                    if (grays[i][j] == x && grays[i][j] == grays[i + 1][j])
                        cGrays[i][j]++;
                }
            }
        }
        return cGrays;
    }

    public double[][] cMatrix45 (double grays[][]){
        double [][] cGrays = new double[grays.length][grays[0].length];
        Arrays.fill(cGrays, 0);

        for (int x = 0 ; x < L ; x++){
            for (int i = 0 ; i < grays.length ; i++){
                for (int j = 0 ; j < grays[0].length ; j++) {
                    if (grays[i][j] == x && grays[i][j] == grays[i + 1][j])
                        cGrays[i][j]++;
                }
            }
        }
        return cGrays;
    }

    //Matrix's parameters
    public static double energie(double grays[][]){
        double sum = 0;
        for (int i =0;i<grays.length;i++){
            for (int j=0;j<grays.length;j++){
                sum += grays[i][j] *grays[i][j];
            }
        }
        return sum;
    }

    public static double inertie(double grays[][]){
        double sum=0;
        for (int i =0;i<grays.length;i++){
            for (int j=0;j<grays.length;j++){
                sum += (i-j)*(i-j)*grays[i][j];
            }
        }
        return sum;
    }

    public static double entropie(double grays[][]){
        double sum=0;
        for (int i =0;i<grays.length;i++){
            for (int j=0;j<grays.length;j++){
                sum += grays[i][j] * Math.log(grays[i][j]);
            }
        }
        return sum;
    }

    public static double mdi(double grays[][]){
        double sum=0;
        for (int i =0;i<grays.length;i++){
            for (int j=0;j<grays.length;j++){
                sum += 1/((1+ (i-j)*(i-j))*grays[i][j]);
            }
        }
        return sum;
    }

}
