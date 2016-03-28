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
    private String res;
    private String op;
    private String tamaño;
    private String etiqueta;

    public IntermediateCode(String dir1, String dir2, String op) {
        this.dir1 = dir1;
        this.dir2 = dir2;
        this.op = op;
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

  
    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    @Override
    public String toString() {
        return "IntermediateCode{" + "dir1=" + dir1 + ", dir2=" + dir2 + ", res=" + res + ", op=" + op + ", tamaño=" + tamaño + ", etiqueta=" + etiqueta + '}';
    }

    
    
    
    
    
    

}
