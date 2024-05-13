package ru.read.reader.fb2format.DescriptionBlock;

import java.util.ArrayList;
import java.util.List;


public class Annotation {
    private List<String> p;
    public Annotation(){
        p = new ArrayList<>();
    }
    public Annotation(String s){
        p = new ArrayList<>();
        p.add(s);
    }

    public List<String> getP() {
        return p;
    }

    public void setP(String p) {
        this.p.add(p);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(String p1 : p){
           sb.append(p1);
        }
        String result = sb.toString();
        return result;
    }
}
