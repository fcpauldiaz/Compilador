/***********************************
* Name: Scope.java
* Date: Aug 20, 2010
* @author martin
* Description:
***********************************/

import java.util.LinkedList;

public class Scope {
    
    private int name;
    private Scope prev_scope;
    private LinkedList<Scope> next_scope = new LinkedList<Scope>();
    private int contador_obtener_hijo = 0;

    public static int cont = 0;

    /*****************************************
    * Scope
    * Constructor
    *****************************************/
    public Scope(){
        name = (cont++);
        this.prev_scope = null;
    }

    /*****************************************
    * ToolbarFrame
    * Constructor
    * @param Scope prev_scope
    *****************************************/
    public Scope(Scope prev_scope){
        name = (cont++);
        this.prev_scope = prev_scope;
        //terminar de formar el arbol
        prev_scope.addKid(this);
    }

    /*****************************************
     * @return the name
     *****************************************/
    public int getName() {
        return name;
    }

    /*****************************************
     * @param name the name to set
     *****************************************/
    public void setName(int name) {
        this.name = name;
    }

    /*****************************************
     * @return the prev_scope
     *****************************************/
    public Scope getPrev_scope() {
        return prev_scope;
    }

    /*****************************************
     * @param prev_scope the prev_scope to set
     *****************************************/
    public void setPrev_scope(Scope prev_scope) {
        this.prev_scope = prev_scope;
    }

    /*****************************************
     * @param prev_scope the prev_scope to set
     *****************************************/
    public void addKid(Scope hijo) {
        this.next_scope.add(hijo);
    }

    /*****************************************
     * @param prev_scope the prev_scope to set
     *****************************************/
    public Scope getKid() {
        if(contador_obtener_hijo<this.next_scope.size()){
            return this.next_scope.get(contador_obtener_hijo++);
        }
        return null;
        //contador_obtener_hijo = 0;
        //return this.next_scope.get(contador_obtener_hijo++);
    }

    /*****************************************
     * @return the next_scope
     *****************************************/
    public LinkedList<Scope> getNext_scope() {
        return next_scope;
    }

    /*****************************************
     * @param next_scope the next_scope to set
     *****************************************/
    public void setNext_scope(LinkedList<Scope> next_scope) {
        this.next_scope = next_scope;
    }


}
