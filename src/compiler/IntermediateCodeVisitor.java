/**
* Universidad Del Valle de Guatemala
* 20-mar-2016
* Pablo Díaz 13203
*/

package compiler;

import antlr4.programBaseVisitor;
import antlr4.programParser;
import gui.ANTGui;
import java.util.Stack;

/**
 *
 * @author Pablo
 * @param <T>
 */
public class IntermediateCodeVisitor <T> extends programBaseVisitor {

    private final InterCodeTable tablaCodigo = new InterCodeTable();
    int contadorTemps = 0;
    Stack stack = new Stack();

    @Override
    public Object visitProgram(programParser.ProgramContext ctx) {
       for (int i = 0;i<ctx.getChildCount();i++){
           this.visit(ctx.getChild(i));
        }
       
        tablaCodigo.printTable();
        ANTGui.jTextIntermediate.setText(tablaCodigo.toString());
        return ""; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    @Override
    public Object visitStatementLocation(programParser.StatementLocationContext ctx) {
        IntermediateCode codigo = new IntermediateCode();
        
        codigo.setRes((String)visit(ctx.getChild(0)));
        T returnValue = (T)visit(ctx.getChild(2));
        String dir1;
        if (returnValue instanceof IntermediateCode ){
            dir1 =((IntermediateCode) returnValue).getRes();
        }
        else{
            dir1 = (String)returnValue;
        }
        codigo.setDir1(dir1);
        codigo.setOp("=");
        tablaCodigo.addCode(codigo);
        
      
        
        return ""; //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public Object visitMethodDeclaration(programParser.MethodDeclarationContext ctx) {
        IntermediateCode codigo = new IntermediateCode();
        String etiqueta = ctx.getChild(1).getText();

        codigo.setEtiqueta(etiqueta+":");
        tablaCodigo.addCode(codigo);
        return super.visitMethodDeclaration(ctx); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitAddExprMinusPlusOp(programParser.AddExprMinusPlusOpContext ctx) {
        String opTemp = "";
        String opTemp2 = "";
        Stack<String> args = new Stack();
        int count = 0;
        for (int i = 0;i<ctx.getChildCount();i++){
           T var = (T) this.visit(ctx.getChild(i));
           if (var instanceof IntermediateCode){
               count++;
               args.push(((IntermediateCode)var).getRes());
              
           }
        }
        String op1 = "";
        if (count > 1){
            opTemp = args.pop();
            op1= opTemp;
        }
        if (opTemp.isEmpty()){
            op1 = (String)this.visit(ctx.getChild(0));
        } 
       
        String op2="";
        if (count >= 2){
            opTemp2 = args.pop();
            op2= opTemp2;
        }
        if (opTemp.isEmpty()){
            op2 = (String)this.visit(ctx.getChild(0));
        } 
       
        IntermediateCode codigo = new IntermediateCode();
        codigo.setDir1(op1);
        codigo.setDir2(op2);
        codigo.setOp(ctx.getChild(1).getText());
        codigo.setRes("temp"+this.contadorTemps++);
        this.tablaCodigo.addCode(codigo);
        
        
        return codigo; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitMultExprMultDivOp(programParser.MultExprMultDivOpContext ctx) {
         String opTemp = "";
        for (int i = 0;i<ctx.getChildCount();i++){
           T var = (T) this.visit(ctx.getChild(i));
           if (var instanceof IntermediateCode){
              opTemp = ((IntermediateCode)var).getRes();
           }
        }
        String op1 = opTemp;
        if (opTemp.isEmpty())
            op1 = (String)this.visit(ctx.getChild(0));
        String op2 = (String)this.visit(ctx.getChild(2));
        
        IntermediateCode codigo = new IntermediateCode();
        codigo.setDir1(op1);
        codigo.setDir2(op2);
        codigo.setOp(ctx.getChild(1).getText());
        codigo.setRes("temp"+this.contadorTemps++);
        this.tablaCodigo.addCode(codigo);
        
        
        return codigo;
    }

    
    
    @Override
    public Object visitLiteral(programParser.LiteralContext ctx) {
       return ctx.getChild(0).getText();// change body of generated methods, choose Tools | Templates.
    }
    
    

}
