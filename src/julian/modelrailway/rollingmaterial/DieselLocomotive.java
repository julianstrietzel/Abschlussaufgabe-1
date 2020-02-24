
package julian.modelrailway.rollingmaterial;

/**
 * Repräsentiert eine Diesellokomotive.
 * 
 * @author Julian Strietzel
 */
public class DieselLocomotive extends Engine {

    /**
     * Erstellt eine neue Diesellokomotive mit Baureihe, NAme, Länge und Kupplungen.
     * 
     * @param series      Baureihe
     * @param name        Name
     * @param length      Länge
     * @param clutchFront Ob Kupplung vorne
     * @param clutchBack  Ob Kupplung hinten
     */
    public DieselLocomotive(String series, String name, int length, boolean clutchFront, boolean clutchBack) {
        super(series, name, length, clutchFront, clutchBack);
    }

    @Override
    public String[] getVisual() {
        String[] visual = { "  (O)(O)      (O)(O)  ", " \\__________________/ ", "\\                    /",
                "/   |____________|   \\", " /_| ____________ |_\\ ", "  _____________|____  " };
        return visual;
    }

    @Override
    public String getType() {
        return "d";
    }

    @Override
    public String getLeerzeile() {
        return "                      ";
    }

    @Override
    public String getTypeForAdding() {
        return "diesel engine";
    }

}
