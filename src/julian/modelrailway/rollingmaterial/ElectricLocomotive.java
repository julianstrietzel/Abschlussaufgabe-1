
package julian.modelrailway.rollingmaterial;

import julian.modelrailway.exceptions.IllegalInputException;

/**
 * Repräsentiert eine Elektrolok
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public class ElectricLocomotive extends Engine {

    /**
     * Erstellt eine neue Elektroloko mit Baureihe, NAme, Länge und Kupplungen.
     * 
     * @param series      Baureihe
     * @param name        Name
     * @param length      Länge
     * @param clutchFront Ob Kupplung vorne
     * @param clutchBack  Ob Kupplung hinten
     * @throws IllegalInputException wenn Series mit W beginnt oder ohne Kupplung 
     */
    public ElectricLocomotive(String series, String name, int length, boolean clutchFront, boolean clutchBack) 
            throws IllegalInputException {
        super(series, name, length, clutchFront, clutchBack);
    }

    @Override
    public String[] getVisual() {
        String[] visual = {"  (O)(O)      (O)(O)  ", " \\__________________/ ", "\\                    /",
            "/   |____________|   \\", " /_| ____________ |_\\ ", "  _______________/__  ",
            "                 \\    ", "               ___    " };
        return visual;
    }

    @Override
    public String getType() {
        return "e";
    }

    @Override
    public String getLeerzeile() {
        return "                        ";
    }

    @Override
    public String getTypeForAdding() {
        return "electrical engine";
    }
}