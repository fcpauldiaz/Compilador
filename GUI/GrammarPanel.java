package GUI;

/***********************************
* Name: GrammarPanel.java
* Date: Aug 17, 2010
* @author martin
* Description:
***********************************/

import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GrammarPanel extends JPanel{

    JTextPane pane = new JTextPane();
    
    /*****************************************
     * constructor
     *****************************************/
    public GrammarPanel(){

        JScrollPane scroll = new JScrollPane(this.pane);

        this.LoadTextPane();
        this.pane.setFont(Main.font);

        this.pane.setEditable(false);
        this.setLayout(new BorderLayout());
        this.add(scroll, BorderLayout.CENTER);
    }


    /***********************************************
    * LoadTextPane:
    * Load the grammar and put it into the pane with format
    * @return void
    ***********************************************/
    private void LoadTextPane() {
        String grammar_text = leerArchivo(new File("src/antlr4/program.g4"));
       
        //**************************************************
        SimpleAttributeSet set = new SimpleAttributeSet();
        //**************************************************
        boolean done = false;

        String letter="[a-zA-Z]";
        String digit="\\d";
        String number=digit+"("+digit+")*";

        while(!done){
            
            if (grammar_text.length()==3598){
                System.out.println("STOP");
            }
            
            if(grammar_text.startsWith("\"")){
                //*************************************************
                String regex="\""+"([^\\\"])*"+"\"";
                //*************************************************
                String to_print = Main.expect(regex, grammar_text);
                //*************************************************
                set = new SimpleAttributeSet();
                if (to_print.isEmpty()){
                    to_print = " ";
                    StyleConstants.setForeground(set, Color.BLACK);//black
                }
                else{
                   
                    StyleConstants.setForeground(set, new Color(225,168,15));//orange
                }
                    Document doc = pane.getStyledDocument();
                    pane.setCharacterAttributes(set, true);
                
                try {
                    doc.insertString(doc.getLength(), to_print, set);
                    grammar_text = grammar_text.substring(to_print.length());
                } catch (Exception ex) {
                    done = true;
                }
                //*************************************************
            }
            else if(grammar_text.startsWith("\'")){
                //*************************************************
                String regex="\'"+"([^\\\'])*"+"\'";
                //*************************************************
                String to_print = Main.expect(regex, grammar_text);
                //*************************************************
                set = new SimpleAttributeSet();
                StyleConstants.setBold(set, true);
                StyleConstants.setForeground(set, new Color(228,167,53));//orange
                Document doc = pane.getStyledDocument();
                pane.setCharacterAttributes(set, true);
                try {
                    doc.insertString(doc.getLength(), to_print, set);
                    grammar_text = grammar_text.substring(to_print.length());
                } catch (Exception ex) {
                    done = true;
                }
                //*************************************************
            }
             else if(grammar_text.startsWith("!")){
                //*************************************************
                String regex="!"+"([^\\\n])*"+"[\\\n]";
                //*************************************************
                String to_print = Main.expect(regex, grammar_text);
                //*************************************************
                set = new SimpleAttributeSet();
                StyleConstants.setForeground(set, Color.gray);
                Document doc = pane.getStyledDocument();
                pane.setCharacterAttributes(set, true);
                try {
                    doc.insertString(doc.getLength(), to_print, set);
                    grammar_text = grammar_text.substring(to_print.length());
                } catch (Exception ex) {
                    done = true;
                }
                //*************************************************
            }
            else if (grammar_text.startsWith("<")){
                //*************************************************
                String regex="<"+"([^>])*"+">";
                //*************************************************
                String to_print = Main.expect(regex, grammar_text);
                //*************************************************
                set = new SimpleAttributeSet();
                StyleConstants.setBold(set, true);
                StyleConstants.setForeground(set, Color.BLUE);
                Document doc = pane.getStyledDocument();
                pane.setCharacterAttributes(set, true);
                try {
                    doc.insertString(doc.getLength(), to_print, set);
                    grammar_text = grammar_text.substring(to_print.length());
                } catch (Exception ex) {
                    done = true;
                }
                //*************************************************
            }
            else if (grammar_text.startsWith("{")){
                //*************************************************
                String regex="\\{"+"([^\\}])*"+"\\}";
                //*************************************************
                String to_print = Main.expect(regex, grammar_text);
                //*************************************************
                set = new SimpleAttributeSet();
                StyleConstants.setBold(set, true);
                StyleConstants.setForeground(set, new Color(29,148,23));//green
                Document doc = pane.getStyledDocument();
                pane.setCharacterAttributes(set, true);
                try {
                    doc.insertString(doc.getLength(), to_print, set);
                    grammar_text = grammar_text.substring(to_print.length());
                } catch (Exception ex) {
                    done = true;
                }
                //*************************************************
            }
            else{
                //default
                //*************************************************
                set = new SimpleAttributeSet();
                StyleConstants.setForeground(set, Color.BLACK);
                Document doc = pane.getStyledDocument();
                pane.setCharacterAttributes(set, true);
                try {
                    doc.insertString(doc.getLength(), grammar_text.charAt(0)+"", set);
                    grammar_text = grammar_text.substring(1);
                } catch (Exception ex) {
                    done = true;
                }
                //*************************************************
            }
            if(grammar_text.length()==1){
                done = true;
            }
        }
    }
    
    public static String leerArchivo(File inputFile){
      
        int contador=0;
        int tamaño=0;
        String input=" ";
        BufferedReader br = null;
 
        try {

                String sCurrentLine;
                
                br = new BufferedReader(new FileReader(inputFile.getAbsoluteFile()));

               
               
               while ((sCurrentLine = br.readLine()) != null) {
                    
                    input+=sCurrentLine+"\r\n";
                
                }
             
                
        input+="\r\n";
                
        return input;
        } catch (IOException e) {
               
        } finally {
                try {
                        if (br != null)br.close();
                } catch (IOException ex) {
                        ex.printStackTrace();
                }
        }
        return null;
        
    }
}
