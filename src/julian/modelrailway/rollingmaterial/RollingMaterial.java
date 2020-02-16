/**
 * Allgemeine Klasse für ein rollendes Objekt.
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

public abstract class RollingMaterial {

    protected final int numberOfClutches;
    protected boolean used;
    protected final int length;

    public RollingMaterial( int noC, int length) {
        this.length = length;
        numberOfClutches = noC;
        used = false;
    }
    
    public abstract String[] getVisual();
    
    public int getVisualHeight() {
        return getVisual().length;
    }
    
    public boolean isUsed() {
        return used;
    }
    
    public void concat() {
        this.used = true;
    }
    
    public void unconcat() {
        this.used = false;
    }
    
    public abstract boolean equals(RollingMaterial o);
    
    
}
