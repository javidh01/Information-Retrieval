/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collection;


import java.io.*;
import static java.lang.Math.log;
import java.util.*;

import java.util.ArrayList;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

/** 
 * Clase que representa una Colección de Documentos de Texto
 * @author Francisco Javier Moya Viedma
 * @author Jose Antonio Medina García
 * @author Nasrdine El Houfi
 * @author Francisco Javier Díaz Herrera
 */
public class Collection{
    
    static Index index = new Index(); //Índice
    static ArrayList<File> listFiles = new ArrayList();
    static ArrayList<Document> collection = new ArrayList(); //Colección de documentos
    static Reader read = new Reader(); //Lector de documentos
    static String path; //Ruta donde se encuentra la colección
    static HashMap<String,Integer> vocab = new HashMap<>();
    static HashMap<Integer,String> docum = new HashMap<>();
    static HashMap<Integer,Double> idf = new HashMap<>();
    static HashMap<Integer,Integer> normaDoc = new HashMap<>();
    static int idDoc = 1;
    
    /** 
     * 
     * Metodo que almacena en una lista las rutas
     * de todos los archivos que cuelgan de un directorio
     * 
     * @param file archivo
     * @return Boolean      
     * 
     **/

    public static Boolean addFiles(File file){ 
       
        if(!file.exists()){
            //System.out.println(file + " no existe.");
            return false;
        }else if(file.isDirectory()){
            for(File f : file.listFiles()){
                addFiles(f);                   
            }
        } else{
            listFiles.add(file);     
        }
        return true;
    }
    
    /**
     * 
     * @param dir directorio donde se encuentra la colección
     * @return true si se ha realizado correctamente
     *         false en caso contrario
     * @throws IOException IO Exception
     * @throws TikaException Tika Exception
     * @throws SAXException SAX Exception
     */
    public static Boolean load(File dir) throws IOException, TikaException, SAXException{
             
        if(addFiles(dir)){
            Reader reader = new Reader();
            int idFile = 1;
            for(File file : listFiles){
                collection.add(reader.parserDoc(idFile, file));
                idFile++;
                
            }
            return true;
        }
        return false;
    }
    
    /**
     * Permite llamar a los métodos
     * correspondientes a eliminar
     * palabras vacías de los
     * documentos y, posteriormente,
     * estemizar cada término
     */
    public static void preprocessColl(){
        
        for(Document doc : collection){
            TokenProcessor.removeStopWords(doc);
            TokenProcessor.stemDocument(doc);
        
        }
    }
    
    /**
     * Crea el vocabulario de la colección
     */
     public static void createVoc(){
        Iterator it = collection.iterator();
            //System.out.println( col.get(docId-1).getFrec());
        
        int idTer = 1;
        while(it.hasNext()){
            Document doc = (Document) it.next();
            
            List<String> a = doc.getTokens();
            
            for ( String b : a ){
                if ( !vocab.containsKey(b) ){
                    vocab.put( b, idTer);
                     ++idTer;
               //System.out.println( idTer + " " + b);
                }
               
            }
       } 
    }
     
    /**
     * Crea los objetos documento de la colección
     */
    public static void createDoc(){
        for(Document doc : collection){
            docum.put(doc.getId(), doc.getName());
        }
    }
    
    /**
     * Permite crear el índice de la colección
     */
    public static void indexation(  ){
        
        index.setVocab(vocab);
       
        for(Document doc : collection){
            doc.fillFrec();
            index.createIndex(doc, collection);
        }
        
    }
    
    /**
     *  La frecuencia inversa de documento es una medida de si el término es común o no, 
     *  en la colección de documentos. Se obtiene dividiendo el número total de documentos por 
     *  el número de documentos que 
     *  contienen el término, y se toma el logaritmo de ese cociente
     */
     public static void createIdf(){      
        for(Document doc : collection){
            idf.put(doc.getId(), 0.0);
        }
        
        double numero_documentos = docum.size();
        
        Map<Integer, Map<Integer, Integer>> index_aux = index.getIndex();
        Iterator it = index_aux.entrySet().iterator();
        
        while(it.hasNext()){
                Map.Entry entrada1 = (Map.Entry) it.next();
                Map<Integer, Integer> mapa_aux = (Map<Integer, Integer>) entrada1.getValue();
                         
                idf.put((Integer) entrada1.getKey(), 1+log2( numero_documentos / ((double) mapa_aux.size()) ) );
                //System.out.println( numero_documentos / ((double) mapa_aux.size()) );
        }
    }
     
    /**
     * Calcula el logaritmo en
     * base 2 de a
     * @param a Valor
     * @return log en base 2 de a
     */
    public static double log2(double a){
         return log(a)/log(2);
     }
    
     /**
      * Obtiene la norma de cada 
      * documento de la colección
      */
    public static void createNorma(){
        
        for(Document doc : collection){
            normaDoc.put(doc.getId(), 0);
        }
        
        
        Map<Integer, Map<Integer, Integer>> index_aux = index.getIndex();
        Iterator it = index_aux.entrySet().iterator();
        
        while(it.hasNext()){
            Map.Entry entrada1 = (Map.Entry) it.next();
            Map<Integer, Integer> mapa_aux = (Map<Integer, Integer>) entrada1.getValue();
            Iterator it2 = mapa_aux.entrySet().iterator();

            while(it2.hasNext()){
                Map.Entry entrada2 = (Map.Entry)it2.next();

                if ( normaDoc.get(entrada2.getKey()) <
                        (int) entrada2.getValue() ) {
                    normaDoc.put((Integer)entrada2.getKey(), (Integer)entrada2.getValue());
                  }
            }
        }
    }
    
