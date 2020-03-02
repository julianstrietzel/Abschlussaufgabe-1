package julian.modelrailway.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.kit.informatik.Terminal;
import julian.modelrailway.exceptions.IllegalInputException;
import julian.modelrailway.exceptions.LogicalException;
import julian.modelrailway.trackmaterial.DirectionalVertex;
import julian.modelrailway.trackmaterial.Vertex;

/**
 * In diesem Enum sind alle Befehle mit ihren regex Patterns gespeichert
 * 
 * @author Julian Strietzel
 * @version 01.03.2020
 */
public enum Commands {

    /**
     * Command, der die Züge um die angegebene Anzahl an Schritten fahren lässt
     * Danach erfolgt ein Statusbericht aller Züge.
     * 
     * Train at (x,y) oder Crash of train x
     * 
     * Error, wenn der Input nicht zu einem Integer verarbeitet werden kann.
     */
    STEP("step ([+-]?\\d+)") {
        @Override
        public void execute(final Matcher matcher, final ModelRailWay model)
                throws IllegalInputException, LogicalException {
            short step;
            try {
                step = Short.parseShort(matcher.group(1));
            } catch (NumberFormatException e1) {
                throw new IllegalInputException("input is not a short number.");
            }
            Terminal.printLine(model.move(step));
        }
    },

    /**
     * Command, der eine visuelle Repräsentation eines Zuges ausgibt
     * 
     * Error, wenn der Zug nicht existiert.
     */
    SHOW_TRAIN("show train (\\d+)") {
        @Override
        public void execute(final Matcher matcher, final ModelRailWay model) throws LogicalException {
            Terminal.printLine(model.showTrain(Integer.parseInt(matcher.group(1))));
        }
    },

    /**
     * Command, der eine bestimmte Weiche auf die angegebene Position setzt, wenn
     * diese einem der Enden entspricht.
     * 
     * Error, wenn die Gleisweiche nicht existiert oder der Input nicht zu Zahlen
     * verarbeitet werden kann. Oder wenn die ID nicht existiert
     */
    SET_SWITCH("set switch (\\d+) position \\(([-+]?\\d+),([-+]?\\d+)\\)") {
        @Override
        public void execute(final Matcher matcher, final ModelRailWay model)
                throws IllegalInputException, LogicalException {
            int id;
            int xcoord;
            int ycoord;
            try {
                id = Integer.parseInt(matcher.group(1));
                xcoord = Integer.parseInt(matcher.group(2));
                ycoord = Integer.parseInt(matcher.group(3));
            } catch (NumberFormatException e) {
                throw new IllegalInputException("input is not a valid number.");
            }
            model.setSwitch(id, new Vertex(xcoord, ycoord));
            Terminal.printLine("OK");
        }
    },

    /**
     * Command, der einen bestimmten Zug auf eine bestimmte Position in eine
     * bestimmte Richtung auf die Gleise setzt
     * 
     * Error, wenn der Input nicht zu einem Integer verarbeitet werden kann oder
     * kein passender Zug, keine passende freie Schiene gefunden werden kann.
     */
    PUT_TRAIN("put train (\\d+) at \\(([-+]?\\d+),([-+]?\\d+)\\) in direction ([+-]?\\d+),([+-]?\\d+)") {
        @Override
        public void execute(final Matcher matcher, final ModelRailWay model)
                throws IllegalInputException, LogicalException {
            int id;
            int px;
            int py;
            int dx;
            int dy;
            try {
                id = Integer.parseInt(matcher.group(1));
                px = Integer.parseInt(matcher.group(2));
                py = Integer.parseInt(matcher.group(3));
                dx = Integer.parseInt(matcher.group(4));
                dy = Integer.parseInt(matcher.group(5));

            } catch (NumberFormatException e1) {
                throw new IllegalInputException("input is not a valid number.");
            }
            Terminal.printLine(model.putTrain(id, new Vertex(px, py), new DirectionalVertex(dx, dy)));
        }
    },

    /**
     * Command, der eine Liste aller Züge im System ausgibt
     */
    LIST_TRAINS("list trains") {
        @Override
        public void execute(final Matcher matcher, final ModelRailWay model) {
            Terminal.printLine(model.listTrains());
        }
    },

    /**
     * Command, der eine Liste aller Schienen im Schienennetz ausgibt
     */
    LIST_TRACKS("list tracks") {
        @Override
        public void execute(final Matcher matcher, final ModelRailWay model) {
            Terminal.printLine(model.listTracks());
        }
    },

    /**
     * Command, der eine Liste allen Rollmaterials eines bestimmten Types (engines,
     * coaches oder train-sets) ausgibt
     */
    LIST_ROLLINGSTOCK("list (engines|coaches|train-sets)") {
        @Override
        public void execute(final Matcher matcher, final ModelRailWay model) {
            if ("coaches".contentEquals(matcher.group(1))) {
                Terminal.printLine(model.coachestoString());
            }
            if ("engines".contentEquals(matcher.group(1))) {
                Terminal.printLine(model.enginestoString());
            }
            if ("train-sets".contentEquals(matcher.group(1))) {
                Terminal.printLine(model.trainSettoString());
            }
        }
    },

