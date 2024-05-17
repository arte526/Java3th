package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class modLaba1 extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root ;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/laba2Form.fxml")));
        stage.setScene(new Scene(root));
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}