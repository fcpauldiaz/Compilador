/**
* Universidad Del Valle de Guatemala
* 15-feb-2016
* Pablo Díaz 13203
*/

package compiler;

/**
 *
 * @author Pablo
 */
public class Array extends Type {
    public Type of;
    public int size = 1;
    
    public Array(int sz, Type p) {
        super("[]", sz * p.getTamaño());
        size = sz;
        of = p;
    }
    
    @Override
    public String toString() { 
        return "[" + size + "] " + of.toString(); 
    }
}
