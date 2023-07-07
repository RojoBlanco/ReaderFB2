package com.read.reader;

import FB2Format.Body;
import FB2Format.Book;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class ReadHandler extends DefaultHandler {
    private Book book;
    private Body mainBody, notesBody, commentsBody;
    private ArrayList<Body> bodyArrayList = new ArrayList<Body>();
    public String currentTag;
    private boolean isBody   = false;
    private boolean isBinary = false;
    @Override
    public void startDocument() throws SAXException {
        book = new Book();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentTag = qName;
        if (qName.equals("body")){
            isBody = true;

        }
        else if (qName.equals("Binary")){
            isBinary = true;
        }
        currentTag = qName;
        if ("body".equals(qName)){
            if (attributes.getLength()==0){
                mainBody = new Body();
                bodyArrayList.add(mainBody);

            } else if (attributes.getValue("name").equals("notes")) {
                notesBody = new Body();


            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("body")){
            isBody = false;
        }
        else if (qName.equals("Binary")){
            isBinary = false;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        Main.setBook(book);
    }
}
