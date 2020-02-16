/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.trackmaterial;

import java.util.LinkedList;

import julian.modelrailway.rollingmaterial.Coach;
import julian.modelrailway.rollingmaterial.PoweredRolling;


public abstract class ListUtility {

    public static <T> boolean contains(LinkedList<T> list, T object) {
        for (T obj : list) {
            if (object.equals(obj)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean contains(LinkedList<Rail> list, Rail object) {
        for (Rail obj : list) {
            if (object.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean contains(LinkedList<Knode> list, Vertex object) {
        for (Knode obj : list) {
            if (obj.equals(object)) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean contains(LinkedList<Vertex> list, Knode object) {
        for (Vertex obj : list) {
            if (obj.equals(object)) {
                return true;
            }
        }
        return false;
    }
    
    public static PoweredRolling exists(Iterable<PoweredRolling> list, String id) {
        for(PoweredRolling p: list) {
            if(id.contentEquals(p.getID())) {
                return p;
            }
        }
        return null;
    }
    
    public static Coach exists(Iterable<Coach> list, int id) {
        for(Coach c: list) {
            if(id == c.getID()) {
                return c;
            }
        }
        return null;
    }

    public static <T> LinkedList<T> deleteDuplicates(LinkedList<T> list) {
        LinkedList<T> workingList = new LinkedList<T>();
        for(T ts: list) {
            if(!workingList.contains(ts)) {
                workingList.add(ts);
            }
        }
        return workingList;
        
    }

}
