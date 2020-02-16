/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

public class SpecialCar extends Coach {
    

    public SpecialCar(int length, boolean clutchFront, boolean clutchBack, int id) {
        super(length, clutchFront, clutchBack, id);
    }

    @Override
    public String[] getVisual() {
        String[] visual = {"   (O)       (O)   ", "|_________________|", " _|_|__________|  |", "  | |          |  |"
                , "\\--------------|  |",  "/--------------|  |", "               ____"};
        return visual;
    }
    
    @Override
    public String getType() {
        return "s";
    }
    
    @Override
    public String getTypeForAdding() {
        return "special coach";
    }

}
