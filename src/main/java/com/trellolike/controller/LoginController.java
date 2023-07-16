package com.trellolike.controller;

import com.trellolike.util.ApiCaller;
import com.trellolike.util.Current;
import com.trellolike.util.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.springframework.http.HttpMethod;

import java.net.URISyntaxException;

public class LoginController {

    private final ApiCaller apiCaller;

    private final Loader loader;

    public LoginController() {
        this.apiCaller = new ApiCaller();
        this.loader = new Loader();
    }

    @FXML
    private Text forgotPasword;

    @FXML
    private Button logIn;

    @FXML
    private TextField password;

    @FXML
    private Button singIn;

    @FXML
    private Text textError;

    @FXML
    private TextField username;

    @FXML
    void connection(ActionEvent event) {
        textError.setVisible(false);
        String pseudoText = username.getText();
        String passwordText = password.getText();
        try {
            Current.userLoggedId = apiCaller.callApi(null, "/users/" + pseudoText + "/" + passwordText, HttpMethod.GET);
        }catch (URISyntaxException e) {
            loader.InternalError(e.getMessage());
        }

        if(Current.userLoggedId == null || Current.userLoggedId.equals("Error")) {
            textError.setText("Identifiant ou mot de passe invalide");
            textError.setVisible(true);
        }
        else {
            loader.loadPage("/view/home.fxml", 950.0, 600.0, event);
        }
    }

    @FXML
    void forgotPassword(MouseEvent event) {
        loader.loadPage("/view/forgotPassword.fxml", 600.0, 400.0, event);
    }

    @FXML
    void singIn(ActionEvent event) {
        loader.loadPage("/view/singIn.fxml", 600.0, 400.0, event);
    }
}
