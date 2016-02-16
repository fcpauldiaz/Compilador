package GUI;

/***********************************
* Name: Structure.java
* Date: Sep 1, 2010
* @author martin
* Description:
***********************************/
import java.util.LinkedList;

public class Structure {
    private LinkedList<Symbol> members = new LinkedList<Symbol>();
    private Scope scope;
    private String id;

    /*****************************************
     * Constructor
     *****************************************/
    public Structure(){
        this.id = "";
        scope = null;
    }
    
    /*****************************************
     * Constructor
     * @param id
     * @param scope
     * @param members
     *****************************************/
    public Structure(String id, Scope scope, LinkedList<Symbol> members){
        this.id = id;
        this.scope = scope;
        this.members = members;
    }

    /*****************************************
     * @return the members
     *****************************************/
    public LinkedList<Symbol> getMembers() {
        return members;
    }

    /*****************************************
     * @return the scope
     *****************************************/
    public Scope getScope() {
        return scope;
    }

    /*****************************************
     * @return the id
     *****************************************/
    public String getId() {
        return id;
    }

    /*****************************************
     * @return the type for the id
     *****************************************/
    public Type getType() {
        Type type = new Type(this.id);
        type.setMembers(members);
        type.setStructure(true);
        return type;
    }

    /*****************************************
     * @return the String[] with all the data
     *****************************************/
    public String[] getData() {
        //"Struct name", "Scope", "Members"
        String[] a = new String[3];
        a[0] = this.id;
        a[1] = this.scope.getName()+"";
        a[2] = this.members.toString();
        return a;
    }

    public String toString(){
        String temp = "";
        temp += "\t"+id+" - scope: "+scope.getName()+"\n";
        for(Symbol a : this.members){
            temp+="\t * "+a.getType().getType_name()+" "+a.getId()+"\n";
        }
        return temp;
    }



}
