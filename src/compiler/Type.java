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
    
    private String  literal_tipo;
    private String  nombreVariable="";
    private boolean parametro;     
    private boolean arreglo; 
    private int tamaño;
    
    public Type(String nombre){
        this.literal_tipo = nombre;
        this.parametro=false;
        this.arreglo=false;
       
    }
    public Type(){
        this.parametro=false;
        this.arreglo=false;
    }

    public Type(String nombre,String literal_tipo) {
        this.nombreVariable = nombre;
        this.literal_tipo = literal_tipo;
        this.parametro = false;
        this.arreglo =false;
    }

   
    public String getNombreVariable() {
        return nombreVariable;
    }

    public void setNombreVariable(String nombre) {
        this.nombreVariable = nombre;
    }

   
    public boolean isParametro() {
        return parametro;
    }

    public void setParametro() {
        this.parametro = true;
    }

    public boolean isArreglo() {
        return arreglo;
    }

    public void setArreglo(int size) {
        this.tamaño = size;
        this.arreglo = true;
    }

    public String getLiteralTipo() {
        return literal_tipo;
    }

    public void setLiteralTipo(String nombre_tipo) {
        this.literal_tipo = nombre_tipo;
    }

   

    public int getTamaño() {
        return tamaño;
    }

    
    @Override
    public String toString() {
        if (this.arreglo == false)
            return "Type{" + " literal_tipo=" + literal_tipo + ", nombreVar=" + nombreVariable + '}';
        return "Type{" + " literal_tipo=" + literal_tipo + ", nombreVar=" + nombreVariable + 
                " ,arreglo tamaño= " + this.tamaño
                +'}';
        
    }

    
    
    
    
    
}
