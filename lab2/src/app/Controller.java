package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Controller {
    @FXML
    public AnchorPane main;
    @FXML
    public Label city1Label;
    @FXML
    public Label city1Label2;
    @FXML
    public Label city1latLabel;
    @FXML
    public Label city1latLabel2;
    @FXML
    public Label city1longlabel;
    @FXML
    public Label city2longlabel;
    @FXML
    public Label labelresalt;
    @FXML
    public TextField TextFieldlat1;
    @FXML
    public TextField TextFieldlat2;
    @FXML
    public TextField TextFieldlong2;
    @FXML
    public TextField TextFieldlong1;
    @FXML
    public TextField TextFieldresalt;
    @FXML
    private Button clearButton;
    @FXML
    public Button calcbutton;
    @FXML
    private void calculateDistance(ActionEvent event) {
        try {
        double lat1 = Double.parseDouble(TextFieldlat1.getText());
        double lon1 = Double.parseDouble(TextFieldlong1.getText());
        double lat2 = Double.parseDouble(TextFieldlat2.getText());
        double lon2 = Double.parseDouble(TextFieldlong2.getText());

        Model model1 = new Model( lat1, lon1);
        Model model2 = new Model( lat2, lon2);

        double distance = model1.calculateDistance(model2);

        //TextFieldresalt.setText(String.valueOf(distance));
            TextFieldresalt.setText(String.format("%.2f км", distance)); // Вивід результату у кілометрах з округленням до двох знаків після коми

        } catch (NumberFormatException e) {
            System.err.println("Invalid input format!"); // Повідомлення про помилку у форматі введення
        }
    }
    @FXML
    public void initialize() {
        TextFieldresalt.setEditable(false);
    }
    @FXML
    private void clearFields(ActionEvent event) {
        TextFieldlat1.clear();
        TextFieldlong1.clear();
        TextFieldlat2.clear();
        TextFieldlong2.clear();
        TextFieldresalt.clear();
    }
}
