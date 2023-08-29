package com.trellolike.controller;

import com.trellolike.util.ApiCaller;
import com.trellolike.util.Current;
import com.trellolike.util.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.springframework.http.HttpMethod;

import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;

public class CreateCardController {

    private final ApiCaller apiCaller;

    private final Loader loader;

    public CreateCardController() {
        this.apiCaller = new ApiCaller();
        this.loader = new Loader();
    }

    @FXML
    private DatePicker date;

    @FXML
    private TextArea cardDescription;

    @FXML
    private TextField cardName;

    @FXML
    private Text errorText;

    @FXML
    void addCard(ActionEvent event) {
        if(cardName.getText().equals("")) {
            errorText.setText("Le nom doit être renseigné");
            errorText.setVisible(true);
        } else if(cardName.getText().length() > 50) {
            errorText.setText("Le nom doit être inférieur à 51 caractères");
            errorText.setVisible(true);
        } else if(cardDescription.getText().length() > 500) {
            errorText.setText("La description doit être inférieur à 501 caractères");
            errorText.setVisible(true);
        } else {
            String body = "{\"name\": \"" + cardName.getText() + "\",\n" +
                    ((cardDescription.getText().equals(""))? "" : "\"description\": \"" + cardDescription.getText() + "\",") +
                    ((date.getValue() == null)? "" : "\"date\": \"" + date.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\",") +
                    "\"id_list\": \"" + Current.listId + "\"}";
            try {
                apiCaller.callApi(body, "/cards", HttpMethod.POST);
            }catch (URISyntaxException e) {
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
