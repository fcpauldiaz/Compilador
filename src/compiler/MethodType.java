/**
* Universidad Del Valle de Guatemala
* 17-feb-2016
* Pablo Díaz 13203
*/

package compiler;

import java.util.ArrayList;

/**
 *
 * @author Pablo
 * @param <T> Generic
 */
public class MethodType<T> extends Type {
    
    private ArrayList parameters;
    

    public MethodType(String nombreVar, String literal_tipo){
        super(nombreVar, literal_tipo);
        
    }
    
    public void addParameter(T param){
        parameters.add(param);
    }
    
    public ArrayList getParameters(){
        return this.parameters;
    }
    
    public boolean compareSignature(MethodType metodo){
        if (this.getLiteralTipo().equals(metodo.getLiteralTipo())
            &&
            this.getParameters() == metodo.getParameters()
            )
            return true;
        return false;
    }
    
    
    
    
}
