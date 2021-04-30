package controller;
import helpers.DbConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class insertReclamController implements Initializable {
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private TextField phone;
    @FXML
    private TextArea message;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    String query = null;
    Connection connection = DbConnect.getConnection();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    private void insert(ActionEvent event) throws SQLException {

        if(name.getText().equals("") || email.getText().equals("") || phone.getText().equals("") || message.getText().equals("")){
           popupShow("Remplir Tous les champs");
        }
        else{
            try {
                int num=Integer.parseInt(phone.getText());
            }catch (NumberFormatException nfe){
                popupShow("Champ Phone Invalid");
            }
            query="SELECT id FROM `reclam`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if(id.getText()!=""){
            int i=Integer.parseInt(id.getText());
            int num=Integer.parseInt(phone.getText());
            if(!findByID(resultSet, i)){
                popupShow("ID Existant");
            }
            else{
                String stmt = "INSERT INTO reclam VALUES ("+i+",'"+name.getText()+"','"+email.getText()+"',"+num+",'"+message.getText()+"',"+false+")";
                executeQuery(stmt);
                closeButtonAction();
            }}else {
                int num=Integer.parseInt(phone.getText());
                String stmt = "INSERT INTO reclam VALUES ("+null+",'"+name.getText()+"','"+email.getText()+"',"+num+",'"+message.getText()+"',"+false+")";
                executeQuery(stmt);
                closeButtonAction();}
        }
    }

    private void executeQuery(String stmt) {
        Connection connection = DbConnect.getConnection();
        Statement st;
        try {
            st = connection.createStatement();
            st.executeUpdate(stmt);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    void setTextField( String ido ,String nom, String msg , String mail, String num) {
        id.setText(ido);
        name.setText(nom);
        message.setText(msg);
        email.setText(mail);
        phone.setText(num);


    }


    @FXML private javafx.scene.control.Button closeButton;

    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();

    }

    private boolean findByID(ResultSet resultSet,Integer integer) throws SQLException {
        while(resultSet.next()){
            if (resultSet.getString(1).contains(integer.toString())){
                return false;
            }
        }
        return true;
    }



    private void popupShow(String s){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        Label label = new Label(s);
        label.setStyle(" -fx-background-color: #f60202 ;-fx-font-size: 16;");
        Popup popup = new Popup();
        popup.getContent().add(label);
        popup.setAutoHide(true);
        popup.show(stage);
    }

}
