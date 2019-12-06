package irdm.indexers;

import irdm.descriptors.Descriptor;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public interface IndexerEngine {

    public Descriptor getDescriptor(BufferedImage image);

    public ArrayList<IndexedImage> fetchImagesBySimilarity(IndexedImage queryImage);

}
