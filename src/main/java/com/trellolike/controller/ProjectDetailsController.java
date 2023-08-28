package com.trellolike.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trellolike.model.model.Board;
import com.trellolike.util.ApiCaller;
import com.trellolike.util.Current;
import com.trellolike.util.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.springframework.http.HttpMethod;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProjectDetailsController implements Initializable {

    private final ApiCaller apiCaller;

    private final Loader loader;

    public ProjectDetailsController() {
        this.apiCaller = new ApiCaller();
        this.loader = new Loader();
    }

    @FXML
    private Text errorText;

    @FXML
    private TextField newName;

    @FXML
    private Text title;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String result = "";
        try {
            result = apiCaller.callApi(null, "/boards/" + Current.projectId, HttpMethod.GET);
        } catch (URISyntaxException e) {
            loader.InternalError(e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Board board = null;
        try {
            board = objectMapper.readValue(result, Board.class);
        } catch (JsonProcessingException e) {
            loader.InternalError(e);
        }
        if(board != null) {
            title.setText(board.getName());
        }
    }

    @FXML
    void updateProject(ActionEvent event) {
        if(newName.getText().equals("") || newName.getText().equals(title.getText())) {
            errorText.setVisible(true);
            errorText.setText("Le champ 'nouveau nom' est vide ou identique au précédent");
        } else {
            String body = "{\"name\": \"" + newName.getText() + "\"}";
            try {
                apiCaller.callApi(body, "/boards/" + Current.projectId, HttpMethod.PUT);
            } catch (URISyntaxException e) {
                loader.InternalError(e);
            }

            loader.loadPage("/view/project.fxml", 1300.0, 900.0, event);
        }
    }

    @FXML
    void closePage(ActionEvent event) {
        loader.loadPage("/view/project.fxml", 1300.0, 900.0, event);
    }
}
