package com.trellolike.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trellolike.model.model.ListModel;
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

public class ListDetailsController implements Initializable {

    private final ApiCaller apiCaller;

    private final Loader loader;

    public ListDetailsController() {
        this.apiCaller = new ApiCaller();
        this.loader = new Loader();
    }

    @FXML
    private TextField listName;

    @FXML
    private Text title;

    @FXML
    private Text errorText;

    private String listNameVar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String result = "";
        try {
            result = apiCaller.callApi(null, "/lists/" + Current.listId, HttpMethod.GET);
        } catch (URISyntaxException e) {
            loader.InternalError(e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ListModel list = null;
        try {
            list = objectMapper.readValue(result, ListModel.class);
        } catch (JsonProcessingException e) {
            loader.InternalError(e);
        }
        if(list != null) {
            title.setText(title.getText() + list.getName());
            listNameVar = list.getName();
        }
    }

    @FXML
    void deleteList(ActionEvent event) {
        try {
            apiCaller.callApi(null, "/lists/" + Current.listId, HttpMethod.DELETE);
        } catch (URISyntaxException e) {
            loader.InternalError(e);
        }

        loader.loadPage("/view/project.fxml", 1300.0, 900.0, event);
    }

    @FXML
    void updateList(ActionEvent event) {
        if (listNameVar != null) {
            if(listName.getText().equals("") || listName.getText().equals(listNameVar)) {
                errorText.setVisible(true);
                errorText.setText("Le champ 'nouveau nom' est vide ou identique au précédent");
            } else {
                String body = "{\"name\": \"" + listName.getText() + "\"}";
                try {
                    apiCaller.callApi(body, "/lists/" + Current.listId, HttpMethod.PUT);
                } catch (URISyntaxException e) {
                    loader.InternalError(e);
                }

                loader.loadPage("/view/project.fxml", 1300.0, 900.0, event);
            }
        }
    }

    @FXML
    void closePage(ActionEvent event) {
        loader.loadPage("/view/project.fxml", 1300.0, 900.0, event);
    }
}
