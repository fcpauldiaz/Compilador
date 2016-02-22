/**
* Universidad Del Valle de Guatemala
* 17-feb-2016
* Pablo Díaz 13203
*/

package compiler;

/**
 *
 * @author Pablo
 */
public abstract class AbstractType<T> {
     private String  literal_tipo;
    private String  nombreVariable;

    public AbstractType() {
    }

    public AbstractType(String literal_tipo, String nombreVariable) {
        this.literal_tipo = literal_tipo;
        this.nombreVariable = nombreVariable;
    }

    public String getLiteralTipo() {
        return literal_tipo;
    }

    public void setLiteralTipo(String literal_tipo) {
        this.literal_tipo = literal_tipo;
    }

    public String getNombreVariable() {
        return nombreVariable;
    }

    public void setNombreVariable(String nombreVariable) {
        this.nombreVariable = nombreVariable;
    }

   
    public abstract void addMember(T miembro);
    

}
