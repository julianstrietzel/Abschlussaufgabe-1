/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

public final class PassengerWagon extends Coach {

    public PassengerWagon(int length, boolean clutchFront, boolean clutchBack, int id) {
        super(length, clutchFront, clutchBack, id);
    }

    @Override
    public String[] getVisual() {
        String[] visual = {"   (O)        (O)   ", "|__________________|",
                "|__________________|",  "|  |_| |_| |_| |_| |", "|  ___ ___ ___ ___ |", "____________________"};
        return visual;
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
