package GUI;

/***********************************
* Name: OutputPanel.java
* Date: Aug 17, 2010
* @author martin
* Description:
***********************************/

import java.awt.BorderLayout;
import javax.swing.*;


public class OutputPanel extends JPanel{

    protected JTextPane pane = new JTextPane();

    /*****************************************
     * constructor
     *****************************************/
    public OutputPanel(){

        JScrollPane scroll = new JScrollPane(this.pane);

        this.pane.setEditable(false);
        this.pane.setFont(Main.font);
        
        this.setLayout(new BorderLayout());
        this.add(scroll, BorderLayout.CENTER);
    }

    public void setTextPane(String text){
        pane.setText(text);
    }

    public JTextPane getTextPane(){
        return pane;
    }
}
