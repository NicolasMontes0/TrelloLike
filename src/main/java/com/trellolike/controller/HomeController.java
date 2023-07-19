package com.trellolike.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trellolike.model.model.Board;
import com.trellolike.util.ApiCaller;
import com.trellolike.util.Current;
import com.trellolike.util.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    private final ApiCaller apiCaller;

    private final Loader loader;

    public HomeController() {
        this.apiCaller = new ApiCaller();
        this.loader = new Loader();
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
            loader.InternalError(e);
        }
    }

    @FXML
    public void getProject() throws JsonProcessingException {
        int count = 1;
        String result = "";
        try {
            result = apiCaller.callApi(null, "/boards/users/" + Current.userLoggedId, HttpMethod.GET);
        } catch (URISyntaxException e) {
            loader.InternalError(e);
        }
        List<Board> boards = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            boards = objectMapper.readValue(result, new TypeReference<>() {});
        } catch (Exception e) {
            loader.InternalError(e);
        }
        while (boards.size() > gridPane.getColumnCount() * gridPane.getRowCount() - 1) {
            gridPane.setPrefHeight(gridPane.getPrefHeight() + button.getPrefHeight());
            anchorPane.setPrefHeight(anchorPane.getPrefHeight() + button.getPrefHeight());
            RowConstraints rowConstraints = new RowConstraints();
            gridPane.getRowConstraints().add(rowConstraints);
        }
        for (Board board : boards) {
            Button b = new Button();
            b.setText(board.getName());
            b.setPrefWidth(button.getPrefWidth());
            b.setPrefHeight(button.getPrefHeight());
            b.setFont(button.getFont());
            b.setOnAction(this::showProject);
            b.setId("" + board.getId_board());

            gridPane.add(b, count % 2, Math.floorDiv(count, 2));
            count++;
        }
    }

    @FXML
    void showProject(ActionEvent event) {
        Button b = (Button) event.getSource();
        Current.projectId = b.getId();

        loader.loadPage("/view/project.fxml", 1300.0, 900.0, event);
    }

    @FXML
    void createProject(ActionEvent event) {
        loader.loadPage("/view/createProject.fxml", 600.0, 400.0, event);
    }
}
