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
import java.util.Collection;
import java.util.Stack;

/**
 *
 * @author Pablo
 * @param <T>
 */
public class IntermediateCodeVisitor <T> extends programBaseVisitor {

    private final InterCodeTable tablaCodigo = new InterCodeTable();
    private int contadorTemps = 0;
    private Scope scopeActual;
    private String etiquetaActual = "";
    private int contEtiquetaActual = 0;
    private String actualOp = "";
    private IntermediateCode last;
    private IntermediateCode elseInt;
    private ArrayList<StackControl> stackControl;
    private ArrayList checkArray = new ArrayList();
    private ArrayList exprOps = new ArrayList();
    
    public IntermediateCodeVisitor() {
        this.stackControl = new ArrayList();
        Scope.setAmbitoActual(0);
        this.scopeActual = new Scope();
        checkArray.add("true");
        checkArray.add("false");
        
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
        else if (returnValue instanceof ArrayList){
           
            if (((ArrayList)returnValue).get(0) instanceof String){
                dir1 = ((String)((ArrayList)returnValue).get(0));
            }
            else{
                dir1 = ((IntermediateCode)((ArrayList)returnValue).get(0)).getRes();
            }

        }
        else {
            dir1 = (String)returnValue;
        }
        if (!dir1.contains("\'")&&!dir1.contains("temp")&&!checkArray.contains(dir1)&&!dir1.contains("[")&&!dir1.contains("global")){
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
        //en caso de las variables booleanas
        //1 = true
        //0 = false
        if (checkArray.contains(dir1)){
            if (dir1.equals("true")){
                dir1 = "1";
            }
            else{
                dir1 = "0";
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
    public Object visitLocationArray(programParser.LocationArrayContext ctx) {
        
        String nombreVar = ctx.getChild(0).getText();
        int locationSave = Integer.parseInt(ctx.getChild(2).getText());
        ArrayList returnArray = new ArrayList();
        returnArray.add(nombreVar);
        returnArray.add(locationSave);
        return returnArray; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitStatementLocationArray(programParser.StatementLocationArrayContext ctx) {
        
        String res = "";
        ArrayList locArray = (ArrayList)visit(ctx.getChild(0));
        String nombreVar = (String)locArray.get(0);
        int posArray = (int)locArray.get(1)-1;
        
        IntermediateCode codigo = new IntermediateCode();
        boolean glbl = tablaCodigo.searchGlobalSymbol(nombreVar);
         if (glbl){
           IntermediateCode globalCode = this.tablaCodigo.searchCodeGlobal(nombreVar);
               Symbol simbolo =  Visitor.tablaSimbolos.findAllScopes(nombreVar);
               String tipo = ((Type)simbolo.getTipo()).getLiteralTipo();
               int index = 0;
               if (tipo.equals("int")||tipo.equals("boolean")){
                   index =  posArray * 4;
               }
               if (tipo.equals("char")){
                   index =  posArray *1;
               }
                System.out.println(globalCode.getEtiqueta());
                res= globalCode.getEtiqueta()+"["+index+"]";
           
        }else{
           res = "stack["+(this.buscarStack(nombreVar)+posArray*4)+"]";
        }
         T returnValue = (T)visit(ctx.getChild(2));
        String dir1;
        System.out.println("dir1" + returnValue);
        if (returnValue instanceof ArrayList ){
            if (((ArrayList) returnValue).get(0) instanceof IntermediateCode)
                dir1 =((IntermediateCode)((ArrayList) returnValue).get(0)).getRes();
            else
                dir1 = ((String)((ArrayList) returnValue).get(0));
        }
        else if (returnValue instanceof IntermediateCode){
            dir1 = ((IntermediateCode)returnValue).getRes();
        }
        else{
            dir1 = (String)returnValue;
        }
        if (!dir1.contains("\'")&&!dir1.contains("temp")&&!checkArray.contains(dir1)&&!dir1.contains("[")){
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
        
        codigo.setRes(res);
        tablaCodigo.addCode(codigo);
        this.contadorTemps= 0;
        return ""; //To change
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
      
        modificarSP();
        
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
    public Object visitUnaryExpr(programParser.UnaryExprContext ctx) {
       
        return visit(ctx.getChild(0));//To change body of generated methods, choose Tools | Templates.
    }

  
    @Override
    public Object visitStatementIF(programParser.StatementIFContext ctx) {
        
        
        IntermediateCode etiqueta1 = new IntermediateCode();
        IntermediateCode etiqueta2 = new IntermediateCode();
       
        int copyEt = contEtiquetaActual;
        etiqueta1.setEtiqueta(etiquetaActual+ ++copyEt+":");
       
        System.out.println(actualOp);
        ArrayList midExpr = (ArrayList)this.visit(ctx.getChild(2));
        System.out.println("midExpr " + midExpr);
        String bool = "ifTrue";
        for (int j = 0 ;j<midExpr.size();j++){
            T actualObj = (T)midExpr.get(j) ;
            if (j < midExpr.size()-1){
                if (midExpr.get(j+1) instanceof String){
                    if (midExpr.get(j+1).equals("&&"))
                        bool = "ifFalse";
                    else
                        bool = "ifTrue";
                }
            }
            if (actualObj instanceof IntermediateCode){
                IntermediateCode actualCode = (IntermediateCode)actualObj;
                IntermediateCode codigo = new IntermediateCode();
                codigo.setStatementIF(true);
                IntermediateCode midCode = actualCode;
                tablaCodigo.addCode(midCode);
                codigo.setRes(bool);

                codigo.setDir1(midCode.getRes());
                if (bool.equals("ifFalse")){
                    codigo.setDir2(etiquetaActual+ copyEt);
                }
                else
                    codigo.setDir2(etiquetaActual+ contEtiquetaActual);
                codigo.setOp("GOTO");

                tablaCodigo.addCode(codigo);
            }
        }
        IntermediateCode prevLast = new IntermediateCode(); 
        if (bool.equals("ifFalse")){
            
        }else{
            prevLast.setDir2(etiquetaActual+ copyEt);
            prevLast.setOp("GOTO");
            tablaCodigo.addCode(prevLast);
        }
        
        Scope scopeIF = new Scope();
        
        scopeActual.addSiguiente(scopeIF);
        scopeIF.setAnterior(scopeActual);
        scopeActual = scopeIF;
        
      
        last = etiqueta1;
      
        if (ctx.getChildCount() > 5){
            IntermediateCode etiqueta = new IntermediateCode();
            etiqueta.setEtiqueta(etiquetaActual+contEtiquetaActual+++":");
            
            this.tablaCodigo.addCode(etiqueta);
            IntermediateCode gotoElse = new IntermediateCode();
            gotoElse.setOp("GOTO");
            gotoElse.setDir2(etiquetaActual+(++contEtiquetaActual));
            
            elseInt = gotoElse;
        }
        else{
            IntermediateCode etiqueta = new IntermediateCode();
            etiqueta.setEtiqueta(etiquetaActual+contEtiquetaActual+++":");
            
            this.tablaCodigo.addCode(etiqueta);
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
            this.stackControl.add(new StackControl((4*tamaño), identificador, tipo));
            
        }
        else if (tipo.equals("char")){
            this.stackControl.add(new StackControl((1*tamaño), identificador, tipo));
        }
        else if (tipo.equals("boolean")){
             this.stackControl.add(new StackControl((4*tamaño), identificador, tipo));
        }
      
        modificarSP();
        return super.visitVarDeclarationArray(ctx); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitStructDeclaration(programParser.StructDeclarationContext ctx) {
       
        
        return super.visitStructDeclaration(ctx); //To change body of generated methods, choose Tools | Templates.
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
             T val1 = (T)visit(ctx.getChild(0));
             T val2 = (T)visit(ctx.getChild(2));
             String dir1;
             String dir2;
             if (val1 instanceof IntermediateCode){
                 dir1 = ((IntermediateCode)(val1)).getRes();
             }else
                 dir1 = (String)val1;
             
             if (val2 instanceof IntermediateCode){
                 dir2 =  ((IntermediateCode)(val2)).getRes();
             }else
                 dir2 = (String)val2;
          
        try{
            int test = Integer.parseInt(dir1);
        }catch(Exception e){
            if (!dir1.contains("[")){
                boolean amb = tablaCodigo.searchGlobalSymbol(dir1);
            if (amb)
               dir1+="_"+"_"+amb;
            else
                dir1 = "stack["+this.buscarStack(dir1)+"]";
            }
            
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
             System.out.println("dir1 " + dir1);
             System.out.println("dir2 " + dir2);
        codigo.setDir1(dir1);
        codigo.setDir2(dir2);
        codigo.setOp(op);
        codigo.setRes("temp"+this.contadorTemps++);
        
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
    public Object visitExpression(programParser.ExpressionContext ctx) {
    
        ArrayList returnArray = new ArrayList();
        System.out.println(ctx.getChildCount());
        if (ctx.getChildCount() < 2 ){
            T valor = (T)visit(ctx.getChild(0));
            if (valor instanceof IntermediateCode){
                returnArray.add(valor);
                return returnArray;
            }
            else if (valor instanceof ArrayList){
                returnArray.addAll((ArrayList) valor);
                return returnArray;
            }
            else{
                returnArray.add(valor);
                return returnArray;
            }
        }
        for (int i = 0;i<ctx.getChildCount();i++){
            T expr1 = (T)visit(ctx.getChild(i));
            
            if (expr1 instanceof ArrayList)
               returnArray.addAll((ArrayList)expr1);
            
            else if (expr1 instanceof IntermediateCode)
               returnArray.add(expr1);
            else
                returnArray.add(expr1);
               
        }
        
        return returnArray; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    @Override
    public Object visitLocationArray2(programParser.LocationArray2Context ctx) {
        String nombreVar = ctx.getChild(0).getText();
        String valor = ctx.getChild(2).getText();
      int intVal = Integer.parseInt(valor) - 1;
        try {
            
            StackControl buscado = this.buscarStackObjeto(nombreVar);
            int searchStack;
            if (buscado.getTipo().equals("int")||buscado.getTipo().equals("boolean")){
                searchStack= this.buscarStack(nombreVar) + intVal*4;
            }
            else{
                searchStack = this.buscarStack(nombreVar) + intVal*1;
            }
            System.out.println("search stack " + searchStack);
            return "stack["+ searchStack+"]";
        }
        catch(Exception e){
            boolean gbl = this.tablaCodigo.searchGlobalSymbol(nombreVar);
            if (gbl){
                IntermediateCode globalCode = this.tablaCodigo.searchCodeGlobal(nombreVar);
               Symbol simbolo =  Visitor.tablaSimbolos.findAllScopes(nombreVar);
               String tipo = ((Type)simbolo.getTipo()).getLiteralTipo();
               int index = 0;
               if (tipo.equals("int")||tipo.equals("boolean")){
                   index = intVal * 4;
               }
               if (tipo.equals("char")){
                   index = intVal *1;
               }
                System.out.println(globalCode.getEtiqueta());
                return globalCode.getEtiqueta()+"["+index+"]";
            }
           //super.visitLocationArray2(ctx);
        }
       
        
        return super.visitLocationArray2(ctx); //To change body of generated methods, choose Tools | Templates.
    }


    
    @Override
    public Object visitAndExprCondOpAnd(programParser.AndExprCondOpAndContext ctx) {
        ArrayList returnArray = new ArrayList();
        for (int i = 0;i<ctx.getChildCount();i++){
            T val = (T)visit(ctx.getChild(i));
            if (val instanceof ArrayList){
                returnArray.addAll((ArrayList)val);
            }
            if (val instanceof IntermediateCode){
                returnArray.add(val);
            }
            else
                returnArray.add(val);
        }
        
        return returnArray; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitCond_op_and(programParser.Cond_op_andContext ctx) {
        return ctx.getChild(0).getText(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitCond_op_or(programParser.Cond_op_orContext ctx) {
        return ctx.getChild(0).getText(); //To change body of generated methods, choose Tools | Templates.
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
   
    public StackControl buscarStackObjeto(String identificador){
       for(StackControl control: this.stackControl){
           if (control.getIdentificador().equals(identificador)){
               return control;
           }
       }
       return null;
   }
   
   public void modificarSP(){
        int tamaño = this.stackControl.size();
       
        if (tamaño >= 2){
             System.out.println(stackControl);
            for (int i = tamaño-1;i!=0;i--){
                StackControl lastStack = this.stackControl.get(i);
                StackControl prevLastStack = this.stackControl.get(i-1);
                int lastPos = lastStack.getPosicion();
                int lastPrevPos = prevLastStack.getPosicion();
                lastStack.setPosicion(lastPrevPos);
                prevLastStack.setPosicion(lastPos);
            }
            System.out.println(stackControl);
        }
      
       
   }

}
