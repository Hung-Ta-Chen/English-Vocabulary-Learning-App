/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_project;

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
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class QuizResultController {

    @FXML
    private Button leaveButton;

    @FXML
    private Label resultLabel;

    @FXML
    void leaveButtonPressed(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        
        // Get set name
        String set_name = (String) stage.getUserData();
        try{
            System.out.println("WordsetButtonPressed");
            ArrayList<WordButton> wordButtList = new ArrayList<WordButton>();
            BorderPane wordsetPageParent = FXMLLoader.load(getClass().getResource("/java_project/WordsetPage.fxml"));
            ((Label)(((BorderPane)wordsetPageParent.getChildren().get(1)).getChildren().get(0))).setText(set_name);

            String query = "SELECT * FROM " + set_name;
            int count = 0;
            try(
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
            ){		
                System.out.println("Access word Table successfully");   

                //Register this table
                while(rs.next()){
                    wordButtList.add(new WordButtonClosable(rs.getString("word"), rs.getString("chinese"), rs.getString("POS"), set_name));
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
        catch(IOException ie){
            System.out.println(ie.getMessage());
            System.out.println(ie.getCause());
        }
    }

    public void mouseEnterButton(MouseEvent event){
        ((Button)event.getSource()).setStyle("-fx-text-fill: lightgray; -fx-background-color: null");
    }
    
    public void mouseExitButton(MouseEvent event){
        ((Button)event.getSource()).setStyle("-fx-text-fill: white; -fx-background-color: null");
    }
}
