package com.read.reader;

import FB2Format.Book;
import FB2Format.Book_Info;
import javafx.application.Application;

import javafx.scene.Scene;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



public class Main extends Application {
    private static Book book;
    private Stage primaryStage;
    private ArrayList<Book_Info> descriptions;
    @Override
    public void start(Stage primaryStage) throws IOException {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        ReadHandler handler = new ReadHandler();
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

        Pane pane = new Pane();
        Scene scene = new Scene(pane, 400,400);

        Text newtxt = new Text("helli");
        newtxt.setX(10);
        newtxt.setY(10);
        pane.getChildren().add(newtxt);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ридер");

        primaryStage.show();

    }

    public Stage getPrimaryStage(){
        return this.primaryStage;
    }
    public static void main(String[] args) throws IOException
    {
        launch(args);
    }

    public static void setBook(Book newbook){
        book = newbook;
    }
}