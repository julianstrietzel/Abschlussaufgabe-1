/**
 * Repräsentiert einen Wagon.
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;


public abstract class Coach extends RollingMaterial {

    private final int id;

    /**
     * Erstellt einen neuen Wagon.
     * @param length    Läneg des Wagons
     * @param clutchFront   Ob der Wagon vorne eine Kupplung hat.
     * @param clutchBack    Ob der Wagon hinten eine Kupplung hat.
     * @param id    ID des Wagons
     */
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
        if(p == null) {
            return false;
        }
        return p.getWStringID().equals(this.getWStringID());
    }
    
    @Override
    public String getLeerzeile() {
        return "                    ";
    }

    @Override
    public String toString() {
        String trainID = "none";
        if(isUsed()) {
            trainID = "" + getTrain().getID();
        }
        return getID() + " " + trainID + " " + getType() + " " + getLength() + " " + isClutchFront() + " "
                + isClutchBack();
    }
    
    /**
     * @return erster Buchstabe der TypBeschreibung.
     */
    public abstract String getType();
    
    @Override
    public int compareTo(RollingMaterial o) {
        if(o.hasPower()) {
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
