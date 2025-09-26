// Parent class shape
class SHAPE {
    private String NAME; 
    private double X;
    private double Y;
    private int ZOrder;

    public SHAPE(String Name, double x, double y, int Zorder){
        this.NAME = Name;
        this.X = x;
        this.Y = y;
        this.ZOrder = Zorder;
    }

    public String Name(){return this.NAME;}
    public double X(){return this.X;}
    public double Y(){return this.Y;}
    public int Z(){return this.ZOrder;}
    public void move(double x, double y){
        this.X += x;
        this.Y += y;
    }

}

class Rectangle extends SHAPE{
    private double WIDTH;
    private double HEIGHT;
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
    public double width(){return this.WIDTH;}
    public double height(){return this.HEIGHT;}
}

class Circle extends SHAPE {
    private double RADIUS;
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
    public double radius(){return this.RADIUS;}
}

class Line extends SHAPE{
    private double X2;
    private double Y2;
    Line (String Name, double x, double y, double x2, double y2, int ZOrder){
        super(Name, x, y, ZOrder);
        this.X2 = x2;
        this.Y2 = y2;
    }
    @Override
    public String Name(){return super.Name();}
    @Override
    public int Z(){return super.Z();}
    public double X1(){return super.X();}
    public double Y1(){return super.Y();}
    public double X2(){return this.X2;}
    public double Y2(){return this.Y2;}
    @Override
    public void move(double x, double y){
        super.move(x,y);
        this.X2 += x;
        this.Y2 += y;
    }
}

class Square extends SHAPE{
    private double LENGTH;

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
    public double length(){return this.LENGTH;}

}

class Group extends SHAPE{
    private SHAPE[] group = new SHAPE[100];
    private int index = 0;
    Group(String Name, int Zorder){
        super(Name,0,0,Zorder);
    }
    public void add(SHAPE shape){
        this.group[index] = shape;
        index+=1;
    }
    @Override
    public void move(double x, double y){
        for(int i = 0; i<this.index; i++){
            group[i].move(x,y);
        }
    }
}