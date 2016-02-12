/**
* Universidad del Valle de Guatemala
* Pablo Diaz 13203
*/

package antlr4;

import java.util.ArrayList;
import org.antlr.v4.Tool;

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
        // TODO code application logic here
     
       /*FileInputStream fis = new FileInputStream("Program.txt");
        CharStream cs =  new ANTLRInputStream(fis);
       
       programLexer lexer = new programLexer(cs);
       
       CommonTokenStream tokens = new CommonTokenStream( lexer);
       programParser parser = new programParser(tokens);
       programParser.ProgramContext result = parser.program();
       //    System.out.println(tokens.getTokens().toString());
      // System.out.println(parser.program().getText());
      
       
   
 
        // Specify our entry point
        programParser.ProgramContext contexto = parser.program();

        // Walk it and attach our listener
        ParseTreeWalker walker = new ParseTreeWalker();
        ANTLRListener listener = new ANTLRListener();
        walker.DEFAULT.walk(listener, contexto);
        Trees.inspect(contexto, parser);

        System.out.println(contexto.toStringTree(parser));

        //show AST in GUI
        JFrame frame = new JFrame("Antlr AST");
        JPanel panel = new JPanel();
        TreeViewer viewr = new TreeViewer(Arrays.asList(
                parser.getRuleNames()),contexto);
       
        viewr.setScale(1.5);//scale a little
        panel.add(viewr);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200,200);
        frame.setVisible(true);*/
        
        //
      
       ANTGui window = new ANTGui();
       window.setExtendedState(ANTGui.MAXIMIZED_BOTH);
       window.setVisible(true);
       window.setLocationRelativeTo(null);
      
       
    }
    
     

}
