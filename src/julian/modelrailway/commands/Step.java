/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.ModelRailWay;
import julian.modelrailway.Exceptions.IllegalInputException;
import julian.modelrailway.Exceptions.LogicalException;

public class Step extends Command {

private static final String REGEX = "step (-?\\d+)"; //TODO Regex für Befehl
    
    public Step(ModelRailWay model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute ( String command) {
        try {
            int step;
            try {
                step = Integer.parseInt(getMatcher(command).group(1));
            } catch (NumberFormatException e1) {
                throw new IllegalInputException("input is not a valid number.");
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
