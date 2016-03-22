/**
* Universidad Del Valle de Guatemala
* 21-mar-2016
* Pablo Díaz 13203
*/

package compiler;

/**
 *
 * @author Pablo
 */
public class IntermediateCode {
    
    private String dir1;
    private String dir2;
    private String tipo;
    private String tamaño;

    public IntermediateCode(String dir1, String dir2, String tipo) {
        this.dir1 = dir1;
        this.dir2 = dir2;
        this.tipo = tipo;
    }

    public IntermediateCode() {
    }

    
    
    public String getDir1() {
        return dir1;
    }

    public void setDir1(String dir1) {
        this.dir1 = dir1;
    }

    public String getDir2() {
        return dir2;
    }

    public void setDir2(String dir2) {
        this.dir2 = dir2;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    @Override
    public String toString() {
        return "IntermediateCode{" + "dir1=" + dir1 + ", dir2=" + dir2 + ", tipo=" + tipo + '}';
    }
    
    
    

}
