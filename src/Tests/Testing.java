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

import julian.modelrailway.main.Commands;
import julian.modelrailway.main.ModelRailWay;

public class Testing {

    ModelRailWay m;
    Commands ui;

    /**
     * Resets before every Test
     */
    @Before
    public void start() {
        Terminal.silent = true;
        // Bei Bedarf hier die ausgabe wieder einschalten!
        reset();
    }

    private void reset() {
        m = new ModelRailWay();

    }

    @After
    public void TerminalBackToNormal() {
        Terminal.silent = false;
    }

    /**
     * Helper Method, to simplify writing tests.
     * 
     * @param command
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    private void e(String command) throws Exception {
        ui.execute(command, m);
    }

    /**
     * Example Commands from Sheet
     * 
     * @throws Exception
     */
    @Test
    public void Beispielablauf() throws Exception {
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
//                .contentEquals("____________________\n"+ "|  ___ ___ ___ ___ |\n"+ "|  |_| |_| |_| |_| |\n"
//                        + "|__________________|\n"+ "|__________________|\n"+ "  (O)        (O)"));
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
//        assertTrue(Terminal.buffer.equals("    ++      +------ ____________________ ____________________\n"
//                + "    ||      |+-+ |  |  ___ ___ ___ ___ | |  ___ ___ ___ ___ |\n"
//                + "  /---------|| | |  |  |_| |_| |_| |_| | |  |_| |_| |_| |_| |\n"
//                + " + ========  +-+ |  |__________________| |__________________|\n"
//                + "_|--/~\\------/~\\-+  |__________________| |__________________|\n"
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
    public void testingRailSystem() throws Exception {
//        Terminal.silent = true;
        e("add track (0,0) -> (10,0)");
        e("add track (0,0) -> (0,10)");
        e("add track (10,10) -> (10,0)");
        e("add track (10,10) -> (0,10)");
        e("list tracks");
        assertTrue(Terminal.buffer.contentEquals("t 1 (0,0) -> (10,0) 10\n"+ "t 2 (0,0) -> (0,10) 10\n"
                + "t 3 (10,10) -> (10,0) 10\n"+ "t 4 (10,10) -> (0,10) 10"));
    }

    @Test
    public void testinDeleteTracks() throws Exception {
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
        assertTrue(Terminal.buffer.contains("Error, "));
        e("delete track 4");
        e("delete track 3");
        e("delete track 2");
        e("put train 1 at (3,1) in direction 1,0");
        e("step 20");
        assertTrue(Terminal.buffer.contentEquals("Crash of train 1"));
        e("delete track 1");
        e("add track (1,1) -> (5,1)");
        e("put train 1 at (3,1) in direction -10000000,0");
        e("list tracks");
        assertTrue(Terminal.buffer.contentEquals("t 1 (1,1) -> (5,1) 4"));

    }

    @Test
    public void multipleCollids() throws Exception {
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
        e("set switch 4 position (10,1)");
        e("set switch 2 position (8,1)");
        e("set switch 9 position (12,-3)");
        e("list tracks");
        e("put train 1 at (1,-1) in direction 0,1");
        e("put train 2 at (3,1) in direction -1,0");
//        e("set switch 2 position (5,3)");
//        e("set switch 9 position (14,3)");
        e("put train 3 at (6,1) in direction -1,0");
        e("step 1");
        e("step 1");
        assertTrue("Crash of train 1,2,3".equals(Terminal.buffer));
        e("put train 1 at (1,1) in direction 1,0");
        e("put train 2 at (2,1) in direction -1,0");
        e("put train 3 at (5,-3) in direction -1,0");
        e("step 1");
        assertTrue(Terminal.buffer.contentEquals("Crash of train 1,2\n"+ "Train 3 at (4,-3)"));
        e("set switch 2 position (5,3)");
        e("set switch 9 position (14,3)");
        e("step 32767");
        assertTrue(Terminal.buffer.contentEquals("Crash of train 3"));
    }

    @Test
    public void closeRoads() throws Exception {
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
        assertTrue(Terminal.buffer.equals("t 1 (1,1) -> (5,1) 4\n"+ "t 2 (1,1) -> (1,0) 1\n"+ "t 3 (2,0) -> (1,0) 1\n"
                + "t 4 (5,0) -> (2,0) 3\n"+ "t 5 (10,0) -> (5,0) 5\n"+ "t 6 (10,0) -> (11,0) 1\n"
                + "t 7 (11,0) -> (11,1) 1\n"+ "t 8 (11,1) -> (5,1) 6"));
//        e("create coach passenger 3 true true");
//        e("add train 1 W0001");
        e("create engine steam T3 Emma 20 true false");
        e("add train 1 T3-Emma");
        e("put train 1 at (1,1) in direction 1,0");
        assertTrue(Terminal.buffer.equals("OK"));
        e("create engine diesel 1 Tom 6 true true");
        e("list engines");
        assertTrue(Terminal.buffer.equals("none d 1 Tom 6 true true\n"+ "1 s T3 Emma 20 true false"));
        e("show train 000001");
    }

    @Test
    public void testPutting() throws Exception {
//        Terminal.silent = false;
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
        assertTrue(Terminal.buffer.equals("1 W1 W2 W3 T4-Emma 1-Tom T3-Emma\n"+ "2 T5-Emma T5-Emma2"));
        e("jbeufbeuf");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("list engines");
        assertTrue(Terminal.buffer
                .equals("1 d 1 Tom 6 true true\n"+ "1 s T3 Emma 20 true false\n"+ "1 e T4 Emma 1 true true"));
        e("list train-sets");
        e("list coaches");
        assertTrue(Terminal.buffer.equals("1 1 s 1 true true\n2 1 p 1 true true\n3 1 f 1 true true"));
        e("add track (0,0) -> (-100,0)");
        assertTrue(Terminal.buffer.equals("2"));
        e("step 0");
        assertTrue(Terminal.buffer.equals("OK"));
        e("step -220"); // TODO entgleisen bei ende vom Track
        assertTrue(Terminal.buffer.equals("OK"));
        e("put train 1 at (-99,0) in direction -1,0");
        assertTrue(Terminal.buffer.equals("OK"));
        e("list tracks");
        e("put train 2 at (1,0) in direction -1,0");
        assertTrue(Terminal.buffer.equals("OK"));
        e("step 0");
//        assertTrue(Terminal.buffer.equals("Train 1 at (-100,0)\n"+ "Train 2 at (1,0)"));
        e("step 1");
        e("step 1");
        assertTrue(Terminal.buffer.equals("Crash of train 1,2"));
        assertTrue("Crash of train 1,2".contentEquals(Terminal.buffer));

        e("show train 2");

    }

    /**
     * Wenn einn Zug entgleist ist das Gleis immernoch besetzt
     * 
     * @throws Exception
     */
    @Test
    public void backwardsANDEntgleisem() throws Exception {
//        Terminal.silent = false;
        e("add track (0,0) -> (100,0)");
        assertTrue("1".contentEquals(Terminal.buffer));
        e("add track (0,0) -> (-100,0)");
        e("create engine steam T3 Emma 10 true false");
        e("add train 1 T3-Emma");
        e("put train 1 at (-99,0) in direction -1,0");
        e("create engine steam T4 Emma 10 true false");
        e("add train 2 T4-Emma");
        e("put train 2 at (1,0) in direction -1,0");
        e("step 2");
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
//        assertTrue("Crash of train 1\nTrain 2 at (-1,0)".contentEquals(Terminal.buffer));

    }

    @Test
    public void deletingSwitches() throws Exception {
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
        e("create engine steam T4 Emma 1 true false");
        e("add train 1 T4-Emma");
        e("list tracks");
        e("put train 1 at (1,1) in direction 1,0");
    }

    @Test
    public void placingTRains() throws Exception {
//        Terminal.silent = false;
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
    public void addDublicats() throws Exception {
//       Terminal.silent = false;
        e("add track (1,1) -> (5,1)");
        e("add track (5,1) -> (1,1)");
        e("add switch (1,1) -> (5,1),(1,10)");
    }

    @Test
    public void entgleisen() throws Exception {
//        Terminal.silent = false;
        e("add switch (1,1) -> (5,1),(1,10)");
        e("set switch 1 position (5,1)");
        e("create engine steam T4 Emma 1 true false");
        e("add train 1 T4-Emma");
        e("put train 1 at (2,1) in direction 1,0");
        e("set switch 1 position (5,1)");
        e("step 0");
        assertTrue("OK".contentEquals(Terminal.buffer));
        e("add track (10,1) -> (5,1)");
        e("put train 1 at (5,1) in direction 0,0");
        e("set switch 1 position (5,1)");
        e("step 0");

    }

    @Test
    public void testWString() throws Exception {
//        Terminal.silent = false;
        e("create engine steam W Emma 10 true false");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("create coach special 1 true true");
        e("create engine steam W1 Emma 10 true false");
        e("delete rolling stock W1-Emma");
        e("list engines");
        assertTrue("No engine exists".contentEquals(Terminal.buffer));
        e("list coaches");
        e("list tracks");
        assertTrue("No track exists".contentEquals(Terminal.buffer));
        e("create engine steam W1 Emma 10 true true");
        e("add train 1 W1");
        assertTrue("special coach W1 added to train 1".contentEquals(Terminal.buffer));
        e("add train 1 W1-Emma");
        e("create engine steam 1 Emma 10 true true");
        e("create train-set W1 145 4 true true");
        e("create train-set W1 146 4 true true");
        e("create train-set 1 147 4 true true");
        e("add train 2 W1-145");
        e("add train 2 W1-146");
        e("list trains");
        assertTrue("1 W1 W1-Emma\n2 W1-145 W1-146".contentEquals(Terminal.buffer));
        e("add train 2 1-147");
        assertTrue(Terminal.buffer.contains("Error, "));

    }

    @Test
    public void puttingOnCorners() throws Exception {
//        Terminal.silent = false;
        e("add track (0,0) -> (5,0)");
        assertTrue(Terminal.buffer.contentEquals("1"));
        e("add track (5,5) -> (5,0)");
        assertTrue(Terminal.buffer.contentEquals("2"));
        e("add track (5,5) -> (0,5)");
        assertTrue(Terminal.buffer.contentEquals("3"));
        e("add track (0,5) -> (0,0)");
        assertTrue(Terminal.buffer.contentEquals("4"));
//        e("delete track 1");
//        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("create engine steam 1 1 1 true true");
        assertTrue(Terminal.buffer.contentEquals("1-1"));
        e("add train 1 1-1");
        assertTrue(Terminal.buffer.contentEquals("steam engine 1-1 added to train 1"));
        e("list tracks");
        e("put train 1 at (0,0) in direction 0,1");
        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("step 2");
        assertTrue(Terminal.buffer.contentEquals("Train 1 at (0,2)"));
        e("delete train 1");
        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("add train 1 1-1");
        e("put train 1 at (0,1) in direction 0,1");
        e("create engine steam 1 2 1 true true");
        e("add train 2 1-2");
        e("put train 2 at (0,1) in direction 0,1");
        e("step 1");
    }

    @Test
    public void extensiveTest() throws Exception {
//        Terminal.silent = false;
        Terminal.printLine("Zug Komposition");
        e("create engine steam T1 Emma 1 true true");
        assertTrue(Terminal.buffer.contentEquals("T1-Emma"));
        e("create engine diesel T2 Emma 1 true true");
        assertTrue(Terminal.buffer.contentEquals("T2-Emma"));
        e("create engine electrical T3 Emma 1 false true");
        assertTrue(Terminal.buffer.contentEquals("T3-Emma"));
        e("add train 2 T1-Emma");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("add train 1 T1-Emma");
        assertTrue(Terminal.buffer.contentEquals("steam engine T1-Emma added to train 1"));
        e("add train 1 T3-Emma");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("show train 1");
        // Überprüfung selbst vornehmen.
        e("add train 3 T2-Emma");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("add train 2 T3-Emma");
        assertTrue(Terminal.buffer.contentEquals("electrical engine T3-Emma added to train 2"));
        e("add train 2 T2-Emma");
        assertTrue(Terminal.buffer.contentEquals("diesel engine T2-Emma added to train 2"));
        e("create coach passenger 1 true true");
        assertTrue(Terminal.buffer.contentEquals("1"));
        e("create coach passenger 1 true true");
        assertTrue(Terminal.buffer.contentEquals("2"));
        e("create coach passenger 1 true true");
        assertTrue(Terminal.buffer.contentEquals("3"));
        e("create coach passenger 1 true true");
        assertTrue(Terminal.buffer.contentEquals("4"));
        e("create coach passenger 1 true true");
        assertTrue(Terminal.buffer.contentEquals("5"));
        e("create coach passenger 1 true true");
        assertTrue(Terminal.buffer.contentEquals("6"));
        e("delete rolling stock W2");
        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("delete rolling stock W3");
        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("delete rolling stock W4");
        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("delete rolling stock W5");
        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("list coaches");
        e("create coach passenger 1 true true");
        assertTrue(Terminal.buffer.contentEquals("2"));
        e("create coach special 1 true true");
        assertTrue(Terminal.buffer.contentEquals("3"));
        e("delete train 1");
        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("delete train 2");
        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("delete train 3");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("list engines");
        assertTrue(Terminal.buffer.contentEquals(
                "none s T1 Emma 1 true true\n"+ "none d T2 Emma 1 true true\n"+ "none e T3 Emma 1 false true"));
        e("list coaches");
        assertTrue(Terminal.buffer.contentEquals("1 none p 1 true true\n"+ "2 none p 1 true true\n"
                + "3 none s 1 true true\n"+ "6 none p 1 true true"));
        e("delete rolling stock 1");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("delete rolling stock W1");
        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("delete rolling stock W2");
        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("delete rolling stock W3");
        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("delete rolling stock W4");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("delete rolling stock W5");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("delete rolling stock W6");
        assertTrue(Terminal.buffer.contentEquals("OK"));

        e("create engine steam W Emma 1 true true");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("create engine steam T1 Emma 10 true true");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("delete rolling stock T1-Emma");
        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("delete rolling stock T2-Emma");
        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("delete rolling stock T3-Emma");
        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("list coaches");
        assertTrue(Terminal.buffer.contentEquals("No coach exists"));
        Terminal.printLine("COMPOSITION OF LONG TRAIN");
        e("");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("create engine steam T1 Emma 1 true true");
        e("create engine diesel T2 Emma 1 true true");
        e("create engine electrical T$ Emma 1 true true");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("create engine electrical T3 Emma 1 true true");
        e("create coach special 1 true true");
        e("create coach freight 1 true true");
        e("create coach passenger 1 true true");
        e("create coach special 1 true true");
        e("create coach freight 1 true true");
        e("create coach passenger 1 true true");
        e("create coach special 1 true true");
        e("create coach freight 1 true true");
        e("create coach passenger 1 true true");
        e("list trains");
        assertTrue(Terminal.buffer.contentEquals("No train exists"));
        e("add train 1 T1-Emma");
        assertTrue(Terminal.buffer.contentEquals("steam engine T1-Emma added to train 1"));
        e("add train 1 T2-Emma");
        assertTrue(Terminal.buffer.contentEquals("diesel engine T2-Emma added to train 1"));
        e("add train 1 W1");
        assertTrue(Terminal.buffer.contentEquals("special coach W1 added to train 1"));
        e("add train 1 W2");
        assertTrue(Terminal.buffer.contentEquals("freight coach W2 added to train 1"));
        e("add train 1 W3");
        assertTrue(Terminal.buffer.contentEquals("passenger coach W3 added to train 1"));
        e("add train 1 W4");
        assertTrue(Terminal.buffer.contentEquals("special coach W4 added to train 1"));
        e("add train 1 W5");
        assertTrue(Terminal.buffer.contentEquals("freight coach W5 added to train 1"));
        e("add train 1 W6");
        assertTrue(Terminal.buffer.contentEquals("passenger coach W6 added to train 1"));
        e("add train 1 W7");
        assertTrue(Terminal.buffer.contentEquals("special coach W7 added to train 1"));
        e("add train 1 W8");
        assertTrue(Terminal.buffer.contentEquals("freight coach W8 added to train 1"));
        e("add train 1 W9");
        assertTrue(Terminal.buffer.contentEquals("passenger coach W9 added to train 1"));
        e("show train 01");
        // Überprüfung selbst vornehmen
        e("list trains");
        assertTrue(Terminal.buffer.contentEquals("1 T1-Emma T2-Emma W1 W2 W3 W4 W5 W6 W7 W8 W9"));
        Terminal.printLine("TRACK-TEST");
        e("add track (0,1) -> (1,2)");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("add track (0,0) -> (5,0)");
        assertTrue(Terminal.buffer.contentEquals("1"));
        e("add switch (5,0) -> (10,0),(5,5)");
        assertTrue(Terminal.buffer.contentEquals("2"));
        e("add switch (0,5) -> (0,0),(0,10)");
        assertTrue(Terminal.buffer.contentEquals("3"));
        e("add track (10,0) -> (10,10)");
        assertTrue(Terminal.buffer.contentEquals("4"));
        e("add track (10,10) -> (0,10)");
        assertTrue(Terminal.buffer.contentEquals("5"));
        e("list tracks");
        assertTrue(Terminal.buffer.contentEquals("t 1 (0,0) -> (5,0) 5\n"+ "s 2 (5,0) -> (10,0),(5,5)\n"
                + "s 3 (0,5) -> (0,0),(0,10)\n"+ "t 4 (10,0) -> (10,10) 10\n"+ "t 5 (10,10) -> (0,10) 10"));
        e("put train 1 at (0,1) in direction 0,1");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("set switch 1 position (0,1)");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("set switch 2 position (10,0)");
        assertTrue(Terminal.buffer.contains("OK"));
        e("put train 1 at (0,1) in direction 0,1");
        assertTrue(Terminal.buffer.contains("Error, "));
        e("set switch 3 position (0,0)");
        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("list tracks");
        assertTrue(Terminal.buffer.contentEquals("t 1 (0,0) -> (5,0) 5\n"+ "s 2 (5,0) -> (10,0),(5,5) 5\n"
                + "s 3 (0,5) -> (0,0),(0,10) 5\n"+ "t 4 (10,0) -> (10,10) 10\n"+ "t 5 (10,10) -> (0,10) 10"));
        e("put train 1 at (0,1) in direction 0,1");
        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("step 1");
        assertTrue(Terminal.buffer.contentEquals("Train 1 at (0,2)"));
        e("step 1");
        e("step 1");
        e("step 1");

        e("step 1");
        assertTrue(Terminal.buffer.contentEquals("Crash of train 1"));

        e("step 1");
        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("delete track 5");
        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("delete track 4");
        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("delete track 3");
        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("delete track 2");
        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("delete track 1");
        assertTrue(Terminal.buffer.contentEquals("OK"));
        e("list tracks");
        assertTrue(Terminal.buffer.contentEquals("No track exists"));
        e("add track (0,0) -> (0,10)");
        assertTrue(Terminal.buffer.contentEquals("1"));
        e("add track (0,10) -> (0,20)");
        assertTrue(Terminal.buffer.contentEquals("2"));
        e("add track (0,20) -> (0,30)");
        assertTrue(Terminal.buffer.contentEquals("3"));
        e("delete track 2");
        assertTrue(Terminal.buffer.contains("Error, "));

    }

    @Test
    public void backOnNotSetSwitch() throws Exception {

        e("add track (0,0) -> (10,0)");

        e("create engine steam T3 Emma 5 false true");
        e("add train 1 T3-Emma");
        e("put train 1 at (5,0) in direction -1,0");
        e("add switch (10,0) -> (10,3),(15,0)");
        e("step 10");
        e("list trains");
        e("set switch 2 position (15,0)");
        e("step -1");
        e("set switch 2 position (15,0)");
        e("step -1");

    }

    @Test
    public void testcase12() throws Exception {

        e("add switch (0,0) -> (10,0),(0,10)");
        e("create engine steam T1 Emma 1 false true");
        e("add train 1 T1-Emma");
        e("put train 1 at (2,0) in direction 1,0");
        e("set switch 1 position (10,0)");
        e("put train 1 at (2,0) in direction 1,0");
        e("step 0");
        e("set switch 1 position (10,0)");
        e("step 0");
        e("exit");
    }



    @Test
    public void somSorting() throws Exception {
//        Terminal.silent = false;
        e("add track (0,0) -> (5,0)");
        e("add track (5,0) -> (10,0)");
        e("add track (10,0) -> (15,0)");
        e("add track (15,0) -> (20,0)");
        e("add track (20,0) -> (25,0)");
        e("add track (25,0) -> (30,0)");
        e("create coach passenger 1 true true");
        e("create coach passenger 1 true true");
        e("create coach passenger 1 true true");
        e("create coach special 1 true true");
        e("create coach passenger 1 true true");
        e("create coach passenger 1 true true");
        e("create coach passenger 1 true true");
        e("create coach freight 1 true true");
        e("create coach passenger 1 true true");
        e("create coach passenger 1 true true");
        e("create coach passenger 1 true true");
        e("create coach passenger 1 true true");
        e("create engine steam T3 r 1 true true");
        e("create engine diesel T3 b 1 true true");
        e("create engine steam T3 c 1 true true");
        e("create engine steam T3 F 1 true true");
        e("create engine electrical T3 e 1 true true");
        e("create engine steam T1 o 1 true true");
        e("create engine steam T3 g 1 true true");
        e("create engine steam T3 a 1 true true");
        e("create engine diesel T3 j 1 true true");
        e("create engine steam T3 1 1 true true");
        e("create engine electrical T3 A 1 true true");
        e("create train-set T3 tr 1 true true");
        e("create train-set RRR A 1 true true");
        e("create train-set RRT A 1 true true");
        e("create train-set RRR B 1 true true");
        e("list coaches");
        e("list engines");
        e("list train-sets");
        e("add train 1 W1");
        e("add train 1 T1-o");
        e("add train 1 W2");
        e("add train 1 W3");
        e("add train 1 T3-A");
        e("add train 1 W8");
        e("show train 1");
        e("put train 1 at (3,0) in direction 1,0");
        e("put train 1 at (3,0) in direction 0,1");
        e("add train 1 T3-F");
        e("show train 1");
        e("put train 1 at (3,0) in direction 1,0");
        e("put train 1 at (3,0) in direction -1,0");
        e("add train 2 W5");
        e("add train 2 T3-tr");
        e("delete train 2");
        e("add train 2 T3-F");
        e("delete train 2");
        e("add train 2 T3-g");
        e("add train 2 T3-tr");
        e("delete train 2");
        e("add train 2 T3-tr");
        e("add train 2 T3-g");
        e("delete train 2");
        e("add train 2 T3-tr");
        e("add train 2 W5");
        e("delete train 2");
        e("add train 2 T3-tr");
        e("add train 2 RRR-A");
        e("delete train 2");
        e("add train 2 RRR-B");
        e("add train 2 RRR-A");
        e("list trains");
        e("add train 2 RRT-A");
        e("list trains");
        e("put train 1 at (20,0) in direction -1,0");
        e("put train 2 at (5,0) in direction -1,0");
        e("step 0");
        e("step 3");
        e("step 1");
        e("put train 1 at (10,0) in direction 1,0");
        e("put train 2 at (12,0) in direction -1,0");
    }
    
    
    @Test
    public void largGen() throws Exception {
//        Terminal.silent = false;
        ////// Setup track network");
        /////// Simple illustration: https://cloud.schwaerzle.net/index.php/s/B4KoWC55EkQPtjq");
        //e("add");

//        //e("add track");
//        assertTrue(Terminal.buffer.contains("Error,"));
//        //e("add track () -> ()");
//        assertTrue(Terminal.buffer.contains("Error,"));
        e("list tracks");
        //No track exists");
        e("add track (5,0) -> (16,0)");
        //1");
        e("add track (16,3) -> (16,0)");
        //2");
        e("add switch (16,3) -> (14,3),(18,3)");
        //3");
        e("list tracks");
        //t 1 (5,0) -> (16,0) 11");
        //t 2 (16,3) -> (16,0) 3");
        Terminal.printLine(">s 3 (16,3) -> (14,3),(18,3)");
        e("set switch 3 position (14,3)");
        Terminal.printLine(">OK");
        e("list tracks");
        Terminal.printLine(">t 1 (5,0) -> (16,0) 11");
        Terminal.printLine(">t 2 (16,3) -> (16,0) 3");
        Terminal.printLine(">s 3 (16,3) -> (14,3),(18,3) 2");
        e("add track (5,0) -> (5,8)");
        Terminal.printLine(">4");
        //// Triple connection");
        e("add track (5,0) -> (5,-5)");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// Diagonal");
        e("add track (5,8) -> (0,0)");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// Not attached");
        e("add track (0,0) -> (0,5)");
        assertTrue(Terminal.buffer.contains("Error,"));
        e("add track (5,8) -> (2,8)");
        Terminal.printLine(">5");
        e("add track (2,8) -> (2,11)");
        Terminal.printLine(">6");
        e("add track (2,11) -> (5,11)");
        Terminal.printLine(">7");
        e("add switch (5,13) -> (5,11),(2,13)");
        Terminal.printLine(">8");
        e("add track (5,13) -> (16,13)");
        Terminal.printLine(">9");
        e("add track (16,13) -> (16,11)");
        Terminal.printLine(">10");
        e("add switch (16,11) -> (14,11),(18,11)");
        Terminal.printLine(">11");
        e("add track (14,11) -> (14,3)");
        Terminal.printLine(">12");
        e("add track (18,11) -> (18,3)");
        Terminal.printLine(">13");
        ////");
        //// Coaches");
        e("list coaches");
        Terminal.printLine(">No coach exists");
        e("create coach passenger 1 true true");
        Terminal.printLine(">1");
        //// Invalid length");
        e("create coach passenger 0 true true");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// Invalid type");
        e("create coach catTransporter 5 true true");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// No coupling");
        e("create coach freight 1 false false");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// No train");
        e("list coaches");
        Terminal.printLine(">1 none p 1 true true");
        e("add train 1 W1");
        Terminal.printLine(">passenger coach W1 added to train 1");
        e("list coaches");
        Terminal.printLine(">1 1 p 1 true true");
        //// Coach in use");
        e("delete rolling stock W1");
        assertTrue(Terminal.buffer.contains("Error,"));
        e("delete train 1");
        //OK");
        e("delete rolling stock W1");
        Terminal.printLine(">OK");
        ////");
        //// Engines");
        e("list engines");
        Terminal.printLine(">No engine exists");
        //// Unicode letters allowed");
        e("create engine steam T3 Küstenwagen 1 true true");
        Terminal.printLine(">T3-Küstenwagen");
        //// Invalid length");
        e("create engine steam T3 Emma -10 true true");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// Invalid type");
        e("create engine catSteam T3 Emma 1 true true");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// No coupling");
        e("create engine steam T3 Emma 1 false false");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// Wrong series");
        e("create engine steam W Emma 1 true true");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// Forbidden symbols");
        e("create engine steam T3 Emm.a 1 true true");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// No train");
        e("list engines");
        Terminal.printLine(">none s T3 Küstenwagen 1 true true");
        e("add train 1 T3-Küstenwagen");
        Terminal.printLine(">steam engine T3-Küstenwagen added to train 1");
        e("list engines");
        Terminal.printLine(">1 s T3 Küstenwagen 1 true true");
        //// Engine in use");
        e("delete rolling stock T3-Küstenwagen");
        assertTrue(Terminal.buffer.contains("Error,"));
        e("delete train 1");
        //OK");
        e("delete rolling stock T3-Küstenwagen");
        Terminal.printLine(">OK");
        ////");
        //// Train-Sets");
        e("list train-sets");
        Terminal.printLine(">No train-set exists");
        //// Unicode letters allowed");
        e("create train-set KIT Exprεss 3 true true");
        Terminal.printLine(">KIT-Exprεss");
        //// Invalid length");
        e("create train-set KIT Exprεss -42 true true");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// No coupling");
        e("create train-set KIT Exprεss 3 false false");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// Wrong series");
        e("create train-set KIT Exprεss 3 true true");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// Forbidden symbols");
        e("create train-set KIT _Exprεss 3 true true");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// No train");
        e("list train-sets");
        Terminal.printLine(">none KIT Exprεss 3 true true");
        e("add train 1 KIT-Exprεss");
        //train-set KIT-Exprεss added to train 1");
        e("list train-sets");
        Terminal.printLine(">1 KIT Exprεss 3 true true");
        //// Engine in use");
        e("delete rolling stock KIT-Exprεss");
        assertTrue(Terminal.buffer.contains("Error,"));
        e("delete train 1");
        Terminal.printLine(">OK");
        e("delete rolling stock KIT-Exprεss");
        Terminal.printLine(">OK");
        ////");
        //// Trains");
        //// Create some rolling stock");
        e("create coach passenger 1 true true");
        Terminal.printLine(">1");
        e("create coach passenger 3 true true");
        Terminal.printLine(">2");
        e("create coach passenger 5 true true");
        Terminal.printLine(">3");
        e("create coach freight 1 true false");
        Terminal.printLine(">4");
        e("create coach special 3 true true");
        Terminal.printLine(">5");
        e("create coach special 3 false true");
        Terminal.printLine(">6");
        e("create engine steam KIT Rennmühle 1 true true");
        Terminal.printLine(">KIT-Rennmühle");
        e("create engine electrical KIT Defekt 1 true true");
        Terminal.printLine(">KIT-Defekt");
        e("create engine diesel KIT 42 1 true false");
        Terminal.printLine(">KIT-42");
        e("create train-set KVV W1 2 true true");
        Terminal.printLine(">KVV-W1");
        e("create train-set KVV W2 2 true true");
        Terminal.printLine(">KVV-W2");
        e("create train-set KVV W3 2 true true");
        Terminal.printLine(">KVV-W3");
        e("create train-set VVS W1 3 true true");
        Terminal.printLine(">VVS-W1");
        e("create train-set VVS W2 3 true true");
        Terminal.printLine(">VVS-W2");
        e("list trains");
        Terminal.printLine(">No train exists");
        //// Some train composition tests");
        //// Invalid id");
        e("add train 0 W1");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// Invalid vehicle id");
        e("add train 1 W");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// Non-existing vehicle");
        e("add train 1 T3-Emma");
        assertTrue(Terminal.buffer.contains("Error,"));
        e("add train 1 KIT-Rennmühle");
        //steam engine KIT-Rennmühle added to train 1");
        //// No connection");
        e("add train 1 W6");
        assertTrue(Terminal.buffer.contains("Error,"));
        e("add train 1 W2");
        //passenger coach W2 added to train 1");
        e("list trains");
        Terminal.printLine(">1 KIT-Rennmühle W2");
        e("add train 2 VVS-W1");
        Terminal.printLine(">train-set VVS-W1 added to train 2");
        e("add train 3 KVV-W1");
        Terminal.printLine(">train-set KVV-W1 added to train 3");
        //// Not compatible");
        e("add train 3 W1");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// Different series");
        e("add train 3 VVS-W1");
        assertTrue(Terminal.buffer.contains("Error,"));
        e("add train 3 KVV-W2");
        Terminal.printLine(">train-set KVV-W2 added to train 3");
        e("delete train 2");
        Terminal.printLine(">OK");
        e("delete train 2");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// Invalid train id");
        e("add train 4 VVS-W1");
        assertTrue(Terminal.buffer.contains("Error,"));
        e("add train 2 VVS-W1");
        //train-set VVS-W1 added to train 2");
        e("list trains");
        //1 KIT-Rennmühle W2");
        //2 VVS-W1");
        //3 KVV-W1 KVV-W2");
        ////");
        e("add train 4 W3");
        //passenger coach W3 added to train 4");
        e("add train 4 KIT-Defekt");
        //electrical engine KIT-Defekt added to train 4");
        e("add train 4 W5");
        //special coach W5 added to train 4");
        ////");
        //// Unset switch");
        e("step 1");
        assertTrue(Terminal.buffer.contains("Error,"));
        e("put train 1 at (5,2) in direction 0,1");
        assertTrue(Terminal.buffer.contains("Error,"));
        ////");
        e("set switch 11 position (14,11)");
        //OK");
        e("set switch 8 position (5,11)");
        //OK");
        e("step 0");
        //OK");
        e("step 1");
        //OK");
        ////");
        //// Invalid data");
        e("put train 1 at (5,2) in direction ,1");
        assertTrue(Terminal.buffer.contains("Error,"));
        e("put train 1 at (5,2) in direction (0,1)");
        assertTrue(Terminal.buffer.contains("Error,"));
        e("put train 5 at (5,2) in direction (0,1)");
        assertTrue(Terminal.buffer.contains("Error,"));
        e("put train 1 at (5,2) in direction 1,1");
        assertTrue(Terminal.buffer.contains("Error,"));
        e("put train 1 at (15,15) in direction 0,1");
        assertTrue(Terminal.buffer.contains("Error,"));
        e("put train 1 at (5,2) in direction 1,0");
        assertTrue(Terminal.buffer.contains("Error,"));
        ////");
        //// Invalid train");
        e("put train 4 at (5,2) in direction 0,1");
        assertTrue(Terminal.buffer.contains("Error,"));
        e("add train 4 KIT-42");
        //diesel engine KIT-42 added to train 4");
        //// No coupling");
        e("add train 4 W4");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// Train setup finished");
        //// Lengths: 4, 3, 4, 10");
        ////");
        e("put train 4 at (5,2) in direction 0,1");
        //OK");
        e("step 6");
        //Train 4 at (5,8)");
        //// One round");
        e("step 58");
        //Train 4 at (5,8)");
        ////");
        //// Invalid syntax");
        e("delete track -20");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// Non-existing track");
        e("delete track 20");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// Train 4 is on this track");
        e("delete track 1");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// ... and also on this one");
        e("delete track 4");
        assertTrue(Terminal.buffer.contains("Error,"));
        e("delete track 2");
        //OK");
        //// This would split the network");
        e("delete track 10");
        assertTrue(Terminal.buffer.contains("Error,"));
        e("delete track 6");
        assertTrue(Terminal.buffer.contains("Error,"));
        e("delete track 8");
        assertTrue(Terminal.buffer.contains("Error,"));
        //// But this switch can still be removed");
        e("delete track 3");
        //OK");
        ////");
        //// Restore network");
        e("add track (16,3) -> (16,0)");
        //2");
        e("add switch (16,3) -> (14,3),(18,3)");
        //3");
        e("step 1");
        assertTrue(Terminal.buffer.contains("Error,"));
        e("set switch 3 position (14,3)");
        //OK");
        //// Another round");
        e("step 58");
        //Train 4 at (5,8)");
        //// train is on connection to track");
        e("delete track 4");
        assertTrue(Terminal.buffer.contains("Error,"));
        ////");
        //// Derail the train");
        e("set switch 8 position (2,13)");
        //OK");
        e("step 9");
        //Train 4 at (5,11)");
        e("step 1");
        //Crash of train 4");
        e("step 1");
        //OK");
        e("set switch 8 position (5,11)");
        //OK");
        ////");
        //// Lengths: 4, 3, 4, 10");
        //// Switches: 3 (16,3) -> [14,3],(18,3); 8 (5,13) -> [5,11],(2,13), 11 (16,11) -> [14,11],(18,11)");
        //// Frontal collision at connection");
        e("put train 1 at (8,0) in direction 0,1");
        //// Wrong direction");
        assertTrue(Terminal.buffer.contains("Error,"));
        e("put train 1 at (8,0) in direction -1,0");
        //OK");
        //// Already on track");
        e("put train 1 at (2,11) in direction 1,0");
        assertTrue(Terminal.buffer.contains("Error,"));
        e("put train 2 at (5,3) in direction 0,-1");
        //OK");
        e("step 3");
        //Crash of train 1,2");
        ////");
        //// Lengths: 4, 3, 4, 10");
        //// Switches: 3 (16,3) -> [14,3],(18,3); 8 (5,13) -> [5,11],(2,13), 11 (16,11) -> [14,11],(18,11)");
        //// Frontal collision on track");
        e("put train 1 at (12,0) in direction -1,0");
        //OK");
        e("put train 2 at (5,3) in direction 0,-1");
        //OK");
        e("step 3");
        //Train 1 at (9,0)");
        //Train 2 at (5,0)");
        e("step 1");
        //Crash of train 1,2");
        ////");
        //// Lengths: 4, 3, 4, 10");
        //// Switches: 3 (16,3) -> [14,3],(18,3); 8 (5,13) -> [5,11],(2,13), 11 (16,11) -> [14,11],(18,11)");
        //// Frontal to back collision on track (on connection not possible anymore)");
        e("put train 1 at (16,1) in direction 0,1");
        //OK");
        e("put train 2 at (5,1) in direction 0,-1");
        //OK");
        e("step 1");
        //Train 1 at (16,2)");
        //Train 2 at (5,0)");
        e("step 1");
        //Crash of train 1,2");
        ////");
        //// Lengths: 4, 3, 4, 10");
        //// Switches: 3 (16,3) -> [14,3],(18,3); 8 (5,13) -> [5,11],(2,13), 11 (16,11) -> [14,11],(18,11)");
        //// Almost a front to back collision");
        e("put train 1 at (16,2) in direction 0,1");
        //OK");
        e("put train 2 at (5,1) in direction 0,-1");
        //OK");
        e("step 1");
        //Train 1 at (16,3)");
        //Train 2 at (5,0)");
        e("step 1");
        //Train 1 at (15,3)");
        //Train 2 at (6,0)");
        //// Crash both trains");
        e("set switch 11 position (18,11)");
        //OK");
        e("step 24");
        //Crash of train 1");
        //Crash of train 2");
        //// Reset switch");
        e("set switch 11 position (14,11)");
        //OK");
        ////");
        //// Lengths: 4, 3, 4, 10");
        //// Switches: 3 (16,3) -> [14,3],(18,3); 8 (5,13) -> [5,11],(2,13), 11 (16,11) -> [14,11],(18,11)");
        //// Three trains can go without collision");
        e("put train 1 at (5,0) in direction 0,-1");
        //OK");
        e("put train 2 at (14,11) in direction 0,1");
        //OK");
        e("put train 3 at (5,11) in direction 0,-1");
        //OK");
        e("step 58");
        //Train 1 at (5,0)");
        //Train 2 at (14,11)");
        //Train 3 at (5,11)");
        //// Train 2 will derail");
        e("set switch 11 position (18,11)");
        //OK");
        e("step 58");
        //Crash of train 1");
        //Crash of train 3");
        //// Reset switch");
        e("set switch 11 position (14,11)");
        //OK");
        ////");
        //// Lengths: 4, 3, 4, 10");
        //// Switches: 3 (16,3) -> [14,3],(18,3); 8 (5,13) -> [5,11],(2,13), 11 (16,11) -> [14,11],(18,11)");
        //// Quadruple collision");
        e("put train 1 at (5,1) in direction 0,-1");
        //OK");
        e("put train 2 at (10,0) in direction 1,0");
        //OK");
        e("put train 3 at (16,1) in direction 0,-1");
        //OK");
        e("put train 4 at (4,8) in direction 1,0");
        //OK");
        e("step 1");
        //Train 1 at (5,0)");
        //Train 2 at (11,0)");
        //Train 3 at (16,0)");
        //Train 4 at (5,8)");
        e("step 1");
        //Crash of train 1,2,3,4");
        ////");
        //// Lengths: 4, 3, 4, 10");
        //// Switches: 3 (16,3) -> [14,3],(18,3); 8 (5,13) -> [5,11],(2,13), 11 (16,11) -> [14,11],(18,11)");
        //// Two independent collisions");
        e("put train 1 at (5,1) in direction 0,-1");
        //OK");
        e("put train 2 at (5,12) in direction 0,1");
        //OK");
        e("put train 3 at (10,0) in direction 1,0");
        //OK");
        e("put train 4 at (16,11) in direction -1,0");
        //OK");
        e("step 5");
        //Crash of train 1,3");
        //Crash of train 2,4");
        ////");
        //// Lengths: 4, 3, 4, 10");
        //// Switches: 3 (16,3) -> [14,3],(18,3); 8 (5,13) -> [5,11],(2,13), 11 (16,11) -> [14,11],(18,11)");
        //// Two collisions and later two collisions");
        e("put train 1 at (5,5) in direction 0,-1");
        //OK");
        e("put train 2 at (5,12) in direction 0,1");
        //OK");
        e("put train 3 at (10,0) in direction 1,0");
        //OK");
        e("put train 4 at (16,11) in direction -1,0");
        //OK");
        e("step 5");
        //Train 1 at (5,0)");
        //Crash of train 2,4");
        //Train 3 at (15,0)");
        e("step 1");
        //Crash of train 1,3");
        ////");
        //// Lengths: 4, 3, 4, 10");
        e("set switch 8 position (2,13)");
        //OK");
        //// Switches: 3 (16,3) -> [14,3],(18,3); 8 (5,13) -> (5,11),[2,13], 11 (16,11) -> [14,11],(18,11)");
        //// Collision and end of track");
        e("put train 4 at (3,13) in direction -1,0");
        //OK");
        e("put train 2 at (16,12) in direction 0,1");
        //OK");
        e("step 1");
        //Train 2 at (16,13)");
        //Train 4 at (2,13)");
        e("step 1");
        //Crash of train 2,4");
        ////");
        //// Lengths: 4, 3, 4, 10");
        //// Switches: 3 (16,3) -> [14,3],(18,3); 8 (5,13) -> (5,11),[2,13], 11 (16,11) -> [14,11],(18,11)");
        //// End of track before collision");
        e("put train 4 at (3,13) in direction -1,0");
        //OK");
        e("put train 2 at (16,11) in direction 0,1");
        //OK");
        e("step 1");
        //Train 2 at (16,12)");
        //Train 4 at (2,13)");
        e("step 1");
        //Train 2 at (16,13)");
        //Crash of train 4");
        e("step 20");
        //Crash of train 2");
        ////");
        //// Lengths: 4, 3, 4, 10");
        //// Switches: 3 (16,3) -> [14,3],(18,3); 8 (5,13) -> (5,11),[2,13], 11 (16,11) -> [14,11],(18,11)");
        //// put train would collide");
        e("put train 1 at (5,0) in direction 0,1");
        //OK");
        e("list tracks");
        e("put train 2 at (5,8) in direction 0,-1");
//        assertTrue(Terminal.buffer.contains("Error,"));
//        //// Cleanup");
//        e("step 58");
//        //Crash of train 1");
//        ////");
//        //// Lengths: 4, 3, 4, 10");
//        //// Switches: 3 (16,3) -> [14,3],(18,3); 8 (5,13) -> (5,11),[2,13], 11 (16,11) -> [14,11],(18,11)");
//        //// put train would collide with end");
//        e("put train 1 at (9,0) in direction 1,0");
//        //OK");
//        e("put train 2 at (5,8) in direction 0,-1");
//        assertTrue(Terminal.buffer.contains("Error,"));
//        //// Cleanup");
//        e("step 58");
//        //Crash of train 1");
//        ////");
//        //// Lengths: 4, 3, 4, 10");
//        //// Switches: 3 (16,3) -> [14,3],(18,3); 8 (5,13) -> (5,11),[2,13], 11 (16,11) -> [14,11],(18,11)");
//        //// end of new train would collide");
//        e("put train 1 at (9,0) in direction 1,0");
//        //OK");
//        e("put train 2 at (2,8) in direction 0,1");
//        assertTrue(Terminal.buffer.contains("Error,"));
//        //// Cleanup");
//        e("step 58");
//        //Crash of train 1");
//        ////");
//        //// Lengths: 4, 3, 4, 10");
//        //// Switches: 3 (16,3) -> [14,3],(18,3); 8 (5,13) -> (5,11),[2,13], 11 (16,11) -> [14,11],(18,11)");
//        //// put train into existing train");
//        e("put train 4 at (5,11) in direction 1,0");
//        //OK");
//        e("put train 2 at (2,11) in direction 0,1");
//        assertTrue(Terminal.buffer.contains("Error,"));
//        //// Cleanup");
//        e("step 58");
//        //Crash of train 4");
//        ////");
//        //// Lengths: 4, 3, 4, 10");
//        //// Switches: 3 (16,3) -> [14,3],(18,3); 8 (5,13) -> (5,11),[2,13], 11 (16,11) -> [14,11],(18,11)");
//        //// train on switch will derail");
//        e("put train 2 at (2,13) in direction -1,0");
//        //OK");
//        e("set switch 8 position (2,13)");
//        //OK");
//        e("step 1");
//        //OK");
//        ////");
//        //// Lengths: 4, 3, 4, 10");
//        //// Switches: 3 (16,3) -> [14,3],(18,3); 8 (5,13) -> (5,11),[2,13], 11 (16,11) -> [14,11],(18,11)");
//        //// train on unconnected switch connection will derail");
//        e("put train 2 at (5,11) in direction 1,0");
//        //OK");
//        e("set switch 8 position (2,13)");
//        //OK");
//        e("step 1");
//        //OK");
//        ////");
//        //// Lengths: 4, 3, 4, 10");
//        //// Switches: 3 (16,3) -> [14,3],(18,3); 8 (5,13) -> (5,11),[2,13], 11 (16,11) -> [14,11],(18,11)");
//        //// train cannot be placed when switch is not connected accordingly");
//        e("put train 2 at (5,12) in direction 0,1");
//        assertTrue(Terminal.buffer.contains("Error,"));
    }
    
    

}
