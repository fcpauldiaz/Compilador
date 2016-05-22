/**
* Universidad Del Valle de Guatemala
* 22-may-2016
* Pablo Díaz 13203
*/

package compiler;

import java.util.ArrayList;

/**
 *
 * Un descriptor de direcciones lleva control de los lugares de
 * location donde se puede acceder una variable
 * El location puede ser dirección de memoria, stack pointer.
 * La información se puede guardar en la tabla de símbolos.
 * @author Pablo
 */
public class AddressDescriptor {

   private ArrayList<Address> direcciones; 

    public AddressDescriptor() {
        this.direcciones = new ArrayList();
    }
    
    
    public String revisarDireccion(String nombreVar){
       
        for(Address adr: direcciones){
            if (adr.getValor().equals(nombreVar)){
                return adr.getDireccion();
            }
            
        }
        
        return "";
    }
    
    public void addDireccion(String nombreVar, String direccion) {
        this.direcciones.add(new Address(nombreVar, direccion));
    }
    
  
    
    
    
}
