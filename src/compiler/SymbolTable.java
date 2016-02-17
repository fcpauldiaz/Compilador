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
    
    
    private HashMap<Integer,Symbol> tabla;
    private final SymbolTable ref;

    public SymbolTable(SymbolTable ref) {
        this.tabla = new HashMap();
        this.ref = ref;
    }
    public SymbolTable() {
        this.tabla = new HashMap();  
        ref = null;
    }
    
    
   /* public void insert(String token, Type t,int b){
        tabla.put(token, new Id(token,t,b));
    }
    
    public Id get(String token) {
        for (SymbolTable tab = this ; tab != null ; tab = tab.outer) {
        Id id = (Id)(tab.table.get(token));
        if ( id != null ) return id;
        }
    return null;
    }*/

    public HashMap getTabla() {
        return tabla;
    }

    public SymbolTable getRef() {
        return ref;
    }
    
    public void addSymbol(Symbol simbolo){
        this.tabla.put(simbolo.getId(), simbolo);
    }
    
    public void printSymbolTable(){
        for (Map.Entry<Integer, Symbol> entry : tabla.entrySet()) {
            int key = entry.getKey();
            Symbol value = entry.getValue();
            System.out.println("key, " + key + " value " + value);
        }
    }

}
