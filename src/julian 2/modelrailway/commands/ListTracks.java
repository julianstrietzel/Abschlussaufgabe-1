
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.main.ModelRailWay;

/**
 * Der Befehl listet alle vorhanden Scheinen auf.
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public class ListTracks extends Command {

    private static final String REGEX = "list tracks";

    /**
     * Erstellt einen neuen Command mit list tarcks -Pattern
     * 
     * @param model Bezugsmodelleisenbahn
     */
    public ListTracks(ModelRailWay model) {
        super(model, REGEX);
    }

    @Override
    public void execute(String command) {

        Terminal.printLine(model.listTracks());
    }

}
