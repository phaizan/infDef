package com.example.infprotection;



import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    public TextField password;

    @FXML
    public Button loginButton;

    @FXML
    public Label message;

    @FXML
    protected void onLoginButtonClick() {


    }

    private List<User> getUsers() {
        List<User> users = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Path.of("users.txt"));
            for (String line : lines) {
                String[] parts = line.split(" ");
                users.add(new User())


            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла" + e.getMessage());
        }
    }

    private String findUsername(String username) {



    };


}