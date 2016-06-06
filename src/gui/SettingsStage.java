package gui;

import core.Dictionary;
import core.LanguagePackage;
import core.WhatToCook;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Mateusz on 05.06.2016.
 * Project WhatToCook
 */
public class SettingsStage extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane mainLayout = new GridPane();
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(33);
        mainLayout.getRowConstraints().add(row);
        mainLayout.getRowConstraints().add(row);
        mainLayout.getRowConstraints().add(row);
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(50);
        mainLayout.getColumnConstraints().add(column);
        mainLayout.getColumnConstraints().add(column);

        Label titleLabel = new Label(LanguagePackage.getWord("Ustawienia"));
        titleLabel.setMaxHeight(Double.MAX_VALUE);
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setTextAlignment(TextAlignment.CENTER);

        Label selectLanguageLabel = new Label(LanguagePackage.getWord("Wybierz język"));
        selectLanguageLabel.setMaxHeight(Double.MAX_VALUE);
        selectLanguageLabel.setMaxWidth(Double.MAX_VALUE);
        selectLanguageLabel.setAlignment(Pos.CENTER);
        selectLanguageLabel.setTextAlignment(TextAlignment.CENTER);

        languageSelectionComboBox = new ComboBox<>();
        for(String s : Dictionary.languages)
        languageSelectionComboBox.getItems().add(s);

        languageSelectionComboBox.setOnAction(event -> {
            if(!languageSelectionComboBox.getSelectionModel().getSelectedItem().equals(LanguagePackage.language)) {
                nextLanguage = languageSelectionComboBox.getSelectionModel().getSelectedItem();
                WhatToCook.exportSettings(nextLanguage);
                Alert languageChangeAlert = new Alert(Alert.AlertType.INFORMATION);
                languageChangeAlert.setTitle(LanguagePackage.getWord("Wybierz język"));
                languageChangeAlert.setHeaderText(null);
                languageChangeAlert.setContentText(LanguagePackage.getWord("Zmiany będą widoczne po ponownym uruchomieniu programu"));
                languageChangeAlert.showAndWait();
            }
        });

        autoNewCardCheckBox = new CheckBox(LanguagePackage.getWord("Automatyczne przechodzenie do nowej karty"));
        autoNewCardCheckBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WhatToCook.autoNewCard = autoNewCardCheckBox.isSelected();
                if(!nextLanguage.equals(""))
                    WhatToCook.exportSettings(nextLanguage);
                else
                    WhatToCook.exportSettings();
            }
        });
        mainLayout.add(titleLabel,0,0,2,1);
        mainLayout.add(selectLanguageLabel,0,1,1,1);
        mainLayout.add(languageSelectionComboBox,1,1,1,1);
        mainLayout.add(autoNewCardCheckBox,0,2,2,1);

        settingsScene = new Scene(mainLayout,330,100);
      //  settingsScene.getStylesheets().add(SettingsStage.class.getResource("css/style.css").toExternalForm());
        settingsStage = new Stage();
        settingsStage.setScene(settingsScene);
        settingsStage.setResizable(false);
        settingsStage.initModality(Modality.WINDOW_MODAL);
        settingsStage.setResizable(false);
    }
    public void refresh() {
        languageSelectionComboBox.getSelectionModel().select(LanguagePackage.language);
        autoNewCardCheckBox.setSelected(WhatToCook.autoNewCard);
        settingsStage.show();
    }
    Stage settingsStage;
    Scene settingsScene;

    private ComboBox<String> languageSelectionComboBox;

    private CheckBox autoNewCardCheckBox;

    private String nextLanguage = "";

}
