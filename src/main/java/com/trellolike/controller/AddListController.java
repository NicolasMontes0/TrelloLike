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

public class AddListController {

    private final ApiCaller apiCaller;

    private final Loader loader;

    public AddListController() {
        this.apiCaller = new ApiCaller();
        this.loader = new Loader();
    }

    @FXML
    private Text errorText;

    @FXML
    private TextField listName;

    @FXML
    void addList(ActionEvent event) {
        if (listName.getText().equals("")) {
            errorText.setText("Le nom doit être renseigné");
            errorText.setVisible(true);
        }
        String body = "{\"name\": \"" + listName.getText() + "\",\n" +
                "\"id_board\": " + Current.projectId + "\n}";
        try {
            apiCaller.callApi(body, "/lists", HttpMethod.POST);
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
