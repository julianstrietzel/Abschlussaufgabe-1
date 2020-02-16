/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.Tests;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import julian.modelrailway.Exceptions.IllegalInputException;
import julian.modelrailway.Exceptions.LogicalException;
import julian.modelrailway.trackmaterial.Knode;
import julian.modelrailway.trackmaterial.ListUtility;
import julian.modelrailway.trackmaterial.Rail;
import julian.modelrailway.trackmaterial.Railsystem;
import julian.modelrailway.trackmaterial.Switch;
import julian.modelrailway.trackmaterial.Vertex;

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
    }
    
    @Test
    public void testAddingSwitches() throws IllegalInputException, LogicalException {
        assertTrue(model.getRails().size() == 0);
        Knode knodene = new Knode(new Vertex(0,0), new Rail(new Vertex(0,0), new Vertex(10,0), 1));
        Vertex two = new Vertex(0,0);
        Vertex one = new Vertex(0,1);
        Vertex three = new Vertex(0,2);
        Switch test = new Switch(one, two, three, 1);
        assertTrue(test.getKnodes().size() == 3);
//        System.out.println(test.getKnodes().toString());
        assertTrue(ListUtility.contains(test.getKnodes(), knodene));
        assertTrue(model.getRails().isEmpty());
        model.addSwitch(new Vertex(0,0), new Vertex(0,10), new Vertex(10,0));
        assertTrue(model.getRails().size() == 1);
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
        notUse.add(model.getRails().getFirst());
        Rail from = model.getRails().getFirst().getNext();
        Rail to = model.getRails().getFirst().getPrevious();
        assertTrue(model.thereIsAWayWithout(notUse, from, to, model.getRails().getFirst()));
        model.deleteTrack(1);
        model.deleteTrack(1);
        model = new Railsystem();
        model.addRail(new Vertex(10,10), new Vertex(0,10));
        model.deleteTrack(model.getRails().getLast().getId());
        assertTrue(model.getRails().isEmpty());
        
    }

}
