package java_project;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.*;
import javafx.fxml.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import static java_project.WordAppController.DB_URL;
import static java_project.WordAppController.PASS;
import static java_project.WordAppController.USER;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class WordApp extends Application {
    static final String DB_URL = "jdbc:mysql://localhost/java_final";
    static final String USER = "root";
    static final String PASS = "naruto54276";

    @Override
    public void start(Stage primaryStage) throws Exception{
        ArrayList<WordSetButton> setButtList = new ArrayList<WordSetButton>();
        BorderPane root = FXMLLoader.load(getClass().getResource("/java_project/HomePage.fxml"));     
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
            ((HBox)((ScrollPane)((VBox)root.getChildren().get(1)).getChildren().get(1)).getContent()).getChildren().add(butt.vBox);
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
        
        ((Label)(((GridPane)((GridPane)((VBox)root.getChildren().get(1)).getChildren().get(2)).getChildren().get(1)).getChildren().get(1))).setText(Integer.toString(count));
        
        Scene scene = new Scene(root);
        primaryStage.setTitle("Word App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
            launch(args);
    }
}
