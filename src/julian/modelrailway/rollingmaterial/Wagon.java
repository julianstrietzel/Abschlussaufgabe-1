/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

public abstract class Wagon extends RollingMaterial {

    protected final int id;
    
    
    /**
     * @param visual
     * @param noC
     */
    public Wagon(int noC, int id, int length) {
        super(noC, length);
        this.id = id;
        // TODO Auto-generated constructor stub
    }
    
    /**
     * 
     * @return
     */
    public int getID() {
        return id;
    }
    
    @Override
    public boolean equals(RollingMaterial p) {
        if(p instanceof Wagon) {
            Wagon w = (Wagon) p;
            return w.getID() == getID();
        }
        return false;
    }

}
