/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collection;

import java.io.*;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * Clase que permite leer un documento de texto
 * 
 * @author Francisco Javier Moya Viedma
 * @author Jose Antonio Medina García
 * @author Nasrdine El Houfi
 * @author Francisco Javier Díaz Herrera
 */
public class Reader {
    
    
    /**
     * Parsea un documento (file), dado su id
     *
     * @param id identificador del documento
     * @param file archivo
     * @return Document
     * @throws IOException IO Exception
     * @throws TikaException Tika Exception
     * @throws SAXException SAX Exception
     */
    public Document parserDoc(int id, File file) throws IOException, TikaException, SAXException{
        
        
        //Leemos el documento
        InputStream input = null;
        
        try{
            input = new FileInputStream(file);
        }catch(FileNotFoundException e){}        
        
        //Extraemos información y lo parseamos
        Metadata metadata = new Metadata(); //Metadatos
        ContentHandler ch = new BodyContentHandler(-1); //ContentHandler
        ParseContext parseContext = new ParseContext(); //Para modificar el comportamiento  de ContentHandler

        AutoDetectParser parser = new AutoDetectParser(); //Detectamos el tipo de archivo          

        //Parseamos el stream
         try{ 
             parser.parse(input, ch, metadata, parseContext);
         }
         catch (IOException e){}
         
        Document doc = new Document(id,ch.toString(), file.getName());
        
         return doc; //Devolvemos un string con los tokens;
    }
} //End Class