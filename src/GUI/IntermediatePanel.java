package GUI;

/***********************************
* Name: IntermediatePanel.java
* Date: Sep 16, 2010
* @author martin
* Description:
***********************************/

import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.Color;

public class IntermediatePanel extends JPanel{

    JTextPane pane = new JTextPane();
    
    /*****************************************
     * constructor
     *****************************************/
    public IntermediatePanel(){

        JScrollPane scroll = new JScrollPane(this.pane);

        this.pane.setFont(Main.font);
        this.pane.setEditable(false);
        this.setLayout(new BorderLayout());
        this.add(scroll, BorderLayout.CENTER);
    }

    /***********************************************
    * insertText
    * @param isBold boolean
    * @param color Color
    * @param text String
    * @return void
    ***********************************************/
    private void insertText(boolean isBold, Color color, String text){
        //**************************************************
        SimpleAttributeSet set = new SimpleAttributeSet();
        //**************************************************
        set = new SimpleAttributeSet();
        StyleConstants.setBold(set, isBold);
        StyleConstants.setForeground(set, color);
        Document doc = pane.getStyledDocument();
        pane.setCharacterAttributes(set, true);
        try {
            doc.insertString(doc.getLength(), text, set);
        } catch (Exception ex) {}
    }

    /***********************************************
    * setText
    * @param text
    * @return void
    ***********************************************/
    private void setText(String text) {
        //**************************************************
        String texto = text;
        //**************************************************
        boolean done = false;
        //**************************************************
        boolean bold = true, not_bold = false;
        //**************************************************
        String letter="[a-zA-Z]";
        String digit="\\d";
        String number=digit+"("+digit+")*";
        //**************************************************
        this.insertText(not_bold, Color.black, "");
        //**************************************************
        
        while(!done){

            if (false){
                //texto
            }
            else{
                //default
                //*************************************************
                this.insertText(not_bold, Color.black, texto.charAt(0)+"");
                texto = texto.substring(1);
                //*************************************************
            }
            if(texto.length()==0){
                done = true;
            }
        }
    }
}
