/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import julian.modelrailway.ModelRailWay;
import julian.modelrailway.UserInterface;

public class Testing {
    ModelRailWay m = new ModelRailWay();
UserInterface ui = new UserInterface(m);

//    @Before
//    public void Reset () {
//        ui = new
//    }

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
    
    public void e(String command) {
        ui.executeCommand(command);
    }
    
    @Test
    public void TestAddTracks() throws InterruptedException {
        e("add track (0,1) -> (0,-2)");
        e("add track (0,1) -> (0,3)");
        e("add track (0,3) -> (2,3)");
        e("add track (0,3) -> (-1,3)"); //F
        e("add track (0,0) -> (0,1)"); //f
        e("add track (2,-2) -> (2,3)");
        e("add track (0,-2) -> (2,-2)");
        e("create engine steam T3 Emma 1 false true");
        e("create engine steam T4 Emma 1 true true");
        e("list engines");
        e("add train 1 T3-Emma");
        e("add train 2 T4-Emma");
        e("list trains");
        e("show train 1");
        e("put train 1 at (0,0) in direction 0,1");
        e("put train 2 at (1,3) in direction 0,1");
        for(int i = 0; i < 100; i++) {
            e("step 1");
//            wait(10);
        }
        
    }

}
