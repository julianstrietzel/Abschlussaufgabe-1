/**
 * 
 * @author Julian Strietzel
 */
package julian.trackmaterial;

public class Vertex {

    private final int excoord;
    private final int whycoord;
    
    public Vertex(int x, int y) {
        this.excoord = x;
        this.whycoord = y;
    }

    public int getExcoord() {
        return excoord;
    }

    public int getWhycoord() {
        return whycoord;
    }
    
    

}
