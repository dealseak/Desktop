/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.logging.Level;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import helpers.DbConnect;
import model.user;

/**
 * FXML Controller class
 *
 * @author user
 */
public class EditController implements Initializable {

  @FXML
    private TextField nome;

    @FXML
    private TextField prenome;

    @FXML
    private TextField emaile;
         @FXML
    private Button edite;
           @FXML
    private Label label;
     String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    user student = null;
    private boolean update;
        int studentId;

           @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  

    @FXML
    private void edit() {
         connection = DbConnect.getConnection();
        String nom = nome.getText();
        String prenom = prenome.getText();
        String email = emaile.getText();

        if (nom.isEmpty()  || prenom.isEmpty() || email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();

        } else {
            getQuery();
            insert();
            
   
        }
        System.out.println("c bon");
        label.setText("le compte a été changer");

    }

    private void getQuery() {
  if (update == false) {
            
            query = "INSERT INTO `gestuser`( `nom`,`prenom`, `email`) VALUES (?,?,?)";

        }else{
            query = "UPDATE `gestuser` SET "
                    + "`nom`=?,"
                    + "`prenom`=?,"
                    + "`email`= ? WHERE id = '"+studentId+"'";
        }
    }

    private void insert() {
     try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nome.getText());
            preparedStatement.setString(2, prenome.getText());
            preparedStatement.setString(3, emaile.getText());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(EditController.class.getName()).log(Level.SEVERE, null, ex);
        }

    


    }
 
    
      void setTextField(int id, String nom, String prenom , String email) {

        studentId = id;
        nome.setText(nom);
        prenome.setText(prenom);
        emaile.setText(email);

    }

      void setUpdate(boolean b) {
        this.update = b;

    }

  

  
    
}
