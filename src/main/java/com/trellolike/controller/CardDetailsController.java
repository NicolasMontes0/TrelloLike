package com.trellolike.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trellolike.model.model.Card;
import com.trellolike.util.ApiCaller;
import com.trellolike.util.Current;
import com.trellolike.util.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import org.springframework.http.HttpMethod;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class CardDetailsController implements Initializable {

    private final ApiCaller apiCaller;

    private final Loader loader;

    public CardDetailsController() {
        this.apiCaller = new ApiCaller();
        this.loader = new Loader();
    }

    @FXML
    private Text date;

    @FXML
    private Text dateText;

    @FXML
    private TextArea descriptionText;

    @FXML
    private Text titleText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String result = "";
        try {
            result = apiCaller.callApi(null, "/cards/" + Current.cardId, HttpMethod.GET);
        } catch (URISyntaxException e) {
            loader.InternalError(e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Card card = null;
        try {
            card = objectMapper.readValue(result, Card.class);
        } catch (JsonProcessingException e) {
            loader.InternalError(e);
        }
        if(card != null) {
            titleText.setText(card.getName());
            if(card.getDescription() != null && !card.getDescription().equals("")) {
                descriptionText.setText(card.getDescription());
            }
            if(card.getDate() != null) {
                dateText.setText(card.getDate());
                dateText.setVisible(true);
                date.setVisible(true);
            }
        }
    }

    @FXML
    void updateCard(ActionEvent event) {
        loader.loadPage("/view/updateCard.fxml", 600.0, 400.0, event);
    }

    @FXML
    void delete(ActionEvent event) {
        try {
            apiCaller.callApi(null, "/cards/" + Current.cardId, HttpMethod.DELETE);
        } catch (URISyntaxException e) {
            loader.InternalError(e);
        }

        loader.loadPage("/view/project.fxml", 1300.0, 900.0, event);
    }

    @FXML
    void closePage(ActionEvent event) {
        loader.loadPage("/view/project.fxml", 1300.0, 900.0, event);
    }
}
