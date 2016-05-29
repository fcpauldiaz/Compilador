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
    public Scope scope;

  
    public StackControl(String valor, Scope scope) {
      
        this.valor = valor;
        this.scope = scope;
    }
    public StackControl(int posicion, String identificador, String tipo, Scope scope) {
         this.posicion = StackControl.staticPos;
         
         StackControl.staticPos = this.posicion + posicion;
         this.identificador = identificador;
         this.tipo = tipo;
         this.scope = scope;
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

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

   public boolean verificarScope(Scope actual){
      Scope verificador = actual;
      while (verificador != null){
          if (verificador.getIdScope() == scope.getIdScope()){
              return true;
          }
          else {
              verificador = verificador.getAnterior();
          }
          
      }
      return false;
   }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
    
    
    

    @Override
    public String toString() {
        return "StackControl{" +"posicion=" + posicion + " ,valor=" + valor + ", identificador=" + identificador + ", tipo=" + tipo + '}';
    }

    
   
    
    
}
