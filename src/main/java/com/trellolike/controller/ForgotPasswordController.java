package com.trellolike.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trellolike.model.model.User;
import com.trellolike.util.ApiCaller;
import com.trellolike.util.Current;
import com.trellolike.util.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.net.URISyntaxException;

public class ForgotPasswordController {

    private final ApiCaller apiCaller;

    private final Loader loader;

    public ForgotPasswordController() {
        this.apiCaller = new ApiCaller();
        this.loader = new Loader();
    }

    @FXML
    private Button confirmationButton;

    @FXML
    private TextField password;

    @FXML
    private TextField passwordConfirmation;

    @FXML
    private TextField pseudo;

    @FXML
    private Text errorText;

    @FXML
    void updatePassword(ActionEvent event) throws IOException {
        errorText.setVisible(false);

        if(pseudo.getText().equals("")) {
            errorText.setText("Un pseudo doit être renseigné");
            errorText.setVisible(true);
        } else if (password.getText().equals("")) {
            errorText.setText("Un mot de passe doit être renseigné");
            errorText.setVisible(true);
        } else if (!password.getText().equals(passwordConfirmation.getText())) {
            errorText.setText("Les mots de passe ne correspondent pas");
            errorText.setVisible(true);
        } else {
            String result = "";
            try {
                result = apiCaller.callApi(null, "/users/pseudo/" + pseudo.getText(), HttpMethod.GET);
            }catch (URISyntaxException e) {
                loader.InternalError(e.getMessage());
            }
            if(result == null || result.equals("Error")) {
                errorText.setText("Identifiant invalide");
                errorText.setVisible(true);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(result, User.class);

            Current.userLoggedId = "" + user.getId_user();

            String body = "{\"pseudo\": \"" + user.getPseudo() + "\",\n" +
                    "\"mail\": \"" + user.getMail() + "\",\n" +
                    "\"password\": \"" + password.getText() + "\"}";
            try {
                apiCaller.callApi(body, "/users/" + Current.userLoggedId, HttpMethod.PUT);
            }catch (URISyntaxException e) {
                loader.InternalError(e.getMessage());
            }
            loader.loadPage("/view/home.fxml", 950.0, 600.0, event);
        }
    }
}