package ru.read.reader;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;

import ru.read.reader.database.Database;
import ru.read.reader.fb2format.FictionBook;
import ru.read.reader.saxhandler.OpenHandler;
import ru.read.reader.fx.BookObject;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Main extends Application {
    public static List<FictionBook> fictionBookList = new ArrayList<>();

    private String selectedFilePath;
    public static File folderPath, configDirectory, tempDirectory, dbDirectory;

    private static Stage primarStage;
    private FlowPane flowPane;

    @Override
    public void start(Stage primaryStage) throws URISyntaxException {
        createMainDirs();
        primarStage = primaryStage;
        flowPane = new FlowPane();
        flowPane.setHgap(10); // Устанавливаем горизонтальный отступ между элементами
        flowPane.setVgap(10); // Устанавливаем вертикальный отступ между элементами
        flowPane.setPrefSize(1000, 900);

        Button chooseFileButton = new Button("Выбрать файл");
        chooseFileButton.setOnAction(e -> {
            try {
                chooseFile();
            } catch (IOException | ParserConfigurationException | SAXException ex) {
                throw new RuntimeException(ex);
            }
        });


        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(flowPane);

        // Создаем отступ для кнопки
        javafx.geometry.Insets buttonMargin = new javafx.geometry.Insets(0, 0, 10, 0);

        // Помещаем кнопку внизу окна с отступом от низа и центрируем ее
        BorderPane.setAlignment(chooseFileButton, javafx.geometry.Pos.BOTTOM_CENTER);
        BorderPane.setMargin(chooseFileButton, buttonMargin);
        borderPane.setBottom(chooseFileButton);

        Scene scene = new Scene(borderPane, 1280, 920);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Библиотека");
        primaryStage.show();
    }
    private void createMainDirs(){
        folderPath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile();
        configDirectory = new File(folderPath, "User");
        dbDirectory = new File(configDirectory, "db");
        tempDirectory = new File(configDirectory, "temp");
        System.out.println(folderPath.getPath());
        System.out.println("Папка уже создана" + configDirectory.getPath()+ " " + !configDirectory.mkdir());


        boolean haveDb = dbDirectory.mkdir();
        Database db = new Database(dbDirectory.getPath());
        System.out.println("Папка уже создана" + dbDirectory.getPath() + " " + !haveDb);
        if(haveDb){
            db.createDataBase();
        }
        System.out.println("Папка уже создана" + tempDirectory.getPath() + " " + !tempDirectory.mkdir());

    }
    private void chooseFile()throws IOException, ParserConfigurationException, SAXException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("FB2 Files", "*.fb2"));
        File selectedFile = fileChooser.showOpenDialog(primarStage);
        if (selectedFile != null) {
            selectedFilePath = selectedFile.getAbsolutePath();
            addNewBook(selectedFilePath);
        }
    }

    private void addNewBook(String path) throws IOException, ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        OpenHandler handler = new OpenHandler();
        parser.parse(new File(path), handler);
        fictionBookList.get(fictionBookList.size() - 1).setPath(path);
                flowPane.getChildren().add(new BookObject(fictionBookList.get(fictionBookList.size() - 1 ).getName(),
                fictionBookList.get(fictionBookList.size() - 1 ).getCoverPath()));
       
    }
    public static Stage getPrimaryStage(){
        return primarStage;
    }
    public static void hideStage(){
        primarStage.hide();
    }
    public static void showStage(){
        primarStage.show();
    }
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException
    {
        launch(args);
    }
}