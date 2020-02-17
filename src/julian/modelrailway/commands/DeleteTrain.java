/**
 * Erstellt einen neuen Command, der das Delete Train Pattern akzeptiert. 
 * Bei der Ausführung wird der Zug gelöscht.
 * @param model
 */
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.ModelRailWay;
import julian.modelrailway.Exceptions.LogicalException;

public class DeleteTrain extends Command {

private static final String REGEX = "delete train (\\d+)"; 
    
    /**
     * Erstellt einen neuen Befehl mit delete-Train-Pattern.
     * @param model Bezugsmodelleisenbahn
     */
    public DeleteTrain(ModelRailWay model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute ( String command) {
        try {
            Terminal.printLine(model.deleteTrain(Integer.parseInt(getMatcher(command).group(1))));;
        } catch (LogicalException e) {
            Terminal.printLine(e.getMessage());
        }
    }

}
