package com.goosesq.zoomir;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class AdminOrders {
    // Связываем элементы интерфейса
    @FXML private TableView<Order> ordersTable;
    @FXML private TableColumn<Order, Integer> idColumn;
    @FXML private TableColumn<Order, LocalDate> dateColumn;
    @FXML private TableColumn<Order, String> nameColumn;
    @FXML private TableColumn<Order, Integer> quantityColumn;
    @FXML private TextField tf_id;
    @FXML private TextField tf_name;
    @FXML private TextField tf_quantity;
    @FXML private ImageView btn_add;
    @FXML private ImageView btn_delete;
    @FXML private ImageView btn_refresh;
    @FXML private TextField tf_search;
    @FXML private AnchorPane btn_back;

    // Хранение данных
    private ObservableList<Order> orderData = FXCollections.observableArrayList();

    // Инициализация
    public void initialize() {
        // Привязка колонок к свойствам модели
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        // Загрузка данных при старте
        loadOrders();
    }

    // Загрузка данных из БД
    private void loadOrders() {
        try {
            orderData.clear();
            orderData.addAll(DatabaseHandler.getAllOrders());
            ordersTable.setItems(orderData);
        } catch (SQLException e) {
            showAlert("Ошибка", "Не удалось загрузить данные");
        }
    }

    // Обработка добавления заказа
    @FXML
    private void handleAddOrder() {
        try {
            int id = Integer.parseInt(tf_id.getText());
            String name = tf_name.getText();
            int quantity = Integer.parseInt(tf_quantity.getText());

            if (name.isEmpty()) {
                showAlert("Ошибка", "Введите название товара");
                return;
            }

            Order newOrder = new Order(id, LocalDate.now(), name, quantity);
            DatabaseHandler.addOrder(newOrder);
            loadOrders();
            clearFields();
        } catch (NumberFormatException | SQLException e) {
            showAlert("Ошибка", "Неверный формат данных");
        }
    }

    // Обработка удаления заказа
    @FXML
    private void handleDeleteOrder() {
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            showAlert("Ошибка", "Выберите заказ для удаления");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setContentText("Вы уверены, что хотите удалить этот заказ?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                DatabaseHandler.deleteOrder(selectedOrder.getId());
                loadOrders();
            } catch (SQLException e) {
                showAlert("Ошибка", "Не удалось удалить заказ");
            }
        }
    }

        // Обработка обновления данных
        @FXML
        private void handleRefresh() {
            loadOrders();
        }

    // Поиск по ID
    @FXML
    private void handleSearch() {
        try {
            int searchId = Integer.parseInt(tf_search.getText());
            Order foundOrder = DatabaseHandler.getOrderById(searchId);
            if (foundOrder != null) {
                ordersTable.getSelectionModel().select(foundOrder);
            } else {
                showAlert("Ошибка", "Заказ с таким ID не найден");
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