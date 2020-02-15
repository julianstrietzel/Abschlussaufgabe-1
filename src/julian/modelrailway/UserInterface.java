package julian.modelrailway;
/**
 * 

   * @author Julian Strietzel
 */


import java.util.LinkedList;

import julian.modelrailway.commands.*;
import julian.modelrailway.ModelRailWay;;

public class UserInterface {

    private final LinkedList <Command> commands = new LinkedList<Command>();
    private Command lastFoundCommand;
    private Command fail;
    
    public UserInterface(ModelRailWay model) {
        commands.add(new Quit(model));
        commands.add(new AddTrack(model));
 
        lastFoundCommand = new Fail(model);
        fail  = new Fail(model);
    }
    
    public void executeCommand(String command) {
        
            getCommand(command).execute(command);
        
    }
    
    private Command getCommand(String command) {
        for(Command found: commands) {
            if(found.matches(command)) {
                lastFoundCommand = found;
                return found;
            }
        }
        return fail;
    }
    
    public boolean isExit() {
        return lastFoundCommand.isExit();
    }
}
