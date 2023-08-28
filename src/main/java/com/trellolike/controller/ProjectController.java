package com.trellolike.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trellolike.model.model.Board;
import com.trellolike.model.model.Card;
import com.trellolike.model.model.ListModel;
import com.trellolike.util.ApiCaller;
import com.trellolike.util.Current;
import com.trellolike.util.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.springframework.http.HttpMethod;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectController implements Initializable {

    private final ApiCaller apiCaller;

    private final Loader loader;

    public ProjectController() {
        this.apiCaller = new ApiCaller();
        this.loader = new Loader();
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
        List<ListModel> lists = new ArrayList<>();
        List<Card> cards = new ArrayList<>();
        try {
            result = apiCaller.callApi(null, "/boards/" + Current.projectId, HttpMethod.GET);
        } catch (URISyntaxException e) {
            loader.InternalError(e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Board board;
        try {
            board = objectMapper.readValue(result, Board.class);
            projectName.setText(board.getName());
        } catch (JsonProcessingException e) {
            loader.InternalError(e);
        }
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
        while(lists.size() + 1 > gridPane.getColumnCount()) {
            gridPane.setPrefWidth(gridPane.getPrefWidth() + addListButton.getPrefWidth());
            anchorPane.setPrefWidth(anchorPane.getPrefWidth() + addListButton.getPrefWidth());
            ColumnConstraints columnConstraints = new ColumnConstraints();
            gridPane.getColumnConstraints().add(columnConstraints);
        }
        gridPane.getChildren().clear();
        int listCount = lists.size() + 1;
        for (ListModel list : lists) {
            try {
                result = apiCaller.callApi(null, "/cards/lists/" + list.getId_list(), HttpMethod.GET);
            } catch (URISyntaxException e) {
                loader.InternalError(e);
            }
            try {
                cards = objectMapper.readValue(result, new TypeReference<>() {});
            } catch (Exception e) {
                loader.InternalError(e);
            }
            while(gridPane.getRowCount() < cards.size() + 2) {
                gridPane.setPrefHeight(gridPane.getPrefHeight() + addListButton.getPrefHeight());
                anchorPane.setPrefHeight(anchorPane.getPrefHeight() + addListButton.getPrefHeight());
                RowConstraints rowConstraints = new RowConstraints();
                gridPane.getRowConstraints().add(rowConstraints);
            }

            Button button = new Button();
            button.setText(list.getName());
            button.setPrefWidth(addListButton.getPrefWidth());
            button.setPrefHeight(addListButton.getPrefHeight());
            button.setFont(addListButton.getFont());
            button.setOnAction(this::showListDetails);
            button.setId("" + list.getId_list());

            gridPane.add(button, listCount, 0);

            int cardCount = 1;
            for (Card card : cards) {
                Button but = new Button();
                but.setText(card.getName());
                but.setPrefWidth(addListButton.getPrefWidth());
                but.setPrefHeight(addListButton.getPrefHeight());
                but.setFont(new Font(11));
                but.setOnAction(this::showCardDetails);
                but.setId("" + card.getId_card());

                gridPane.add(but, listCount, cardCount);
                cardCount++;
            }
            Button b = new Button();
            b.setText("Ajouter une tâche");
            b.setPrefWidth(addListButton.getPrefWidth());
            b.setPrefHeight(addListButton.getPrefHeight());
            b.setFont(new Font(24));
            b.setOnAction(this::createCard);
            b.setId("" + list.getId_list());

            gridPane.add(b, listCount, cardCount);
            listCount++;
        }
        gridPane.add(addListButton, listCount, 0);
    }

    void showListDetails(ActionEvent event) {
        Button b = (Button) event.getSource();
        Current.listId = b.getId();

        loader.loadPage("/view/listDetails.fxml", 600.0, 400.0, event);
    }

    void showCardDetails(ActionEvent event) {
        Button b = (Button) event.getSource();
        Current.cardId = b.getId();

        loader.loadPage("/view/cardDetails.fxml", 600.0, 400.0, event);
    }

    void createCard(ActionEvent event) {
        Button b = (Button) event.getSource();
        Current.listId = b.getId();

        loader.loadPage("/view/createCard.fxml", 600.0, 400.0, event);
    }

    @FXML
    void deleteProject(ActionEvent event) {
        try {
            apiCaller.callApi(null, "/boards/" + Current.projectId, HttpMethod.DELETE);
        } catch (URISyntaxException e) {
            loader.InternalError(e);
        }

        loader.loadPage("/view/home.fxml", 950.0, 600.0, event);
    }

    @FXML
    void export(ActionEvent event) {
        loader.loadPage("/view/export.fxml", 600.0, 400.0, event);
    }

    @FXML
    void addList(ActionEvent event) {
        loader.loadPage("/view/addList.fxml", 600.0, 400.0, event);
    }

    @FXML
    void closePage(ActionEvent event) {
        loader.loadPage("/view/home.fxml", 950.0, 600.0, event);
    }

    @FXML
    void updateProject(MouseEvent event) {
        loader.loadPage("/view/projectDetails.fxml", 600.0, 300.0, event);
    }
}
