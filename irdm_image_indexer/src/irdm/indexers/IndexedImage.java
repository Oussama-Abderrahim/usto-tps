package irdm.indexers;

import irdm.DatabaseManager;
import irdm.FileManager;
import irdm.descriptors.ColorDescriptor;
import irdm.descriptors.CompoundDescriptor;
import irdm.descriptors.Descriptor;
import irdm.descriptors.TextureDescriptor;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.util.ArrayList;

public class IndexedImage {

    public final static int DEFAULT_WIDTH = 400;
    public final static int DEFAULT_HEIGHT = 400;


    private BufferedImage image = null;
    private Descriptor textureDescriptor;
    private ColorDescriptor colorDescriptor;
    private String filePath;
    private String fileName;

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

    public IndexedImage(String path, String colorDescriptor, String textureDescriptor) {
        this();
        setFilePath(path);
        this.colorDescriptor = new ColorDescriptor(colorDescriptor);
        this.textureDescriptor = new TextureDescriptor(textureDescriptor);
    }

    public static ArrayList<IndexedImage> fetchAllImages() {
        ResultSet resultSet = DatabaseManager.getInstance().fetchAllImages();

        ArrayList<IndexedImage> indexedImages = new ArrayList<>();

        try {
            while (resultSet.next()) {
                indexedImages.add(
                        new IndexedImage(
                                resultSet.getString("path"),
                                resultSet.getString("COLOR_DESCRIPTOR"),
                                resultSet.getString("TEXTURE_DESCRIPTOR"))
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return indexedImages;
    }

    public void setFilePath(String path) {
        this.fileName = path;
        this.filePath = FileManager.IMAGE_FOLDER_PATH + path;
        this.image = FileManager.loadImage(filePath, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public boolean saveToDB() {
        int b = DatabaseManager.getInstance().insertImage(this.fileName, this.colorDescriptor.toString(), this.textureDescriptor.toString());
        return b != 0;
    }

    public void loadImageFromChooser() {
        this.image = FileManager.loadImageFromChooser(DEFAULT_WIDTH, DEFAULT_HEIGHT, this);
        this.computeDescriptors();
    }

    private void computeDescriptors() {
        this.colorDescriptor = ColorIndexerEngine.getInstance().getDescriptor(this.image);
        this.textureDescriptor = TextureIndexerEngine.getInstance().getDescriptor(this.image);
    }

    public ImageIcon getImageIcon() {
        return new ImageIcon(this.image);
    }

    public Descriptor getColorDescriptor() {
        return colorDescriptor;
    }

    public Descriptor getTextureDescriptor() {
        return textureDescriptor;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public Descriptor getCompoundDescriptor() {
        return new CompoundDescriptor((ColorDescriptor) this.getColorDescriptor(), (TextureDescriptor) this.getTextureDescriptor());
    }
}
