
package julian.modelrailway.rollingmaterial;

import julian.modelrailway.exceptions.IllegalInputException;

/**
 * Abstrakte Überklasse für alle Lokomotiven
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public abstract class Engine extends PoweredRolling {

    /**
     * Erstellt eine neue Lokomotive mit Baureihe, NAme, Länge und Kupplungen.
     * 
     * @param series      Baureihe
     * @param name        Name
     * @param length      Länge
     * @param clutchFront Ob Kupplung vorne
     * @param clutchBack  Ob Kupplung hinten
     * @throws IllegalInputException 
     */
    public Engine(String series, String name, int length, boolean clutchFront, boolean clutchBack) 
            throws IllegalInputException {
        super(series, name, length, clutchFront, clutchBack);
    }

    /**
     * @return Gibt den ersten Buchstaben der Baureihe zurück.
     */
    public abstract String getType();

    @Override
    public String toString() {
        String trainID = "none";
        if (isUsed()) {
            trainID = "" + getTrain().getID();
        }

        return trainID + " " + getType() + " " + getSeries() + " " + getName() + " " + getLength() + " "
                + isClutchFront() + " " + isClutchBack();
    }

}
