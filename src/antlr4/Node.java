/**
* Universidad Del Valle de Guatemala
* 12-feb-2016
* Pablo Díaz 13203
*/

package antlr4;

/**
 *
 * @author Pablo
 */
public class Node<T> {

    private T tipo;
    private Node izquierdo;
    private Node derecho;

    public Node(T tipo) {
        this.tipo = tipo;
    }

    public Node(T tipo, Node izquierdo, Node derecho) {
        this.tipo = tipo;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    public T getTipo() {
        return tipo;
    }

    public void setTipo(T tipo) {
        this.tipo = tipo;
    }

    public Node getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(Node izquierdo) {
        this.izquierdo = izquierdo;
    }

    public Node getDerecho() {
        return derecho;
    }

    public void setDerecho(Node derecho) {
        this.derecho = derecho;
    }

    @Override
    public String toString() {
        return "Node{" + "tipo=" + tipo + ", izquierdo=" + izquierdo + ", derecho=" + derecho + '}';
    }
    
    
    
}
