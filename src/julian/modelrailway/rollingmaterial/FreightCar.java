/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

public final class FreightCar extends Coach {

    
    
    public FreightCar(int length, boolean clutchFront, boolean clutchBack, int id) {
        super(length, clutchFront, clutchBack, id);
    }

    @Override
    public String[] getVisual() {
        String[] visual = {"   (O)        (O)   ", "|__________________|", "|                  |"
                , "|                  |", "|                  |"};
        return visual;
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
