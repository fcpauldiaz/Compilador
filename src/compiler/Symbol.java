/**
* Universidad Del Valle de Guatemala
* 16-feb-2016
* Pablo Díaz 13203
*/

package compiler;

/**
 *
 * @author Pablo
 */
public class Symbol<T> {
    
    private int id;
    private int ambito;
    private T tipo;

    public Symbol(int id, int ambito, T tipo) {
        this.id = id;
        this.ambito = ambito;
        this.tipo = tipo;
    }

    public Symbol(int ambito, T tipo) {
        this.ambito = ambito;
        this.tipo = tipo;
    }

    public Symbol() {
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmbito() {
        return ambito;
    }

    public void setAmbito(int ambito) {
        this.ambito = ambito;
    }

    public T getTipo() {
        return tipo;
    }

    public void setTipo(T tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Symbol{" + "id=" + id + ", ambito=" + ambito + ", tipo=" + tipo + '}';
    }
    
    
    

}
