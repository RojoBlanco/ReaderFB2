package ru.read.reader.FB2Format;

public class Image {
    private String id;
    private String format;

    public Image(){}

    public void setImage(String id){
    	StringBuilder newId = new StringBuilder(id); 
    	newId.deleteCharAt(0);
    	this.id = newId.toString();
        this.format =  id.substring(id.lastIndexOf('.')+1);
    }
    public String getImage(){
        return id;
    }
    public String getFormat(){
        return format;
    }
}
