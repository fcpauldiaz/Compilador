/***********************************
* Name: MethodTable.java
* Date: Aug 30, 2010
* @author martin
* Description:
***********************************/

import java.util.LinkedList;

public class MethodTable {

    LinkedList<Method> methodTable = new LinkedList<Method>();

    /*****************************************
     * MethodTable
     * Constructor
     *****************************************/
    public MethodTable(){}

    /*****************************************
     * addMethodd
     * @param name
     * @param ret
     * @param parameters
     * @return boolean saying if canAdd
     *****************************************/
    public boolean addMethod(String name, Type ret, LinkedList<Symbol> parameters){
        Method met = new Method(name, ret, parameters);
        //buscar si ya existe esa firma en algún lado
        for(Method a : this.methodTable){
            if(a.getMethodSignature().equals(met.getMethodSignature())){
                //su firma es igual...
                return false;
            }
        }
        this.methodTable.add(met);
        return true;
    }

    /*****************************************
     * addMethodd
     * @param met
     * @return boolean saying if canAdd
     *****************************************/
    public boolean addMethod(Method met){
        //buscar si ya existe esa firma en algún lado
        for(Method a : this.methodTable){
            if(a.getMethodSignature().compareTo(met.getMethodSignature())==0){
                //su firma es igual...
                return false;
            }
        }
        this.methodTable.add(met);
        return true;
    }

    /*****************************************
     * getMethod
     * @param Signature
     * @return method
     *****************************************/
    public Method getMethod(String signature){
        //buscar si ya existe esa firma en algún lado
        for(Method a : this.methodTable){
            if(a.getMethodSignature().compareTo(signature)==0){
                //su firma es igual...
                return a;
            }
        }
        return null;
    }

    /*****************************************
    * getData
    * @return a String[][]
    *****************************************/
    public String[][] getData(){
        //"Return", "Name", "Parameters"
        String[][] a = new String[this.methodTable.size()][4];
        for(int i=0;i<a.length;i++){
            String[] b = this.methodTable.get(i).getData();
            a[i][0]= b[0];
            a[i][1]= b[1];
            a[i][2]= b[2];
            a[i][3]= b[3];
        }
        return a;
    }

    /*****************************************
    * getDefaultData
    * @return a String[][]
    *****************************************/
    public String[][] getDefaultData(){
        //"Return", "Name", "Parameters","Signature"
        String[][] a = new String[1][4];
        a[0][0]= "";
        a[0][1]= "";
        a[0][2]= "";
        a[0][3]= "";
        return a;
    }

    /*****************************************
    * getColumNames
    * @return a String[]
    *****************************************/
    public static String[] getColumNames(){
        String columNames[] = {"Return", "Name", "Parameters", "Signature"};
        return columNames;
    }

    public String toString(){
        String temp  = "";
        for(Method a: this.methodTable){
            temp +=a.toString()+"\n";
        }
        return temp;
    }


    
}
