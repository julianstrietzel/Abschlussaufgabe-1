/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import  julian.modelrailway.ModelRailWay;

public abstract class Command {

    protected String regex = "";
    private final Pattern commandPattern;
    protected final ModelRailWay model;

    protected Command(ModelRailWay model, String regex) {
        this.regex = regex;
        this.model = model;
        commandPattern = Pattern.compile(regex);
    }

    public abstract void execute(String command);

    public boolean matches(String command) {
        return getMatcher(command).matches();
    }

    protected Matcher getMatcher(String command) {
        Matcher matcher = commandPattern.matcher(command);
        matcher.matches();
        return matcher;
    }

    public boolean isExit() {
        return false;
    }
}

