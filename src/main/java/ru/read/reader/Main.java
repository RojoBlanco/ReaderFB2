package ru.read.reader;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.io.FileSystemResource;

import org.springframework.web.client.RestTemplate;
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
    private Database db;
    public static File folderPath, configDirectory, tempDirectory, dbDirectory;
    private static Stage primaryStage;
    private FlowPane flowPane;

    @Override
    public void start(Stage primaryStage) throws URISyntaxException {
        Thread readDbThread = new Thread(this::createMainDirs);
        readDbThread.start();
        this.primaryStage = primaryStage;
        flowPane = new FlowPane();

        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPrefSize(1000, 900);

        MenuBar menuBar = new MenuBar();
        Menu sendBooksMenu = new Menu("Отправить книги");
        MenuItem sendBooksItem = new MenuItem("Отправить все");
        sendBooksItem.setOnAction(e -> {
            try {
                sendFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        sendBooksMenu.getItems().add(sendBooksItem);
        Menu getBooks = new Menu("Получить книги");
        MenuItem getBooksItem = new MenuItem("Получить все");
        getBooksItem.setOnAction(e ->
                getFile()
        );
        getBooks.getItems().add(getBooksItem);

        Menu fileMenu = new Menu("Файл");
        MenuItem openItem = new MenuItem("Открыть");
        MenuItem exitItem = new MenuItem("Выход");
        exitItem.setOnAction(e -> primaryStage.close());

        fileMenu.getItems().addAll(openItem, new SeparatorMenuItem(), exitItem);
        menuBar.getMenus().addAll(fileMenu, sendBooksMenu, getBooks);
        openItem.setOnAction(e -> {
            try {
                chooseFile();
            } catch (IOException | ParserConfigurationException | SAXException ex) {
                throw new RuntimeException(ex);
            }
        });



        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(flowPane);

        Scene scene = new Scene(borderPane, 1280, 920);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Библиотека");

        primaryStage.show();
        // assuming fictionBookList is a valid reference
        if (!fictionBookList.isEmpty())
            for (FictionBook fb : fictionBookList)
                addNewBookObject(fb);
    }


    private void createMainDirs() {
        folderPath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile();
        configDirectory = new File(folderPath, "User");
        dbDirectory = new File(configDirectory, "db");
        tempDirectory = new File(configDirectory, "temp");
        System.out.println(folderPath.getPath());
        System.out.println("Папка уже создана" + configDirectory.getPath() + " " + !configDirectory.mkdir());

        boolean haveDb = dbDirectory.mkdir();
        db = new Database(dbDirectory.getPath());
        System.out.println("Папка уже создана" + dbDirectory.getPath() + " " + !haveDb);
        if (haveDb) {
            db.createDataBase();
        } else {
            Thread thread = new Thread(db::readAllBook);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        System.out.println("Папка уже создана" + tempDirectory.getPath() + " " + !tempDirectory.mkdir());

    }

    private void chooseFile() throws IOException, ParserConfigurationException, SAXException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("FB2 Files", "*.fb2"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            String selectedFilePath = selectedFile.getAbsolutePath();
            addNewBook(selectedFilePath);
        }
    }
    private void sendFile() throws IOException{
        String url = "http://localhost:8080/upload/books";

        // Создаем объект RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Создаем объект заголовков
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Создаем объект MultiValueMap для передачи параметров
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // Добавляем файлы и другие параметры в MultiValueMap
        for (int i = 0; i < fictionBookList.size(); i++) {
            File file = new File(fictionBookList.get(i).getPath());
            body.add("files", new FileSystemResource(file));
            body.add("titles", fictionBookList.get(i).getDesc().getTitleInfo().getBookTitle());
            body.add("datesWritten", fictionBookList.get(i).getDesc().getTitleInfo().getDate());
            body.add("languages", fictionBookList.get(i).getDesc().getTitleInfo().getLang());
            String pepega = fictionBookList.get(i).getDesc().getTitleInfo().getAnnotation().toString();
            body.add("annotations",pepega);
        }

        // Создаем запрос с методом POST
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Выполняем запрос
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }

    private void getFile(){

            String url = "http://localhost:8080/download/allBooks";

            // Создаем объект RestTemplate
            RestTemplate restTemplate = new RestTemplate();

            // Выполняем GET-запрос
            ResponseEntity<List<Resource>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Resource>>() {});

            // Получаем тело ответа
            List<Resource> books = responseEntity.getBody();

    }

    private void addNewBook(String path) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            System.out.println(e.getMessage());
        }

        OpenHandler handler = new OpenHandler();
        try {
            parser.parse(new File(path), handler);
        } catch (SAXException | IOException e) {
            System.out.println(e.getMessage());
        }
        if (handler.isAdd) {
            fictionBookList.get(fictionBookList.size() - 1).setPath(path);
            addNewBookObject(fictionBookList.get(fictionBookList.size() - 1));
            new Thread(() -> db.addNewBook(fictionBookList.get(fictionBookList.size() - 1))).start();
        }
    }

    private void addNewBookObject(FictionBook fb) {
        flowPane.getChildren().add(new BookObject(fb.getName(),
                fb.getCoverPath()));
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void hideStage() {
        primaryStage.hide();
    }

    public static void showStage() {
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}