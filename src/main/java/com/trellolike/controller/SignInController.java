package com.trellolike.controller;

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
import java.util.regex.Pattern;

public class SignInController {

    private final ApiCaller apiCaller;

    private final Loader loader;

    public SignInController() {
        this.apiCaller = new ApiCaller();
        this.loader = new Loader();
    }

    @FXML
    private Text errorText;

    @FXML
    private TextField mail;

    @FXML
    private TextField password;

    @FXML
    private TextField passwordConfirmation;

    @FXML
    private TextField pseudo;

    @FXML
    private Button signInButton;

    @FXML
    void signIn(ActionEvent event) throws IOException {
        errorText.setVisible(false);

        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);

        if(pseudo.getText().equals("")) {
            errorText.setText("Un pseudo doit être renseigné");
            errorText.setVisible(true);
        } else if (mail.getText().equals("")) {
            errorText.setText("Un mail doit être renseigné");
            errorText.setVisible(true);
        } else if (password.getText().equals("")) {
            errorText.setText("Un mot de passe doit être renseigné");
            errorText.setVisible(true);
        } else if (!password.getText().equals(passwordConfirmation.getText())) {
            errorText.setText("Les mots de passe ne correspondent pas");
            errorText.setVisible(true);
        } else if (!pattern.matcher(mail.getText()).matches()) {
            errorText.setText("Le mail n'est pas au bon format");
            errorText.setVisible(true);
        } else {
            String body = "{\"pseudo\": \"" + pseudo.getText() + "\",\n" +
                    "\"mail\": \"" + mail.getText() + "\",\n" +
                    "\"password\": \"" + password.getText() + "\"}";
            try {
                Current.userLoggedId = apiCaller.callApi(body, "/users", HttpMethod.POST);
            }catch (URISyntaxException e) {
                loader.InternalError(e.getMessage());
            }
            loader.loadPage("/view/home.fxml", 950.0, 600.0, event);
        }
    }
}