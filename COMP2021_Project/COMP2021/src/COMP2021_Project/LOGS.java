package COMP2021_Project;
import java.io.*;

/**
 * Log command into 'log.txt' and 'log.html'
 */
public class LOGS {
    private final String[] inputs;
    private static String txtPath;
    private static String htmlPath;
    private static BufferedWriter txtWriter;
    private static BufferedWriter htmlWriter;
    private static BufferedReader htmlReader;
    private static int index;
    static{
        index=0;
    }

    /**
     * @param inputs input from INTERFACE
     */
    public LOGS(String[] inputs) {
        this.inputs=inputs;
        WriteInTxt();
        WriteInHtml();
    }

    /**
     * @return inputs
     */
    protected String[] getInputs() {
        return inputs;
    }

    /**
     * @return path of 'log.txt'
     */
    protected String getTextPath() {
        return txtPath;
    }

    /**
     * @return path of 'log.html'
     */
    protected String getHtmlPath() {
        return htmlPath;
    }

    /**
     * @param args args from INTERFACE
     * @return whether the path of 'log.txt' is effective
     */
    protected static boolean IstxtPathEffective(String[] args){
        for(int i=0;i<args.length;i+=2){
            if(args[i].equals("-txt")&&i+1< args.length){
                txtPath=args[i+1];
            }
        }
        if(txtPath==null){
            System.out.println("Error! Empty txt path.");
            return false;
        }
        try{
            txtWriter=new BufferedWriter(new FileWriter("log.txt"));
        }catch(IOException e){
            System.out.println("Error! Invalid txt path: "+txtPath);
            return false;
        }
        return true;
    }

    /**
     * @param args args from INTERFACE
     * @return whether the path of 'log.html' is effective.
     */
    protected static boolean IshtmlPathEffective(String[] args){
        for(int i=0;i<args.length;i+=2){
            if(args[i].equals("-html")&&i+1< args.length){
                htmlPath=args[i+1];
            }
        }
        if(htmlPath==null){
            System.out.println("Error! Empty html path.");
            return false;
        }
        try{
            htmlWriter=new BufferedWriter(new FileWriter("log.html"));
            htmlWriter.write("<html><body><table border=\"1\">");
            htmlWriter.newLine();
            htmlWriter.flush();
        }catch(IOException e){
            System.out.println("Error! Invalid html path: "+htmlPath);
            return false;
        }
        return true;
    }
    private static void Count(){
        index++;
    }
    private void WriteInTxt(){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<inputs.length;i++){
            sb.append(inputs[i]+" ");
        }
        sb.deleteCharAt(sb.length()-1);
        String line=sb.toString();
        try {
            txtWriter.write(line);
            txtWriter.newLine();
            txtWriter.flush();
        }catch(IOException e){
            System.out.println("Error! Failed to write txt file: "+txtPath);
        }
    }
    private void WriteInHtml(){
        Count();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<inputs.length;i++){
            sb.append(inputs[i]+" ");
        }
        sb.deleteCharAt(sb.length()-1);
        String cmd=sb.toString();
        try {
            htmlWriter.write("<tr>");
            htmlWriter.newLine();
            htmlWriter.write("<td>"+index+"</td>");
            htmlWriter.newLine();
            htmlWriter.write("<td>"+cmd+"</td>");
            htmlWriter.newLine();
            htmlWriter.write("</tr>");
            htmlWriter.newLine();
            htmlWriter.flush();
        }catch(IOException e){
            System.out.println("Error! Failed to write html file: "+htmlPath);
        }
    }

    /**
     * Close the file when user types 'quit'.
     */
    public static void CloseFile(){
        try {
            txtWriter.close();
        }catch(IOException e){
            System.out.println("Error! Failed to close txt file: "+txtPath);
        }
        try {
            htmlWriter.write("</table></body></html>");
            htmlWriter.flush();
            htmlWriter.close();
        }catch(IOException e){
            System.out.println("Error! Failed to close html file: "+htmlPath);
        }
    }

    /**
     * @param n index of the target line of command
     * @return the target line of command
     */
    public String getCommand(int n){
        if(n<1){
            System.out.println("Error! Invalid command index.");
            return null;
        }
        String line=null;
        try {
            BufferedReader txtReader = new BufferedReader(new FileReader("log.txt"));
            for(int i=0;i<n;i++){
                try{
                    line= txtReader.readLine();
                    if(line==null){
                        System.out.println("Error! Invalid command index.");
                        return null;
                    }
                }catch(IOException e){
                    System.out.println("Error! Failed to read txt file: "+txtPath);
                    return null;
                }
            }
            return line;
        }catch(FileNotFoundException e){
            System.out.println("Error! Failed to open txt file: "+txtPath);
            return null;
        }
    }
}
