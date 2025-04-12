package com.goosesq.zoomir;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Auth {
    // Все элементы с fx:id
    @FXML private TextField tf_login;
    @FXML private PasswordField pf_pass;
    @FXML private TextField tf_pass;
    @FXML private Button b_signin;
    @FXML private Button b_signus;
    @FXML private Button b_exit;
    @FXML private ImageView iv_hide;

    // Обработка события скрытия пароля
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

    // Метод авторизации
    @FXML
    private void boa_signin() {
        String login = tf_login.getText();
        String password = pf_pass.getText();

        // Проверка в БД
        User user = DatabaseHandler.checkUser(login, password);

        if (user != null) {
            switch (user.getRole()) {
                case "admin":
                    openAdminMenu();
                    break;
                case "user":
                    openUserMenu();
                    break;
                default:
                    showAlert("Ошибка", "Неизвестная роль");
            }
        } else {
            showAlert("Ошибка", "Неверный логин или пароль");
        }
    }

    // Метод регистрации
    @FXML
    private void boa_signus() {
        MainApp.showRegistrationScreen(); // Используем статический метод
    }

    // Метод выхода
    @FXML
    private void b_exit() {
        System.exit(0);
    }

    // Открытие админского меню
    private void openAdminMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_menu.fxml"));
            MainApp.getPrimaryStage().setScene(new Scene(loader.load()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Открытие пользовательского меню
    private void openUserMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("user_menu.fxml"));
                MainApp.getPrimaryStage().setScene(new Scene(loader.load()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Показ сообщения
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}