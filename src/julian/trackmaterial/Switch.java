/**
 * 
 * @author Julian Strietzel
 */
package julian.trackmaterial;

import java.util.LinkedList;

public class Switch extends Rail{
    private Rail nextTwo;
    private final Vertex endTwo;
    private boolean set;
    private boolean setDirOne;
    private DirectionalVertex setDirection;
    private DirectionalVertex directionTwo;
    
    public Switch(Vertex start, Vertex end, Vertex endTwo) throws IllegalInputException {
        super(start, end);
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
    
    public int getSetLength() {
        if(!set) {
            return getMinLength();
        }
        if(setDirOne) {
            return getLength();
        }
        return Math.max(start.getXcoord() - endTwo.getXcoord(), start.getYcoord() 
                - endTwo.getYcoord());
    }
    
    public Rail getNextTwo() {
        return nextTwo;
    }

    public void setNextTwo(Rail nextTwo) {
        this.nextTwo = nextTwo;
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
        return Math.min(getLength(), Math.max(start.getXcoord() - endTwo.getXcoord(), start.getYcoord() 
                - endTwo.getYcoord()));
    }
    
    
    public void setSwitch(DirectionalVertex newDire) {
        if(newDire.equals(direction)) {
            this.setDirection = direction;
            set = true;
            setDirOne = true;
            return;
        }
        if(newDire.equals(directionTwo)) {
            this.setDirection = directionTwo;
            set = true;
            setDirOne = false;
            return;
        }
//        if(occupied) {
//            currentTrain.derail();
//        }
        return;
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
    

}
