package ru.read.reader.fb2format.BodyBlock;

import java.util.*;

public class Section {
    private static int idCount = 0;
    List<String> paragraphs;
    private final int id;

    Section(){
        id = idCount++;
        paragraphs = new ArrayList<>();
    }
    public void addParagraph(String paragraph){
        paragraphs.add(paragraph);
    }

    public List<String> getParagraphs() {
        return paragraphs;
    }
}
