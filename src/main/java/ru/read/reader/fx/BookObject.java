package ru.read.reader.fx;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.xml.sax.SAXException;
import ru.read.reader.fb2format.DescriptionBlock.Author;
import ru.read.reader.fb2format.FictionBook;
import ru.read.reader.Main;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class BookObject extends VBox {
    private static int nowBook;
    private static int idCounter = 0;
    private  static Stage popupStage;
    private int id;
    private ImageView cover;
    private Image img;
    private Label name;
    private FictionBook thisBook = Main.fictionBookList.get(this.id);
    public BookObject(String name, String path){
        id = idCounter++;
        this.name = new Label(name);
        img = new Image("file:"+ path);

        cover = new ImageView(img);
        cover.setFitHeight(300);
        cover.setFitWidth(300);

        setSpacing(20);

        getChildren().addAll(cover, this.name);


        addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            openPopupWindow();
            System.out.println("Clicked on ImageWithTextPane with id: " + id);
        });
    }

    private void openPopupWindow() {
        String authors = new String("");
        nowBook = id;
        // Создаем новое окно
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.DECORATED);

        var cover1 = new ImageView(img);
        cover1.setFitHeight(350);
        cover1.setFitWidth(350);
        // Создаем контент для нового окна
        DescriptionObject descriptionObject = new DescriptionObject("Название");
        descriptionObject.addToTextFlow(new Text(thisBook.getName()));

        DescriptionObject descriptionObject2 = new DescriptionObject("Автор");
        for (Author author: thisBook.getDesc().getTitleInfo().getAuthors()){
            descriptionObject2.addToTextFlow(new Text(author.toString() + " "));
        }
        var annotation = thisBook.getDesc().getTitleInfo().getAnnotation().toString();
        DescriptionObject descriptionObject3 = new DescriptionObject("Описание");
        descriptionObject3.addToTextFlow(new Text(annotation));

        Button btn = new Button();
        btn.setText("Открыть книгу");
        btn.setOnAction(e -> {
            try {
                System.out.println(Main.fictionBookList.get(id).getPath());
                openFb2ReaderWindow(Main.fictionBookList.get(id).getPath());

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ParserConfigurationException ex) {
                throw new RuntimeException(ex);
            } catch (SAXException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Создаем VBox и добавляем в него Label
        VBox popupLayout = new VBox(10);

        popupLayout.setAlignment(javafx.geometry.Pos.TOP_CENTER);

        popupLayout.getChildren().addAll(cover1,descriptionObject, descriptionObject2, descriptionObject3);

        setVgrow(btn,  Priority.ALWAYS);
        popupLayout.getChildren().add(btn);

        // Создаем сцену для нового окна
        Scene popupScene = new Scene(popupLayout, 977, 760);

        // Устанавливаем сцену для нового окна
        popupStage.setScene(popupScene);
        this.popupStage = popupStage;
        // Показываем новое окно
        popupStage.show();
    }

    public static int getNowBook() {
        return nowBook;
    }

    private void openFb2ReaderWindow(String filePath) throws IOException, ParserConfigurationException, SAXException {
        Reader fb2ReaderWindow = new Reader();
        fb2ReaderWindow.display(filePath);
        popupStage.hide();
        Main.hideStage();
    }
    public static void showPopoutStage(){
        popupStage.show();
    }
}
