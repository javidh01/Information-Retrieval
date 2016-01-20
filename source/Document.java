/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collection;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.tika.language.LanguageIdentifier;

/**
 * Clase que representa un documento de
 * la colección almacenado en tokens
 * 
 * @author Francisco Javier Moya Viedma
 * @author Jose Antonio Medina García
 * @author Nasrdine El Houfi
 * @author Francisco Javier Díaz Herrera
 */
public class Document {
    private final String name;
    private final int id;
    private List<String> tokens = new ArrayList();
    private final String idiom;
    private final  StringTokenizer toke;
     HashMap<String, Integer> listfrec = new HashMap();
     HashMap<Integer,String> vocab = new HashMap();
     HashMap<Integer,String> docs = new HashMap();
    
     /**
      * Consturctor que inicializa un objeto
      * documento con su identificador, texto y nombre
      * 
      * @param id identificador del documento
      * @param texto contenido del documento (texto)
      * @param name_ nombre dle documento
      */
    public Document(int id, String texto, String name_){

        this.id = id;
        name = name_;
        //Obtenemos el lenguaje en el que está el documento
        LanguageIdentifier ident = new LanguageIdentifier(texto);
        this.idiom = ident.getLanguage();
        
        //Eliminamos caracteres que no vamos a tokenizar
        String delim = ":;. \"|\'=(),-+/%1234567890•¿?¡!/—...»«{}[]@#©\t\n\b\r\f";
        toke = new StringTokenizer(texto.toLowerCase(),delim);
       
        //Rellenamos la tabla de tokens
        fillToken();
    }
    
    /**
     * Devuelve el nombre del documento
     * @return  name
     */
     public String getName(){   
         return name;
     }
     
     /**
      * Rellena la lista de tokens del documento
      */
    public void fillToken(){        
       while(toke.hasMoreElements()){ 
           String token = toke.nextToken();
           tokens.add(token);
        }
    }
    
    /**
     * Rellena la lista con las frecuencias
     * de los tokesn del documento
     */
    public void fillFrec(){        
         
        Iterator it = tokens.iterator();
        while(it.hasNext()){ 
            String token = (String) it.next();
             if( !listfrec.containsKey(token)){
                listfrec.put(token,1);
            }
            else{               
                listfrec.put(token,(int)listfrec.get(token)+1);
            }
         }
    }
    
    /**
     * Devuelve la lista con las frecuencias
     * de los tokens del documento
     * 
     * @return listfrec
     */
    public HashMap<String,Integer> getFrec(){
        return listfrec;
    }
    
     /**
     * 
     * Método que permite imprimir una lista pasada como argumento
     * 
     * @param lista lista de String
     */
    public  void printList(List<String> lista){
        for (String l : lista) {
            System.out.println(l);
        }
    }
    
    
    /**
     * Devuelve la lista de tokens
     * @return tokens
     */
    public List<String> getTokens(){
        
        return tokens;
    }
    
    /**
     * Devuelve el identificador del documento
     * @return id
     */
    public int getId(){
        return this.id;
    }
    
    /**
     * Devuelve el idioma del documento
     * @return idiom
     */
    public String getIdiom(){
        return this.idiom;
    }
} //End Class