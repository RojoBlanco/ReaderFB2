package SaxHandler;

import FB2Format.DescriptionBlock.*;

import java.util.UUID; 

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class OpenHandler extends DefaultHandler {

	private TitleInfo titleInfo = new TitleInfo();
	private Boolean isTitleInfo = false;
	private Boolean isAuthor = false;
	private String lastAtribute;
	private Boolean inCharacter = false;
	private Author author;
	
	@Override
	public void characters(char[] ch, int start, int length) {
		if (inCharacter == true) {
			return;
		}
		else {inCharacter = true;}
		switch(lastAtribute) {
		case ("genre"):
			if(isTitleInfo)
				titleInfo.setGenres(new String(ch, start, length));
			break;
		case("first-name"):
			if(isAuthor) {
				author.setFirstName(new String(ch,start, length));
			}
			break;
		case("last-name"):
			if(isAuthor) {
				author.setLastName(new String(ch,start, length));
			}
		case("nickname"):
			if(isAuthor)
				author.setNickName(new String(ch,start,length));
			break;
		case("middle-name"):
			if(isAuthor)
				author.setMidleNamte(new String(ch,start,length));
			break;
		case("id"):
			if(isAuthor) 
				author.setUUID(UUID.fromString(new String(ch,start,length)));
		case("email"):
			if(isAuthor)
				author.setEmail(new String(ch,start,length));
		case("book-title"):
			if(isTitleInfo)
				titleInfo.setBookTitle(new String(ch,start,length));
		}
	}

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    	inCharacter = false;
    	lastAtribute = qName;
    	switch(qName) {
    	case("title-info"):
    		isTitleInfo = true;
    		break;

    	case("author"):
    		author = new Author();
    		isAuthor = true;
    		break;
    		
    	}
    		
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
		inCharacter = false;
		switch(qName) {
    	case("title-info"):
    		isTitleInfo = false;
    	case("author"):
    		isAuthor = false;
    		if(isTitleInfo) {
    			titleInfo.setAuthors(author);
    		}
    		break;
    	}
    }
}
