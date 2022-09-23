package java_project;

import javafx.scene.control.Label;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddPageController extends WordAppController{

    @FXML
    private TextField titleTextField;

    @FXML
    private TextArea describeTextArea;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;
    
    @FXML
    private Label titleLabel;

    @Override
    public void addButtonPressed(ActionEvent event) throws IOException{
        //Do nothing
    }
    
    public void mouseEnterButton(MouseEvent event){
        ((Button)event.getSource()).setStyle("-fx-text-fill: lightgray; -fx-background-color: null");
    }
    
    public void mouseExitButton(MouseEvent event){
        ((Button)event.getSource()).setStyle("-fx-text-fill: white; -fx-background-color: null");
    }
    
    
    public void cancelButtonPressed(ActionEvent event) throws IOException{
        System.out.println("cancelButtonPressed");
        Parent homePageParent = FXMLLoader.load(getClass().getResource("/java_project/HomePage.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(homePageScene);
        window.setTitle("Word App");
        window.show();
    }
    
    public void textFieldCheckLength(KeyEvent event){
        if (titleTextField.getText().length() > 50){
            titleTextField.setText(titleTextField.getText().substring(0, 50));
            titleTextField.positionCaret(50);
        }
    }
    
    public void textAreaCheckLength(KeyEvent event){
        if (describeTextArea.getText().length() > 400){
            describeTextArea.setText(describeTextArea.getText().substring(0, 400));
            describeTextArea.positionCaret(400);
        }
    }
    
    public void saveButtonPressed(ActionEvent event){
        
        if(titleTextField.getText().equals("")){
            //Do nothing
            titleLabel.setTextFill(Color.RED);
        }
        else{
            titleLabel.setTextFill(Color.web("#6F86A3"));
    
            try(
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
            ){		
                //Create new table
                String sql = "CREATE TABLE " + titleTextField.getText();
                sql += " (word VARCHAR(45) NOT NULL, "
                     + "chinese VARCHAR(45) NOT NULL, "
                     + "POS ENUM('n', 'v', 'adj', 'prep', 'adv', 'conj', 'interj', 'pronoun') NOT NULL)";
                stmt.executeUpdate(sql);
                System.out.println("Table created successfully");   
                
                //Register this table
                sql = "INSERT INTO settable VALUES ('" + titleTextField.getText();
                sql += "', 0, '" + describeTextArea.getText()+ "')";
                stmt.executeUpdate(sql);
                System.out.println("Table registered successfully");               
            } 
            catch (SQLException e) {
                e.printStackTrace();
            } 
            
            //Switch to other page after creating the set
            try{
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

                Scene homePageScene = new Scene(homePageParent);        
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(homePageScene);
                window.setTitle("Word App");
                window.show();
            }catch (IOException e){
                System.out.println("IO error");
            }
        } 

    }
    

    
}

