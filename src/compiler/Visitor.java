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
    
    private int ambito=0;
    private final SymbolTable tablaSimbolos;
    public int autoincrement = 0;

    public Visitor() {
        this.tablaSimbolos = new SymbolTable();
    }
    @Override 
    public T visitProgram(programParser.ProgramContext ctx) {
        System.out.println("YOLO: " + ctx.getText());
        System.out.println(ctx.getChildCount());
       
       for (int i = 0;i<ctx.getChildCount();i++){
           System.out.println(ctx.getChild(i).getText());
           this.visit(ctx.getChild(i));
       }
       
        
        
        tablaSimbolos.printSymbolTable();
        return (T)"";
    }
    
    @Override
    public T visitStructDeclaration(programParser.StructDeclarationContext ctx){
        
        
        return (T) "s";
    }
    
    @Override
    public T visitDeclaration(programParser.DeclarationContext ctx){
        
        
        
        return (T)"T";
    }
    
    @Override
    public T visitVarDeclarationID(programParser.VarDeclarationIDContext ctx){
        System.out.println(ctx.getChildCount()+"cantidad var declaration");
        System.out.println(ctx.toStringTree());
        
        Symbol simbolo = new Symbol();
        //ambito,id
        Type tipo = new Type();
        tipo.setNombre(ctx.getChild(0).getText());
        tipo.setNombre_tipo((String)this.visit(ctx.getChild(0)));
        simbolo.setAmbito(ambito);
        simbolo.setId(++autoincrement);
        
       
        
        return (T)simbolo;
    }
    
    @Override
    public T visitVarType(programParser.VarTypeContext ctx){
            
        return (T)ctx.getText();
    }
}
