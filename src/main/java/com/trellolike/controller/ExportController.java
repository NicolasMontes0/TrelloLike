package com.trellolike.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trellolike.export.ProjectExporter;
import com.trellolike.model.model.Board;
import com.trellolike.util.ApiCaller;
import com.trellolike.util.Current;
import com.trellolike.util.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import org.springframework.http.HttpMethod;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ExportController implements Initializable {

    private final ApiCaller apiCaller;

    private final Loader loader;

    private final ProjectExporter projectExporter;

    public ExportController() {
        this.apiCaller = new ApiCaller();
        this.loader = new Loader();
        this.projectExporter = new ProjectExporter();
    }

    @FXML
    private ComboBox<String> exportChoice;

    @FXML
    private Text title;

    public List<String> exportFormats = Arrays.asList("json");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String result = "";
        try {
            result = apiCaller.callApi(null, "/boards/" + Current.projectId, HttpMethod.GET);
        } catch (URISyntaxException e) {
            loader.InternalError(e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Board board = null;
        try {
            board = objectMapper.readValue(result, Board.class);
        } catch (JsonProcessingException e) {
            loader.InternalError(e);
        }
        if(board != null) {
            title.setText(title.getText() + board.getName());
        }
        exportChoice.getItems().addAll(exportFormats);
    }

    @FXML
    void export(ActionEvent event) {
        String choice = exportChoice.getValue();

        if(choice.equals("json")) {
            projectExporter.exportProjectJson();
        }

        loader.loadPage("/view/project.fxml", 1300.0, 900.0, event);
    }

    @FXML
    void closePage(ActionEvent event) {
        loader.loadPage("/view/project.fxml", 1300.0, 900.0, event);
    }
}
