package controller;

import helpers.DbConnect;
import helpers.JavaMailUtil;
import helpers.Pdf;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import model.Reclamation;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
public class showReclamController implements Initializable  {
    @FXML
    private TextField filterField;
    @FXML
    private TableView<Reclamation> reclamTable;
    @FXML
    private TableColumn<Reclamation, String> idCol;
    @FXML
    private TableColumn<Reclamation, String> nameCol;
    @FXML
    private TableColumn<Reclamation, String> emailCol;
    @FXML
    private TableColumn<Reclamation, String> phoneCol;
    @FXML
    private TableColumn<Reclamation, String> messageCol;
    @FXML
    private TableColumn<Reclamation, String> resCol;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    ObservableList<Reclamation> reclamationList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        loadDate();
        recherche();
    }


    private void recherche(){
        FilteredList<Reclamation> filteredData = new FilteredList<>(reclamationList, b -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(reclamation -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (reclamation.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (reclamation.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (String.valueOf(reclamation.getId()).contains(lowerCaseFilter))
                    return true;
                else
                    return false;
            });
        });
        SortedList<Reclamation> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(reclamTable.comparatorProperty());
        reclamTable.setItems(sortedData);
    }


    private void loadDate() {
        connection = DbConnect.getConnection();
        refreshTable();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        messageCol.setCellValueFactory(new PropertyValueFactory<>("message"));
        resCol.setCellValueFactory(new PropertyValueFactory<>("resolu"));

    }

    @FXML
    public void refreshTable(){

        try {
            reclamationList.clear();
            query="SELECT * FROM `reclam`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                reclamationList.add(new Reclamation(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getInt("phone"),
                        resultSet.getString("message"),
                        resultSet.getBoolean("resolu")
                ));
                reclamTable.setItems(reclamationList);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @FXML
    public void insertButton() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/controller/views/insertReclam.fxml"));
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

    @FXML
    private void delete() throws SQLException {
        Reclamation reclamation = reclamTable.getSelectionModel().getSelectedItem();
        query = "DELETE FROM `reclam` WHERE id  ="+reclamation.getId();
        connection = DbConnect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        preparedStatement.execute();
        refreshTable();
    }


    @FXML
    private void updateButton(){
        int reclamation = reclamTable.getSelectionModel().getSelectedItem().getId();
        boolean SelRes = reclamTable.getSelectionModel().getSelectedItem().isResolu();
        String SelRest ;
        if (SelRes){SelRest="true";}else{SelRest="false";}
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/controller/views/updateReclam.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            Image icon = new Image("assets/logo.png");
            stage.getIcons().add(icon);
            String s = Arrays.toString(new String[]{Integer.toString(reclamation) + "-" + SelRest});
            String a = s.replace("[","");
            String b = a.replace("]","");
            stage.setTitle(b);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void chartBuilder() throws SQLException {
        String query = "SELECT resolu FROM `reclam`";
        preparedStatement = DbConnect.getConnection().prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
        PreparedStatement preparedStatement1 = DbConnect.getConnection().prepareStatement(query);
        ResultSet resultSet1 = preparedStatement1.executeQuery();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Réclamations Résolus", countRes(resultSet)),
                new PieChart.Data("Réclamations Non Résolus", countNRes(resultSet1)));
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Résolution des réclamations");
        pieChart.setClockwise(true);
        pieChart.setLabelLineLength(50);
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(180);
        Group root = new Group(pieChart);

        Scene scene = new Scene(root, 600, 400);

        Stage stage = new Stage();
        stage.setTitle("Pie chart");
        stage.setScene(scene);
        stage.show();
    }
    private int countRes(ResultSet resultSet) throws SQLException {
        int i = 0;
        while(resultSet.next()){
            if(resultSet.getString(1).contains("1")){
                i++;
            }
        }
        return i;
    }

    private int countNRes(ResultSet resultSet) throws SQLException {
        int j = 0;
        while(resultSet.next()){
            if(resultSet.getString(1).contains("0")){
                j++;
            }
        }
        return j;
    }

    @FXML
    public void mailButton(MouseEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/controller/views/homeAdmin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void gg(){
        Pdf.generate("test","TEST","JECER");
    }

}
