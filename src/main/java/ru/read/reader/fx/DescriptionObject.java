package ru.read.reader.fx;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;


public class DescriptionObject extends VBox {
     private Label name;
     private TextFlow text;

     DescriptionObject(String name) {
          this.name = new Label(name);
          this.text = new TextFlow();
          text.setTextAlignment(TextAlignment.CENTER);
          setSpacing(5);
          System.out.println(this.name.getLayoutBounds().getWidth());
          this.name.setStyle("-fx-font-weight: bold;");
          text.setMaxWidth(300);

          getChildren().addAll(this.name,this.text );
          setAlignment(javafx.geometry.Pos.TOP_CENTER);
     }
     public void addToTextFlow(Text text){
          text.setTextAlignment(TextAlignment.CENTER);
          this.text.getChildren().add(text);
     }
}
