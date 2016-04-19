/**
* Universidad Del Valle de Guatemala
* 19-abr-2016
* Pablo Díaz 13203
*/

package compiler;

/**
 *
 * @author Pablo
 */
public class GlobalStackControl {

    
    private int posicion;
    private String identificador;
    private String tipo;
    public static int staticPos = 0;
   
    public GlobalStackControl(int posicion, String identificador, String tipo) {
         this.posicion = GlobalStackControl.staticPos;
         
         GlobalStackControl.staticPos = this.posicion + posicion;
         this.identificador = identificador;
         this.tipo = tipo;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
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

    public static int getStaticPos() {
        return staticPos;
    }

    public static void setStaticPos(int staticPos) {
        GlobalStackControl.staticPos = staticPos;
    }

    @Override
    public String toString() {
        return "GlobalStackControl{" + "posicion=" + posicion + ", identificador=" + identificador + ", tipo=" + tipo + '}';
    }

    
    
}
