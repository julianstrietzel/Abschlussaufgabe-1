/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.trackmaterial;

public class DirectionalVertex extends Vertex{

    
    /**
     * 
     */
    public DirectionalVertex(int xcoord, int ycoord) {
        super((int) Math.signum(xcoord), (int) Math.signum(ycoord));
    }

    
    public boolean equalsDirection(DirectionalVertex vertex) {
        return (vertex.getXcoord() == this.getXcoord()) && (vertex.getYcoord() == this.getYcoord());
    }
    
    public boolean compatibleDirection(DirectionalVertex vertex) {
        return (Math.abs(Math.signum(vertex.getXcoord())) == Math.abs(Math.signum(this.getXcoord())) && 
                Math.abs(Math.signum(vertex.getYcoord())) == Math.abs(Math.signum(this.getYcoord())));
    }
    
    public DirectionalVertex getInverseDirection() {
        return new DirectionalVertex( this.xcoord * (-1), this.ycoord * (-1));
    }
}
