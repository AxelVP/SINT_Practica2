package p2;

import p2.Cast;
import p2.Movie;

import java.io.*;
import java.net.MalformedURLException;
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

public class FrontEnd extends HttpServlet{
    
    private String password="asdfg12345";
    

    /********************************HTML*********************************/

    public void Fase01HTML(HttpServletRequest req, HttpServletResponse res){
        
            res.setCharacterEncoding("UTF-8");
            res.setContentType("text/html");

            String pass = req.getParameter("p");
        try{

            PrintWriter out = res.getWriter();

            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=''=UTF-8'/>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servicio de consulta de peliculas</h1><br>");
            out.println("<h2>Bienvenido al Servicio</h2><br>");
            out.println("<h3>Selecione una consulta</h3><br>");
            out.println("<form>");
            out.println("<input type='hidden' name='pphase' value='01'>");
            out.println("<input type='hidden' name='p' value="+pass+">");
            out.println("<li><a href='?p="+pass+"&pphase=11'>Consulta 1:reparto de una película de un año</a></li>");
            out.println("</form>");
            out.println("<div class='derecha' id='foot'> &copy;Axel Valladares </div>");
            out.println("</body>");
            out.println("</html>");

        }catch (Exception e){
            e.printStackTrace();
        }

        return;
    }

    public void Fase02HTML (HttpServletRequest req, HttpServletResponse res, String pass){

        res.setCharacterEncoding("utf-8");
        res.setContentType("text/html");

        if(pass == null){
            FaltaContrasenaHTML(req, res, "no passwd");

        }else if(!pass.equals(password)){
            FaltaContrasenaHTML(req, res, "bad passwd");
        }
        
        return;
    }

    public void Fase11HTML(HttpServletRequest req, HttpServletResponse res, ArrayList<String> years){

        String pass = req.getParameter("p");
        String pphase = req.getParameter("pphase");

        try{

            res.setCharacterEncoding("UTF-8");
            res.setContentType("text/html");

            PrintWriter out = res.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=''=UTF-8'/>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servicio de consulta de peliculas</h1><br>");
            out.println("<h2>Consulta 1: Fase 1</h2><br>");
            out.println("<h3>Selecione un año</h3>");
            out.println("<form>");
            out.println("<ol>");
            for(int i=0; i<years.size();i++){
                out.println("<li><a href='?p="+pass+"&pphase=12&pyear="+years.get(i)+"'>"+years.get(i)+"</a></li>");
            }
            out.println("</ol>");
            out.println("<input type='hidden' name='p' value="+pass+">");
            out.println("<a href='?p="+pass+"&pphase=01'><input type='button' value='Inicio'></a>");
            out.println("</form>");
            out.println("<div class='derecha' id='foot'> &copy;Axel Valladares </div>");
            out.println("</body>");
            out.println("</html>");
            
        }catch(Exception e){}
        return;
    }

    public void Fase12HTML(HttpServletRequest req, HttpServletResponse res, ArrayList <Movie> movies){

        String pass = req.getParameter("p");
        String pphase = req.getParameter("pphase");
        String Year = req.getParameter("pyear");

        try{

            res.setCharacterEncoding("utf-8");
            res.setContentType("text/html");

            PrintWriter out = res.getWriter();

            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=''=UTF-8'/>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servicio de consulta de peliculas</h1><br>");
            out.println("<h2>Consulta 1: Fase 2 (Año ="+Year+")</h2><br>");
            out.println("<h3>Selecione una película: </h3>");
            out.println("<ol>");
            for(int i=0; i<movies.size();i++){
                out.println("<li><a href='?p="+pass+"&pphase=13&pyear="+Year+"&pmovie="+movies.get(i).getTitle()+"'>Pelicula= "+movies.get(i).getTitle()+"</a>"+MovieString(movies.get(i))+"</li>");
            }
            out.println("</ol>");
            out.println("<form>");
            out.println("<input type='hidden' name='p' value="+pass+">");
            out.println("<li><a href='?p="+pass+"&pphase=01'><input type='button' value='Inicio'></a></li>");
            out.println("<li><a href='?p="+pass+"&pphase=11'><input type='button' value='Atras'></a></li>");
            out.println("</form>");
            out.println("<div class='derecha' id='foot'> &copy;Axel Valladares </div>");
            out.println("</body>");
            out.println("</html>");
        }catch(Exception e){
            e.printStackTrace();
        }
        return;
    }


