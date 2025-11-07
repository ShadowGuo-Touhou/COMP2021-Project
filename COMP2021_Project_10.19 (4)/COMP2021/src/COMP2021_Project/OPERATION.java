package COMP2021_Project;

import java.util.Stack;
import java.util.HashMap;

/**
 * 所有可以进行 redo 和 undo 的操作
 * 包括 创建形状或组 (CREATE 和 GROUP), 取消组 (UNGROUP), 删除 (DELETE), 移动(MOVE)
 */
public abstract class OPERATION {

    /**
     * 该操作对应的图形的名字
     */
    protected String NAME;
    /**
     * 映射：名字 -> 图形
     */
    protected static final HashMap<String, SHAPE> MAP = new HashMap<>();
    /**
     * 表示过去进行过的操作
     */
    protected static final Stack<OPERATION> pastOperation = new Stack<>();
    /**
     * 表示被撤销的操作
     */
    protected static final Stack<OPERATION> undoOperation = new Stack<>();

    /**
     * @param s 表示图形的名称
     * @return 相应的图形
     */
    public static SHAPE get(String s) {
        return MAP.get(s);
    }

    /**
     * 重做被 undo 的操作
     */
    static void REDO() {
        if (undoOperation.empty()) {
            System.out.println("No undo operation yet!");
            return;
        }
        OPERATION it = undoOperation.pop();
        it.redo();
        pastOperation.push(it);
    }

    /**
     * 撤销操作
     */
    static void UNDO() {
        if (pastOperation.empty()) {
            System.out.println("No operation yet!");
            return;
        }
        OPERATION it = pastOperation.pop();
        it.undo();
        undoOperation.push(it);
    }

    /**
     * 重做当前操作
     * 抽象方法，需要在子类中实现
     */
    protected abstract void redo();

    /**
     * 撤销当前操作
     * 抽象方法，需要在子类中实现
     */
    protected abstract void undo();
}

/**
 * 创建非组图形的操作
 */
class CREATE extends OPERATION {
    private int originalZ = -1, Z;

    /**
     * @param type 需要创造的图形的类型：rectangle、circle、line、square
     * @param name 图形的名字
     * @param arg  与图形有关的参数
     */
    CREATE(String type,String name,double[] arg){
        undoOperation.clear();
        this.NAME=name;
        this.Z=++SHAPE.MAXZ;
        SHAPE.AllShapes[Z] = switch (type) {
            case "rectangle" -> new Rectangle(name, arg[0], arg[1], arg[2], arg[3], Z);
            case "circle" -> new Circle(name, arg[0], arg[1], arg[2], Z);
            case "line" -> new Line(name, arg[0], arg[1], arg[2], arg[3], Z);
            case "square" -> new Square(name, arg[0], arg[1], arg[2], Z);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
        SHAPE it=SHAPE.AllShapes[Z];
        /**@lqc: 这里进行了修改，默认不可覆写形状*/
        MAP.put(name,it);
        if(MAP.containsKey(name)){
            originalZ=MAP.get(name).Z();
            MAP.replace(name,it);
        }else{
            MAP.put(name,it);
        }
        pastOperation.push(this);
    }

    @Override
    protected void redo() {
        SHAPE shape = SHAPE.AllShapes[Z];
        if(originalZ==-1){
            MAP.put(NAME,shape);
        }else{
            MAP.replace(NAME,shape);
        }
        shape.setEXIST(true);
    }

    @Override
    protected void undo() {
        SHAPE shape = MAP.get(NAME);
        shape.setEXIST(false);
        if (originalZ != -1){
            MAP.replace(NAME, SHAPE.AllShapes[originalZ]);
        } else {
            MAP.remove(NAME);
        }
    }
}

/**
 * 创建组的操作
 */
class _GROUP extends OPERATION {
    private int originalZ = -1, Z;

    /**
     * @param name    图形的名字
     * @param members 组所包含的图形
     */
    _GROUP(String name,String[] members){
        undoOperation.clear();
        this.NAME=name;
        Z=++SHAPE.MAXZ;
        SHAPE.AllShapes[Z]= new Group(name,Z);
        Group it=(Group) SHAPE.AllShapes[Z];

         if(MAP.containsKey(name)){
         originalZ=MAP.get(name).Z();
         MAP.replace(name,it);
         }else{
         MAP.put(name,it);
         }

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
        if (originalZ == -1) {
            MAP.put(NAME, group);
        }else{
            MAP.replace(NAME,group);
        }

    }

    @Override
    protected void undo() {
        Group group=(Group)SHAPE.AllShapes[Z];
        group.setEXIST(false);

        for (SHAPE member : group.getMembers()) {
            member.setFather(null);
        }
        if (originalZ != -1) {
            MAP.replace(NAME, SHAPE.AllShapes[originalZ]);
        } else {
            MAP.remove(NAME);
        }
    }
}

/**
 * 解散组的操作
 */
class UNGROUP extends OPERATION {
    private int Z;
    /**
     * @param name 被解散的组的名字
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

        // 恢复成员独立状态
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

        // 恢复组状态
        group.setEXIST(true);
        MAP.put(NAME,group);
        // 恢复成员关系
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
 * 删除图形的操作
 */
class DELETE extends OPERATION {
    private int Z;
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
     * @param name 被删除的图形的名称
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
 * 移动图形的操作
 */
class MOVE extends OPERATION {
    private double x, y;

    /**
     * @param name 所移动图形的名称
     * @param x    x正方向移动的距离
     * @param y    y正方向移动的距离
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
