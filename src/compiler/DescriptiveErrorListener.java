/**
* Universidad Del Valle de Guatemala
* 25-ene-2016
* Pablo Díaz 13203
*/

package compiler;



import static compiler.ANTGui.jTextArea3;
import java.awt.Color;
import java.util.Collections;
import java.util.List;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 *
 * @author Pablo
 */
public class DescriptiveErrorListener extends BaseErrorListener {
    public static DescriptiveErrorListener INSTANCE = new DescriptiveErrorListener();
   
    

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg, RecognitionException e)
    {
        
       
       List<String> stack = ((Parser)recognizer).getRuleInvocationStack(); Collections.reverse(stack);
        System.err.println("rule stack: "+stack);
        System.err.println("linea "+line+":"+charPositionInLine+" at "+
        offendingSymbol+": "+msg);
        String rule = "rule stack: "+stack;
        String mensaje = "linea "+line+":"+charPositionInLine+" at "+
        offendingSymbol+": "+msg + "\n\r";
       
       
       agregarLog("Un error inesperado ha ocurrido " +"\n" + mensaje, line, charPositionInLine,true);
        
        
    }
    public void agregarLog(String mensaje, int linea, int columna, boolean error){
        
        StyledDocument doc = jTextArea3.getStyledDocument();

        Style style = jTextArea3.addStyle("I'm a Style", null);
        StyleConstants.setForeground(style, Color.red);
        
        if (error){
            try { doc.insertString(doc.getLength(), "linea: " + linea +": "+ columna +  " " + mensaje+"\n",style); }
            catch (BadLocationException e){}
        }
        else{
            StyleConstants.setForeground(style, Color.blue);
            try { doc.insertString(doc.getLength(), "linea: " + linea +": "+ columna +  " " + mensaje+"\n",style); }
            catch (BadLocationException e){}
        }
        
       
        
    }
}