package COMP2021_Project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.Stack;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 * Setup Gui Interface
 */
public class GUIInterface {
    private final int TEXTWIDTH = 30;
    private final Canvass myCanvass = new Canvass();
    private final JButton enterButton = new JButton("OK");
//    private final JButton redoButton = new JButton("←");
//    private final JButton undoButton = new JButton("→");
    private final JButton zoomInButton = new JButton("+");
    private final JButton zoomOutButton = new JButton("-");
    private final JTextArea inputTextArea = new JTextArea(2,TEXTWIDTH);
    private final JTextArea outputTextArea = new JTextArea(8,TEXTWIDTH);
    private String userInputString = "";
    private final JFrame mainDisplayFrame = new JFrame("Cirno's Graphic Displayer");

    /**
     * Initialize the Jframe panel
     */
    public GUIInterface(){
        SwingUtilities.invokeLater(this::CreateMainDisplayFrame);

    }
    /*
     * Initialize the main dispaly GUI, this is where the main panel is created and components of which are added. 
     */
    private void CreateMainDisplayFrame(){
        // System.out.println("On EDT = "+ SwingUtilities.isEventDispatchThread()); //Debug Info
//        mainDisplayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exit on close
        // mainDisplayFrame.setResizable(false); //Set if the window is resizable
        //============================================================================================\\
        mainDisplayFrame.setLayout(new GridBagLayout()); //Set layout
        GridBagConstraints layoutManager = new GridBagConstraints(); //Access the layout manager
        layoutManager.gridx = 0; layoutManager.gridy = 0; //location to insert components
        layoutManager.fill = GridBagConstraints.HORIZONTAL;
        mainDisplayFrame.add(myCanvass, layoutManager); //Add canvas to panel

        //=============================================================================================\\
        //Change Icon
        if(getClass().getResource("cirno.png") != null) {
            ImageIcon imgIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("cirno.png")));
            mainDisplayFrame.setIconImage(imgIcon.getImage());
        }
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
        outputTextArea.append("Use CTRL+arrow keys to adjust position\nUse CTRL+ PLUS/MINUS to zoom in/out\n(´▽`ʃ♡ƪ)\n");

        layoutManager.gridheight = 1;
        layoutManager.fill = GridBagConstraints.HORIZONTAL;
        layoutManager.gridx = 0;layoutManager.gridy = 2;
        inputTextArea.setBackground(Color.black);
        inputTextArea.setForeground(Color.WHITE);
        inputTextArea.setLineWrap(true);
        JScrollPane inputTextAreaScrollPane = new JScrollPane(inputTextArea);
        inputTextAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
//        mainDisplayFrame.add(inputTextAreaScrollPane, layoutManager);
        //=============================================================================================\\
        //Buttons
//        layoutManager.gridx = 0;layoutManager.gridy = 3;
//        JPanel buttonPanel = new JPanel();
        // buttonPanel.add(zoomOutButton);
        // buttonPanel.add(redoButton);
//        buttonPanel.add(enterButton);
        // buttonPanel.add(undoButton);
        // buttonPanel.add(zoomInButton);
//        mainDisplayFrame.add(buttonPanel,layoutManager);
        //=============================================================================================\\
        ButtonFunctions();
        //=============================================================================================\\
        mainDisplayFrame.pack(); //Resize window so it incorporate all component
        mainDisplayFrame.setLocationRelativeTo(null);
        mainDisplayFrame.setVisible(true); //show up
        mainDisplayFrame.setAlwaysOnTop(true);
    }
