package com.trellolike.controller;

import com.trellolike.TrelloLikeApplication;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.net.URISyntaxException;

public class LoginController {

    private ApiCaller apiCaller;

    public LoginController() {
        this.apiCaller = new ApiCaller();
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
    void connection(ActionEvent event) throws URISyntaxException, IOException {
        textError.setVisible(false);
        String pseudoText = username.getText();
        String passwordText = password.getText();
        try {
            Current.userLoggedId = apiCaller.callApi(null, "/users/" + pseudoText + "/" + passwordText, HttpMethod.GET);
        }catch (URISyntaxException e) {
            InternalError();
        }

        if(Current.userLoggedId == null || Current.userLoggedId.equals("Error")) {
            textError.setText("Identifiant ou mot de passe invalide");
            textError.setVisible(true);
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(TrelloLikeApplication.class.getResource("/view/home.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 950, 600);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void forgotPassword(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TrelloLikeApplication.class.getResource("/view/forgotPassword.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void singIn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TrelloLikeApplication.class.getResource("/view/singIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void InternalError() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Error");
        alert.setContentText("Erreur interne, contacter un administrateur !");
        alert.showAndWait();
    }
}
