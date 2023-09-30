package SaxHandler;

import FB2Format.DescriptionBlock.*;

import java.util.UUID; 

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.read.reader.Main;
public class OpenHandler extends DefaultHandler {
	private TitleInfo titleInfo;
	private Document_Info documentInfo;
	private Boolean isTitleInfo = false;
	private Boolean isAuthor = false;
	private Boolean isDocumentInfo = false;
	private String lastAtribute;
	private Author author;
	
	@Override
	public void characters(char[] ch, int start, int length) {

		switch(lastAtribute) {
			case ("genre"):
				if (isTitleInfo)
					titleInfo.setGenres(new String(ch, start, length));
				break;
			case ("first-name"):
				if (isAuthor) {
					author.setFirstName(new String(ch, start, length));
				}
				break;
			case ("last-name"):
				if (isAuthor) {
					author.setLastName(new String(ch, start, length));
				}
				break;
			case ("nickname"):
				if (isAuthor)
					author.setNickName(new String(ch, start, length));
				break;
			case ("middle-name"):
				if (isAuthor)
					author.setMidleNamte(new String(ch, start, length));
				break;
			case ("id"):
				if (isAuthor)
					author.setUUID(UUID.fromString(new String(ch, start, length)));
				break;
			case ("email"):
				if (isAuthor)
					author.setEmail(new String(ch, start, length));
				break;
			case ("book-title"):
				if (isTitleInfo)
					titleInfo.setBookTitle(new String(ch, start, length));
				break;
			case("program-used"):
				documentInfo.setProgramUsed(new String(ch,start,length));
			case("date"):
				if(isTitleInfo)
					titleInfo.setDate(new String(ch, start, length));
				if(isDocumentInfo)
					documentInfo.setDate(new String(ch,start, length));
		}
	}

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    	lastAtribute = qName;
    	switch(qName) {
			case("document-info"):
				documentInfo = new Document_Info();
				isDocumentInfo = true;
				break;
    		case("title-info"):
			 	titleInfo = new TitleInfo();
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
		lastAtribute = "";
		switch(qName) {
    	case("title-info"):
    		isTitleInfo = false;
			Main.description.setTitleInfo(titleInfo);
    	case("author"):
    		isAuthor = false;
    		if(isTitleInfo)
    			titleInfo.setAuthors(author);
			else if(isDocumentInfo)
				documentInfo.setAuthors(author);
    		break;
    	}
    }
}