//=====================================================================================================\\
    private void ButtonFunctions(){
//        enterButton.addActionListener((e)->TextUpDate());
        zoomInButton.addActionListener((e)->myCanvass.zoomIn());
        zoomOutButton.addActionListener((e)->myCanvass.zoomOut());
    //Set up key binding============================================================
        int condition = JComponent.WHEN_FOCUSED;
        InputMap inputMap = mainDisplayFrame.getRootPane().getInputMap(condition);
        ActionMap actionMap = mainDisplayFrame.getRootPane().getActionMap();
    //LEFT Key Binding==============================================================
        final String MOVELEFT = "MoveLeft";
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,KeyEvent.CTRL_DOWN_MASK), MOVELEFT);
        actionMap.put(MOVELEFT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                myCanvass.shiftLeft();
            }});
       inputTextArea.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,KeyEvent.CTRL_DOWN_MASK), MOVELEFT);
       inputTextArea.getActionMap().put(MOVELEFT, new AbstractAction() {
           @Override
           public void actionPerformed(ActionEvent e){
               myCanvass.shiftLeft();
           }});
    //RIGHT Key Binding==============================================================
        final String MOVERIGHT = "MoveRIGHT";
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,KeyEvent.CTRL_DOWN_MASK), MOVERIGHT);
        actionMap.put(MOVERIGHT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                myCanvass.shiftRight();
            }});
       inputTextArea.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,KeyEvent.CTRL_DOWN_MASK), MOVERIGHT);
       inputTextArea.getActionMap().put(MOVERIGHT, new AbstractAction() {
           @Override
           public void actionPerformed(ActionEvent e){
               myCanvass.shiftRight();
           }});
    //UP Key Binding================================================================
        final String MOVEUP = "MoveUp";
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,KeyEvent.CTRL_DOWN_MASK), MOVEUP);
        actionMap.put(MOVEUP, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                myCanvass.shiftUp();
            }});
    //DOWN Key Binding==============================================================
        final String MOVEDOWN = "MoveDown";
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,KeyEvent.CTRL_DOWN_MASK), MOVEDOWN);
        actionMap.put(MOVEDOWN, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                myCanvass.shiftDown();
            }});
    //ENTER Key Binding==============================================================
        // final String ENTER = "ENTER";
        // inputTextArea.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), ENTER);
        // inputTextArea.getActionMap().put(ENTER, new AbstractAction() {
        //     @Override
        //     public void actionPerformed(ActionEvent e){
        //         TextUpDate();
        //     }});
    //PLUS Key Binding==============================================================
        final String PLUS = "PLUS";
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, KeyEvent.CTRL_DOWN_MASK), PLUS);
        actionMap.put(PLUS, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                myCanvass.zoomIn();
            }});   
    //MINUS Key Binding==============================================================
        final String MINUS = "MINUS";
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, KeyEvent.CTRL_DOWN_MASK), MINUS);
        actionMap.put(MINUS, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                myCanvass.zoomOut();
            }});    
    }
//==============================Move functions========================


//==================================Handle Enter button===============================================\\
    /*
     * 将输入栏清空，并保存用户输入结果后写入输出栏，
     */
//    private void TextUpDate(){
//        String getText = inputTextArea.getText();
//        this.userInputString = getText;
//        outputTextArea.append(getText+"\n");
//        if (getText.equals("")) return;
//        inputTextArea.selectAll();
//        inputTextArea.replaceSelection("");
//    }
    
    /**@return
     * 这个方法可以获取用户输入，返回String
     */
    public String getUserInput(){return this.userInputString;}
    /**
     * 写入输出栏
     * @param s 输出
     */
    public void updateOutput(String s){
        SwingUtilities.invokeLater(()->outputTextArea.append(s+"\n"));
    }
//===================================Draw shape=====================================================\\
    /**
     * 这个方法是用来绘制图形的，建议直接传入包含所有图形的数组，无需去掉Group类
     * @param shape s
     */
    public void DrawShape(SHAPE[] shape){
        SwingUtilities.invokeLater(()->this.myCanvass.drawShape(shape));
    }
}

//=====================================================================================================\\
/**
 * This class is the canvas on which the shapes are drawn.
 */
