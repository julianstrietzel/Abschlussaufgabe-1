/**
 * Allgemeine Klasse für ein rollendes Objekt.
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

public abstract class RollingMaterial implements Comparable<RollingMaterial>{
    
    private boolean used;
    private final int length;
    private final boolean clutchFront;
    private final boolean clutchBack;
    private Train train;

    /**
     * Erstellt ein neues rollendes Objekt
     * @param length    Länge
     * @param clutchFront   Ob Kupplung vorne
     * @param clutchBack    Ob Kupplung hinten
     */
    public RollingMaterial(int length, boolean clutchFront, boolean clutchBack) {
        this.length = length;
        this.clutchBack = clutchBack;
        this.clutchFront = clutchFront;
        used = false;
    }
    
    /**
     * 
     * @return Die höhe der Stringrepräsentation des Materials
     */
    public int getVisualHeight() {
        return getVisual().length;
    }
    
    /**
     * 
     * @return Ob das Material schon genutzt
     */
    public boolean isUsed() {
        return used;
    }
    
    /**
     * Baut das Material in einen Zug ein
     * @param t neuer Zug
     */
    public void concat(Train t) {
        this.used = true;
        SetTrain(t);
    }
    
    /**
     * Baut das Material aus dem Zug wieder aus.
     */
    public void unconcat() {
        this.used = false;
        this.train = null;
    }
    
    /**
     * 
     * @return die Länge des Zuges
     */
    public int getLength() {
        return length;
    }
    
    /**
     * Setzt den Zug zudem das Material zugehörig
     * @param t neuer Zug
     */
    public void SetTrain(Train t) {
        this.train = t;
    }
    
    /**
     * 
     * @return der Zug zudem das Material zugehörig ist.
     */
    public Train getTrain() {
        return train;
    }

//    public int getNumberOfClutches() {
//        int i = 0;
//        if(clutchFront) {
//            i++;
//        }
//        if(clutchBack) {
//            i++;
//        }
//        return i;
//    }
    
    /**
     * 
     * @return ob vorne eine Kupplung
     */
    public boolean isClutchFront() {
        return clutchFront;
    }

    /**
     * 
     * @return ob hinten ein Kupplung
     */
    public boolean isClutchBack() {
        return clutchBack;
    }

//    /**
//     * Setzt, ob
//     * @param used
//     */
//    public void setUsed(boolean used) {
//        this.used = used;
//    }
    
    /**
     * 
     * @return die ID des Materials als String
     */
    public abstract String getStringID();
    
    /**
     * Bsp. "special coach"
     * @return String Beschreibung des Types
     */
    public abstract String getTypeForAdding();

    
    /**
     * Vergleicht, ob es sich nach der ID um das gleiche Material handelt.
     * @param o anderes Material
     * @return boolean, ob das gleiche Material
     */
    public abstract boolean equals(RollingMaterial o);
    
    /**
     * Gibt ein Feld mit den Spalten der visuellen Repräsentation zurück.
     * @return visuelle Repr als String Feld
     */
    public abstract String[] getVisual();
    
    /**
     * Gibt die String ID bei normalen Waggons mit W davor zurück.
     * Bei anderen genauso, wie getStringID()
     * @return die String ID repräsentation
     */
    public String getWStringID() {
        return getStringID();
    }
    
    @Override
    public abstract String toString();
    
    @Override    
    public abstract int compareTo(RollingMaterial o);
}
