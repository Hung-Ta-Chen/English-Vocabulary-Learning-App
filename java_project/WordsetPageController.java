/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_project;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.Alert.*;


public class WordsetPageController extends WordAppController{

    @FXML private Label titleLabel;
    @FXML private Button addWordButton;
    @FXML private Button quizButton;
    @FXML private Label setSize;


    public void addWordButtonPressed(ActionEvent event) throws IOException{
        System.out.println("newWord Button Pressed");
        BorderPane newWordPageParent = FXMLLoader.load(getClass().getResource("/java_project/NewWordPage.fxml"));
        ((Label)(((VBox)newWordPageParent.getChildren().get(2)).getChildren().get(6))).setText(titleLabel.getText());
        Scene newWordPageScene = new Scene(newWordPageParent);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(newWordPageScene);
        window.setTitle("Word App");
        window.show();
    }
    
    public void quizButtonPressed(ActionEvent event) throws IOException{
        System.out.println("Quiz Button Pressed");
        if(Integer.parseInt(setSize.getText()) >= 3){
            BorderPane parent = FXMLLoader.load(getClass().getResource("/java_project/QuizOption.fxml"));
            ((Label)(((VBox)parent.getChildren().get(2)).getChildren().get(4))).setText(titleLabel.getText());
            ((Label)(((VBox)parent.getChildren().get(2)).getChildren().get(5))).setText(setSize.getText());
            Scene newWordPageScene = new Scene(parent);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(newWordPageScene);
            window.setTitle("Word App");
            window.show();
        }
        else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Set size must larger than 3");
            alert.showAndWait();
        }
    }
    
    public void mouseEnterButton(MouseEvent event){
        ((Button)event.getSource()).setStyle("-fx-text-fill: lightgray; -fx-background-color: null");
    }
    
    public void mouseExitButton(MouseEvent event){
        ((Button)event.getSource()).setStyle("-fx-text-fill: white; -fx-background-color: null");
    }

}
