/***********************************
* Name: Type.java
* Date: Aug 22, 2010
* @author martin
* Description:
***********************************/

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Type {

    //public static LinkedList<String> type_names = new LinkedList<String>();
    private boolean simple;    //true if is simple
    private boolean structure; //true if is structure
    private boolean param;     //true if is param
    private boolean array;     //true if is an array
    private int array_len;

    private LinkedList<Symbol> members = new LinkedList<Symbol>();
    
    private String type_name;
    
    //private ListaDeParametros params;
    //private ListaDeMiembros miembros;

    /*****************************************
    * Type
    * Constructor
    *****************************************/
    public Type(String type_name){
        this.type_name = type_name;
        this.simple = false;
        this.structure = false;
        this.array = false;
        this.param = false;
    }

    /*****************************************
     * @return the simple
     *****************************************/
    public boolean isSimple() {
        return simple;
    }

    /*****************************************
     * @param simple the simple to set
     *****************************************/
    public void setSimple(boolean simple) {
        this.simple = simple;
    }

    /*****************************************
     * @return the structure
     *****************************************/
    public boolean isStructure() {
        return structure;
    }

    /*****************************************
     * @param structure the structure to set
     *****************************************/
    public void setStructure(boolean structure) {
        this.structure = structure;
    }

    /*****************************************
     * @return the array
     *****************************************/
    public boolean isArray() {
        return array;
    }

    /*****************************************
     * Usado para definir arreglos
     * @param array the array to set
     *****************************************/
    public void setArray(boolean array, int array_len) {
        this.array = array;
        this.array_len = array_len;
    }
    /*****************************************
     * Usado para definir arreglo como parámetro
     * @param array the array to set
     *****************************************/
    public void setArray(boolean array) {
        this.array = array;
    }

    /*****************************************
     * @return the members
     *****************************************/
    public LinkedList<Symbol> getMembers() {
        return members;
    }

    /*****************************************
     * @param members the members to set
     *****************************************/
    public void setMembers(LinkedList<Symbol> members) {
        this.members = members;
    }

    /*****************************************
     * @return the type_name
     *****************************************/
    public String getType_name() {
        String type = type_name;
        if(this.isArray()){
            type = type+"[]";
        }
        if(this.isStructure()){
            type = "struct:" + type;
        }
        return type;
    }

    /*****************************************
     * @return the type_name
     *****************************************/
    public String getType_name_Without_struct() {
        String type = type_name;
        if(this.isArray()){
            type = type+"[]";
        }
        if(this.isStructure()){
            type = ""+type;
        }
        return type;
    }



    /*****************************************
     * @param type_name the type_name to set
     *****************************************/
    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    /*****************************************
     * @return the param
     *****************************************/
    public boolean isParam() {
        return param;
    }

    /*****************************************
     * @param param the param to set
     *****************************************/
    public void setParam(boolean param) {
        this.param = param;
    }
    
}
