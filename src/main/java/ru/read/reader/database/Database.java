package ru.read.reader.database;

import java.sql.*;

public class Database {
    private final String DB_URL;

    public Database(String path){
        this.DB_URL = "jdbc:sqlite:" + path + "\\database.db";
    }
    private void createNewTables(Connection conn) {
        // Ваш код создания таблицы Book
        String sqlBook = "CREATE TABLE IF NOT EXISTS Book (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    path_to_book TEXT NOT NULL,\n"
                + "    path_to_cover BLOB NOT NULL,\n"
                + "    id_setting INTEGER NOT NULL,\n"
                + "    lastPageNumber INTEGER,\n"
                + "    FOREIGN KEY(id_setting) REFERENCES settings(id)\n"
                + ");";

        // Ваш код создания таблицы Annotation
        String sqlAnnotation = "CREATE TABLE IF NOT EXISTS Annotation (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    color INTEGER,\n"
                + "    annotation_text TEXT,\n"
                + "    id_book INTEGER,\n"
                + "    word TEXT,\n"
                + "    FOREIGN KEY(id_book) REFERENCES Book(id)\n"
                + ");";

        // Ваш код создания таблицы settings
        String sqlSettings = "CREATE TABLE IF NOT EXISTS settings (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    font TEXT,\n"
                + "    font_size INTEGER,\n"
                + "    color_background TEXT,\n"
                + "    font_color TEXT\n"
                + ");";

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            // Создание таблицы Book
            stmt.execute(sqlBook);
            System.out.println("Таблица Book создана");

            // Создание таблицы Annotation
            stmt.execute(sqlAnnotation);
            System.out.println("Таблица Annotation создана");

            // Создание таблицы settings
            stmt.execute(sqlSettings);
            System.out.println("Таблица settings создана");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void createDataBase(){
        try (Connection conn = DriverManager.getConnection(DB_URL)){
            if (conn != null){
                DatabaseMetaData metaData= conn.getMetaData();
                System.out.println("Драйвер с именем " + metaData.getDriverName());
                System.out.println("Новая база данных создана");
                createNewTables(conn);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}