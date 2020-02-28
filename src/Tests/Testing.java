/**
 * Probably marginal changes needed.
 * @author Julian Strietzel
 */
package Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;

import edu.kit.informatik.Terminal;
import org.junit.Before;
import org.junit.Test;

import julian.modelrailway.main.ModelRailWay;
import julian.modelrailway.main.UserInterface;

public  class Testing {
    
    ModelRailWay m;
    UserInterface ui;

    /**
     * Resets before every Test
     */
    @Before
    public void start() {
        Terminal.silent = false;
        // Bei Bedarf hier die ausgabe wieder einschalten!
        reset();
    }
    
    private void reset() {
        m = new ModelRailWay();
        ui = new UserInterface(m);
    }

    @After
    public void TerminalBackToNormal() {
        Terminal.silent = false;
    }

    /**
     * Helper Method, to simplify writing tests.
     * 
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
//        Terminal.silent = false;
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
//        assertTrue(Terminal.buffer
//                .contentEquals("____________________\n" + "|  ___ ___ ___ ___ |\n" + "|  |_| |_| |_| |_| |\n"
//                        + "|__________________|\n" + "|__________________|\n" + "   (O)        (O)"));
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
//        assertTrue(Terminal.buffer.equals("     ++      +------ ____________________ ____________________\n"
//                + "     ||      |+-+ |  |  ___ ___ ___ ___ | |  ___ ___ ___ ___ |\n"
//                + "   /---------|| | |  |  |_| |_| |_| |_| | |  |_| |_| |_| |_| |\n"
//                + "  + ========  +-+ |  |__________________| |__________________|\n"
//                + " _|--/~\\------/~\\-+  |__________________| |__________________|\n"
//                + "//// \\_/      \\_/       (O)        (O)       (O)        (O)"));
        e("list engines");
        assertTrue("1 s T3 Emma 1 false true".equals(Terminal.buffer));
        e("create train-set 403 145 4 true true");
        assertTrue("403-145".equals(Terminal.buffer));
        e("add train 2 403-145");
        
        assertTrue("train-set 403-145 added to train 2".equals(Terminal.buffer));
        e("show train 2");
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
//        Terminal.silent = true;
        e("add track (0,0) -> (10,0)");
        e("add track (0,0) -> (0,10)");
        e("add track (10,10) -> (10,0)");
        e("add track (10,10) -> (0,10)");
        e("list tracks");
        assertTrue(Terminal.buffer.contentEquals("t 1 (0,0) -> (10,0) 10\n" + "t 2 (0,0) -> (0,10) 10\n"
                + "t 3 (10,10) -> (10,0) 10\n" + "t 4 (10,10) -> (0,10) 10"));
    }

    @Test
    public void testinDeleteTracks() {
//        Terminal.silent = false;
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
        e("put train 1 at (3,1) in direction -10000000,0");
        e("list tracks");
        assertTrue(Terminal.buffer.contentEquals("t 1 (1,1) -> (5,1) 4"));

    }

    @Test
    public void multipleCollids() {
//        e("list trains");
//        Terminal.silent = false;
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
        e("add train 1 T3-Emma");
        e("create engine steam T4 Emma 1 false true");
        e("add train 2 T4-Emma");
        e("create engine steam T5 Emma 1 false true");
        e("add train 3 T5-Emma");
        e("put train 1 at (1,1) in direction 1,0");
        e("put train 2 at (2,1) in direction -1,0");
        e("set switch 4 position (10,1)");
        e("set switch 2 position (8,1)");
        e("set switch 9 position (12,-3)");
        e("put train 3 at (5,1) in direction -1,0");
        e("step 1");
        assertTrue("Crash of train 1,2,3".equals(Terminal.buffer));
        e("put train 1 at (1,1) in direction 1,0");
        e("put train 2 at (2,1) in direction -1,0");
        e("put train 3 at (5,-3) in direction -1,0");
        e("step 1");
        assertTrue(Terminal.buffer.contentEquals("Crash of train 1,2\n" + "Train 3 at (4,-3)"));
        e("set switch 2 position (5,3)");
        e("set switch 9 position (14,3)");
        e("step 32767");
        assertTrue(Terminal.buffer.contentEquals("Crash of train 3"));
    }

    @Test
    public void closeRoads() {
//        Terminal.silent = false;
        e("add track (1,1) -> (5,1)");
        e("add track (1,1) -> (1,1)");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("add track (1,1) -> (1,0)");
        e("add track (2,0) -> (1,0)");
        e("add track (5,0) -> (2,0)");
        e("add track (10,0) -> (5,0)");
        e("add track (10,0) -> (11,0)");
        e("add track (11,0) -> (11,1)");
        e("add track (11,1) -> (5,1)");
        e("list tracks");
        e("delete track 2");
        e("add track (1,1) -> (1,0)");
        e("list tracks");
        assertTrue(Terminal.buffer.equals("t 1 (1,1) -> (5,1) 4\n" + "t 2 (1,1) -> (1,0) 1\n" + "t 3 (2,0) -> (1,0) 1\n"
                + "t 4 (5,0) -> (2,0) 3\n" + "t 5 (10,0) -> (5,0) 5\n" + "t 6 (10,0) -> (11,0) 1\n"
                + "t 7 (11,0) -> (11,1) 1\n" + "t 8 (11,1) -> (5,1) 6"));
        e("create coach passenger 3 true true");
        e("add train 1 W0001");
        e("create engine steam T3 Emma 20 true false");
        e("add train 1 T3-Emma");
        e("put train 1 at (1,1) in direction 1,0");
        assertTrue(Terminal.buffer.equals("OK"));
        e("create engine diesel 1 Tom 6 true true");
        e("list engines");
        assertTrue(Terminal.buffer.equals("none d 1 Tom 6 true true\n" + "1 s T3 Emma 20 true false"));
        e("show train 000001");
    }

    @Test
    public void testPutting() {
        Terminal.silent = false;
        e("add track (0,0) -> (10000,0)");
        assertTrue(Terminal.buffer.equals("1"));
        e("create engine steam T3 Emma 20 true false");
        assertTrue(Terminal.buffer.equals("T3-Emma"));
        e("create engine diesel 1 Tom 6 true true");
        e("add train 1 T3-Emma");
        e("add train 1 1-Tom");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("put train 1 at (1,0) in direction 100,0");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("put train 1 at (100,0) in direction 100,0");
        assertTrue(Terminal.buffer.equals("OK"));
        e("put train 3 at (1,0) in direction 100,0");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("delete train 1");
        e("step 0");
        assertTrue(Terminal.buffer.equals("OK"));
        e("create engine electrical T4 Emma 1 true true");
        assertTrue(Terminal.buffer.equals("T4-Emma"));
        e("create train-set T5 Emma 10 true true");
        e("create train-set T6 Emma 1 true true");
        e("create coach special 1 true true");
        e("create coach passenger 1 true true");
        e("create coach freight 1 true true");
        e("add train 1 W1");
        assertTrue(Terminal.buffer.equals("special coach W1 added to train 1"));
        e("put train 1 at (100,0) in direction 100,0");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("add train 1 W2");
        assertTrue(Terminal.buffer.equals("passenger coach W2 added to train 1"));
        e("add train 1 W3");
        assertTrue(Terminal.buffer.equals("freight coach W3 added to train 1"));
        e("add train 1 T4-Emma");
        assertTrue(Terminal.buffer.equals("electrical engine T4-Emma added to train 1"));
        e("add train 1 T5-Emma");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("add train 1 1-Tom");
        assertTrue(Terminal.buffer.equals("diesel engine 1-Tom added to train 1"));
        e("add train 1 T3-Emma");
        assertTrue(Terminal.buffer.equals("steam engine T3-Emma added to train 1"));
        e("add train 2 T5-Emma");
        e("add train 2 T6-Emma");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("create train-set T5 Emma2 1 true true");
        e("add train 2 T5-Emma2");
        assertTrue(Terminal.buffer.equals("train-set T5-Emma2 added to train 2"));
        e("show train 1");
////        assertTrue(Terminal.buffer
////                .equals("                                                                             ___\n"
////                        + "               ____                                                            \\\n"
////                        + "/--------------|  | ____________________                        _______________/__     _____________|____        ++      +------\n"
////                        + "\\--------------|  | |  ___ ___ ___ ___ | |                  |  /_| ____________ |_\\   /_| ____________ |_\\       ||      |+-+ |\n"
////                        + "  | |          |  | |  |_| |_| |_| |_| | |                  | /   |____________|   \\ /   |____________|   \\    /---------|| | |\n"
////                        + " _|_|__________|  | |__________________| |                  | \\                    / \\                    /   + ========  +-+ |\n"
////                        + "|_________________| |__________________| |__________________|  \\__________________/   \\__________________/   _|--/~\\------/~\\-+\n"
////                        + "   (O)       (O)       (O)        (O)       (O)        (O)      (O)(O)      (O)(O)     (O)(O)      (O)(O)   //// \\_/      \\_/"));
//////        assertTrue(Terminal.buffer.contains(
//////                "/--------------|  | ____________________                        _______________/__     _____________|____        ++      +------\n"
////                        + "\\--------------|  | |  ___ ___ ___ ___ | |                  |  /_| ____________ |_\\   /_| ____________ |_\\       ||      |+-+ |"));
        e("step -10");
        assertTrue(Terminal.buffer.equals("OK"));
        e("put train 1 at (100,0) in direction 100,0");
        e("step -10");
        e("step -10");
        e("step -50");
        assertTrue(Terminal.buffer.equals("Train 1 at (30,0)"));
        e("step -1");
        assertTrue(Terminal.buffer.contains("Crash of train 1"));
        e("list trains");
        assertTrue(Terminal.buffer.equals("1 W1 W2 W3 T4-Emma 1-Tom T3-Emma\n" + "2 T5-Emma T5-Emma2"));
        e("jbeufbeuf");
        assertTrue(Terminal.buffer.contains("Error, command unknown."));
        e("list engines");
        assertTrue(Terminal.buffer
                .equals("1 d 1 Tom 6 true true\n" + "1 s T3 Emma 20 true false\n" + "1 e T4 Emma 1 true true"));
        e("list train-sets");
//        assertTrue(Terminal.buffer.equals("2 T5 Emma 1 true true\n" + 
//                "2 T5 Emma2 1 true true\n" + 
//                "none T6 Emma 1 true true"));
        e("list coaches");
        assertTrue(Terminal.buffer.equals("1 1 s 1 true true\n2 1 p 1 true true\n3 1 f 1 true true"));
        e("add track (0,0) -> (-100,0)");
        assertTrue(Terminal.buffer.equals("2"));
        e("step 0");
        assertTrue(Terminal.buffer.equals("OK"));
        e("step -220"); // TODO entgleisen bei ende vom Track
        assertTrue(Terminal.buffer.equals("OK"));
        e("put train 1 at (-100,0) in direction -1,0");
        assertTrue(Terminal.buffer.equals("OK"));
        e("put train 2 at (0,0) in direction -1,0");
        assertTrue(Terminal.buffer.equals("OK"));
        e("step 0");
        assertTrue(Terminal.buffer.equals("Train 1 at (-100,0)\n" + "Train 2 at (0,0)"));
        e("step 1");
        assertTrue(Terminal.buffer.equals("Crash of train 1,2"));
        assertTrue("Crash of train 1,2".contentEquals(Terminal.buffer));
        /*
         * ZWei Fehler 1. Train 2 bewegt sich bei step 1 nicht 2. Crash müsste beide
         * betreffen
         */
        // TODO getList überall entfernen
        
