
package julian.modelrailway.rollingmaterial;

import julian.modelrailway.exceptions.IllegalInputException;

/**
 * Passagierwaggon
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public final class PassengerWagon extends Coach {

    /**
     * Erstellt einen neuen Passagierwaggon mit ID, Länge und Kupplungen.
     * 
     * @param length      Länge
     * @param clutchFront Ob Kupplung vorne
     * @param clutchBack  Ob Kupplung hinten
     * @param id          des Wagons
     * @throws IllegalInputException bei keiner Kupplung
     */
    public PassengerWagon(int length, boolean clutchFront, boolean clutchBack, int id) throws IllegalInputException {
        super(length, clutchFront, clutchBack, id);
    }

    @Override
    public String[] getVisual() {
        String[] visual = {"   (O)        (O)   ", "|__________________|", "|__________________|",
            "|  |_| |_| |_| |_| |", "|  ___ ___ ___ ___ |", "____________________" };
        return visual;
    }

    @Override
    public String getLeerzeile() {
        return "                    ";
    }

    @Override
    public String getType() {
        return "p";
    }

    @Override
    public String getTypeForAdding() {
        return "passenger coach";
    }
}
