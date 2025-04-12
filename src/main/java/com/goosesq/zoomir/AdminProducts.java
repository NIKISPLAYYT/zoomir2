package com.goosesq.zoomir;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Optional;

public class AdminProducts {
    // Связываем элементы интерфейса
    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, Integer> idColumn;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, Integer> quantityColumn;
    @FXML private TextField tf_id;
    @FXML private TextField tf_name;
    @FXML private TextField tf_quantity;
    @FXML private ImageView btn_add;
    @FXML private ImageView btn_delete;
    @FXML private ImageView btn_refresh;
    @FXML private TextField tf_search;
    @FXML private AnchorPane btn_back;

    // Хранение данных
    private ObservableList<Product> productData = FXCollections.observableArrayList();

    // Инициализация
    public void initialize() {
        // Привязка колонок к свойствам модели
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        // Загрузка данных при старте
        loadProducts();
    }

    // Загрузка данных из БД
    private void loadProducts() {
        try {
            productData.clear();
            productData.addAll(DatabaseHandler.getAllProducts());
            productsTable.setItems(productData);
        } catch (SQLException e) {
            showAlert("Ошибка", "Не удалось загрузить данные");
        }
    }

    // Обработка добавления товара
    @FXML
    private void handleAddProduct() {
        try {
            int id = Integer.parseInt(tf_id.getText());
            String name = tf_name.getText();
            int quantity = Integer.parseInt(tf_quantity.getText());

            if (name.isEmpty()) {
                showAlert("Ошибка", "Введите название товара");
                return;
            }

            Product newProduct = new Product(id, name, quantity);
            DatabaseHandler.addProduct(newProduct);
            loadProducts();
            clearFields();
        } catch (NumberFormatException | SQLException e) {
            showAlert("Ошибка", "Неверный формат данных");
        }
    }

    // Обработка удаления товара
    @FXML
    private void handleDeleteProduct() {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert("Ошибка", "Выберите товар для удаления");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setContentText("Вы уверены, что хотите удалить этот товар?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                DatabaseHandler.deleteProduct(selectedProduct.getId());
                loadProducts();
            } catch (SQLException e) {
                showAlert("Ошибка", "Не удалось удалить товар");
            }
        }
    }

    // Обработка обновления данных
    @FXML
    private void handleRefresh() {
        loadProducts();
    }

    // Поиск по ID
    @FXML
    private void handleSearch() {
        try {
            int searchId = Integer.parseInt(tf_search.getText());
            Product foundProduct = DatabaseHandler.getProductById(searchId);
            if (foundProduct != null) {
                productsTable.getSelectionModel().select(foundProduct);
            } else {
                showAlert("Ошибка", "Товар с таким ID не найден");
            }
        } catch (NumberFormatException | SQLException e) {
            showAlert("Ошибка", "Неверный формат ID");
        }
    }

    // Возврат в главное меню админа
    @FXML
    private void goBack() {
        MainApp.showAdminMenuScreen();
    }

    // Очистка полей
    private void clearFields() {
        tf_id.clear();
        tf_name.clear();
        tf_quantity.clear();
    }

    // Показ сообщения
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}