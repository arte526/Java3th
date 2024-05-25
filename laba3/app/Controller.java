package laba3.app;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.sql.*;

import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;




import java.io.File;
import java.util.Optional;

import static javafx.scene.control.SelectionMode.MULTIPLE;

public class Controller {
    @FXML
    public TableColumn<Model, Integer> idColumn;
    @FXML
    public TableColumn<Model, String> nameColumn;
    @FXML
    public TableColumn<Model, Double> latitudeColumn;
    @FXML
    public TableColumn<Model, Double> longitudeColumn;
    @FXML
    public TableColumn<Model, String> regionColumn;

    @FXML
    public AnchorPane main;
    @FXML
    public AnchorPane leftPane;
    @FXML
    public Label idLabel;
    @FXML
    public TextField IdTextField;
    @FXML
    public TextField nameTextField;
    @FXML
    public Label latLabel;

    @FXML
    public Label longlabel;
    @FXML
    public Label regionLabel;
    @FXML
    public Label fotoLabel;

    @FXML
    public Button insertButton;
    @FXML
    public Button updateButton;
    @FXML
    public Button clearbutton;
    @FXML
    public Button deletebutton;
    @FXML
    public AnchorPane rightPane;
    @FXML
    public ComboBox<String> regionComboBox;
    public Label nameLabel;
    public TableView<Model> BDTable;
    public ImageView ImageView;
    public Button uploadButton;
    public Button printButton;
    public Button cancelButton;
    public TableColumn PictureColumn;
    public TextField latitudeTextField;
    public TextField longitudeTextField;
    public Button EnterClearButton;

