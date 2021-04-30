package operationable;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class OperationTable extends Application {

    public void start(Stage primaryStage){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/controller/views/login.fxml"));
            Scene scene = new Scene(parent);
            Image icon = new Image("assets/logo.png");
            primaryStage.getIcons().add(icon);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