class Canvass extends JPanel {
    private Stack<SHAPE> shapeToDrawStack = new Stack<>();
    private final int defaultCenter = 250;
    private int CenterX = defaultCenter;
    private int CenterY = defaultCenter;
    private int zoomRate = 1;
    private final int shiftDistance = 30;
    /**
     * Initialize the canvass
     */
    Canvass(){
        setBorder(BorderFactory.createLineBorder(Color.black));
        //Create buttons
        // setUpButtonFunction();
    }

//    private void setUpButtonFunction(){
//        final int buttonWidth = 20;
//        final int buttonHeigh = 20;
//        JButton ShiftUpButton = new JButton("↑");
//        ShiftUpButton.setPreferredSize(new Dimension(buttonWidth,10));
//        JButton ShiftDownButton = new JButton("↓");
//        ShiftDownButton.setPreferredSize(new Dimension(buttonWidth,10));
//        JButton ShiftLeftButton = new JButton("←");
//        ShiftLeftButton.setPreferredSize(new Dimension(10,buttonHeigh));
//        JButton ShiftRightButton = new JButton("→");
//        ShiftRightButton.setPreferredSize(new Dimension(10,buttonHeigh));
//        //add buttons to canvas
//        setLayout(new BorderLayout());
//        add(ShiftUpButton,BorderLayout.NORTH);
//        add(ShiftDownButton, BorderLayout.SOUTH);
//        add(ShiftLeftButton, BorderLayout.WEST);
//        add(ShiftRightButton, BorderLayout.EAST);
//
//        ShiftUpButton.addActionListener(e->shiftUp());
//        ShiftDownButton.addActionListener(e->shiftDown());
//        ShiftLeftButton.addActionListener(e->shiftLeft());
//        ShiftRightButton.addActionListener(e->shiftRight());
//    }
    /*
     * Return the size for the pack() in main display GUI
     */
    @Override
    public Dimension getPreferredSize(){
        final int size = 500;
        return new Dimension(size, size);
    }
    /*
     * Draw components
     */
    @Override
    public void paintComponent(Graphics shape) {
        super.paintComponent(shape);
        //draw coordinate system
        final int size = 500;
        shape.setColor(Color.BLACK);
        shape.drawLine(0, CenterY, size, CenterY);
        shape.drawLine(CenterX, 0, CenterX, size);
        Stack<SHAPE> shapeToDrawStack2 = new Stack<>();

        shape.setColor(Color.BLUE);
        while (!(shapeToDrawStack.isEmpty())) {
            while (!shapeToDrawStack.isEmpty()) {
                SHAPE shapeToDraw = shapeToDrawStack.pop();
                shapeToDrawStack2.push(shapeToDraw);
                //不画被删除的图形
                if (!shapeToDraw.isEXIST()) continue;

                switch (shapeToDraw) {
                    case Rectangle s -> {
                        int[] fit = fitToWindow((int) Math.round(s.X()), (int) Math.round(s.Y()));
                        shape.drawRect(fit[0], fit[1], zoomRate * (int) Math.round(s.W()), zoomRate * (int) Math.round(s.H()));
                    }
                    case Line s -> {
                        int[] fit1 = fitToWindow((int) Math.round(s.X1()), (int) Math.round(s.Y1()));
                        int[] fit2 = fitToWindow((int) Math.round(s.X2()), (int) Math.round(s.Y2()));
                        shape.drawLine(fit1[0], fit1[1], fit2[0], fit2[1]);
                    }
                    case Circle s -> {
                        int[] fit = fitToWindow((int) Math.round(s.X()), (int) Math.round(s.Y()));
                        shape.drawOval(fit[0], fit[1], zoomRate * (int) Math.round(s.R()) * 2, zoomRate * (int) Math.round(s.R()) * 2);
                    }
                    case Square s -> {
                        int[] fit = fitToWindow((int) Math.round(s.X()), (int) Math.round(s.Y()));
                        shape.drawRect(fit[0], fit[1], zoomRate * (int) Math.round(s.L()), zoomRate * (int) Math.round(s.L()));
                    }
                    case Group g -> {
                        // Group handling
                    }
                    default -> {}
                }
            }
        }
        shapeToDrawStack = shapeToDrawStack2;
    }
    // 　　　　　　　＿人人人人人人人人人人人人人＿_                            ,,....,, _
    // 　　　　　　　＞　　ゆっくりしていってね！！！＜             ／::::::::::::::::: " ' :; ,,,
    // 　　　　　　　￣^Ｙ^Ｙ^Ｙ^Ｙ^Ｙ^Ｙ^Ｙ^Ｙ^Ｙ^Ｙ^Ｙ^Ｙ^   ／::::::::::::::::::::::::/"     ,,,
    // 　　　＿_＿　　　 _____　 ______.　　　　　r‐- .,_／::::::::::::; ／￣ヽ;:::::::|
    // 　　 ネ　　_,, '-´￣￣｀-ゝ、_''　　　　__.)　　　`''ｧ-ｧ'"´, '　　　 ヽ:::|
    // 　　, ﾝ 'r ´　　　　　　　　　　ヽ、　　ゝ_, '"ソ二ﾊ二`ゝ- ﾍ ､_　_ ゞ!._
    // 　 i　,' ＝=─-　　　 　 -─=＝ ;　､'"ヽ, '´　,' 　; 　　`"''‐-=ﾌﾞ､_,:::::"'''- ,,
    // 　 |　i　ｲ ルゝ､ｲ;人レ／ﾙヽｲ　 i　ヽ_/i.　 /!　ﾊ 　ﾊ　 ! ヽ　ヽ 丶'ァ' '"
    // 　 ||. i、|. |　(ﾋ_]　　　　ﾋ_ﾝ) i ﾘｲj　　 <、 ',. /__,.!/ V　､!__,ﾊ､ |｀、｀; ,!i;
    // 　 |　iヽ「 ! ""　　,＿__,　 "" !Y.!　　　ヽ iV (ﾋ_] 　　　ﾋ_ﾝ )　ﾚ !;　 ｲ ）
    // 　 .| |ヽ.L.」　　　 ヽ _ﾝ　　　,'._.」　　　　V i '"　 ,＿__,　　 "' '!　ヽ　 （
    // 　 ヽ |ｲ|| |ヽ､　　　　　　　 ｲ|| |　　　　 i,.人.　　ヽ _ｿ　　　 ,.ﾊ　 ）　､ `､
    // 　 　ﾚ　ﾚル.　｀.ー--一 ´ル レ　　　　ﾉハ　> ,､ ._____,. ,,. ｲ;（　 （ '` .)　）
    //  写烦了，整点行为艺术，这BGUI谁爱写谁写