    /**
     * Command, der einen bestimmten Zug vom Netz nimmt und aus dem System löscht.
     * 
     * Error, wenn der Zug nicht existiert oder der input nicht verarbeitet werden
     * kann.
     */
    DELETE_TRAIN("delete train (\\d+)") {
        @Override
        public void execute(final Matcher matcher, final ModelRailWay model)
                throws LogicalException, IllegalInputException {
            try {
                Terminal.printLine(model.deleteTrain(Integer.parseInt(matcher.group(1))));
            } catch (NumberFormatException e1) {
                throw new IllegalInputException("input needs to be an Integer.");
            }

        }
    },

    /**
     * Command, der eine bestimmt Schiene löscht.
     * 
     * Error, wenn der Input kein Integer ist, die Schiene nicht existiert oder
     * notwendig ist.
     */
    DELETE_TRACK("delete track (\\d+)") {
        @Override
        public void execute(final Matcher matcher, final ModelRailWay model)
                throws IllegalInputException, LogicalException {
            int id;
            try {
                id = Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e1) {
                throw new IllegalInputException("input needs to be an Integer.");
            }
            Terminal.printLine(model.deleteTrack(id));
        }
    },

    /**
     * Command, der ein bestimmtes Rollmaterial entfernt
     * 
     * Error, wenn Rollmaterial mit der ID nicht existiert.
     */
    DELETE_ROLLING_STOCK("delete rolling stock ((W?)(-|\\w)+)") {
        @Override
        public void execute(final Matcher matcher, final ModelRailWay model)
                throws IllegalInputException, LogicalException {
            Terminal.printLine(model.delete(!"W".contentEquals(matcher.group(2)), matcher.group(1)));

        }
    },

    /**
     * Command, der einen neuen Triebzug erstellt
     * 
     * create train-set (Baureihe) (Name) (Länge) (Kupplung vorne) (Kupplung hinten)
     * 
     * Error, wenn input nicht den Anforderungen entspricht, der Triebzug schon
     * existiert oder die Länge null besitzt
     */
    CREATE_TRAIN_SET("create train-set (\\w+) (\\w+) (\\d+) (true|false) (true|false)") {
        @Override
        public void execute(final Matcher matcher, final ModelRailWay model)
                throws LogicalException, IllegalInputException {
            try {
                Terminal.printLine(
                        model.createTrainSet(matcher.group(1), matcher.group(2), Integer.parseInt(matcher.group(3)),
                                "true".contentEquals(matcher.group(4)), "true".contentEquals(matcher.group(5))));
            } catch (NumberFormatException e) {
                throw new IllegalInputException("input needs to be a valid integer");
            }
        }
    },

    /**
     * Command, der ein neues motorisiertes rollmaterial erstellt create engine
     * (electrical|steam|diesel) (Baureihe) (Name) (Länge) (Kupplung vorne)
     * (Kupplung hinten)
     * 
     * Error, wenn input nicht den Anforderungen entspricht, das Material schon
     * existiert oder die Länge null besitzt
     */
    CREATE_ENGINE("create engine (electrical|steam|diesel) (\\w+) (\\w+) (\\d+) (true|false) (true|false)") {
        @Override
        public void execute(final Matcher matcher, final ModelRailWay model)
                throws LogicalException, IllegalInputException {
            try {
                Terminal.printLine(model.createEngine(matcher.group(1), matcher.group(2), matcher.group(3),
                        Integer.parseInt(matcher.group(4)), "true".contentEquals(matcher.group(5)),
                        "true".contentEquals(matcher.group(6))));
            } catch (NumberFormatException e) {
                throw new IllegalInputException("input needs to be a valid integer");
            }
        }
    },

    /**
     * Command, der einen neuen Waggon erstellt
     * 
     * create coach (passenger|freight|special) (Länge) (Kupplung vorne) (Kupplung
     * hinten)
     * 
     * Error, wenn input nicht den Anforderungen entspricht, das Material schon
     * existiert oder die Länge null besitzt
     */
    CREATE_COACH("create coach (passenger|freight|special) (\\d+) (true|false) (true|false)") {
        @Override
        public void execute(final Matcher matcher, final ModelRailWay model) throws IllegalInputException {
            try {
                Terminal.printLine(model.createCoach(matcher.group(1), Integer.parseInt(matcher.group(2)),
                        "true".contentEquals(matcher.group(3)), "true".contentEquals(matcher.group(4))));
            } catch (NumberFormatException e) {
                throw new IllegalInputException("input needs to be a valid integer");
            }
        }
    },

    /**
     * Command, der einem Zug ein existierendes Rollmaterial hinzufügt.
     * 
     * add train (train-id) (RollMat ID)
     * 
     * Error, wenn der Input nicht valide, das Material nicht mit dem Zug
     * funktioniert oder das RollMaterial nicht existiert.
     */
    ADD_TRAIN("add train (\\d+) ((W?)(-|\\w)+)") {
        @Override
        public void execute(final Matcher matcher, final ModelRailWay model)
                throws IllegalInputException, LogicalException {
            try {
                Terminal.printLine(model.addTrain(Integer.parseInt(matcher.group(1)), matcher.group(2),
                        !"W".contentEquals(matcher.group(3))));
            } catch (NumberFormatException e) {
                throw new IllegalInputException("input needs to contain valid integers");
            }
        }
    },

