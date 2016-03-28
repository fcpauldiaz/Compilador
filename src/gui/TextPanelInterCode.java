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
import java.awt.event.ActionEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;


public class TextPanelInterCode extends JPanel{

    private JTextPane textPane = new JTextPane();
    private Timer timer, timer2;
    private boolean activar_timer = false;

    private int pos_selected_start = 0;
    private int pos_selected_end = 0;
    private String pos_selected_txt = "";

    private boolean inserto = false;
    private boolean quito = false;

    private int tamano_antes = 0;

    /*****************************************
     * TextPanel
     * constructor
     *****************************************/
    public TextPanelInterCode(){

        JScrollPane scroll = new JScrollPane(this.textPane);
        this.textPane.setEditable(true);

        this.textPane.setFont(new Font("monospaced", Font.PLAIN, 12));

       
      
        //add the  listener
        this.textPane.getDocument().addDocumentListener(new myDocumentListener());

        timer = new javax.swing.Timer(1, (ActionEvent e) -> {
            if(activar_timer == false){
                return;
            }
            //System.out.println("actualizar colores");
            setPosSelectedWithEnter();
            if(inserto){
                insertTabs();
            }
            
            activar_timer = false;
            tamano_antes = textPane.getText().length();
            timer.stop();
        });
        //timer.start();
        timer2 = new javax.swing.Timer(10, (ActionEvent e) -> {
            if(textPane.getText().isEmpty())
                return;
            highLightKey();
        });
        timer2.start();

        this.setLayout(new BorderLayout());
        this.add(scroll, BorderLayout.CENTER);
        
    }

    /*****************************************
    * insertTabs
    * @return void
    *****************************************/
    private void insertTabs(){

        int start = this.textPane.getSelectionStart();
        String all_txt = this.textPane.getText();
        int cant_tabs = 0;
        try{
            if(all_txt.charAt(start-1)!='\n'){
                return;
            }
            if(all_txt.charAt(start-2)=='{'){
                cant_tabs=1;
                if(all_txt.charAt(start)=='}'){
                    cant_tabs=0;
                }
            }
        }catch(Exception exp){

        }
        int suma=0;
        String[] lineas = all_txt.split("\n");
        for(int i=1;i<lineas.length+1;i++){
            suma+=lineas[i-1].length()+1;
           
            if(suma>=start){
               
                String cadena = expect("(\t)+", lineas[i-1]);
                if(!cadena.isEmpty()){
                    cant_tabs += cadena.length();
                }
                break;
            }
        }
        
        for(int j=0;j<cant_tabs;j++){
            SimpleAttributeSet set = new SimpleAttributeSet();
            Document doc = textPane.getStyledDocument();
            textPane.setCharacterAttributes(set, true);
            try {
                doc.insertString(start, "\t", set);
            } catch (Exception ex) {}
        }
        

    }

    /*****************************************
    * setPosSelectedWithEnter
    * @return void
    *****************************************/
    private void setPosSelectedWithEnter(){

        int start = this.textPane.getSelectionStart();
        int end = start;
        String all_txt = this.textPane.getText();
        //start--;

        if(tamano_antes<all_txt.length()){
            start-=(all_txt.length()-tamano_antes);
        }else if(tamano_antes>all_txt.length()){
            //borro
            
        }

        try{
            while(all_txt.charAt(start)=='\n' || all_txt.charAt(start)=='\t' || all_txt.charAt(start)==' ')
                start--;
        }catch(Exception exp){}

        while(true){
            try{
                if(all_txt.charAt(start) == ' ' || all_txt.charAt(start) == '\n' || all_txt.charAt(start) == '\t'){
                    break;
                }else{
                    start--;
                }
            }catch(Exception exp){
                start++;
                break;
            }
        }
        if(start<0){
            start=0;
        }

        while(true){
            try{
                if(all_txt.charAt(end) == ' ' || all_txt.charAt(end) == '\n' || all_txt.charAt(end) == '\t'){
                    break;
                }else{
                    end++;
                }
            }catch(Exception exp){
                end--;
                break;
            }
        }

        end++;

        String text = "";
        try{
            text = all_txt.substring(start, end);
        }catch(Exception exp){
            return;
        }
        
        this.pos_selected_start = start;
        this.pos_selected_end = end;
        this.pos_selected_txt = text;

        //System.out.println("init: "+temp);
        //System.out.println("last: "+temp_final);
        //System.out.println("txt: ."+text+".");

        int selection = this.textPane.getSelectionStart();

        changePosSelected();
        changeComments();

        this.textPane.setSelectionStart(selection);
        this.textPane.setSelectionEnd(selection);
    }

