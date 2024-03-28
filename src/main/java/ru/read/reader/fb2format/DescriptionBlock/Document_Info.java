package ru.read.reader.fb2format.DescriptionBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Document_Info {


	// Список авторов книги
	private List<Author> authors;
	// Программы использованные при написании книги
	private String programUsed;
	// Ссылка откуда взят оригинальный документ
	private String srcUrl;
	// Автор OCR или оригинального документа
	private String srcOcr;
	//Версия документа
	private float version;
	//Айди книги
	private UUID id;
	// Набор изменений документа
	private History history;
	private String date;

	public Document_Info(){
		authors = new ArrayList<>();
	}

	public void setAuthors(Author author) {this.authors.add(author);}
	public void setDate(String date) {this.date = date;}
	public void setProgramUsed(String programUsed) {this.programUsed = programUsed;}
	public void setSrcUrl(String srcUrl) {this.srcUrl=srcUrl;}
	public void setSrcOcr(String srcOcr) {this.srcOcr = srcOcr;}
	public void setVersion(float version) {this.version = version;}
	public void setUUID(UUID id) {this.id = id;}
	public void setHistory(History history) {this.history = history;}



	public List<Author> getAuthors() {return authors;}
	public String       getSrcUrl() {return srcUrl;}
	public String 	    getSrcOcr() {return srcOcr;}
	public float   getVersion() {return version;}
	public UUID getUUID() {return id;}
	public History getHistory() {return history;}
	
	
}
