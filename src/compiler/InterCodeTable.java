/**
* Universidad Del Valle de Guatemala
* 21-mar-2016
* Pablo Díaz 13203
*/

package compiler;

import static compiler.Visitor.tablaSimbolos;
import java.util.ArrayList;
import java.util.Map;

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
            boolean global = arrayCode.get(i).isGlobal();
            boolean stIF = arrayCode.get(i).isStatementIF();
            String bandera = arrayCode.get(i).getBandera();
            IntermediateCode gotoL = arrayCode.get(i).getGotoLabel();
            boolean declaration = arrayCode.get(i).isDeclaration();
            if (etiqueta != null && global == false && declaration == false){
                returnString += etiqueta + "\n";
            }
            if (dir1 != null && dir2 != null && op != null && res != null && !stIF){
                returnString += res +" = "+ dir1 +" "+ op +" "+ dir2+"\n";
            }
            if (dir1 != null && dir2 == null && op != null && res != null){
                returnString += res +" "+ op +" "+ dir1+ "\n";
            }
            if (global == true){
                returnString += ".global " + etiqueta +"\n";
               // returnString += etiqueta +": word 0" + "\n";
            }
            if (stIF){
                returnString += res +" "+ dir1+" " + op +" "+ dir2 + "\n";
            }
            if (dir1 == null && op != null && dir2 != null){
                returnString += op +" "+ dir2 + "\n";
            }
            //method call
            if (dir1 != null && dir2 != null && res != null && op == null){
                returnString += res +" = "+ dir1 + " " + dir2 +"\n ";
            }
            //param
            if (dir1 != null && dir2 != null && op == null && res == null){
                returnString += dir1 + " " + dir2 + "\n";           
            }
            if (this.arrayCode.get(i).isReturnStatement()){
                returnString += "return " + res +"\n";
            }
        }
        
       return returnString;
    }
    public void printTable(){
        reorderCode();
        for (IntermediateCode arrayCode1 : this.arrayCode) {
            System.out.println(arrayCode1.completeString());
        }
    }
    
    //método para re-ordenar la reserva del espacio
    //este método es importante porque se tiene que reservar el espacio
    //independientemente del bloque donde se encuentre la declaración.
    public void reorderCode(){
        int j = 0;
        ArrayList<IntermediateCode> copyArray = this.arrayCode;
        for (int i = 0; i < this.arrayCode.size(); i++){
            boolean method = this.arrayCode.get(i).isMethod();
            StackControl sp = this.arrayCode.get(i).getLocalStack();
            boolean param = this.arrayCode.get(i).isParam();
            String op = this.arrayCode.get(i).getOp();
            String dir1 = this.arrayCode.get(i).getDir1();
            String dir2 = this.arrayCode.get(i).getDir2();
            String res = this.arrayCode.get(i).getRes();
            if (method){
               j = i;
            }
            
            //revisar posición si es declaracion o call param
            if (sp != null){
                if (i != (j+1)){
                    IntermediateCode c = copyArray.get(i);
                    copyArray.remove(i);
                    copyArray.add(j+1, c);
                    j = j +1 ;
                }else{
                    j = i ;
                }
            }
          
            
            
        }
        this.arrayCode = copyArray;
    }
    
    public IntermediateCode searchCodeGlobal(String nombreVar){
        for (int i = 0;i<this.arrayCode.size();i++){
            IntermediateCode inter = this.arrayCode.get(i);
            String etiqueta = inter.getEtiqueta().substring(0,inter.getEtiqueta().indexOf("_"));
           if (inter.isGlobal()&&etiqueta.equals(nombreVar)){
               return inter;
           }
        }
        return null;
    }
    
    public Symbol searchSymbolLastScope(String nombreVar){
        ArrayList<Symbol> simbolos = new ArrayList();
        for (Map.Entry<Integer, Symbol> entry : tablaSimbolos.getTabla().entrySet()) {
            Symbol simbol = entry.getValue();
            if (((Type)simbol.getTipo()).getNombreVariable().equals(nombreVar)){
                simbolos.add(simbol);
            }
        }
        
        return simbolos.get(simbolos.size()-1);
    }
    
    public boolean searchGlobalSymbol(String nombreVar, Scope ambitoActual){
        Symbol simbol = tablaSimbolos.showSymbol(nombreVar, ambitoActual);
        if (simbol==null){
            return false;
        }
        return simbol.getAmbito()==0;
    }
    
     public boolean searchGlobalSymbol(String nombreVar, int ambitoActual){
        Symbol simbol = tablaSimbolos.showSymbol(nombreVar, ambitoActual);
        if (simbol==null){
            return false;
        }
        return simbol.getAmbito()==0;
    }
    
    
 
    

}
