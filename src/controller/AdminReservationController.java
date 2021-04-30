/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import helpers.ConnexionSingleton;
import model.Reservation;
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
public class AdminReservationController implements Initializable {
    @FXML
    private TextField rechercheFld;
    @FXML
    private Button recherche;
    @FXML
    private TableView<Reservation> tbData1;
    @FXML
    private TextField Fld;
    @FXML
    private TableColumn<Reservation, String> AdultFld;

    @FXML
    private TableColumn<Reservation, String> ChildrenFld;

    @FXML
    private TableColumn<Reservation, String> nbjourFld;;

    @FXML
    private TableColumn<Reservation, String> nbchFld;

    @FXML
    private TableColumn<Reservation, String> ActionFld;
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Reservation reservation = null;

    ObservableList<Reservation> ResList = FXCollections.observableArrayList();

    @FXML
    private void refreshTab() {
        try {
            ResList.clear();

            query = " SELECT * FROM `reservation`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ResList.add(new Reservation(
                        resultSet.getInt("id"),
                        resultSet.getString("adult"),
                        resultSet.getString("children"),
                        resultSet.getString("nbjour"),
                        resultSet.getString("nbchambre")
          ));
                tbData1.setItems(ResList);

            }

        } catch (SQLException ex) {
            Logger.getLogger(AdminReservationController.class.getName()).log(Level.SEVERE, null, ex);
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
        refreshTab();

        AdultFld.setCellValueFactory(new PropertyValueFactory<>("adult"));
        ChildrenFld.setCellValueFactory(new PropertyValueFactory<>("children"));
        nbjourFld.setCellValueFactory(new PropertyValueFactory<>("nbjour"));
        nbchFld.setCellValueFactory(new PropertyValueFactory<>("nbchambre"));

        //add cell of button edit 
        Callback<TableColumn<Reservation, String>, TableCell<Reservation, String>> cellFoctory;
        cellFoctory = (TableColumn<Reservation, String> param) -> {
            // make cell containing buttons
            final TableCell<Reservation, String> cell;
            cell = new TableCell<Reservation, String>() {
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
                                reservation = tbData1.getSelectionModel().getSelectedItem();
                                query = "delete from `reservation` where adult  =" + reservation.getAdult();
        connection = ConnexionSingleton.getInstance().getCnx();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTab();

                            } catch (SQLException ex) {
                                Logger.getLogger(AdminReservationController.class.getName()).log(Level.SEVERE, null, ex);
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
        ActionFld.setCellFactory(cellFoctory);
        tbData1.setItems(ResList);

    }

@FXML
    private void searchRecord(KeyEvent ke) {
        //personList is table setter getter
        FilteredList<Reservation> filterData = new FilteredList<>(ResList, p -> true);
        Fld.textProperty().addListener((obsevable, oldvalue, newvalue) -> {
            filterData.setPredicate(res -> {

                if (newvalue == null || newvalue.isEmpty()) {
                    return true;
                }
                String typedText = newvalue.toLowerCase();
                if (res.getAdult().toLowerCase().indexOf(typedText) != -1) {

                    return true;
                }
                if (res.getChildren().toLowerCase().indexOf(typedText) != -1) {

                    return true;
                }
                if (res.getNbjour().toLowerCase().indexOf(typedText) != -1) {
                    return true;
                }
  if (res.getNbchambre().toLowerCase().indexOf(typedText) != -1) {
                    return true;
                }
    

    return false;
            });
            SortedList<Reservation> sortedList = new SortedList<>(filterData);
            sortedList.comparatorProperty().bind(tbData1.comparatorProperty());
            tbData1.setItems(sortedList);
                       
            
        });

    

}


 @FXML
   private void rechercheres() {

 try{
ResList.clear();
     query =" SELECT * FROM `reservation` where adult =?";
  preparedStatement = connection.prepareStatement(query);
  preparedStatement.setString(1, rechercheFld.getText());
          resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
                ResList.add(new Reservation(
                        resultSet.getInt("id"),
                        resultSet.getString("adult"),
                        resultSet.getString("children"),
                        resultSet.getString("nbjour"),
                        resultSet.getString("nbchambre")));
                tbData1.setItems(ResList);
                
            }
    }catch (SQLException ex) {
            Logger.getLogger(AdminReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }

     
    }
   private void backhome(MouseEvent event) throws IOException{
  Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/controller/views/homeAdmin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
}
}
