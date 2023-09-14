package FB2Format.DescriptionBlock;

import java.util.List;

import FB2Format.Image;

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
    private List<Author> translator;
    //Язык изначальной книги (Для переводов)
    private String srcLang;
    //Серия изданий, в которое входит книга и номер в серии
    private List<Sequence> sequence;
}
