/**
* Universidad del Valle de Guatemala
* Pablo Diaz 13203
* Para poder ser ejecutado este programa depende de 2 JARS
* ANTRL 4
* y JSCH
*/

package compiler;

import gui.ANTGui;
import java.util.ArrayList;
/**
 *
 * @author Pablo
 */
public class Antlr4 {

    public static ArrayList<String> mensajeError = new ArrayList();
    public static String erroresAcum = "";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
       ANTGui window = new ANTGui();
       window.setExtendedState(ANTGui.MAXIMIZED_BOTH);
       window.setVisible(true);
       window.setLocationRelativeTo(null);
      
       
    }
    
     

}
