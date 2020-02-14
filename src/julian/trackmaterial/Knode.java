/**
 * 
 * @author Julian Strietzel
 */
package julian.trackmaterial;

public class Knode extends Vertex{

    private Rail railIn;
    private Rail railOut;
    
    public Knode(int xcoord, int ycoord, Rail railIn) {
        super(xcoord, ycoord);
        this.railIn = railIn;
    }
    
    public Knode(Vertex pos, Rail railIn) {
        super(pos.getXcoord(), pos.getYcoord());
        this.railIn = railIn;
    }

    public Rail getRailIn() {
        return railIn;
    }

    public void setRailIn(Rail railIn) {
        this.railIn = railIn;
    }

    public Rail getRailOut() {
        return railOut;
    }

    public void setRailOut(Rail railOut) {
        this.railOut = railOut;
    }

    public boolean isFree() {
        return railOut == null;
    }
   
    public Rail getNext(Rail railone) {
        if(railone.equals(railIn)) {
            return railOut;
        } else if(railone.equals(railOut)) {
            return railIn;
        }
        return null;
    }
}
