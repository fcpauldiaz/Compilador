/**
* Universidad Del Valle de Guatemala
* 01-abr-2016
* Pablo Díaz 13203
*/

package compiler;

/**
 *
 * @author Pablo
 */
public class Registro {
    
    private String registro;
    private int numRegistro;
    private boolean ocupado;
    private String varAssociated;

    public Registro(int num) {
        this.registro = "R";
        this.numRegistro=num;
        this.registro +=this.numRegistro;
        this.ocupado = false;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public String getVarAssociated() {
        return varAssociated;
    }

    public void setVarAssociated(String varAssociated) {
        this.varAssociated = varAssociated;
    }

    
    
    @Override
    public String toString() {
        return this.registro;
    }
    
    
    
    
    
    
    

}
