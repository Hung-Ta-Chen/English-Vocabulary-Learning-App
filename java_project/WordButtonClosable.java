/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_project;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.scene.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import static java_project.WordAppController.DB_URL;
import static java_project.WordAppController.PASS;
import static java_project.WordAppController.USER;
import javafx.event.EventType;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.image.*;
import javafx.event.ActionEvent;
/**
 *
 * @author narut
 */

public class WordButtonClosable extends WordButton{
    Button deleteBut = new Button();
    Image img = new Image("/java_project/close.png");
    ImageView iv = new ImageView(img); 
    
    public WordButtonClosable(String word, String trans, String pos, String wordsetName){
        super(word, trans, pos, wordsetName);
        
        //bp.setPrefWidth(200);
        //bp.setPrefHeight(18);
        deleteBut.setStyle("-fx-background-color:none;");
        deleteBut.setPrefSize(12, 12);
        iv.setFitHeight(12);
        iv.setFitWidth(12);
        deleteBut.setGraphic(iv);
        bp.setRight(deleteBut);
        bp.setMargin(deleteBut, new Insets(2, 5, 0, 0));
        //vBox.getChildren().add(bp);
        

        vBox.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent e){
                vBox.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.7, 0.0, 0.0);");
            }
        });
        
        vBox.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent e){
                vBox.setStyle("-fx-effect: null;");
            }
        });
        
        
        
        deleteBut.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent e){
                deleteBut.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.7, 0.0, 0.0);-fx-background-color:none;");
            }
        });
        
        deleteBut.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent e){
                deleteBut.setStyle("-fx-effect: none;-fx-background-color:none;");
            }
        });
        
        deleteBut.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent e){
                try(
                    Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    Statement stmt = conn.createStatement();
                ){		
                    //Delete the word
                    String sql1 = "DELETE FROM " + wordSetName + " WHERE word = '" + wordLabel.getText() + "'";
                    String sql2 = "DELETE FROM wordtable WHERE word = '" + wordLabel.getText() + "'";
                    stmt.executeUpdate(sql1);
                    stmt.executeUpdate(sql2);
                    System.out.println("Word deleted successfully");   

                    // Update table information
            
                } 
                catch (SQLException se) {
                    se.printStackTrace();
                } 
                
                String query = "SELECT size FROM settable WHERE name = '" + wordSetName + "'";
                int table_size = 1;
                try(
                    Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                ){		
                    System.out.println("Access setTable successfully");   

                    //Get size of table
                    while(rs.next()){
                        table_size = rs.getInt("size");
                    }
                } 
                catch (SQLException se) {
                    se.printStackTrace();
                } 

                table_size -= 1;
                String sql3 = "UPDATE settable SET size = " + table_size;
                sql3 += " WHERE name = '" + wordSetName + "'";
                try(
                    Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    Statement stmt = conn.createStatement();
                ){		
                    stmt.executeUpdate(sql3);
                    System.out.println("Table size updated successfully");   
                } 
                catch (SQLException se) {
                    se.printStackTrace();
                }   
                
                
                // Go back to WordsetPage
                try{
                    //System.out.println("WordsetButtonPressed");
                    ArrayList<WordButton> wordButtList = new ArrayList<WordButton>();
                    BorderPane wordsetPageParent = FXMLLoader.load(getClass().getResource("/java_project/WordsetPage.fxml"));
                    ((Label)(((BorderPane)wordsetPageParent.getChildren().get(1)).getChildren().get(0))).setText(wordSetName);

                    String query1 = "SELECT * FROM " + wordSetName;
                    int count = 0;
                    try(
                        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(query1);
                    ){		
                        System.out.println("Access word Table successfully");   

                        //Register this table
                        while(rs.next()){
                            wordButtList.add(new WordButtonClosable(rs.getString("word"), rs.getString("chinese"), rs.getString("POS"), wordSetName));
                            count++;
                        }
                    } 
                    catch (SQLException se) {
                        se.printStackTrace();
                    } 

                    for(WordButton butt: wordButtList){
                        ((VBox)((ScrollPane)((VBox)wordsetPageParent.getChildren().get(2)).getChildren().get(0)).getContent()).getChildren().add(butt.vBox);
                    }
                    
                    ((Label)((VBox)wordsetPageParent.getChildren().get(2)).getChildren().get(1)).setText(Integer.toString(count));
                    Scene wordsetPageScene = new Scene(wordsetPageParent);
                    Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
                    window.setScene(wordsetPageScene);
                    window.setTitle("Word App");
                    window.show();
                }
                catch(IOException ie){
                    System.out.println(ie.getMessage());
                    System.out.println(ie.getCause());
                }
            }
        });
    }
}
