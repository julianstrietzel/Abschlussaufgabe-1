
package julian.modelrailway.rollingmaterial;

/**
 * Repräsentiert einen Güterwaggon.
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public final class FreightCar extends Coach {

    /**
     * Erstellt einen neuen Güterwaggon mit ID, Länge und Kupplungen.
     * 
     * @param length      Länge
     * @param clutchFront Ob Kupplung vorne
     * @param clutchBack  Ob Kupplung hinten
     * @param id          des Wagons
     */
    public FreightCar(int length, boolean clutchFront, boolean clutchBack, int id) {
        super(length, clutchFront, clutchBack, id);
    }

    @Override
    public String[] getVisual() {
        String[] visual = {"   (O)        (O)   ", "|__________________|", "|                  |",
                "|                  |", "|                  |" };
        return visual;
    }

    @Override
    public String getLeerzeile() {
        return "                    ";
    }

    @Override
    public String getType() {
        return "f";
    }

    @Override
    public String getTypeForAdding() {
        return "freight coach";
    }

}
