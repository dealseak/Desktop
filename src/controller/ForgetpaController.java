/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.HeadlessException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import helpers.JavaMailUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import helpers.DbConnect;
import model.user;


/**
 * FXML Controller class
 *
 * @author user
 */
public class ForgetpaController implements Initializable {

      @FXML
    private TextField nomf;

    @FXML
    private TextField prenomf;

    @FXML
    private TextField emailf;

    @FXML
    private Button send;
    @FXML
    private TextField pass;
    /**
     * Initializes the controller class.
     */
    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    user student = null;
       @FXML
    private String send()  {
        String status = "Success";
         String mailf = emailf.getText();
         String prenof = prenomf.getText();
         String nof = nomf.getText();
         String pas = pass.getText();
        if(mailf.isEmpty() || prenof.isEmpty() || nof.isEmpty()) {
            
            status = "Error";
        } else {
            //query
            String sql = "SELECT `password` FROM `gestuser` Where nom = ? and prenom = ? and email = ?";
            //String sql = "UPDATE `gestuser` set password='"+pas+"' Where nom ='"+nof+"' and prenom = '"+prenof+"' and email = '"+mailf+"'";
            try {
                connection = DbConnect.getConnection();
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, nof);
                preparedStatement.setString(2, prenof);
                preparedStatement.setString(3, mailf);
                
               // preparedStatement.setString(4,getPassword());
                resultSet = preparedStatement.executeQuery();
              
                // String pass = resultSet.getString(1));
                if (!resultSet.next()) {
                   
                status = "Error";
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Entrez le bon e-mail / prénom / nom");
                alert.showAndWait();
                   // System.out.println("ok");
                }
                else{
                     preparedStatement.executeUpdate("UPDATE `gestuser` set password='"+doHashing(pas)+"' Where nom ='"+nof+"' and prenom = '"+prenof+"' and email = '"+mailf+"'");
                sendotp();}}
                catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
           return status;
        
    }  
   
   
  
   
    public static String doHashing (String resultSet) {
  try {
   MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
   byte[]  result = messageDigest.digest(resultSet.getBytes());
   StringBuilder sb = new StringBuilder();
   for(int i=0 ; i<result.length; i++){
       sb.append(Integer.toString((result[i] & 0xff) + 0x100,16).substring(1));
   }
   
   
  
   
   return sb.toString();
   
  } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
  }
  
    }

 public void sendotp() throws Exception {
     try{
         JavaMailUtil.sendMail("xxpcxx.ads@gmail.com","Mot de Passe changé","Mot de Passe changé");
         JOptionPane.showMessageDialog(null,"Mot de Passe changé");
     }catch(HeadlessException | MessagingException e)
     {
         JOptionPane.showMessageDialog(null,"S'il vous plait, vérifiez votre connexion internet");
     }
 }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          
              // TODOse
              send();
       
    }  
    
    }
