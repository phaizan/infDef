package com.example.infprotection;



import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LoginController {

    @FXML
    public TextField login;

    @FXML
    public PasswordField password;

    @FXML
    public Button loginButton;

    @FXML
    public Label message;


    @FXML
    protected void onLoginButtonClick() {
        List<User> users = getUsers();
        User user = findUser(login.getText(), users);
        if (user != null)
            if (user.getPassword().equals(password.getText()))
                message.setText("Привет " + user.getLogin());
            else
                message.setText("Введён неправильный пароль");

        else
            message.setText("Пользователь с логином " + login.getText() + " не найден");

    }

    private List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Path.of("users.txt"));
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] parts = line.split(" ");
                users.add(new User()
                        .setLogin(parts[0])
                        .setPassword(parts[1])
                        .setBlocked(Boolean.parseBoolean(parts[2]))
                        .setPasswordRestrctions(Boolean.parseBoolean(parts[3]))
                );
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла" + e.getMessage());
        }
        return users;
    }

    private User findUser(String username, List<User> users) {
        for (User user : users)
            if (user.getLogin().equals(username))
                return user;
        return null;
    };


}