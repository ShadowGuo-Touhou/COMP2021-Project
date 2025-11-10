package COMP2021_Project;

/**
 * ALL QUERIES
 * INCLUDE boundingbox, shapeAt, intersect, list, listall
 */
public class QUERY {
    /** Ask about the bounding box of the shape
     * @param shape The inquired shape
     * @return the bounding box
     */
    public static double[] boundingBox(SHAPE shape){
        double Bx=0,By=0,Bw=0,Bh=0;
        switch (shape) {
            case Rectangle _shape -> {
                Bx = _shape.X();
                By = _shape.Y();
                Bw = _shape.W();
                Bh = _shape.H();
            }
            case Line _shape -> {
                Bx = Math.min(_shape.X1(), _shape.X2());
                By = Math.max(_shape.Y1(), _shape.Y2());
                Bw = Math.abs(_shape.X2() - _shape.X1());
                Bh = Math.abs(_shape.Y2() - _shape.Y1());
            }
            case Circle _shape -> {
                Bx = _shape.X() - _shape.R();
                By = _shape.Y() + _shape.R();
                Bw = 2 * _shape.R();
                Bh = 2 * _shape.R();
            }
            case Square _shape -> {
                Bx = _shape.X();
                By = _shape.Y();
                Bw = _shape.L();
                Bh = _shape.L();
            }
            case Group _shape -> {
                _shape.updateBox();
                Bx = _shape.getLeftX() + _shape.X();
                By = _shape.getTopY() + _shape.Y();
                Bw = _shape.getRightX() - _shape.getLeftX();
                Bh = _shape.getTopY() - _shape.getBottomY();
            }
            case null, default -> {
            }
        }
        ;
        return new double[]{Bx,By,Bw,Bh};
    }

    /** Ask for the shape with the highest Z Order at (x,y)
     * @param x The inquired x-coordinate
     * @param y The inquired y-coordinate
     * @return shape with highest Z order at (x,y)
     */
    public static String shapeAt(double x, double y){
        for(int i=SHAPE.getMAXZ();i>0;i--){
            SHAPE shape=SHAPE.AllShapes[i];
            if(shape.isEXIST() &&shape.findFather()==null&&shape.at(x,y)){
                return shape.Name();
            }
        }
        return null;
    }
    private static boolean internal(double[] box,double x,double y){
        return box[0]<=x&&x<=box[0]+box[2]&&box[1]<=y&&y<=box[1]+box[3];
    }

    /**
     * @param shape1 shape1
     * @param shape2 shape2
     * @return Whether the two shape bounding boxes overlap
     */
    public static boolean insection(SHAPE shape1,SHAPE shape2){
        double[] box1=boundingBox(shape1),box2=boundingBox(shape2);

        if(internal(box1,box2[0],box2[1]))return true;
        if(internal(box1,box2[0]+box2[2],box2[1]))return true;
        if(internal(box1,box2[0],box2[1]+box2[3]))return true;
        if(internal(box1,box2[0]+box2[2],box2[1]+box2[3]))return true;

        if(internal(box2,box1[0],box1[1]))return true;
        if(internal(box2,box1[0]+box1[2],box1[1]))return true;
        if(internal(box2,box1[0],box1[1]+box1[3]))return true;
        if(internal(box2,box1[0]+box1[2],box1[1]+box1[3]))return true;

        return false;
    }

    /**
     * @param shape shape to be listed
     * @param pre retract
     */
    public static void list(SHAPE shape,String pre){
        switch (shape){
            case Rectangle _shape:
                System.out.printf(pre);
                System.out.printf(_shape.Name() + " is a rectangle" + "\n\t%sTop-left corner: (" + _shape.X() + "," + _shape.Y() + ") "
                                + "\n\t%swidth: " + _shape.W() + "\n\t%sheight: "+ _shape.H()+"\n",pre,pre,pre);
                break;
            case Line _shape:
                System.out.printf(pre);
                System.out.printf(_shape.Name() + " is a line" + "\n\t%sEndpoint: (" + _shape.X1() + "," + _shape.Y1() + ") ("
                                + _shape.X2() + "," + _shape.Y2() + ")\n",pre);
                break;
            case Circle _shape:
                System.out.printf(pre);
                System.out.printf(_shape.Name() + " is a circle" + "\n\t%sCenter: (" + _shape.X() + "," + _shape.Y()
                                + ")\n\t%sRadius: " + _shape.R()+"\n",pre,pre);
                break;
            case Square _shape:
                System.out.printf(pre);
                System.out.printf(_shape.Name() + " is a square\n\t%sTop-left corner: (" + _shape.X() + "," + _shape.Y()
                                + ")\n\t%sSide length: " + _shape.L()+"\n",pre,pre);
                break;
            case Group _shape:
                System.out.printf(pre);
                System.out.printf(_shape.Name() + " is a group\n\t%sMembers:\n",pre);
                for(SHAPE member:_shape.getMembers()){
                    list(member,pre+"      ");
                }
                break;
            default:
                break;
        }
    }

    /**
     * list all shapes
     */
    public static void listall(){
        for(int i=1;i<=SHAPE.getMAXZ();i++){
            SHAPE shape=SHAPE.AllShapes[i];
            if(shape.isEXIST()&&shape.findFather()==null){
                list(shape,"");
            }
        }
    }
}