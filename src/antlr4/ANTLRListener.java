/**
* Universidad Del Valle de Guatemala
* 24-ene-2016
* Pablo Díaz 13203
*/

package antlr4;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;


/**
 *
 * @author Pablo
 */
public class ANTLRListener extends programBaseListener{
    
    private String stringTree;
    
    @Override
    public void enterProgram(programParser.ProgramContext ctx) {
        //System.out.println(ctx.toStringTree());
        //System.out.println(ctx.getStart());
        //System.out.println(ctx.toString());
        stringTree = ctx.toString();
        
        
    }
    
    @Override
    public void visitErrorNode(ErrorNode node) { 
        System.out.println(node.toStringTree());
        
    
    }
    public String getStringTree() {
        return stringTree;
    }
    
  

}
