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
public class Type {
    
    private int     tamaño = 0;
    private String  nombre_tipo;
    private String  nombre="";
    private boolean simple;    
    private boolean estructura; 
    private boolean parametro;     
    private boolean arreglo;     
    
    public Type(String nombre, int tamaño){
        this.nombre_tipo = nombre;
        this.tamaño = tamaño;
    }

    public Type() {
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

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isSimple() {
        return simple;
    }

    public void setSimple(boolean simple) {
        this.simple = simple;
    }

    public boolean isEstructura() {
        return estructura;
    }

    public void setEstructura(boolean estructura) {
        this.estructura = estructura;
    }

    public boolean isParametro() {
        return parametro;
    }

    public void setParametro(boolean parametro) {
        this.parametro = parametro;
    }

    public boolean isArreglo() {
        return arreglo;
    }

    public void setArreglo(boolean arreglo) {
        this.arreglo = arreglo;
    }

    public String getNombre_tipo() {
        return nombre_tipo;
    }

    public void setNombre_tipo(String nombre_tipo) {
        this.nombre_tipo = nombre_tipo;
    }

    @Override
    public String toString() {
        return "Type{" + "tamaño=" + tamaño + ", nombre_tipo=" + nombre_tipo + ", nombre=" + nombre + '}';
    }

    
    
    
    
    
}