    /*****************************************
    * changeComments
    * @return void
    *****************************************/
    private void changeComments(){
        //**************************************************
        SimpleAttributeSet set = new SimpleAttributeSet();
        //**************************************************
        String regex_comment = "(//"+"([^\\\n])*"+"\\\n)|(/\\*([^\\*/])*\\*/)";//TODO arreglar esta expresión..
        //*************************************************
        Color gray = Color.lightGray;
        //*************************************************
         boolean bold = true, not_bold = false;
        //*************************************************

        String to_print_comment="";
        Pattern pattern = Pattern.compile(regex_comment);
        Matcher matcher = pattern.matcher(this.textPane.getText());

        while(matcher.find()) {
            to_print_comment=matcher.group();
            int start = matcher.start();
            this.replaceSytleColor(set, not_bold, gray, to_print_comment, start);
        }

    }

    /*****************************************
    * changePosSelected
    * @return void
    *****************************************/
    private void changePosSelected(){
        
        //**************************************************
        SimpleAttributeSet set = new SimpleAttributeSet();
        //**************************************************
        String letter="[a-zA-Z]";
        String digit="\\d";
        String number=digit+"("+digit+")*";
        String id = letter+"("+letter+"|"+number+"|_)*";
        String regex_character = "\'"+"([^\\\'])*"+"\'";
        String regex_reserved_1 = "(class|struct|true|false|void|int|char|boolean)";
        String regex_reserved_2 = "(return|else|if|while)";
        String regex_method = id+"( )*"+"\\(";
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
        boolean bold = true, not_bold = false;
        //*************************************************
        
        String text = this.pos_selected_txt;
        int indice = this.pos_selected_start;
        boolean done = false;

        while(!done){

            //*************************************************
            String to_print_character = expect(regex_character, text);
            String to_print_number = expect(number, text);
            String to_print_id = expect(id, text);
            String to_print_reserved_1 = expect(regex_reserved_1, text);
            String to_print_reserved_2 = expect(regex_reserved_2, text);
            String to_print_method = expect(regex_method, text);
            //*************************************************
            
            if(!to_print_method.isEmpty() && to_print_id.length() != to_print_reserved_1.length() && to_print_id.length() != to_print_reserved_2.length()){
                //id() <- pinta el id de color
                this.replaceSytleColor(set, bold, morado, to_print_id, indice);
                indice+=to_print_id.length();
                text = text.substring(to_print_id.length());
            }
            else if(!to_print_id.isEmpty() && to_print_id.length() != to_print_reserved_1.length() && to_print_id.length() != to_print_reserved_2.length()){
                //id
                this.replaceSytleColor(set, bold, black, to_print_id, indice);
                indice+=to_print_id.length();
                text = text.substring(to_print_id.length());
            }
            else if(!to_print_reserved_1.isEmpty()){
                //class|struct|true|false|void|int|char|boolean)
                this.replaceSytleColor(set, bold, green, to_print_reserved_1, indice);
                indice+=to_print_reserved_1.length();
                text = text.substring(to_print_reserved_1.length());
            }
            else if(!to_print_reserved_2.isEmpty()){
                //(return|else|if|while)
                this.replaceSytleColor(set, bold, celeste, to_print_reserved_2, indice);
                indice+=to_print_reserved_2.length();
                text = text.substring(to_print_reserved_2.length());
            }
            else if(!to_print_character.isEmpty()){
                //'a'
                this.replaceSytleColor(set, bold, orange, to_print_character, indice);
                indice+=to_print_character.length();
                text = text.substring(to_print_character.length());
            }
            else if(!to_print_number.isEmpty()){
                this.replaceSytleColor(set, not_bold, magenta, to_print_number, indice);
                indice+=to_print_number.length();
                text = text.substring(to_print_number.length());
            }
            else{
                //default
                //*************************************************
                set = new SimpleAttributeSet();
                //StyleConstants.setForeground(set, Color.GREEN);
                Document doc = textPane.getStyledDocument();
                textPane.setCharacterAttributes(set, true);
                try {
                    doc.remove(indice, 1);
                    doc.insertString(indice, text.charAt(0)+"", set);
                    text = text.substring(1);
                    indice++;
                } catch (Exception ex) {
                    done = true;
                }
                //*************************************************
            }
            if(text.length()==0){
                done = true;
            }
        }
    }


