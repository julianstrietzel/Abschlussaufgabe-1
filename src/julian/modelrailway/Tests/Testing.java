/**
 * Probably marginal changes needed.
 * @author Julian Strietzel
 */
package julian.modelrailway.Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;

import  edu.kit.informatik.Terminal;
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
//        Terminal.test = true;
        //Bei Bedarf hier die ausgabe wieder einschalten!
        m = new ModelRailWay();
        ui = new UserInterface(m);
    }
    
    @After
    public void TerminalBackToNormal() {
        Terminal.test = false;
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
    @Test
    public void Beispielablauf() {
//        Terminal.test = true;
        Terminal.printLine("EXAMPLE ABLAUF");
        assertTrue("EXAMPLE ABLAUF".equals(Terminal.buffer));
        e("add track (1,1) -> (5,1)");
        assertTrue("1".equals(Terminal.buffer));
        e("add track (10,10) -> (10,11)");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("list tracks");
        assertTrue("t 1 (1,1) -> (5,1) 4".equals(Terminal.buffer));
        e("add switch (5,1) -> (8,1),(5,3)");
        assertTrue("2".equals(Terminal.buffer));
        e("add track (5,3) -> (8,1)");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("add track (10,1) -> (8,1)");
        assertTrue("3".equals(Terminal.buffer));
        e("add switch (10,-3) -> (10,1),(12,-3)");
        assertTrue("4".equals(Terminal.buffer));
        e("add track (10,-3) -> (1,-3)");
        assertTrue("5".equals(Terminal.buffer));
        e("add track (1,-3) -> (1,1)");
        assertTrue("6".equals(Terminal.buffer));
        e("add track (5,3) -> (10,3)");
        assertTrue("7".equals(Terminal.buffer));
        e("add track (10,3) -> (12,3)");
        assertTrue("8".equals(Terminal.buffer));
        e("add switch (12,3) -> (12,-3),(14,3)");
        assertTrue("9".equals(Terminal.buffer));
        e("add track (14,-1) -> (14,3)");
        assertTrue("10".equals(Terminal.buffer));
        
        e("create engine steam T3 Emma 1 false true");
        assertTrue("T3-Emma".equals(Terminal.buffer));
        e("list engines");
        assertTrue("none s T3 Emma 1 false true".equals(Terminal.buffer));
        e("create engine electrical 103 118 3 true true");
        assertTrue("103-118".equals(Terminal.buffer));
        e("list engines");
        assertTrue("none e 103 118 3 true true\nnone s T3 Emma 1 false true".equals(Terminal.buffer));
        e("delete rolling stock 3");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("delete rolling stock 103-118");
        assertTrue("OK".equals(Terminal.buffer));
        e("create coach passenger 1 true true");
        assertTrue("1".equals(Terminal.buffer));
        e("create coach passenger 1 true true");
        assertTrue("2".equals(Terminal.buffer));
        e("list coaches");
        assertTrue("1 none p 1 true true\n2 none p 1 true true".equals(Terminal.buffer));
        e("add train 1 W1");
        assertTrue("passenger coach W1 added to train 1".equals(Terminal.buffer));
        e("list trains");
        assertTrue("1 W1".equals(Terminal.buffer));
        e("show train 01");
//        assertTrue(Terminal.buffer.contentEquals("____________________\n| ____________|\n| |_||_||_||_||\n|__________________|\n|__________________|\n   (O)        (O)"));
        e("delete train 1");
        assertTrue("OK".equals(Terminal.buffer));
        e("list trains");
        assertTrue("No train exists".equals(Terminal.buffer));
        e("add train 1 T3-Emma");
        assertTrue("steam engine T3-Emma added to train 1".equals(Terminal.buffer));
        e("add train 1 W1");
        assertTrue("passenger coach W1 added to train 1".equals(Terminal.buffer));
        e("add train 1 W2");
        assertTrue("passenger coach W2 added to train 1".equals(Terminal.buffer));
        e("list trains");
        assertTrue("1 T3-Emma W1 W2".equals(Terminal.buffer));
        e("show train 01");
//Test von visueller Zug funkt nicht ganz
        e("list engines");
        assertTrue("1 s T3 Emma 1 false true".equals(Terminal.buffer));
        e("create train-set 403 145 4 true true");
        assertTrue("403-145".equals(Terminal.buffer));
        e("add train 2 403-145");
        assertTrue("train-set 403-145 added to train 2".equals(Terminal.buffer));
        e("set switch 4 position (10,1)");
        assertTrue("OK".equals(Terminal.buffer));
        e("step 1");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("set switch 2 position (8,1)");
        assertTrue("OK".equals(Terminal.buffer));
        e("set switch 9 position (12,-3)");
        assertTrue("OK".equals(Terminal.buffer));
        e("step 1");
        assertTrue("OK".equals(Terminal.buffer));
        e("put train 1 at (1,1) in direction 1,0");
        assertTrue("OK".equals(Terminal.buffer));
        e("put train 2 at (10,-2) in direction 0,1");
        assertTrue("OK".equals(Terminal.buffer));
        e("step 2");
        assertTrue("Train 1 at (3,1)\nTrain 2 at (10,0)".equals(Terminal.buffer));
        e("step -2");
        assertTrue("Train 1 at (1,1)\nTrain 2 at (10,-2)".equals(Terminal.buffer));
        e("step 2");
        assertTrue("Train 1 at (3,1)\nTrain 2 at (10,0)".equals(Terminal.buffer));
        e("step 3");
        assertTrue("Train 1 at (6,1)\nTrain 2 at (8,1)".equals(Terminal.buffer));
        e("step 1");
        assertTrue("Crash of train 1,2".equals(Terminal.buffer));
        e("put train 1 at (1,1) in direction 0,-1");
        assertTrue("OK".equals(Terminal.buffer));
        e("exit");
    }

    
    @Test
    public void testingRailSystem() {
        Terminal.test = true;
        e("add track (0,0) -> (10,0)");
        e("add track (0,0) -> (0,10)");
        e("add track (10,10) -> (10,0)");
        e("add track (10,10) -> (0,10)");
        e("list tracks");
        assertTrue(Terminal.buffer.contentEquals("t 1 (0,0) -> (10,0) 10\n" + 
                "t 2 (0,0) -> (0,10) 10\n" + 
                "t 3 (10,10) -> (10,0) 10\n" + 
                "t 4 (10,10) -> (0,10) 10"));
    }
    
    @Test
    public void testinDeleteTracks() {
        Terminal.test = true;
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
        assertTrue(Terminal.buffer.contentEquals("Crash of train 1"));
        e("delete track 1");
        e("add track (1,1) -> (5,1)");
        e("put train 1 at (3,1) in direction 1,0");
        e("list tracks");
        assertTrue(Terminal.buffer.contentEquals("t 1 (1,1) -> (5,1) 4"));
        
    }

}
