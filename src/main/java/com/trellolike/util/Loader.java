package com.trellolike.util;

import com.trellolike.TrelloLikeApplication;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Loader {

    public void loadPage(String fxml, Double width, Double height, Event event) {
        FXMLLoader fxmlLoader = new FXMLLoader(TrelloLikeApplication.class.getResource(fxml));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), width, height);
        } catch (IOException e) {
            e.printStackTrace();
            InternalError(e.getMessage());
        }
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
        stage.show();
    }

    public void InternalError(String m) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Erreur interne : " + m);
        alert.showAndWait();
    }
}
