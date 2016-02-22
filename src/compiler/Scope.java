/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.util.ArrayList;

/**
 *
 * @author Pablo
 */
public class Scope {
  
    private int idScope;
    private Scope anterior;
    private ArrayList<Scope> siguientes;
    
    public static int ambitoActual;

    public Scope(int idScope, Scope anterior) {
        this.idScope = idScope;
        this.anterior = anterior;
        
    }

    public Scope() {
        this.idScope = ambitoActual++;
        this.siguientes = new ArrayList();
    }

    public int getIdScope() {
        return idScope;
    }

    public void setIdScope(int idScope) {
        this.idScope = idScope;
    }

    public Scope getAnterior() {
        return anterior;
    }

    public void setAnterior(Scope anterior) {
        this.anterior = anterior;
    }

    public ArrayList<Scope> getSiguientes() {
        return siguientes;
    }

    public void setSiguientes(ArrayList<Scope> siguientes) {
        this.siguientes = siguientes;
    }

    public static int getAmbitoActual() {
        return ambitoActual;
    }

    public static void setAmbitoActual(int ambitoActual) {
        Scope.ambitoActual = ambitoActual;
    }
    
    public void addSiguiente(Scope hijo){
        this.siguientes.add(hijo);
    }
    

    @Override
    public String toString() {
        return "Scope{" + "idScope=" + idScope + ", anterior=" + anterior + ", siguientes=" + siguientes + '}';
    }
    
    
    
            
    
}
