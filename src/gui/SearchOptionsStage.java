package gui;

import core.WhatToCook;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Mateusz on 24.05.2016.
 * Project WhatToCookFX
 */
public class SearchOptionsStage extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        searchOptionsStage = new Stage();
        GridPane mainGridPane = new GridPane();
        RowConstraints mainGridPaneRow = new RowConstraints();
        mainGridPaneRow.setPercentHeight(50);
        mainGridPane.getRowConstraints().add(mainGridPaneRow);
        mainGridPane.getRowConstraints().add(mainGridPaneRow);

        caseSensitive = new CheckBox("Rozróżniaj wielkość liter");
        caseSensitive.setSelected(WhatToCook.caseSensitiveSearch);
        searchInEveryWord = new CheckBox("Szukaj w każdym słowie");
        caseSensitive.setSelected(WhatToCook.searchInEveryWord);
        mainGridPane.add(caseSensitive,0,0);
        mainGridPane.add(searchInEveryWord,0,1);

        caseSensitive.selectedProperty().addListener((observable, oldValue, newValue) -> {
            WhatToCook.caseSensitiveSearch = caseSensitive.isSelected();
            WhatToCook.exportSettings();
        });
        searchInEveryWord.selectedProperty().addListener((observable, oldValue, newValue) -> {
            WhatToCook.searchInEveryWord = searchInEveryWord.isSelected();
            WhatToCook.exportSettings();
        });

        Scene mainScene = new Scene(mainGridPane,200,100);
        searchOptionsStage.initModality(Modality.APPLICATION_MODAL);
        searchOptionsStage.setScene(mainScene);
    }
    public void refresh() {
        caseSensitive.selectedProperty().setValue(WhatToCook.caseSensitiveSearch);
        searchInEveryWord.selectedProperty().setValue(WhatToCook.searchInEveryWord);
        searchOptionsStage.show();
    }
    private CheckBox caseSensitive;
    private CheckBox searchInEveryWord;
    Stage searchOptionsStage;
}
