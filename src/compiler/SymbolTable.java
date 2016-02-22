/**
* Universidad Del Valle de Guatemala
* 15-feb-2016
* Pablo Díaz 13203
*/

package compiler;

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
    
    
 
    public HashMap getTabla() {
        return tabla;
    }

    public SymbolTable getRef() {
        return ref;
    }
    
    public void addSymbol(Symbol simbolo){
         if (revisarSimbolos(simbolo))
            this.tabla.put(simbolo.getId(), simbolo);
         else
             System.out.println("Variable ya declarada");
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
                value.getTipo().getNombreVariable().equals(simbolo.getTipo().getNombreVariable()))
                return false;
        }
        return true;
    }

}
