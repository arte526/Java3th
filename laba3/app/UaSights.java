package laba3.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class UaSights extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception{
        Parent root ;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FormSights.fxml")));
        stage.setTitle("Завантаження фотографії");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