        e("show train 2");

    }

    /**
     * Wenn einn Zug entgleist ist das Gleis immernoch besetzt
     */
    @Test
    public void backwardsANDEntgleisem() {
//        Terminal.silent = false;
        e("add track (0,0) -> (100,0)");
        assertTrue("1".contentEquals(Terminal.buffer));
        e("add track (0,0) -> (-100,0)");
        e("create engine steam T3 Emma 10 true false");
        e("add train 1 T3-Emma");
        e("put train 1 at (-100,0) in direction -1,0");
        e("create engine steam T4 Emma 10 true false");
        e("add train 2 T4-Emma");
        e("put train 2 at (0,0) in direction -1,0");
        e("step 1");
        assertTrue("Crash of train 1,2".contentEquals(Terminal.buffer));
        reset();
        e("add track (0,0) -> (100,0)");
        e("add track (0,0) -> (-100,0)");
        e("create engine steam T3 Emma 1 true false");
        e("add train 1 T3-Emma");
        e("put train 1 at (-100,0) in direction -1,0");
        e("create engine steam T4 Emma 10 true false");
        e("add train 2 T4-Emma");
        e("put train 2 at (0,0) in direction -1,0");
        e("step 0");
        e("step 1");
        assertTrue("Crash of train 1\nTrain 2 at (-1,0)".contentEquals(Terminal.buffer));

    }

    @Test
    public void deletingSwitches() {
//        Terminal.silent = false;
        e("add track (1,1) -> (5,1)");
      e("add switch (5,1) -> (8,1),(5,3)");
      e("add track (10,1) -> (8,1)");
      e("add track (10,-1) -> (10,1)");
      e("add track (10,-1) -> (1,-1)");
      e("delete track 2");
      assertTrue(Terminal.buffer.contains("Error, "));
      e("add track (1,-1) -> (1,1)");
      e("delete track 2");
      e("add track (5,1) -> (8,1)");
      assertTrue("2".contentEquals(Terminal.buffer));
      e("delete track 2");
      e("add switch (5,1) -> (8,1),(5,3)");
      e("add switch (5,3) -> (10,3),(5,8)");
      e("delete track 2");
      assertTrue(Terminal.buffer.contains("Error, "));
      e("delete track 3");
      e("add switch (10,1) -> (10,3),(8,1)");
      e("delete track 2");
      assertTrue("OK".contentEquals(Terminal.buffer));
    }

    @Test
    public void placingTRains() {
        Terminal.silent = false;
        e("add track (1,1) -> (5,1)");
        e("create engine steam T4 Emma 1 true false");
        e("add train 1 T4-Emma");
        e("put train 1 at (1,1) in direction 1,0");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("add track (1,1) -> (1,5)");
        e("put train 1 at (1,1) in direction 0,-1");
        assertFalse(Terminal.buffer.contains("Error, "));
        e("step 2");
        
    }
   
   @Test
   public void addDublicats() {
//       Terminal.silent = false;
       e("add track (1,1) -> (5,1)");
       e("add track (5,1) -> (1,1)");
       e("add switch (1,1) -> (5,1),(1,10)");
   }
}
