/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_project;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.ArrayList;
/**
 *
 * @author narut
 */
public class TestUnit {
    String answer;
    String question;
    VBox vBox;
    Label qLabel;

    
    
    public TestUnit(String ans, String ques){

        answer = ans;
        question = ques;
        vBox = new VBox();
        qLabel = new Label();
        qLabel.setText(ques);
        qLabel.setTextFill(Color.web("0x6F86A3"));
        qLabel.setFont(Font.font("Arial Rounded MT Bold", FontWeight.NORMAL, 20));
    }
}
