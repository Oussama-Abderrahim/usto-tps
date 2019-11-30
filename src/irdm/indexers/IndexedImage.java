package irdm.indexers;

import irdm.DatabaseManager;
import irdm.FileManager;
import irdm.descriptors.ColorDescriptor;
import irdm.descriptors.Descriptor;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class IndexedImage {

    public final static int DEFAULT_WIDTH = 400;
    public final static int DEFAULT_HEIGHT = 400;


    private BufferedImage image = null;
    private Descriptor textureDescriptor;
    private ColorDescriptor colorDescriptor;
    private String filePath;

    public IndexedImage(String filePath) {
        this();
        this.setFilePath(filePath);
    }

    public IndexedImage() {
        this.filePath = "";
        this.image = null;
        this.textureDescriptor = null;
        this.colorDescriptor = null;
    }

    public void setFilePath(String path) {
        this.filePath = path;
        this.image = FileManager.loadImage(filePath, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public boolean saveToDB() {
        int b = DatabaseManager.getInstance().insertImage(this.filePath, this.colorDescriptor.toString(), "");
        return b != 0;
    }

    public void loadImageFromChooser() {
        this.image = FileManager.loadImageFromChooser(DEFAULT_WIDTH, DEFAULT_HEIGHT, this);
        this.computeDescriptors();
    }

    private void computeDescriptors() {
        this.colorDescriptor = ColorIndexerEngine.getInstance().getDescriptor(this.image);
    }

    public ImageIcon getImageIcon() {
        return new ImageIcon(this.image);
    }

    public Descriptor getColorDescriptor() {
        return colorDescriptor;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public BufferedImage getImage() {
        return this.image;
    }
}
