/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

public abstract class Coach extends RollingMaterial {

    protected final int id;

    public Coach(int length, boolean clutchFront, boolean clutchBack, int id) {
        super(length, clutchFront, clutchBack);
        this.id = id;
    }

    /**
     * @return die ID des Waggons
     */
    public int getID() {
        return id;
    }

    @Override
    public boolean equals(RollingMaterial p) {
        if (p instanceof Coach) {
            Coach w = (Coach) p;
            return w.getID() == getID();
        }
        return false;
    }

    @Override
    public String toString() {
        return getID() + " " + getTrain().getID() + " " + getType() + " " + getLength() + " " + isClutchFront() + " "
                + isClutchBack();
    }
    
    public abstract String getType();
    
    @Override
    public int compareTo(RollingMaterial o) {
        if(!(o instanceof Coach)) {
            return 0;
        }
        Coach po = (Coach) o;
        return po.getID() - this.getID(); //TODO check correct direction;
    }
    
    @Override
    public String getStringID() {
        return "" + getID();
    }
    
    @Override
    public String getWStringID() {
        return "W" + getID();
    }

}
