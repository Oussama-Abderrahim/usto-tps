package irdm.descriptors;

public class TextureDescriptor implements Descriptor {


    private double energy;
    private double inertia;
    private double entropie;
    private double moment;

    public TextureDescriptor(String descriptor) {
        this.parseDescriptor(descriptor);
    }

    public TextureDescriptor(double energy, double inertia, double entropie, double moment) {
        this.energy = energy;
        this.inertia = inertia;
        this.entropie = entropie;
        this.moment = moment;

        System.out.println(this.toString());
    }

    /**
     * Take a string representation of the descriptor, and make it into a Texture descriptor Object (or just the values)
     *
     * @param descriptor String : gotten from the database
     */
    public void parseDescriptor(String descriptor) {
        String[] values = descriptor.split(" ");
        this.energy = Double.parseDouble(values[0]);
        this.inertia = Double.parseDouble(values[1]);
        this.entropie = Double.parseDouble(values[2]);
        this.moment = Double.parseDouble(values[3]);
    }

    /**
     * Converts the descriptor into a string, to be saved to the database
     *
     * @return String
     */
    @Override
    public String toString() {
        return "" + energy + " " + inertia + " " + entropie + " " + moment;
    }

    /**
     * @param o2 the TextureDescriptor to compare to
     * @return the computed distance (one value)
     */
    @Override
    public double distance(Descriptor o2) {
        TextureDescriptor o = (TextureDescriptor) o2;

        return (
                paramsDistance(this.energy, o.energy)
                + paramsDistance(this.inertia, o.inertia)
                + paramsDistance(this.entropie, o.entropie)
                + paramsDistance(this.moment, o.moment)
                ) / 4;
    }

    private static double paramsDistance(double param1, double param2) {
        return Math.abs((param1 - param2) / param1);
    }
}
