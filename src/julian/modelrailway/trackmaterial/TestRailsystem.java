/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.trackmaterial;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import julian.modelrailway.Exceptions.IllegalInputException;
import julian.modelrailway.Exceptions.LogicalException;

public class TestRailsystem {
static Railsystem model;
    @Before
    public void createSystem() {
        model = new Railsystem();
    }
    
    @Test
    public void testAddingTrack() throws IllegalInputException, LogicalException {
        Knode one = new Knode(new Vertex(0,0), new Rail(new Vertex(0,0), new Vertex(10,0), 1));
        Vertex two = new Vertex(0,0);
        assertTrue(one.equals(two));
        assertTrue(two.equals(one));
        
        LinkedList<Vertex> list = new LinkedList<Vertex>();
        list.add(two);
        assertTrue(list.getFirst().equals(new Vertex(0,0)));
        assertTrue(ListUtility.contains(list, one ));
        assertTrue(ListUtility.contains(list, two));
        model.addRail(new Vertex(10,10), new Vertex(0,10));
        assertTrue(model.getKnodes().getFirst().isFree());
        model.addRail(new Vertex(10,10), new Vertex(20,10));
        model.addRail(new Vertex(20,10), new Vertex(20,-1));
        model.addRail(new Vertex(0,-1), new Vertex(20,-1));
        model.addRail(new Vertex(0,0), new Vertex(0,-1));
        model.addRail(new Vertex(0,10), new Vertex(0,0));
//        System.out.println(model.toString());
//        System.out.println(model.knodes.toString());
    }
    
    @Test
    public void testAddingSwitches() throws IllegalInputException, LogicalException {
        assertTrue(model.rails.size() == 0);
        Knode knodene = new Knode(new Vertex(0,0), new Rail(new Vertex(0,0), new Vertex(10,0), 1));
        Vertex two = new Vertex(0,0);
        Vertex one = new Vertex(0,1);
        Vertex three = new Vertex(0,2);
        Switch test = new Switch(one, two, three, 1);
        assertTrue(test.getKnodes().size() == 3);
//        System.out.println(test.getKnodes().toString());
        assertTrue(ListUtility.contains(test.getKnodes(), knodene));
        assertTrue(model.rails.isEmpty());
        model.addSwitch(new Vertex(0,0), new Vertex(0,10), new Vertex(10,0));
        assertTrue(model.rails.size() == 1);
//        System.out.println(model.toString());
    }
    
    @Test 
    public void testWayWithout() throws Exception {
        model.addRail(new Vertex(10,10), new Vertex(0,10));
        model.addRail(new Vertex(10,10), new Vertex(20,10));
        model.addRail(new Vertex(20,10), new Vertex(20,-1));
        model.addRail(new Vertex(0,-1), new Vertex(20,-1));
        model.addSwitch(new Vertex(0,0), new Vertex(0,-1), new Vertex(1,0));
        model.addRail(new Vertex(0,10), new Vertex(0,0));
        int number = model.getRails().size();
        model.getRails().remove(null);
        assertTrue(number == model.getRails().size());
        LinkedList<Rail> notUse = new LinkedList<Rail>();
        notUse.add(model.rails.getFirst());
        Rail from = model.rails.getFirst().getNext();
        Rail to = model.rails.getFirst().getPrevious();
        assertTrue(model.thereIsAWayWithout(notUse, from, to, model.rails.getFirst()));
        model.deleteTrack(1);
        try{
            model.deleteTrack(1);
        } catch(IllegalInputException ex) {
            assertTrue(ex.getMessage().contentEquals("track not existing"));
        }
        model = new Railsystem();
        model.addRail(new Vertex(10,10), new Vertex(0,10));
        model.deleteTrack(model.rails.getLast().getId());
        assertTrue(model.rails.isEmpty());
        
    }

}
