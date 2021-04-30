package controller;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import helpers.ConnexionSingleton;
import model.Payment;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class AdminPaymentController implements Initializable {

    @FXML
    private TableView<Payment> tbData;
    @FXML
    private TableColumn<Payment, String> firstNameFld;
    @FXML
    private TableColumn<Payment, String> lastNameFld;
    @FXML
    private TableColumn<Payment, String> EmailFld;
    @FXML
    private TableColumn<Payment, String> PhoneFld;

    @FXML
    private TableColumn<Payment, String> CardFld;
    @FXML
    private TableColumn<Payment, String> CardFld1;

    @FXML
    private TextField Fld;
    @FXML
    private TextField rechercheFld;
    @FXML
    private Button deletebnt;

    @FXML
    private Button recherche;
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Payment payment = null;

    ObservableList<Payment> PayList = FXCollections.observableArrayList();

    @FXML
    private void refreshTable() {
        try {
            PayList.clear();

            query = " SELECT * FROM `payment`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PayList.add(new Payment(
                        resultSet.getInt("id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),

                        resultSet.getString("card_number")));
                tbData.setItems(PayList);

            }

        } catch (SQLException ex) {
            Logger.getLogger(AdminPaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDate();

    }

    private void loadDate() {

                                connection = ConnexionSingleton.getInstance().getCnx();
        refreshTable();

        firstNameFld.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastNameFld.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        EmailFld.setCellValueFactory(new PropertyValueFactory<>("email"));
        PhoneFld.setCellValueFactory(new PropertyValueFactory<>("phone"));
        CardFld.setCellValueFactory(new PropertyValueFactory<>("card_number"));

        //add cell of button edit 
        Callback<TableColumn<Payment, String>, TableCell<Payment, String>> cellFoctory;
        cellFoctory = (TableColumn<Payment, String> param) -> {
            // make cell containing buttons
            final TableCell<Payment, String> cell;
            cell = new TableCell<Payment, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {

                        setGraphic(null);

                        setText(null);
                    } else {
                        Button button = new Button("supprimer");
                        button.setOnMouseClicked((value) -> {
                            try {
                                payment = tbData.getSelectionModel().getSelectedItem();
                                query = "delete from `payment` where id  =" + payment.getId();
                                connection = ConnexionSingleton.getInstance().getCnx();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(AdminPaymentController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });

                        HBox managebtn = new HBox(button);
                        setGraphic(managebtn);
                        setText(null);
                    }
                }

            };

            return cell;
        };
        CardFld1.setCellFactory(cellFoctory);
        tbData.setItems(PayList);

    }
@FXML
    private void searchRecord(KeyEvent ke) {
        //personList is table setter getter
        FilteredList<Payment> filterData = new FilteredList<>(PayList, p -> true);
        Fld.textProperty().addListener((obsevable, oldvalue, newvalue) -> {
            filterData.setPredicate(payment -> {

                if (newvalue == null || newvalue.isEmpty()) {
                    return true;
                }
                String typedText = newvalue.toLowerCase();
                if (payment.getFirstname().toLowerCase().indexOf(typedText) != -1) {

                    return true;
                }
                if (payment.getLastname().toLowerCase().indexOf(typedText) != -1) {

                    return true;
                }
                if (payment.getEmail().toLowerCase().indexOf(typedText) != -1) {
                    return true;
                }
  if (payment.getPhone().toLowerCase().indexOf(typedText) != -1) {
                    return true;
                }
    if (payment.getCard_number().toLowerCase().indexOf(typedText) != -1) {
                    return true;
                }

    return false;
            });
            SortedList<Payment> sortedList = new SortedList<>(filterData);
            sortedList.comparatorProperty().bind(tbData.comparatorProperty());
            tbData.setItems(sortedList);
                       
            
        });

    

}

     @FXML
    private void back(MouseEvent event) throws IOException{
  Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com.esprit.views/Adminreservation.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

 @FXML
   private void rechercheres() {

 try{
PayList.clear();
     query =" SELECT * FROM `payment` where firstname =?";
  preparedStatement = connection.prepareStatement(query);
  preparedStatement.setString(1, rechercheFld.getText());
          resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
                PayList.add(new Payment(
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("card_number")));

                tbData.setItems(PayList);
                
            }
    }catch (SQLException ex) {
            Logger.getLogger(AdminPaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }

     
    }

     @FXML
    private void home(MouseEvent event) throws IOException{
  Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/controller/views/homeAdmin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
}
}
