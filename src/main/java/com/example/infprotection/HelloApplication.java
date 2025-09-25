package com.example.infprotection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Вход в систему");
        stage.setScene(scene);
        stage.show();
    }

    //private boolean

    public static void main(String[] args) {
        launch();
    }
}