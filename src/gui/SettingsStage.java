package gui;

import auxiliary.Dictionary;
import auxiliary.LanguagePackage;
import core.WhatToCook;
import javafx.application.Application;
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
        row.setPercentHeight(25);
        mainLayout.getRowConstraints().add(row);
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
        autoNewCardCheckBox.setMaxWidth(Double.MAX_VALUE);
        autoNewCardCheckBox.setAlignment(Pos.CENTER);
        autoNewCardCheckBox.setOnAction(event -> {
            WhatToCook.autoNewCard = autoNewCardCheckBox.isSelected();
            if(!nextLanguage.equals(""))
                WhatToCook.exportSettings(nextLanguage);
            else
                WhatToCook.exportSettings();
        });
        interfaceTypeComboBox = new ComboBox<>();
        interfaceTypeComboBox.getItems().add(LanguagePackage.getWord("Jedna Kolumna"));
        interfaceTypeComboBox.getItems().add(LanguagePackage.getWord("Dwie Kolumny"));
        interfaceTypeComboBox.getItems().add(LanguagePackage.getWord("Adaptacyjnie"));

        interfaceTypeComboBox.setOnAction(event -> {
            WhatToCook.interfaceType = interfaceTypeComboBox.getSelectionModel().getSelectedIndex();
            WhatToCook.exportSettings();
            WhatToCook.whatToCookStage.drawInterface();
        });

        Label interfaceTypeLabel = new Label(LanguagePackage.getWord("Typ interfejsu"));
        interfaceTypeLabel.setMaxWidth(Double.MAX_VALUE);
        interfaceTypeLabel.setAlignment(Pos.CENTER);
        interfaceTypeLabel.setTextAlignment(TextAlignment.CENTER);

        mainLayout.add(titleLabel,0,0,2,1);
        mainLayout.add(selectLanguageLabel,0,1,1,1);
        mainLayout.add(languageSelectionComboBox,1,1,1,1);
        mainLayout.add(interfaceTypeLabel,0,2,1,1);
        mainLayout.add(interfaceTypeComboBox,1,2,1,1);
        mainLayout.add(autoNewCardCheckBox,0,3,2,1);

        Scene settingsScene = new Scene(mainLayout, 330, 130);
        settingsStage = new Stage();
        settingsStage.setScene(settingsScene);
        settingsStage.setResizable(false);
        settingsStage.initModality(Modality.WINDOW_MODAL);
        settingsStage.setResizable(false);
    }
    void refresh() {
        languageSelectionComboBox.getSelectionModel().select(LanguagePackage.language);
        interfaceTypeComboBox.getSelectionModel().select(WhatToCook.interfaceType);
        autoNewCardCheckBox.setSelected(WhatToCook.autoNewCard);
        settingsStage.show();
    }
    private Stage settingsStage;

    private ComboBox<String> languageSelectionComboBox;
    private ComboBox<String> interfaceTypeComboBox;
    private CheckBox autoNewCardCheckBox;

    private String nextLanguage = "";

}
