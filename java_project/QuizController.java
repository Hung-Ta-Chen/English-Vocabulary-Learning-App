/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_project;

/**
 *
 * @author narut
 */
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import java.util.Collections;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class QuizController {

    @FXML
    private Button finishButton;
    
    @FXML
    private VBox quizBox;


    @FXML
    void finishButtonPressed(ActionEvent event) throws IOException{
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        
        // Get Quiz data
        Quiz quiz = (Quiz) stage.getUserData();
        for(int i = 0; i < quiz.quiz_count; i++){
            quiz.replyArray.add(((TextField)((VBox)quizBox.getChildren().get(i)).getChildren().get(1)).getText());
        }
        
        for(int i = 0; i < quiz.quiz_count; i++){
            if(quiz.ansArray.get(i).equals(quiz.replyArray.get(i))){
                quiz.correct.add(true);
            }
            else{
                quiz.correct.add(false);
            }
        }
        
        int true_count = Collections.frequency(quiz.correct, true);
        String result_str = Integer.toString(true_count)+ " / " + Integer.toString(quiz.quiz_count);
        BorderPane parent = FXMLLoader.load(getClass().getResource("/java_project/QuizResult.fxml"));
        ((Label)((VBox)(((VBox)parent.getChildren().get(1)).getChildren().get(0))).getChildren().get(0)).setText(result_str);
        Scene newWordPageScene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setUserData(quiz.set_name);
        window.setScene(newWordPageScene);
        window.setTitle("Word App");
        window.show();
           
    }

    public void mouseEnterButton(MouseEvent event){
        ((Button)event.getSource()).setStyle("-fx-text-fill: lightgray; -fx-background-color: null");
    }
    
    public void mouseExitButton(MouseEvent event){
        ((Button)event.getSource()).setStyle("-fx-text-fill: white; -fx-background-color: null");
    }

}
