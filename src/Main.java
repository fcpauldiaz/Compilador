/***********************************
* Name: Main.java
* Date: Aug 17, 2010
* @author martin
* Description:
***********************************/

import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;


public class Main {

    //************************* Icons *************************
    public static ImageIcon iconNew = new ImageIcon("src/resources/new.png");
    public static ImageIcon iconOpen = new ImageIcon("src/resources/open.png");
    public static ImageIcon iconClose = new ImageIcon("src/resources/close.png");
    public static ImageIcon iconMiniClose = new ImageIcon("src/resources/mini_close.gif");
    public static ImageIcon iconSave = new ImageIcon("src/resources/save.png");
    public static ImageIcon iconSaveAs = new ImageIcon("src/resources/saveAs.png");
    //*********************************************************
    public static ImageIcon iconCopy = new ImageIcon("src/resources/copy.png");
    public static ImageIcon iconPaste = new ImageIcon("src/resources/paste.png");
    public static ImageIcon iconCut = new ImageIcon("src/resources/cut.png");
    //*********************************************************
    public static ImageIcon iconParse = new ImageIcon("src/resources/play.png");
    //*********************************************************
    public static ImageIcon iconTree = new ImageIcon("src/resources/tree_icon.gif");
    public static ImageIcon iconConstruction = new ImageIcon("src/resources/icon_construction.gif");
    public static ImageIcon iconSearch = new ImageIcon("src/resources/search-button.png");
    //*********************************************************
    public static ToolbarFrame frame;
    //*********************************************************
    public static Font font = new Font("monospaced", Font.PLAIN, 12);
    //*********************************************************

    /*****************************************
     * @param args the command line arguments
     *****************************************/
    public static void main(String[] args) {

        frame = new ToolbarFrame();
    }

    /***********************************************
     * readFile:
     * reads the complete file
     * @param path to the file you want to read
     * @return String with all the file string
    ***********************************************/
    public static String readFile(String path) {
        String sCadena ="", archivo="";

        try{
            FileReader fr = new FileReader(path);
            BufferedReader bf = new BufferedReader(fr);
            while ((sCadena = bf.readLine())!=null) {
                archivo+=sCadena+"\n";
            }
            bf.close();
            fr.close();
        }catch(Exception exception){
            System.out.println("Error reading "+path);
            return "";
        }
        return archivo;
    }

    /***********************************************
     * expect
     * Reviews if all in the chain match with the regex
     * @param regex
     * @param chain
     * @return String
    ***********************************************/
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
