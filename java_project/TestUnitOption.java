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
public class TestUnitOption extends TestUnit{
    ToggleGroup group;
    RadioButton option1Butt;
    RadioButton option2Butt;
    RadioButton option3Butt;
    VBox subBox;
    
    public TestUnitOption(String ans, String ques, String option1, String option2, String option3){
        super(ans, ques);
        group = new ToggleGroup();
        option1Butt = new RadioButton(option1);
        option2Butt = new RadioButton(option2);
        option3Butt = new RadioButton(option3);
        
        // Set vbox
        vBox.setPrefWidth(400);
        vBox.setPrefHeight(180);
        vBox.setMinWidth(400);
        vBox.setMinHeight(180);
        vBox.setMaxWidth(400);
        vBox.setMaxHeight(180);
        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(20), new Insets(25, 20, 0, 20))));
        vBox.setStyle("-fx-background-color:lightskyblue;");
        vBox.setStyle("-fx-background-radius:15;");
        vBox.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 0, 0.0, 0.0, 0.0);;");
        vBox.setSpacing(15);
        vBox.setAlignment(Pos.TOP_LEFT);
        vBox.setVisible(true);
        
        subBox = new VBox();
        subBox.setSpacing(10);
        
        option1Butt.setFont(Font.font("Arial Rounded MT Bold", FontWeight.NORMAL, 15));
        option1Butt.setTextFill(Color.web("0x6F86A3"));
        option1Butt.setToggleGroup(group);
        option2Butt.setFont(Font.font("Arial Rounded MT Bold", FontWeight.NORMAL, 20));
        option2Butt.setTextFill(Color.web("0x6F86A3"));
        option2Butt.setToggleGroup(group);
        option3Butt.setFont(Font.font("Arial Rounded MT Bold", FontWeight.NORMAL, 20));
        option3Butt.setTextFill(Color.web("0x6F86A3"));
        option3Butt.setToggleGroup(group);
        
        subBox.getChildren().add(option1Butt);
        subBox.getChildren().add(option2Butt);
        subBox.getChildren().add(option3Butt);
        
        vBox.getChildren().add(qLabel);
        vBox.getChildren().add(subBox);
    }
}
