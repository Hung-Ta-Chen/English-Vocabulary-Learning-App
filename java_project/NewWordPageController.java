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

public class NewWordPageController extends WordAppController{

    @FXML private Button homeButton;
    @FXML private Button searchButton;
    @FXML private Button addButton;
    @FXML private Button settingButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private TextField wordTextfield;
    @FXML private TextField transTextfield;
    @FXML private RadioButton POSbutton1;
    @FXML private ToggleGroup POS;
    @FXML private RadioButton POSbutton2;
    @FXML private RadioButton POSbutton3;
    @FXML private RadioButton POSbutton4;
    @FXML private RadioButton POSbutton5;
    @FXML private RadioButton POSbutton6;
    @FXML private RadioButton POSbutton7;
    @FXML private RadioButton POSbutton8;
    @FXML private Label setNameLabel;
    @FXML private Label transTitleLabel;
    @FXML private Label wordTitleLabel;
    
    @FXML 
    void cancelButtonPressed(ActionEvent event) throws IOException{
        System.out.println("WordsetButtonPressed");
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
    void saveButtonPressed(ActionEvent event) {
        // Save the word info
        if(wordTextfield.getText().equals("") || transTextfield.getText().equals("")){
            if(wordTextfield.getText().equals("")){
                wordTitleLabel.setTextFill(Color.RED);
            }
            if(transTextfield.getText().equals("")){
                transTitleLabel.setTextFill(Color.RED);
            }
        }
        else{
            wordTitleLabel.setTextFill(Color.web("#6F86A3"));
            transTitleLabel.setTextFill(Color.web("#6F86A3"));
            
            try(
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
            ){		
                
                //insert into coreesponding table
                String sql1 = "INSERT INTO " + setNameLabel.getText() + " VALUES ('" + wordTextfield.getText();
                String sql2 = "INSERT INTO wordtable VALUES ('" + wordTextfield.getText();
                sql1 += "', '" + transTextfield.getText() + "', '";
                sql2 += "', '" + transTextfield.getText() + "', '";
                
                if(POS.getSelectedToggle() == POSbutton1){
                    sql1 = sql1 + "n')";
                    sql2 = sql2 + "n')";
                }
                else if(POS.getSelectedToggle() == POSbutton2){
                    sql1 = sql1 + "adj')";
                    sql2 = sql2 + "adj')";
                }
                else if(POS.getSelectedToggle() == POSbutton3){
                    sql1 = sql1 + "v')";
                    sql2 = sql2 + "v')";
                }
                else if(POS.getSelectedToggle() == POSbutton4){
                    sql1 = sql1 + "adv')";
                    sql2 = sql2 + "adv')";
                }
                else if(POS.getSelectedToggle() == POSbutton5){
                    sql1 = sql1 + "pronoun')";
                    sql2 = sql2 + "pronoun')";
                }
                else if(POS.getSelectedToggle() == POSbutton6){
                    sql1 = sql1 + "prep')";
                    sql2 = sql2 + "prep')";
                }
                else if(POS.getSelectedToggle() == POSbutton7){
                    sql1 = sql1 + "conj')";
                    sql2 = sql2 + "conj')";
                }
                else{
                    sql1 = sql1 + "interj')";
                    sql2 = sql2 + "interj')";
                }
                stmt.executeUpdate(sql1);
                stmt.executeUpdate(sql2);
                System.out.println("Record inserted successfully");        
            } 
            catch (SQLException e) {
                e.printStackTrace();
            } 
            
            
            // Update table information
            String query = "SELECT size FROM settable WHERE name = '" + setNameLabel.getText() + "'";
            int table_size = 0;
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
            catch (SQLException e) {
                e.printStackTrace();
            } 
            
            table_size += 1;
            String sql3 = "UPDATE settable SET size = " + table_size;
            sql3 += " WHERE name = '" + setNameLabel.getText() + "'";
            try(
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
            ){		
                stmt.executeUpdate(sql3);
                System.out.println("Table size updated successfully");   
            } 
            catch (SQLException e) {
                e.printStackTrace();
            } 
            
            
            // Go back to WordsetPage
            try{
                System.out.println("WordsetButtonPressed");
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
            catch(IOException ie){
                System.out.println(ie.getMessage());
                System.out.println(ie.getCause());
            }
            
        }
        
    }
    
    public void mouseEnterButton(MouseEvent event){
        ((Button)event.getSource()).setStyle("-fx-text-fill: lightgray; -fx-background-color: null");
    }
    
    public void mouseExitButton(MouseEvent event){
        ((Button)event.getSource()).setStyle("-fx-text-fill: white; -fx-background-color: null");
    }





}


