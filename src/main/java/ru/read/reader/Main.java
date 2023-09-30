package ru.read.reader;

import FB2Format.DescriptionBlock.Description;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import org.xml.sax.SAXException;

import SaxHandler.OpenHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Main extends Application {
    public static Description description = new Description();
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        primaryStage.setTitle("Библиотека");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public Stage getPrimaryStage(){
        return this.primaryStage;
    }
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        OpenHandler handler = new OpenHandler();
        parser.parse(new File("C:\\Books\\TestBook.fb2"), handler);

    }
}