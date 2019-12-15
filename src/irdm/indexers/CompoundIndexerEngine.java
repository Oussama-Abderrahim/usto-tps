package irdm.indexers;

import irdm.descriptors.ColorDescriptor;
import irdm.descriptors.CompoundDescriptor;
import irdm.descriptors.Descriptor;
import irdm.descriptors.TextureDescriptor;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

public class CompoundIndexerEngine implements IndexerEngine {

    public static CompoundIndexerEngine instance = null;

    public static CompoundIndexerEngine getInstance() {
        if (instance == null)
            instance = new CompoundIndexerEngine();
        return instance;
    }

    @Override
    public Descriptor getDescriptor(BufferedImage image) {
        return new CompoundDescriptor(
                ColorIndexerEngine.getInstance().getDescriptor(image),
                TextureIndexerEngine.getInstance().getDescriptor(image)
        );
    }

    @Override
    public ArrayList<IndexedImage> fetchImagesBySimilarity(IndexedImage queryImage) {
        ArrayList<IndexedImage> indexedImages = IndexedImage.fetchAllImages();

        indexedImages.sort(Comparator.comparingDouble(o -> queryImage.getCompoundDescriptor().distance(o.getCompoundDescriptor())));

        return indexedImages;
    }
}
