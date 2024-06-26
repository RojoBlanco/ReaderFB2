package ru.read.reader.fx;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import ru.read.reader.fb2format.BodyBlock.Body;
import ru.read.reader.Main;
import ru.read.reader.saxhandler.OpenHandler;
import ru.read.reader.saxhandler.ReadHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class Reader {
    private Stage primaryStage;
    public static Body body;
    public void display(String filePath) throws IOException, ParserConfigurationException, SAXException {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("FB2 Reader");

        WebView webView = new WebView();
        webView.setPrefSize(800, 800);

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        primaryStage.setOnCloseRequest(event -> {
                Main.showStage();
                BookObject.showPopoutStage(); // Показываем предыдущее окно при закрытии

         });
        ReadHandler handler = new ReadHandler();
        parser.parse("file:"+Main.fictionBookList.get(BookObject.getNowBook()).getPath(),handler);


         // Создание ComboBox для выбора размера шрифта
         ComboBox<String> fontSizeComboBox = new ComboBox<>(FXCollections.observableArrayList("12px", "16px", "20px", "24px"));
         fontSizeComboBox.setValue("16px"); // Установка размера шрифта по умолчанию
         fontSizeComboBox.setPrefSize(15, 15);

         // Обработчик изменения размера шрифта
         fontSizeComboBox.setOnAction(event -> {
             String selectedFontSize = fontSizeComboBox.getValue();
             applyFontSize(webView, selectedFontSize);
         });

         BorderPane root = new BorderPane();
         root.setTop(fontSizeComboBox); // Устанавливаем ComboBox в верхнюю часть макета
         root.setCenter(webView); // Устанавливаем WebView в центр макета
         primaryStage.setScene(new Scene(root, 800, 800));
         primaryStage.show();
    }

    private void applyFontSize(WebView webView, String fontSize) {
        WebEngine webEngine = webView.getEngine();
        String customStylesheet = "body { font-size: " + fontSize + "; }";
        webEngine.setUserStyleSheetLocation("data:text/css," + customStylesheet);
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
