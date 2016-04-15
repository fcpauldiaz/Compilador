/**
* Universidad Del Valle de Guatemala
* 01-abr-2016
* Pablo Díaz 13203
*/

package compiler;

import java.util.ArrayList;

/**
 *
 * @author Pablo
 */
public class CodeGenerator {
     private ArrayList<IntermediateCode> arrayCode;
     private Registro r0 = new Registro(0);
     private Registro r1 = new Registro(1);
     private Registro r2 = new Registro(2);
     
     public CodeGenerator(ArrayList array){
         this.arrayCode = array;
     }
     
     public String ponerGlobalVar(){
         String returnString = "";
         for (IntermediateCode interCode : this.arrayCode) {
          if (interCode.isDeclaration()){
                if (interCode.isGlobal())
                    returnString += ".global " + interCode.getEtiqueta() +"\n";
                if (interCode.getTipo().equals("int")){
                    returnString +=  interCode.getEtiqueta()+": " +".word 0" +"\n";
                }
                if (interCode.getTipo().equals("char")){

                    returnString += interCode.getEtiqueta()+": " + ".byte \' \'"+"\n";
                }
          }
        }
         return returnString;
     }
     
     public String genCode(){
         String returnString = "";
         for (IntermediateCode interCode: this.arrayCode){
               String etiqueta = interCode.getEtiqueta();
                String dir1 = interCode.getDir1();
                String dir2 = interCode.getDir2();
                String op = interCode.getOp();
                String res = interCode.getRes();
                boolean global = interCode.isGlobal();
                boolean stIF = interCode.isStatementIF();
                boolean declaration = interCode.isDeclaration();
                if (etiqueta != null && global == false && declaration == false){
                    returnString += etiqueta + "\n";
                }
                if (dir1 != null && dir2 != null && op != null && res != null && !stIF){
                    //aquí tengo que empezar a usar registros para asignaciones
                    //FORMATO: instrucción registroDestino, registroArg1, registroArg2
                    //tengo que hacer una función para guardar y otra para cargar variables
                    //declaradas
                   // returnString += res +" = "+ dir1 +" "+ op +" "+ dir2+"\n";
                    if (isNum(dir1)&&isNum(dir2)){
                        returnString += genDouble(interCode);
                    }
                    if (isNum(dir1)&&!isNum(dir2)){
                        returnString += genSimpleLeft(interCode);
                    }
                    if (!isNum(dir1)&&isNum(dir2)){
                        returnString += genSimpleRigth(interCode);
                    }
                }
                //instrucción de copia
                if (dir1 != null && dir2 == null && op != null && res != null){
                    //returnString += res +" "+ op +" "+ dir1+ "\n";
                    if (!isNum(dir1))
                        returnString += genCopy(interCode);
                    else
                        returnString += assignNum(interCode);
                }
         }
         return returnString;
     }
     
     public String assignNum(IntermediateCode interCode){
        String returnString="";
        returnString += "MOV"+" " + r0+", " + "#"+interCode.getDir1() + "\n";
        returnString += "LDR" +" " +r1 +", "+ "="+interCode.getRes()+"\n";
        //returnString += "LDR" +" " +r1 + "["+r1+"]"; 
        returnString +=  "STR" +" " + r0 + ", "+"["+r1+"]" +"\n";
        
         return returnString;
     }
     
     public String genCopy(IntermediateCode interCode){
        String returnString = "";
        Registro rDynamic = this.searchVar(interCode.getDir1());
        Registro disponible = this.returnFreeRegister(rDynamic);
        returnString += "LDR" +" " +disponible +", "+ "="+interCode.getRes()+"\n";
        //returnString += "LDR" +" " +r1 + "["+r1+"]"; 
        returnString +=  "STR" +" " + rDynamic + ", "+"["+disponible+"]" +"\n";
        
        
        return returnString;
     }
     
