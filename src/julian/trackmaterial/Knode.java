/**
 * 
 * @author Julian Strietzel
 */
package julian.trackmaterial;

public abstract class Knode {

    private final Vertex pos;
    private Rail railIn;
    private Rail railOut;
    
    public Knode(Vertex pos, Rail railin) {
        this.pos = pos;
        this.railIn = railin;
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

    public Vertex getPos() {
        return pos;
    }
   
    
}
