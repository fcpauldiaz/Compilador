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
    private boolean global;
    private boolean statementIF;
    private String bandera;
    private IntermediateCode gotoLabel;
    private boolean declaration;
    private String tipo;
    private StackControl localStack;
    private boolean salidaMetodo;
    private boolean param;
    private boolean method;
    private boolean returnStatement;
    
    public IntermediateCode(String dir1, String dir2, String op) {
        this.dir1 = dir1;
        this.dir2 = dir2;
        this.op = op;
        this.global = false;
    }

    public IntermediateCode() {
        this.global = false;
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

    public boolean isGlobal() {
        return global;
    }
    
    

    public void setGlobal(boolean global) {
        this.global = global;
    }

    public boolean isStatementIF() {
        return statementIF;
    }

    public void setStatementIF(boolean statementIF) {
        this.statementIF = statementIF;
    }

    public String getBandera() {
        return bandera;
    }

    public void setBandera(String bandera) {
        this.bandera = bandera;
    }

    public IntermediateCode getGotoLabel() {
        return gotoLabel;
    }

    public void setGotoLabel(IntermediateCode gotoLabel) {
        this.gotoLabel = gotoLabel;
    }

    public boolean isDeclaration() {
        return declaration;
    }

    public void setDeclaration(boolean declaration) {
        this.declaration = declaration;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public StackControl getLocalStack() {
        return localStack;
    }

    public void setLocalStack(StackControl localStack) {
        this.localStack = localStack;
    }

    public boolean isSalidaMetodo() {
        return salidaMetodo;
    }

    public void setSalidaMetodo(boolean salidaMetodo) {
        this.salidaMetodo = salidaMetodo;
    }

    public boolean isParam() {
        return param;
    }

    public void setParam(boolean param) {
        this.param = param;
    }

    public boolean isMethod() {
        return method;
    }

    public void setMethod(boolean method) {
        this.method = method;
    }

    public boolean isReturnStatement() {
        return returnStatement;
    }

    public void setReturnStatement(boolean returnStatement) {
        this.returnStatement = returnStatement;
    }

    @Override
    public String toString() {
        return "IntermediateCode{" + "dir1=" + dir1 + ", dir2=" + dir2 + ", res=" + res + ", op=" + op + ", tamaño=" + tamaño + ", etiqueta=" + etiqueta + '}';
    }

    
    public String completeString() {
        return "IntermediateCode{" + "dir1=" + dir1 + ", dir2=" + dir2 + ", res=" + res + ", op=" + op + ", tama\u00f1o=" + tamaño + ", etiqueta=" + etiqueta + ", global=" + global + ", statementIF=" + statementIF + ", bandera=" + bandera + ", gotoLabel=" + gotoLabel + ", declaration=" + declaration + ", tipo=" + tipo + ", localStack=" + localStack + ", salidaMetodo=" + salidaMetodo + ", param=" + param + ", method=" + method + ", returnStatement=" + returnStatement + '}';
    }
    
    
    
    
    
    
    
    
    
    

}
