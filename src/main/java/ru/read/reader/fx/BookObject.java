package ru.read.reader.fx;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BookObject extends VBox {
    private static long idCounter = 0;

    private long id;
    private ImageView cover;
    private Label name;
    public BookObject(String name, String path){
        id = idCounter++;
        this.name = new Label(name);
        Image image = new Image(path);

        cover = new ImageView(image);
        cover.setFitHeight(300);
        cover.setFitWidth(300);

        setSpacing(20);

        getChildren().addAll(cover, this.name);

        setAlignment(javafx.geometry.Pos.CENTER);
        addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            openPopupWindow("hell");
            System.out.println("Clicked on ImageWithTextPane with id: " + id);
        });
    }

    private void openPopupWindow(String popupText) {
        // Создаем новое окно
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.DECORATED);

        // Создаем контент для нового окна
        DescriptionObject descriptionObject = new DescriptionObject("Привет", "Пока");


        // Создаем VBox и добавляем в него Label
        VBox popupLayout = new VBox(10);



        popupLayout.getChildren().add(descriptionObject);

        // Создаем сцену для нового окна
        Scene popupScene = new Scene(popupLayout, 200, 100);

        // Устанавливаем сцену для нового окна
        popupStage.setScene(popupScene);

        // Показываем новое окно
        popupStage.show();
    }
}
