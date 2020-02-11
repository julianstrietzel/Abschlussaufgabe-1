/**
 * 
 * @author Julian Strietzel
 */
package julian.trackmaterial;

public class Vertex {

    private final int xcoord;
    private final int ycoord;
    
    public Vertex(int x, int y) {
        this.xcoord = x;
        this.ycoord = y;
    }

    public int getXcoord() {
        return xcoord;
    }

    public int getYcoord() {
        return ycoord;
    }
    
    public boolean equals(Vertex vertex) {
        return (vertex.xcoord == this.xcoord) && (vertex.ycoord == this.ycoord);
    }
    

}
