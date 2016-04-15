/**
* Universidad Del Valle de Guatemala
* 15-abr-2016
* Pablo Díaz 13203
*/

package compiler;

/**
 *
 * @author Pablo
 */
public class StackControl {

    private int posicion;
    private String valor;
    private String identificador;
    private String tipo;
    public static int staticPos = 0;

  
    public StackControl(String valor) {
      
        this.valor = valor;
    }
      public StackControl(int posicion, String identificador, String tipo) {
         this.posicion = StackControl.staticPos + posicion;
         
         StackControl.staticPos = this.posicion;
         this.identificador = identificador;
         this.tipo = tipo;
    }

    public int getPosicion() {
        return posicion;
    }

    public void aumentarPos(int posicion) {
         this.posicion = StackControl.staticPos + posicion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "StackControl{" +"posicion=" + posicion + " ,valor=" + valor + ", identificador=" + identificador + ", tipo=" + tipo + '}';
    }

    
   
    
    
}
