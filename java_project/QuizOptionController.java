/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static java_project.WordAppController.DB_URL;
import static java_project.WordAppController.PASS;
import static java_project.WordAppController.USER;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.util.Collections;
import java.util.Random;

public class QuizOptionController extends WordAppController{

    @FXML private Button startButton;

    @FXML private Button cancelButton;

    @FXML private Label wordTitleLabel;

    @FXML private TextField numTextfield;

    @FXML private RadioButton POSbutton1;

    @FXML private ToggleGroup type;

    @FXML private RadioButton POSbutton2;

    @FXML private Label setNameLabel;
    @FXML private Label setSizeLabel;

    @FXML
    void cancelButtonPressed(ActionEvent event) throws IOException{
        ArrayList<WordButton> wordButtList = new ArrayList<WordButton>();
        BorderPane wordsetPageParent = FXMLLoader.load(getClass().getResource("/java_project/WordsetPage.fxml"));
        ((Label)(((BorderPane)wordsetPageParent.getChildren().get(1)).getChildren().get(0))).setText(setNameLabel.getText());

        String query1 = "SELECT * FROM " + setNameLabel.getText();
        int count = 0;
        try(
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query1);
        ){		
            System.out.println("Access word Table successfully");   

            //Register this table
            while(rs.next()){
                wordButtList.add(new WordButtonClosable(rs.getString("word"), rs.getString("chinese"), rs.getString("POS"), setNameLabel.getText()));
                count++;
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        } 

        for(WordButton butt: wordButtList){
            ((VBox)((ScrollPane)((VBox)wordsetPageParent.getChildren().get(2)).getChildren().get(0)).getContent()).getChildren().add(butt.vBox);
        }

        ((Label)((VBox)wordsetPageParent.getChildren().get(2)).getChildren().get(1)).setText(Integer.toString(count));
        Scene wordsetPageScene = new Scene(wordsetPageParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(wordsetPageScene);
        window.setTitle("Word App");
        window.show();
    }

    @FXML
    void startButtonPressed(ActionEvent event) throws IOException{
        if(numTextfield.getText() == ""){
            wordTitleLabel.setTextFill(Color.RED);
        }
        else if(isNumeric(numTextfield.getText()) == false){
            wordTitleLabel.setTextFill(Color.RED);
        }
        else if(Integer.parseInt(numTextfield.getText()) > Integer.parseInt(setSizeLabel.getText())){
            wordTitleLabel.setTextFill(Color.RED);
        }
        else if(Integer.parseInt(numTextfield.getText()) > 50 || Integer.parseInt(numTextfield.getText()) < 1){
            wordTitleLabel.setTextFill(Color.RED);
        }
        else{
            int cnt = Integer.parseInt(numTextfield.getText());
            String set_name = setNameLabel.getText();
            Quiz quiz = new Quiz(set_name, cnt);
            
            ArrayList<String> wordArray = new ArrayList<String>();
            ArrayList<String> transArray = new ArrayList<String>();
            
            // Access database to get words
            String query = "SELECT * FROM " + quiz.set_name;
            try(
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
            ){		
                System.out.println("Access " + set_name + " successfully");   

                //Register this table
                while(rs.next()){
                    wordArray.add(rs.getString("word"));
                    transArray.add(rs.getString("chinese"));
                }
            } 
            catch (SQLException e) {
                e.printStackTrace();
            } 
            
            // Shuffle two arrays
            long seed = System.nanoTime();
            Collections.shuffle(wordArray, new Random(seed));
            Collections.shuffle(transArray, new Random(seed));
            
            for(int i = 0; i < quiz.quiz_count; i++){
                quiz.quesArray.add(transArray.get(i));
                quiz.ansArray.add(wordArray.get(i));
            }
            
            // Load in quiz page
            ArrayList<TestUnitBlank> quizQuesList = new ArrayList<TestUnitBlank>();
            BorderPane quizParent = FXMLLoader.load(getClass().getResource("/java_project/Quiz.fxml"));
               
            for(int i = 0; i < quiz.quiz_count; i++){
                quizQuesList.add(new TestUnitBlank(quiz.ansArray.get(i), quiz.quesArray.get(i)));
            }
            

            for(TestUnitBlank butt: quizQuesList){
                ((VBox)((ScrollPane)((VBox)quizParent.getChildren().get(1)).getChildren().get(0)).getContent()).getChildren().add(butt.vBox);
            }
            
            
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            
            Scene quizScene = new Scene(quizParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setUserData(quiz);
            window.setScene(quizScene);
            window.setTitle("Word App");
            window.show();
        }
    }
    
    public void mouseEnterButton(MouseEvent event){
        ((Button)event.getSource()).setStyle("-fx-text-fill: lightgray; -fx-background-color: null");
    }
    
    public void mouseExitButton(MouseEvent event){
        ((Button)event.getSource()).setStyle("-fx-text-fill: white; -fx-background-color: null");
    }
    
    boolean isNumeric(String str){
        try{
            int num = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe){
            return false;
        }
        
        return true;
    }
}
