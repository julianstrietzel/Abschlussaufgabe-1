/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.rollingmaterial;

public abstract class Engine extends PoweredRolling {

    public Engine(String series, String name, int length, boolean clutchFront, boolean clutchBack) {
        super(series, name, length, clutchFront, clutchBack);
    }
    
    public abstract String getType();

    @Override
    public String toString() {
        return getTrain().getID() + " " + getType() + " " + getSeries() + " " + getName() + " " + getLength() + " "
                + isClutchFront() + " " + isClutchBack();
    }
    
    

}
