/**
* Universidad Del Valle de Guatemala
* 25-ene-2016
* Pablo Díaz 13203
*/

package antlr4;

import java.awt.Color;
import java.awt.Container;
import java.util.Collections;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
        
        List<String> stack = ((Parser)recognizer).getRuleInvocationStack();
        Collections.reverse(stack);
        StringBuilder buf = new StringBuilder();
        buf.append("<html>");
        buf.append("reglas afectadas: ").append(stack).append("<br>");
        buf.append("línea ").append(line).append(":").append(charPositionInLine).append(" en ").append(offendingSymbol).append(": ").append(msg).append(System.getProperty("line.separator"));
        buf.append("</html>");
        JDialog dialog = new JDialog();
        dialog.setSize(600,100);
        Container contentPane = dialog.getContentPane(); 
        contentPane.setSize(600,100);
        JLabel label = new JLabel(buf.toString());
        label.setSize(600,100);
        contentPane.add(label); 
        contentPane.setBackground(Color.white); dialog.setTitle("Error de Sintaxis");
        dialog.setLocationRelativeTo(null); 
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        dialog.setVisible(true);

       
    }
}