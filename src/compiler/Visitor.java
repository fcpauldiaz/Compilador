/**
* Universidad Del Valle de Guatemala
* 12-feb-2016
* Pablo Díaz 13203
*/

package compiler;

import antlr4.programBaseVisitor;
import antlr4.programParser;
import static compiler.ANTGui.jTextArea3;
import java.util.ArrayList;


/**
 *
 * @author Pablo
 * @param <T>
 */
public class Visitor<T> extends programBaseVisitor {
    
    public int ambitoActual=0;
   
    private final SymbolTable tablaSimbolos;
    public int autoincrement = 0;
    public Scope scopeActual;
    public static boolean verificacion = true;
    public static boolean verificadorMain;
    
    
    public Visitor() {
       
        scopeActual = new Scope();
        scopeActual.setIdScope(ambitoActual);
        this.tablaSimbolos = new SymbolTable();
        verificadorMain = false;
       
       
    }
    @Override 
    public T visitProgram(programParser.ProgramContext ctx) {
       
        //System.out.println(ctx.getChildCount());
        
        for (int i = 0;i<ctx.getChildCount();i++){
           //System.out.println(ctx.getChild(i).getText());
           this.visit(ctx.getChild(i));
        }
       
       
        
        tablaSimbolos.printSymbolTable();
        return (T)"";
    }
    
    
    @Override
    public T visitDeclaration(programParser.DeclarationContext ctx){
        //System.out.println("cantidad declaration: " + ctx.getChildCount());
        for (int i = 0;i<ctx.getChildCount();i++){
            this.tablaSimbolos.addSymbol((Symbol)this.visit(ctx.getChild(i)),ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());
        }
        
        
        return (T)"void";
    }
    /**
     * Debe devolver un símbolo
     * @param ctx
     * @return 
     */
    @Override
    public T visitStructDeclaration(programParser.StructDeclarationContext ctx){
        
        StructType struct = new StructType();
        
        Scope scopeStruct = new Scope();
        
        scopeActual.addSiguiente(scopeStruct);
        scopeStruct.setAnterior(scopeActual);
        scopeActual = scopeStruct;
        
       
        struct.setLiteralTipo(ctx.getChild(0).getText());
        struct.setNombreVariable(ctx.getChild(1).getText());
        for (int i = 0;i<ctx.getChildCount();i++){
            Symbol simbolo = (Symbol)visit(ctx.getChild(i));
            if (simbolo != null){
                this.tablaSimbolos.addSymbol(simbolo,ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());
                struct.addMember(simbolo);
            }
        }
        
        
        scopeActual = scopeActual.getAnterior();
        Symbol simbolo = new Symbol(++autoincrement, scopeActual.getIdScope(),struct);
        
        return (T)simbolo ;
    }
    
   
    
    
    
    
    
    @Override
    public T visitVarDeclarationID(programParser.VarDeclarationIDContext ctx){
        //System.out.println(ctx.getChildCount()+"cantidad var declaration");
        
        
        //param1 = nombre variable
        //param2 = nombre tipo
        Type tipo = new Type(ctx.getChild(1).getText(),ctx.getChild(0).getText());
        Symbol simbolo = new Symbol(++autoincrement,scopeActual.getIdScope(),tipo);
       
        
        return (T)simbolo;
    }

    @Override
    public T visitVarDeclarationArray(programParser.VarDeclarationArrayContext ctx) {
        
        Type tipo = new Type(ctx.getChild(1).getText(),ctx.getChild(0).getText());
        tipo.setArreglo(Integer.parseInt(ctx.getChild(3).getText()));
        
        Symbol simbolo = new Symbol(++autoincrement,scopeActual.getIdScope(),tipo);
       
        
        return (T)simbolo;
    }

