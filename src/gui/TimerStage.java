package gui;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Created by WTC-Team on 26.05.2016.
 * Project WhatToCook
 */

public class TimerStage extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage mainStage = new Stage();
        GridPane mainGridLayout = new GridPane();
        ColumnConstraints columnInTimer = new ColumnConstraints();
        columnInTimer.setPercentWidth(12.5);
        for (int i = 0; i < 8; i++)
            mainGridLayout.getColumnConstraints().add(columnInTimer);

        RowConstraints rowInTimer = new RowConstraints();
        rowInTimer.setPercentHeight(25);
        for (int i = 0; i < 4; i++)
            mainGridLayout.getRowConstraints().add(rowInTimer);
        Button plusMinutes = new Button("+");
        plusMinutes.setMaxHeight(Double.MAX_VALUE);
        plusMinutes.setMaxWidth(Double.MAX_VALUE);
        plusMinutes.setOnAction(event -> {
            value += 60;
            refresh();
        });
        Button minusMinutes = new Button("-");
        minusMinutes.setMaxHeight(Double.MAX_VALUE);
        minusMinutes.setMaxWidth(Double.MAX_VALUE);
        minusMinutes.setOnAction(event -> {
            if (value >= 60) {
                value -= 60;
            }
            refresh();
        });
        mainGridLayout.add(plusMinutes, 0, 3, 1, 1);
        mainGridLayout.add(minusMinutes, 1, 3, 1, 1);

        Button plusSeconds = new Button("+");
        plusSeconds.setMaxHeight(Double.MAX_VALUE);
        plusSeconds.setMaxWidth(Double.MAX_VALUE);
        plusSeconds.setOnAction(event -> {
            value += 10;
            refresh();
        });
        Button minusSeconds = new Button("-");
        minusSeconds.setMaxHeight(Double.MAX_VALUE);
        minusSeconds.setMaxWidth(Double.MAX_VALUE);
        minusSeconds.setOnAction(event -> {
            if (value >= 10) {
                value -= 10;
            }
            refresh();
        });
        mainGridLayout.add(plusSeconds, 3, 3, 1, 1);
        mainGridLayout.add(minusSeconds, 4, 3, 1, 1);

        Button start = new Button("start");
        start.setMaxHeight(Double.MAX_VALUE);
        start.setMaxWidth(Double.MAX_VALUE);
        start.setOnAction(event -> {
            if (value > 0) {
                Task <Void> timer = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        if (("" + value % 60).length() < 2) {
                            updateMessage(value / 60 + ":" + "0" + value % 60);
                        } else
                            updateMessage(value / 60 + ":" + value % 60);
                        while (value >= 0) {
                            try {
                                Thread.sleep(1000);
                                value -= 1;
                                if (("" + value % 60).length() < 2) {
                                    updateMessage(value / 60 + ":" + "0" + value % 60);
                                } else
                                    updateMessage(value / 60 + ":" + value % 60);
                            } catch (InterruptedException e) {
                                break;
                            }
                        }
                        start.setDisable(false);
                        minusMinutes.setDisable(false);
                        minusSeconds.setDisable(false);
                        plusMinutes.setDisable(false);
                        plusSeconds.setDisable(false);
                        value = 0;
                        updateMessage("0:00");
                        timerLabel.textProperty().unbind();
                        return null;
                    }
                };
                timerThread = new Thread(timer);
                timerThread.start();
                timerLabel.textProperty().bind(timer.messageProperty());
                start.setDisable(true);
                minusMinutes.setDisable(true);
                minusSeconds.setDisable(true);
                plusMinutes.setDisable(true);
                plusSeconds.setDisable(true);
            }
        });
        Button reset = new Button("reset");
        reset.setMaxHeight(Double.MAX_VALUE);
        reset.setMaxWidth(Double.MAX_VALUE);
        reset.setOnAction(event -> {
            if (timerThread != null) {
                timerThread.interrupt();
                try {
                    timerThread.join();
                } catch (InterruptedException e) {

                }
                start.setDisable(false);
                timerLabel.textProperty().unbind();
                value = 0;
                timerLabel.setText("0:00");
                minusMinutes.setDisable(false);
                minusSeconds.setDisable(false);
                plusMinutes.setDisable(false);
                plusSeconds.setDisable(false);
            }
        });

        mainGridLayout.add(start, 6, 0, 2, 2);
        mainGridLayout.add(reset, 6, 2, 2, 2);

        timerLabel = new Label("0:00");
        timerLabel.setMaxHeight(Double.MAX_VALUE);
        timerLabel.setMaxWidth(Double.MAX_VALUE);
        timerLabel.setAlignment(Pos.CENTER);
        timerLabel.setTextAlignment(TextAlignment.CENTER);
        mainGridLayout.add(timerLabel, 0, 0, 6, 3);

        Scene mainTimerScene = new Scene(mainGridLayout, 300, 150);
        timerLabel.setFont(Font.font("Courier", 66));
        mainStage.setScene(mainTimerScene);
        mainStage.initModality(Modality.WINDOW_MODAL);
        mainStage.setResizable(false);
        mainStage.show();

        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(timerThread!=null) {
                    timerThread.interrupt();
                    try {
                        timerThread.join();
                    } catch (InterruptedException e) {
                    }
                    value = 0;
                }
            }
        });

    }
    private void refresh() {
        if((""+value%60).length()<2) {
            timerLabel.setText(value / 60 + ":" + "0" +value % 60);
        }
        else
            timerLabel.setText(value / 60 + ":" + value % 60);
    }
    private Thread timerThread;
    private Label timerLabel;
    private int value = 0;
}
