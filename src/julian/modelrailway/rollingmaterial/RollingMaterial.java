
package julian.modelrailway.rollingmaterial;

import julian.modelrailway.exceptions.IllegalInputException;

/**
 * Allgemeine Klasse für ein rollendes Objekt.
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public abstract class RollingMaterial implements Comparable<RollingMaterial> {

    private boolean used;
    private final int length;
    private final boolean clutchFront;
    private final boolean clutchBack;
    private Train train;

    /**
     * Erstellt ein neues rollendes Objekt
     * 
     * @param length      Länge
     * @param clutchFront Ob Kupplung vorne
     * @param clutchBack  Ob Kupplung hinten
     * @throws IllegalInputException bei keiner Kupplung
     */
    public RollingMaterial(int length, boolean clutchFront, boolean clutchBack) throws IllegalInputException {
        if (!(clutchFront || clutchBack)) {
            throw new IllegalInputException("needs to have at least one clutch");
        }
        this.length = length;
        this.clutchBack = clutchBack;
        this.clutchFront = clutchFront;
        used = false;
    }

    /**
     * lololo
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
     * 
     * @return die Länge des Zuges
     */
    public int getLength() {
        return length;
    }

    /**
     * Setzt den Zug zudem das Material zugehörig
     * 
     * @param t neuer Zug
     */
    public void setTrain(Train t) {
        this.train = t;
    }

    /**
     * 
     * @return der Zug zudem das Material zugehörig ist.
     */
    public Train getTrain() {
        return train;
    }

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
    
    /**
     * Baut das Material in einen Zug ein
     * 
     * @param t neuer Zug
     */
    public void concat(Train t) {
        this.used = true;
        setTrain(t);
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
     * @return die ID des Materials als String
     */
    public abstract String getStringID();

    /**
     * Bsp. "special coach"
     * 
     * @return String Beschreibung des Types
     */
    public abstract String getTypeForAdding();

    /**
     * Vergleicht, ob es sich nach der ID um das gleiche Material handelt.
     * 
     * @param o anderes Material
     * @return boolean, ob das gleiche Material
     */
    public abstract boolean sameRollingMat(RollingMaterial o);

    /**
     * Gibt ein Feld mit den Spalten der visuellen Repräsentation zurück.
     * 
     * @return visuelle Repr als String Feld
     */
    public abstract String[] getVisual();

    /**
     * Gibt die String ID bei normalen Waggons mit W davor zurück. Bei anderen
     * genauso, wie getStringID()
     * 
     * @return die String ID repräsentation
     */
    public String getWStringID() {
        return getStringID();
    }

    /**
     * 
     * @return Die Baureihe des Materials. Bei Wagons gleich null.
     */
    public String getSeries() {
        return null;
    }

    /**
     * 
     * @return Eine Leerzeile entsprechend der Länge des Models.
     */
    public abstract String getLeerzeile();

    /**
     * Guckt, ob eine Liste aus PoweredRolling einen Zug mti der ID beinhaltet
     * 
     * @param list zu überpfüfendes ELemnt von Iterable
     * @param id   zu suchende String ID
     * @return PoweredRolling, falls dieses existiert.
     */
    public static PoweredRolling exists(Iterable<PoweredRolling> list, String id) {
        for (PoweredRolling p : list) {
            if (id.contentEquals(p.getStringID())) {
                return p;
            }
        }
        return null;
    }

    /**
     * 
     * @return Ob das Material motorisiert ist.
     */
    public boolean hasPower() {
        return false;
    }

    @Override
    public abstract String toString();

    @Override
    public abstract int compareTo(RollingMaterial o);
}
