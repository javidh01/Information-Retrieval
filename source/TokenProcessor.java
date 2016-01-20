/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collection;

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.spanishStemmer;

import java.util.List;

/**
 * Clase que permite procesar los tokens
 * 
 * @author Francisco Javier Moya Viedma
 * @author Jose Antonio Medina García
 * @author Nasrdine El Houfi
 * @author Francisco Javier Díaz Herrera
 */
public class TokenProcessor {
    
    /**
     * Elimina las palabras vacías del documento
     * 
     * @param doc Objeto Documento
     */
    public static void removeStopWords(Document doc){
        Cleaner cl = new Cleaner();
        
        if(doc.getIdiom().equals("es")){
            cl.removeSpStopWords(doc.getTokens());
        }        
    }
    
    /**
     * Estemiza un documento según su idioma
     * @param doc Objeto Documento
     */
    public static void stemDocument(Document doc){
        
        if(doc.getIdiom().equals("es")){
            SnowballStemmer sp = new spanishStemmer();
            stemDocumentES(doc,sp);
        }
    }
    
    /**
     * Estemiza el documento
     * 
     * @param doc Objeto Documento
     * @param st SnowballStemmer
     */
    public static void stemDocumentES(Document doc, SnowballStemmer st){
        List<String> tokens = doc.getTokens();
        
        for(int i = 0; i < tokens.size(); i++){
            st.setCurrent(tokens.get(i));
            if(st.stem()){
                tokens.set(i,st.getCurrent());
            }
        }
    }
    
    /**
     * Permite estemizar una consulta
     * @param q consulta
     * @param idiom idioma de la consulta
     * @return string con la consulta estemizada
     */
    public static String stemQ(String q, String idiom){
        String b = null;       
        
        if(idiom.equals("es")){
            SnowballStemmer spStemmer = new spanishStemmer();
            spStemmer.setCurrent(q.toLowerCase());
            
            if(spStemmer.stem()) b= spStemmer.getCurrent();
            else b= q;
        }
        //System.out.println(q + " " + b);
        return b;
    }
}//End Class