    /**
     * Command, der ein neues Gleis erstellt
     * 
     * add track (x,y) -> (x,y)
     * 
     * Error, wenn der Input oder die Schiene nicht den Anforderungen entspricht.
     * Bsp. länge = null, nicht nur horizontal bzw waagerecht, kreuzung anderer
     * Schienen
     */
    ADD_TRACK("add track \\(([-+]?\\d+),([-+]?\\d+)\\) -> \\(([-+]?\\d+),([-+]?\\d+)\\)") {
        @Override
        public void execute(final Matcher matcher, final ModelRailWay model) 
                throws IllegalInputException, LogicalException {
            
            int sx;
            int sy;
            int ex;
            int ey;
            try {
                sx = Integer.parseInt(matcher.group(1));
                sy = Integer.parseInt(matcher.group(2));
                ex = Integer.parseInt(matcher.group(3));
                ey = Integer.parseInt(matcher.group(4));
            } catch (NumberFormatException e) {
                throw new IllegalInputException("input needs to contain valid integers");
            }
            Terminal.printLine(model.addTrack(sx, sy, ex, ey));
        }
    },

    /**
     * Command, der eine neue Weiche erstellt
     * 
     * add switch (x,y) -> (x,y),(x,y)
     * 
     * Error, wenn die Weiche nicht den Anforderungen entspricht. Bsp. länge = null,
     * nicht nur horizontal bzw waagerecht, kreuzung anderer Schienen oder keinen
     * Anschluss an bestehendes Schienennetz
     */
    ADD_SWITCH("add switch \\(([-+]?\\d+),([-+]?\\d+)\\) -> \\(([-+]?\\d+),([-+]?\\d+)\\),"
            + "\\(([-+]?\\d+),([-+]?\\d+)\\)") {
        @Override
        public void execute(final Matcher matcher, final ModelRailWay model)
                throws IllegalInputException, LogicalException {

            int sx;
            int sy;
            int ex;
            int ey;
            int e2x;
            int e2y;
            try {
                sx = Integer.parseInt(matcher.group(1));
                sy = Integer.parseInt(matcher.group(2));
                ex = Integer.parseInt(matcher.group(3));
                ey = Integer.parseInt(matcher.group(4));
                e2x = Integer.parseInt(matcher.group(5));
                e2y = Integer.parseInt(matcher.group(6));
            } catch (NumberFormatException e1) {
                throw new IllegalInputException("input needs to be an Integer.");
            }
            Terminal.printLine(model.addSwitch(sx, sy, ex, ey, e2x, e2y));

        }
    },

    /**
     * Command, der nichts tut. Gibt als einziger isExit == true zurück und beendet
     * dadurch das Programm
     */
    EXIT("(exit)") {
        @Override
        public void execute(final Matcher matcher, final ModelRailWay model) {
        }

        @Override
        public boolean isExit() {
            return true;
        }
    },

    /**
     * Gibt als Fehlermeldung, dass kein Command gefunden wurde
     */
    UNKNOWN("command unknown") {
        @Override
        public void execute(final Matcher matcher, final ModelRailWay model) throws IllegalInputException {
            throw new IllegalInputException("command unknown");
        }
    };

    private final Pattern pattern;

    private Commands(final String commandRegex) {
        this.pattern = Pattern.compile(commandRegex);
    }

    /**
     * Sucht im den passenden Command raus und fphrut diesen aus.
     * 
     * @param input Nutzereingabe, die verarbeitet werdne soll
     * @param model Modelleisenbahn, auf der gearbeitet werden soll
     * @return Command , der ausgeführt wurde
     */
    public static Commands execute(final String input, final ModelRailWay model) {
        for (Commands cs : Commands.values()) {
            Matcher matcher = cs.pattern.matcher(input);
            if (matcher.matches()) {
                try {
                    cs.execute(matcher, model);
                } catch (IllegalInputException | LogicalException e) {
                    Terminal.printError(e.getMessage());
                }
                return cs;
            }

        }
        try {
            Commands.UNKNOWN.execute(Commands.UNKNOWN.pattern.matcher(input), model);
        } catch (IllegalInputException | LogicalException e) {
            Terminal.printError(e.getMessage());
        }
        return Commands.UNKNOWN;

    }

    /**
     * Gibt vor, was der bestimmte Command machen soll
     * 
     * @param matcher , der das command pattern verarbeitet
     * @param model   , Modelleisenbahn auf der gearbeitet wird
     * @throws IllegalInputException wenn der Input nicht den Anforderungen
     *                               entspricht
     * @throws LogicalException      wenn es in der Logik des Systems einen Fehler
     *                               gibt                            
     */
    public abstract void execute(Matcher matcher, ModelRailWay model)
            throws IllegalInputException, LogicalException;

    /**
     * 
     * @return ob der bestimmte Befehl der Exit-Befehl war
     */
    public boolean isExit() {
        return false;
    }
}
