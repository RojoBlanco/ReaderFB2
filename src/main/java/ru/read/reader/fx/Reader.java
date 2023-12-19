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
import ru.read.reader.FB2Format.BodyBlock.Body;
import ru.read.reader.Main;
import ru.read.reader.SaxHandler.ReadHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class Reader {
    private Stage primaryStage;
    public void display(String filePath) throws IOException, ParserConfigurationException, SAXException {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("FB2 Reader");

        WebView webView = new WebView();
        webView.setPrefSize(800, 800);

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        ReadHandler handler = new ReadHandler();
        parser.parse(new File(filePath), handler);
        primaryStage.setOnCloseRequest(event -> {
                Main.showStage();
                BookObject.showPopoutStage(); // Показываем предыдущее окно при закрытии

        });


        // Создание ComboBox для выбора размера шрифта
        ComboBox<String> fontSizeComboBox = new ComboBox<>(FXCollections.observableArrayList("12px", "16px", "20px", "24px"));
        fontSizeComboBox.setValue("16px"); // Установка размера шрифта по умолчанию
        fontSizeComboBox.setPrefSize(15, 15);

        // Обработчик изменения размера шрифта
        fontSizeComboBox.setOnAction(event -> {
            String selectedFontSize = fontSizeComboBox.getValue();
            applyFontSize(webView, selectedFontSize);
        });


        String htmlContent = "<html>" + Main.fictionBookList.get(BookObject.getNowBook()).getListBody().get(0).getTextBook() + "</html>";

        webView.getEngine().loadContent(htmlContent);

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
