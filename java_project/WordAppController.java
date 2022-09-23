/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.*;
import java.io.IOException;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.ArrayList;

public class WordAppController {
    static final String DB_URL = "jdbc:mysql://localhost/java_final";
    static final String USER = "root";
    static final String PASS = "naruto54276";
    @FXML private Button homeBotton;
    @FXML private Button searchButton;
    @FXML private Button addButton;
    @FXML private Button settingButton;
     
    public void homeButtonPressed(ActionEvent event) throws IOException{
        ArrayList<WordSetButton> setButtList = new ArrayList<WordSetButton>();
        System.out.println("homeButtonPressed");
        BorderPane homePageParent = FXMLLoader.load(getClass().getResource("/java_project/HomePage.fxml"));
        
        String query = "SELECT * FROM settable";
        try(
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        ){		
            System.out.println("Access setTable successfully");   

            //Register this table
            while(rs.next()){
                setButtList.add(new WordSetButton(rs.getString("name"), rs.getInt("size")));
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        } 
        

        for(WordSetButton butt: setButtList){
            ((HBox)((ScrollPane)((VBox)homePageParent.getChildren().get(1)).getChildren().get(1)).getContent()).getChildren().add(butt.vBox);
        }
        
        query = "SELECT * FROM wordtable";
        int count = 0;
        try(
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        ){		
            System.out.println("Access wordTable successfully");   

            //Register this table
            while(rs.next()){
                count++;
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        ((Label)(((GridPane)((GridPane)((VBox)homePageParent.getChildren().get(1)).getChildren().get(2)).getChildren().get(1)).getChildren().get(1))).setText(Integer.toString(count));
        
        Scene homePageScene = new Scene(homePageParent);        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(homePageScene);
        window.setTitle("Word App");
        window.show();
    }
    
    public void searchButtonPressed(ActionEvent event) throws IOException{
        System.out.println("searchButtonPressed");
        BorderPane searchPageParent = FXMLLoader.load(getClass().getResource("/java_project/SearchPage.fxml"));
        Scene searchPageScene = new Scene(searchPageParent);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(searchPageScene);
        window.setTitle("Word App");
        window.show();
    }
    
    public void addButtonPressed(ActionEvent event) throws IOException{
        System.out.println("addButtonPressed");
        BorderPane addPageParent = FXMLLoader.load(getClass().getResource("/java_project/AddPage.fxml"));
        Scene addPageScene = new Scene(addPageParent);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPageScene);
        window.setTitle("Word App");
        window.show();
    }
    
    public void settingButtonPressed(ActionEvent event){
        
    }
    
    public void mouseEnter(MouseEvent event){
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.SLATEGREY);
        ((Button)event.getSource()).setEffect(shadow);
    }
    
    public void mouseExit(MouseEvent event){
        ((Button)event.getSource()).setEffect(null);
    }
 
}
