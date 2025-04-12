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

public class AdminReports {
    // Связываем элементы интерфейса
    @FXML private TableView<Sale> salesTable;
    @FXML private TableColumn<Sale, Integer> idColumn;
    @FXML private TableColumn<Sale, String> productColumn;
    @FXML private TableColumn<Sale, Integer> quantityColumn;
    @FXML private TableColumn<Sale, Double> priceColumn;
    @FXML private TableColumn<Sale, Double> totalColumn;
    @FXML private TextField tf_id;
    @FXML private TextField tf_product;
    @FXML private TextField tf_quantity;
    @FXML private TextField tf_price;
    @FXML private ImageView btn_add;
    @FXML private ImageView btn_delete;
    @FXML private ImageView btn_refresh;
    @FXML private TextField tf_search;
    @FXML private AnchorPane btn_back;

    // Хранение данных
    private ObservableList<Sale> saleData = FXCollections.observableArrayList();

    // Инициализация
    public void initialize() {
        // Привязка колонок к свойствам модели
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        productColumn.setCellValueFactory(cellData -> cellData.getValue().productProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());

        // Загрузка данных при старте
        loadSales();
    }

    // Загрузка данных из БД
    private void loadSales() {
        try {
            saleData.clear();
            saleData.addAll(DatabaseHandler.getAllSales());
            salesTable.setItems(saleData);
        } catch (SQLException e) {
            showAlert("Ошибка", "Не удалось загрузить данные");
        }
    }

    // Обработка добавления продажи
    @FXML
    private void handleAddSale() {
        try {
            int id = Integer.parseInt(tf_id.getText());
            String product = tf_product.getText();
            int quantity = Integer.parseInt(tf_quantity.getText());
            double price = Double.parseDouble(tf_price.getText());

            if (product.isEmpty()) {
                showAlert("Ошибка", "Введите название товара");
                return;
            }

            Sale newSale = new Sale(id, product, quantity, price);
            DatabaseHandler.addSale(newSale);
            loadSales();
            clearFields();
        } catch (NumberFormatException | SQLException e) {
            showAlert("Ошибка", "Неверный формат данных");
        }
    }

    // Обработка удаления продажи
    @FXML
    private void handleDeleteSale() {
        Sale selectedSale = salesTable.getSelectionModel().getSelectedItem();
        if (selectedSale == null) {
            showAlert("Ошибка", "Выберите продажу для удаления");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setContentText("Вы уверены, что хотите удалить эту продажу?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                DatabaseHandler.deleteSale(selectedSale.getId());
                loadSales();
            } catch (SQLException e) {
                showAlert("Ошибка", "Не удалось удалить продажу");
            }
        }
    }

    // Обработка обновления данных
    @FXML
    private void handleRefresh() {
        loadSales();
    }

    // Поиск по ID
    @FXML
    private void handleSearch() {
        try {
            int searchId = Integer.parseInt(tf_search.getText());
            Sale foundSale = DatabaseHandler.getSaleById(searchId);
            if (foundSale != null) {
                salesTable.getSelectionModel().select(foundSale);
            } else {
                showAlert("Ошибка", "Продажа с таким ID не найдена");
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
        tf_product.clear();
        tf_quantity.clear();
        tf_price.clear();
    }

    // Показ сообщения
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}