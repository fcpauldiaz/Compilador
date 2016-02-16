package GUI;

/***********************************
* Name: Simbol.java
* Date: Aug 20, 2010
* @author martin
* Description:
***********************************/

public class Symbol {

    private String id;
    private Scope  scope;
    private Type   type;

    //private int longitud;

    /*****************************************
    * Simbol
    * Constructor
    *****************************************/
    public Symbol(){
        this.id = "";
    }

    /*****************************************
    * Simbol
    * Constructor
    * @param id
    * @param scope
    * @param type
    *****************************************/
    public Symbol(String id, Type type, Scope scope){
        this.id = id;
        this.scope = scope;
        this.type = type;
    }

    /*****************************************
     * @return the id
     *****************************************/
    public String getId() {
        return id;
    }

    /*****************************************
     * @param id the id to set
     *****************************************/
    public void setId(String id) {
        this.id = id;
    }

    /*****************************************
     * @return the scope
     *****************************************/
    public Scope getScope() {
        return scope;
    }

    /*****************************************
     * @param scope the scope to set
     *****************************************/
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    /*****************************************
     * @return the type
     *****************************************/
    public Type getType() {
        return type;
    }

    /*****************************************
     * @param type the type to set
     *****************************************/
    public void setType(Type type) {
        this.type = type;
    }

    /*****************************************
     * @return the String[] with all the data
     *****************************************/
    public String[] getData() {
        //"id", "Scope", "Type","is Simple","is Structure","is Param","is Array","Members"
        String[] a = new String[8];
        a[0] = this.id;
        a[1] = this.scope.getName()+"";
        a[2] = this.getType().getType_name();
        a[3] = this.getType().isSimple()+"";
        a[4] = this.getType().isStructure()+"";
        a[5] = this.getType().isParam()+"";
        a[6] = this.getType().isArray()+"";
        a[7] = this.getType().getMembers().toString();
        //a[7] = this.getType().getParams().toString();
        return a;
    }


    /*****************************************
     * toString
     * @return string representing a symbol
     *****************************************/
    public String toString(){
        String temp = "";
        //temp+="\tid: "+id+" - type: "+type.getType_name()+" - scope: "+this.scope.getName();
        temp+=id+": "+type.getType_name();
        return temp;
    }
    
}
