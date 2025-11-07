package COMP2021_Project;

/**
 * 所有询问
 * 包括 boundingbox, shapeAt, intersect, list, listall
 */
public class QUERY {
    /** 询问图形的边界框
     * @param shape 所询问的图形
     * @return 图形边界框的左上角顶点的坐标和长与宽
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

    /** 询问在 (x,y) 处 Z Order 最高的图形的名字
     * @param x 所询问的x坐标
     * @param y 所询问的y坐标
     * @return 所在坐标 Z order 最高的图形的名字
     */
    public static String shapeAt(double x, double y){
        for(int i=SHAPE.MAXZ;i>0;i--){
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

    /** 询问两图行边界框是否有重合
     * @param shape1 图形1
     * @param shape2 图形2
     * @return 两图形边界框是否有重合
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

    /** 询问图形信息
     * @param shape 所需要列表的图形
     * 列出该图形的信息
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

    /** 询问所有图形信息
     * 列出所有图形的信息
     */
    public static void listall(){
        for(int i=1;i<=SHAPE.MAXZ;i++){
            SHAPE shape=SHAPE.AllShapes[i];
            if(shape.isEXIST()&&shape.findFather()==null){
                list(shape,"");
            }
        }
    }
}