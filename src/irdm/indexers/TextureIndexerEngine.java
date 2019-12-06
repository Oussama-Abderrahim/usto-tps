package irdm.indexers;

import irdm.descriptors.TextureDescriptor;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TextureIndexerEngine implements IndexerEngine {

    public static TextureIndexerEngine instance = null;

    public static TextureIndexerEngine getInstance() {
        if (instance == null)
            instance = new TextureIndexerEngine();
        return instance;
    }

    @Override
    public TextureDescriptor getDescriptor(BufferedImage image) {
        return new TextureDescriptor();
    }

    @Override
    public ArrayList<IndexedImage> fetchImagesBySimilarity(IndexedImage queryImage) {
        return null;
    }
}
