package ru.read.reader.fb2format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.read.reader.fb2format.DescriptionBlock.Description;
import ru.read.reader.fb2format.BodyBlock.Body;

public class FictionBook {
    private String path;
    private Description desc;
    private List<Body> listBody;
    private Map<String, String> binary;

    public FictionBook(){
        this.binary = new HashMap<>();
        this.listBody = new ArrayList<>();
    }

    public void setDesc(Description desc){this.desc = desc;}
    public Description getDesc() {
        return desc;
    }

    public Map<String, String> getListBinary() {
        return binary;
    }

    public List<Body> getListBody() {
        return listBody;
    }
    public void setBinary(String id, String path) {
        binary.put(id, path);
    }
    public void addBody(Body body){
        listBody.add(body);
    }

    public Description getDescription(){
        return desc;
    }
    public String getCoverPath(){
        if(binary.containsKey("cover.jpg")) {
            return binary.get("cover.jpg");
        }
        else return "error";
    }
    public String getCoverName(){
        if(binary.containsKey("cover.jpg")) {
            return "cover.jpg";
        }
        else return "error";
    }
    public String getName(){
       return desc.getTitleInfo().getBookTitle();
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
