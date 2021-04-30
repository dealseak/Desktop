package controller;
import helpers.DbConnect;
import helpers.JavaMailUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class updateReclamController implements Initializable {
    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private TextField phone;
    @FXML
    private TextArea message;
    @FXML
    private ChoiceBox res;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] st = { "True", "False" };
        res.getItems().addAll(FXCollections.observableArrayList(st));
    }

    @FXML private javafx.scene.control.Button saveButton;

    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void update(ActionEvent event) throws Exception {
            nullQuery();
            if(res.getValue().toString().equals("True")){

                JavaMailUtil.sendMail(email.getText(),"Reclamation Résolu",message.getText());
            }

            try {
                int num=Integer.parseInt(phone.getText());
            }catch (NumberFormatException nfe){
                popupShow("Champ Phone Invalid");
            }

            int num=Integer.parseInt(phone.getText());

            Stage stage = (Stage) saveButton.getScene().getWindow();
            String[] title = stage.getTitle().split("-");
            int idd = Integer.parseInt(title[0]);

            String query ="UPDATE reclam SET name='"+name.getText()+
                    "',email='"+email.getText()+
                    "',phone="+num+
                    ",message='"+message.getText()+
                    "',resolu='" +resVal(res.getValue().toString())+
                    "' WHERE id = "+idd;

            executeQuery(query);

            closeButtonAction();
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

    private int resVal (String s){
        if (s.equals("True"))
            return 1;
        else
            return 0;
    }

    private void popupShow(String s){
        Stage stage = (Stage) saveButton.getScene().getWindow();
        Label label = new Label(s);
        label.setStyle(" -fx-background-color: #f60202 ;-fx-font-size: 16;");
        Popup popup = new Popup();
        popup.getContent().add(label);
        popup.setAutoHide(true);
        popup.show(stage);
    }

    private void nullQuery() throws SQLException {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        String[] title = stage.getTitle().split("-");

        int idd = Integer.parseInt(title[0]);
        String query = "SELECT * FROM `reclam` WHERE id="+idd;
        Connection connection = DbConnect.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        if(name.getText().equals("")){
            String n = resultSet.getString(2);
            name.setText(n);
        }
        if(email.getText().equals("")){
            String e = resultSet.getString(3);
            email.setText(e);
        }
        if(phone.getText().equals("")){
            String p = resultSet.getString(4);
            phone.setText(p);
        }
        if(message.getText().equals("")){
            String m = resultSet.getString(5);
            message.setText(m);
        }
        if(res.getValue()==null){
            String r = resultSet.getString(6);
            res.setValue(resVal(r));
        }

    }

}
