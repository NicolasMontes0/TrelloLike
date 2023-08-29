package com.trellolike.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trellolike.model.model.Card;
import com.trellolike.model.model.ListModel;
import com.trellolike.util.ApiCaller;
import com.trellolike.util.Current;
import com.trellolike.util.Loader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.springframework.http.HttpMethod;

import java.net.URISyntaxException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateCardController implements Initializable {

    private final ApiCaller apiCaller;

    private final Loader loader;

    public UpdateCardController() {
        this.apiCaller = new ApiCaller();
        this.loader = new Loader();
    }

    @FXML
    private Text errorText;

    @FXML
    private TextArea newDescription;

    @FXML
    private DatePicker newDate;

    @FXML
    private TextField newName;

    @FXML
    private ComboBox<String> newList;

    private final ArrayList<ListModel> allListInProject = new ArrayList<>();

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
            newName.setText(card.getName());
            if(card.getDescription() != null)
                newDescription.setText(card.getDescription());
            if(card.getDate() != null)
                newDate.setPromptText(card.getDate());

            List<ListModel> lists = new ArrayList<>();
            try {
                result = apiCaller.callApi(null, "/lists/boards/" + Current.projectId, HttpMethod.GET);
            } catch (URISyntaxException e) {
                loader.InternalError(e);
            }
            try {
                lists = objectMapper.readValue(result, new TypeReference<>() {});
            } catch (Exception e) {
                loader.InternalError(e);
            }
            for(ListModel list : lists) {
                if(list.getId_list().equals(card.getId_list())) {
                    newList.setPromptText(list.getName());
                }
                newList.getItems().add(list.getName());

            }
            allListInProject.addAll(lists);
        }
    }

    @FXML
    void closePage(ActionEvent event) {
        loader.loadPage("/view/cardDetails.fxml", 600.0, 400.0, event);
    }

    @FXML
    void updateCard(ActionEvent event) {
        if(newName.getText().equals("")) {
            errorText.setText("Le nom doit être renseigné");
            errorText.setVisible(true);
        } else if(newName.getText().length() > 50) {
            errorText.setText("Le nom doit être inférieur à 51 caractères");
            errorText.setVisible(true);
        } else if(newDescription.getText().length() > 500) {
            errorText.setText("La description doit être inférieur à 501 caractères");
            errorText.setVisible(true);
        } else {
            System.out.println(newList.getSelectionModel().getSelectedIndex());
            String body = "{\"name\": \"" + newName.getText() + "\",\n" +
                    ((newDescription.getText().equals(""))? "" : "\"description\": \"" + newDescription.getText() + "\"") +
                    ((newDate.getValue() == null)? "" : ",\"date\": \"" + newDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\"") +
                    ((newList.getSelectionModel().getSelectedIndex() == -1)? "" : ",\"id_list\": " + allListInProject.get(newList.getSelectionModel().getSelectedIndex()).getId_list()) + "}";
            try {
                apiCaller.callApi(body, "/cards/" + Current.cardId, HttpMethod.PUT);
            }catch (URISyntaxException e) {
                loader.InternalError(e);
            }
            loader.loadPage("/view/project.fxml", 1300.0, 900.0, event);
        }
    }
}
