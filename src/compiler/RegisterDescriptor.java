/**
* Universidad Del Valle de Guatemala
* 22-may-2016
* Pablo Díaz 13203
*/

package compiler;

import java.util.ArrayList;

/**
 * Para cada registro , un descriptor de registros lleva el control
 * del nombre de la variable en la que se encuentra un registro.
 * Al inicio de cada bloque el descriptor de registros se encuentra vacío.
 * Un registro puede tener cero o más nombres.
 * @author Pablo
 */
public class RegisterDescriptor {
    
    private ArrayList<Registro> registros; 
    
    
    public RegisterDescriptor() {
        this.registros = new ArrayList();
    }
    
    public String revisarRegistros(String nombreVar){
       
        for(Registro rg: registros){
            if (rg.getVariables().contains(nombreVar)){
                return "R"+rg.getNumRegistro();
            }
            
        }
        
        return "";
    }
    
    public Registro agregarRegistro(String nombreVar) {
        if (this.registros.size() == 4){
            return this.buscarRegistroMenor();
        }
        Registro rg  = new Registro(nombreVar);
        this.registros.add(rg);
        return rg;
    }
    

    public Registro buscarRegistroMenor(){
        if (this.registros.size()>0){
            Registro p1 = this.registros.get(0);
            for (int i = 1;i<this.registros.size();i++){
                if (this.registros.get(i).getVariables().size()<p1.getVariables().size()){
                    p1 = this.registros.get(i);
                }
            }
            return p1;
        }
        return null;
        
    }
     public Registro buscarRegistroMenor(Registro actual){
         Registro metodo = buscarRegistroMenor();
         if (metodo.getRegistro().equals(actual.getRegistro())){
            Registro nuevo = agregarRegistro(actual.getVariables().get(0).toString());
            metodo = nuevo;
         }
        return metodo;
    }
    
    public void setRegistroValor(Registro r, String var){
        r.getVariables().clear();
        r.addVariable(var);
    }
    
    public void setRegistroValor(String registro, String var){
        for(Registro r: this.registros){
            if (r.getRegistro().equals(registro)){
                r.getVariables().clear();
                r.addVariable(var);
            }
        }
    }
   
}
