package ru.read.reader.SaxHandler;

import ru.read.reader.FB2Format.DescriptionBlock.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.read.reader.FB2Format.FictionBook;
import ru.read.reader.Main;
public class OpenHandler
		extends DefaultHandler {
	private Description description;
	private File directory;
	private FictionBook ficbook;
	private TitleInfo titleInfo;
	private StringBuilder imgBase64 = new StringBuilder();
	private Document_Info documentInfo;
	private boolean isTitleInfo , isAnnatation , isCoverPage, isDocumentInfo , isAuthor  ;
	private String lastAtribute, binaryId;
	private Author author;
	
	@Override
	public void characters(char[] ch, int start, int length) {
		switch(lastAtribute) {
			case ("p"):
				if(isAnnatation){
					titleInfo.getAnnotation().setP(new String(ch, start, length));
				}
				break;
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
				break;
			case("binary"):
				if (isCoverPage){
					String imgBase64low = new String(ch, start,length);
					
					imgBase64.append(imgBase64low);
					
				}
				break;
		}
	}

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    	lastAtribute = qName;
    	switch(qName) {
			case ("annotation"):
				isAnnatation = true;
				break;
			case ("FictionBook"):
				ficbook = new FictionBook();
				break;
			case ("description"):
				description = new Description();
				break;
			case("binary"): 
					binaryId = attributes.getValue("id");
					imgBase64 = new StringBuilder();
					isCoverPage  = true;		
				break;
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
    		case("image"):
    			if(isTitleInfo) {
    				titleInfo.setCoverPage(attributes.getValue("l:href"));
    			}
			break;
    	}
    		
    }
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
		lastAtribute = "";
		switch(qName) {
			case ("annotation"):
				isAnnatation = false;
				break;
			case("binary"):
				if (isCoverPage){
					File pathToImage = new File(directory,binaryId);
					byte[] decodedBytes = Base64.getDecoder().decode(imgBase64.toString());
					try{
						Files.write(Paths.get(pathToImage.toString()), decodedBytes);
					}
					catch (IOException e){
					}
					ficbook.setBinary(binaryId, pathToImage.toString());
					isCoverPage = false;
				}
				break;
			case("document-info"):
				description.setDocumentInfo(documentInfo);
				break;
			case("description"):
				ficbook.setDesc(description);
				break;
    		case("title-info"):
    			isTitleInfo = false;
				description.setTitleInfo(titleInfo);
				directory = new File(Main.configDirectory,  description.getTitleInfo().getBookTitle());
				directory.mkdir();
				break;
    		case("author"):
				isAuthor = false;
				if(isTitleInfo)
					titleInfo.setAuthors(author);
				else if(isDocumentInfo)
					documentInfo.setAuthors(author);
				break;
			case ("FictionBook"):
				Main.fictionBookList.add(ficbook);
				break;
    	}
    }
}