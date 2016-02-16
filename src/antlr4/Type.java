/**
* Universidad Del Valle de Guatemala
* 15-feb-2016
* Pablo Díaz 13203
*/

package antlr4;

/**
 *
 * @author Pablo
 */
public class Type {
    
    public int tamaño = 0;
    public String nombre="";
    
    public Type(String s, int w){
        nombre = s;
        tamaño=w;
    }
    
    public static final Type
            Int = new Type("int",4),
            Float = new Type("float",8),
            Char = new Type("char",1),
            Bool = new Type("bool",1);
    
    public static boolean numeric(Type p){
        return p==Type.Char || p==Type.Int || p == Type.Float;
    }
    
    public static Type max(Type p1, Type p2) {
    if (!numeric(p1) || !numeric(p2)) 
        return null;
     else if (p1 == Type.Float || p2 == Type.Float)
         return Type.Float;
    else if (p1 == Type.Int || p2 == Type.Int)
        return Type.Int;
    else 
        return Type.Char;
    }
    

}
