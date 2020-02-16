/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

public final class PassengerWagon extends Wagon {

    /**
     * @param visual
     * @param noC
     * @param id
     */
    public PassengerWagon(int noC, int id, int length) {
        super(noC, id, length);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String[] getVisual() {
        String[] visual = {"   (O)        (O)   ", "|__________________|",
                "|__________________|",  "|  |_| |_| |_| |_| |", "|  ___ ___ ___ ___ |", "____________________"};
        return visual;
    }

}
