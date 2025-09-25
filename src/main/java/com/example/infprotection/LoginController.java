package com.example.infprotection;



import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class LoginController {

    private static final Path USERS_PATH = Path.of("users.txt");

    @FXML
    public TextField login;

    @FXML
    public PasswordField password;

    @FXML
    public Button loginButton;

    @FXML
    public Label message;

    @FXML
    public AnchorPane loginForm;

    @FXML
    public AnchorPane adminPanel;

    @FXML
    public AnchorPane checkUsersPanel;

    @FXML
    public AnchorPane passwordChangeForm;

    @FXML
    public PasswordField oldPassword;

    @FXML
    public PasswordField newPassword;

    @FXML
    public PasswordField passwordVerification;

    @FXML
    public Button changePasswordConfirm;

    @FXML
    public Button changePassword;

    @FXML
    public Button cancelChangePassword;

    @FXML
    public Button exit;

    @FXML
    public Button checkUsers;

    @FXML
    public TextField userLogin;

    @FXML
    public CheckBox isBlocked;

    @FXML
    public CheckBox passwordRestrctions;

    @FXML
    public Button nextUser;

    @FXML
    public Button saveUser;

    @FXML
    public Button ok;

    @FXML
    public Button cancelUserView;

    @FXML
    public AnchorPane addUserPanel;

    @FXML
    public Button createUser;

    @FXML
    public TextField newUserName;

    @FXML
    public Button addUser;

    @FXML
    public Button cancelAddUser;

    private User user;



    @FXML
    protected void onLoginButtonClick() {

        List<User> users = getUsers();
        user = findUser(login.getText(), users);
        if (user != null)
            if (user.getPassword().equals(password.getText())) {
                if (user.getLogin().equals("a")) {
                    showAdminPanel();
                }
            }
            else
                message.setText("Введён неправильный пароль");

        else
            message.setText("Пользователь с логином " + login.getText() + " не найден");

    }

    private List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(USERS_PATH);
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] parts = line.split(" ");
                if (parts.length == 4)
                    users.add(new User()
                            .setLogin(parts[0])
                            .setPassword(parts[1])
                            .setBlocked(Boolean.parseBoolean(parts[2]))
                            .setPasswordRestrctions(Boolean.parseBoolean(parts[3])));
                else
                    users.add(new User()
                            .setLogin(parts[0]));
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
    }

    private void showAdminPanel() {
        loginForm.setVisible(false);
        adminPanel.setVisible(true);
    }

    @FXML
    private void onChangePasswordClick() {
        adminPanel.setVisible(false);
        passwordChangeForm.setVisible(true);
    }

    @FXML
    private void onChangePasswordConfirmClick() {
        if (oldPassword.getText().equals(user.getPassword()))
            if (newPassword.getText().equals(passwordVerification.getText()))
                if (!user.isPasswordRestrctions() || isNewPasswordValid(newPassword.getText())) {
                    changeUserPassword(newPassword.getText());
                    message.setText("Пароль изменён");
                }
                else {
                    message.setText("Пароль не соответствует\nтребованиям");
                }

            else
                message.setText("Новые пароли не совпадают");
        else
            message.setText("Неправильно введён старый пароль");
    }

    @FXML
    private void onCancelChangePasswordClick() {
        passwordChangeForm.setVisible(false);
        adminPanel.setVisible(true);
    }

    @FXML
    private void onExitClick() {
        user = null;
        message.setText("");
        adminPanel.setVisible(false);
        loginForm.setVisible(true);
    }

    @FXML
    private void onCheckUsersClick() {
        message.setText("");
        adminPanel.setVisible(false);
        checkUsersPanel.setVisible(true);
        List<User> users = getUsers();
        int i = 1;
        checkUsersPanel.setUserData(new Object[]{users, i});
        userLogin.setText(users.get(i).getLogin());
        isBlocked.setSelected(users.get(i).isBlocked());
        passwordRestrctions.setSelected(users.get(i).isPasswordRestrctions());
    }

    @FXML
    private void onNextUserClick() {
        Object[] data = (Object[]) checkUsersPanel.getUserData();
        List<User> users = (List<User>) data[0];
        int i = (int) data[1];
        if (++i > users.size() - 1)
            i = 1;

        checkUsersPanel.setUserData(new Object[]{users, i});
        userLogin.setText(users.get(i).getLogin());
        isBlocked.setSelected(users.get(i).isBlocked());
        passwordRestrctions.setSelected(users.get(i).isPasswordRestrctions());
    }

    @FXML
    private void onSaveUserClick() {
        Object[] data = (Object[]) checkUsersPanel.getUserData();
        List<User> users = (List<User>) data[0];
        int i = (int) data[1];
        User user = users.get(i);
        user.setLogin(userLogin.getText());
        user.setBlocked(isBlocked.isSelected());
        user.setPasswordRestrctions(passwordRestrctions.isSelected());
        try {
            List<String> lines = Files.readAllLines(USERS_PATH);
            lines.set(i + 1, user.getLogin() + " " + user.getPassword() + " " + user.isBlocked() + " " + user.isPasswordRestrctions());
            rewriteFile(lines);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла" + e.getMessage());
        }

    }

    @FXML
    private void onOkClick() {
        onSaveUserClick();
        onCancelUserViewClick();
    }

    @FXML
    private void onCancelUserViewClick() {
        checkUsersPanel.setVisible(false);
        adminPanel.setVisible(true);
    }

    @FXML
    private void onCreateUserClick() {
        adminPanel.setVisible(false);
        addUserPanel.setVisible(true);
    }

    @FXML
    private void onAddUserClick() {
        try {
            Files.writeString(USERS_PATH, newUserName.getText() + System.lineSeparator(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            message.setText("Ошибка при записи нового пользователя в файл");
        }
    }

    @FXML
    private void onCancelAddUserClick() {
        addUserPanel.setVisible(false);
        adminPanel.setVisible(true);
    }

    private void changeUserPassword (String newPassword)
    {
        try{
            List<String> lines = Files.readAllLines(USERS_PATH);
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] parts = line.split(" ");
                if (parts[0].equals(user.getLogin())) {
                    lines.set(i, parts[0] + " " + newPassword + " " + parts[2] + " " + parts[3]);
                    user.setPassword(newPassword);
                    rewriteFile(lines);
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла" + e.getMessage());
        }
    }

    private void rewriteFile (List<String> lines)
    {
        try {
            Files.write(USERS_PATH, lines, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл" + e.getMessage());
        }
    }

    private boolean isNewPasswordValid (String password)
    {
        boolean hasNum = false;
        boolean hasLower = false;
        boolean hasUpper = false;

        for (int i = 0; i < password.length(); i++)
        {
            if (Character.isDigit(password.charAt(i))) {
                hasNum = true;
                continue;
            }
            if (Character.isLowerCase(password.charAt(i))) {
                hasLower = true;
                continue;
            }
            if (Character.isUpperCase(password.charAt(i))) {
                hasUpper = true;
            }
        }
        return hasNum && hasLower && hasUpper;
    }



}