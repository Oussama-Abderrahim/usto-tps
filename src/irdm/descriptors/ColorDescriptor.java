package irdm.descriptors;

import irdm.indexers.ColorIndexerEngine;

import java.awt.*;

public class ColorDescriptor implements Descriptor {

    private static final int DESCRIPTOR_MAX_SIZE = ColorIndexerEngine.M*3;
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
        for(int i = 0; i < histogram.length-1; i++)
            res.append(histogram[i]).append(",");
        res.append(histogram[histogram.length-1]);

        return res.toString();
    }

    public int[] parseDescriptor(String descriptor) {
        int[] array = new int[DESCRIPTOR_MAX_SIZE];

        String[] values = descriptor.split(",");

        for(int i = 0; i < array.length; i++)
        {
            array[i] = Integer.parseInt(values[i]);
        }

        this.histogram = array;
        return array;
    }

    @Override
    public int distance(Descriptor o2) {
        double sum = 0;
        int[] histogram2 = ((ColorDescriptor) o2).histogram;
        for(int i = 0; i < histogram.length && i < histogram2.length; i++)
        {
            sum += Math.abs(histogram[i] - histogram2[i]);
        }
        System.out.println("Distance " + sum/histogram.length);
        return (int) sum/histogram.length;
    }
}