    @Override
    public Object visitMethodDeclaration(programParser.MethodDeclarationContext ctx) {
    
         MethodType tipo = new MethodType();
        
        Scope scopeMethod = new Scope();
        
        scopeActual.addSiguiente(scopeMethod);
        scopeMethod.setAnterior(scopeActual);
        scopeActual = scopeMethod;
        
       
        tipo.setLiteralTipo(ctx.getChild(0).getText());
        String nombreMetodo = ctx.getChild(1).getText();
        tipo.setNombreVariable(nombreMetodo);
        if (nombreMetodo.equals("main")){
            Visitor.verificadorMain = true;
        }
        for (int i = 0;i<ctx.getChildCount();i++){
            Symbol simbolo = (Symbol)visit(ctx.getChild(i));
            if (simbolo != null){
                if (((Type)simbolo.getTipo()).isParametro())
                    tipo.addParameter(simbolo);
                this.tablaSimbolos.addSymbol(simbolo, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
                    
                
            }
        }
        
        
        scopeActual = scopeActual.getAnterior();
        Symbol simbolo = new Symbol(++autoincrement, scopeActual.getIdScope(),tipo);
        
        return (T)simbolo;
    }

    @Override
    public T visitBlock(programParser.BlockContext ctx) {
      for (int i = 0;i<ctx.getChildCount();i++){
           Symbol simbolo = (Symbol)visit(ctx.getChild(i));
            if (simbolo != null){
                 
                this.tablaSimbolos.addSymbol(simbolo,ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());
                
            }
      }
   
      return (T)null;
    }
    
    @Override
    public T visitParameterID(programParser.ParameterIDContext ctx) {
        //System.out.println("ENTRA");
        Type tipo = new Type(ctx.getChild(1).getText(),ctx.getChild(0).getText());
        Symbol simbolo = new Symbol(++autoincrement,this.scopeActual.getIdScope(),tipo);
        tipo.setParametro();
        
        return (T)simbolo;
    }

 

    
    @Override
    public T visitVarType(programParser.VarTypeContext ctx){
        
        
        return (T)ctx.getText();
        
    }

    @Override
    public T visitStatementIF(programParser.StatementIFContext ctx) {
    
        Scope scopeIF = new Scope();
        
        scopeActual.addSiguiente(scopeIF);
        scopeIF.setAnterior(scopeActual);
        scopeActual = scopeIF;
        
         for (int i = 0;i<ctx.getChildCount();i++){
            if (this.visit(ctx.getChild(i)) != null){
                String tipo = (String)this.visit(ctx.getChild(i));
                if (!tipo.contains("boolean")){
                    agregarLog("Error: se espera expresión booleana", ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());
                }
            }
        }
         scopeActual = scopeActual.getAnterior();
         
       
        return (T)null;
    }

    @Override
    public T visitStatementWhile(programParser.StatementWhileContext ctx) {
       
        
        Scope scopeWhile = new Scope();
        
        scopeActual.addSiguiente(scopeWhile);
        scopeWhile.setAnterior(scopeActual);
        scopeActual = scopeWhile;
        
        for (int i = 0;i<ctx.getChildCount();i++){
            if (this.visit(ctx.getChild(i)) != null){
                String tipo = (String)this.visit(ctx.getChild(i));
                if (!tipo.contains("boolean")){
                    agregarLog("Error: se espera expresión booleana", ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());
                }
            }
        }
         scopeActual = scopeActual.getAnterior();
         
      
        return (T)null;
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
    public T visitLocation(programParser.LocationContext ctx) {
        this.visit(ctx.getChild(0));
        return (T)ctx.getChild(0).getText(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitStatementLocation(programParser.StatementLocationContext ctx) {
        
        //aqui tengo el nombre de la variable
        String nombreVar = (String)this.visit(ctx.getChild(0));
        if (nombreVar.contains("["))
            nombreVar = nombreVar.substring(0,nombreVar.indexOf("["));
        
        //ahora verifico que ya esté declarada.
        boolean encontrado = this.tablaSimbolos.revisarNombreVar(nombreVar, scopeActual);
        System.out.println("");
        if (!encontrado){
            System.out.println("Variable no declarada " + nombreVar+ ": línea " + ctx.getStart().getLine()+
                    " columna: " + ctx.getStart().getCharPositionInLine());
        }
        System.out.println("");
        if (encontrado){
            Symbol simboloEncontrado = this.tablaSimbolos.showSymbol(nombreVar, scopeActual);

            String tipo =   ((Type)simboloEncontrado.getTipo()).getLiteralTipo();
            String tipoDeclarado =  (String)this.visit(ctx.getChild(2));
           
            System.out.println(tipo);
            System.out.println(tipoDeclarado);
            if (tipoDeclarado.contains("literal")){
                 
                if (tipoDeclarado.contains(tipo))
                    agregarLog("Tipo Correcto " + tipo,ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());

                else{
                        System.out.println("Tipo incorrecto");
                        agregarLog("Error: tipo incorrecto",ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());

                        Visitor.verificacion = false;
                }
            }
            else{
                String search = buscarRecursivo(tipoDeclarado,tipo);
                if (search.contains(tipo)){
                    System.out.println("Tipo Correcto");
                    agregarLog("Tipo Correcto "+ tipo,ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());

                }
                else{
                    System.out.println("Tipo Incorrecto");
                    agregarLog("Error tipo incorrecto " + tipo,ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());
                }
                
            }
        }
        
        //ahora reviso que tenga el tipo correcto.
        
        
        return null; //To change body of generated methods, choose Tools | Templates.
    }

    public String buscarRecursivo(String nombreVar,String tipo){
         boolean encontrado = this.tablaSimbolos.revisarNombreVar(nombreVar, scopeActual);
        System.out.println("");
        if (!encontrado){
            /*System.out.println("Variable no declarada: línea " + ctx.getStart().getLine()+
                    " columna: " + ctx.getStart().getCharPositionInLine());*/
        }
        System.out.println("");
        if (encontrado){
            Symbol simboloEncontrado = this.tablaSimbolos.showSymbol(nombreVar, scopeActual);
            return ((Type)simboloEncontrado.getTipo()).getLiteralTipo();
           
           
         }
        
        return "";
    }
    
    @Override
    public Object visitInt_literal(programParser.Int_literalContext ctx) {
        return ANTGui.ruleNames[ctx.getRuleContext().getRuleIndex()];
        
    }

    @Override
    public Object visitChar_literal(programParser.Char_literalContext ctx) {
       return ANTGui.ruleNames[ctx.getRuleContext().getRuleIndex()]; 
    }

    @Override
    public Object visitBoolean_literal(programParser.Boolean_literalContext ctx) {
        return ANTGui.ruleNames[ctx.getRuleContext().getRuleIndex()];
    }

    @Override
    public T visitStatementReturn(programParser.StatementReturnContext ctx) {
        
         String currentReturnType = (String)this.visit(ctx.getChild(1));
         Symbol simbolo = this.tablaSimbolos.showSymbol(currentReturnType, scopeActual);
         if (simbolo != null )
             currentReturnType = ((Type)simbolo.getTipo()).getLiteralTipo();
         if (currentReturnType == null)
             currentReturnType = "void";
         System.out.println("Nombre método "+ ctx.getParent().getParent().getChild(1).getText());
       
         System.out.println(autoincrement);
         
            if (ctx.getParent().getParent().getChild(0).getChild(0) != null){
                String returnType = ctx.getParent().getParent().getChild(0).getChild(0).getText();
                System.out.println(returnType);
                System.out.println(currentReturnType);
                if (!currentReturnType.contains(returnType))
                    agregarLog("Error: return type "+returnType+ " incorrecto",ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());
                else
                    agregarLog("Return type "+ returnType+" del método es correcto", ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());
                }
        return null;
    }

    
    

    @Override
    public Object visitMethodCall(programParser.MethodCallContext ctx) {
        int validCount = 0;
        int countValidSymbols = 0;
        String returnMethodCall ="";
        for (int j = 0;j<ctx.getChildCount();j++){
            if (this.visit(ctx.getChild(j))!= null){
                validCount++;
            }
        }
         String nombreMetodo = ctx.getText();
         nombreMetodo = nombreMetodo.substring(0,nombreMetodo.indexOf("("));
         Symbol metodo = this.tablaSimbolos.showSymbol(nombreMetodo, scopeActual);
        for (int i = 0;i<ctx.getChildCount();i++){
            if (this.visit(ctx.getChild(i))!= null){
                String varName = (String)this.visit(ctx.getChild(i));
                Symbol found = this.tablaSimbolos.showSymbol(varName, scopeActual);
               
                System.out.println("TEST");
                System.out.println(found);
               
                System.out.println(nombreMetodo);
              
                MethodType tipoMetodo = (MethodType)metodo.getTipo();
               
               
                ArrayList arraySimbolos = tipoMetodo.getParameters();
                if (arraySimbolos.size() != validCount){
                    agregarLog("Error no coincide el número de argumentos y parámetros", ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
                    verificacion = false;
                }
                
                Symbol simboloInterno = (Symbol)arraySimbolos.get(countValidSymbols);
                String stringInternalType = ((Type)simboloInterno.getTipo()).getLiteralTipo();
                String stringExternalType = ((Type)found.getTipo()).getLiteralTipo();
                if (!stringExternalType.equals(stringInternalType)){
                    agregarLog("Error: Tipo incorrecto en argumentos " + ((Type)found.getTipo()).getNombreVariable(),ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());
                    verificacion = false;
                }
                else{
                    agregarLog("Tipo correcto en argumentos "+ ((Type)found.getTipo()).getNombreVariable(),ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());
                }
                System.out.println(arraySimbolos);
                countValidSymbols++;
            }
            
        }
         MethodType tipoMetodo = (MethodType)metodo.getTipo();
         returnMethodCall = tipoMetodo.getLiteralTipo();
        System.out.println("method call " + returnMethodCall+" "+tipoMetodo.getNombreVariable());
         
        return returnMethodCall+"_literal";
    }

    @Override
    public T visitLocationArray(programParser.LocationArrayContext ctx) {
            String compare = ((String)this.visit(ctx.getChild(2)));
            boolean verArray = true;
            if (!compare.contains("int") && !compare.isEmpty()){
                agregarLog("Error: invalid return type " + ((String)this.visit(ctx.getChild(2))), ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());
                verArray = false;
            }
            String nombreArray = ctx.getChild(0).getText();
            Symbol simboloArray = this.tablaSimbolos.showSymbol(nombreArray, scopeActual);
            Type tipoArray = ((Type)simboloArray.getTipo());
            if (tipoArray.isArreglo()== false){
                agregarLog("Error: " + tipoArray.getNombreVariable() + " no es un array", ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine() );
                 verArray = false;
            }
            if (verArray){
                agregarLog("Array " + tipoArray.getNombreVariable()+" tipo correcto" , ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
            }
            
        return null;
    }

    @Override
    public Object visitRelationExpr(programParser.RelationExprContext ctx) {
        int cant = ctx.getChildCount();
        if (cant>1){
            return "boolean";
        }
        return this.visit(ctx.getChild(0));
    }
    
    @Override
    public Object visitEqExprEqOp(programParser.EqExprEqOpContext ctx) {
        int cant = ctx.getChildCount();
        
        if (cant>1){
            return "boolean_literal";
        }
        return this.visit(ctx.getChild(0));
    }

    @Override
    public Object visitCond_op_or(programParser.Cond_op_orContext ctx) {
        int cant = ctx.getChildCount();
        
        System.out.println(cant + "CANT OP OR");
        return "boolean_literal";
        
        //return this.visit(ctx.getChild(0));
    }
    
    
    
    public void agregarLog(String mensaje, int linea, int columna){
        
        jTextArea3.setText(jTextArea3.getText()+"\n"+
                "linea: " + linea +": "+ columna +  " " + mensaje
                );
        
    }
    
}