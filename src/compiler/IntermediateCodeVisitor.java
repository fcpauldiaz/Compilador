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
    private int contadorTemps = 0;
    private Stack globalStack;
    private Scope scopeActual;
    private String etiquetaActual = "";
    private int contEtiquetaActual = 0;
    private String actualOp = "";
    private IntermediateCode last;
    private IntermediateCode elseInt;

    public IntermediateCodeVisitor() {
        this.globalStack = new Stack();
        this.scopeActual = new Scope();
        this.scopeActual.setIdScope(0);
    }
    
    

    @Override
    public Object visitProgram(programParser.ProgramContext ctx) {
       for (int i = 0;i<ctx.getChildCount();i++){
           this.visit(ctx.getChild(i));
        }
       
        tablaCodigo.printTable();
         ANTGui.jTextIntermediate.clear();
        
        ANTGui.jTextIntermediate.setText(tablaCodigo.toString());
      
       
        return ""; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    @Override
    public Object visitStatementLocation(programParser.StatementLocationContext ctx) {
        IntermediateCode codigo = new IntermediateCode();
        String res = ((String)visit(ctx.getChild(0)));
        boolean glbl = tablaCodigo.searchGlobalSymbol(res);
        if (glbl){
            IntermediateCode code = this.tablaCodigo.searchCodeGlobal(res);
            res = code.getEtiqueta();
        }
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
        
        //Symbol simbolo = tablaCodigo.searchSymbolLastScope(res);
        //System.out.println("jajaja" + simbolo.getAmbito());
        //if (simbolo.getAmbito()==0){
            //res = "global_"+res;
       // }
        codigo.setRes(res);
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
    public Object visitVarDeclarationID(programParser.VarDeclarationIDContext ctx){
        //System.out.println(ctx.getChildCount()+"cantidad var declaration");
        
        if (scopeActual.getIdScope() == 0) {
            IntermediateCode codigo = new IntermediateCode();
            codigo.setEtiqueta(ctx.getChild(1).getText()+"_global");
            codigo.setGlobal(true);
            tablaCodigo.addCode(codigo);
        }
       
        
        return super.visitVarDeclarationID(ctx);
    }
    
     
    @Override
    public Object visitMethodDeclaration(programParser.MethodDeclarationContext ctx) {
        Scope scopeMethod = new Scope();
        
        scopeActual.addSiguiente(scopeMethod);
        scopeMethod.setAnterior(scopeActual);
        scopeActual = scopeMethod;
        
        IntermediateCode codigo = new IntermediateCode();
        String etiqueta = ctx.getChild(1).getText();
        etiquetaActual = etiqueta;
        contEtiquetaActual = 0;

        codigo.setEtiqueta(etiqueta+":");
        tablaCodigo.addCode(codigo);
         super.visitMethodDeclaration(ctx);
        scopeActual = scopeActual.getAnterior();
        return "";//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitAddExprMinusPlusOp(programParser.AddExprMinusPlusOpContext ctx) {
        String opTemp = "";
        String opTemp2;
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
            op2 = (String)this.visit(ctx.getChild(2));
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

    @Override
    public Object visitStatementIF(programParser.StatementIFContext ctx) {
       
        IntermediateCode etiqueta1 = new IntermediateCode();
         IntermediateCode etiqueta2 = new IntermediateCode();
        IntermediateCode codigo = new IntermediateCode();
        codigo.setStatementIF(true);
        etiqueta1.setEtiqueta(etiquetaActual+contEtiquetaActual+":");
       
        System.out.println(actualOp);
        IntermediateCode midCode = (IntermediateCode)this.visit(ctx.getChild(2));
        tablaCodigo.addCode(midCode);
        codigo.setRes("ifFalse");
        
        codigo.setDir1(midCode.getRes());
        codigo.setDir2(etiquetaActual+contEtiquetaActual+++":");
        codigo.setOp("GOTO");
        
        tablaCodigo.addCode(codigo);
        last = etiqueta1;
      
        if (ctx.getChildCount() > 5){
            IntermediateCode gotoElse = new IntermediateCode();
            gotoElse.setOp("GOTO");
            gotoElse.setDir2(etiquetaActual+contEtiquetaActual);
            elseInt = gotoElse;
        }
        
        this.visit(ctx.getChild(4));
         
        
       if (ctx.getChildCount() > 5){
         
            this.visit(ctx.getChild(5));
            etiqueta2.setEtiqueta(etiquetaActual+contEtiquetaActual+++":");
            tablaCodigo.addCode(etiqueta2);
       }
         
        return ""; //To change body of generated methods, choose Tools | Templates.
    }
  @Override
    public Object visitBlock(programParser.BlockContext ctx) {
        super.visitBlock(ctx); 
        if (elseInt != null){
            this.tablaCodigo.addCode(elseInt);
            elseInt = null;
        }
        if (last!=null){
            this.tablaCodigo.addCode(last);
            last = null;
        }
        return "";//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitEqExprEqOp(programParser.EqExprEqOpContext ctx) {
        actualOp = ctx.getChild(1).getChild(0).getText();
        return super.visitEqExprEqOp(ctx); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitRelationExpr(programParser.RelationExprContext ctx) {
        if (ctx.getChildCount()>1){
        String dir1 = ((IntermediateCode)visit(ctx.getChild(0))).getRes();
        String dir2 = ((IntermediateCode)visit(ctx.getChild(2))).getRes();
        String op = ctx.getChild(1).getText();
        IntermediateCode codigo = new IntermediateCode();
        codigo.setDir1(dir1);
        codigo.setDir2(dir2);
        codigo.setOp(op);
        codigo.setRes("temp"+this.contadorTemps);
        
        return codigo; //To change body of generated methods, choose Tools | Templates.
        }
        return super.visitRelationExpr(ctx);
    }
    
    

    @Override
    public Object visitValueLocation(programParser.ValueLocationContext ctx) {
        IntermediateCode codigo = new IntermediateCode();
        codigo.setRes(ctx.getChild(0).getText());
        return codigo; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitValueLiteral(programParser.ValueLiteralContext ctx) {
          IntermediateCode codigo = new IntermediateCode();
        codigo.setRes(ctx.getChild(0).getChild(0).getText());
        return codigo; //To change body of generated methods, choose Tools | Templates.
    }
    
    
   

}
