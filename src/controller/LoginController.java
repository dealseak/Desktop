/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import helpers.JavaMailUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.mail.MessagingException;
import javax.swing.JOptionPane;
import helpers.DbConnect;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import model.user;

/**
 * FXML Controller class
 *
 * @author user
 */
public class LoginController implements Initializable {
 @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    public TextField email,verif;

    @FXML
    private TextField password;
     
     @FXML
    private TextField emaill;

    @FXML
    private PasswordField passwordd;

    @FXML
    private Button signi,signu,save;
     @FXML
     
     private TextField hide;
         @FXML
    private PasswordField password1;
               @FXML
    private Button btn;
                  @FXML
    private Hyperlink forget;
    private static int workload = 12;


   

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    user student = null;
    
       @FXML
    private void forget(ActionEvent event)throws SQLException {

    
       if (event.getSource() == forget) {
           try {
               Parent root = FXMLLoader.load(getClass().getResource("/controller/views/forgetpa.fxml"));
               Scene scene = new Scene(root);
               Stage stage = new Stage();
               Image icon = new Image("assets/logo.png");
               stage.getIcons().add(icon);
               JMetro jMetro = new JMetro(Style.LIGHT);
               jMetro.setScene(scene);
               stage.setScene(scene);
               stage.show();
           } catch (IOException e) {
               e.printStackTrace();
           }

       }

        
    }
   @FXML
     private void signup() throws SQLException {
   
    query = "INSERT INTO `gestuser`( `nom`,`prenom`, `email`,`password`,`role`) VALUES (?,?,?,?,0)";
         connection = DbConnect.getConnection();
        String passwor= password.getText();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nom.getText());
            preparedStatement.setString(2, prenom.getText());
            preparedStatement.setString(3, email.getText());
            // preparedStatement.setString(4, password.getText());
           preparedStatement.setString(4, doHashing(passwor));
           // preparedStatement.setString(4, getSecurePassword(password));
              
            preparedStatement.execute();
         /* String status = "Success";
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("le compte a éte bien crée");
                alert.showAndWait();*/
            
              nom.setText("");
              prenom.setText("");
              email.setText("");
              password.setText("");
              password1.setText("");
              hide.setText("");}
    
    
   
    /* @FXML
       private void signup() throws SQLException {
           Random();
           sendotp();
       }*/

    /**
     *
     * @param event
     */

    @FXML
    
    private void handleButtonAction(ActionEvent event) throws Exception {

    
       if (event.getSource() == btn) {
           Random();
           sendotp();
       }
       //si le hide egale le zone de text donc ajouter le compte
       else if (event.getSource()== save){
                if(hide.getText().equals(verif.getText()) && password.getText().equals(password1.getText())){
                    signup();
                }
                else{
                     JOptionPane.showMessageDialog(null,"le mot de passe ou le code de confirmation est incorrect");
                }
           
       }
    }

 @FXML
   private String signin() {
        String status = "Success";
        String mail = emaill.getText();
        String pass = passwordd.getText();
        if(mail.isEmpty() || pass.isEmpty()) {
            
            status = "Error";
        } else {
            //query
           
           
            try {
                    String sql = "SELECT * FROM `gestuser` Where email = ? and password = ? and role=1";
                    
                  // String sq3 = "SELECT role FROM `gestuser` Where  role=1";
                connection = DbConnect.getConnection();
                preparedStatement = connection.prepareStatement(sql);
                  //preparedStatement = connection.prepareStatement(sq3);
                preparedStatement.setString(1, mail);
                preparedStatement.setString(2,doHashing(pass));
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                   
                status = "Error";
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Entrez le bon e-mail / mot de passe");
                alert.showAndWait();
                   // System.out.println("ok");
                } else   {





                    //System.out.println("okdf,dfdf");

                     FXMLLoader loader = new FXMLLoader ();
                     loader.setLocation(getClass().getResource("/controller/views/homeAdmin.fxml"));
                     try {
                     Object load;
                     load = loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();
                }
             
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
            
            
            
               try {
                   String sq2 = "SELECT * FROM `gestuser` Where email = ? and password = ? and role=0";
                connection = DbConnect.getConnection();
                preparedStatement = connection.prepareStatement(sq2);
                preparedStatement.setString(1, mail);
                preparedStatement.setString(2, doHashing(pass));
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                   
                status = "Error";
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Entrez le bon e-mail / mot de passe");
                alert.showAndWait();
                   // System.out.println("ok");
                } else  {

                    //System.out.println("okdf,dfdf");
                   
                     FXMLLoader loader = new FXMLLoader ();
                     loader.setLocation(getClass().getResource("/controller/views/profil.fxml"));
                     try {
                     Object load;
                     load = loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();
                }
             
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
                    
                    
             
           
            
        }
        
        
        return status;
    }

    private void setLblError(Color TOMATO, String empty_credentials) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //signup();
        signin();
    }    

    
    
//envoyer nombre aléatoire
 public void Random(){
        Random rd=new Random();
        hide.setText(""+rd.nextInt(10000+1));
        
    }
 //envoyer le nombre par mail
    public void sendotp() throws Exception {
        try{
        JavaMailUtil.sendMail("xxpcxx.ads@gmail.com","MailButton",hide.getText());
            JOptionPane.showMessageDialog(null,"le code de confirmation sera envoyée a votre email");
        }catch(HeadlessException | MessagingException e)
        {
            JOptionPane.showMessageDialog(null,"S'il vous plait, vérifiez votre connexion internet");
        }
    }
    
    
    
 


	/**
	  * A simple test case for the main method, verify that a pre-generated test hash verifies successfully
	  * for the password it represents, and also generate a new hash and ensure that the new hash verifies
	  * just the same.
     * @param password
     * @return 
	  */
        
/*public static String doHashing (TextField password) {
  try {
   MessageDigest messageDigest = MessageDigest.getInstance("MD5");
   byte[]  MessageDigest = messageDigest.digest();
   BigInteger number = new BigInteger(1, MessageDigest);
   
   
   String hashtext = number.toString(16);
   while(hashtext.length()<32){
   hashtext= "0" +hashtext;
   }
   
   return hashtext;
   
  } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
  }
  

 }*/
    public static String doHashing (String password) {
  try {
   MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
   byte[]  result = messageDigest.digest(password.getBytes());
   StringBuffer sb = new StringBuffer();
   for(int i=0 ; i<result.length; i++){
       sb.append(Integer.toString((result[i] & 0xff) + 0x100,16).substring(1));
   }
   
   
  
   
   return sb.toString();
   
  } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
  }
  

 }
 @FXML
 private void acc(MouseEvent event) throws IOException {
     Node node = (Node) event.getSource();
     Stage stage = (Stage) node.getScene().getWindow();
     Parent root = FXMLLoader.load(getClass().getResource("/controller/views/Accueil.fxml"));
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
 }
    
    
    
}
 