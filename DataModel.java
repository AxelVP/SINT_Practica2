package p2;

import p2.Sint164P2;

import java.io.*;
import java.net.MalformedURLException;
import java.time.Year;
import java.util.LinkedList;
import java.util.*;
import java.net.*;
import org.w3c.dom.Document;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.helpers.DefaultHandler;

public class DataModel {
    
    private XPathFactory xpathFactory = XPathFactory.newInstance(); 
    private XPath xpath = xpathFactory.newXPath();

    public DataModel(){ 

    }


    /*********************************************AÑOS*******************************************************/
    public ArrayList<String> getQ1Years (){

        ArrayList<String> Years = new ArrayList<String>();

        Years.addAll(Sint164P2.MapaDocument.keySet());

        Collections.sort(Years,Collections.reverseOrder());

       // Years.addAll(MapaDocument.keySet());

        return Years;

    }

    /*****************************************PELICULAS*******************************************************/

    public ArrayList<Movie> getQ1Movies (String year){

        ArrayList<Movie> Movies = new ArrayList<Movie>();
        Document documento = Sint164P2.MapaDocument.get(year);
        String sinopsis="";

        NodeList nlMovies = null;

        try{
            nlMovies = (NodeList) xpath.evaluate("/Movies/Movie", documento, XPathConstants.NODESET);

            
        }catch(XPathExpressionException e){
            e.printStackTrace();
        }

        for(int i=0; i<nlMovies.getLength(); i++){

            ArrayList <String> Arraygenre = new ArrayList<String>();
            String duration = "";

            /**************************SINOPSIS***********************/
            try{
                sinopsis = (String) xpath.evaluate("text()[normalize-space()]", nlMovies.item(i), XPathConstants.STRING);
                
            }catch(XPathExpressionException e){
                e.printStackTrace();
            }

            sinopsis=sinopsis.trim();
            /*********************************************************/
            

            /*************************TITULO**************************/
            Element Movie = (Element) nlMovies.item(i);
            NodeList nlMovie = Movie.getElementsByTagName("Title");
            Element elemNombrePelicula = (Element) nlMovie.item(0);

            String nombrePelicula = (String) elemNombrePelicula.getTextContent().trim();
            /*********************************************************/

            /*************************GENEROS*************************/
            NodeList nlGenre = Movie.getElementsByTagName("Genre");
            for(int x=0; x<nlGenre.getLength(); x++ ){
                Element elemGenre = (Element) nlGenre.item(x);
                String genre = (String) elemGenre.getTextContent().trim();
                Arraygenre.add(genre);
            }
            /*********************************************************/

            /***********************DURACION**************************/
            NodeList nlDuration = Movie.getElementsByTagName("Duration");
            Element elemDuration = (Element) nlDuration.item(0);
            if(elemDuration!=null){
                duration = (String) elemDuration.getTextContent().trim();
            }
            /*********************************************************/

            /*************************IDIOMAS*************************/
            String lang = Movie.getAttribute("langs").trim();
            /*********************************************************/

            
            Movies.add(new Movie(nombrePelicula, Arraygenre, duration, sinopsis, lang));
            
            
        }
        
        Collections.sort(Movies);

        return Movies;

    }

    /*****************************************CASTING*******************************************************/

    public ArrayList<Cast> getQ1Cast (String year, String movie){

        ArrayList<Cast> Cast = new ArrayList<Cast>();
        Document documento = Sint164P2.MapaDocument.get(year);

        NodeList nlCasting = null;
        try{

            nlCasting = (NodeList) xpath.evaluate("/Movies/Movie[Title='"+movie+"']/Cast", documento, XPathConstants.NODESET);

        }catch(XPathExpressionException e){
            e.printStackTrace();
        }

        for(int i=0; i<nlCasting.getLength();i++){

            Element Casting = (Element) nlCasting.item(i);

            /********************NOMBRE ACTOR*************************/
            NodeList nlNombre = Casting.getElementsByTagName("Name");
            Element nombre = (Element) nlNombre.item(0);
            String nombreActor = nombre.getTextContent().trim();
            /*********************************************************/

            /***************************ROL***************************/
            NodeList nlRole = Casting.getElementsByTagName("Role");
            Element rol = (Element) nlRole.item(0);
            String papel = rol.getTextContent().trim();
            /*********************************************************/


            /**************************CONTACTO***********************/
            NodeList nlPhone = Casting.getElementsByTagName("Phone");
            NodeList nlEmail = Casting.getElementsByTagName("Email");
            Element telefono;
            Element email;
            String contacto;
            if(nlPhone.getLength() != 0){
                telefono = (Element) nlPhone.item(0);
                contacto = telefono.getTextContent().trim();
            }else{
                email = (Element) nlEmail.item(0);
                contacto = email.getTextContent().trim();
            }
            /*********************************************************/
            
            /**************************ID ****************************/
            String id = Casting.getAttribute("id").trim();
            /*********************************************************/

            Cast.add(new Cast(nombreActor, papel, contacto, id));

            
        }

        Collections.sort(Cast);

        return Cast;

    }

}