    public void Fase13HTML(HttpServletRequest req, HttpServletResponse res, ArrayList <Cast> cast){

        String pass = req.getParameter("p");
        String pphase = req.getParameter("pphase");
        String Year = req.getParameter("pyear");
        String Movie = req.getParameter("pmovie");

        try{
            res.setCharacterEncoding("utf-8");
            res.setContentType("text/html");

            PrintWriter out = res.getWriter();

            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset==UTF-8/>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servicio de consulta de peliculas</h1><br>");
            out.println("<h2>Consulta 1: Fase 3 (Año = "+Year+", Película = "+Movie+")</h2><br>");
            out.println("<h3>Este es el resultado de la consulta: </h3>");
            out.println("<ol>");
            for(int i=0; i<cast.size();i++){
                out.println("<li><b>Nombre</b>= '" +cast.get(i).name+ "'--- <b>ID</b>= '" +cast.get(i).id+ "'--- <b>Papel</b>= '" +cast.get(i).role+ "'--- <b>Contacto</b>= '" +cast.get(i).contacto+ "'</li>");
            }
            out.println("</ol>");
            out.println("<form>");
            out.println("<input type='hidden' name='p' value="+pass+">");
            out.println("<li><a href='?p="+pass+"&pphase=01'><input type='button' value='Inicio'></a></li>");
            out.println("<li><a href='?p="+pass+"&pphase=12&pyear="+Year+"'><input type='button' value='Atras'></a></li>");
            out.println("</form>");
            out.println("<div class='derecha' id='foot'> &copy;Axel Valladares </div>");
            out.println("</body>");
            out.println("</html>");
        }catch (Exception e){
            e.printStackTrace();
        }
        return;
    }

    /***************************XML************************/

    public void Fase01XML (HttpServletRequest req, HttpServletResponse res){

        res.setCharacterEncoding("utf-8");
        res.setContentType("text/xml");

        String pass = req.getParameter("p");
        
        try{

            PrintWriter out = res.getWriter();

            out.println("<?xml version='1.0' encoding='utf-8'?>");
            out.println("<service>");
            out.println("<status>OK</status>");
            out.println("</service>");
        }catch (Exception e){
            e.printStackTrace();
        }
        return;
    }

    public void Fase02XML (HttpServletRequest req, HttpServletResponse res, String pass){

        res.setCharacterEncoding("utf-8");
        res.setContentType("text/xml");

        if(pass == null){
            FaltaContrasenaXML(req, res, "no passwd");

        }else if(!pass.equals(password)){
            FaltaContrasenaXML(req, res, "bad passwd");
        }
        
        return;
    }

    public void Fase11XML(HttpServletRequest req, HttpServletResponse res, ArrayList<String> years){

        String pass = req.getParameter("p");
        String pphase = req.getParameter("pphase");

        try{

            res.setCharacterEncoding("utf-8");
            res.setContentType("text/xml");

            PrintWriter out = res.getWriter();

            out.println("<?xml version='1.0' encoding='utf-8' ?>");
            out.println("<years>");
            for(int i=0; i<years.size(); i++){
                out.println("<year>"+years.get(i)+"</year>");
            }
            out.println("</years>");
        }catch (Exception e){
            e.printStackTrace();
        }
        return;
    }

    public void Fase12XML(HttpServletRequest req, HttpServletResponse res, ArrayList<Movie> movies){

        String pass = req.getParameter("p");
        String pphase = req.getParameter("pphase");
        String Year = req.getParameter("pyear");

        try{

            res.setCharacterEncoding("utf-8");
            res.setContentType("text/xml");

            PrintWriter out = res.getWriter();

            out.println("<?xml version='1.0' encoding='utf-8'?>");
            out.println("<movies>");
            for(int i=0; i<movies.size();i++){
                out.println("<movie langs='"+movies.get(i).getIdiomas()+"' genres= '"+movies.get(i).devuelveArray()+"' synopsis= '"+movies.get(i).getSinopsis()+"'>"+movies.get(i).getTitle()+"</movie>");
            }
            out.println("</movies>");

        }catch (Exception e){
            e.printStackTrace();
        }
        return;
    }

