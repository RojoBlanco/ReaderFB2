package ru.read.reader.database;

import ru.read.reader.Main;
import ru.read.reader.fb2format.DescriptionBlock.Annotation;
import ru.read.reader.fb2format.DescriptionBlock.Author;
import ru.read.reader.fb2format.FictionBook;

import javax.xml.transform.Result;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final String DB_URL;

    public Database(String path) {
        File correctPath = new File(path, "database.db");
        this.DB_URL = "jdbc:sqlite:" + correctPath;
    }

    private void createNewTables(Connection conn) {
        String sqlAuthor =
                "CREATE TABLE IF NOT EXISTS Author (\n"
                        + "    Name TEXT NOT NULL,\n"
                        + "    MiddleName TEXT NOT NULL,\n"
                        + "    LastName TEXT NOT NULL,\n"
                        + "    Nickname TEXT,\n"
                        + "    uuid TEXT PRIMARY KEY NOT NULL UNIQUE,\n"
                        + "    email TEXT\n"
                        + ");";

        String sqlBooks = "CREATE TABLE IF NOT EXISTS Books (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    Title TEX NOT NULL,\n"
                + "    Path TEXT NOT NULL,\n"
                + "    Date_Written TEXT NOT NULL,\n"
                + "    Language TEXT NOT NULL,\n"
                + "    Annotation TEXT NOT NULL,\n"
                + "    FOREIGN KEY (id) REFERENCES Author(id)\n"
                + ");";

        String sqlImages = "CREATE TABLE IF NOT EXISTS Images (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    Title INTEGER NOT NULL,\n"
                + "    Path INTEGER NOT NULL,\n"
                + "    FOREIGN KEY (id) REFERENCES Books(id)\n"
                + ");";

        String sqlGenres = "CREATE TABLE IF NOT EXISTS Genres (\n"
                + "    Genre TEXT PRIMARY KEY\n"
                + ");";

        String sqlSeries = "CREATE TABLE IF NOT EXISTS Series (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    Series_Number INTEGER,\n"
                + "    Series_Title TEXT NOT NULL\n"
                + ");";

        String sqlSettings = "CREATE TABLE IF NOT EXISTS Settings (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    Font TEXT NOT NULL,\n"
                + "    Font_Size INTEGER NOT NULL,\n"
                + "    Background_Color VARCHAR(7) NOT NULL,\n"
                + "    Font_Color VARCHAR(7) NOT NULL,\n"
                + "    FOREIGN KEY (id) REFERENCES Books(id)\n"
                + ");";

        String sqlNotes = "CREATE TABLE IF NOT EXISTS Notes (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    Color VARCHAR(7) NOT NULL,\n"
                + "    Note TEXT NOT NULL,\n"
                + "    Book_Id INTEGER NOT NULL,\n"
                + "    Page_Number INTEGER NOT NULL\n"
                + ");";

        String sqlBookGenres = "CREATE TABLE IF NOT EXISTS Book_Genres (\n"
                + "    Book_Id INTEGER NOT NULL,\n"
                + "    Genre_Id TEXT NOT NULL,\n"
                + "    PRIMARY KEY (Book_Id, Genre_Id),\n"
                + "    FOREIGN KEY (Book_Id) REFERENCES Books(id),\n"
                + "    FOREIGN KEY (Genre_Id) REFERENCES Genres(id)\n"
                + ");";

        String sqlBookSeries = "CREATE TABLE IF NOT EXISTS Book_Series (\n"
                + "    Book_Id INTEGER NOT NULL,\n"
                + "    Series_Id INTEGER NOT NULL,\n"
                + "    PRIMARY KEY (Book_Id, Series_Id),\n"
                + "    FOREIGN KEY (Book_Id) REFERENCES Books(id),\n"
                + "    FOREIGN KEY (Series_Id) REFERENCES Series(id)\n"
                + ");";

        String sqlBookAuthor = "CREATE TABLE IF NOT EXISTS Book_Author (\n"
                + "    Book_Id INTEGER NOT NULL,\n"
                + "    Author_Id String NOT NULL,\n"
                + "    PRIMARY KEY (Book_Id, Author_Id),\n"
                + "    FOREIGN KEY (Book_Id) REFERENCES Books(id),\n"
                + "    FOREIGN KEY (Author_Id) REFERENCES Author(id)\n"
                + ");";

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(sqlAuthor);
            System.out.println("Таблица Author создана");

// Создание таблицы Books
            stmt.execute(sqlBooks);
            System.out.println("Таблица Books создана");

// Создание таблицы Images
            stmt.execute(sqlImages);
            System.out.println("Таблица Images создана");

// Создание таблицы Genres
            stmt.execute(sqlGenres);
            System.out.println("Таблица Genres создана");

// Создание таблицы Series
            stmt.execute(sqlSeries);
            System.out.println("Таблица Series создана");

// Создание таблицы Settings
            stmt.execute(sqlSettings);
            System.out.println("Таблица Settings создана");

// Создание таблицы Notes
            stmt.execute(sqlNotes);
            System.out.println("Таблица Notes создана");

// Создание таблицы Book_Genres
            stmt.execute(sqlBookGenres);
            System.out.println("Таблица Book_Genres создана");

// Создание таблицы Book_Series
            stmt.execute(sqlBookSeries);
            System.out.println("Таблица Book_Series создана");

// Создание таблицы Book_Author
            stmt.execute(sqlBookAuthor);
            System.out.println("Таблица Book_Author создана");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addNewBook(FictionBook ficbook) {
        String sqlBookAdd = "INSERT INTO Books (Path,Title ,Date_Written, Language, Annotation)   VALUES(?,?,?,?,?)";
        String sqlImageAdd = "INSERT INTO Images (Path, Title)   VALUES(?,?)";
        String sqlAuthorsAdd = "INSERT INTO Author (Name, MiddleName, LastName, Nickname , uuid)   VALUES(?,?,?,?,?)";
        String sqlGenreAdd = "INSERT INTO Genres (Genre)   VALUES(?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmtBook = conn.prepareStatement(sqlBookAdd);
             PreparedStatement pstmtImage = conn.prepareStatement(sqlImageAdd);
             PreparedStatement pstmtAuthor = conn.prepareStatement(sqlAuthorsAdd);
             PreparedStatement pstmtGenre = conn.prepareStatement(sqlAuthorsAdd);) {
            if (conn != null) {
                insertBook(ficbook, pstmtBook);
                insertImage(ficbook, pstmtImage);
                insertAuthor(ficbook, pstmtAuthor, conn);
                insertGenre(ficbook, pstmtGenre, conn);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void insertBook(FictionBook fictionBook, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, fictionBook.getPath());
        pstmt.setString(2, fictionBook.getDesc().getTitleInfo().getBookTitle());
        pstmt.setString(3, fictionBook.getDescription().getTitleInfo().getDate());
        pstmt.setString(4, fictionBook.getDescription().getTitleInfo().getLang());
        pstmt.setString(5, fictionBook.getDescription().getTitleInfo().getAnnotation().toString());
        pstmt.executeUpdate();
    }

    private void insertImage(FictionBook fictionBook, PreparedStatement pstmt) throws SQLException {
        for (String s : fictionBook.getListBinary().keySet()) {
            pstmt.setString(1, fictionBook.getListBinary().get(s));
            pstmt.setString(2, s);
            pstmt.executeUpdate();
        }
    }

    private void insertAuthor(FictionBook fictionBook, PreparedStatement pstmt, Connection conn) throws SQLException {
        for (Author author : fictionBook.getDescription().getTitleInfo().getAuthors()) {
            String sqlSelectAuthor = "SELECT uuid FROM Author WHERE uuid = '" + author.getId().toString() + "'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelectAuthor);
            if (!resultSet.next()) {
                pstmt.setString(1, author.getFirstName());
                pstmt.setString(2, author.getMidleName());
                pstmt.setString(3, author.getLastName());
                pstmt.setString(4, author.getNickName());
                pstmt.setString(5, author.getId().toString());
                pstmt.executeUpdate();
            }
            PreparedStatement pstmtAuthor = conn.prepareStatement("INSERT INTO Book_Author (Book_Id, Author_Id)   VALUES(?,?)");
            pstmtAuthor.setInt(1, fictionBook.getIndex());
            pstmtAuthor.setString(2, author.getId().toString());
            pstmtAuthor.executeUpdate();
        }
    }
    private void insertGenre(FictionBook fictionBook, PreparedStatement pstmt, Connection conn) throws SQLException {

        for (String genre : fictionBook.getDescription().getTitleInfo().getGenres()) {
            String sqlSelectAuthor = "SELECT Genre FROM Genres where Genre = '" + genre + "'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelectAuthor);
            if (!resultSet.next()) {
                pstmt.setString(1, genre);
                pstmt.executeUpdate();
            }
            PreparedStatement pstmtAuthor = conn.prepareStatement("INSERT INTO Book_Genres (Book_Id, Genre_Id)   VALUES(?,?)");
            pstmtAuthor.setInt(1, fictionBook.getIndex());
            pstmtAuthor.setString(2, genre);
            pstmtAuthor.executeUpdate();
        }
    }


    public List<FictionBook> readAllBook() {
        String sqlSelectAllBook = "SELECT * FROM Books";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement pstmtBook = conn.createStatement();
             Statement pstmtAuthors = conn.createStatement()) {
            if (conn != null) {
                ResultSet resultSet = pstmtBook.executeQuery(sqlSelectAllBook);
                while (resultSet.next()){
                    FictionBook fictionBook = new FictionBook();
                    int idBook = resultSet.getInt("id");
                    String sqlSelectAuthorsBook = "SELECT a.Name, a.MiddleName, a.LastName, a.Nickname, a.uuid, a.email\n" +
                            "FROM Author a\n" +
                            "JOIN Book_Author ba ON a.id = ba.Author_Id\n" +
                            "JOIN Books b ON b.id = ba.Book_Id\n" +
                            "WHERE b.id = " + idBook ;
                    ResultSet resultSetAuthor = pstmtBook.executeQuery(sqlSelectAuthorsBook);
                    while (resultSet.next()){

                    }
                    fictionBook.setIndex(resultSet.getInt("id"));
                    fictionBook.getDescription().getTitleInfo().setLang(resultSet.getString("Language"));
                    fictionBook.getDescription().getTitleInfo().setBookTitle(resultSet.getString("Title"));
                    fictionBook.getDescription().getTitleInfo().setAnnotation(new Annotation(resultSet.getString("Annotation")));
                    fictionBook.getDescription().getTitleInfo().setDate(resultSet.getString("Date_Written"));
                    fictionBook.setPath(resultSet.getString("Path"));


                    Main.fictionBookList.add(fictionBook);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void createDataBase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                DatabaseMetaData metaData = conn.getMetaData();
                System.out.println("Драйвер с именем " + metaData.getDriverName());
                System.out.println("Новая база данных создана");
                createNewTables(conn);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}