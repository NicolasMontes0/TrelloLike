package com.trellolike.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trellolike.model.model.Board;
import com.trellolike.model.model.List;
import com.trellolike.util.ApiCaller;
import com.trellolike.util.Current;
import com.trellolike.util.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private Button deleteButton;

    @FXML
    private TextField listName;

    @FXML
    private Text title;

    @FXML
    private Button updateButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String result = "";
        try {
            result = apiCaller.callApi(null, "/lists/" + Current.listId, HttpMethod.GET);
        } catch (URISyntaxException e) {
            loader.InternalError(e.getMessage());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List list = null;
        try {
            list = objectMapper.readValue(result, List.class);
        } catch (JsonProcessingException e) {
            loader.InternalError(e.getMessage());
        }
        if(list != null) {
            title.setText(title.getText() + list.getName());
        }
    }

    @FXML
    void deleteList(ActionEvent event) {

    }

    @FXML
    void updateList(ActionEvent event) {

    }
}
