package ru.read.reader;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import ru.read.reader.FB2Format.DescriptionBlock.Description;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import org.xml.sax.SAXException;

import ru.read.reader.FB2Format.FictionBook;
import ru.read.reader.SaxHandler.OpenHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Main extends Application {
    public static FictionBook ficbook = new FictionBook();
    public static String folderPath = "C:\\temp";
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        /*
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        primaryStage.setTitle("Библиотека");
        primaryStage.setScene(scene);
        primaryStage.show();*/
        // Загружаем изображение
        Image image = new Image("C:\\temp\\Лавкрафт. Я – Провиденс. Книга 1\\cover.jpg");

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200); // Устанавливаем ширину
        imageView.setFitHeight(150); // Устанавливаем высоту

        // Создаем Label для отображения текста
        Label textLabel = new Label("Ваш текст здесь");

        // Создаем контейнер VBox и добавляем в него ImageView и Label
        VBox vbox = new VBox(imageView, textLabel);

        // Создаем сцену и устанавливаем на нее контейнер VBox
        Scene scene = new Scene(vbox, 400, 300);

        // Устанавливаем сцену на primaryStage и отображаем окно
        primaryStage.setScene(scene);
        primaryStage.setTitle("Изображение с текстом");
        primaryStage.show();

    }

    public Stage getPrimaryStage(){
        return this.primaryStage;
    }
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException
    {
        launch(args);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        OpenHandler handler = new OpenHandler();
        parser.parse(new File("C:\\Books\\TestBook1.fb2"), handler);
    }
}