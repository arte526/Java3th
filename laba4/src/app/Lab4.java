package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Lab4 extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        // Opening graphics Laba4.fxml
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Laba4.fxml")));
        stage.setTitle("");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
