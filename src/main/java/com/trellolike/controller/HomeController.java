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
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    private ApiCaller apiCaller;

    public HomeController() {
        this.apiCaller = new ApiCaller();
    }

    @FXML
    private Button button;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private GridPane gridPane;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getProject();
        } catch (JsonProcessingException e) {
            InternalError();
        }
    }

    @FXML
    public void getProject() throws JsonProcessingException {
        int count = 1;
        String result = "";
        try {
            result = apiCaller.callApi(null, "/boards/users/" + Current.userLoggedId, HttpMethod.GET);
        } catch (URISyntaxException e) {
            InternalError();
        }
        List<Board> boards = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        List list = objectMapper.readValue(result, List.class);
        for (Object o : list) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) o;
            String json = objectMapper.writeValueAsString(map);
            Board board = objectMapper.readValue(json, Board.class);
            boards.add(board);
        }
        while (boards.size() > gridPane.getColumnCount() * gridPane.getRowCount() - 1) {
            gridPane.setPrefHeight(gridPane.getPrefHeight() + 100);
            anchorPane.setPrefHeight(anchorPane.getPrefHeight() + 100);
            RowConstraints rowConstraints = new RowConstraints();
            gridPane.getRowConstraints().add(rowConstraints);
        }
        for (Board board : boards) {
            Button b = new Button();
            b.setText(board.getName());
            b.setPrefWidth(350);
            b.setPrefHeight(100);
            b.setFont(new Font(24));
            b.setOnAction(this::showProject);
            b.setId("" + board.getId_board());

            if (!gridPane.getChildren().contains(b)) {
                gridPane.add(b, count % 2, Math.floorDiv(count, 2));
                count++;
            }
        }
    }

    @FXML
    void showProject(ActionEvent event) {
        Button b = (Button) event.getSource();
        Current.projectId = b.getId();

        FXMLLoader fxmlLoader = new FXMLLoader(TrelloLikeApplication.class.getResource("/view/project.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1300, 900);
        } catch (IOException e) {
            InternalError();
        }
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void createProject(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TrelloLikeApplication.class.getResource("/view/createProject.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        String body = "{}";
        String result = "";
        try {
            result = apiCaller.callApi(body, "/boards/users/" + Current.userLoggedId, HttpMethod.POST);
        } catch (URISyntaxException e) {
            InternalError();
        }
    }

    public void InternalError() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Error");
        alert.setContentText("Erreur interne, contacter un administrateur !");
        alert.showAndWait();
    }
}
