package com.read.reader;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ReaderDB {
    private static String PathDB = "jdbc:sqlite:D:\\SQLite\\books.db";
    private static boolean readyConnect = false;
    private static Connection connection;
    public static void Connect(){
        if (readyConnect == false){
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(PathDB);
                System.out.println("Подключилось");
                readyConnect = true;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        else {
            System.out.println("База данных уже подключена");
        }
    }
    public static void Add(Book book){


    }
    public static void Close() throws SQLException {
        connection.close();
    }
}
