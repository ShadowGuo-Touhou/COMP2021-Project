package COMP2021_Project;


/**
 * Judge whether the inputs from INTERFACE is effective
 */
public class IS_EFFECTIVE {
    private String[] inputs;
    private String command;
    private GUIInterface gui = INTERFACE.getGui();

    /**
     * @param inputs input from INTERFACE
     * @param command the command type of input
     */
    public IS_EFFECTIVE(String[] inputs, String command){
        this.inputs=inputs;
        this.command=command;
    }
    /**判断形状是否重复*/
    private boolean IsDuplicate(){
        if(OPERATION.MAP.containsKey(inputs[1])){
            String out = String.format("Error! Duplicate Shape: " + inputs[1]);
            System.out.println(out);
            gui.updateOutput(out);
            return true;
        }
        return false;
    }
    /**确认参数个数是否正确*/
    private boolean IsWrongNumberOfParameters(){
        int n=inputs.length;
        switch (command){

            case "rectangle":
            case "line":
                if(n!=6) {
                    return true;
                }
                return false;
            case "circle":
            case "square":
                if(n!=5){
                    return true;
                }
                return false;
            case "group":
                if(n<4) {
                    return true;
                }
                return false;
            case "move":
                if(n!=4) {
                    return true;
                }
                return false;
            case "ungroup":
            case "delete":
            case "boundingbox":
            case "list":
                if(n!=2) {
                    return true;
                }
                return false;
            case "shapeat":
            case "intersect":
                if(n!=3) {
                    return true;
                }
                return false;
            case "listall":
            case "quit":
            case "undo":
            case "redo":
                if(n!=1) {
                    return true;
                }
                return false;
            default:
                return false;
        }
    }
    /**判断形状和组是否存在*/
    private boolean IsShapeExist(String name){
        if(!(OPERATION.MAP.containsKey(name))){
            String out = String.format("Error! Can't find shape: " + name);
            System.out.println(out);
            gui.updateOutput(out);
            return false;
        }else if(!OPERATION.MAP.get(name).isEXIST()){
            String out = String.format("Error! Can't find shape: " + name);
            System.out.println(out);
            gui.updateOutput(out);
            return false;
        }
        return true;
    }

