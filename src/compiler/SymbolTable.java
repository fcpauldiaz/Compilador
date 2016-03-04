/**
* Universidad Del Valle de Guatemala
* 15-feb-2016
* Pablo Díaz 13203
*/

package compiler;

import static compiler.ANTGui.jTextArea3;
import java.util.HashMap;
import java.util.Map;

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
         if (revisarSimbolos(simbolo))
            this.tabla.put(simbolo.getId(), simbolo);
         else{
            System.out.println("Variable ya declarada");
            agregarLog("Error: Variable ya declarada " + ((Type)(simbolo.getTipo())).getNombreVariable(), lineaActual, columnaActual);
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
        for (Map.Entry<Integer, Symbol> entry : tabla.entrySet()) {
            int key = entry.getKey();
            Symbol value = entry.getValue();
            if (value.getAmbito()==simbolo.getAmbito() 
                && 
                ((Type)value.getTipo()).getNombreVariable().equals(((Type)simbolo.getTipo()).getNombreVariable()))
                return false;
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
        while (scope!=null){
            for (Map.Entry<Integer, Symbol> entry : tabla.entrySet()) {
                int key = entry.getKey();
                Symbol value = entry.getValue();
                
                String varName = ((Type)value.getTipo()).getNombreVariable();
                int ambito = value.getAmbito();
                if (varName.equals(nombreVar)
                    && 
                   ambito == scope.getIdScope())
                    return value;
            }
            scope = scope.getAnterior();
        }
        return null;
    
    }
    
    public void agregarLog(String mensaje, int linea, int columna){
        jTextArea3.setText(jTextArea3.getText()+"\n"+
                "linea: " + linea +": "+ columna +  " " + mensaje
                );
    }
}
