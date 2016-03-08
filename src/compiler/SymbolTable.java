/**
* Universidad Del Valle de Guatemala
* 15-feb-2016
* Pablo Díaz 13203
*/

package compiler;

import static compiler.ANTGui.jTextArea3;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Pablo
 */
public class SymbolTable {
    
    
    private final HashMap<Integer,Symbol> tabla;
    private final SymbolTable ref; //not used

    public SymbolTable(SymbolTable ref) {
        this.tabla = new HashMap();
        this.ref = ref;
    }
    public SymbolTable() {
        this.tabla = new HashMap(100);  
        ref = null;
    }
    
    
 
    public HashMap<Integer,Symbol> getTabla() {
        return tabla;
    }

    public SymbolTable getRef() {
        return ref;
    }
    
    public void addSymbol(Symbol simbolo, int lineaActual, int columnaActual){
        if (simbolo != null){
            if (revisarSimbolos(simbolo))
               this.tabla.put(simbolo.getId(), simbolo);
            else{
               System.out.println("Variable ya declarada");
               agregarLog("Error: Variable ya declarada " + ((Type)(simbolo.getTipo())).getNombreVariable(), lineaActual, columnaActual,true);
           }
        }
    }
    
    public void printSymbolTable(){
        for (Map.Entry<Integer, Symbol> entry : tabla.entrySet()) {
            int key = entry.getKey();
            Symbol value = entry.getValue();
            System.out.println("key, " + key + " value " + value);
        }
    }
    /**
     * Método para revisar variables repetidas en un mismo ámbito
     * @param simbolo
     * @return boolean
     * TRUE = valido
     * FALSE = no valido
     */
    public boolean revisarSimbolos(Symbol simbolo){
        if (simbolo == null)
            return false;
        for (Map.Entry<Integer, Symbol> entry : tabla.entrySet()) {
                int key = entry.getKey();
                Symbol value = entry.getValue();
                if (value.getAmbito()==simbolo.getAmbito() 
                    && 
                    ((Type)value.getTipo()).getNombreVariable().equals(((Type)simbolo.getTipo()).getNombreVariable())){
                    if (simbolo.getTipo().getClass().getName().equals("compiler.MethodType")){
                        if ( ((MethodType)value.getTipo()).getParameters().size() !=
                           ((MethodType)simbolo.getTipo()).getParameters().size()  ){
                            return true;
                        }
                    }
                    
                    return false;
                }
            }
            return true;
        
    }

    public boolean revisarNombreVar(String nombreVar, Scope ambitoActual){
        Scope scope = ambitoActual;
        while (scope!=null){
            for (Map.Entry<Integer, Symbol> entry : tabla.entrySet()) {
                int key = entry.getKey();
                Symbol value = entry.getValue();
                String varName =  ((Type)value.getTipo()).getNombreVariable();
                int ambito = value.getAmbito();
                if (varName.equals(nombreVar)
                    && 
                   ambito == scope.getIdScope())
                    return true;
            }
            scope = scope.getAnterior();
        }
        return false;
    }
    
    public Symbol showSymbol(String nombreVar,Scope ambitoActual){
         Scope scope = ambitoActual;
         try{
        while (scope!=null){
            for (Map.Entry<Integer, Symbol> entry : tabla.entrySet()) {
                int key = entry.getKey();
                Symbol value = entry.getValue();
                
                String varName = ((Type)value.getTipo()).getNombreVariable();
                int ambito = value.getAmbito();
                //System.out.println(varName);
                //System.out.println(nombreVar);
                if (varName.trim().equals(nombreVar.trim())
                    && 
                   ambito == scope.getIdScope())
                    return value;
            }
            scope = scope.getAnterior();
        }
         }catch(Exception e){}
        return null;
    
    }
    
    public Symbol findAllScopes(String nombreVar){
       
         try{
        
            for (Map.Entry<Integer, Symbol> entry : tabla.entrySet()) {
                int key = entry.getKey();
                Symbol value = entry.getValue();
                
                String varName = ((Type)value.getTipo()).getNombreVariable();
                int ambito = value.getAmbito();
                //System.out.println(varName);
                //System.out.println(nombreVar);
                if (varName.trim().equals(nombreVar.trim()))
                   
                    return value;
            }
         
         }catch(Exception e){}
        return null;
    
    }
    
    public void agregarLog(String mensaje, int linea, int columna, boolean error){
        
        StyledDocument doc = jTextArea3.getStyledDocument();

        Style style = jTextArea3.addStyle("I'm a Style", null);
        StyleConstants.setForeground(style, Color.red);
        
        if (error){
            try { doc.insertString(doc.getLength(), "linea: " + linea +": "+ columna +  " " + mensaje+"\n",style); }
            catch (BadLocationException e){}
        }
        else{
            StyleConstants.setForeground(style, Color.blue);
            try { doc.insertString(doc.getLength(), "linea: " + linea +": "+ columna +  " " + mensaje+"\n",style); }
            catch (BadLocationException e){}
        }
        
        /*jTextArea3.setText(jTextArea3.getText()+"\n"+
                "linea: " + linea +": "+ columna +  " " + mensaje
        );*/
        
    }
}
