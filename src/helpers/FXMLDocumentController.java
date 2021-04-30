/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;


import controller.TableViewController;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.user;

/**
 *
 * @author user
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
      @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Button signup;

    @FXML
    private TextField mail;

    @FXML
    private PasswordField pass;

    @FXML
    private Button signin;
    
 String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    user student = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
  @FXML
   private void signin() {

    }

    @FXML
    private String signup() {
          
     try{
        query = "INSERT INTO `gestuser`( `nom`,`prenom`, `email`,`password`) VALUES (?,?,?,?)";
         connection = DbConnect.getConnection();
         preparedStatement = connection.prepareStatement(query);
         preparedStatement.execute();
        
                                
        } catch (SQLException ex) {
   System.out.println(ex.getMessage());
       
            return "Exception";        
         
        
}       return null;
}

      
    }