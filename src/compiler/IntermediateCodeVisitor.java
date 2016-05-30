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
import java.util.regex.Pattern;
import static compiler.Visitor.tablaSimbolos;

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
    private IntermediateCode elseInt;
    private ArrayList<StackControl> stackControl;
    private ArrayList<GlobalStackControl> globalStack;
    private ArrayList checkArray = new ArrayList();
    private ArrayList exprOps = new ArrayList();
    private int contWhile = 0;
    private int cantidadIF = 0;
    private boolean structValidation = false;
    private final int OFFSET = 4;
    private String varLocation = "";
    private int globalCantidad;
    private IntermediateCode exprParent;
    
    public IntermediateCodeVisitor() {
        this.stackControl = new ArrayList();
        this.globalStack = new ArrayList();
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
    public T visitStatementElse(programParser.StatementElseContext ctx) {
         Scope scopeElse = new Scope();
        
        scopeActual.addSiguiente(scopeElse);
        scopeElse.setAnterior(scopeActual);
        scopeActual = scopeElse;
        
        for (int i = 0;i<ctx.getChildCount();i++){
            this.visit(ctx.getChild(i));
        }
        scopeActual = scopeActual.getAnterior();
         
      
        return (T)null;
    }
    
    @Override
    public Object visitStatementLocation(programParser.StatementLocationContext ctx) {
        IntermediateCode codigo = new IntermediateCode();
        String res = ((String)visit(ctx.getChild(0)));
        System.out.println("res " + res);
        if (!res.contains("stack")){
            boolean glbl = tablaCodigo.searchGlobalSymbol(res, scopeActual);
            if (glbl){
               res = "stack_global["+this.buscarGlobalStack(res)+"]";

            }else{
               res = "stack["+this.buscarStack(res, this.scopeActual)+"]";
            }
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
                boolean glbl2 = tablaCodigo.searchGlobalSymbol(dir1, scopeActual);
                if (glbl2){
                      dir1 = "stack_global["+this.buscarGlobalStack(dir1)+"]";
                }
                else { 
                    int pos = this.buscarStack(dir1, this.scopeActual);
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
        
         if (ctx.getChildCount() == 6){
               return super.visitLocationArray(ctx);
        }
        
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
        boolean glbl = tablaCodigo.searchGlobalSymbol(nombreVar, scopeActual);
         if (glbl){
            res = "stack_global["+(this.buscarGlobalStack(nombreVar)+posArray*OFFSET)+"]";;
           
        }else{
           res = "stack["+(this.buscarStack(nombreVar, this.scopeActual)+posArray*OFFSET)+"]";
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
                boolean glbl2 = tablaCodigo.searchGlobalSymbol(dir1, scopeActual);
                if (glbl2){
                      dir1 = "stack_global[" + this.buscarGlobalStack(dir1) +"]";
                }
                else { 
                    int pos = this.buscarStack(dir1, this.scopeActual);
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
    public Object visitLocationMethod(programParser.LocationMethodContext ctx) {
        return super.visitLocationMethod(ctx); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitLocationMemberMethod(programParser.LocationMemberMethodContext ctx) {
       
        if (ctx.getChildCount()>1){
            this.varLocation = this.varLocation+"€" + ctx.getChild(0).getText();
            super.visitLocationMemberMethod(ctx);
            return this.varLocation;
        }
        this.varLocation += "€"+ctx.getChild(0).getText();
        String[] locations = this.varLocation.split("€");
        Symbol methodSymbol = null;
        String lastTipo = "";
        for (int i = 0;i<locations.length;i++){
            if (i == 0){
                methodSymbol = tablaSimbolos.showSymbol(locations[i], scopeActual);
            }
            else{
                methodSymbol = tablaSimbolos.findAllScopes(((Type)methodSymbol.getTipo()).getLiteralTipo());
                ArrayList members = ((StructType)methodSymbol.getTipo()).getMembers();
                for (int k = 0;k<members.size();k++){
                     Symbol innerSymbol =  (Symbol)members.get(k);
                     if (((Type)innerSymbol.getTipo()).getNombreVariable().equals(
                             locations[i]
                     )){
                         lastTipo = ((Type)methodSymbol.getTipo()).getNombreVariable();
                         methodSymbol = innerSymbol;
                     }
                }
            }
        }
        String nombreFinalVar =  ((Type)methodSymbol.getTipo()).getNombreVariable()+"_"+lastTipo;
        System.out.println("");
        
       this.varLocation =  "stack_global[" + this.buscarGlobalStack(nombreFinalVar) +"]";
       
        
        return this.varLocation; //To change body of generated methods, choose Tools | Templates.
    }
    

    
    
    
   @Override
    public T visitLocation(programParser.LocationContext ctx) {
       
        if (ctx.getChildCount()>1){
            this.varLocation = ctx.getChild(0).getText();
            String type = (String)visit(ctx.getChild(2));
            System.out.println("returning");
            System.out.println(type);
            this.varLocation = "";
            return (T)type;
        }
        
        
        return (T)ctx.getChild(0).getText(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitLocationMemberArray(programParser.LocationMemberArrayContext ctx) {
     this.varLocation += "€"+ctx.getChild(0).getChild(0).getText();
        String[] locations = this.varLocation.split("€");
        Symbol methodSymbol = null;
        for (int i = 0;i<locations.length;i++){
            if (i == 0){
                methodSymbol = tablaSimbolos.showSymbol(locations[i], scopeActual);
            }
            else{
                methodSymbol = tablaSimbolos.findAllScopes(((Type)methodSymbol.getTipo()).getLiteralTipo());
                ArrayList members = ((StructType)methodSymbol.getTipo()).getMembers();
                for (int k = 0;k<members.size();k++){
                     Symbol innerSymbol =  (Symbol)members.get(k);
                     if (((Type)innerSymbol.getTipo()).getNombreVariable().equals(
                             locations[i]
                     ))
                         methodSymbol = innerSymbol;
                }
            }
        }
       
        super.visitLocationMemberArray(ctx);
    
     
        int valPos =  Integer.parseInt(ctx.getChild(0).getChild(2).getText());
        valPos = valPos -1;
        String nombreVar = ((Type)methodSymbol.getTipo()).getNombreVariable();
        if (!this.varLocation.contains("global"))
            this.varLocation = 
            "stack_global["+(this.buscarGlobalStack(nombreVar)+valPos*OFFSET)+"]";
                
       
        
       
        System.out.println("member array " +  ctx.getChild(0).getText());
        return this.varLocation; 
    }
    
    
      @Override
    public Object visitVarDeclarationID(programParser.VarDeclarationIDContext ctx){
        //System.out.println(ctx.getChildCount()+"cantidad var declaration");
        IntermediateCode codigo = new IntermediateCode();
        String tipo = ctx.getChild(0).getText();
        String identificador = ctx.getChild(1).getText();
        if (scopeActual.getIdScope() == 0|| structValidation) {
            if (structValidation){
                identificador += "_"+ctx.getParent().getChild(1).getText();
            }
            this.globalStack.add(new GlobalStackControl(OFFSET,identificador, tipo));
            return super.visitVarDeclarationID(ctx);
        }
       
     
        StackControl stackC = new StackControl(OFFSET, identificador, tipo, this.scopeActual);
        this.stackControl.add(stackC);
        codigo.setLocalStack(stackC);
        this.tablaCodigo.addCode(codigo);
            
   
      
        //modificarSP();
        
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
        codigo.setMethod(true);
        tablaCodigo.addCode(codigo);
        super.visitMethodDeclaration(ctx);
        scopeActual = scopeActual.getAnterior();
        StackControl.staticPos = 0;
        this.contEtiquetaActual= 0;
        IntermediateCode codigoSalida = new IntermediateCode();
        codigoSalida.setSalidaMetodo(true);
        this.tablaCodigo.addCode(codigoSalida);
                
        return "";//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitAddExprMinusPlusOp(programParser.AddExprMinusPlusOpContext ctx) {
        String opTemp = "";
        String opTemp2;
       
        String op1 = "";
       
        if (opTemp.isEmpty()){
            T val1 = (T)this.visit(ctx.getChild(2));
            if (val1 instanceof String){
                op1 = (String)val1;
            }
            if (val1 instanceof IntermediateCode)
                op1= ((IntermediateCode)val1).getRes();
            } 
       
        String op2="";
       
        if (opTemp.isEmpty()){
            T val2 = (T)this.visit(ctx.getChild(0));
            if (val2 instanceof String)
                op2 = (String)val2;
            if (val2 instanceof IntermediateCode)
                op2 = ((IntermediateCode)val2).getRes();
            
        } 
        //probar si argumentos son variables
        //probar si argumentos son variables
        try{
           int valor = Integer.parseInt(op1);
           //si es valor numérico no hago nada.
        }catch(Exception e){
            //si no es numérico busco en el stack
            if (!op1.contains("temp")&&!op1.contains("[")&&!op1.contains("global"))
                op1 = "stack"+"["+this.buscarStack(op1, this.scopeActual)+"]";
        }
        try {
            int valor = Integer.parseInt(op2);
            //si es valor numérico no hago nada
        }catch(Exception e){
           if (!op2.contains("temp")&&!op2.contains("[")&&!op2.contains("global"))
                op2 = "stack"+"["+this.buscarStack(op2, this.scopeActual)+"]";
        }
        
       
        IntermediateCode codigo = new IntermediateCode();
        codigo.setDir1(op2);
        codigo.setDir2(op1);
        codigo.setOp(ctx.getChild(1).getText());
        codigo.setRes("temp"+this.contadorTemps++);
        this.tablaCodigo.addCode(codigo);
        
        
        return codigo; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitMultExprMultDivOp(programParser.MultExprMultDivOpContext ctx) {
        String opTemp = "";
      
     
        String op1 = "";
     
        if (opTemp.isEmpty()){
            T val1 = (T)this.visit(ctx.getChild(2));
            if (val1 instanceof String){
                op1 = (String)val1;
            }
            if (val1 instanceof IntermediateCode)
                op1= ((IntermediateCode)val1).getRes();
            } 
       
        String op2="";
       
        if (opTemp.isEmpty()){
            T val2 = (T)this.visit(ctx.getChild(0));
            if (val2 instanceof String)
                op2 = (String)val2;
            if (val2 instanceof IntermediateCode)
                op2 = ((IntermediateCode)val2).getRes();
            
        } 
        //probar si argumentos son variables
        try{
           int valor = Integer.parseInt(op1);
           //si es valor numérico no hago nada.
        }catch(Exception e){
            //si no es numérico busco en el stack
             if (!op1.contains("temp")&&!op1.contains("[")&&!op1.contains("global"))
                op1 = "stack"+"["+this.buscarStack(op1, this.scopeActual)+"]";
        }
        try {
            int valor = Integer.parseInt(op2);
            //si es valor numérico no hago nada
        }catch(Exception e){
            if (!op2.contains("temp")&&!op2.contains("[")&&!op2.contains("global"))
                op2 = "stack"+"["+this.buscarStack(op2, this.scopeActual)+"]";
        }
        
       
        IntermediateCode codigo = new IntermediateCode();
        codigo.setDir1(op2);
        codigo.setDir2(op1);
        codigo.setOp(ctx.getChild(1).getText());
        codigo.setRes("temp"+this.contadorTemps++);
        this.tablaCodigo.addCode(codigo);
        
        
        return codigo; //To change 
    }

    @Override
    public Object visitParameterID(programParser.ParameterIDContext ctx) {
        
        StackControl param = new StackControl(4,ctx.getChild(1).getText(), ctx.getChild(0).getText(), this.scopeActual);
        this.stackControl.add(param);
        IntermediateCode codigo = new IntermediateCode();
        codigo.setLocalStack(param);
        codigo.setParam(true);
        this.tablaCodigo.addCode(codigo);
        return super.visitParameterID(ctx); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitParameterArray(programParser.ParameterArrayContext ctx) {
        
        int valArray = Integer.parseInt(ctx.getChild(3).getText());
        
        StackControl param = new StackControl(4*valArray,ctx.getChild(1).getText(), ctx.getChild(0).getText(), this.scopeActual);
        this.stackControl.add(param);
        
        return super.visitParameterArray(ctx); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    @Override
    public Object visitUnaryExpr(programParser.UnaryExprContext ctx) {
       T val = (T)visit(ctx.getChild(0));
       
        return val;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitValueExprWithParent(programParser.ValueExprWithParentContext ctx) {
        
        //agregar precedencia
        this.contEtiquetaActual++;
        exprParent = new IntermediateCode();
        exprParent.setEtiqueta(etiquetaActual+this.contEtiquetaActual+":");
        this.globalCantidad++;
        T val = (T)visit(ctx.getChild(1));
        return val; //To change body of generated methods, choose Tools | Templates.
    }

    
  
    @Override
    public Object visitStatementIF(programParser.StatementIFContext ctx) {
        
        IntermediateCode etiqueta1 = new IntermediateCode();
        IntermediateCode etiqueta2 = new IntermediateCode();
        
        int copyEt = contEtiquetaActual;
        this.globalCantidad = 1+ this.countStrings(ctx.getChild(4).getText(), "if")*2;
        if (scopeActual.getIdScope()!=1){
            globalCantidad++;
        }
        if (ctx.getChildCount()>5){
            this.globalCantidad++;
            
        }
        etiqueta1.setEtiqueta(etiquetaActual+ ++copyEt+":");
        //copyEt = copyEt + cantidad;
        System.out.println(actualOp);
        ArrayList midExpr = (ArrayList)this.visit(ctx.getChild(2));
        if (midExpr.size()==1){
             String bool;
                if (actualOp.equals("&&"))
                    bool="ifFalse";
                else
                    bool="ifTrue";
                 IntermediateCode actualCode = (IntermediateCode)midExpr.get(0);
                IntermediateCode codigo = new IntermediateCode();
                codigo.setStatementIF(true);
                IntermediateCode midCode = actualCode;
                tablaCodigo.addCode(midCode);
                codigo.setRes(bool);

                codigo.setDir1(midCode.getRes());
                if (bool.equals("ifFalse")){
                    codigo.setDir2(etiquetaActual+ (this.globalCantidad));
                }
                else
                    codigo.setDir2(etiquetaActual+ contEtiquetaActual);
                codigo.setOp("GOTO");
                if (midCode.getRes().contains("temp"))
                    tablaCodigo.addCode(codigo);
                    
            
            IntermediateCode afterExpr = new IntermediateCode();

            afterExpr.setOp("GOTO");
            afterExpr.setDir2(etiquetaActual+(this.globalCantidad));

            this.tablaCodigo.addCode(afterExpr);
        }
       /* System.out.println("midExpr " + midExpr);
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
            
            System.out.println(ctx.getChild(4).getText());
            System.out.println("cantidad" + cantidad);
           
           prevLast.setDir2(etiquetaActual+ (cantidad));
            prevLast.setOp("GOTO");
            tablaCodigo.addCode(prevLast);
        }*/
        
        Scope scopeIF = new Scope();
        
        scopeActual.addSiguiente(scopeIF);
        scopeIF.setAnterior(scopeActual);
        scopeActual = scopeIF;
        
       
        if (ctx.getChildCount() > 5){
            IntermediateCode etiqueta = new IntermediateCode();
            etiqueta.setEtiqueta(etiquetaActual+contEtiquetaActual+++":");
            
            this.tablaCodigo.addCode(etiqueta);
            IntermediateCode gotoElse = new IntermediateCode();
            gotoElse.setOp("GOTO");
            gotoElse.setDir2(etiquetaActual+(++this.globalCantidad));
            ++this.contEtiquetaActual;
            
            elseInt = gotoElse;
        }
        else{
            IntermediateCode etiqueta = new IntermediateCode();
            etiqueta.setEtiqueta(etiquetaActual+contEtiquetaActual+++":");
            
            this.tablaCodigo.addCode(etiqueta);
        }
        
         this.visit(ctx.getChild(4));
         IntermediateCode label = new IntermediateCode();
         label.setEtiqueta(etiquetaActual+contEtiquetaActual+++":");
         tablaCodigo.addCode(label);
        
       if (ctx.getChildCount() > 5){
         
            this.visit(ctx.getChild(5));
            etiqueta2.setEtiqueta(etiquetaActual+contEtiquetaActual+++":");
            tablaCodigo.addCode(etiqueta2);
       }
        scopeActual = scopeActual.getAnterior();
        return "";
    }

    @Override
    public Object visitVarDeclarationArray(programParser.VarDeclarationArrayContext ctx) {
        
        String tipo = ctx.getChild(0).getText();
        String identificador = ctx.getChild(1).getText();
        int tamaño = Integer.parseInt(ctx.getChild(3).getText());
        
        IntermediateCode codigo = new IntermediateCode();
        if (scopeActual.getIdScope() == 0 || structValidation) {
           
            this.globalStack.add(new GlobalStackControl(OFFSET*tamaño,identificador, tipo));
            return super.visitVarDeclarationArray(ctx);
        }
       
     
        StackControl stackC = new StackControl((OFFSET*tamaño), identificador, tipo, this.scopeActual);
        this.stackControl.add(stackC);
        codigo.setLocalStack(stackC);
        this.tablaCodigo.addCode(codigo);
            
       
       // modificarSP();
        return super.visitVarDeclarationArray(ctx); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitStructDeclaration(programParser.StructDeclarationContext ctx) {
        if (scopeActual.getIdScope()==0)
            this.structValidation = true;
        Scope scopeIF = new Scope();
        
        scopeActual.addSiguiente(scopeIF);
        scopeIF.setAnterior(scopeActual);
        scopeActual = scopeIF;
        
       
        
        super.visitStructDeclaration(ctx);
        
        this.structValidation = false;
        
         scopeActual = scopeActual.getAnterior();
        return ""; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
  @Override
    public Object visitBlock(programParser.BlockContext ctx) {
        super.visitBlock(ctx); 
        if (elseInt != null){
            this.tablaCodigo.addCode(elseInt);
            elseInt = null;
        }
       
        return "";//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitEqExprEqOp(programParser.EqExprEqOpContext ctx) {
        actualOp = ctx.getChild(1).getChild(0).getText();
         if (ctx.getChildCount()>1){
             T val1 = (T)visit(ctx.getChild(0));
             System.out.println(val1);
             T val2 = (T)visit(ctx.getChild(2));
             System.out.println(val2);
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
                boolean amb = tablaCodigo.searchGlobalSymbol(dir1, scopeActual);
            if (amb)
               dir1 =  "stack_global[" + this.buscarGlobalStack(dir1) +"]";
            else
                dir1 = "stack["+this.buscarStack(dir1, this.scopeActual)+"]";
            }
            
        }
        try{
            int test = Integer.parseInt(dir2);
        }catch(Exception e){
            int amb = tablaCodigo.searchSymbolLastScope(dir2).getAmbito();
            if (amb==0)
               dir2 = "stack_global[" + this.buscarGlobalStack(dir2) +"]";
            else
                dir2 =  "stack["+this.buscarStack(dir2, this.scopeActual)+"]";
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
//        actualOp = ctx.getChild(1).getChild(0).getText();
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
                boolean amb = tablaCodigo.searchGlobalSymbol(dir1, scopeActual);
            if (amb)
               dir1 = "stack_global[" + this.buscarGlobalStack(dir1) +"]";
            else
                dir1 = "stack["+this.buscarStack(dir1, this.scopeActual)+"]";
            }
            
        }
        try{
            int test = Integer.parseInt(dir2);
        }catch(Exception e){
            int amb = tablaCodigo.searchSymbolLastScope(dir2).getAmbito();
            if (amb==0)
               dir2 =  "stack_global[" + this.buscarGlobalStack(dir2) +"]";
            else
                dir2 = "stack["+this.buscarStack(dir2, this.scopeActual)+"]";
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
        return super.visitRelationExpr(ctx);
    }

    @Override
    public Object visitStatementWhile(programParser.StatementWhileContext ctx) {
        String bool="";
        Scope scopeIF = new Scope();
        contWhile++;
        scopeActual.addSiguiente(scopeIF);
        scopeIF.setAnterior(scopeActual);
        scopeActual = scopeIF;
       
        
        IntermediateCode etiqueta1 = new IntermediateCode();
        IntermediateCode etiqueta2 = new IntermediateCode();
        IntermediateCode etiquetaWhile = new IntermediateCode();
        etiquetaWhile.setEtiqueta("whileLoop" + "_"+etiquetaActual+contEtiquetaActual+":");
        this.tablaCodigo.addCode(etiquetaWhile);
            
        int copyEt = contEtiquetaActual;
        etiqueta1.setEtiqueta(etiquetaActual+ ++copyEt+":");
          ArrayList midExpr = (ArrayList)this.visit(ctx.getChild(2));
        if (midExpr.size()==1){
        
                if (actualOp.equals("&&"))
                    bool="ifFalse";
                else
                    bool="ifTrue";
                 IntermediateCode actualCode = (IntermediateCode)midExpr.get(0);
                IntermediateCode codigo = new IntermediateCode();
                codigo.setStatementIF(true);
                IntermediateCode midCode = actualCode;
                tablaCodigo.addCode(midCode);
                codigo.setRes(bool);

                codigo.setDir1(midCode.getRes());
                if (bool.equals("ifFalse")){
                    codigo.setDir2(etiquetaActual+ (this.globalCantidad));
                }
                else
                    codigo.setDir2(etiquetaActual+ contEtiquetaActual);
                codigo.setOp("GOTO");
                if (midCode.getRes().contains("temp"))
                    tablaCodigo.addCode(codigo);
                    
           
            
        }
        
          IntermediateCode prevLast = new IntermediateCode(); 
        if (bool.equals("ifFalse")){
            
        }else{
            
            if (ctx.getChild(4).getText().contains("while")){
               int offset = this.countStrings(ctx.getChild(4).getText(), "while");
                prevLast.setDir2(etiquetaActual+ (copyEt+1+offset));
            }
            else
                prevLast.setDir2(etiquetaActual + (copyEt));
            prevLast.setOp("GOTOP");
            tablaCodigo.addCode(prevLast);
        }
        
       
       
        IntermediateCode etiqueta = new IntermediateCode();
        etiqueta.setEtiqueta(etiquetaActual+contEtiquetaActual+++":");
            
        this.tablaCodigo.addCode(etiqueta);
        visit(ctx.getChild(4));
         
      
        IntermediateCode finalGoto = new IntermediateCode();
        finalGoto.setOp("GOTO");
        int copyCont =contEtiquetaActual;
        copyCont = --contWhile;
        finalGoto.setDir2("whileLoop" + "_"+etiquetaActual+(copyCont));
        tablaCodigo.addCode(finalGoto);
        
        
        etiqueta2.setEtiqueta(etiquetaActual+contEtiquetaActual+++":");
        tablaCodigo.addCode(etiqueta2);
        
        scopeActual = scopeActual.getAnterior();
        
        
        return "";
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
                if (exprParent != null){
                   
                    this.tablaCodigo.addCode(exprParent);
                }
                String bool = "";
                int copyEt = this.globalCantidad;
                returnArray.addAll((ArrayList) valor);
                for (int i = 0; i < returnArray.size();i++){
                    System.out.println(returnArray.get(i));
                    T innerValue = (T)returnArray.get(i);

                    if (i < returnArray.size() - 1){
                        if (returnArray.get(i+1) instanceof String){
                            if (returnArray.get(i+1).equals("&&"))
                                bool = "ifFalse";
                            else
                                bool = "ifTrue";
                        }
                    }
                    if (innerValue instanceof IntermediateCode){

                        IntermediateCode actualCode = (IntermediateCode)innerValue;
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


                    System.out.println("cantidad" + this.globalCantidad);

                    prevLast.setDir2(etiquetaActual+ (this.globalCantidad));
                    prevLast.setOp("GOTO");
                    tablaCodigo.addCode(prevLast);
                }
        
                return returnArray;
            }
            else{
                returnArray.add(valor);
                return returnArray;
            }
        }
        for (int i = 0;i<ctx.getChildCount();i++){
            T expr1 = (T)visit(ctx.getChild(i));
            System.out.println(expr1);
            if (expr1 instanceof ArrayList)
               returnArray.addAll((ArrayList)expr1);
            
            else if (expr1 instanceof IntermediateCode)
               returnArray.add(expr1);
            else
                returnArray.add(expr1);
               
        }
        System.out.println(returnArray);
        int copyEt = contEtiquetaActual;
         String bool = "ifTrue";
        for (int i = 0; i < returnArray.size();i++){
            System.out.println(returnArray.get(i));
            T innerValue = (T)returnArray.get(i);
           
            if (i > 0){
                if (returnArray.get(i-1) instanceof String){
                    System.out.println("STRING " + returnArray.get(i-1));
                    if (returnArray.get(i-1).equals("&&"))
                        bool = "ifFalse";
                    else
                        bool = "ifTrue";
                }
            }
            if (innerValue instanceof IntermediateCode){
                 
                IntermediateCode actualCode = (IntermediateCode)innerValue;
                IntermediateCode codigo = new IntermediateCode();
                codigo.setStatementIF(true);
                IntermediateCode midCode = actualCode;
                tablaCodigo.addCode(midCode);
                codigo.setRes(bool);

                codigo.setDir1(midCode.getRes());
                if (bool.equals("ifFalse")){
                    codigo.setDir2(etiquetaActual+ (copyEt+1));
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
            
            
            System.out.println("cantidad" + this.globalCantidad);
           
            prevLast.setDir2(etiquetaActual+ (this.globalCantidad));
            prevLast.setOp("GOTO");
            tablaCodigo.addCode(prevLast);
        }
        
        return new ArrayList(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    @Override
    public Object visitLocationArray2(programParser.LocationArray2Context ctx) {
        if (ctx.getChildCount() == 6){
                this.varLocation = ctx.getChild(0).getText();
                super.visitLocationArray2(ctx);
        }
        String nombreVar = ctx.getChild(0).getText();
        String valor = ctx.getChild(2).getText();
        int intVal = Integer.parseInt(valor) - 1;
        try {
            
            StackControl buscado = this.buscarStackObjeto(nombreVar);
            int searchStack;
            if (buscado.getTipo().equals("int")||buscado.getTipo().equals("boolean")){
                searchStack= this.buscarStack(nombreVar, this.scopeActual) + intVal*OFFSET;
            }
            else{
                searchStack = this.buscarStack(nombreVar,this.scopeActual) + intVal*OFFSET;
            }
            System.out.println("search stack " + searchStack);
            return "stack["+ searchStack+"]";
        }
        catch(Exception e){
            boolean gbl = this.tablaCodigo.searchGlobalSymbol(nombreVar, scopeActual);
            if (gbl){
               
             
                return  "stack_global[" + this.buscarGlobalStack(nombreVar) + intVal*OFFSET +"]";
            }
           //super.visitLocationArray2(ctx);
        }
        if (ctx.getChildCount() == 6){
            this.varLocation = ctx.getChild(0).getText();
        }
        
        return super.visitLocationArray2(ctx); //To change body of generated methods, choose Tools | Templates.
    }


    
    @Override
    public Object visitAndExprCondOpAnd(programParser.AndExprCondOpAndContext ctx) {
        ArrayList returnArray = new ArrayList();
        for (int i = 0;i<ctx.getChildCount();i++){
            T val = (T)visit(ctx.getChild(i));
            System.out.println(val);
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
    public Object visitMethodCall(programParser.MethodCallContext ctx) {
        //manejo de parámetros
        
        for (int i = 1;i<ctx.getChildCount();i++){
           T param = (T)this.visit(ctx.getChild(i));
            System.out.println(param);
           if (param instanceof IntermediateCode){
             
               IntermediateCode sendParam = new IntermediateCode();
               sendParam.setDir1("PARAM");
               sendParam.setDir2(((IntermediateCode)param).getRes());
               this.tablaCodigo.addCode(sendParam);
           }
            if (param instanceof ArrayList){
                IntermediateCode sendParam = new IntermediateCode();
                sendParam.setDir1("PARAM");
                if (((ArrayList)param).get(0) instanceof String){
                    String nombreVAr = (String)((ArrayList)param).get(0);
                         boolean amb = tablaCodigo.searchGlobalSymbol(nombreVAr, scopeActual);
                        if (amb)
                            sendParam.setDir2( "stack_global[" + this.buscarGlobalStack(nombreVAr) +"]");
                        else
                            sendParam.setDir2( "stack[" + this.buscarStack(nombreVAr, this.scopeActual) +"]");
                        
                   
                }
                else
                    sendParam.setDir2(((IntermediateCode)((ArrayList)param).get(0)).getRes());
                
               this.tablaCodigo.addCode(sendParam);
           }
        }
        
        
        //llamada al método
        IntermediateCode intermedicateMethod  = new IntermediateCode();
        intermedicateMethod.setRes("temp0");
       
        intermedicateMethod.setDir1("CALL");
        intermedicateMethod.setDir2(ctx.getChild(0).getText());
        this.tablaCodigo.addCode(intermedicateMethod);
        return  intermedicateMethod;//To change body of generated methods, choose Tools | Templates.
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
    
   /**
    * Este método da problemas cuando se declara una variable en otro scope
    * @param identificador
     * @param scope
    * @return 
    */
   public int buscarStack(String identificador, Scope scope){
       for(StackControl control: this.stackControl){
           if (control.getIdentificador().equals(identificador)&&
               control.verificarScope(scope)){
               return control.getPosicion();
           }
       }
       return -1;
   }
   
    public int buscarGlobalStack(String identificador){
       for(GlobalStackControl control: this.globalStack){
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
    
    public int countStrings(String haystack, String needle){
    
        return haystack.split(Pattern.quote(needle), -1).length - 1;
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
