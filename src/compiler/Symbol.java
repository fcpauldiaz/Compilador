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
public class Symbol {
    
    private int id;
    private int ambito;
    private Type tipo;

    public Symbol(int id, int ambito, Type tipo) {
        this.id = id;
        this.ambito = ambito;
        this.tipo = tipo;
    }

    public Symbol(int ambito, Type tipo) {
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

    public Type getTipo() {
        return tipo;
    }

    public void setTipo(Type tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Symbol{" + "id=" + id + ", ambito=" + ambito + ", tipo=" + tipo + '}';
    }
    
    
    

}
