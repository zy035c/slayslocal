package referenceExamples;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
/* ww w  .  j  a v a 2  s  . c o m*/
public class ToolTipExamp {
    public static void main(String[] args) {
        String html = "<html><body>" + "<h1>Header</h1>"
                + "<img src='http://www.java2s.com/style/download.png' "
                + "width='160' height='120'>";
        JLabel label = new JLabel("Point at me!");
        label.setToolTipText(html);
        JOptionPane.showMessageDialog(null, label);
    }
}
