/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

public abstract class PoweredRolling extends RollingMaterial {

    protected final String series;
    protected final String name;
    
    /**
     * 
     * @param visual
     * @param noC
     * @param series
     * @param name
     */
    public  PoweredRolling(int noC, String series, String name, int length) {
        super(noC, length);
        this.series = series;
        this.name = name;
    }
    
    public String getID() {
        return series + "-" + name;
    }
    
    public String getSeries() {
        return series;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public boolean equals(RollingMaterial p) {
        if(p instanceof PoweredRolling) {
            PoweredRolling pr = (PoweredRolling) p;
            return pr.getID().contentEquals(getID());
        }
        return false;
    }

}
