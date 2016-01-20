/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collection;

/**
 *
 * Representa un par de tipos S y T
 * 
 * @author Francisco Javier Moya Viedma
 * @author Jose Antonio Medina García
 * @author Nasrdine El Houfi
 * @author Francisco Javier Díaz Herrera
 * 
 * @param <S> S
 * @param <T> T
 */
    
public class Pair<S, T>{
    private S first; //Primer miembro del pair
    private T second; //Segundo miembro del pair
    
    /**
     * Constructor que inicializa 
     * un par de elementos
     * @param first S
     * @param second T
     */
    public Pair(S first, T second){
        this.first = first;
        this.second = second;
    }
    
    /**
     * Permite cambiar el valor
     * del primer elemento del par
     * @param first S
     */
    public void setFirst(S first){
        this.first = first;
    }

    /**
     * Permite cambiar el valor
     * del segundo elemento del par
     * @param second T
     */
    public void setSecond(T second) {
        this.second = second;
    }

    /**
     * Devuelve el primer elemento
     * del par
     * @return first
     */
    public S getFirst() {
     return this.first;
    }
    
    /**
     * Devuelve el segundo elemento
     * del par
     * @return second
     */
    public T getSecond() {
     return this.second;
    }   
    
}
