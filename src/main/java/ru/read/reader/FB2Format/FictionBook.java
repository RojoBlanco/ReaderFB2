package ru.read.reader.FB2Format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.read.reader.FB2Format.DescriptionBlock.Description;
import ru.read.reader.FB2Format.BinaryBlock.Binary;
import ru.read.reader.FB2Format.BodyBlock.Body;

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

    public String getCover(){
        var x=  binary.containsKey("cover");
        if(binary.containsKey("cover.jpg")) {
            return binary.get("cover.jpg");
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
