package irdm.indexers;

import irdm.DatabaseManager;
import irdm.descriptors.ColorDescriptor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ColorIndexerEngine implements IndexerEngine {

    public static final int M = 32;
    public static ColorIndexerEngine instance = null;

    public static ColorIndexerEngine getInstance() {
        if (instance == null)
            instance = new ColorIndexerEngine();
        return instance;
    }

    public ColorIndexerEngine() {
    }


    /**
     * =================================
     * JFreeChart part, to be moved
     */
    private static DefaultCategoryDataset createDataset(int[] arr, int[] arr2, int[] arr3) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < arr.length; i++) {
            dataset.addValue(arr[i], "" + i, "red");
        }
        for (int i = 0; i < arr2.length; i++) {
            dataset.addValue(arr2[i], "" + (i + arr.length), "blue");
        }
        for (int i = 0; i < arr3.length; i++) {
            dataset.addValue(arr3[i], "" + (i + arr.length + arr2.length), "green");
        }

        return dataset;
    }

    public static DefaultCategoryDataset getChartFromImage(BufferedImage image) {

        int[] reds = new int[M + 1];
        Arrays.fill(reds, 0);
        int[] blues = new int[M + 1];
        Arrays.fill(blues, 0);
        int[] greens = new int[M + 1];
        Arrays.fill(greens, 0);

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color c = new Color(image.getRGB(i, j));
                reds[c.getRed() * M / 255]++;
                blues[c.getBlue() * M / 255]++;
                greens[c.getGreen() * M / 255]++;
            }
        }

        return createDataset(reds, blues, greens);

    }

    public static JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Histogramme de Couleurs RGB", null /* x-axis label*/,
                "Occurences" /* y-axis label */, dataset);
//        chart.addSubtitle(new TextTitle("Time to generate 1000 charts in SVG "
//                + "format (lower bars = better performance)"));
        chart.setBackgroundPaint(null);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(null);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setSeriesFillPaint(0, Color.RED);
        renderer.setSeriesFillPaint(1, Color.BLUE);
        renderer.setSeriesFillPaint(2, Color.GREEN);

        chart.getLegend().setFrame(BlockBorder.NONE);
        chart.getLegend().setVisible(false);

        return chart;
    }

    /**
     * JFreeChart part END
     * =================================
     */

    public ArrayList<IndexedImage> fetchImagesBySimilarity(IndexedImage queryImage)
    {
        ResultSet resultSet = DatabaseManager.getInstance().fetchAllImages();

        ArrayList<IndexedImage> indexedImages = new ArrayList<>();

        try {
            while (resultSet.next()) {
                indexedImages.add(
                        new IndexedImage(
                                resultSet.getString("path"),
                                resultSet.getString("COLOR_DESCRIPTOR"),
                                "")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        indexedImages.sort(Comparator.comparingDouble(o -> queryImage.getColorDescriptor().distance(o.getColorDescriptor())));

        return indexedImages;
    }

    @Override
    public ColorDescriptor getDescriptor(BufferedImage image) {
        int[] reds = new int[M + 1];
        Arrays.fill(reds, 0);
        int[] blues = new int[M + 1];
        Arrays.fill(blues, 0);
        int[] greens = new int[M + 1];
        Arrays.fill(greens, 0);

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color c = new Color(image.getRGB(i, j));
                reds[c.getRed() * M / 255]++;
                blues[c.getBlue() * M / 255]++;
                greens[c.getGreen() * M / 255]++;
            }
        }

        return new ColorDescriptor(concatArrays(reds, greens, blues));
    }

    public static int[] concatArrays(int[] a, int[] b, int[] c) {
        int[] r = new int[a.length + b.length + c.length];
        int i = 0;
        for (int x : a) {
            r[i] = x;
            i++;
        }
        for (int x : b) {
            r[i] = x;
            i++;
        }
        for (int x : c) {
            r[i] = x;
            i++;
        }
        return r;
    }
}
