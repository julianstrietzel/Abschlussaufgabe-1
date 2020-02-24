
package julian.modelrailway.rollingmaterial;

/**
 * Repräsentiert ein spezial Waggon
 * 
 * @author Julian Strietzel
 */
public class SpecialCar extends Coach {

    /**
     * Erstellt einen neuen spezial Waggon mit ID, Länge und Kupplungen.
     * 
     * @param length      Länge
     * @param clutchFront Ob Kupplung vorne
     * @param clutchBack  Ob Kupplung hinten
     * @param id          des Wagons
     */
    public SpecialCar(int length, boolean clutchFront, boolean clutchBack, int id) {
        super(length, clutchFront, clutchBack, id);
    }

    @Override
    public String[] getVisual() {
        String[] visual = { "   (O)       (O)   ", "|_________________|", " _|_|__________|  |", "  | |          |  |",
                "\\--------------|  |", "/--------------|  |", "               ____" };
        return visual;
    }

    @Override
    public String getType() {
        return "s";
    }

    @Override
    public String getLeerzeile() {
        return "                   ";
    }

    @Override
    public String getTypeForAdding() {
        return "special coach";
    }

}
