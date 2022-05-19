package p2;

public class Cast implements Comparable <Cast>{
 
    String name, role, contacto, id;

    public Cast(String name, String role, String contacto, String id){

        this.name=name;
        this.role=role;
        this.contacto=contacto;
        this.id=id;
    }

    public String getName(){
        return this.name;
    }

    public String getRole(){
        return this.role;
    }

    public String getContacto(){
        return this.contacto;
    }

    public String getId(){
        return this.id;
    }

    public void setName(String name){
        this.name=name;
        return;
    }

    public void setRole(String role){
        this.role=role;
        return;
    }

    public void setContacto(String contacto){
        this.contacto=contacto;
        return;
    }

    public void setId(String id){
        this.id=id;
        return;
    }

    @Override
    public int compareTo(Cast o) {

        if(o.getRole().equals(role)){

            for(int i=0; i<o.getId().length(); i++){
                if(o.getId().charAt(i)<id.charAt(i)){
                    return 0;
                }
                if(o.getId().charAt(i)>id.charAt(i)){
                    return -1;
                }
            }
        }
       
        if(o.getRole().charAt(0)<role.charAt(0)){
            return -1;
        }else if(o.getRole().charAt(0)>role.charAt(0)){
            return 0;
            }else if(o.getRole().charAt(0)==role.charAt(0)){
                if(o.getId().charAt(0)<id.charAt(0)){
                    return 0;
                }else{return -1;}
            }
            
            return 0;
    }        
    
}
