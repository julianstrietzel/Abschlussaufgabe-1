/**
 * Der Befehl visualisiert einen bestimmten Zug.
 * @author Julian Strietzel
 */
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.ModelRailWay;
import julian.modelrailway.Exceptions.LogicalException;

public class ShowTrain extends Command {

private static final String REGEX = "show train (\\d+)"; 
    
    /**
     * Erstellt einen neuen Command mit dem show Train pattern.
     * @param model Bezugsmodelleisenbahn
     */
    public ShowTrain(ModelRailWay model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute ( String command) {
        try {
            Terminal.printLine(model.showTrain(Integer.parseInt(getMatcher(command).group(1))));
        } catch (LogicalException e) {
            Terminal.printError(e.getMessage());
        }
    }

}
