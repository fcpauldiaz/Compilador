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
 * @param <T>
 */
public class StructType<T> extends Type {
    
    private final ArrayList members;
    
    public StructType(String nombreVar, String tipo_literal){
        super(nombreVar,tipo_literal);
        members = new ArrayList();
    }

    public StructType() {
        this.members = new ArrayList();
    }
    
    
    
    public void printMembers(){
        for (int i = 0;i<members.size();i++){
            System.out.println(members);
        }
    }

    @Override
    public String toString() {
        return "StructType{" + "members=" + members + ", literal_tipo= " + this.getLiteralTipo() + ", nombre= " + this.getNombreVariable()+'}';
    }

   
    public void addMember(T miembro) {
        members.add(miembro);
    }

   
    
    
    
    

}
