/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

public class TrainSet extends PoweredRolling {

    

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
        return getTrain().getID() + " " + getSeries() + " " + getName() + " " + getLength() + " "
                + isClutchFront() + " " + isClutchBack();
    }

    @Override
    public String getTypeForAdding() {
        return "train-set";
    }
}
