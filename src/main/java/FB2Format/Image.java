package FB2Format;

public class Image {
    private String id;

    public Image(){}

    public void setImage(String id){
    	StringBuilder newId = new StringBuilder(id); 
    	newId.deleteCharAt(0);
    	this.id = newId.toString();
    	
    }
    public String getImage(){
        return id;
    }
}
