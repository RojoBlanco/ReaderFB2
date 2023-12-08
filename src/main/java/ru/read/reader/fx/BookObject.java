package ru.read.reader.fx;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.read.reader.FB2Format.DescriptionBlock.Author;
import ru.read.reader.FB2Format.FictionBook;
import ru.read.reader.Main;

public class BookObject extends VBox {
    private static int idCounter = 0;

    private int id;
    private ImageView cover;
    private Image img;
    private Label name;
    private FictionBook thisBook = Main.fictionBookList.get(this.id);
    public BookObject(String name, String path){
        id = idCounter++;
        this.name = new Label(name);
        img = new Image(path);

        cover = new ImageView(img);
        cover.setFitHeight(300);
        cover.setFitWidth(300);

        setSpacing(20);

        getChildren().addAll(cover, this.name);


        addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            openPopupWindow("hell");
            System.out.println("Clicked on ImageWithTextPane with id: " + id);
        });
    }

    private void openPopupWindow(String popupText) {
        String authors = new String("");
        // Создаем новое окно
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.DECORATED);

        var cover1 = new ImageView(img);
        cover1.setFitHeight(350);
        cover1.setFitWidth(350);
        // Создаем контент для нового окна
        DescriptionObject descriptionObject = new DescriptionObject("Название", thisBook.getName());
        for (Author author: thisBook.getDesc().getTitleInfo().getAuthors()){
            authors += author.toString() + " ";
        }
        DescriptionObject descriptionObject2 = new DescriptionObject("Автор",authors);
        var annotation = thisBook.getDesc().getTitleInfo().getAnnotation().toString();
        DescriptionObject descriptionObject3 = new DescriptionObject("Описание",annotation);

        Button btn = new Button();
        btn.setText("Открыть книгу");

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

        // Показываем новое окно
        popupStage.show();
    }
}
