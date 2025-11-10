package COMP2021_Project;
import java.util.ArrayList;

/**
 * 图形父类，所有图形子类由其延申而来
 */
public abstract class SHAPE {
    private static final int CAPACITY = 10000000;
    /** shapeAt 的偏差值 */
    protected static final double bias = 0.05;
    private final String NAME;
    private double X;
    private double Y;
    private boolean EXIST;
    private final int ZOrder;
    /** 如果该图形在某个组内，则其 Father 为其所属组图形。若其不属于某个组，其 Father 值为 null */
    protected SHAPE Father ;
    /** 记录所有存在过的图形，图形的索引等于其 Z Order */
    protected static SHAPE[] AllShapes = new SHAPE[CAPACITY];
    /** 记录目前最新图形的 Z Order */
    protected static int MAXZ=0;

    /**
     * @param Name 图形的名字
     * @param x 图形的x坐标（不同子类的意义不一定相同）
     * @param y 图形的y坐标（不同子类的意义不一定相同）
     * @param Zorder 图形的 Z Order
     */
    public SHAPE(String Name, double x, double y, int Zorder){
        this.NAME = Name;
        this.setX(x);
        this.setY(y);
        this.ZOrder = Zorder;
        this.EXIST = true;
    }

    /** @return 图形的名字 */
    public String Name(){return this.NAME;}
    /** @return 图形的 x 坐标 */
    public double X(){return this.X;}
    /** @return 图形的 y 坐标 */
    public double Y(){return this.Y;}
    /** @return 图形的 Z Order */
    public int Z(){return this.ZOrder;}
    /** 图形的移动方法，在Line子类中被重写
     * @param x 图形在 x 正方向移动的距离
     * @param y 图形在 y 正方向移动的距离
     */
    public void move(double x, double y){
        this.X+=x;
        this.Y+=y;
    }

    /** 判断图形是否在 (x,y) 处
     * @param x 所询问的 x 坐标
     * @param y 所询问的 y 坐标
     * @return 图形是否在 (x,y) 处
     */
    public abstract boolean at(double x,double y);

    /** 计算两点的距离
     * @param x1 点 1 的 x 坐标
     * @param y1 点 1 的 y 坐标
     * @param x2 点 2 的 x 坐标
     * @param y2 点 2 的 y 坐标
     * @return 两点的距离
     */
    protected static double dis(double x1,double y1,double x2,double y2){
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }

    /** @param y 将纵坐标设定为 y */
    public void setY(double y) {Y = y;}
    /** @param x 将横坐标设定为 x */
    public void setX(double x) {X = x;}

    /** @return 图形是否存在 */
    public boolean isEXIST() { return EXIST; }
    /** @param b 设定图形是否存在 */
    public void setEXIST(boolean b) { EXIST = b; }
    /** @return 图形所在组 */
    public SHAPE findFather() { return Father; }
    /** @param b 设定图形所在组 */
    public void setFather(SHAPE b) { Father = b; }
}

/**
 * 长方形类
 */
class Rectangle extends SHAPE{
    private final double WIDTH;
    private final double HEIGHT;

    /**
     * @param Name 长方形的名字
     * @param x 长方形左上角的点的 x 坐标
     * @param y 长方形左上角的点的 y 坐标
     * @param width 长方形宽度
     * @param height 长方形高度
     * @param Zorder 图形 Z Order
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

    /** @return 长方形的宽度 */
    public double W(){return this.WIDTH;}
    /** @return 长方形的高度 */
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
 * 圆形类
 */
class   Circle extends SHAPE {
    private final double RADIUS;

    /**
     * @param Name 圆形的名字
     * @param x 圆心的 x 坐标
     * @param y 圆心的 y 坐标
     * @param radius 圆形半径
     * @param Zorder 图形的 Z Order
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
    /** @return 圆形的半径 */
    public double R(){return this.RADIUS;}
    @Override
    public boolean at(double x, double y){
        double distance=dis(X(),Y(),x,y);
        return distance<=R()+bias&&distance>=R()-bias;
    }
}

/**
 * 线段类
 */
class Line extends SHAPE{
    private double X2;
    private double Y2;

    /**
     * @param Name 线段的名字
     * @param x 点 1 的 x 坐标
     * @param y 点 1 的 y 坐标
     * @param x2 点 2 的 x 坐标
     * @param y2 点 2 的 y 坐标
     * @param ZOrder 图形的 Z Order
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

    /** @return 点 1 的 x 坐标 */
    public double X1(){return super.X();}
    /** @return 点 1 的 y 坐标 */
    public double Y1(){return super.Y();}
    /** @return 点 2 的 x 坐标 */
    public double X2(){return this.X2;}
    /** @return 点 2 的 y 坐标 */
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
 * 正方形类
 */
class Square extends SHAPE{
    private final double LENGTH;
    /**
     * @param Name 正方形的名字
     * @param x 正方形左上角点的 x 坐标
     * @param y 正方形左上角点的 y 坐标
     * @param length 正方形的边长
     * @param Zorder 图形的 Z Order
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
 * 组类
 */
class Group extends SHAPE{
    /** 一个极大值 */
    static final double LARGE_NUMBER=2000000000;
    private final ArrayList<SHAPE> Members=new ArrayList<>();
    private double LeftX=LARGE_NUMBER;
    private double TopY=-LARGE_NUMBER;
    private double RightX=-LARGE_NUMBER;
    private double BottomY=LARGE_NUMBER;

    /**
     * @param Name 组的名字
     * @param ZOrder 图形的 Z Order
     */
    Group(String Name, int ZOrder){
        super(Name,0,0,ZOrder);
    }

    /** @return 组内成员 */
    public ArrayList<SHAPE> getMembers(){
        return Members;
    }

    /** @param shape 将图形 shape 加入组 */
    public void add(SHAPE shape){
        while(shape.findFather()!=null)shape=shape.findFather();
        if(shape==this)return;
        Members.add(shape);
        shape.setFather(this);
    }

    /** 计算组的边界框 */
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
    /** @return 组边界框最左 x 坐标 */
    public double getLeftX() {
        return LeftX;
    }
    /** @return 组边界框最上 y 坐标 */
    public double getTopY() {
        return TopY;
    }
    /** @return 组边界框最右 x 坐标 */
    public double getRightX() {
        return RightX;
    }
    /** @return 组边界框最下 y 坐标 */
    public double getBottomY() {
        return BottomY;
    }
}