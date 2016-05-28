/**
* Universidad Del Valle de Guatemala
* 01-abr-2016
* Pablo Díaz 13203
*/

package compiler;

/**
 *
 * @author Pablo
 */
public class CodeGenerator {
    FileCreator file = new FileCreator();
    String strFile ="";
    public CodeGenerator(){
        String header;
        header = "/*" +"\n" +
                 "* UVG " + "\n"+
                "* Pablo Diaz " + "\n"+
                "* Compilador " + "\n"+
                "* Mayo 2016 "+ "\n"+
                "*/"+"\n"+"\n"+
                ".data"+"\n"+
                ".align 2"+"\n"+"\n"+
                "salida_num:    .asciz \"%d \\n\""+"\n"+
                "salida_str:    .asciz \"%f \\n\""+"\n"+
                "global: .space"+"\n"+"\n"+
                ".text"+"\n"+
                ".align 2"+"\n"+"\n"+
                ".global main"+"\n"+
                ".type main, %function"+"\n";
             
                
        
                
         strFile += header;
       
    }
    
    public void insertCode(String str, int cantTabs, int cantEnters, String comment){
        str = str + "\t" + "//" + comment + "\n";
        for (int i = 0; i<cantTabs;i++){
            str = "\t" + str;
        }
        if (!comment.isEmpty()){
            cantEnters--;
        }
         for (int i = 0; i<cantEnters;i++){
            str = str + "\n";
        }
        
        strFile += str;
    }
    
     public void insertCode(String str, int cantTabs, int cantEnters){
        for (int i = 0; i<cantTabs;i++){
            str = "\t" + str;
        }
         for (int i = 0; i<cantEnters;i++){
            str = str + "\n";
        }
       
        strFile += str;
    }
    
    public void crearArchivo(){
         file.crearASM(strFile);
    }
}
