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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
/**
 *
 * @author narut
 */
public class WordSetButton{
    VBox vBox = new VBox();
    Label titleLabel = new Label();
    Label countLabel = new Label();
    public WordSetButton(String title, int count){
        vBox.setPrefWidth(146);
        vBox.setPrefHeight(115);
        vBox.setMinWidth(146);
        vBox.setMinHeight(115);
        vBox.setMaxWidth(146);
        vBox.setMaxHeight(115);
        vBox.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE, new CornerRadii(15), new Insets(0, 0, 0, 0))));
        vBox.setStyle("-fx-background-color:lightskyblue;");
        vBox.setStyle("-fx-background-radius:15;");
        vBox.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.2, 0.0, 0.0);");
        vBox.setPadding(new Insets(10, 0, 0, 15));
        vBox.setSpacing(3);
        
        titleLabel.setText(title);
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 20));

        countLabel.setText(Integer.toString(count) + " word(s)");
        countLabel.setTextFill(Color.WHITE);
        countLabel.setFont(Font.font("System", 14));
        
        vBox.getChildren().add(titleLabel);
        vBox.getChildren().add(countLabel);
        
        vBox.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent e){
                vBox.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.7, 0.0, 0.0);");
            }
        });
        
        vBox.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent e){
                vBox.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.2, 0.0, 0.0);");
            }
        });
        
        vBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                try{
                    System.out.println("WordsetButtonPressed");
                    ArrayList<WordButton> wordButtList = new ArrayList<WordButton>();
                    BorderPane wordsetPageParent = FXMLLoader.load(getClass().getResource("/java_project/WordsetPage.fxml"));
                    ((Label)(((BorderPane)wordsetPageParent.getChildren().get(1)).getChildren().get(0))).setText(titleLabel.getText());
                    
                    String query = "SELECT * FROM " + titleLabel.getText();
                    int count = 0;
                    try(
                        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                    ){		
                        System.out.println("Access word Table successfully");   

                        //Register this table
                        while(rs.next()){
                            wordButtList.add(new WordButtonClosable(rs.getString("word"), rs.getString("chinese"), rs.getString("POS"), titleLabel.getText()));
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
        });
    }
    
}
