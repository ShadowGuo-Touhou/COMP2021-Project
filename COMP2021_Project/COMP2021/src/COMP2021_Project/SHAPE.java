package COMP2021_Project;
import java.util.ArrayList;

/**
 * SHAPE --super class--
 * abstract class
 */
public abstract class SHAPE {
    private static final int CAPACITY = 10000000;
    /** shapeAt bias */
    protected static final double bias = 0.05;
    private final String NAME;
    private double X;
    private double Y;
    private boolean EXIST;
    private final int ZOrder;
    /** the Father of a shape is the group it belongs to */
    protected SHAPE Father ;
    /** store all shapes，the index is its Z Order */
    protected static SHAPE[] AllShapes = new SHAPE[CAPACITY];
    /** the max Z Order */
    private static int MAXZ=0;

    /** @return MAXZ */
    public static int getMAXZ(){return MAXZ;}

    /** @return ++MAXZ */
    public static int MAXZplus(){return ++MAXZ;}
    /**
     * @param Name name of the shape
     * @param x The X-coordinate of the shape (the meanings of different subclasses may not be the same)
     * @param y The Y-coordinate of the shape (the meanings of different subclasses may not be the same)
     * @param Zorder its Z Order
     */
    public SHAPE(String Name, double x, double y, int Zorder){
        this.NAME = Name;
        this.setX(x);
        this.setY(y);
        this.ZOrder = Zorder;
        this.EXIST = true;
    }

    /** @return name of shape */
    public String Name(){return this.NAME;}
    /** @return The X-coordinate of the shape */
    public double X(){return this.X;}
    /** @return The Y-coordinate of the shape */
    public double Y(){return this.Y;}
    /** @return its Z Order */
    public int Z(){return this.ZOrder;}
    /**
     * @param x The distance the shape moves in the positive direction of x
     * @param y The distance the shape moves in the positive direction of y
     */
    public void move(double x, double y){
        this.X+=x;
        this.Y+=y;
    }

    /**
     * @param x The inquired x-coordinate
     * @param y The inquired y-coordinate
     * @return whether the shape is at (x,y)
     */
    public abstract boolean at(double x,double y);

    /**
     * @param x1 point1's x
     * @param y1 point1's y
     * @param x2 point2's x
     * @param y2 point2's y
     * @return distance between point1 and point2
     */
    protected static double dis(double x1,double y1,double x2,double y2){
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }

    /** @param y set y */
    public void setY(double y) {Y = y;}
    /** @param x set x */
    public void setX(double x) {X = x;}

    /** @return if it's exist */
    public boolean isEXIST() { return EXIST; }
    /** @param b set its existence */
    public void setEXIST(boolean b) { EXIST = b; }
    /** @return its father(group) */
    public SHAPE findFather() { return Father; }
    /** @param b set its father */
    public void setFather(SHAPE b) { Father = b; }
}

/**
 * RECTANGLE --subclass--
 */
class Rectangle extends SHAPE{
    private final double WIDTH;
    private final double HEIGHT;

    /**
     * @param Name name
     * @param x The x-coordinate of the point in the upper left corner
     * @param y The y-coordinate of the point in the upper left corner
     * @param width its width
     * @param height its height
     * @param Zorder its Z Order
     */
    Rectangle(String Name, double x, double y, double width, double height,int Zorder){
        super(Name,x,y,Zorder);
        this.WIDTH = width;
        this.HEIGHT = height;
    }
    @Override
    public String Name(){return super.Name();}
    @Override
    public double X(){return super.X();}
    @Override
    public double Y(){return super.Y();}
    @Override
    public int Z(){return super.Z();}

    /** @return its width */
    public double W(){return this.WIDTH;}
    /** @return its height */
    public double H(){return this.HEIGHT;}
    @Override
    public boolean at(double x,double y){
        Line l1= new Line(null,X(),Y(),X()+W(),Y(),-1);
        if(l1.at(x,y))return true;
        Line l2= new Line(null,X(),Y()-H(),X()+W(),Y()-H(),-1);
        if(l2.at(x,y))return true;
        Line l3= new Line(null,X(),Y(),X(),Y()-H(),-1);
        if(l3.at(x,y))return true;
        Line l4= new Line(null,X()+W(),Y(),X()+W(),Y()-H(),-1);
        return l4.at(x, y);
    }

}

/**
 * CIRCLE --subclass--
 */
class   Circle extends SHAPE {
    private final double RADIUS;

    /**
     * @param Name name
     * @param x The x-coordinate of the center
     * @param y The y-coordinate of the center
     * @param radius its radius
     * @param Zorder its Z Order
     */
    Circle(String Name, double x, double y, double radius, int Zorder){
        super(Name,x,y,Zorder);
        this.RADIUS = radius;
    }
    @Override
    public String Name(){return super.Name();}
    @Override
    public double X(){return super.X();}
    @Override
    public double Y(){return super.Y();}
    @Override
    public int Z(){return super.Z();}
    /** @return its radius */
    public double R(){return this.RADIUS;}
    @Override
    public boolean at(double x, double y){
        double distance=dis(X(),Y(),x,y);
        return distance<=R()+bias&&distance>=R()-bias;
    }
}