    /*****************************************
    * replaceSytleColor
    * @return void
    * @param SimpleAttributeSet set
    * @param boolean isBold
    * @param Color color
    * @param String to_print
    * @param int start
    *****************************************/
    private void replaceSytleColor(SimpleAttributeSet set, boolean isbold, Color color,String to_print, int start){

        set = new SimpleAttributeSet();
        StyleConstants.setBold(set, isbold);
        StyleConstants.setForeground(set, color);
        Document doc = textPane.getStyledDocument();
        textPane.setCharacterAttributes(set, true);
        try {
            doc.remove(start, to_print.length());
            doc.insertString(start, to_print, set);
        } catch (Exception ex) {System.out.println("no se pudSystem.o remover");}
         set = new SimpleAttributeSet();
        textPane.setCharacterAttributes(set, true);
    }

    /*****************************************
    * simpleHighLight
    * @param length
    * @param start
    * @param painter
    * @return void
    *****************************************/
    private void simpleHighLight(int start, int length, MyHighlightPainter painter){
        JTextComponent textComp = textPane;
        //First remove all old highlights
        //removeHighlights();
        try{
            Highlighter hilite = textComp.getHighlighter();
            Document doc = textComp.getDocument();
            String text = doc.getText(0, doc.getLength());
            hilite.addHighlight(start, start+length, painter);
        }catch(Exception exp){

        }
    }

    /*****************************************
    * highLightKey
    * @return void
    *****************************************/
    private void highLightKey(){
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();
        if(start == end){
            //nothing selected
            try{
                if(textPane.getText().charAt(start)=='{' || textPane.getText().charAt(start)=='('){
                    end++;
                }
                else if(textPane.getText().charAt(start) == '}' || textPane.getText().charAt(start) == ')'){
                    end++;
                }else if(textPane.getText().charAt(start-1) == '{' || textPane.getText().charAt(start-1) == '('){
                    start--;
                }
                else if(textPane.getText().charAt(start-1) == '}' || textPane.getText().charAt(start-1) == ')'){
                    start--;
                }
            }catch(Exception exp){
                try{
                    if(textPane.getText().charAt(start-1) == '{' || textPane.getText().charAt(start-1) == '('){
                        start--;
                    }
                    else if(textPane.getText().charAt(start-1) == '}' || textPane.getText().charAt(start-1) == ')'){
                        start--;
                    }else if(textPane.getText().charAt(start)=='{' || textPane.getText().charAt(start)=='('){
                        end++;
                    }
                    else if(textPane.getText().charAt(start) == '}' || textPane.getText().charAt(start) == ')'){
                        end++;
                    }
                }catch(Exception exp1){}
            }
            removeHighlights();
            if(start != end){
                String to_hightlight = textPane.getText().substring(start, end);
                //System.out.println("."+to_hightlight+".");
                int close = getIndexClose(start,to_hightlight.charAt(0));
                if(close == -1){
                    //no está cerrado
                    simpleHighLight(start, 1, this.highlightPainter_red);
                }else{
                    simpleHighLight(start, 1, this.highlightPainter_yellow);
                    simpleHighLight(close, 1, this.highlightPainter_yellow);
                }
            }
        }else{
            //si seleccionó algo...

            //System.out.println(this.textPane.getSelectedTextColor());
            //this.textPane.setSelectedTextColor(Color.magenta);
            //this.textPane.setSelectionColor(Color.red);

        }
    }

    /*****************************************
    * getIndexClose
    * @return int with the index
    *****************************************/
    private int getIndexClose(int start, char carac){

        int retorno = -1;
        if(carac == '{' || carac == '('){
            retorno = indexOfCloseParentesis(start, carac);
        }else if(carac == '}' || carac == ')'){
            retorno = indexOfOpenParentesis(start, carac);
        }
        return retorno;
    }

