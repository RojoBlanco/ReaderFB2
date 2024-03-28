package ru.read.reader.fb2format.DescriptionBlock;

import javafx.beans.binding.StringBinding;

import java.util.ArrayList;
import java.util.List;


public class Annotation {
    private StringBuilder result = new StringBuilder();
    private List<String> p;
    public Annotation(){
        p = new ArrayList<>();
    }

    public List<String> getP() {
        return p;
    }

    public void setP(String p) {
        this.p.add(p);
    }

    @Override
    public String toString() {
        for(String p1 : p){
            result.append(p1 + "\n");
        }
        return result.toString();
    }
}
