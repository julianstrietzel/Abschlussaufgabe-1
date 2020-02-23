/**
 * Befehl, der die Züge fahren lässt
 * @author Julian Strietzel
 */
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.ModelRailWay;
import julian.modelrailway.Exceptions.IllegalInputException;
import julian.modelrailway.Exceptions.LogicalException;

public class Step extends Command {

private static final String REGEX = "step ([+-]?\\d+)"; 
    
    /**
     * Erstellt einen neuen Befehl mit move Pattern
     * @param model Bezugsmodelleisenbahn
     */
    public Step(ModelRailWay model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute ( String command) {
        try {
            short step;
            try {
                step = Short.parseShort(getMatcher(command).group(1));
            } catch (NumberFormatException e1) {
                throw new IllegalInputException("input is not a short number.");
            }
            Terminal.printLine(model.move(step));
        }   catch (LogicalException ea) {
            Terminal.printError(ea.getMessage());
        } catch (IllegalInputException e) {
            Terminal.printError(e.getMessage());
        }
    }
    
    @Override
    public boolean isExit() {
        return false;
    }

}
