
package julian.modelrailway.trackmaterial;

import java.util.LinkedList;
import java.util.List;

/**
 * Utility-Klasse für Operationen auf einer Liste ist gerechtfertigt, da mehrere
 * voneinander unabhängige Klassen die gleiche Funktionen brauchen und diese
 * nicht an ein bestimmtes Objekt gebunden ist.
 * 
 * @author Julian Strietzel
 * @version 1.0
 */
public abstract class ListUtility {

    /**
     * Löscht Duplikate aus einer Liste, ohne diese zu verändern
     * 
     * @param <T>  Typparameter für die Objekte
     * @param list zu bearbeitende Liste
     * @return neue Kopie der Liste ohne Duplikate
     */
    public static <T> List<T> copyWithoutDuplicates(List<T> list) {
        List<T> workingList = new LinkedList<T>();
        for (T ts : list) {
            if (!workingList.contains(ts)) {
                workingList.add(ts);
            }
        }
        return workingList;
    }
}
