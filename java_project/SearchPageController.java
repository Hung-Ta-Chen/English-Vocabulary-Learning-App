
package java_project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
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
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SearchPageController extends WordAppController{

    @FXML
    private TextField searchTextField;
    
    @Override
    public void searchButtonPressed(ActionEvent event) throws IOException{
        //Do nothing
    }
    
    public void enterPressed(KeyEvent event) throws IOException{
        if(event.getCode() == KeyCode.ENTER){
            System.out.print("Search text change");
            ArrayList<WordButton> wordButtList = new ArrayList<WordButton>();
            System.out.println("searchButtonPressed");
            BorderPane searchPageParent = FXMLLoader.load(getClass().getResource("/java_project/SearchPage.fxml"));

            String query1 = "SELECT * FROM wordtable WHERE word LIKE '" + searchTextField.getText() +"%'";
            try(
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query1);
            ){		
                System.out.println("Access word Table successfully");   

                //Register this table
                while(rs.next()){
                    wordButtList.add(new WordButton(rs.getString("word"), rs.getString("chinese"), rs.getString("POS"), "wordtable"));
                }
            } 
            catch (SQLException se) {
                se.printStackTrace();
            } 

            for(WordButton butt: wordButtList){
                ((VBox)((ScrollPane)((VBox)searchPageParent.getChildren().get(1)).getChildren().get(1)).getContent()).getChildren().add(butt.vBox);
            }
            Scene searchPageScene = new Scene(searchPageParent);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(searchPageScene);
            window.setTitle("Word App");
            window.show();
        }
    }

}