    @FXML
    public void initialize() {
        cancelButton.setVisible(false); // Зробити кнопку скасування невидимою при ініціалізації
        // Налаштування стовпців таблиці BDTable
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nameSight"));
        latitudeColumn.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        longitudeColumn.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        PictureColumn.setCellValueFactory(new PropertyValueFactory<>("picture"));
        BDTable.getSelectionModel().setSelectionMode(MULTIPLE);
        IdTextField.setDisable(true);
        IdTextField.setStyle("-fx-opacity: 1;");
        BDTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                handleTableSelectionFillFields();
            }
        });
        try {
            // Підключення до бази даних
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","1313");
            Statement st = con.createStatement();

            // Виконання запиту для отримання даних з таблиці
            String query = "SELECT name FROM regions";
            ResultSet resultSet = st.executeQuery(query);

            // Отримання даних та додавання їх до комбо боксу
            ObservableList<String> regionNames = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String regionName = resultSet.getString("name");
                regionNames.add(regionName);
            }

            // Додавання даних до комбо боксу
            regionComboBox.getItems().addAll(regionNames);

            // Закриття з'єднання з базою даних
            st.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @FXML
    private String photoPath;
    @FXML
    public void uploadPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Оберіть фотографію");
        fileChooser.setInitialDirectory(new File("D:/Java2/untitled/src/laba3/app/Photo")); // Встановлюємо початкову директорію
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Зображення", "*.jpg", "*.png", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            ImageView.setImage(image);
            photoPath = selectedFile.getAbsolutePath(); // Зберігаємо шлях до вибраного файл
            uploadButton.setVisible(false);
            uploadButton.setDisable(true); // Робимо кнопку завантаження неактивною
            cancelButton.setDisable(false); // Робимо кнопку скасування активною
            cancelButton.setVisible(true); // Зробити кнопку скасування невидимою при ініціалізації
        }
    }
    @FXML
    public void cancelPhotoSelection() {
        ImageView.setImage(null); // Очищаємо зображення у ImageView
        photoPath = ""; // Очищаємо шлях до фотографії
        uploadButton.setDisable(false); // Робимо кнопку завантаження активною
        cancelButton.setDisable(true); // Робимо кнопку скасування неактивною
        uploadButton.setVisible(true);
        cancelButton.setVisible(false);
    }

    @FXML
    private void handlePrintButton() {
        try {

            // Встановлюємо з'єднання з базою даних
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","1313");
            Statement st = con.createStatement();
            String query = "SELECT id, name, latitude, longitude, region, photo_path FROM sights";
            ResultSet resultSet = st.executeQuery(query);

            // Отримуємо дані з бази даних і додаємо їх до ObservableList
            ObservableList<Model> dataList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String nameSight = resultSet.getString("name");
                double latitude = resultSet.getDouble("latitude");
                double longitude = resultSet.getDouble("longitude");
                String region = resultSet.getString("region");
                String picture = resultSet.getString("photo_path");
                System.out.println(id + ", " + nameSight + ", " + latitude + ", " + longitude + ", " + region + ", " + picture); // Додайте цей рядок для перевірки
                Model model = new Model(id, nameSight, latitude, longitude, region, picture);
                dataList.add(model);
            }
// Перевірка кількості стовпців
            System.out.println("Кількість стовпців у BDTable: " + BDTable.getColumns().size());
            // Очищаємо таблицю перед додаванням нових даних
            BDTable.getItems().clear();
            // Додаємо дані до таблиці
            System.out.println("Кількість елементів у dataList: " + dataList.size());

            BDTable.setItems(dataList);
// Перевірка, чи dataList містить дані
            if (dataList.isEmpty()) {
                System.out.println("Список dataList порожній.");
            } else {
                System.out.println("Список dataList містить дані.");
            }

            // Закриваємо з'єднання з базою даних
            st.close();
            con.close();

            System.out.println("Кількість елементів у dataList: " + dataList.size());
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    private void handleClearTable() {
        // Очищення таблиці BDTable
        BDTable.getItems().clear();

        // Створення нового порожнього списку даних та встановлення його для таблиці
        ObservableList<Model> emptyList = FXCollections.observableArrayList();
        BDTable.setItems(emptyList);

    }

    @FXML
    private void handleInsertButton() {
        try {
            // Підключення до бази даних
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","1313");

            // Підготовка SQL-запиту для вставки даних
            String query = "INSERT INTO sights (name, latitude, longitude, region, photo_path) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Отримання даних з текстових полів та зображення
            String name = nameTextField.getText();
            double latitude = Double.parseDouble(latitudeTextField.getText());
            double longitude = Double.parseDouble(longitudeTextField.getText());
            String region = regionComboBox.getValue();
            String photoPath = this.photoPath;// Отримайте шлях до зображення з ImageView

            // Встановлення параметрів для SQL-запиту
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, latitude);
            preparedStatement.setDouble(3, longitude);
            preparedStatement.setString(4, region);
            preparedStatement.setString(5, photoPath);

            // Виконання SQL-запиту
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Дані успішно вставлено в базу даних!");
            }

            // Закриття підключення до бази даних
            preparedStatement.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        handlePrintButton();
    }

    @FXML
    private void handleDeleteButton() {
        try {
            // Отримання виділених рядків з таблиці
            ObservableList<Model> selectedModels = BDTable.getSelectionModel().getSelectedItems();
            if (selectedModels.isEmpty()) {
                // Якщо жоден рядок не вибрано, виводимо повідомлення
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("No rows selected");
                alert.setContentText("Please select rows to delete.");
                alert.showAndWait();
                return;
            }
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmation");
            confirmAlert.setHeaderText("Delete Confirmation");
            confirmAlert.setContentText("Ви впевнені, що бажаєте видалити обрані рядки?");
            Optional<ButtonType> result = confirmAlert.showAndWait();

            // Якщо користувач підтверджує видалення, виконуємо видалення
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Підключення до бази даних
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1313");

                // Підготовка SQL-запиту для вилучення даних
                String query = "DELETE FROM sights WHERE id=?";
                PreparedStatement preparedStatement = con.prepareStatement(query);

                // Видалення кожного виділеного рядка
                for (Model selectedModel : selectedModels) {
                    preparedStatement.setInt(1, selectedModel.getId());
                    preparedStatement.executeUpdate();
                }

                // Очищення виділених рядків з таблиці
                BDTable.getItems().removeAll(selectedModels);

                // Закриття підключення до бази даних
                preparedStatement.close();
                con.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        handleTableSelection();



    }

    @FXML
    private void handleTableSelection() {
        // Перевірка, чи є виділений рядок
        if (BDTable.getSelectionModel().getSelectedItem() != null) {
            // Зняття виділення з виділеного рядка
            BDTable.getSelectionModel().clearSelection();
        }

    }

    @FXML
    private void handleClearFieldsButton() {
        // Очищення текстових полів вводу
        IdTextField.clear();
        nameTextField.clear();
        latitudeTextField.clear();
        longitudeTextField.clear();
        // Очищення комбо-боксу
        regionComboBox.getSelectionModel().clearSelection();
        // Очищення зображення у ImageView
        ImageView.setImage(null);
        // Очищення шляху до фото
        cancelPhotoSelection();
    }

    @FXML
    private void handleTableSelectionFillFields() {
        // Отримуємо виділений рядок з таблиці
        Model selectedModel = BDTable.getSelectionModel().getSelectedItem();

        if (selectedModel != null) {
            // Отримання даних з обраного рядка
            int id = selectedModel.getId();
            String name = selectedModel.getNameSight();
            double latitude = selectedModel.getLatitude();
            double longitude = selectedModel.getLongitude();
            String region = selectedModel.getRegion();
            String picture = selectedModel.getPicture();

            // Встановлення отриманих даних у текстові поля для редагування
            IdTextField.setText(String.valueOf(id));
            nameTextField.setText(name);
            latitudeTextField.setText(String.valueOf(latitude));
            longitudeTextField.setText(String.valueOf(longitude));
            regionComboBox.setValue(region);

            // Відображення зображення у полі картинки
            if (picture != null && !picture.isEmpty()) {
                File imageFile = new File(picture);
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    photoPath = imageFile.getAbsolutePath(); // Зберігаємо шлях до вибраного файл
                    uploadButton.setVisible(false);
                    uploadButton.setDisable(true); // Робимо кнопку завантаження неактивною
                    cancelButton.setDisable(false); // Робимо кнопку скасування активною
                    cancelButton.setVisible(true); // Зробити кнопку скасування невидимою при ініціалізації
                    ImageView.setImage(image);
                } else {
                    System.out.println("Файл зображення не знайдено: " + picture);
                    ImageView.setImage(null); // Очищаємо зображення у ImageView
                    photoPath = ""; // Очищаємо шлях до фотографії
                    uploadButton.setDisable(false); // Робимо кнопку завантаження активною
                    cancelButton.setDisable(true); // Робимо кнопку скасування неактивною
                    uploadButton.setVisible(true);
                    cancelButton.setVisible(false);
                }
            } else {
                System.out.println("Шлях до зображення не вказано або порожній.");
                ImageView.setImage(null); // Очищаємо зображення у ImageView
                photoPath = ""; // Очищаємо шлях до фотографії
                uploadButton.setDisable(false); // Робимо кнопку завантаження активною
                cancelButton.setDisable(true); // Робимо кнопку скасування неактивною
                uploadButton.setVisible(true);
                cancelButton.setVisible(false);
            }
        }
    }

    @FXML
    private void handleUpdateButton() {
        try {
            // Отримання обраного рядка з таблиці
            Model selectedModel = BDTable.getSelectionModel().getSelectedItem();
            if (selectedModel == null) {
                // Якщо рядок не обрано, виводимо повідомлення
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("No row selected");
                alert.setContentText("Please select a row to update.");
                alert.showAndWait();
                return;
            }

            // Підключення до бази даних
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","1313");

            // Підготовка SQL-запиту для оновлення даних
            String query = "UPDATE sights SET name=?, latitude=?, longitude=?, region=?, photo_path=? WHERE id=?";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Отримання даних з текстових полів та зображення
            String name = nameTextField.getText();
            double latitude = Double.parseDouble(latitudeTextField.getText());
            double longitude = Double.parseDouble(longitudeTextField.getText());
            String region = regionComboBox.getValue();
            String photoPath = this.photoPath; // Отримайте шлях до зображення з ImageView
            int id = selectedModel.getId();

            // Встановлення параметрів для SQL-запиту
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, latitude);
            preparedStatement.setDouble(3, longitude);
            preparedStatement.setString(4, region);
            preparedStatement.setString(5, photoPath);
            preparedStatement.setInt(6, id);

            // Виконання SQL-запиту
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Дані успішно оновлено в базі даних!");
            }

            // Закриття підключення до бази даних
            preparedStatement.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        handlePrintButton(); // Оновлення таблиці після оновлення даних
    }




}
