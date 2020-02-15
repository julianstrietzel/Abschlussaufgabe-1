/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.commands;

import edu.kit.informatik.Terminal;
import julian.modelrailway.ModelRailWay;

public class Fail extends Command{
    
    public Fail(ModelRailWay model) {
        super(model, "");
    }
    
    @Override
    public void execute(String command) {
        Terminal.printError("command unknown.");
    }
}
