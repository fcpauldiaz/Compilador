/**
* Universidad Del Valle de Guatemala
* 12-feb-2016
* Pablo Díaz 13203
*/

package compiler;

import antlr4.programBaseVisitor;
import antlr4.programParser;
import org.antlr.v4.runtime.tree.ParseTreeProperty;


/**
 *
 * @author Pablo
 */
public class Visitor<T> extends programBaseVisitor {
    
    private int ambito;
    ParseTreeProperty<T> values = new ParseTreeProperty<T>();
    @Override 
    public T visitProgram(programParser.ProgramContext ctx) {
        System.out.println("YOLO: " + ctx.getText());
        System.out.println(ctx.getChildCount());
       
       for (int i = 0;i<ctx.getChildCount();i++){
           System.out.println(ctx.getChild(i).getText());
           this.visit(ctx.getChild(i));
       }
       
        //this.visit(ctx.);
        ambito=0;
        
        return (T)"";
    }
    
    @Override
    public T visitStructDeclaration(programParser.StructDeclarationContext ctx){
        
        
        return (T) "s";
    }
}
