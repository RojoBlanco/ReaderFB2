package ru.read.reader.fx;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

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

        setSpacing(10);

        getChildren().addAll(cover, this.name);

        setAlignment(javafx.geometry.Pos.CENTER);


    }
}
