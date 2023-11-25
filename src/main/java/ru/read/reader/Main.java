package ru.read.reader;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ru.read.reader.FB2Format.DescriptionBlock.Description;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

import org.xml.sax.SAXException;

import ru.read.reader.FB2Format.FictionBook;
import ru.read.reader.SaxHandler.OpenHandler;
import ru.read.reader.fx.BookObject;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Main extends Application {
    public static List<FictionBook> fictionBookList = new ArrayList<>();

    public static String folderPath = "C:\\temp\\";
    private Stage primaryStage;
    private FlowPane flowPane;

    @Override
    public void start(Stage primaryStage) throws IOException, ParserConfigurationException, SAXException  {
        flowPane = new FlowPane();
        flowPane.setHgap(10); // Устанавливаем горизонтальный отступ между элементами
        flowPane.setVgap(10); // Устанавливаем вертикальный отступ между элементами
        addNewBook("C:\\Books\\Test.fb2");
        addNewBook("C:\\Books\\TestBook1.fb2");

        Scene scene = new Scene(flowPane, 300, 600);

        // Устанавливаем сцену на primaryStage и отображаем окно
        primaryStage.setScene(scene);
        primaryStage.setTitle("Изображения с текстом");
        primaryStage.show();
        }


    private void addNewBook(String path) throws IOException, ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        OpenHandler handler = new OpenHandler();
        parser.parse(new File(path), handler);
        flowPane.getChildren().add(new BookObject(fictionBookList.get(fictionBookList.size() - 1 ).getName(),
                fictionBookList.get(fictionBookList.size() - 1 ).getCover()));
    }
    public Stage getPrimaryStage(){
        return this.primaryStage;
    }
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException
    {
        launch(args);

    }
}