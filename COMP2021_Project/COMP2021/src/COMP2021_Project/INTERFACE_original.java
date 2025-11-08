package COMP2021_Project;
import java.util.Scanner;

public class INTERFACE {
    private static Scanner scanner = new Scanner(System.in);
    private static GUIInterface gui = new GUIInterface();
    static GUIInterface getGui(){
        return gui;
    }
    public static void deal(String sc){
        gui.DrawShape(SHAPE.AllShapes);
        String[] inputs = sc.trim().split(" ");
        gui.updateOutput(sc);
        int n=inputs.length;
        String command=inputs[0].toLowerCase();
        IS_EFFECTIVE sign=new IS_EFFECTIVE(inputs,command);
        /**@lqc: 在选择指令之前检测输入是否有效*/
        if(!sign.IsEffective()){
            System.out.println("Please try again!");
            return;
        }
        new LOGS(inputs);
        //gui.updateOutput("666");
        double x,y;
        switch (command){
            case "rectangle":
            case "circle":
            case "line":
            case "square":
                double[] arg=new double[n-2];
                for(int i=0;i<arg.length;i++){
                    arg[i]=Double.parseDouble(inputs[2+i]);
                }
                new CREATE(command,inputs[1],arg);
                break;
            case "group":
                String[] members=new String[n-2];
                System.arraycopy(inputs, 2, members, 0, members.length);
                new _GROUP(inputs[1],members);
                break;
            case "ungroup":
                new UNGROUP(inputs[1]);
                break;
            case "delete":
                new DELETE(inputs[1]);
                break;
            case "move":
                x=Double.parseDouble(inputs[2]);
                y=Double.parseDouble(inputs[3]);
                new MOVE(inputs[1],x,y);
                break;
            case "boundingbox":
                double[] box=QUERY.boundingBox(OPERATION.get(inputs[1]));
                System.out.printf("%.2f %.2f %.2f %.2f\n",box[0],box[1],box[2],box[3]);
                break;
            case "shapeat":
                x=Double.parseDouble(inputs[1]);
                y=Double.parseDouble(inputs[2]);
                System.out.println(QUERY.shapeAt(x,y));
                break;
            case "intersect":
                System.out.println(QUERY.insection(OPERATION.get(inputs[1]),OPERATION.get(inputs[2])));
                break;
            case "list":
                QUERY.list(OPERATION.get(inputs[1]),"");
                break;
            case "listall":
                QUERY.listall();
                break;
            case "undo":
                OPERATION.UNDO();
                break;
            case "redo":
                OPERATION.REDO();
                break;
            case "quit":
                System.out.println("Goodbye!");
                /**@lqc: 在这里关闭并保存日志*/
                LOGS.CloseFile();
                System.exit(0);
                //return;
        }
    }
    public static boolean IsPathEffective(String[] args){
        return LOGS.IshtmlPathEffective(args)&&LOGS.IstxtPathEffective(args);
    }
    public static void main(String[] args) {
        /**@lqc: 确保两个文件路径有效才进行下一步的程序*/
        if(!IsPathEffective(args)){
            System.out.println("Please try again!");
            return;
        }
        while(true){
            String sc = scanner.nextLine();
            deal(sc);
        }
    }
}
