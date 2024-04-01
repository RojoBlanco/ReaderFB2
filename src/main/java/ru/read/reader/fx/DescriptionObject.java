package ru.read.reader.fx;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;



public class DescriptionObject extends VBox {
     private Label name;
     private Label text;

     DescriptionObject(String name, String text){
          this.name = new Label(name);
          this.text = new Label(text);
          setSpacing(5);
          System.out.println(this.name.getLayoutBounds().getWidth());
          this.name.setStyle("-fx-font-weight: bold;");


          getChildren().addAll(this.name,this.text );
          setAlignment(javafx.geometry.Pos.TOP_CENTER);
     }
}