    /**
     * @return whether the input is effective
     */
    protected boolean IsEffective(){
        int n=inputs.length;
        if(this.IsWrongNumberOfParameters()){
            System.out.println("Error! Wrong number of parameters!");
            gui.updateOutput("Error! Wrong number of parameters!");
            return false;
        }
        switch (command){
            case "rectangle":
            case "circle":
            case "line":
            case "square":
                if(this.IsDuplicate()){
                    return false;
                }
                double[] arg=new double[n-2];
                /**检查参数是否可以进行double类型转换*/
                for(int i=0;i<arg.length;i++){
                    try {
                        arg[i] = Double.parseDouble(inputs[2+i]);
                    } catch (NumberFormatException e) {
                        String out = String.format("Wrong parameter format: " + inputs[2 + i]);
                        System.out.println(out);
                        gui.updateOutput(out);
                        return false;
                    }
                }
                if(command.equals("rectangle")){
                    if(arg[2]<=0||arg[3]<=0){
                        System.out.println("Error! Width and height must be positive!");
                        gui.updateOutput("Error! Width and height must be positive!");
                        return false;
                    }
                }else if(command.equals("line")){
                    if(arg[0]==arg[2]&&arg[1]==arg[3]){
                        System.out.println("Error! The two endpoints of the line segment coincide!");
                        gui.updateOutput("Error! The two endpoints of the line segment coincide!");
                        return false;
                    }
                }else if(command.equals("circle")){
                    if(arg[2]<=0){
                        System.out.println("Error! Radius must be positive!");
                        gui.updateOutput("Error! Radius must be positive!");
                        return false;
                    }
                }else if(command.equals("square")){
                    if(arg[2]<=0){
                        System.out.println("Error! Side length must be positive!");
                        gui.updateOutput("Error! Side length must be positive!");
                        return false;
                    }
                }
                return true;
            case "group":
                if(/*!(inputs[1].equals(inputs[2]))&&!(inputs[1].equals(inputs[3]))&&*/this.IsDuplicate()){
                    return false;
                }
                /*
                if(!(OPERATION.MAP.containsKey(inputs[2]))){
                    System.out.println("Error! Can't find shape: " + inputs[2]);
                    return false;
                }else if(!(OPERATION.MAP.containsKey(inputs[3]))){
                    System.out.println("Error! Can't find shape: " + inputs[3]);
                    return false;
                }else if(!OPERATION.MAP.get(inputs[2]).isEXIST()){
                    System.out.println("Error! Can't find shape: " + inputs[2]);
                    return false;
                }else if(!OPERATION.MAP.get(inputs[3]).isEXIST()) {
                    System.out.println("Error! Can't find shape: " + inputs[3]);
                    return false;
                }
                */
                for(int i=0;i<n-2;i++){
                    if(!(IsShapeExist(inputs[2+i]))){
                        return false;
                    }
                }
                for(int i=0;i<n-2;i++){
                    for(int j=i+1;j<n-2;j++){
                        if(inputs[2+i].equals(inputs[2+j])){
                            System.out.println("Error! The shapes to be grouped coincide!");
                            gui.updateOutput("Error! The shapes to be grouped coincide!");
                            return false;
                        }
                    }
                }
                return true;
            case "ungroup":
                if(!IsShapeExist(inputs[1])){
                    return false;
                }
                SHAPE testshape=OPERATION.MAP.get(inputs[1]);
                if(!(testshape instanceof  Group)){
                    System.out.println("Error! The shape is not a group!");
                    gui.updateOutput("Error! The shape is not a group!");
                    return false;
                }
                return true;
            case "delete":
            case "boundingbox":
            case "list":
                if(!IsShapeExist(inputs[1])){
                    return false;
                }
                if(OPERATION.MAP.get(inputs[1]).findFather()!=null){
                    String out = String.format("Error! Operation "+command+" can't be used on a single shape in a group!");
                    System.out.println(out);
                    gui.updateOutput(out);
                    return false;
                }
                return true;
            case "move":
                try {
                    double x = Double.parseDouble(inputs[2]);
                }catch(NumberFormatException e){
                    String out = String.format("Wrong parameter format: " + inputs[2]);
                    System.out.println(out);
                    gui.updateOutput(out);
                    return false;
                }
                try {
                    double x = Double.parseDouble(inputs[3]);
                }catch(NumberFormatException e){
                    String out = String.format("Wrong parameter format: " + inputs[3]);
                    System.out.println(out);
                    gui.updateOutput(out);
                    return false;
                }
                if(!IsShapeExist(inputs[1])){
                    return false;
                }
                if(OPERATION.MAP.get(inputs[1]).findFather()!=null){
                    String out = String.format("Error! Operation "+command+" can't be used on a single shape in a group!");
                    System.out.println(out);
                    gui.updateOutput(out);
                    return false;
                }
                return true;
            case "shapeat":
                try {
                    double x = Double.parseDouble(inputs[1]);
                }catch(NumberFormatException e){
                    String out = String.format("Wrong parameter format: " + inputs[1]);
                    System.out.println(out);
                    gui.updateOutput(out);
                    return false;
                }
                try {
                    double x = Double.parseDouble(inputs[2]);
                }catch(NumberFormatException e){
                    String out = String.format("Wrong parameter format: " + inputs[2]);
                    System.out.println(out);
                    gui.updateOutput(out);
                    return false;
                }
                return true;
            case "intersect":
                if(!(IsShapeExist(inputs[1])&&IsShapeExist(inputs[2]))){
                    return false;
                }
                if(OPERATION.MAP.get(inputs[1]).findFather()!=null){
                    String out = "Error! Operation "+command+" can't be used on a single shape in a group!";
                    System.out.println(out);
                    return false;
                }
                if(OPERATION.MAP.get(inputs[2]).findFather()!=null){
                    String out = "Error! Operation "+command+" can't be used on a single shape in a group!";
                    System.out.println(out);
                    return false;
                }
                return true;
            case "listall":
            case "quit":
                return true;
            case "undo":
                if(OPERATION.pastOperation.isEmpty()){
                    System.out.println("Error! No operation can be undone!");
                    gui.updateOutput("Error! No operation can be undone!");
                    return false;
                }
                return true;
            case "redo":
                if(OPERATION.undoOperation.isEmpty()){
                    System.out.println("Error! No operation can be redone!");
                    gui.updateOutput("Error! No operation can be redone!");
                    return false;
                }
                return true;
            default:
                System.out.println("Error! Unknown command!");
                gui.updateOutput("Error! Unknown command!");
                return false;
        }
    }
}
