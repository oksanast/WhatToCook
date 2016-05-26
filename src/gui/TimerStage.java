package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by WTC-Team on 26.05.2016.
 * Project WhatToCook
 */

public class TimerStage extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage mainStage = new Stage();
        GridPane mainGridPane = new GridPane();
        Scene mainTimerScene = new Scene(mainGridPane,300,150);
        mainStage.setScene(mainTimerScene);
        mainStage.initModality(Modality.WINDOW_MODAL);
        mainStage.show();
    }
}
