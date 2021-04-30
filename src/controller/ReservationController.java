/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.dao.ReservationDao;
import model.Reservation;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ReservationController implements Initializable {
    @FXML
    private Button btn;
    @FXML
    private TextField adultFld;
    @FXML
    private TextField childrenFld;
    @FXML
    private TextField jourFld;
    @FXML
    private TextField chambreFld;

     @FXML
    private void res(MouseEvent event) throws IOException{
  Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/controller/views/payement.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO     
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Reservation r;
                r = new Reservation(adultFld.getText(), childrenFld.getText(),jourFld.getText(),chambreFld.getText());
                ReservationDao rdao = ReservationDao.getInstance();
                rdao.insert(r);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Reservation");
                alert.setHeaderText(null);
                alert.setContentText("Reservation effectué avec succés!");
                alert.show();
                adultFld.setText("");
                childrenFld.setText("");
                jourFld.setText("");
                chambreFld.setText("");
            }
        });
        
        
    }
}
