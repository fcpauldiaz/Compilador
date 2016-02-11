/**
* Universidad Del Valle de Guatemala
* 25-ene-2016
* Pablo Díaz 13203
*/

package antlr4;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 *
 * @author Pablo
 */
public class DescriptiveErrorListener extends BaseErrorListener {
    public static DescriptiveErrorListener INSTANCE = new DescriptiveErrorListener();
    public static String mensaje;
    public static int linea;
    public static int columna;
    

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg, RecognitionException e)
    {
        

        String sourceName = recognizer.getInputStream().getSourceName();
        if (!sourceName.isEmpty()) {
            sourceName = String.format("%s:%d:%d: ", sourceName, line, charPositionInLine);
        }
        mensaje= msg;
        linea=line;
        columna=charPositionInLine;
        System.err.println(sourceName+"linea "+line+":"+charPositionInLine+" "+msg);
    }
}