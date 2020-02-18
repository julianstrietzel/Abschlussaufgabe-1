/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import julian.modelrailway.ModelRailWay;
import julian.modelrailway.UserInterface;
import julian.modelrailway.rollingmaterial.RollingMaterialComparator;

public class Testing {
    ModelRailWay m = new ModelRailWay();
UserInterface ui = new UserInterface(m);

    @Before
    public void Reset () {
        m = new ModelRailWay();
    }

    public void Beispielablauf() {
        e("list trains");
        e("add track (1,1) -> (5,1)");
        e("add track (10,10) -> (10,11)");
        e("list tracks");
        e("add switch (5,1) -> (8,1),(5,3)");
        e("add track (5,3) -> (8,1)");
        e("add track (10,1) -> (8,1)");
        e("add switch (10,-3) -> (10,1),(12,-3)");
        e("add track (10,-3) -> (1,-3)");
        e("add track (1,-3) -> (1,1)");
        e("add track (5,3) -> (10,3)");
        e("add track (10,3) -> (12,3)");
        e("add switch (12,3) -> (12,-3),(14,3)");
        e("add track (14,-1) -> (14,3)");
        e("create engine steam T3 Emma 1 false true");
        e("list engines");
        e("create engine electrical 103 118 3 true true");
        System.out.println("new Side");
        e("list engines");
        e("delete rolling stock 3");
        e("delete rolling stock 103-118");
        e("create coach passenger 1 true true");
        System.out.println("create coach passenger 1 true true");
        e("create coach passenger 1 true true");
        e("list coaches");
        e("add train 1 W1");
        e("list trains");
        e("show train 01");
        e("delete train 1");
        e("list trains");
        e("add train 1 T3-Emma");
        
    }
    
    private void e(String command) {
        ui.executeCommand(command);
    }
    
    
    
    @Test
    public void trainandMovement() throws InterruptedException {
        
        e("add track (0,0) -> (10,0)");
        e("add track (0,0) -> (0,10)");
        System.out.println( m.getRailSystem().getKnodes());
//        e("add track (0,1) -> (0,3)");
//        e("add track (0,3) -> (2,3)");
//        e("add track (0,3) -> (-1,3)"); //F
//        e("add track (0,0) -> (0,1)"); //f
//        e("add track (2,-2) -> (2,3)");
//        e("add track (0,-2) -> (2,-2)");
        e("create engine steam 119 119 2 true true");
        e("create engine diesel 119 120 2 true true");
        e("create engine electrical 119 121 2 true true");
//        m.getRollStock().getPowered().sort(new RollingMaterialComparator());
//        System.out.println(m.getRollStock().getPowered());
//        e("list train-sets");
//        e("create coach special 1 true true");
//        e("create coach special 1 true true");
        e("create coach freight 1 true true");
        e("create coach passenger 1 true true");
        e("create coach special 1 true true");
        e("create train-set 118 118 2 true true");
        e("create train-set 118 119 2 true true");
//        
//        e("list train-sets");
////        e("add train 1 1");
//        e("add train 1 119-ae");
//        e("add train 1 W1");
//
//        
        e("add train 1 119-119");
        e("add train 1 119-120");

        e("add train 1 W1");
        e("add train 1 W2");
        e("add train 1 W3");
//        e("add train 1 W4");
//        e("add train 1 W5");
//        e("add train 1 119-121");
//        e("add train 2 118-118");
//        e("add train 2 118-119");
        
//        e("list trains");
        e("show train 1");
        e("list trains");
        e("put train 1 at (0,0) in direction 1,0");
//
        for(int i = 0; i < 8; i++) {
            e("step 1");
            e("step 1");
        }
        e("step 1");
        
        e("step 2");
        e("step 1");
        
        
        
    }
    
    @Test
    public void testingRailSystem() {
        e("add track (0,0) -> (10,0)");
        e("add track (0,0) -> (0,10)");
        e("add track (10,10) -> (10,0)");
        e("add track (10,10) -> (0,10)");
        e("list tracks");
       System.out.println( m.getRailSystem().getKnodes());
        
    }

}
