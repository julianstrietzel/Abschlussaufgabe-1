/**
 * Allgemeine Klasse für ein rollendes Objekt.
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

public abstract class RollingMaterial implements Comparable<RollingMaterial>{
    
    protected boolean used;
    protected final int length;
    protected final boolean clutchFront;
    protected final boolean clutchBack;
    protected Train train;

    public RollingMaterial(int length, boolean clutchFront, boolean clutchBack) {
        this.length = length;
        this.clutchBack = clutchBack;
        this.clutchFront = clutchFront;
        used = false;
    }
    
    public int getVisualHeight() {
        return getVisual().length;
    }
    
    public boolean isUsed() {
        return used;
    }
    
    public void concat(Train t) {
        this.used = true;
        this.train = t;
    }
    
    public void unconcat() {
        this.used = false;
    }
    
    public int getLength() {
        return length;
    }
    
    public void SetTrain(Train t) {
        this.train = t;
    }
    
    public Train getTrain() {
        return train;
    }

    public int getNumberOfClutches() {
        int i = 0;
        if(clutchFront) {
            i++;
        }
        if(clutchBack) {
            i++;
        }
        return i;
    }
    
    public boolean isClutchFront() {
        return clutchFront;
    }

    public boolean isClutchBack() {
        return clutchBack;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
    
    public abstract String getStringID();
    
    public abstract String getTypeForAdding();

    public abstract boolean equals(RollingMaterial o);
    
    @Override
    public abstract String toString();
    
    @Override    
    public abstract int compareTo(RollingMaterial o);
    
    public abstract String[] getVisual();
    
    public String getWStringID() {
        return getStringID();
    }
    
    
}
