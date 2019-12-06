package irdm.descriptors;

import irdm.indexers.ColorIndexerEngine;

import java.awt.*;

public class ColorDescriptor implements Descriptor {

    private static final int DESCRIPTOR_MAX_SIZE = ColorIndexerEngine.M * 3 + 3;
    private int[] histogram;

    public ColorDescriptor(String descriptor) {
        this();
        parseDescriptor(descriptor);
    }

    public ColorDescriptor() {
        this.histogram = null;
    }

    public ColorDescriptor(int[] concatArrays) {
        this.histogram = concatArrays;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < histogram.length - 1; i++)
            res.append(histogram[i]).append(",");
        res.append(histogram[histogram.length - 1]);

        return res.toString();
    }

    public int[] parseDescriptor(String descriptor) {
        int[] array = new int[DESCRIPTOR_MAX_SIZE];

        String[] values = descriptor.split(",");

        for (int i = 0; i < values.length; i++) {
            array[i] = Integer.parseInt(values[i]);
        }

        this.histogram = array;
        return array;
    }

    public static int DISTANCE_ALGORITHM = 2;

    public static void setAlgorithm(int algo) {
        DISTANCE_ALGORITHM = algo;
    }

    @Override
    public double distance(Descriptor o2) {
        switch (DISTANCE_ALGORITHM) {
            case 0:
                return distanceS(o2);
            case 1:
                return distanceS2(o2);
            case 2:
                return distanceJeffrey(o2);
            default:
                return distanceMean(o2);
        }
    }

    public double distanceS(Descriptor o2) {
        int[] histogram2 = ((ColorDescriptor) o2).histogram;

        double sum = 0;
        double sumMin = 0;
        for (int i = 0; i < histogram.length && i < histogram2.length; i++) {
            sumMin += Math.min(histogram[i], histogram2[i]);
            sum += histogram[i];
        }
        return (sumMin / sum);
    }


    public double distanceJeffrey(Descriptor o2) {
        int[] histogram2 = ((ColorDescriptor) o2).histogram;

        double sum = 0;

        for (int i = 0; i < histogram.length && i < histogram2.length; i++) {
            double h1 = histogram[i] == 0 ? 0.00001 : histogram[i];
            double h2 = histogram2[i] == 0 ? 0.00001 : histogram2[i];

            sum += h1 * Math.log((2 * h1) / (h1 + h2)) + h2 * Math.log((2 * h2) / (h1 + h2));
        }

        return (sum);
    }

    public double distanceS2(Descriptor o2) {
        int[] histogram2 = ((ColorDescriptor) o2).histogram;

        double sum = 0;
        double sum2 = 0;
        double sumMin = 0;
        for (int i = 0; i < histogram.length && i < histogram2.length; i++) {
            sumMin += Math.min(histogram[i], histogram2[i]);
            sum += histogram[i];
            sum2 += histogram2[i];
        }
        return (sumMin / Math.min(sum, sum2));
    }

    public double distanceMean(Descriptor o2) {
        double sum = 0;
        int[] histogram2 = ((ColorDescriptor) o2).histogram;
        for (int i = 0; i < histogram.length && i < histogram2.length; i++) {
            sum += Math.abs(histogram[i] - histogram2[i]);
        }
        return sum / histogram.length;
    }
}
