/**
* Universidad Del Valle de Guatemala
* 12-feb-2016
* Pablo Díaz 13203
*/

package compiler;

import antlr4.programBaseVisitor;
import antlr4.programParser;
import static gui.ANTGui.jTextArea3;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


/**
 *
 * @author Pablo
 * @param <T>
 */
public class Visitor<T> extends programBaseVisitor {
    
    public int ambitoActual=0;
   
    public static SymbolTable tablaSimbolos;
    public int autoincrement = 0;
    public Scope scopeActual;
    public static boolean verificacion = true;
    public static boolean verificadorMain;
    public Stack literals = new Stack();
    public Stack<String> methods = new Stack();
    
    public Visitor() {
       
        scopeActual = new Scope();
        scopeActual.setIdScope(ambitoActual);
        tablaSimbolos = new SymbolTable();
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
            tablaSimbolos.addSymbol((Symbol)this.visit(ctx.getChild(i)),ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());
        }
        
        
        return (T)"void";//useless
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
                tablaSimbolos.addSymbol(simbolo,ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine());
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
        Type tipo = new Type(ctx.getChild(1).getText(),(String)this.visit(ctx.getChild(0)));
        Symbol simbolo = new Symbol(++autoincrement,scopeActual.getIdScope(),tipo);
       
        
        return (T)simbolo;
    }

    @Override
    public T visitVarDeclarationArray(programParser.VarDeclarationArrayContext ctx) {
        
        Type tipo = new Type(ctx.getChild(1).getText(),(String)visit(ctx.getChild(0)));
        tipo.setArreglo(Integer.parseInt(ctx.getChild(3).getText()));
        
        if (tipo.getTamaño()<=0){
            agregarLog("Error: tamaño de declaración de array debe ser > 0", ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),true);
            return null;
        }
        
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
        
        if (ctx.getChildCount()> 1){
            String returnString = "";
            for (int i =0;i<ctx.getChildCount();i++){
                returnString = ctx.getChild(i).getText() + " ";
            }
            return (T)ctx.getChild(1).getText();
        }
        
        
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
                Symbol var = tablaSimbolos.showSymbol(tipo, scopeActual);
                if (var != null){
                    tipo = ((Type)var.getTipo()).getLiteralTipo();
                  
                }
                if (!tipo.contains("boolean")){
                    agregarLog("Error: se espera expresión booleana " + tipo, ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),true);
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
                    agregarLog("Error: se espera expresión booleana", ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),true);
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
        for (int i = 0;i<ctx.getChildCount();i++){
            T var = (T)this.visit(ctx.getChild(i));
            if (i==1){
                return var;
            }
        }
        
        
        return (T)ctx.getChild(0).getText(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitStatementLocation(programParser.StatementLocationContext ctx) {
        
        //aqui tengo el nombre de la variable
        String nombreVar = (String)this.visit(ctx.getChild(0));
        System.out.println("Nombre array-> "+ nombreVar);
        
        //ahora verifico que ya esté declarada.
        boolean encontrado = tablaSimbolos.revisarNombreVar(nombreVar, scopeActual);
        System.out.println("");
        if (!encontrado){
            agregarLog("Error: Usando variable no declarada " + nombreVar, ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),true);
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
                    agregarLog("Tipo Correcto " + tipo,ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),false);

                else{
                        System.out.println("Tipo incorrecto");
                        agregarLog("Error: tipo incorrecto",ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),true);

                       
                }
            }
            else{
                Symbol symbol = buscarRecursivo(tipoDeclarado,tipo);
                if (symbol != null){
                    String search = ((Type)symbol.getTipo()).getLiteralTipo();
                     if (((Type)symbol.getTipo()).isArreglo()==true){

                         if (((Type)simboloEncontrado.getTipo()).isArreglo()==true){
                           agregarLog("Tipo Correcto array "+ tipo,ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),false);

                         }
                         
                         else{
                             
                             agregarLog("Error tipo incorrecto, no es array " + tipo,ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),true);

                         }

                     }
                     else if (((Type)simboloEncontrado.getTipo()).isArreglo()==true){

                         if (((Type)symbol.getTipo()).isArreglo()==true){
                           agregarLog("Tipo Correcto array "+ tipo,ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),false);

                         }
                        
                         else{
                            agregarLog("Error tipo incorrecto, no es array " + tipo,ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),true);

                         }

                     }
                     else{

                        if (search.contains(tipo)){

                            System.out.println("Tipo Correcto");
                            agregarLog("Tipo Correcto "+ tipo,ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),false);

                        }
                        else{

                            System.out.println("Tipo Incorrecto");
                            agregarLog("Error tipo incorrecto " + tipo,ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),true);
                        }
                    }
                }
            }
        }
        
        //ahora reviso que tenga el tipo correcto.
        
        
        return null; //To change body of generated methods, choose Tools | Templates.
    }

    public Symbol buscarRecursivo(String nombreVar,String tipo){
        boolean encontrado = this.tablaSimbolos.revisarNombreVar(nombreVar, scopeActual);
        System.out.println("");
        if (!encontrado){
            /*System.out.println("Variable no declarada: línea " + ctx.getStart().getLine()+
                    " columna: " + ctx.getStart().getCharPositionInLine());*/
        }
        System.out.println("");
        if (encontrado){
            Symbol simboloEncontrado = this.tablaSimbolos.showSymbol(nombreVar, scopeActual);
            return simboloEncontrado;
           
           
         }
        
        return null;
    }
    
    @Override
    public Object visitInt_literal(programParser.Int_literalContext ctx) {
        literals.push(ctx.getChild(0).getText());
        return "int_literal";
        
    }

    @Override
    public Object visitChar_literal(programParser.Char_literalContext ctx) {
       return "char_literal";
    }

    @Override
    public Object visitBoolean_literal(programParser.Boolean_literalContext ctx) {
        return "boolean_literal";
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
                    agregarLog("Error: return type "+returnType+ " incorrecto",ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),true);
                else
                    agregarLog("Return type "+ returnType+" del método es correcto", ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),false);
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
         Symbol metodo = tablaSimbolos.showSymbol(nombreMetodo, scopeActual);
         if (metodo == null){
             return "";
         }
         
          MethodType tipoMetodo = (MethodType)metodo.getTipo();
          ArrayList arraySimbolos = tipoMetodo.getParameters();
          if (arraySimbolos.size() != validCount){
            agregarLog("Error no coincide el número de argumentos y parámetros", ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine(),true);
           
            return "";
        }
        for (int i = 0;i<ctx.getChildCount();i++){
            if (this.visit(ctx.getChild(i))!= null){
                String varName = (String)this.visit(ctx.getChild(i));
                Symbol found = tablaSimbolos.showSymbol(varName, scopeActual);
               
                System.out.println("TEST");
                System.out.println(found);
                System.out.println(varName);
               
                System.out.println(nombreMetodo);
              
               
                
                
                Symbol simboloInterno = (Symbol)arraySimbolos.get(countValidSymbols);
                if (simboloInterno == null){
                    agregarLog("Error: variable no declarada"+varName,ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),true );
                }
                String stringInternalType = ((Type)simboloInterno.getTipo()).getLiteralTipo();
                String stringExternalType = "";
                
                if (found!=null){
                    stringExternalType = ((Type)found.getTipo()).getLiteralTipo();
                    varName = ((Type)found.getTipo()).getNombreVariable();
                }
                else{
                    if (varName.contains("literal"))
                          stringExternalType = varName;// int_literal, char_literal, boolean_literal
                    else{
                        agregarLog("Error: la variable no existe " + varName,ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),true);
                        
                    }
                        
                }
                
                
                    
                if (!stringExternalType.contains(stringInternalType)){
                    agregarLog("Error: Tipo incorrecto en argumentos " + varName,ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),true);
                   
                }
                else{
                    agregarLog("Tipo correcto en argumentos "+ varName,ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),false);
                }
                System.out.println(arraySimbolos);
                countValidSymbols++;
            }
            
        }
        
         returnMethodCall = tipoMetodo.getLiteralTipo();
        System.out.println("method call " + returnMethodCall+" "+tipoMetodo.getNombreVariable());
         
        return returnMethodCall+"_literal";//mafiosa que devuelve el tipo
    }

    @Override
    public T visitLocationArray(programParser.LocationArrayContext ctx) {
            
            for (int i = 0;i<ctx.getChildCount();i++){
                visit(ctx.getChild(i));
            }
        
            String compare = ((String)this.visit(ctx.getChild(2)));
            boolean verArray = true;
            if (!compare.contains("int") && !compare.isEmpty()){
                agregarLog("Error: invalid return type " + ((String)this.visit(ctx.getChild(2))), ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),true);
                verArray = false;
              
            }
            String nombreArray = ctx.getChild(0).getText();
            Symbol simboloArray = tablaSimbolos.findAllScopes(nombreArray);
            if (simboloArray == null){
               
                return null;
            }
            Type tipoArray = ((Type)simboloArray.getTipo());
            if (tipoArray.isArreglo()== false){
                agregarLog("Error: " + tipoArray.getNombreVariable() + " no es un array", ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),true );
                verArray = false;
               
              
            }
            if (verArray){
                int tamañoArray = ((Type)simboloArray.getTipo()).getTamaño();
                int tamañoActual = Integer.parseInt((String)literals.pop());
                if (tamañoActual > tamañoArray-1 || tamañoActual < 0){
                    agregarLog("Error: index "+tipoArray.getNombreVariable()+" out of bounds ", ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine(),true);
                    verArray = false;
               
                   
                }
                
            }
            
            if (verArray){
                agregarLog("Array " + tipoArray.getNombreVariable()+" tipo correcto" , ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine(),false);
                
            }
           
            
        return (T)compare;
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
    public Object visitExpressionCondOpOr(programParser.ExpressionCondOpOrContext ctx) {
        for (int i = 0;i<ctx.getChildCount();i++){
            this.visit(ctx.getChild(i));
        }
        return this.visit(ctx.getChild(1)); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
    @Override
    public Object visitCond_op_or(programParser.Cond_op_orContext ctx) {
        int cant = ctx.getChildCount();
        for (int i = 0;i<ctx.getChildCount();i++){
            this.visit(ctx.getChild(i));
        }
        System.out.println(cant + "CANT OP OR");
        return "boolean_literal";
        
        //return this.visit(ctx.getChild(0));
    }

    @Override
    public Object visitAndExprCondOpAnd(programParser.AndExprCondOpAndContext ctx) {
        for (int i = 0;i<ctx.getChildCount();i++){
            this.visit(ctx.getChild(i));
        }
        return "boolean_literal"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitUnaryExprNot(programParser.UnaryExprNotContext ctx) {
        for (int i = 0;i<ctx.getChildCount();i++){
             this.visit(ctx.getChild(i));
        }
        return "boolean_literal"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitAddExprMinusPlusOp(programParser.AddExprMinusPlusOpContext ctx) {
        
        String firstOpType = (String)this.visit(ctx.getChild(0));
        String secondOpType = (String)this.visit(ctx.getChild(2));
        String firstOp = (String)(ctx.getChild(0).getChild(0).getText());
        String secondOp = (String)(ctx.getChild(2).getChild(0).getText());
        System.out.println("OPS");
        System.out.println(firstOpType);
        System.out.println(secondOpType);
        if (!firstOpType.contains("literal")){
            Symbol firstSymbol = tablaSimbolos.showSymbol(firstOp, scopeActual);
            System.out.println(firstOp);
            if (firstSymbol != null)
                firstOpType = ((Type)firstSymbol.getTipo()).getLiteralTipo();
            else{
               
                firstOpType =  (String) visit(ctx.getChild(0));
               
                
            }
                
            
        }
          if (!secondOpType.contains("literal")){
            Symbol secondSymbol = tablaSimbolos.showSymbol(firstOp, scopeActual);
            secondOpType = ((Type)secondSymbol.getTipo()).getLiteralTipo();
        }
        if (!firstOpType.contains("int")){
            agregarLog(firstOpType+" "+firstOp+" no es de tipo int en la suma/resta",ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),true);
            return "";//se propaga el error
        }
        if(!secondOpType.contains("int")){
             agregarLog(secondOpType+" "+secondOp+" no es de tipo int en la suma/resta",ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),true);
             return "";//se propagar el error
        }
    
        return "int_literal";//se propaga el exito
    }

    @Override
    public Object visitLocationMethod(programParser.LocationMethodContext ctx) {
        
        
        String tipoEncontrado = "";
        String nombreStruct = ctx.getParent().getChild(0).getText();
       
        String nombreAtributo = (String)visit(ctx.getChild(1));
        String preAtributo = nombreAtributo;
        System.out.println("Nombre atributo " + nombreAtributo);
        
        Symbol simboloStruct = tablaSimbolos.showSymbol(nombreStruct, scopeActual);
        
        
        System.out.println("simbolo struct " + simboloStruct);
        if (simboloStruct != null){
        if (simboloStruct.getTipo().getClass().getName().equals("compiler.Type")){
    
                
           
           
        }
        
            System.out.println("simbolo STRUCT" + simboloStruct);
         if (!((Type)simboloStruct.getTipo()).getLiteralTipo().contains("int") &&
             !((Type)simboloStruct.getTipo()).getLiteralTipo().contains("boolean")&&
             !((Type)simboloStruct.getTipo()).getLiteralTipo().contains("char")
             ){
             String buscarStruct = ((Type)simboloStruct.getTipo()).getLiteralTipo();
             Symbol showStruct = tablaSimbolos.showSymbol(buscarStruct, scopeActual);
             simboloStruct = showStruct;
             if (simboloStruct == null){
                 return "";
             }
            /// agregarLog("returning " + ((Type)simboloStruct.getTipo()).getLiteralTipo(),1,1,false);
            // return ((Type)simboloStruct.getTipo()).getLiteralTipo();
             
         }
        if (!simboloStruct.getTipo().getClass().getName().equals("compiler.StructType")){
            
            agregarLog("Error: Solo los structs tienen atributos",ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),true);
            return "";
        }
        ArrayList<Symbol> arrayStruct = ((compiler.StructType)simboloStruct.getTipo()).getMembers();
        
        boolean encontrado = false;
        System.out.println(arrayStruct);
        Type typeFound =null;
        for (int i = 0;i<arrayStruct.size();i++){
            if (((compiler.Type)arrayStruct.get(i).getTipo()).getNombreVariable().equals(nombreAtributo)){
                encontrado = true;
                tipoEncontrado = ((compiler.Type)arrayStruct.get(i).getTipo()).getLiteralTipo();
                typeFound = ((compiler.Type)arrayStruct.get(i).getTipo());
//                if (((compiler.Type)arrayStruct.get(i).getTipo()).isArreglo()
//                    && !preAtributo.contains("[")
//                    ){
//                    encontrado = false;
//                    tipoEncontrado = "";
//                }
                
                
                
            }
        }
        if (!encontrado){
            agregarLog("Error:El atributo "+nombreAtributo+" del struct " + nombreStruct + " no existe", ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine(),true);
        }
        else{
             //agregarLog("El atributo "+nombreAtributo+" del struct " + nombreStruct + " es correcto", ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine(),false);
             if (!methods.isEmpty()){
                 while(!methods.isEmpty()){
                     String atributo = methods.pop();
                     Symbol otroSimbolo = tablaSimbolos.showSymbol(typeFound.getLiteralTipo(), scopeActual);
                     if (otroSimbolo != null){
                     ArrayList<Symbol> arrayStruct2 = ((compiler.StructType)otroSimbolo.getTipo()).getMembers();
                      for (int i = 0;i<arrayStruct2.size();i++){
                            if (((compiler.Type)arrayStruct2.get(i).getTipo()).getNombreVariable().equals(atributo)){
                                encontrado = true;
                                tipoEncontrado = ((compiler.Type)arrayStruct2.get(i).getTipo()).getLiteralTipo();
                                typeFound = ((compiler.Type)arrayStruct.get(i).getTipo());
             
                            }
                        }
                     }
                    if (!encontrado){
                        agregarLog("Error:El atributo "+atributo+" del struct " + nombreStruct + " no existe", ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine(),true);
                    }
                    else{
                        Symbol last  = tablaSimbolos.showSymbol(atributo,scopeActual);
                        if (last != null){
                           tipoEncontrado = ((Type)last.getTipo()).getLiteralTipo();
                        }
                        agregarLog("El atributo "+atributo+" del struct " + nombreStruct + " es correcto", ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine(),false);
                    }
                    
                 }
             }
        }
        }
        else
            methods.push(nombreAtributo);
//        for (int j = 0;j<ctx.getChildCount();j++){
//            
//            visit(ctx.getChild(j));
//            
//        }
        
        return tipoEncontrado; //To cha return "";
    }

    @Override
    public Object visitLocationMemberArray(programParser.LocationMemberArrayContext ctx) {
        for (int i = 0;i<ctx.getChildCount();i++){
            visit(ctx.getChild(i));
        }
        return ctx.getChild(0).getChild(0).getText(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitLocationMemberMethod(programParser.LocationMemberMethodContext ctx) {
        
        Symbol methodSymbol = tablaSimbolos.findAllScopes(ctx.getChild(0).getText());
        if (methodSymbol != null){
            if (((Type)methodSymbol.getTipo()).isArreglo()){
                agregarLog("Error: la variable " +((Type)methodSymbol.getTipo()).getNombreVariable() + " es un array", ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine(),true );
            }
        }
        for (int i = 0;i<ctx.getChildCount();i++){
            visit(ctx.getChild(i));
        }
       return ctx.getChild(0).getText();
    }

    
    
    
    public void agregarLog(String mensaje, int linea, int columna, boolean error){
        
        StyledDocument doc = jTextArea3.getStyledDocument();

        Style style = jTextArea3.addStyle("", null);
        StyleConstants.setForeground(style, Color.red);
        
        if (error){
            try {
                doc.insertString(doc.getLength(), "linea: " + linea +": "+ columna +  " " + mensaje+"\n",style);
                verificacion = false;
            }
            catch (BadLocationException e){}
        }
        else{
            StyleConstants.setForeground(style, Color.blue);
            try { doc.insertString(doc.getLength(), "linea: " + linea +": "+ columna +  " " + mensaje+"\n",style); }
            catch (BadLocationException e){}
        }
        
        
        
    }


    
}