
package julian.modelrailway.events;

import java.util.List;

import julian.modelrailway.rollingmaterial.SetTrain;
import julian.modelrailway.trackmaterial.ListUtility;

/**
 * Event, dass alle bteiligten Züge eines Crashes speichert.
 * 
 * @author Julian Strietzel
 */
public class Crash extends Event implements Comparable<Event> {

    /**
     * Erstellt einen neuen Crash mit dem gesetzten Zug invol.
     * 
     * @param invol beteiligter Zug
     */
    public Crash(SetTrain invol) {
        super(invol);
    }

    /**
     * Erstellt einen neuen Crash mit allen Zügen aus invol.
     * 
     * @param invol alle involvierten Züge
     */
    public Crash(List<SetTrain> invol) {
        super(invol);
        getInvolved().sort(null);
    }

    @Override
    public String getMessage() {
        String output = "Crash of train ";
        setInvolved(ListUtility.copyWithoutDuplicates(getInvolved()));
        for (SetTrain tr : getInvolved()) {
            output += tr.getId() + ",";
        }
        output = (String) output.subSequence(0, output.length() - 1);
        return output;
    }

}
