/**
* Universidad Del Valle de Guatemala
* 25-ene-2016
* Pablo Díaz 13203
*/

package compiler;


import GUI.ToolbarFrame;
import static GUI.ToolbarFrame.outputTextPane;
import static compiler.ANTGui.jTextArea3;
import java.awt.Color;
import java.awt.Container;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
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
       // String anterior = MainPanel.outputPanel.getTextPane().getText();
        //MainPanel.outputPanel.setTextPane(anterior + mensaje);
        //OutputStream redirect = System.err;
	//PrintStream myPrintStream = new PrintStream(redirect);
	
        /*SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setForeground(set, Color.red);
        ToolbarFrame.outputTextPane.setCharacterAttributes(set, true);*/
       
       agregarLog("Un error inesperado ha ocurrido " +"\n" + mensaje, line, charPositionInLine);
        
        
        //**ToolbarFrame************** escribe la palabra error con un estilo diferente *******
        /*StyleConstants.setBold(set, true);
        String texto_error = "\nAn error has occurred. ¡Check the log!";
        mensaje += ToolbarFrame.outputTextPane.getText()+"\r\n";
            Document doc = outputTextPane.getStyledDocument();
            outputTextPane.setCharacterAttributes(set, true);

            try {
                doc.insertString(doc.getLength(), texto_error, set);
            } catch (BadLocationException ex) {
                //Logger.getLogger(DocumentEditorView.class.getName()).log(Level.SEVERE, null, ex);
            }
             ToolbarFrame.outputTextPane.setText(mensaje);*/
    }
    
     public static void agregarLog(String mensaje, int linea, int columna){
        
        jTextArea3.setText(jTextArea3.getText()+"\n"+
                "linea: " + linea +": "+ columna +  " " + mensaje
                );
        
    }
}