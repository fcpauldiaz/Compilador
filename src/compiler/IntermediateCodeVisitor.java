/**
* Universidad Del Valle de Guatemala
* 20-mar-2016
* Pablo Díaz 13203
*/

package compiler;

import antlr4.programBaseVisitor;
import antlr4.programParser;
import gui.ANTGui;
import java.util.ArrayList;
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
    private ArrayList<StackControl> stackControl;

    public IntermediateCodeVisitor() {
        this.stackControl = new ArrayList();
        this.globalStack = new Stack();
        Scope.setAmbitoActual(0);
        this.scopeActual = new Scope();
        
    }
    
    

    @Override
    public Object visitProgram(programParser.ProgramContext ctx) {
       for (int i = 0;i<ctx.getChildCount();i++){
           this.visit(ctx.getChild(i));
        }
       
        tablaCodigo.printTable();
        ANTGui.jTextIntermediate.clear();
        ANTGui.jTextARM.clear();
        ANTGui.jTextIntermediate.setText(tablaCodigo.toString());
        System.out.println(this.stackControl);
       
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
           
        }else{
           res = "stack["+this.buscarStack(res)+"]";
        }
        T returnValue = (T)visit(ctx.getChild(2));
        String dir1;
        if (returnValue instanceof IntermediateCode ){
            dir1 =((IntermediateCode) returnValue).getRes();
        }
        else{
            dir1 = (String)returnValue;
        }
        if (!dir1.contains("\'")&&!dir1.contains("temp")){
            try {
                int num = Integer.parseInt(dir1);
            }catch(Exception e){
                boolean glbl2 = tablaCodigo.searchGlobalSymbol(dir1);
                if (glbl2){
                       IntermediateCode code = this.tablaCodigo.searchCodeGlobal(dir1);
                       dir1 = code.getEtiqueta();
                }
                else { 
                    int pos = this.buscarStack(dir1);
                    dir1 = "stack[" + pos +"]";
                }
            }
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
        this.contadorTemps= 0;
        return ""; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitValueMethodCall(programParser.ValueMethodCallContext ctx) {
        String nombreMetodo = ctx.getChild(0).getText();
        return super.visitValueMethodCall(ctx); //To change body of generated methods, choose Tools | Templates.
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
        IntermediateCode codigo = new IntermediateCode();
        if (scopeActual.getIdScope() == 0) {
           
            codigo.setEtiqueta(ctx.getChild(1).getText()+"_global");
            codigo.setGlobal(true);
            tablaCodigo.addCode(codigo);
            return super.visitVarDeclarationID(ctx);
        }
        String tipo = ctx.getChild(0).getText();
        String identificador = ctx.getChild(1).getText();
        if (tipo.equals("int")){
            this.stackControl.add(new StackControl(4, identificador, tipo));
            
        }
        else if (tipo.equals("char")){
            this.stackControl.add(new StackControl(1, identificador, tipo));
        }
        else if (tipo.equals("boolean")){
             this.stackControl.add(new StackControl(4, identificador, tipo));
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
        StackControl.staticPos = 0;
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
        
        Scope scopeIF = new Scope();
        
        scopeActual.addSiguiente(scopeIF);
        scopeIF.setAnterior(scopeActual);
        scopeActual = scopeIF;
        
        
        codigo.setDir1(midCode.getRes());
        codigo.setDir2(etiquetaActual+contEtiquetaActual++);
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
        scopeActual = scopeActual.getAnterior();
        return ""; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitVarDeclarationArray(programParser.VarDeclarationArrayContext ctx) {
        
        String tipo = ctx.getChild(0).getText();
        String identificador = ctx.getChild(1).getText();
        int tamaño = Integer.parseInt(ctx.getChild(3).getText());
        
         IntermediateCode codigo = new IntermediateCode();
        if (scopeActual.getIdScope() == 0) {
           
            codigo.setEtiqueta(ctx.getChild(1).getText()+"_global");
            codigo.setGlobal(true);
            tablaCodigo.addCode(codigo);
            return super.visitVarDeclarationArray(ctx);
        }
       
     
        if (tipo.equals("int")){
            this.stackControl.add(new StackControl(4*tamaño, identificador, tipo));
            
        }
        else if (tipo.equals("char")){
            this.stackControl.add(new StackControl(1*tamaño, identificador, tipo));
        }
        else if (tipo.equals("boolean")){
             this.stackControl.add(new StackControl(4*tamaño, identificador, tipo));
        }
      
        
        return super.visitVarDeclarationArray(ctx); //To change body of generated methods, choose Tools | Templates.
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
         if (ctx.getChildCount()>1){
        String dir1 = ((IntermediateCode)visit(ctx.getChild(0))).getRes();
        String dir2 = ((IntermediateCode)visit(ctx.getChild(2))).getRes();
        try{
            int test = Integer.parseInt(dir1);
        }catch(Exception e){
            int amb = tablaCodigo.searchSymbolLastScope(dir1).getAmbito();
            if (amb==0)
               dir1+="_"+"_"+amb;
            else
                dir1+="_"+etiquetaActual+"_"+amb;
        }
        try{
            int test = Integer.parseInt(dir2);
        }catch(Exception e){
            int amb = tablaCodigo.searchSymbolLastScope(dir2).getAmbito();
            if (amb==0)
               dir2+="_"+"_"+amb;
            else
                dir2+="_"+etiquetaActual+"_"+amb;
        }
        String op = ctx.getChild(1).getText();
        IntermediateCode codigo = new IntermediateCode();
        codigo.setDir1(dir1);
        codigo.setDir2(dir2);
        codigo.setOp(op);
        codigo.setRes("temp"+this.contadorTemps);
        
        return codigo; //To change body of generated methods, choose Tools | Templates.
        }
        return super.visitEqExprEqOp(ctx);
      
    }

    @Override
    public Object visitRelationExpr(programParser.RelationExprContext ctx) {
        if (ctx.getChildCount()>1){
        String dir1 = ((IntermediateCode)visit(ctx.getChild(0))).getRes();
        String dir2 = ((IntermediateCode)visit(ctx.getChild(2))).getRes();
        try{
            int test = Integer.parseInt(dir1);
        }catch(Exception e){
            dir1+="_"+etiquetaActual+"_"+scopeActual.getIdScope();
        }
        try{
            int test = Integer.parseInt(dir2);
        }catch(Exception e){
            dir2+="_"+etiquetaActual+"_"+scopeActual.getIdScope();
        }
        
        
       
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

    public InterCodeTable getTablaCodigo() {
        return tablaCodigo;
    }
    
    
   public int buscarStack(String identificador){
       for(StackControl control: this.stackControl){
           if (control.getIdentificador().equals(identificador)){
               return control.getPosicion();
           }
       }
       return -1;
   }

}
