
package julian.modelrailway.rollingmaterial;

import julian.modelrailway.exceptions.IllegalInputException;

/**
 * Repräsentiert eine Dampflokomotive
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public class SteamLocomotive extends Engine {

    /**
     * Erstellt eine neue Dampflokomotive mit Baureihe, NAme, Länge und Kupplungen.
     * 
     * @param series      Baureihe
     * @param name        Name
     * @param length      Länge
     * @param clutchFront Ob Kupplung vorne
     * @param clutchBack  Ob Kupplung hinten
     * @throws IllegalInputException wenn Series mit W beginnt
     */
    public SteamLocomotive(String series, String name, int length, boolean clutchFront, boolean clutchBack) 
            throws IllegalInputException {
        super(series, name, length, clutchFront, clutchBack);
    }

    @Override
    public String[] getVisual() {
        String[] visual = {"//// \\_/      \\_/   ", " _|--/~\\------/~\\-+ ", "  + ========  +-+ | ",
            "   /---------|| | | ", "     ||      |+-+ | ", "     ++      +------" };
        return visual;

    }

    @Override
    public String getType() {
        return "s";
    }

    @Override
    public String getLeerzeile() {
        return "                    ";
    }

    @Override
    public String getTypeForAdding() {
        return "steam engine";
    }

}