    /*
     * invoke repainting the canvass
     */
    private void updateCanvas(){
        repaint();
    }
    /**
     * append the shape to redraw to the stack
     * @param shape s
     */
    protected void drawShape(SHAPE[] shape){
        this.shapeToDrawStack.clear();
        for(SHAPE s: shape) if(s!=null) this.shapeToDrawStack.push(s);
        updateCanvas();
    }
    private int[] fitToWindow(int x, int y){
        int[] fit = {zoomRate*x+CenterX,CenterY-zoomRate*y};
        return fit;
    }

    /**
     * Shift window left
     */
    protected void shiftLeft(){
        this.CenterX+=shiftDistance;
        updateCanvas();
    }

    /**
     * Shift window Right
     */
    protected void shiftRight(){
        this.CenterX-=shiftDistance;
        updateCanvas();
    }

    /**
     * Shift window Up
     */
    protected void shiftUp(){
        this.CenterY+=shiftDistance;
        updateCanvas();
    }

    /**
     * Shift window Down
     */
    protected void shiftDown(){
        this.CenterY-=shiftDistance;
        updateCanvas();
    }

    /**
     * Zoom in view
     */
    protected void zoomIn(){
        zoomRate+=1;
        updateCanvas();
    }

    /**
     * Zoom out view
     */
    public void zoomOut(){
        if(zoomRate==1)return;
        zoomRate-=1;
        updateCanvas();
    }
}
