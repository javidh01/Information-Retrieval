/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collection;


import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

/**
 * Clase que representa el índice de documentos
 * 
 * @author Francisco Javier Moya Viedma
 * @author Jose Antonio Medina García
 * @author Nasrdine El Houfi
 * @author Francisco Javier Díaz Herrera
 */
public class Index {
    private final Map<Integer, Map<Integer, Integer>> index = new HashMap();
    static HashMap<String,Integer> vocab_help = new HashMap<>();
    
   /**
    * Devuelve el objeto índice
    * @return index
    */   
    public Map<Integer, Map<Integer, Integer>> getIndex(){
        return index;
    }
    
    /**
     * Permite cambiar el vocabulario de
     * de un documento
     * @param vocab vocabulario de la colección
     */
    public void setVocab(HashMap<String,Integer> vocab){
        vocab_help = vocab;
    }
    
    /**
     * Crea el índice
     * @param doc Objeto Documento
     * @param col Lista de documentos (colección)
     */
    public void createIndex(Document doc, ArrayList<Document> col){
        //System.out.println("Entro createIndex()");
        List<String> tokens = doc.getTokens();
        for(String token : tokens ){
            indexToken(doc.getId(), token, col);
        }
    }
    
    /**
     * Devuelve la lista de tokens
     * asociada al índice
     * @return tokens
     */
    public List<Pair<String, Integer>> getTokens(){
        List<Pair<String, Integer>> tokens = new ArrayList();
        String key;
        
        for(Object entry : index.entrySet()){
            Map.Entry pairs = (Map.Entry) entry;
            key = pairs.getKey().toString();
            tokens.add(new Pair(key, ((Map<String, Integer>)pairs.getValue()).size()));
        }
        return tokens; //Devolvemos la lista con los tokens
    }
    
    /**
     * Permite indexar un token en el índice
     * @param docId identificador del documento
     * @param token tokens del documento
     * @param col   colección de documentos
     */
    private void indexToken(int docId, String token, ArrayList<Document> col){

        int currenFreq = -1; //Inicialmente la frecuencia tiene un valor nulo

        Map<Integer, Integer> oc = new HashMap<>(); //Número de ocurrencias del token
       
        HashMap<String, Integer> aux = col.get(docId-1).getFrec();
        Iterator it = aux.entrySet().iterator();
        //System.out.println( col.get(docId-1).getFrec());
        while(it.hasNext()){
            Map.Entry e = (Map.Entry)it.next();

            if(e.getKey() == token){
              // System.out.println(e.getKey() + " == " + token);
                currenFreq = (int) e.getValue();
                oc.put(docId,currenFreq);
            }
        }
           
        if ( currenFreq != -1  ){
           int indice = -1;
           if (vocab_help.get(token) != null) {
               indice = vocab_help.get(token);
               
               //System.out.println("indice: " + indice  + " término: " + token);
                if ( !index.containsKey(indice) )
                    index.put(indice, oc); //Lo añadimos al índice
                else 
                    index.get(indice).put(docId, currenFreq);
           }
           else System.out.println("Error: No existe token en lista VOCABULARIO");
        }

    }
    
    /**
     * Permite imprimir el índice
     * @throws FileNotFoundException  File Not Found Exception
     */
    public void printIndex() throws FileNotFoundException{
        PrintStream descriptor = new PrintStream("index.txt"); 
        
        descriptor.print("tID       {idD = idf}");
        descriptor.print("\n");
        descriptor.print("----------------------------------------------------------------------------------------------------------------------------------");
        descriptor.print("\n");
        for (Map.Entry e : index.entrySet()) {
            descriptor.print(e.getKey());
            descriptor.print("\t\t");
            descriptor.print(e.getValue().toString());
            descriptor.print("\n");
            //System.out.println(e.getKey() + " --> " + e.getValue().toString());
        }
    }
} //End Class