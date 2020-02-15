/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.trackmaterial;

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
    
    public Rail getTrack(DirectionalVertex direc){
        if(railIn.getEndInDirection(direc).equals(this)) {
            return railIn;
        }
       
        try {
            if(railOut.getEndInDirection(direc.getInverseDirection()).equals(this)) {
                return railIn;
            }
        } catch (NullPointerException e) {
        }
        if(!isFree()) {
            try {
                    if(railOut.getEndInDirection(direc).equals(this) || railIn.getEndInDirection(direc.getInverseDirection()).equals(this)) {
                        return railOut;
                    }
            } catch ( NullPointerException ex) {
                
            }
        }
        return null;
    }
    
//    public boolean equals(Vertex v) {
//        return v.getXcoord() == this.xcoord && v.getYcoord() == this.ycoord;
//    }
}