     public String genDouble(IntermediateCode interCode){
        String returnString = "";
        returnString += "MOV"+" " + r0+", " + "#"+interCode.getDir1() + "\n";
        if (interCode.getOp().equals("+")){
            returnString += "ADD" + " "+ r0 + "," + r0 + ", " + "#"+interCode.getDir2()+ "\n";
            r0.setVarAssociated(interCode.getRes());
        }
         if (interCode.getOp().equals("-")){
            returnString += "SUB" + " "+ r0 + "," + r0 + ", " + "#"+interCode.getDir2()+ "\n";
            r0.setVarAssociated(interCode.getRes());
        }
        if (interCode.getOp().equals("*")){
            returnString += "MOV"+" " + r1+", " + "#"+interCode.getDir2() + "\n";
            returnString += "MUL" + " "+ r0 + ", " + r0 + ", " + r1+ "\n";
            r0.setVarAssociated(interCode.getRes());
        }
         if (interCode.getOp().equals("/")){
            returnString += "MOV"+" " + r1+", " + "#"+interCode.getDir2() + "\n";
            returnString += "SDIV" + " "+ r0 + ", " + r0 + ", " + r1+ "\n";
            r0.setVarAssociated(interCode.getRes());
        }
       
         
        return returnString;
     }
       public String genSimpleLeft(IntermediateCode interCode){
        String returnString = "";
        Registro rDynamic = this.searchVar(interCode.getDir2());
        if (interCode.getOp().equals("+")){
            returnString += "ADD" + " "+ rDynamic + ", " + rDynamic + ", " + "#"+interCode.getDir1()+ "\n";
            rDynamic.setVarAssociated(interCode.getRes());
        }
        if (interCode.getOp().equals("-")){
             returnString += "SUB" + " "+ rDynamic + ", " + rDynamic + ", " + "#"+interCode.getDir1()+ "\n";
            rDynamic.setVarAssociated(interCode.getRes());
        }
         if (interCode.getOp().equals("*")){
             Registro free = returnFreeRegister(rDynamic);
             returnString += "MOV" + " " + free+ ", " + interCode.getDir1();
             returnString += "MUL" + " "+ rDynamic + ", " + rDynamic + ", " + free+  "\n";
            rDynamic.setVarAssociated(interCode.getRes());
        }
          if (interCode.getOp().equals("/")){
             Registro free = returnFreeRegister(rDynamic);
             returnString += "MOV" + " " + free+ ", " + interCode.getDir1();
             returnString += "SDIV" + " "+ rDynamic + ", " + rDynamic + ", " + free+  "\n";
            rDynamic.setVarAssociated(interCode.getRes());
        }
       
         
        return returnString;
     }
        public String genSimpleRigth(IntermediateCode interCode){
        String returnString = "";
        Registro rDynamic = this.searchVar(interCode.getDir1());
        if (interCode.getOp().equals("+")){
            returnString += "ADD" + " "+ rDynamic + ", " + rDynamic + ", " + "#"+interCode.getDir2()+ "\n";
            rDynamic.setVarAssociated(interCode.getRes());
        }
        if (interCode.getOp().equals("-")){
             returnString += "SUB" + " "+ rDynamic + ", " + rDynamic + ", " + "#"+interCode.getDir2()+ "\n";
            rDynamic.setVarAssociated(interCode.getRes());
        }
         if (interCode.getOp().equals("*")){
             Registro free = returnFreeRegister(rDynamic);
             returnString += "MOV" + " " + free+ ", " + interCode.getDir2();
             returnString += "MUL" + " "+ rDynamic + ", " + rDynamic + ", " + free+  "\n";
            rDynamic.setVarAssociated(interCode.getRes());
        }
          if (interCode.getOp().equals("*")){
             Registro free = returnFreeRegister(rDynamic);
             returnString += "MOV" + " " + free+ ", " + interCode.getDir2();
             returnString += "SDIV" + " "+ rDynamic + ", " + rDynamic + ", " + free+  "\n";
            rDynamic.setVarAssociated(interCode.getRes());
        }
       
         
        return returnString;
     }
     
     public Registro returnFreeRegister(Registro actual){
         if (actual == r0){
             return r1;
         }
         if (actual == r1){
             return r2;
         }
         if (actual == r2){
             return r0;
         }
         return null;
     }
     
     public Registro searchVar (String var){
         if (r0.getVarAssociated().equals(var)){
             return r0;
         }
         if (r1.getVarAssociated().equals(var)){
             return r1;
         }
         if (r2.getVarAssociated().equals(var)){
             return r2;
         }
         return null;
         
     }
     
     public boolean isNum(String param){
        try{
            int test = Integer.parseInt(param);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
