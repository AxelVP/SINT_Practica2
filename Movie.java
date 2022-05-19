package p2;

import java.util.ArrayList;

public class Movie implements Comparable <Movie> {

    String title, duration, sinopsis, idiomas;
    ArrayList<String> genre;

    public Movie(String title, ArrayList<String> genre, String duration, String sinopsis, String idiomas){

        this.title=title;
        this.genre=genre;
        this.duration=duration;
        this.sinopsis=sinopsis;
        this.idiomas=idiomas;
    }
    
    public String getTitle(){
        return this.title;
    }

    public ArrayList<String> getGenre(){
        return this.genre;
    }

    public String getDuration(){
        return this.duration;
    }

    public String getSinopsis(){
        return this.sinopsis;
    }

    public String getIdiomas(){
        return this.idiomas;
    }

    public void setTitle(String title ){
        this.title=title;
        return;
    }

    public void setDuration(String duration){
        this.duration=duration;
        return;
    }

    public void setGenre(ArrayList<String> genre){
        this.genre=genre;
        return;
    }

    public void setSinopsis(String sinopsis){
        this.sinopsis=sinopsis;
        return;
    }

    public void setIdiomas(String idiomas){
        this.idiomas=idiomas;
        return;
    }

    public String devuelveArray(){

        String retorna="";

        for(String generos: genre){

            retorna = retorna+generos+",";
        }
        retorna=retorna.substring(0, retorna.length()-1);
        return retorna;
    }

    public int compareTo(Movie o){ 
        
        if(o.getGenre().size()> genre.size()){
            return 0;
        }else if(o.getGenre().size()==genre.size()){

            for(int i=0; i<o.getTitle().length(); i++){
                if(o.getTitle().charAt(i)<title.charAt(i)){
                    return 0;
                }
                if(o.getTitle().charAt(i)>title.charAt(i)){ 
                    return -1;
                }
                
            }
        }else{return -1;}
        return 0;
        
    }

}
