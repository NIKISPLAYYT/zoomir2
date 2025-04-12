package com.goosesq.zoomir;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Registration {
    // Связываем элементы интерфейса
    @FXML private TextField tf_login;
    @FXML private PasswordField pf_pass;
    @FXML private TextField tf_pass;
    @FXML private TextField tf_name;
    @FXML private Button b_signup;
    @FXML private Button b_exit;
    @FXML private ImageView iv_hide;

    // Обработка скрытия/показа пароля
    @FXML
    private void iv_hide() {
        if (pf_pass.isVisible()) {
            tf_pass.setText(pf_pass.getText());
            pf_pass.setVisible(false);
            tf_pass.setVisible(true);
        } else {
            pf_pass.setText(tf_pass.getText());
            pf_pass.setVisible(true);
            tf_pass.setVisible(false);
        }
    }

    // Регистрация нового пользователя
    @FXML
    private void boa_signup() {
        String login = tf_login.getText();
        String password = pf_pass.getText();
        String name = tf_name.getText();

        // Проверка заполнения полей
        if (login.isEmpty() || password.isEmpty() || name.isEmpty()) {
            showAlert("Ошибка", "Все поля должны быть заполнены");
            return;
        }

        // Проверка уникальности логина
        if (DatabaseHandler.isLoginExists(login)) {
            showAlert("Ошибка", "Пользователь с таким логином уже существует");
            return;
        }

        // Создание нового пользователя
        User newUser = new User(name, login, password, "user"); // По умолчанию роль "user"
        DatabaseHandler.addUser(newUser);

        // Очистка полей
        tf_login.clear();
        pf_pass.clear();
        tf_name.clear();

        // Возврат к форме авторизации
        showAuthScreen();
    }

    // Выход из приложения
    @FXML
    private void b_exit() {
        System.exit(0);
    }

    // Показ сообщения
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Переход к форме авторизации
    private void showAuthScreen() {
        MainApp.showAuthScreen(); // Используем статический метод
    }
}