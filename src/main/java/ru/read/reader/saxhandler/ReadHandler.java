package ru.read.reader.saxhandler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.read.reader.fb2format.BodyBlock.Body;
import ru.read.reader.fb2format.FictionBook;
import ru.read.reader.fb2format.Image;
import ru.read.reader.Main;
import ru.read.reader.fx.BookObject;

public class ReadHandler
        extends DefaultHandler {
    private FictionBook fictionBook;
    private String lastAtribute;
    StringBuilder bodyText = new StringBuilder();
    boolean isMainText;
    private Body body;
    private Image image;

    @Override
    public void startDocument() throws SAXException {
        fictionBook = Main.fictionBookList.get(BookObject.getNowBook());
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        lastAtribute = qName;
        if(lastAtribute.equals("body")){
            isMainText = true;
            body = new Body();

        }
    }

    @Override
    public void endDocument() throws SAXException {
        body.setTextBook(bodyText.toString());
        fictionBook.addBody(body);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
    if(qName.equals("body") && isMainText)
        isMainText = false;

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(isMainText) {
            bodyText.append("<" + lastAtribute +">" +new String(ch, start, length) + " </" + lastAtribute +">");

        }
    }


}
