package irdm.descriptors;

public class CompoundDescriptor implements Descriptor {

    public static final double COLOR_WEIGHT = 0.2;
    public static final double TEXTURE_WEIGHT = 1 - COLOR_WEIGHT;

    ColorDescriptor colorDescriptor;
    TextureDescriptor textureDescriptor;

    public CompoundDescriptor(ColorDescriptor colorDescriptor, TextureDescriptor textureDescriptor)
    {
        this.colorDescriptor = colorDescriptor;
        this.textureDescriptor = textureDescriptor;
    }

    public double distance(Descriptor o2) {
        CompoundDescriptor o = (CompoundDescriptor) o2;

        double colorDist = this.colorDescriptor.distance(o.colorDescriptor);
        double textureDist = this.textureDescriptor.distance(o.textureDescriptor);

        return COLOR_WEIGHT * colorDist + TEXTURE_WEIGHT * textureDist;
    }
}
