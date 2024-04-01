package ru.read.reader.fb2format.DescriptionBlock;

import ru.read.reader.fb2format.Image;

import java.util.List;
import java.util.ArrayList;


public class TitleInfo {
	//Авторы
    private List<Author> authors;
    //Дата написания книги
    private String date;
    //Жанры
    private List<String> genres;
    //Название книги
    private String bookTitle;
    //Язык книги
    private String lang;
    //Анотация книги
    private Annotation annotation;
    //Ключевые слова
    private List<String> keywords;
    //Обложка книги
    private Image coverpage;
    //Переводчики
    private List<Author> translators;
    //Язык изначальной книги (Для переводов)
    private String srcLang;

    //Серия изданий, в которое входит книга и номер в серии
    private List<Sequence> sequence;
    
    public TitleInfo(){
    	this.genres = new ArrayList<String>();
    	this.authors = new ArrayList<Author>();
    	this.translators = new ArrayList<Author>();
    	this.sequence = new ArrayList<Sequence>();
    	this.keywords = new ArrayList<String>();
        annotation = new Annotation();
    }
    //Сеттеры
    public void setGenres(String genres) {this.genres.add(genres);}
    public void setDate(String date){this.date = date;}
    public void setAuthors(Author author) {this.authors.add(author);}
    public void setBookTitle(String bookTitle) {this.bookTitle = bookTitle;}
    public void setLang(String lang) {this.lang=lang;}
    public void setAnnotation(Annotation annotation) {this.annotation = annotation;}
    public void setKeyword(String keyword) {keywords.add(keyword);}
    public void setTranslator(Author translator) {this.translators.add(translator);}
    public void setSrcLang(String srcLang) {this.srcLang = srcLang;}
    public void setSequence(Sequence sequence) {this.sequence.add(sequence);}
    public void setCoverPage(String coverpage) {
    	this.coverpage = new Image();
    	this.coverpage.setImage(coverpage);
    }
    
    //Гетерры
    public List<String> getGenres() {return genres;}
    public List<Author> getAuthors() {return authors;}
    public String       getBookTitle() {return bookTitle;}
    public String 	    getLang() {return lang;}
    public Annotation   getAnnotation() {return annotation;}
    public List<String> getKeyword() {return keywords;}
    public List<Author> getTranslator() {return translators;}
    public String       getSrcLang() {return srcLang;}
    public List<Sequence> getSequence() {return sequence;}
    public Image getCoverpage() {return coverpage;}
    public String getDate(){return date;}
}