    /*****************************************
    * indexOfOpenParentesis
    * @param start
    * @param carac
    *****************************************/
    private int indexOfOpenParentesis(int start, char carac){
        //}
        int up = -1;
        char open = '}', close = '{';
        if(carac == '}'){
            open = '}'; close = '{';
        }else if (carac == ')'){
            open = ')'; close = '(';
        }

        String text = this.textPane.getText().substring(0,start);

        int retorno = text.length()-1;

        int contador = 1;

        while(contador>0){
            if(retorno>text.length() || retorno<0){
                retorno = -2;
                break;
            }
            if(text.charAt(retorno)==open){
                contador++;
            }else if(text.charAt(retorno)==close){
                contador--;
            }
            retorno+=up;
        }
        retorno-=up;
        return retorno;
    }

    /*****************************************
    * indexOfCloseParentesis
    * @param start
    * @param carac
    *****************************************/
    private int indexOfCloseParentesis(int start, char carac){
        //{
        int up = 1;
        char open = '{', close = '}';
        if(carac == '{'){
            open = '{'; close = '}';
        }else if (carac == '('){
            open = '('; close = ')';
        }
        String text = this.textPane.getText().substring(start);
        int retorno = 1;
        int contador = 1;
        while(contador>0){
            if(retorno>=text.length() || retorno<0){
                retorno = -1;
                break;
            }
            if(text.charAt(retorno)==open){
                contador++;
            }else if(text.charAt(retorno)==close){
                contador--;
            }
            retorno+=up;
        }
        if(retorno == -1){
            return -1;
        }
        retorno-=up;
        return start+retorno;
    }



    /*****************************************
    * myDocumentListener
    * if the document is ever edited, assume
    * that it needs to be saved
    *****************************************/
    public class myDocumentListener implements DocumentListener{

        @Override
        public void insertUpdate(DocumentEvent de) {
            //System.out.println("insertUpdate");

            if(activar_timer == true){

            }else{
                inserto = true;
                quito = false;
                removeHighlights();
                //Main.frame.setModified(true);
                activar_timer = true;
                timer.start();
            }
        }

        @Override
        public void removeUpdate(DocumentEvent de) {
            //System.out.println("remove Update");
            if(activar_timer == true){

            }else{
                inserto = false;
                quito = true;
                removeHighlights();
                //Main.frame.setModified(true);
                activar_timer = true;
                timer.start();
            }
        }

        @Override
        public void changedUpdate(DocumentEvent de) {
            //System.out.println("System.changedUpdate");
            //if(activar_timer == true){

            //}else{
                removeHighlights();
                //Main.frame.setModified(true);
                //activar_timer = true;
            //}
        }
    }

    /*****************************************
    * @param text
    *****************************************/
    public void setText(String text){

        activar_timer = true;

        this.textPane.setText(text);

        this.pos_selected_start = 0;
        this.pos_selected_end = 0;
        this.pos_selected_txt = text;

        changePosSelected();
        changeComments();

        this.textPane.setSelectionStart(0);
        this.textPane.setSelectionEnd(0);

        activar_timer = false;
    }

    /*****************************************
    * @return String
    *****************************************/
    public String getText(){
        return this.textPane.getText();
    }

    public void highlight(String pattern){
        JTextComponent textComp = this.textPane;
        //First remove all old highlights
        removeHighlights();
        try{
            Highlighter hilite = textComp.getHighlighter();
            Document doc = textComp.getDocument();
            String text = doc.getText(0, doc.getLength());
            int pos = 0;

            //Search for pattern
            while ((pos = text.indexOf(pattern, pos)) >= 0) {
                // Create highlighter using private painter and apply around pattern
                hilite.addHighlight(pos, pos+pattern.length(), this.highlightPainter_yellow);
                pos += pattern.length();
            }

        }catch(Exception exp){

        }
    }

    /*****************************************
    * removeHighlights
    * remove all high lights from the pane
    *****************************************/
    public void removeHighlights() {
        JTextComponent textComp = this.textPane;
        Highlighter hilite = textComp.getHighlighter();
        Highlighter.Highlight[] hilites = hilite.getHighlights();
        for (Highlighter.Highlight hilite1 : hilites) {
            if (hilite1.getPainter() instanceof MyHighlightPainter) {
                hilite.removeHighlight(hilite1);
            }
        }
    }

    MyHighlightPainter highlightPainter_yellow = new MyHighlightPainter(Color.yellow);
    MyHighlightPainter highlightPainter_red = new MyHighlightPainter(Color.red);

    // A private subclass of the default highlight painter
    class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
        public MyHighlightPainter(Color color) { super(color); }
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