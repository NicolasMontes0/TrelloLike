package com.trellolike.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trellolike.TrelloLikeApplication;
import com.trellolike.model.model.User;
import com.trellolike.util.ApiCaller;
import com.trellolike.util.Current;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.net.URISyntaxException;

public class ForgotPasswordController {

    private ApiCaller apiCaller;

    public ForgotPasswordController() {
        this.apiCaller = new ApiCaller();
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
                InternalError();
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
                InternalError();
            }

            FXMLLoader fxmlLoader = new FXMLLoader(TrelloLikeApplication.class.getResource("/view/home.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 950, 600);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    public void InternalError() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Error");
        alert.setContentText("Erreur interne, contacter un administrateur !");
        alert.showAndWait();
    }
}