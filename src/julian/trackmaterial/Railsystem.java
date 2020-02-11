/**
 * 
 * @author Julian Strietzel
 */
package julian.trackmaterial;

import java.util.HashMap;

public class Railsystem {

    private HashMap<Knode, Integer> knodes;
    private HashMap<Rail, Integer> rails;
    
    public Railsystem() {
        knodes = new HashMap<Knode, Integer>();
        rails = new HashMap<Rail, Integer>();
    }

}
