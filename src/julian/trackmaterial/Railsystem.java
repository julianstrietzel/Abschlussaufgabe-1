/**
 * 
 * @author Julian Strietzel
 */
package julian.trackmaterial;

import java.util.LinkedList;


public class Railsystem {
    
    private LinkedList<Rail> rails;
    private LinkedList<Knode> knodes;
    private LinkedList<Switch> switches;
    
    public Railsystem() {
        rails = new LinkedList<Rail>();
        knodes = new LinkedList<Knode>();
        switches = new LinkedList<Switch>();
    }
    
    public void addSwitch(Vertex start, Vertex endOne, Vertex endTwo) throws IllegalInputException {
        Switch newSw = new Switch(start, endOne, endTwo);
        if(newSw.getMinLength() == 0) {
            throw new IllegalInputException("length needs to be not null.");
        }
        newSw.setSwitch(newSw.getDirection());
        Vertex checker = newSw.getStart();
        for(int i = 1; i < newSw.getSetLength(); i++) {
            checker.add(newSw.getDirection());
            if(knodes.contains(checker)) {
                throw new IllegalInputException("Switch cutting another Rail");
            }
        }
        newSw.setSwitch(newSw.getDirectionTwo());
        checker = newSw.getStart();
        for(int i = 1; i < newSw.getSetLength(); i++) {
            checker.add(newSw.getDirectionTwo());
            if(knodes.contains(checker)) {
                throw new IllegalInputException("Switch cutting another Rail");
            }
        }
        newSw.unSet();
        if(checkTrackCollision(newSw.getStart(), newSw.getEnd()) 
                || checkTrackCollision(newSw.getStart(), newSw.getEndTwo())) {
            throw new IllegalInputException("Switch cutting another Rail.");
        }
        if(!checkFreeKnodes(newSw.getKnodes())) {
            throw new IllegalInputException("knodes occupied.");
        }
        if(!rails.isEmpty()) {
            for(Knode knode: knodes) {
                if(knode.equals(newSw.getStart())) {
                    knode.setRailOut(newSw);
                    knode.getRailIn().connectEasy(newSw, newSw.getStart());
                }
                if(knode.equals(newSw.getEnd())) {
                    knode.setRailOut(newSw);
                    knode.getRailIn().connectEasy(newSw, newSw.getEnd());
                }
                if(knode.equals(newSw.getEndTwo())) {
                    knode.setRailOut(newSw);
                    knode.getRailIn().connectEasy(newSw, newSw.getEndTwo());
                }
            }
            if(!knodes.contains(newSw.start)) {
                knodes.add(new Knode(newSw.getStart(), newSw));
            }
            if(!knodes.contains(newSw.end)) {
                knodes.add(new Knode(newSw.getEnd(), newSw));
            }
            if(!knodes.contains(newSw.getEndTwo())) {
                knodes.add(new Knode(newSw.getEndTwo(), newSw));
            }
        } else {
            rails.add(newSw);
            knodes.add(new Knode(newSw.getStart(), newSw));
            knodes.add(new Knode(newSw.getEnd(), newSw));
            knodes.add(new Knode(newSw.getEndTwo(), newSw));   
        }
    }
    
    public void addTrack(Vertex start, Vertex end) throws IllegalInputException{
        Rail newRail = new Rail(start, end);
        if(newRail.getLength() == 0) {
            throw new IllegalInputException("length needs to be not null.");
        }
        Vertex checker = newRail.getStart();
        for(int i = 1; i < newRail.getLength(); i++) {
            checker.add(newRail.getDirection());
            if(knodes.contains(checker)) {
                throw new IllegalInputException("Rail cutting another Rail");
            }
            ;
        }
        if(checkTrackCollision(newRail.getStart(), newRail.getEnd())) {
            throw new IllegalInputException("Rail cutting another Rail");
        }
        if(!checkFreeKnodes(newRail.getKnodes())) {
            throw new IllegalInputException("knodes occupied.");
        }
        if(!rails.isEmpty()) {
            for(Knode knode: knodes) {
                if(knode.equals(newRail.getStart())) {
                    knode.setRailOut(newRail);
                    knode.getRailIn().connectEasy(newRail, newRail.getStart());
                }
                if(knode.equals(newRail.getEnd())) {
                    knode.setRailOut(newRail);
                    knode.getRailIn().connectEasy(newRail, newRail.getEnd());
                }
            }
            if(!knodes.contains(newRail.start)) {
                knodes.add(new Knode(newRail.getStart(), newRail));
            }
            if(!knodes.contains(newRail.end)) {
                knodes.add(new Knode(newRail.getEnd(), newRail));
            }
        } else {
            rails.add(newRail);
            knodes.add(new Knode(newRail.getStart(), newRail));
            knodes.add(new Knode(newRail.getEnd(), newRail));
        }
    }
    
    
    public boolean checkTrackCollision(Vertex start, Vertex end){
        for (Rail rail: rails) {
            Vertex checker = rail.getStart();
            for(int i = 1; i < rail.getLength(); i++) {
                checker.add(rail.getDirection());
                if(checker.equals(start) || checker.equals(end)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean checkFreeKnodes(LinkedList<Vertex> knodes)  {
        int freeKnodes = 0;
        for(Knode knode: this.knodes) {
            if(knodes.contains(knode) ) {
                freeKnodes++;
                if(!knode.isFree()) {
                    return false; 
                }
                
            }
        }
        if(freeKnodes == 0) {
            return false;
        }
        return true;
    }

}
