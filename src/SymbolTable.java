/***********************************
* Name: SymbolTable.java
* Date: Aug 20, 2010
* @author martin
* Description:
***********************************/

import java.util.LinkedList;

public class SymbolTable {

    LinkedList<Symbol> table;

    /*****************************************
    * SymbolTable
    * Constructor
    *****************************************/
    public SymbolTable(){
        table = new LinkedList<Symbol>();
    }

    /*****************************************
    * addSymbol
    * @param symbol to add
    * @return saying if can add
    *****************************************/
    public boolean addSymbol(Symbol symbol){
        return table.add(symbol);
    }

    /*****************************************
    * addAllSymbol
    * @param symbol to add
    * @return saying if can add
    *****************************************/
    public boolean addAllSymbol(LinkedList<Symbol> symbol){
        return table.addAll(symbol);
    }

    /*****************************************
    * existSymbol
    * @param Scope ambit to search
    * @return saying if exist
    *****************************************/
    public boolean existSymbolInScope(String id, Scope ambit){

        LinkedList<Symbol> list = this.getAllSymbolInScope(ambit);
        for(int i=0;i<list.size();i++){
            String name = list.get(i).getId();
            if(name.equals(id)==true){
                return true;
            }
        }
        return false;
    }

    /*****************************************
    * existTypeInScope
    * @param Scope ambit to search
    * @return saying if exist
    *****************************************/
    public boolean existTypeInScope(String type_name, Scope ambit){

        LinkedList<Symbol> list = this.getAllSymbolInScope(ambit);
        for(int i=0;i<list.size();i++){
            String name = list.get(i).getType().getType_name();
            if(name.equals(type_name)==true){
                return true;
            }
        }
        return false;
    }

    /*****************************************
    * getAllSymbolInScope
    * @param Scope ambit to search
    * @return a list with symbols in the scope ambit
    *****************************************/
    public LinkedList<Symbol> getAllSymbolInScope(Scope ambit){
        LinkedList<Symbol> list = new LinkedList<Symbol>();

        for(Symbol a : this.table){
            if(a.getScope().getName()==ambit.getName()){
                list.add(a);
            }
        }
        return list;
    }

    /*****************************************
    * getAllSymbolInScope
    * @param Scope ambit to search
    * @return a list with symbols in the scope ambit
    *****************************************/
    public Symbol getSymbol(String id, Scope ambit){
        Scope local_scope = ambit;

        while(local_scope != null){
            LinkedList<Symbol> list = getAllSymbolInScope(local_scope);

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
    * getAllSymbolInScope
    * @param Scope ambit to search
    * @return a list with symbols in the scope ambit
    *****************************************/
    public boolean existTypeStruct(String id, Scope ambit){
        Scope local_scope = ambit;

        while(local_scope != null){
            LinkedList<Symbol> list = getAllSymbolInScope(local_scope);

            for(int i=0;i<list.size();i++){
                String type_name = list.get(i).getType().getType_name();
                boolean isStruct = list.get(i).getType().isStructure();
                if(type_name.equals("struct:"+id)==true && isStruct){
                    return true;
                }
            }
            local_scope = local_scope.getPrev_scope();
        }
        return false;
    }

    /*****************************************
    * getAllSymbolInScope
    * @param Scope ambit to search
    * @return a list with symbols in the scope ambit
    *****************************************/
    public Type getTypeStruct(String id, Scope ambit){
        Scope local_scope = ambit;

        while(local_scope != null){
            LinkedList<Symbol> list = getAllSymbolInScope(local_scope);

            for(int i=0;i<list.size();i++){
                String type_name = list.get(i).getType().getType_name();
                boolean isStruct = list.get(i).getType().isStructure();
                if(type_name.equals("struct:"+id)==true && isStruct){
                    return list.get(i).getType();
                }
            }
            local_scope = local_scope.getPrev_scope();
        }
        return null;
    }

    /*****************************************
    * getData
    * @return a String[][]
    *****************************************/
    public String[][] getData(){
        //"id", "Scope", "Type","is Simple","is Structure","is Param","is Array","Members"
        String[][] a = new String[this.table.size()][8];
        for(int i=0;i<a.length;i++){
            String[] b = this.table.get(i).getData();
            a[i][0]= b[0];
            a[i][1]= b[1];
            a[i][2]= b[2];
            a[i][3]= b[3];
            a[i][4]= b[4];
            a[i][5]= b[5];
            a[i][6]= b[6];
            a[i][7]= b[7];
        }
        return a;
    }

    /*****************************************
    * getDefaultData
    * @return a String[][]
    *****************************************/
    public String[][] getDefaultData(){
        //"Return", "Name", "Parameters","Signature"
        String[][] a = new String[1][8];
        a[0][0]= "";
        a[0][1]= "";
        a[0][2]= "";
        a[0][3]= "";
        a[0][4]= "";
        a[0][5]= "";
        a[0][6]= "";
        a[0][7]= "";
        return a;
    }

    /*****************************************
    * getColumNames
    * @return a String[]
    *****************************************/
    public static String[] getColumNames(){
        String columNames[] = {"id", "Scope", "Type","is Simple","is Structure","is Param","is Array","Members"};
        return columNames;
    }



    
}
