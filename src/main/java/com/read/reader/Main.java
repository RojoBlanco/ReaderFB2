package com.read.reader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Библиотека");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) throws SQLException, IOException
    {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\SQLite\\books.db");
            System.out.println("Подключилось");
            launch();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}