package com.read.reader;


import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginControler {

    @FXML
    private PasswordField Password;

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginFiled;

    @FXML
    private TextField visiblePasswordFiied;

    @FXML
    public void clicedLogin(ActionEvent event) {
        String login, password;

        login = loginFiled.getText();
        password = Password.getText();

        System.out.println(login + " " + password);
        loginButton.setVisible(false);
        loginButton.setDisable(true);
    }
}
