
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.exceptions.IllegalInputException;
import julian.modelrailway.exceptions.LogicalException;
import julian.modelrailway.main.ModelRailWay;

/**
 * Ein Befehl, der ein bestimmtes RollMaterial löscht
 * 
 * @author Julian Strietzel
 * @version 1.0
 * 
 */
public class DeleteRollingStock extends Command {

    private static final String REGEX = "delete rolling stock (W?)((-|\\w)+)";

    /**
     * Erstellt einen neuen Befehl mit delete-Stock-Pattern.
     * 
     * @param model Bezugsmodelleisenbahn
     */
    public DeleteRollingStock(ModelRailWay model) {
        super(model, REGEX);
    }

    @Override
    public void execute(String command) {
        try {
            Terminal.printLine(
                    model.delete(!"W".contentEquals(getMatcher(command).group(1)), getMatcher(command).group(2)));
        } catch (IllegalInputException | LogicalException e) {
            Terminal.printError(e.getMessage());
        }
    }

}
