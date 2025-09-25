import javax.swing.JOptionPane;

public class testApp{
    public static void main(String[] args) {
        //dialog boxes
        String str = JOptionPane.showInputDialog("Fuck you man");
        JOptionPane.showMessageDialog(null, "Today is nothing but pure shit", "got it?",1);//JOptionPane.ERROR_MESSAGE,JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE
        int ans = JOptionPane.showConfirmDialog(null, "Are you ok?", "Fuck you", JOptionPane.NO_OPTION); //JOptionPane.YES_OPTION, DEFAULT_OPTION, OK_CANCEL_OPTION, YES_NO_CANCEL_OPTION
    }
}
