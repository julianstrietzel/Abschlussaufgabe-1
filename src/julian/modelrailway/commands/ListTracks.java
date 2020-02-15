/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.ModelRailWay;

public class ListTracks extends Command {

private static final String REGEX = "list tracks"; 
    
    public ListTracks(ModelRailWay model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute ( String command) {
        Terminal.printLine(model.listTracks());
    }
    
    @Override
    public boolean isExit() {
        return false;
    }

}