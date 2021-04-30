/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.logging.Level;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import helpers.DbConnect;
import model.user;

/**
 * FXML Controller class
 *
 * @author user
 */
public class TableViewController implements Initializable {
    @FXML
    private TableView<user> table;

    @FXML
    private TableColumn<user, String> id;

    @FXML
    private TableColumn<user, String> nom;

    @FXML
    private TableColumn<user, String> prenom;

    @FXML
    private TableColumn<user, String> email;
  // @FXML
   
    //private TextField filterField;
    @FXML
    private TableColumn<user, String> password;
        @FXML
    private TableColumn<user, String> editCol;
        @FXML
    private Button refresh;
        
                @FXML
    private TextField txtrechercher;

    @FXML
    private Button btnrechercher;
       
 
     String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    user student = null ;
    
    ObservableList<user>  StudentList = FXCollections.observableArrayList();
   // ObservableList<user>  datalist = FXCollections.observableArrayList();

    @FXML
    private void getAddView(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/controller/views/edit.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    @FXML
    private void refreshTable() {
        try {
            StudentList.clear();
            
            query =" SELECT * FROM `gestuser`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                StudentList.add(new user(
                        resultSet.getInt("id"),
                        resultSet.getInt("role"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("email"),
                        resultSet.getString("password")));
                table.setItems(StudentList);
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    
    private void delete(int id){
    try{
         query="DELETE FROM `gestuser` WHERE `gestuser`.`id` ="+id ;
         connection = DbConnect.getConnection();
         preparedStatement = connection.prepareStatement(query);
         preparedStatement.execute();
         refreshTable();
                                
        } catch (SQLException ex) {
         Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDate();
                 
    }  
     private void loadDate() {
        
        connection = DbConnect.getConnection();
        refreshTable();
      //  delete(5);
        
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        
        //add cell of button edit 
         Callback<TableColumn<user, String>, TableCell<user, String>> cellFoctory;
        cellFoctory = (TableColumn<user, String> param) -> {
            // make cell containing buttons
            final TableCell<user, String> cell;
            cell = new TableCell<user, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                         
                        setGraphic(null);

                        setText(null);
                    }
                    else {
                        Button button = new Button("supprimer");
                        button.setOnMouseClicked((value) ->{
                        try{
                            student = table.getSelectionModel().getSelectedItem();
                            query = "DELETE FROM `gestuser` WHERE id  ="+student.getId();
                            connection = DbConnect.getConnection();
                            preparedStatement = connection.prepareStatement(query);
                            preparedStatement.execute();
                            refreshTable();
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    });
                        Button edit = new Button("modifier");
                        edit.setOnMouseClicked((value) ->{
                            
                            student = table.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("/controller/views/edit.fxml"));
                            try {
                                //  try {
                                loader.load();
                                //} catch (IOException ex) {
                                //  Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                                //}
                            } catch (IOException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            EditController addStudentController = loader.getController();
                            addStudentController.setUpdate(true);
                            addStudentController.setTextField(student.getId(), student.getNom(), 
                            student.getPrenom(),student.getEmail());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();
                            

                           

                        });
                    HBox managebtn = new HBox(button,edit);
                    setGraphic(managebtn);
                    
                    setText(null);
                    }
                }

            };

            return cell;
        };
         editCol.setCellFactory(cellFoctory);
         table.setItems(StudentList);
          }
    /* @FXML
    private void back(MouseEvent event) throws IOException{
  Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/login/login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }*/
    
    
    @FXML
   private void recherche() {

 try{
StudentList.clear();
     query =" SELECT * FROM `gestuser` where nom =?";
  preparedStatement = connection.prepareStatement(query);
  preparedStatement.setString(1, txtrechercher.getText());
          resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
                StudentList.add(new user(
                        resultSet.getInt("id"),
                         resultSet.getInt("role"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("email"),
                        resultSet.getString("password")));
                table.setItems(StudentList);
                
            }
    }catch (SQLException ex) {
            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

     
    }
    @FXML
    public void gest(javafx.scene.input.MouseEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/controller/views/homeAdmin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
   
}