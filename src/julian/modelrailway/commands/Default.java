/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.commands;

import julian.modelrailway.ModelRailWay;

public class Default extends Command {

private static final String REGEX = "DEFAULT"; //TODO Regex für Befehl
    
    public Default(ModelRailWay model) {
        super(model, REGEX);
    }
    
    @Override
    public void execute ( String command) {
        //TODO what to do
    }
    
    @Override
    public boolean isExit() {
        return false;
    }

}
