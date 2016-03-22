/**
* Universidad Del Valle de Guatemala
* 20-mar-2016
* Pablo Díaz 13203
*/

package compiler;

import antlr4.programBaseVisitor;
import antlr4.programParser;

/**
 *
 * @author Pablo
 * @param <T>
 */
public class IntermediateCodeVisitor <T> extends programBaseVisitor {

    private final InterCodeTable tablaCodigo = new InterCodeTable();
    
    @Override
    public Object visitStatementLocation(programParser.StatementLocationContext ctx) {
        IntermediateCode codigo = new IntermediateCode();
        codigo.setDir1((String)visit(ctx.getChild(0)));
        codigo.setDir2((String)visit(ctx.getChild(2)));
        codigo.setTipo("assignment");
        tablaCodigo.addCode(codigo);
        System.out.println(tablaCodigo.toString());
        return super.visitStatementLocation(ctx); //To change body of generated methods, choose Tools | Templates.
    }
    
     @Override
    public T visitLocation(programParser.LocationContext ctx) {
        for (int i = 0;i<ctx.getChildCount();i++){
            T var = (T)this.visit(ctx.getChild(i));
            if (i==1){
                return var;
            }
        }
        
        
        return (T)ctx.getChild(0).getText(); //To change body of generated methods, choose Tools | Templates.
    }
    
       

}
