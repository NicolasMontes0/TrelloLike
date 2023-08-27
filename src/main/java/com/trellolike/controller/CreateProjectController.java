package com.trellolike.controller;

import com.trellolike.util.ApiCaller;
import com.trellolike.util.Current;
import com.trellolike.util.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.springframework.http.HttpMethod;

import java.net.URISyntaxException;

public class CreateProjectController {

    private final ApiCaller apiCaller;

    private final Loader loader;

    public CreateProjectController() {
        this.apiCaller = new ApiCaller();
        this.loader = new Loader();
    }

    @FXML
    private Text errorText;

    @FXML
    private TextField projectName;

    @FXML
    void createProject(ActionEvent event) {
        if(projectName.getText().equals("")) {
            errorText.setText("Un nom doit être renseigné");
            errorText.setVisible(true);
        } else {
            String body = "{\"name\": \"" + projectName.getText() + "\"}";
            try {
                apiCaller.callApi(body, "/boards/users/" + Current.userLoggedId, HttpMethod.POST);
            } catch (URISyntaxException e) {
                loader.InternalError(e);
            }
            loader.loadPage("/view/home.fxml", 950.0, 600.0, event);
        }
    }

    @FXML
    void closePage(ActionEvent event) {
        loader.loadPage("/view/home.fxml", 950.0, 600.0, event);
    }
}
