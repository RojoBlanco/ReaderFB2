package com.read.reader;

import FB2Format.Book_Info;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Main extends Application {
    private Stage primaryStage;
    private ArrayList<Book_Info> descriptions;
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
    public static void main(String[] args) throws IOException
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        OpenHandler handler = new OpenHandler();
        SAXParser parser = null;

        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }


        File book = new File("C:/Books/Test.fb2");

        try {
            parser.parse(book, handler);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        launch(args);
    }
}