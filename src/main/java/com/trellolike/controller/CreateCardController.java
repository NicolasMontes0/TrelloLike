package com.trellolike.controller;

import com.trellolike.util.ApiCaller;
import com.trellolike.util.Current;
import com.trellolike.util.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.springframework.http.HttpMethod;

import java.net.URISyntaxException;

public class CreateCardController {

    private final ApiCaller apiCaller;

    private final Loader loader;

    public CreateCardController() {
        this.apiCaller = new ApiCaller();
        this.loader = new Loader();
    }

    @FXML
    private Button addCardButton;

    @FXML
    private TextArea cardDescription;

    @FXML
    private TextField cardName;

    @FXML
    private Text errorText;

    @FXML
    void addCard(ActionEvent event) {
        if(cardName.getText().equals("")) {
            errorText.setText("Le titre doit être renseigné");
            errorText.setVisible(true);
        } else {
            String body = "{\"name\": \"" + cardName.getText() + "\",\n" +
                    "\"description\": \"" + ((cardDescription.getText().equals(""))? null : cardDescription.getText()) + "\"," +
                    "\"id_list\": \"" + Current.listId + "\"}";
            try {
                Current.userLoggedId = apiCaller.callApi(body, "/cards", HttpMethod.POST);
            }catch (URISyntaxException e) {
                loader.InternalError(e);
            }
            loader.loadPage("/view/project.fxml", 1300.0, 900.0, event);
        }
    }

}