    public void Fase13XML(HttpServletRequest req, HttpServletResponse res, ArrayList<Cast> cast){

        String pass = req.getParameter("p");
        String pphase = req.getParameter("pphase");
        String Year = req.getParameter("pyear");
        String Movie = req.getParameter("pmovie");
        
        try{

            res.setCharacterEncoding("utf-8");
            res.setContentType("text/xml");

            PrintWriter out = res.getWriter();
            
            out.println("<?xml version='1.0' encoding='utf-8'?>");
            out.println("<thecast>");
            for(int i=0; i<cast.size();i++){
                out.println("<cast id='"+cast.get(i).getId()+"' role='"+cast.get(i).getRole()+"' contact='"+cast.get(i).getContacto()+"'>"+cast.get(i).getName()+"</cast>");
            }
            out.println("</thecast>");

        }catch (Exception e){
            e.printStackTrace();
        }
        return;
    }

    /**********************************************FALTA ALGO HTML***************************************************************/

    public void FaltaContrasenaHTML(HttpServletRequest req, HttpServletResponse res, String error){

        try{
            res.setCharacterEncoding("utf-8");
            res.setContentType("text/html");

            PrintWriter out = res.getWriter();

            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset= 'utf-8'/><title>Consulta</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servicio de consulta de peliculas</h1>");
            out.println("<h2>"+error+"</h2><br>");
            out.println("</body>");
            out.println("<div class='derecha' id='foot'> &copy;Axel Valladares </div>");
            out.println("</html>");

        }catch (Exception e){
            e.printStackTrace();
        }
        return;
    }

    public void FaltaParametroHTML(HttpServletRequest req, HttpServletResponse res, String parametro){

        try{
            res.setCharacterEncoding("utf-8");
            res.setContentType("text/html");

            PrintWriter out = res.getWriter();

            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset= 'utf-8'/><title>Consulta</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servicio de consulta de peliculas</h1><br>");
            out.println("<h2>no param:"+parametro+"</h2><br>");
            out.println("</body>");
            out.println("<div class='derecha' id='foot'> &copy;Axel Valladares </div>");
            out.println("</html");

        }catch (Exception e){
            e.printStackTrace();
        }
        return;
    }

    /**********************************************FALTA ALGO XML***************************************************************/

    public void FaltaContrasenaXML(HttpServletRequest req, HttpServletResponse res, String error){

        try{

            res.setCharacterEncoding("utf-8");
            res.setContentType("text/xml");

            PrintWriter out = res.getWriter();

            out.println("<?xml version='1.0' encoding='utf-8'?>");
            out.println("<wrongRequest>"+error+"</wrongRequest>");

        }catch (Exception e){
            e.printStackTrace();
        }
        return;
    }

    public void FaltaParametroXML(HttpServletRequest req, HttpServletResponse res, String parametro){

        try{

            res.setCharacterEncoding("utf-8");
            res.setContentType("text/xml");

            PrintWriter out = res.getWriter();

            out.println("<?xml version='1.0' encoding='utf-8'?>");
            out.println("<wrongRequest>no param:"+parametro+"</wrongRequest>");

        }catch (Exception e){
            e.printStackTrace();
        }
        return;
    }

    /*******************************CADENA DE DATOS DE LA PELICULA************************************/

    public String MovieString(Movie pelicula){

        String cadena = "";
        if(pelicula.idiomas!=""){
            cadena=cadena+"--- <b>Idiomas</b>= '" +pelicula.idiomas+ "'";
        }
        if(pelicula.duration!=""){
            cadena=cadena+"--- <b>Duration</b>= '" +pelicula.duration+ "'";
        }
        if(pelicula.genre!=null){
            cadena= cadena+"---<b>Generos</b>= '" +pelicula.devuelveArray()+ "'";
        }
        if(pelicula.sinopsis!=""){
            cadena=cadena+"--- <b>Sinopsis</b>= '"+pelicula.sinopsis+"'";
        }
        return cadena;

    }
}

