package COMP2021_Project;

import java.util.Stack;
import java.util.HashMap;

/**
 * All operations that can redo and undo
 * INCLUDE CREATE  GROUP UNGROUP DELETE MOVE
 */
public abstract class OPERATION {

    /**
     * name of corresponding shape
     */
    protected String NAME;
    /**
     * name -> shape
     */
    protected static final HashMap<String, SHAPE> MAP = new HashMap<>();
    /**
     * past operation
     */
    protected static final Stack<OPERATION> pastOperation = new Stack<>();
    /**
     * undo operation
     */
    protected static final Stack<OPERATION> undoOperation = new Stack<>();

    /**
     * @param s name
     * @return shape
     */
    public static SHAPE get(String s) {
        return MAP.get(s);
    }

    /**
     * global redo
     */
    static void REDO() {
        OPERATION it = undoOperation.pop();
        it.redo();
        pastOperation.push(it);
    }

    /**
     * global undo
     */
    static void UNDO() {
        OPERATION it = pastOperation.pop();
        it.undo();
        undoOperation.push(it);
    }

    /**
     * redo this operation
     * abstacted method
     */
    protected abstract void redo();

    /**
     * undo this operation
     * abstracted method
     */
    protected abstract void undo();
}

/**
 * create a non-group shape
 */
class CREATE extends OPERATION {
    private final int Z;

    /**
     * @param type type to create: rectangle circle line square
     * @param name name of shape
     * @param arg related parameters
     */
    CREATE(String type,String name,double[] arg){
        undoOperation.clear();
        this.NAME=name;
        this.Z=SHAPE.MAXZplus();
        SHAPE.AllShapes[Z] = switch (type) {
            case "rectangle" -> new Rectangle(name, arg[0], arg[1], arg[2], arg[3], Z);
            case "circle" -> new Circle(name, arg[0], arg[1], arg[2], Z);
            case "line" -> new Line(name, arg[0], arg[1], arg[2], arg[3], Z);
            case "square" -> new Square(name, arg[0], arg[1], arg[2], Z);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
        SHAPE it=SHAPE.AllShapes[Z];
        MAP.put(name,it);
        pastOperation.push(this);
    }

    @Override
    protected void redo() {
        SHAPE shape = SHAPE.AllShapes[Z];
        MAP.put(NAME,shape);
        shape.setEXIST(true);
    }

    @Override
    protected void undo() {
        SHAPE shape = MAP.get(NAME);
        shape.setEXIST(false);
        MAP.remove(NAME);
    }
}

/**
 * create a group
 */
class _GROUP extends OPERATION {
    private final int Z;

    /**
     * @param name    name of group
     * @param members member of group
     */
    _GROUP(String name,String[] members){
        undoOperation.clear();
        this.NAME=name;
        Z=SHAPE.MAXZplus();
        SHAPE.AllShapes[Z]= new Group(name,Z);
        Group it=(Group) SHAPE.AllShapes[Z];
        MAP.put(name,it);
        for (String member : members) {
            SHAPE shape = MAP.get(member);
            it.add(shape);
        }
        it.updateBox();
        pastOperation.push(this);
    }

    @Override
    protected void redo() {
        Group group=(Group)SHAPE.AllShapes[Z];
        group.setEXIST(true);
        for (SHAPE member : group.getMembers()) {
            member.setFather(group);
        }
        MAP.put(NAME, group);
    }

    @Override
    protected void undo() {
        Group group=(Group)SHAPE.AllShapes[Z];
        group.setEXIST(false);

        for (SHAPE member : group.getMembers()) {
            member.setFather(null);
        }
        MAP.remove(NAME);
    }
}

/**
 * ungroup a group
 */
class UNGROUP extends OPERATION {
    private final int Z;
    /**
     * @param name name of group to be ungrouped
     */
    UNGROUP(String name) {
        undoOperation.clear();
        this.NAME = name;
        Group it = (Group) MAP.get(name);
        MAP.remove(name);
        this.Z=it.Z();
        it.setEXIST(false);
        Group Father=(Group)it.findFather();
        if(Father!=null)Father.getMembers().remove(it);
        for (SHAPE member : it.getMembers()) {
            member.setFather(null);
            if(Father!=null)Father.add(member);
        }
        pastOperation.push(this);
    }

    @Override
    protected void redo() {
        Group group=(Group)SHAPE.AllShapes[Z];
        group.setEXIST(false);
        MAP.remove(NAME);
        Group Father=(Group)group.findFather();
        if(Father!=null)Father.getMembers().remove(group);
        for (SHAPE member : group.getMembers()) {
            member.setFather(null);
            if(Father!=null)Father.add(member);
        }
    }

    @Override
    protected void undo() {
        Group group=(Group)SHAPE.AllShapes[Z];
        group.setEXIST(true);
        MAP.put(NAME,group);
        Group Father=(Group)group.findFather();
        for (SHAPE member : group.getMembers()) {
            member.setFather(group);
            if(Father!=null)Father.getMembers().remove(member);
        }
        group.setFather(null);
        if(Father!=null)Father.add(group);
    }
}

/**
 * delete a shape
 */
class DELETE extends OPERATION {
    private final int Z;
    private void noLongerExist(SHAPE shape) {
        shape.setEXIST(false);
        MAP.remove(shape.Name());
        if (shape instanceof Group _shape) {
            for (SHAPE member : _shape.getMembers()) {
                noLongerExist(member);
            }
        }
    }
    private void nowExist(SHAPE shape){
        shape.setEXIST(true);
        MAP.put(shape.Name(),shape);
        if(shape instanceof Group _shape){
            for(SHAPE member : _shape.getMembers()){
                nowExist(member);
            }
        }
    }

    /**
     * @param name name of shape to be deleted
     */
    DELETE(String name) {
        undoOperation.clear();
        this.NAME = name;
        SHAPE it = MAP.get(name);
        this.Z=it.Z();
        noLongerExist(it);
        pastOperation.push(this);
    }

    @Override
    protected void redo() {
        SHAPE shape = SHAPE.AllShapes[Z];
        noLongerExist(shape);
    }

    @Override
    protected void undo() {
        SHAPE shape = SHAPE.AllShapes[Z];
        nowExist(shape);
    }
}

/**
 * move a shape
 */
class MOVE extends OPERATION {
    private final double x,y;

    /**
     * @param name name of the shape to be moved
     * @param x    The distance moved in the positive direction of x
     * @param y    The distance moved in the positive direction of y
     */
    MOVE(String name, double x, double y) {
        undoOperation.clear();
        this.NAME = name;
        this.x = x;
        this.y = y;
        MAP.get(name).move(x, y);
        pastOperation.push(this);
    }

    @Override
    protected void redo() {
        SHAPE shape = MAP.get(NAME);
        shape.move(x,y);
    }

    @Override
    protected void undo() {
        SHAPE shape = MAP.get(NAME);
        shape.move(-x,-y);
    }

}
