package com.read.reader;

import FB2Format.Author;
import FB2Format.Book_Info;
import FB2Format.Genre;
import FB2Format.Title_info;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class OpenHandler extends DefaultHandler {
    Book_Info book;
    Title_info titleInfo;
    String bookTitle;
    String date;
    ArrayList<Genre> genres;
    ArrayList<Author> authors;
    String cover;
    String currentElement;

    @Override
    public void startDocument() throws SAXException {
        book = new Book_Info();
        titleInfo = new Title_info();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = qName;
        switch (currentElement)
        {
            case "binary": System.out.println(attributes.getValue("id"));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (currentElement){

        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {


    }
}
