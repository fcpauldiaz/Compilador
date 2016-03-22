/**
* Universidad Del Valle de Guatemala
* 21-mar-2016
* Pablo Díaz 13203
*/

package compiler;

import java.util.ArrayList;

/**
 *
 * @author Pablo
 */
public class InterCodeTable {
    
    private ArrayList<IntermediateCode> arrayCode;

    public InterCodeTable() {
        this.arrayCode = new ArrayList();
    }
    
    public void addCode(IntermediateCode code){
        this.arrayCode.add(code);
    }

    public ArrayList<IntermediateCode> getArrayCode() {
        return arrayCode;
    }

    public void setArrayCode(ArrayList<IntermediateCode> arrayCode) {
        this.arrayCode = arrayCode;
    }

    @Override
    public String toString() {
        return "InterCodeTable{" + "arrayCode=" + arrayCode + '}';
    }
    
    
    

}
