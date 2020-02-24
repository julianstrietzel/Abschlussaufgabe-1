
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.main.ModelRailWay;

/**
 * Der Befehl listet alle vorhanden Scheinen auf.
 * 
 * @author Julian Strietzel
 */
public class ListRollingStock extends Command {

    private static final String REGEX = "list (engines|coaches|train-sets)";

    /**
     * Erstellt einen neuen Command mit list tarcks -Pattern
     * 
     * @param model Bezugsmodelleisenbahn
     */
    public ListRollingStock(ModelRailWay model) {
        super(model, REGEX);
    }

    @Override
    public void execute(String command) {
        if ("coaches".contentEquals(getMatcher(command).group(1))) {
            Terminal.printLine(model.coachestoString());
        }
        if ("engines".contentEquals(getMatcher(command).group(1))) {
            Terminal.printLine(model.enginestoString());
        }
        if ("train-sets".contentEquals(getMatcher(command).group(1))) {
            Terminal.printLine(model.trainSettoString());
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }

}
