/***********************************
* Name: Method.java
* Date: Aug 29, 2010
* @author martin
* Description:
***********************************/

import java.util.LinkedList;

public class Method {

    LinkedList<Symbol> parameters = new LinkedList<Symbol>();
    Type ret;
    String name;
    //Scope ambit;

    /*****************************************
    * Method
    * Constructor
    *****************************************/
    public Method(String name, Type ret, LinkedList<Symbol> parameters){
        this.parameters = parameters;
        this.ret = ret;
        this.name = name;
    }

    /*****************************************
    * getMethodSignature
    * @return String with the signature of the method
    * ex: nombre(int,boolean,char,char,int)
    *****************************************/
    public String getMethodSignature(){
        String retorno = name+"(";
        if(parameters.size()>0){
            retorno+=parameters.get(0).getType().getType_name()+",";
            for(int i = 1;i<this.parameters.size(); i++){
                Symbol a  = parameters.get(i);
                retorno+=""+a.getType().getType_name()+",";
            }
            retorno = retorno.substring(0, retorno.length()-1);
        }
        retorno+=")";
        return retorno;
    }


    /*****************************************
     * @return the String[] with all the data
     *****************************************/
    public String[] getData() {
        //"Return", "Name", "Parameters"
        String[] a = new String[4];
        a[0] = this.ret.getType_name();
        a[1] = this.name+"";
        a[2] = this.parameters.toString();
        a[3] = this.getMethodSignature();
        return a;
    }

    /*****************************************
     * toString
     * @return string representing a method
     *****************************************/
    public String toString(){
        String temp  = "";
        temp += "\t"+this.getMethodSignature();
        return temp;
    }
    
}