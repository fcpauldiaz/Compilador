package gui;

/***********************************
* Name: TextPanelInterCode.java
* Archivo modificado por: Pablo D. 20/03/2016
* Date: Aug 17, 2010
* @author martin
* 
***********************************/


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;


public class TextPanelInterCode extends JPanel{

     JTextPane pane = new JTextPane();
     private Timer timer, timer2;
    /*****************************************
     * constructor
     *****************************************/
    public TextPanelInterCode(){

        JScrollPane scroll = new JScrollPane(this.pane);

        this.pane.setFont(new Font("monospaced", Font.PLAIN, 12));
      
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
    public void setText(LinkedList<String> lista) {
        this.pane.setText("");

        for(String a : lista){
            this.setText(a+"\n");
        }
    }
    
    public void clear(){
        this.pane.setText("");
    }

    /***********************************************
    * setText
    * @param text
    * @return void
    ***********************************************/
    public void setText(String text) {
        //**************************************************
        
        if(text.isEmpty())
            return;
        String texto = text;
        //**************************************************
        boolean done = false;
        //**************************************************
        boolean bold = true, not_bold = false;
        //**************************************************
        String letter="[a-zA-Z]";
        String digit="\\d";
        String number=digit+"("+digit+")*";
        String id = letter+"("+letter+"|"+number+"|_|\\$)*";
        String regex_character = "\'"+"([^\\\'])*"+"\'";
        String regex_comment = "(@"+"([^\\\n])*"+"\\\n)";
        String label = "L"+"([^:])*"+":";
        String regex_reserved_1 = "(param|call|return|proc|endp|ret|far|near)";
        String regex_if = "(.)*if .* goto .*\\\n";
        String regex_reserved_2 = "(if|goto|and|or|not)";
        String regex_temporales = "t\\$_"+number+"";
        String regex_goto = "goto .*\\\n";
        //*************************************************
        Color orange = new Color(228,167,53);//orange
        Color blue       = Color.BLUE;
        Color green      = new Color(29,148,23);//green
        Color corinto    = new Color(169,42,42);//corinto
        Color dark_green = new Color(46,139,87);//verde oscuro
        Color dark_blue  = new Color(0,102,153);//azul oscuro
        Color celeste    = new Color(0,153,255);//celeste
        Color morado     = new Color(153,102,255);//morado
        Color magenta    = Color.magenta;
        Color black      = Color.BLACK;
        Color gray       = Color.lightGray;
        //*************************************************

        while(!done){

            //*************************************************
            String to_print_temporales = expect(regex_temporales, texto);
            String to_print_character = expect(regex_character, texto);
            String to_print_number = expect(number, texto);
            String to_print_id = expect(id, texto);
            String to_print_label = expect(label, texto);
            String to_print_reserved_1 = expect(regex_reserved_1, texto);
            String to_print_reserved_2 = expect(regex_reserved_2, texto);
            String to_print_regex_if = expect(regex_if, texto);
            String to_print_regex_goto = expect(regex_goto, texto);
            String to_print_comment = expect(regex_comment, texto);
            //*************************************************

            if(!to_print_regex_goto.isEmpty()){

                this.insertText(bold, celeste, to_print_regex_goto.split(" ")[0]+" ");
                this.insertText(bold, green, to_print_regex_goto.split(" ")[1]);

                texto = texto.substring(to_print_regex_goto.length());

            }else if(!to_print_temporales.isEmpty()){

                this.insertText(bold, blue, to_print_temporales);

                texto = texto.substring(to_print_temporales.length());

            }else if(!to_print_regex_if.isEmpty()){

                String[] a = to_print_regex_if.split(" ");

                this.insertText(bold, celeste, a[0]+" ");
                this.insertText(bold, corinto, a[1]+" ");
                this.insertText(bold, black, a[2]+" ");//operador
                this.insertText(bold, corinto, a[3]+" ");
                this.insertText(bold, celeste, a[4]+" ");
                this.insertText(bold, green, a[5]);


                texto = texto.substring(to_print_regex_if.length());


            }
            else if(!to_print_comment.isEmpty()){
                //comment <- pinta el comentario
                this.insertText(bold, gray, to_print_comment);
                texto = texto.substring(to_print_comment.length());
            }
            else if(!to_print_label.isEmpty()){
                //label <- pinta el id de color
                this.insertText(bold, green, to_print_label.substring(0, to_print_label.length()-1));
                this.insertText(bold, black, ":");
                texto = texto.substring(to_print_label.length());
            }
            else if(!to_print_id.isEmpty() && to_print_id.length() != to_print_reserved_1.length() && to_print_id.length() != to_print_reserved_2.length()){
                //id
                this.insertText(bold, black, to_print_id);
                texto = texto.substring(to_print_id.length());
            }
            else if(!to_print_reserved_1.isEmpty()){
                //(true|false)
                this.insertText(bold, dark_blue, to_print_reserved_1);
                texto = texto.substring(to_print_reserved_1.length());
            }
            else if(!to_print_reserved_2.isEmpty()){
                //(if|goto|call)
                this.insertText(bold, celeste, to_print_reserved_2);
                texto = texto.substring(to_print_reserved_2.length());
            }
            else if(!to_print_character.isEmpty()){
                //'a'
                this.insertText(bold, orange, to_print_character);
                texto = texto.substring(to_print_character.length());
            }
            else if(!to_print_number.isEmpty()){
                this.insertText(not_bold, magenta, to_print_number);
                texto = texto.substring(to_print_number.length());
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
    
     public static String expect(String regex, String chain) {

        String cadena_encontrada="";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(chain);

        if (matcher.find()) {
            cadena_encontrada=matcher.group();
            //System.out.println("Encontrado: \""+cadena_encontrada+"\"");// entre ["+index_begin+","+index_end+"]");
            if(matcher.start()==0){
                return cadena_encontrada;
            }
        }
        return "";
    }

}