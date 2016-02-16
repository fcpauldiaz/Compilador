/**
* Universidad Del Valle de Guatemala
* 12-feb-2016
* Pablo Díaz 13203
*/

package compiler;

import antlr4.programBaseVisitor;
import antlr4.programParser;


/**
 *
 * @author Pablo
 */
public class Visitor<T> extends programBaseVisitor {
    
    private int ambito;
    
    @Override 
    public T visitProgram(programParser.ProgramContext ctx) {
        System.out.println("YOLO: " + ctx.getText());
        System.out.println(ctx.getChildCount());
        System.out.println(ctx.start);
        System.out.println(ctx.start.getText());
        System.out.println(ctx.start.getType());
        for (int i = 0 ;i<11;i++){
            
        }
        System.out.println("ctx" + ctx);
        this.visit(ctx.declaration(0));
       ambito=0;
        //System.out.println(ctx.getChild(1));
        //System.out.println(ctx.toString());
        //System.out.println(ctx.toStringTree());
        return (T)"";
    }
    
    @Override
    public T visitStructDeclaration(programParser.StructDeclarationContext ctx){
        
        System.out.println(ctx.toString());
        System.out.println("xg");
        return (T) "s";
    }
}
