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
    private ArrayList checkArray = new ArrayList();
    private ArrayList exprOps = new ArrayList();
    private int contWhile = 0;
    private int cantidadIF = 0;
    private boolean structValidation = false;
    private final int OFFSET = 4;
    private String varLocation = "";
    
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
        System.out.println("res " + res);
        boolean glbl = tablaCodigo.searchGlobalSymbol(res, scopeActual);
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
                boolean glbl2 = tablaCodigo.searchGlobalSymbol(dir1, scopeActual);
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
           IntermediateCode globalCode = this.tablaCodigo.searchCodeGlobal(nombreVar);
               Symbol simbolo =  Visitor.tablaSimbolos.findAllScopes(nombreVar);
               String tipo = ((Type)simbolo.getTipo()).getLiteralTipo();
               int index = 0;
               if (tipo.equals("int")||tipo.equals("boolean")){
                   index =  posArray * OFFSET;
               }
               if (tipo.equals("char")){
                   index =  posArray * OFFSET;
               }
                System.out.println(globalCode.getEtiqueta());
                res= globalCode.getEtiqueta()+"["+index+"]";
           
        }else{
           res = "stack["+(this.buscarStack(nombreVar)+posArray*OFFSET)+"]";
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
        String nombreFinalVar = ((Type)methodSymbol.getTipo()).getNombreVariable();
        System.out.println("");
        
       this.varLocation = nombreFinalVar+"_global";
       
        
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
        if (!this.varLocation.contains("global"))
            this.varLocation = ((Type)methodSymbol.getTipo()).getNombreVariable()+"_global["+
               (valPos*4)+"]"
                ;
        
       
        System.out.println("member array " +  ctx.getChild(0).getText());
        return this.varLocation; 
    }
    
    
      @Override
    public Object visitVarDeclarationID(programParser.VarDeclarationIDContext ctx){
        //System.out.println(ctx.getChildCount()+"cantidad var declaration");
        IntermediateCode codigo = new IntermediateCode();
        if (scopeActual.getIdScope() == 0|| structValidation) {
           
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
            this.stackControl.add(new StackControl(OFFSET, identificador, tipo));
        }
        else if (tipo.equals("boolean")){
             this.stackControl.add(new StackControl(OFFSET, identificador, tipo));
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
                op1 = "stack"+"["+this.buscarStack(op1)+"]";
        }
        try {
            int valor = Integer.parseInt(op2);
            //si es valor numérico no hago nada
        }catch(Exception e){
           if (!op2.contains("temp")&&!op2.contains("[")&&!op2.contains("global"))
                op2 = "stack"+"["+this.buscarStack(op2)+"]";
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
                op1 = "stack"+"["+this.buscarStack(op1)+"]";
        }
        try {
            int valor = Integer.parseInt(op2);
            //si es valor numérico no hago nada
        }catch(Exception e){
            if (!op2.contains("temp")&&!op2.contains("[")&&!op2.contains("global"))
                op2 = "stack"+"["+this.buscarStack(op2)+"]";
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
    public Object visitUnaryExpr(programParser.UnaryExprContext ctx) {
       
        return visit(ctx.getChild(0));//To change body of generated methods, choose Tools | Templates.
    }

  
    @Override
    public Object visitStatementIF(programParser.StatementIFContext ctx) {
        
         IntermediateCode etiqueta1 = new IntermediateCode();
        IntermediateCode etiqueta2 = new IntermediateCode();
        
        int copyEt = contEtiquetaActual;
        int cantidad = 1+ this.countStrings(ctx.getChild(4).getText(), "if")*2;
        if (scopeActual.getIdScope()!=1){
            cantidad++;
        }
     
        etiqueta1.setEtiqueta(etiquetaActual+ ++copyEt+":");
        //copyEt = copyEt + cantidad;
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
            
            System.out.println(ctx.getChild(4).getText());
            System.out.println("cantidad" + cantidad);
           
           prevLast.setDir2(etiquetaActual+ (cantidad));
            prevLast.setOp("GOTO");
            tablaCodigo.addCode(prevLast);
        }
        
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
            gotoElse.setDir2(etiquetaActual+(++contEtiquetaActual));
            
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
           
            codigo.setEtiqueta(ctx.getChild(1).getText()+"_global");
            codigo.setGlobal(true);
            tablaCodigo.addCode(codigo);
            return super.visitVarDeclarationArray(ctx);
        }
       
     
        if (tipo.equals("int")){
            this.stackControl.add(new StackControl((OFFSET*tamaño), identificador, tipo));
            
        }
        else if (tipo.equals("char")){
            this.stackControl.add(new StackControl((OFFSET*tamaño), identificador, tipo));
        }
        else if (tipo.equals("boolean")){
             this.stackControl.add(new StackControl((OFFSET*tamaño), identificador, tipo));
        }
      
        modificarSP();
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
        return super.visitRelationExpr(ctx);
    }

    @Override
    public Object visitStatementWhile(programParser.StatementWhileContext ctx) {
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
            
            if (ctx.getChild(4).getText().contains("while")){
               int offset = this.countStrings(ctx.getChild(4).getText(), "while");
                prevLast.setDir2(etiquetaActual+ (copyEt+1+offset));
            }
            else
                prevLast.setDir2(etiquetaActual + (copyEt));
            prevLast.setOp("GOTO");
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
                searchStack= this.buscarStack(nombreVar) + intVal*OFFSET;
            }
            else{
                searchStack = this.buscarStack(nombreVar) + intVal*OFFSET;
            }
            System.out.println("search stack " + searchStack);
            return "stack["+ searchStack+"]";
        }
        catch(Exception e){
            boolean gbl = this.tablaCodigo.searchGlobalSymbol(nombreVar, scopeActual);
            if (gbl){
                IntermediateCode globalCode = this.tablaCodigo.searchCodeGlobal(nombreVar);
               Symbol simbolo =  Visitor.tablaSimbolos.findAllScopes(nombreVar);
               String tipo = ((Type)simbolo.getTipo()).getLiteralTipo();
               int index = 0;
               if (tipo.equals("int")||tipo.equals("boolean")){
                   index = intVal * OFFSET;
               }
               if (tipo.equals("char")){
                   index = intVal *OFFSET;
               }
                System.out.println(globalCode.getEtiqueta());
                return globalCode.getEtiqueta()+"["+index+"]";
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
