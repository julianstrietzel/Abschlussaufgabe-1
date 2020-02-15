/**
 * Erstellt einen neuen Command, der das Delete Pattern akzeptiert. 
 * Bei der Ausführung wird die entsprechende Schiene gelöscht.
 * @param model
 */
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.ModelRailWay;
import julian.modelrailway.Exceptions.IllegalInputException;
import julian.modelrailway.Exceptions.LogicalException;

public class Delete extends Command {

private static final String REGEX = "delete track (\\d+)"; //TODO Regex für Befehl
    
    /**
     * Erstellt einen neuen Befehl mit delete-Pattern.
     * @param model Bezugsmodelleisenbahn
     */
    public Delete(ModelRailWay model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute ( String command) {
        try {
            int id;
            try {
                id = Integer.parseInt(getMatcher(command).group(1));
            } catch (NumberFormatException e1) {
                throw new IllegalInputException("input needs to be an Integer.");
            }
            model.delete(id);
            Terminal.printLine("OK");
        } catch (IllegalInputException | LogicalException e) {
            Terminal.printError(e.getMessage());
        }
    }

}