    /**
     * Permite imprimir un Map
     * @param eleccion eleccion
     * @throws FileNotFoundException  File Not Found Exception
     */
    public static void printMap( String eleccion ) throws FileNotFoundException{
        
       
        if ( eleccion.equals("1") ){
            for (Map.Entry e : vocab.entrySet()) { 
                System.out.println(e.getKey() + " --> " + e.getValue().toString());
            }
        }
        else if ( eleccion.equals("2") ){
             for (Map.Entry e : docum.entrySet()) { 
                System.out.println(e.getKey() + " --> " + e.getValue().toString());
            }
        }
        else if ( eleccion.equals("3")){
             for (Map.Entry e : idf.entrySet()) { 
                System.out.println(e.getKey() + " --> " + e.getValue().toString());
            }
        
        }
        else if ( eleccion.equals("4")){
             for (Map.Entry e : normaDoc.entrySet()) { 
                System.out.println(e.getKey() + " --> " + e.getValue().toString());
            }
        
        }
        else if ( eleccion.equals("5") ) index.printIndex();
    }
    
    private void processQuery(String query, String idiom){
        String token = TokenProcessor.stemQ(query,idiom);
        
        if(token.isEmpty()){
            System.out.println("Introduzca un término");
        }
        
        //int n_ocurrences = 
    }
    
    /**
     * Imprime mensaje de bienvenida y solicita el path
     * en donde se encuentra la colección que se quiere indexar
     */
    public static void printMenuBienVenida(){
        
        System.out.println("************************************************************");
        System.out.println("*****                                                  *****");
        System.out.println("*******   Práctica 2 - Recuperación de Información    ******");
        System.out.println("*****                                                  *****");
        System.out.println("************************************************************\n");
        
        System.out.println("\nIntroduzca el path de la colección\n");
        System.out.print("-> ");
    }
    
    /**
     * Imprime menú con las opciones de
     * ejecución del sistema
     * 
     * @throws FileNotFoundException FileNotFoundException
     */
    public static void printMenu() throws FileNotFoundException{
        System.out.println("************************************************************");
        System.out.println("*****       ¿Qué desea mostrar por pantalla?           *****");
        System.out.println("************************************************************\n");
        System.out.println("1.Vocabulario de la colección\n");
        System.out.println("2.Estructura Documento (idD, documento)\n");
        System.out.println("3.Estructura idf\n");
        System.out.println("4.Normas de cada documento\n");
        System.out.println("5.Índice (se almacena en el archivo index.txt\n");
        
        System.out.print("-> ");
               
        //Para leer los datos de entrada
        String opcion;
        Scanner in_op = new Scanner(System.in);
        opcion = in_op.next(); 
        
        printMap(opcion);
    }
    
    /**
     * 
     * @param args the command line arguments
     * @throws java.io.IOException IO Exception
     * @throws org.apache.tika.exception.TikaException Tika Exception
     * @throws org.xml.sax.SAXException SAX Exception
     * 
     */
    public static void main(String[] args) throws IOException, TikaException, SAXException{
        
        printMenuBienVenida();
        
         //Para leer los datos de entrada
        Scanner in = new Scanner(System.in);
        path = in.next(); 
        
        //path = "C:\\Users\\javi\\Desktop\\colecciones";
        
        //Directorio donde se encuentra la colección
        File file = new File(path);     
        
        //Añadimos la ruta a nuestra función
        
        if(load(file)){ //Si se carga correctamente   
            System.out.println("\n->Se ha cargado correctamente la colección");        
        
            System.out.println("\n----->Tamaño de la colección: " + collection.size());         
            
            //Eliminamos palabras vacías y estemizamos cada documento
            preprocessColl();
            System.out.println("\n->Procesamiento de datos terminado");
            
            //Creamos el volcabulario
            createVoc();
            System.out.println("\n->Estructura vocabulario creada");
            
            createDoc();
            System.out.println("\n->Estructura documentos creada");
            
            
            long t0 = System.currentTimeMillis();
            //Creamos el índice
            indexation();
            long t1 = System.currentTimeMillis();
            System.out.println("\n->Estructura índice creada" + "\n\t--->Se ha tardado " + (t1-t0)/1000 + " segundos");
            //System.out.println("Tamaño colección: " + (int)vocab.size());
            
            
            
            //Creamos el IDF
            createIdf();
            System.out.println("\n->Estructura idf creada");
            
            //Obtenemos la norma del documento
            createNorma();
            System.out.println("\n->Estructura normaDoc creada");
            
            
            printMenu();
            //System.out.println(collection.get(66).getId() + " " + collection.get(66).getName());
        } //End if
        else{
            System.err.print("\nNo se ha podido cargar la colección desde ese directorio\n");
        }
    }
    
} //End Class
