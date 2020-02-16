package julian.modelrailway;

/**
 * 

   * @author Julian Strietzel
 */

import java.util.LinkedList;

import julian.modelrailway.commands.*;
import julian.modelrailway.ModelRailWay;;

public class UserInterface {

    private final LinkedList<Command> commands = new LinkedList<Command>();
    private Command lastFoundCommand;
    private Command fail;

    public UserInterface(ModelRailWay model) {
        commands.add(new Exit(model));
        commands.add(new AddTrack(model));
        commands.add(new Delete(model));
        commands.add(new AddSwitch(model));
        commands.add(new ListTracks(model));
        commands.add(new Step(model));
        lastFoundCommand = new Fail(model);
        fail = new Fail(model);
    }

    public void executeCommand(String command) {
        getCommand(command).execute(command);
    }

    private Command getCommand(String command) {
        for (Command found : commands) {
            if (found.matches(command)) {
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
