package compiler;



/**
* Universidad Del Valle de Guatemala
* 04-ago-2015
* Pablo Díaz 13203
*/



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Pablo
 */
public class FileCreator {
    
    public FileCreator(){
        
    }
    
    public void crearASM(String output){
        try {
            
                
                //output += "\r\n"+"\r\n"+"\r\n"+leerArchivo();
                File file;
                File dummy = new File("");
                String path = dummy.getAbsolutePath();
                String nombreArchivo = "CompileMe"+".s";
               
                file = new File(nombreArchivo);
                // if FileCreator doesnt exists, then create it
               
                
               
                FileWriter fw = new FileWriter(path+"/ASM/"+nombreArchivo);
                BufferedWriter bw = new BufferedWriter(fw);
               
                bw.write(output+"\r\n");
               
                bw.close();

                System.out.println("Se ha creado el archivo " + nombreArchivo+" exitosamente");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     

    private String leerArchivo(){
      
        int contador=0;
        int tamaño=0;
        String input=" ";
        BufferedReader br = null;
 
        try {

                String sCurrentLine;
                File file = new File("CompileMe.s");
                br = new BufferedReader(new FileReader(file.getAbsoluteFile()));

               
               
               while ((sCurrentLine = br.readLine()) != null) {
                    
                    input+=sCurrentLine+"\r\n";
                
                }
             
                
        input+="\r\n";
                
        return input;
        } catch (IOException e) {
               
        } finally {
                try {
                        if (br != null)br.close();
                } catch (IOException ex) {
                        ex.printStackTrace();
                }
        }
        return null;
        
    }

}
