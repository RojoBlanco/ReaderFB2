package ru.read.reader.fb2format;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

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
    public static void fromBase64ToImage(File pathToFile, StringBuilder image){
        byte[] decodedBytes = Base64.getDecoder().decode(image.toString());
        try{
            Files.write(Paths.get(pathToFile.toString()), decodedBytes);
        }
        catch (IOException ignored){
            System.out.println(ignored.getStackTrace());
        }

    }
}
