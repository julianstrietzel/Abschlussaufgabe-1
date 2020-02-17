/**
 * Repräsentiert einen Triebzug
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

public class TrainSet extends PoweredRolling {

    /**
     * Erstellt einen neuen Triebzug mit Baureihe, NAme, Länge und Kupplungen.
     * @param series    Baureihe
     * @param name      Name    
     * @param length    Länge
     * @param clutchFront   Ob Kupplung vorne
     * @param clutchBack    Ob Kupplung hinten
     */
    public TrainSet(String series, String name, int length, boolean clutchFront, boolean clutchBack) {
        super(series, name, length, clutchFront, clutchBack);
    }

    @Override
    public String[] getVisual() {
        String[] visual = { "   (O)        (O)   ", "|__________________|", "|__________________|",
                "|  |_| |_| |_| |_| |", "|  ___ ___ ___ ___ |", "_________||_________", "         ||         ",
                "         ++         " };
        return visual;
    }
    
    @Override
    public String toString() {
        String trainID = "none";
        if(isUsed()) {
            trainID = "" + getTrain().getID();
        }
        return trainID + " " + getSeries() + " " + getName() + " " + getLength() + " "
                + isClutchFront() + " " + isClutchBack();
    }

    @Override
    public String getTypeForAdding() {
        return "train-set";
    }
}
