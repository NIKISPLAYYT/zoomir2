package com.goosesq.zoomir;

public class User {
    private int id;
    private String name;
    private String login;
    private String password;
    private String role;

    // Конструктор для создания нового пользователя
    public User(String name, String login, String password, String role) {
        this.name = name;
        this.login = login;
        this.password = password; // В реальном проекте нужно хешировать
        this.role = role;
    }

    // Конструктор для загрузки из БД
    public User(int id, String name, String login, String password, String role) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    // Геттеры
    public int getId() { return id; }
    public String getName() { return name; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    // Сеттеры (если нужно редактирование)
    public void setName(String name) { this.name = name; }
    public void setLogin(String login) { this.login = login; }
    public void setPassword(String password) {
        this.password = password; // Добавьте хеширование здесь
    }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}