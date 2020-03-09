package Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.kit.informatik.Terminal;
import julian.modelrailway.Commands;
import julian.modelrailway.ModelRailWay;

/**
 * @author Julian Strietzel
 * @version 08.03.2020
 */
public class Testing2 {

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

    @SuppressWarnings("static-access")
    private void e(String command) {
        ui.execute(command, m);
    }

    private void c(String checkInput) {
        assertTrue(Terminal.buffer.contentEquals(checkInput));
    }

    private void iE() {
        assertTrue(Terminal.buffer.contains("Error, "));
    }

    @Test
    public void bigInt() {
        c("");
        iE();
        e("add track (0,0) -> (2,0)");
        e("add switch (2,0) -> (6,1),(2,3)");
        e("add switch (2,0) -> (6,0),(2,3)");
        e("add track (6,0) -> (7,0)");
        e("add track (11,0) -> (11,2)");
        e("add track (7,0) -> (11,0)");
        e("add track (11,0) -> (11,2)");
        e("add track (11,2) -> (7,2)");
        e("delete track 3");
        e("add switch (7,2) -> (5,2),(7,5)");
        e("add track (0,5) -> (7,5)");
        e("add track (0,0) -> (0,5)");
        e("add track (2,3) -> (2,4)");
        e("add track (5,4) -> (2,4)");
        e("add track (5,2) -> (5,4)");
        e("delete track 3");
        e("add track (6,0) -> (7,0)");
        e("list tracks");
        e("create train-set c0D TSl 1 true false");
        e("create train-set c0D TSr 2 false true");
        e("create train-set C0D TSa 2 true true");
        e("create train-set c0D TSl 3 true true");
        e("create coach special 2 true true");
        e("create coach passenger 1 true true");
        e("create coach freight 1 false true");
        e("create engine electrical C0D TSa 2 false true");
        e("create engine electrical 3AAA Anton 2 false true");
        e("create engine steam 12AB Berta 1 true true");
        e("create engine diesel d0IE Clown666 2 true true");
        e("create engine electrical C0D NOtrainSet 2 false true");
        e("add train 1 c0D-TSl");
        e("add train 3 c0D-TSr");
        e("add train 1 c0D-TSr");
        e("delete train 1");
        e("add train 1 c0D-TSr");
        e("add train 1 C0D-TSa");
        e("add train 1 c0D-TSl");
        e("add train 1 W2");
        e("add train 2 C0D-TSa");
        e("add train 2 C0D-NOtrainSet");
        e("add train 3 C0D-NOtrainSet");
        e("add train 3 2");
        e("add train 3 W3");
        e("add train 3 W2");
        e("delete train 3");
        e("add train 4 W3");
        e("add train 3 W3");
        e("add train 4 C0D-NOtrainSet");
        e("add train 4 W2");
        e("delete train 2");
        e("add train 5 d0IE-Clown666");
        e("add train 2 d0IE-Clown666");
        e("add train 5 C0D-TSa");
        e("delete rolling stock d0IE-Clown666");
        e("delete rolling stock W1");
        e("create coach special 2 true true");
        e("list trains");
        e("put train 1 at (0,1) in direction 0,-1");
        e("set switch 7 position (5,2)");
        e("set switch 2 position (2,2)");
        e("set switch 2 position (2,3)");
        e("list tracks");
        e("put train 1 at (1,0) in direction -1,0");
        e("set switch 2 position (6,0)");

        e("put train 1 at (1,0) in direction -1,0");
        e("put train 3 at (0,5) in direction -1,0");
        e("add train 3 12AB-Berta");
        e("put train 3 at (3,5) in direction -1,0");
        e("put train 2 at (1,5) in direction 1,0");
        e("put train 2 at (0,5) in direction 1,0");
        e("put train 2 at (5,2) in direction 0,-1");
        Terminal.silent = false;
        e("list tracks");
        e("step 0");
        e("set switch 7 position (7,5)");
        e("put train 2 at (5,2) in direction 0,-1");
        e("put train 4 at (9,0) in direction 1,0");
        e("put train 5 at (10,2) in direction 1,0");
        e("delete train 4");
        e("step 4");
        e("exit");

    }

}
