package controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;
import helpers.DbConnect;
import javafx.scene.layout.AnchorPane;


public class UtilityController implements Initializable {

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField messageField;

    @FXML
    private Button insertButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;



    @FXML
    private void insertButton() {
        String query = "insert into reclam values("+idField.getText()+",'"+ nameField.getText()+"','"+ emailField.getText()+"',"+ phoneField.getText()+","+ messageField.getText()+")";
        executeQuery(query);

    }


    @FXML
    private void updateButton() {
        String query = "UPDATE reclam SET name='"+ nameField.getText()+"',email='"+ emailField.getText()+"',phone="+ phoneField.getText()+",message="+ messageField.getText()+" WHERE ID="+idField.getText()+"";
        executeQuery(query);

    }

    @FXML
    private void deleteButton() {
        String query = "DELETE FROM reclam WHERE ID="+idField.getText()+"";
        executeQuery(query);

    }

    public void executeQuery(String query) {
        Connection conn = DbConnect.getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public static void setRoot(AnchorPane pane) {
        try {
            Parent parent = FXMLLoader.load(UtilityController.class.getResource("/controller/views/insertReclam.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
