public interface Descriptor {


    public String toString();

    public static void parseDescriptor(String descriptor) {

    }

    public double distance(Descriptor o2);

    public static double distance(Descriptor o1, Descriptor o2) {
        return o1.distance(o2);
    }


}
