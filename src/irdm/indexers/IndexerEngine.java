package irdm.indexers;

import irdm.descriptors.Descriptor;

import java.awt.image.BufferedImage;

public interface IndexerEngine {

    public Descriptor getDescriptor(BufferedImage image);

}
