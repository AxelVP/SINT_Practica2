package p2;

import p2.Cast;
import p2.Movie;
import p2.FrontEnd;

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
import javax.xml.crypto.Data;
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


public class Sint164P2 extends HttpServlet{

    
    private final String passwordServlet = "asdfg12345";
    
    FrontEnd FrontEnd = new FrontEnd();
    
    private XPathFactory xpathFactory = XPathFactory.newInstance(); 
    private XPath xpath = xpathFactory.newXPath();
    
    
    private String documentoXML = "http://alberto.gil.webs.uvigo.es/SINT/21-22/mml2001.xml"; /**********Primer XML************/
    
    
    static HashMap<String,Document> MapaDocument = new HashMap<String,Document>();
    
    DataModel DataModel = new DataModel();

/*****************************INIT*****************************/

    public void init(ServletConfig config){
 
        ServletContext context = config.getServletContext();
        try{
            XML_Parser(context.getResource("/p2/mml.xsd"));
        }catch(Exception e){
            System.out.println("ERROR EN INIT");
            e.printStackTrace();
        }
    }

/****************************************doGet*****************************************/

    public void doGet(HttpServletRequest req, HttpServletResponse res){
        
        String auto = req.getParameter("auto");
        String pass = req.getParameter("p");
        String pphase = req.getParameter("pphase");
        String pmovie = req.getParameter("pmovie");
        String pyear = req.getParameter("pyear");

        ArrayList <String> years = new ArrayList<String>();
        ArrayList <Movie> movies = new ArrayList<Movie>();
        ArrayList <Cast> cast = new ArrayList<Cast>();

        if(auto == null){
            auto="false";
        }
        if(pphase == null){
            pphase="01";
        }

/*************************************ERROR CONTRASEÑA*************************************/

        if(pass == null || !pass.equals(passwordServlet)){
            if(auto.equals("true")){
                FrontEnd.Fase02XML(req, res, pass);
                return;

            }else if(auto.equals("false")){
                FrontEnd.Fase02HTML(req, res, pass);
                return;

            }
        }

/***************************************FALTA PARAMETRO******************************************/        

        if(auto.equals("true")){
            if(pyear == null){
                if(pphase.equals("12") || pphase.equals("13")){
                    FrontEnd.FaltaParametroXML(req, res, "pyear");
                    return;
                }

            }else if(pmovie == null){
                if(pphase.equals("13")){
                    FrontEnd.FaltaParametroXML(req, res, "pmovie");
                    return;                    
                }
            }
        }

        if(auto.equals("false")){
            if(pyear == null){
                if(pphase.equals("12") || pphase.equals("13")){
                    FrontEnd.FaltaParametroHTML(req, res, "pyear");
                    return;
                }

            }else if(pmovie == null){
                if(pphase.equals("13")){
                    FrontEnd.FaltaParametroHTML(req, res, "pmovie");
                    return;                    
                }
            }
        }

        if(pphase.equals("11")){

            years=DataModel.getQ1Years();

        }else if(pphase.equals("12")){

            movies=DataModel.getQ1Movies(pyear);

        }else if(pphase.equals("13")){

            cast=DataModel.getQ1Cast(pyear, pmovie);

        }
 /***************************************FASES XML************************************************************/
        if(auto.equals("true")){ 

            switch(pphase) {
                case "01":
                    FrontEnd.Fase01XML(req, res);
                    return;

                case "11":
                    FrontEnd.Fase11XML(req, res, years);
                    return;

                case "12":
                    FrontEnd.Fase12XML(req, res, movies);
                    return;

                case "13":
                    FrontEnd.Fase13XML(req, res, cast);
                    return;
                
                default:
                    FrontEnd.Fase01XML(req, res);
                    return;
            }
/***********************************FASES HTML***************************************/
        }else{ 

            switch(pphase){
                case "01":
                    FrontEnd.Fase01HTML(req, res);
                    return;

                case "11":
                    FrontEnd.Fase11HTML(req, res, years);
                    return;

                case "12":
                    FrontEnd.Fase12HTML(req, res, movies);
                    return;

                case "13":
                    FrontEnd.Fase13HTML(req, res, cast);
                    return;
                
                default:
                    FrontEnd.Fase01HTML(req, res);
                    return;
            }
        }
    }


/***************************************************XML PARSER (URL: DIRECTORIO DEL PARSER)********************************************************/
    public void XML_Parser(URL parser){

/**************LISTA DE FICHEROS A PARSEAR O YA PARSEADOS*************************/
        LinkedList<URL> parsear = new LinkedList<URL>();
        LinkedList<URL> parseado = new LinkedList<URL>();
/*********************************************************************************/

        SchemaFactory factorySchema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = null;  

        DocumentBuilderFactory factoryDocument = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;

        URL URL_XML =   null;
        

/****************VARIABLES COMPROBADOR******************************/
        String year = "";
        NodeList otroMML = null;
        Document document = null;
        URLConnection fichero = null;
        /*************************************************************/
        
        try {
            URL_XML = new URL(documentoXML);
            
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        parsear.add(URL_XML); /**************Añadimos el primer xml para hacer el primer parser y descubrir nuevos xml********************/

        try{
            schema = factorySchema.newSchema(parser);
        }catch(SAXException e){   
            e.printStackTrace();
        }

        factoryDocument.setSchema(schema);
        try{
            documentBuilder = factoryDocument.newDocumentBuilder();
        }catch(ParserConfigurationException e){
            e.printStackTrace();
        }

        documentBuilder.setErrorHandler(new GestorErrores());

        while(!parsear.isEmpty()){

            try{
                fichero = parsear.pop().openConnection();
                document = documentBuilder.parse(fichero.getInputStream(),fichero.getURL().toString());  /********Devuelve el arbol dom del documento***********/
                parseado.add(fichero.getURL());

                year = (String) xpath.evaluate("/Movies/Year", document, XPathConstants.STRING);
                otroMML = (NodeList) xpath.evaluate("/Movies/Movie/Cast/MML", document, XPathConstants.NODESET);

            }catch(IOException | XPathExpressionException e){
                parseado.add(fichero.getURL());
                e.printStackTrace();
                continue;
            }catch(SAXException ex){
                parseado.add(fichero.getURL());
                ex.printStackTrace();
                continue;
            }

            MapaDocument.put(year, document);
            
            for(int i=0; i<otroMML.getLength(); i++){
                String NodeActual="";
                URL documento = null;

                try{

                    NodeActual= (String) xpath.evaluate("text()",otroMML.item(i) ,XPathConstants.STRING);
                    documento= new URL(fichero.getURL(),NodeActual);

                }catch(XPathExpressionException | MalformedURLException e){
                    e.printStackTrace();
                    continue;
                }

                if((!parsear.contains(documento)) && (!parseado.contains(documento))){
                    parsear.add(documento);
                }
            }

        }
    }

    /**********************************CLASE PARA LOS ERRORES*****************************************/

    public class GestorErrores extends DefaultHandler {

        public GestorErrores (){}
    
        public void warning(SAXParseException spe) throws SAXException{ 
            System.out.println("Warning: "+spe.toString()); 
            throw(spe);
        }
            
        public void error(SAXParseException spe) throws SAXException{
            System.out.println("Error: "+spe.toString());
            throw(spe);
        }
        
        public void fatalError(SAXParseException spe) throws SAXException{
            System.out.println("FatalError: "+spe.toString());  
            throw(spe);        
        }
    }
        
}

