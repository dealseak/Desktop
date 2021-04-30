package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccueilController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    private void Actreclam(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/controller/views/insertReclam.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            javafx.scene.image.Image icon = new Image("assets/logo.png");
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
    private void Actres(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/controller/views/Reservation.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        javafx.scene.image.Image icon = new Image("assets/logo.png");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void log(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/controller/views/login.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        javafx.scene.image.Image icon = new Image("assets/logo.png");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

}
