/**
 * Erstellt einen neuen Command, der das Delete Stock Pattern akzeptiert. 
 * Bei der Ausführung wird der entsprechende Wagon gelöscht.
 * @param model
 */
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.ModelRailWay;
import julian.modelrailway.Exceptions.IllegalInputException;
import julian.modelrailway.Exceptions.LogicalException;

public class DeleteRollingStock extends Command {

private static final String REGEX = "delete rolling stock (W?)((-|\\w)+)"; 
    
    /**
     * Erstellt einen neuen Befehl mit delete-Stock-Pattern.
     * @param model Bezugsmodelleisenbahn
     */
    public DeleteRollingStock(ModelRailWay model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute ( String command) {
        try {
            model.getRollStock().delete("W".contentEquals(getMatcher(command).group(1)), getMatcher(command).group(3));
        } catch (IllegalInputException | LogicalException e) {
            Terminal.printLine(e.getMessage());
        }
    }

}
