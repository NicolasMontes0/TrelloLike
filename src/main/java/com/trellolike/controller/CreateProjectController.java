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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.net.URISyntaxException;

public class CreateProjectController {

    private ApiCaller apiCaller;

    public CreateProjectController() {
        this.apiCaller = new ApiCaller();
    }

    @FXML
    private Text errorText;

    @FXML
    private TextField projectName;

    @FXML
    private Button validationButton;

    @FXML
    void createProject(ActionEvent event) throws IOException {
        if(projectName.getText().equals("")) {
            errorText.setText("Un nom doit être renseigné");
            errorText.setVisible(true);
        } else {
            String body = "{\"name\": \"" + projectName.getText() + "\"}";
            String result = "";
            try {
                apiCaller.callApi(body, "/boards/users/" + Current.userLoggedId, HttpMethod.POST);
            } catch (URISyntaxException e) {
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
