/**
 * 
 * @author Julian Strietzel
 */
package julian.modelrailway.trackmaterial;

import java.util.LinkedList;

import julian.modelrailway.Exceptions.IllegalInputException;

public class Switch extends Rail{
    private Rail nextTwo;
    private final Vertex endTwo;
    private boolean set;
    private boolean setDirOne;
    private DirectionalVertex setDirection;
    private DirectionalVertex directionTwo;
    
    public Switch(Vertex start, Vertex end, Vertex endTwo, int id) throws IllegalInputException {
        super(start, end, id);
        this.endTwo = endTwo;
        this.set = false;
        if(!(start.getXcoord() - endTwo.getXcoord() == 0 ^ start.getYcoord() - endTwo.getYcoord() == 0)) {
            throw new IllegalInputException("wrong start and end.");
        }
        this.directionTwo = start.normedDirection(endTwo);
        if(directionTwo.equalsDirection(direction)) {
            throw new IllegalInputException("not a correct Switch.");
        }
    }
    
    public Rail getNextInDirection(DirectionalVertex direction) throws IllegalInputException{
        if(!set) {
            throw new IllegalInputException("switch not set yet.");
        }
        if(!this.setDirection.compatibleDirection(direction)) {
            throw new IllegalInputException("wrong directional Input");
        }
        if(setDirection.equals(directionTwo)) {
            if(this.setDirection.equalsDirection(direction)) {
                return nextTwo;
            } else { 
                return previous;
            }
        } else {
            if(this.setDirection.equalsDirection(direction)) {
                return next;
            } else { 
                return previous;
            }
        }
        
    }
    
    public int getLengthTwo() {
        return Math.max(start.getXcoord() - endTwo.getXcoord(), start.getYcoord() 
                - endTwo.getYcoord());
    }
    
    public int getSetLength() {
        if(!set) {
            return getMinLength();
        }
        if(setDirOne) {
            return getLength();
        }
        return getLengthTwo();
    }
    
    public Rail getNextTwo() {
        return nextTwo;
    }

    public void setNextTwo(Rail nextTwo) {
        this.nextTwo = nextTwo;
    }

    public boolean isSetCorrectly(Vertex posiVertex) {
        if(posiVertex.equals(endTwo) && setDirOne || posiVertex.equals(end) && !setDirOne) {
            return false;
        }
        return set;
    }
    
    public boolean isSet() {
        return set;
    }

    public void setSet(boolean set) {
        this.set = set;
    }

    public boolean isSetDirOne() {
        return setDirOne;
    }

    public void setSetDirOne(boolean setDirOne) {
        this.setDirOne = setDirOne;
    }

    public DirectionalVertex getSetDirection() {
        return setDirection;
    }

    public void setSetDirection(DirectionalVertex setDirection) {
        this.setDirection = setDirection;
    }

    public DirectionalVertex getDirectionTwo() {
        return directionTwo;
    }

    public void setDirectionTwo(DirectionalVertex directionTwo) {
        this.directionTwo = directionTwo;
    }

    public Vertex getEndTwo() {
        return endTwo;
    }

    public int getMinLength() {
        return Math.min(getLength(), Math.max(Math.abs(start.getXcoord() - endTwo.getXcoord()), Math.abs(start.getYcoord() 
                - endTwo.getYcoord())));
    }
    
    
    public void setSwitch(Vertex point) throws IllegalInputException {
        if(end.equals(point)) {
            this.setDirection = direction;
            set = true;
            setDirOne = true;
            return;
        }
        if(endTwo.equals(point)) {
            this.setDirection = directionTwo;
            set = true;
            setDirOne = false;
            return;
        }
        throw new IllegalInputException("point is not part of switch.");
    }
    
    public void unSet() {
        set = false;
        setDirOne = true;
    }
    
    public void connectEasy(Rail newRail, Vertex point) {
        super.connectEasy(newRail, point);
        if(point.equals(this.endTwo)) {
            this.nextTwo = newRail;
        }
        
    }

    
    public boolean connectsFreeTo(Vertex point) {
        return super.connectsFreeTo(point) || (point.equals(endTwo) && this.nextTwo == null );
    }
    
    public Knode createKnode(boolean start) {
        if(start) {
            return new Knode(this.start.getXcoord(), this.start.getYcoord(), this);
        }
        return new Knode(this.end.getXcoord(), this.end.getYcoord(), this);
    }
    
    public LinkedList<Vertex> getKnodes() {
        LinkedList<Vertex> list = super.getKnodes();
        list.add(endTwo);
        return list;
    }
    
    public LinkedList<Rail> getConnected(Rail notThisOne) {
        LinkedList<Rail> list = new LinkedList<Rail>();
        list.add(next);
        list.add(previous);
        list.add(nextTwo);
        list.remove(notThisOne);
        return list;
    }
    
    public DirectionalVertex getDirectionFrom(Vertex point) {
        if(point.equals(this.start)) {
            return this.setDirection;
        } else {
            if(point.equals(endTwo)) {
                return this.directionTwo.getInverseDirection();
            } else {
                return this.direction.getInverseDirection();
            }
        }
    }
    
    public Vertex getEndInDirection(DirectionalVertex direction) throws NullPointerException{
        if(direction.equals(this.setDirection)) {
            if(setDirOne) {
                return end;
            }
            return endTwo;
        } 
        if(direction.equals(this.setDirection.getInverseDirection())){
            return start;
        }
        return null;
    }
    
    @Override
    public String toString() {
        return "t " + id + " " + start.toString() + " -> " + end.toString() + "," 
                    + endTwo.toString();
    }
    
    @Override
    public boolean equals(Rail r) {
        try {
        if(r instanceof Switch) {
            Switch re = (Switch) r;
            if(new Rail(re.start, re.end,0).equals(new Rail(this.start, this.end, 0)) 
                    || new Rail(re.start, re.end,0).equals(new Rail(this.start, this.endTwo, 0))) {
                return true;
            }
            if(new Rail(re.start, re.endTwo,0).equals(new Rail(this.start, this.end, 0)) 
                    || new Rail(re.start, re.endTwo,0).equals(new Rail(this.start, this.endTwo, 0))) {
                return true;
            }
        } else {
            
                if(r.equals(new Rail(start, end, 0)) || r.equals(new Rail(start, endTwo, 0))) {
                    return true;
                }
            
        }
        
        } catch (IllegalInputException e) {
        }
        return false;
    }
    

    

}
