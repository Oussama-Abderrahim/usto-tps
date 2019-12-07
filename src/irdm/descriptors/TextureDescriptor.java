package irdm.descriptors;

public class TextureDescriptor implements Descriptor {


    public TextureDescriptor() {

    }

    /**
     * Take a string representation of the descriptor, and make it into a Texture descriptor Object (or just the values)
     * @param descriptor String : gotten from the database
     */
    public static void parseDescriptor(String descriptor) {

    }

    /**
     * Converts the descriptor into a string, to be saved to the database
     * @return String
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     *
     * @param o2 the TextureDescriptor to compare to
     * @return the computed distance (one value)
     */
    @Override
    public double distance(Descriptor o2) {
        /// TODO: Aya.
        return 0;
    }
}
