/***********************************
* Name: StructTable.java
* Date: Sep 1, 2010
* @author martin
* Description:
***********************************/

import java.util.LinkedList;

public class StructTable {

    LinkedList<Structure> table = new LinkedList<Structure>();

    public StructTable(){

    }

    /*****************************************
    * addStructure
    * @param string id
    * @param scope
    * @param member's list
    * @return saying if can add
    *****************************************/
    public boolean addStructure(String id, Scope scope, LinkedList<Symbol> members){
        //TODO revisar si ya existe
        return table.add(new Structure(id, scope, members));
    }
    /*****************************************
    * addStructure
    * @param string id
    * @param scope
    * @param member's list
    * @return saying if can add
    *****************************************/
    public boolean addStructure(Structure struct){
        //TODO revisar si ya existe

        Structure st = this.getStructure(struct.getId(), struct.getScope());
        //significa que no existía..
        if(st == null){
            return table.add(struct);
        }
        //si no es null significa que ya existe esa estructura
        return false;
    }



    /*****************************************
    * getLastStruct
    * @return the last structure in the list
    *****************************************/
    public Structure getLastStruct(){
        return this.table.getLast();
    }
    /*****************************************
    * getAllSimbolInScope
    * @param Scope ambit to search
    * @return a list with Structure in the scope ambit
    *****************************************/
    public Structure getStructure(String id, Scope ambit){
        Scope local_scope = ambit;

        while(local_scope != null){
            LinkedList<Structure> list = getAllStructureInScope(local_scope);

            for(int i=0;i<list.size();i++){
                String name = list.get(i).getId();
                if(name.equals(id)==true){
                    return list.get(i);
                }
            }
            local_scope = local_scope.getPrev_scope();
        }
        return null;
    }


    /*****************************************
    * getAllSimbolInScope
    * @param Scope ambit to search
    * @return a list with structure in the scope ambit
    *****************************************/
    public LinkedList<Structure> getAllStructureInScope(Scope ambit){
        LinkedList<Structure> list = new LinkedList<Structure>();

        for(Structure a : this.table){
            if(a.getScope().getName()==ambit.getName()){
                list.add(a);
            }
        }
        return list;
    }


    /*****************************************
    * getData
    * @return a String[][]
    *****************************************/
    public String[][] getData(){
        //"Struct name", "Scope", "Members"
        String[][] a = new String[this.table.size()][3];
        for(int i=0;i<a.length;i++){
            String[] b = this.table.get(i).getData();
            a[i][0]= b[0];
            a[i][1]= b[1];
            a[i][2]= b[2];
        }
        return a;
    }

    /*****************************************
    * getDefaultData
    * @return a String[][]
    *****************************************/
    public String[][] getDefaultData(){
        //"Struct name", "Scope", "Members"
        String[][] a = new String[1][3];
        a[0][0]= "";
        a[0][1]= "";
        a[0][2]= "";
        return a;
    }

    /*****************************************
    * getColumNames
    * @return a String[]
    *****************************************/
    public static String[] getColumNames(){
        String columNames[] = {"Struct name", "Scope", "Members"};
        return columNames;
    }

    public String toString(){
        String temp  = "";
        for(Structure a: this.table){
            temp +=a.toString()+"\n";
        }
        return temp;
    }

}