/**
 * LINE --subclass--
 */
class Line extends SHAPE{
    private double X2;
    private double Y2;

    /**
     * @param Name name
     * @param x point1's x
     * @param y point1's y
     * @param x2 point1's x
     * @param y2 point1's y
     * @param ZOrder its Z Order
     */
    Line (String Name, double x, double y, double x2, double y2, int ZOrder){
        super(Name, x, y, ZOrder);
        this.X2 = x2;
        this.Y2 = y2;
    }
    @Override
    public String Name(){return super.Name();}
    @Override
    public int Z(){return super.Z();}

    /** @return point1's x */
    public double X1(){return super.X();}
    /** @return point1's y */
    public double Y1(){return super.Y();}
    /** @return point2's x */
    public double X2(){return this.X2;}
    /** @return point2's y */
    public double Y2(){return this.Y2;}
    @Override
    public void move(double x, double y){
        super.move(x,y);
        this.X2 += x;
        this.Y2 += y;
    }
    @Override
    public boolean at(double x,double y){
        double[] lineVector= new double[]{X2() - X1(), Y2() - Y1()};
        double[] pointVector=new double[]{x-X1(),y-Y1()};
        double len=dis(X1(),Y1(),X2(),Y2());
        double t=(pointVector[0]*lineVector[0]+pointVector[1]*lineVector[1])/len;
        if(t<0){
            return dis(X1(),Y1(),x,y)<=bias;
        }else if(t>1){
            return dis(X2(),Y2(),x,y)<=bias;
        }else{
            return dis(X1()+t*lineVector[0],Y1()+t*lineVector[1],x,y)<=bias;
        }
    }
}

/**
 * SQUARE --subclass--
 */
class Square extends SHAPE{
    private final double LENGTH;
    /**
     * @param Name name
     * @param x The x-coordinate of the top left corner point
     * @param y The y-coordinate of the top left corner point
     * @param length its length
     * @param Zorder its Z Order
     */
    public Square(String Name, double x, double y, double length, int Zorder) {
        super(Name, x, y, Zorder);
        this.LENGTH = length;
    }
    @Override
    public String Name(){return super.Name();}
    @Override
    public double X(){return super.X();}
    @Override
    public double Y(){return super.Y();}
    @Override
    public int Z(){return super.Z();}
    /** @return 正方形的边长 */
    public double L(){return this.LENGTH;}
    @Override
    public boolean at(double x,double y){
        Line l1= new Line(null,X(),Y(),X()+L(),Y(),-1);
        if(l1.at(x,y))return true;
        Line l2= new Line(null,X(),Y()-L(),X()+L(),Y()-L(),-1);
        if(l2.at(x,y))return true;
        Line l3= new Line(null,X(),Y(),X(),Y()-L(),-1);
        if(l3.at(x,y))return true;
        Line l4= new Line(null,X()+L(),Y(),X()+L(),Y()-L(),-1);
        return l4.at(x, y);
    }
}

/**
 * GROUP --subclass--
 */
class Group extends SHAPE{
    /** A maximum value */
    static final double LARGE_NUMBER=2000000000;
    private final ArrayList<SHAPE> Members=new ArrayList<>();
    private double LeftX=LARGE_NUMBER;
    private double TopY=-LARGE_NUMBER;
    private double RightX=-LARGE_NUMBER;
    private double BottomY=LARGE_NUMBER;

    /**
     * @param Name name
     * @param ZOrder its Z Order
     */
    Group(String Name, int ZOrder){
        super(Name,0,0,ZOrder);
    }

    /** @return its members */
    public ArrayList<SHAPE> getMembers(){
        return Members;
    }

    /** @param shape add the shape into this group */
    public void add(SHAPE shape){
        while(shape.findFather()!=null)shape=shape.findFather();
        if(shape==this)return;
        Members.add(shape);
        shape.setFather(this);
    }

    /** Calculate the bounding box of the group */
    public void updateBox(){
        for(SHAPE member:Members){
            double[] box=QUERY.boundingBox(member);
            this.LeftX=Math.min(getLeftX(),box[0]);
            this.TopY=Math.max(getTopY(),box[1]);
            this.RightX=Math.max(getRightX(),box[0]+box[2]);
            this.BottomY=Math.min(getBottomY(),box[1]-box[3]);
        }
    }
    @Override
    public boolean at(double x,double y){
        for(SHAPE member:Members){
            if(member.at(x,y))
                return true;
        }
        return false;
    }
    @Override
    public void move(double x,double y){
        for(SHAPE member:Members){
            member.move(x,y);
        }
    }
    /** @return leftmost x-coordinate in group */
    public double getLeftX() {
        return LeftX;
    }
    /** @return topmost y-coordinate in group */
    public double getTopY() {
        return TopY;
    }
    /** @return rightmost x-coordinate in group */
    public double getRightX() {
        return RightX;
    }
    /** @return bottommost x-coordinate in group */
    public double getBottomY() {
        return BottomY;
    }
}