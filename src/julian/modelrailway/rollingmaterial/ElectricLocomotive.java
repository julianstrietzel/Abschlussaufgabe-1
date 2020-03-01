
package julian.modelrailway.rollingmaterial;

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
     */
    public ElectricLocomotive(String series, String name, int length, boolean clutchFront, boolean clutchBack) {
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