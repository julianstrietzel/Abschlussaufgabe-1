/**
 * Der Befehl listet alle vorhanden Scheinen auf.
 * @author Julian Strietzel
 */
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.ModelRailWay;

public class ListRollingStock extends Command {

private static final String REGEX = "list (engines|coaches|train-sets)"; 
    
    /**
     * Erstellt einen neuen Command mit list tarcks -Pattern
     * @param model Bezugsmodelleisenbahn
     */
    public ListRollingStock(ModelRailWay model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute ( String command) {
        if("coaches".contentEquals(getMatcher(command).group(1))) {
            Terminal.printLine(model.getRollStock().coachestoString());
        }
        if("engines".contentEquals(getMatcher(command).group(1))) {
            Terminal.printLine(model.getRollStock().enginestoString());
        }
        if("train-sets".contentEquals(getMatcher(command).group(1))) {
            Terminal.printLine(model.getRollStock().trainSettoString());
        }
    }
    
    @Override
    public boolean isExit() {
        return false;
    }

}
