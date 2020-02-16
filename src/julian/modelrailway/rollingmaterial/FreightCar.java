/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

public final class FreightCar extends Wagon {

    /**
     * @param visual
     * @param noC
     * @param id
     * @param length
     */
    public FreightCar(int noC, int id, int length) {
        super(noC, id, length);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public String[] getVisual() {
        String[] visual = {"   (O)        (O)   ", "|__________________|", "|                  |"
                , "|                  |", "|                  |"};
        return visual;
    }

}
