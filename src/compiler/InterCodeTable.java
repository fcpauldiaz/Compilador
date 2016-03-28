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
        String returnString = "";
        for (int i = 0;i<this.arrayCode.size();i++){
            String etiqueta = arrayCode.get(i).getEtiqueta();
            String dir1 = arrayCode.get(i).getDir1();
            String dir2 = arrayCode.get(i).getDir2();
            String op = arrayCode.get(i).getOp();
            String res = arrayCode.get(i).getRes();
            if (etiqueta != null){
                returnString += etiqueta + "\n";
            }
            if (dir1 != null && dir2 != null && op != null && res != null){
                returnString += res +" = "+ dir1 +" "+ op +" "+ dir2+"\n";
            }
            if (dir1 != null && dir2 == null && op != null && res != null){
                returnString += res +" "+ op +" "+ dir1+ "\n";
            }
            
        }
        
       return returnString;
    }
    public void printTable(){
        for (int i = 0;i<this.arrayCode.size();i++){
            System.out.println(arrayCode.get(i).toString());
        }
    }
    
   
    
    

}
