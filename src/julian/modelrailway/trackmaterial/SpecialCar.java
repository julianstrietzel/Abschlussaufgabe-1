/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.trackmaterial;

import julian.modelrailway.rollingmaterial.Wagon;

public class SpecialCar extends Wagon {
    
    public SpecialCar(int noC, int id, int length) {
        super(noC, id, length);
    }

    @Override
    public String[] getVisual() {
        String[] visual = {"   (O)       (O)   ", "|_________________|", " _|_|__________|  |", "  | |          |  |"
                , "\\--------------|  |",  "/--------------|  |", "               ____"};
        return visual;
    }

}
