/**
* Universidad Del Valle de Guatemala
* 15-feb-2016
* Pablo Díaz 13203
*/

package compiler;

import java.util.HashMap;

/**
 *
 * @author Pablo
 */
public class SymbolTable {
    
    
    private HashMap tabla;
    private SymbolTable ref;

    public SymbolTable(SymbolTable ref) {
        this.tabla = new HashMap();
        this.ref = ref;
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
    
    
    

}
