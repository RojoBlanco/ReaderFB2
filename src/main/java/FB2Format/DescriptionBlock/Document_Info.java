package FB2Format.DescriptionBlock;

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
	
	
	
}
