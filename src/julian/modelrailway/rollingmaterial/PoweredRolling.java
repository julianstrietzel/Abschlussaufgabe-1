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
    public  PoweredRolling(String series, String name, int length, boolean clutchFront, boolean clutchBack) {
        super(length, clutchFront, clutchBack);
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
    
    @Override
    public int compareTo(RollingMaterial o) {
        if(!(o instanceof PoweredRolling)) {
            return 0;
        }
        PoweredRolling po = (PoweredRolling) o;
        return this.getID().compareTo(po.getID());
    }
    
    @Override
    public String getStringID() {
        return getID();
    }

}
