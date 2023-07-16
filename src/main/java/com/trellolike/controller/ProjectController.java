package com.trellolike.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trellolike.TrelloLikeApplication;
import com.trellolike.model.model.Board;
import com.trellolike.util.ApiCaller;
import com.trellolike.util.Current;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProjectController implements Initializable {

    private ApiCaller apiCaller;

    public ProjectController() {
        this.apiCaller = new ApiCaller();
    }

    @FXML
    private Button addListButton;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private Text projectName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String result = "";
        try {
            result = apiCaller.callApi(null, "/boards/" + Current.projectId, HttpMethod.GET);
        } catch (URISyntaxException e) {
            InternalError();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Board board = null;
        try {
            board = objectMapper.readValue(result, Board.class);
        } catch (JsonProcessingException e) {
            InternalError();
        }

        projectName.setText(board.getName());
    }

    @FXML
    void addList(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TrelloLikeApplication.class.getResource("/view/addList.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void InternalError() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Error");
        alert.setContentText("Erreur interne, contacter un administrateur !");
        alert.showAndWait();
    }
}
