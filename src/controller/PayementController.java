/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;



import java.awt.HeadlessException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

import controller.dao.PaymentDao;
import helpers.JavaMailUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Payment;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class PayementController implements Initializable {
    @FXML
    private TextField hide;
    @FXML
    private Button save;

    @FXML
    private Button btn;
    @FXML
    private TextField firstnameFld;
    @FXML
    private TextField emailFld;
    @FXML
    private TextField cardFld, verif;
    @FXML
    private TextField lastnameFld;
    @FXML
    private TextField phoneFld;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     *
     * @param event
     */

    @FXML

    private void pay(ActionEvent event) throws Exception {

        if (event.getSource() == btn) {
            Random();
            sendotp();
        }
        //si le hide egale le zone de text donc ajouter le compte
        else if (event.getSource()== save){
            if(hide.getText().equals(verif.getText())){
                // TODO


                Payment p = new Payment(firstnameFld.getText(), lastnameFld.getText(), emailFld.getText(), phoneFld.getText(), cardFld.getText());
                PaymentDao pdao = PaymentDao.getInstance();
                pdao.insert(p);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Paiment effectué avec succés!");
                alert.show();
                firstnameFld.setText("");
                lastnameFld.setText("");
                emailFld.setText("");
                phoneFld.setText("");
                cardFld.setText("");



            }
            else{
                JOptionPane.showMessageDialog(null,"le mot de passe ou le code de confirmation est incorrect");
            }

        }
    }
    public void Random(){
        Random rd=new Random();
        hide.setText(""+rd.nextInt(10000+1));

    }
    //envoyer le nombre par mail
    public void sendotp() throws Exception {

            JavaMailUtil.sendMail("xxpcxx.ads@gmail.com","MailButton",hide.getText());

    }

}
