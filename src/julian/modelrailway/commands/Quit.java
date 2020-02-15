/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.commands;

import julian.modelrailway.ModelRailWay;;

public class Quit extends Command{

    private static final String REGEX = "(exit)";
    
    public Quit(ModelRailWay model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute ( String command) {
        
    }
    
    @Override
    public boolean isExit() {
        return true;
    }
}
