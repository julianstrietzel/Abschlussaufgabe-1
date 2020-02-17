/**
 * Der Befehl listet alle vorhanden Züge auf.
 * @author Julian Strietzel
 */
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.ModelRailWay;

public class ListTrains extends Command {

private static final String REGEX = "list trains"; 
    
    /**
     * Erstellt einen neuen Command mit list trains -Pattern
     * @param model Bezugsmodelleisenbahn
     */
    public ListTrains(ModelRailWay model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute ( String command) {
        Terminal.printLine(model.listTrains());
    }

}
