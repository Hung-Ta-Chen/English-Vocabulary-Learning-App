/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_project;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
/**
 *
 * @author narut
 */
public class TestUnitBlank extends TestUnit{
    TextField ansBlank;
    public TestUnitBlank(String ans, String ques){
        super(ans, ques);
        
        // Set vbox
        vBox.setPrefWidth(450);
        vBox.setPrefHeight(140);
        vBox.setMinWidth(450);
        vBox.setMinHeight(140);
        vBox.setMaxWidth(450);
        vBox.setMaxHeight(140);
        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(20), new Insets(25, 20, 0, 20))));
        //vBox.setStyle("-fx-background-color:lightskyblue;");
        //vBox.setStyle("-fx-background-radius:15;");
        vBox.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 0, 0.0, 0.0, 0.0);;");
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setVisible(true);
        
        //qLabel.setText(ques);
        
        ansBlank = new TextField();
        ansBlank.setFont(new Font(15));
        ansBlank.getStylesheets().add("/java_project/textfield_style.css");
        ansBlank.setMaxWidth(350);
        
        vBox.getChildren().add(qLabel);
        vBox.getChildren().add(ansBlank);
        vBox.setMargin(vBox.getChildren().get(0), new Insets(20, 0, 0, 60));
        vBox.setMargin(vBox.getChildren().get(1), new Insets(0, 0, 0, 50));
    }
}
