
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.lang.Math;



public class GUIInterface {
    private static final int TEXTWIDTH = 30;
    private static final Canvass myCanvass = new Canvass();
    private static final JButton enterButton = new JButton("OK");
    private static final JButton redoButton = new JButton("←");
    private static final JButton undoButton = new JButton("→");

    private static final JTextArea inputTextArea = new JTextArea(2,TEXTWIDTH);
    private static final JTextArea outputTextArea = new JTextArea(5,TEXTWIDTH);


    public static void main(String[] args) {
        Initialize();
        myCanvass.drawShape(new Rectangle("2",0,0,10,10,1));
        myCanvass.drawShape(new Circle("1", -50, 50, 25, 0));
        myCanvass.repaint();
    }

    /*
     * Initialize the Jframe panel
     */
    private  static void Initialize(){
        SwingUtilities.invokeLater(()->CreateMainDisplayFrame());

    }
    /*
     * Initialize the main dispaly GUI, this is where the main panel is created and components of which are added. 
     */
    private static void CreateMainDisplayFrame(){
        System.out.println("On EDT = "+ SwingUtilities.isEventDispatchThread()); //Debug Info
        JFrame mainDisplayFrame = new JFrame("I am a GUI"); //Title
        mainDisplayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exit on close
        // mainDisplayFrame.setResizable(false); //Set if the window is resizable
        //============================================================================================\\
        mainDisplayFrame.setLayout(new GridBagLayout()); //Set layout
        GridBagConstraints layoutManager = new GridBagConstraints(); //Access the layout manager
        layoutManager.gridx = 0; layoutManager.gridy = 0; //location to insert components
        layoutManager.fill = GridBagConstraints.HORIZONTAL;
        mainDisplayFrame.add(myCanvass, layoutManager); //Add canvas to panel

        //=============================================================================================\\
        //Jtextarea Reference: https://docs.oracle.com/javase/8/docs/api/javax/swing/JTextArea.html
        //Jscrollable Referece: https://stackoverflow.com/questions/8849063/adding-a-scrollable-jtextarea-java
        /*
         * This section of code is configuring the text input and output area.
         */
        layoutManager.fill = GridBagConstraints.HORIZONTAL;
        layoutManager.gridwidth = 3;
        layoutManager.gridx = 0;layoutManager.gridy = 1;
        outputTextArea.setBackground(Color.black);
        outputTextArea.setForeground(Color.WHITE);
        outputTextArea.setEditable(false);
        outputTextArea.setLineWrap(true);
        JScrollPane outputTextAreaScrollPane = new JScrollPane(outputTextArea);
        outputTextAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        mainDisplayFrame.add(outputTextAreaScrollPane, layoutManager);

        layoutManager.gridheight = 1;
        layoutManager.fill = GridBagConstraints.HORIZONTAL;
        layoutManager.gridx = 0;layoutManager.gridy = 2;
        inputTextArea.setBackground(Color.black);
        inputTextArea.setForeground(Color.WHITE);
        inputTextArea.setLineWrap(true);
        JScrollPane inputTextAreaScrollPane = new JScrollPane(inputTextArea);
        inputTextAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        mainDisplayFrame.add(inputTextAreaScrollPane, layoutManager);
        //=============================================================================================\\
        layoutManager.gridx = 0;layoutManager.gridy = 3;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(redoButton);
        buttonPanel.add(enterButton);
        buttonPanel.add(undoButton);
        mainDisplayFrame.add(buttonPanel,layoutManager);
        //=============================================================================================\\
        enterButton.addActionListener(new ActionListener(){
            //set up button action
            @Override
            public void actionPerformed(ActionEvent e) {
                String getText = inputTextArea.getText();
                if (getText.equals(""))return;
                outputTextArea.append(StripLineFeed(getText)+"\n");
                inputTextArea.selectAll();
                inputTextArea.replaceSelection("");
            }});
            // //Set up key binding
            // JPanel contentPane = (JPanel) mainDisplayFrame.getContentPane();
            // int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
            // InputMap inputMap = contentPane.getInputMap(condition);
            // ActionMap actionMap = contentPane.getActionMap();

            // String enter = "Enter";
            // inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), enter);
            // actionMap.put(enter, new AbstractAction() {
            //     @Override
            //     public void actionPerformed(ActionEvent key){
            //         String getText = inputTextArea.getText();
            //         if (getText.equals(""))return;
            //         outputTextArea.append(getText+"\n");
            //         inputTextArea.selectAll();
            //         inputTextArea.replaceSelection("");
            //     }
            // });
        
        //=============================================================================================\\
        mainDisplayFrame.pack(); //Resize window so it incorporate all component
        mainDisplayFrame.setLocationRelativeTo(null);
        mainDisplayFrame.setVisible(true); //show up
    }
    
    private static String StripLineFeed(String inputString){
        int start = 0;
        int end = inputString.length();
        while(inputString.charAt(start)=='\n') start++;
        while(inputString.charAt(end-1)=='\n') end--;
        return inputString.substring(start,end);
    }
}

//=====================================================================================================\\
/*
 * This class is the canvas on which the shapes are drawn.
 */
class Canvass extends JPanel {
    private Stack<SHAPE> shapeToDrawStack = new Stack<>();
    private int halfWidth = 250;
    private int halfHeight = 250;
    private int zoomRate = 1;
    /*
     * Initialize the canvass
     */
    public Canvass(){
        setBorder(BorderFactory.createLineBorder(Color.black));
    }
    /*
     * Return the size for the pack() in main display GUI
     */
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(500, 500);
    }
    /*
     * Draw components
     */
    @Override
    public void paintComponent(Graphics shape){
        super.paintComponent(shape);
        //draw coordinate system    
        shape.drawLine(0,250,500,250);
        shape.drawLine(250,0,250,500);
        shape.drawString("Shit", 20, 20);
        while (!(shapeToDrawStack.isEmpty())){
            SHAPE shapeToDraw = shapeToDrawStack.pop();
            if (shapeToDraw instanceof Rectangle s) {
                int[] fit = fitToWindow((int)Math.round(s.X()), (int)Math.round(s.Y()));
                shape.drawRect(fit[0], fit[1], (int)Math.round(s.W()), (int)Math.round(s.H()));
            } else if (shapeToDraw instanceof Line s) {
                int[] fit1 = fitToWindow((int)Math.round(s.X1()), (int)Math.round(s.Y1()));
                int[] fit2 = fitToWindow((int)Math.round(s.X2()), (int)Math.round(s.Y2()));
                shape.drawLine(fit1[0], fit1[1], fit2[0], fit2[1]);
            } else if (shapeToDraw instanceof Circle s) {
                int [] fit = fitToWindow((int)Math.round(s.X()), (int)Math.round(s.Y()));
                shape.drawOval(fit[0], fit[1], (int)Math.round(s.R())*2, (int)Math.round(s.R())*2);
            } else if (shapeToDraw instanceof Square s) {
                int [] fit = fitToWindow((int)Math.round(s.X()), (int)Math.round(s.Y()));
                shape.drawRect(fit[0], fit[1], (int)Math.round(s.L()), (int)Math.round(s.L()));
            }
        }
        
    }
    /*
     * invoke repainting the canvass
     */
    private void updateCanvas(){
        repaint();
    }
    /*
     * append the shape to redraw to the stack
     */
    public void drawShape(SHAPE shape){
        this.shapeToDrawStack.push(shape);
    }
    public int[] fitToWindow(int x, int y){
        int[] fit = {x*zoomRate+halfWidth,halfHeight-y*zoomRate};
        return fit;
    }

}