/**
 * Probably marginal changes needed.
 * @author Julian Strietzel
 */
package julian.modelrailway.Tests;

import org.junit.Before;
import org.junit.Test;

import julian.modelrailway.ModelRailWay;
import julian.modelrailway.UserInterface;

public class Testing {
ModelRailWay m;
UserInterface ui;

    /**
     * Resets before every Test
     */
    @Before
    public void Reset () {
        m = new ModelRailWay();
        ui = new UserInterface(m);
    }
    /**
     * Helper Method, to simplify writing tests.
     * @param command
     */
    private void e(String command) {
        ui.executeCommand(command);
    }
    
    /**
     * Example Commands from Sheet
     */
//    @Test
    public void Beispielablauf() {
        System.out.println("EXAMPLE ABLAUF");
//        e("create train-set 403 145 4 true true");
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
        e("list tracks");
//        e("delete track 1");
//        e("delete track 6");
//        e("delete track 5");
        e("create engine steam T3 Emma 1 false true");
        e("list engines");
        e("create engine electrical 103 118 3 true true");
        e("list engines");
        e("delete rolling stock 3");
        e("delete rolling stock 103-118");
        e("create coach passenger 1 true true");
        e("create coach passenger 1 true true");
        e("list coaches");
        e("add train 1 W1");
        e("list trains");
        e("show train 01");
        e("delete train 1");
        e("list trains");
        e("add train 1 T3-Emma");
        e("add train 1 W1");
        e("add train 1 W2");
        e("list trains");
        e("show train 01");
        e("list engines");
        e("create train-set 403 145 4 true true");
        e("add train 2 403-145");
        e("set switch 4 position (10,1)");
        e("step 1");
        e("set switch 2 position (8,1)");
        e("set switch 9 position (12,-3)");
        e("step 1");
        e("put train 1 at (1,1) in direction 1,0");
        e("put train 2 at (10,-2) in direction 0,1");
        e("step 2");
        e("step -2");
        e("step 2");
        e("step 3");
//        e("set switch 2 position (5,3)");
//        e("set switch 2 position (8,1)");
        e("step 1");
        e("put train 1 at (1,1) in direction 0,-1");
        e("exit");
        
    }

//    @Test
    public void trainandMovement() throws InterruptedException {
        
        e("add track (0,0) -> (10,0)");
        e("add track (0,0) -> (0,10)");
//        System.out.println( m.getRailSystem().getKnodes());
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
    
//    @Test
    public void testingRailSystem() {
        e("add track (0,0) -> (10,0)");
        e("add track (0,0) -> (0,10)");
        e("add track (10,10) -> (10,0)");
        e("add track (10,10) -> (0,10)");
        e("list tracks");
//       System.out.println( m.getRailSystem().getKnodes());
        
    }
    
    @Test
    public void testinDeleteTracks() {
        
        e("add track (1,1) -> (5,1)");
//        e("add track (10,10) -> (10,11)");
//        e("list tracks");
        e("add switch (5,1) -> (8,1),(5,3)");
//        e("add track (5,3) -> (8,1)");
        e("add track (10,1) -> (8,1)");
        e("add switch (10,-3) -> (10,1),(12,-3)");
        e("add track (5,3) -> (5,5)");
        e("list tracks");
//        e("set s")
        e("delete track 5");
        e("create engine steam T3 Emma 1 false true");
        e("add train 1 T3-Emma");
        e("put train 1 at (3,1) in direction 1,0");
        e("delete track 4");
        e("delete track 3");
        e("delete track 2");
        e("step 20");
        e("delete track 1");
        e("add track (1,1) -> (5,1)");
        e("put train 1 at (3,1) in direction 1,0");
        e("list tracks");
        
    }

}
