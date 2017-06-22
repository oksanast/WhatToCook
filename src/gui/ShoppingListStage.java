package gui;

import com.sun.javafx.image.impl.ByteIndexed;
import core.Ingredient;
import auxiliary.LanguagePackage;
import core.ToBuyIngredientsList;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by WTC-Team on 24.05.2016.
 * Project WhatToCook
 */
public class ShoppingListStage extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        shoppingListStage = new Stage();
        shoppingListStage.setTitle(LanguagePackage.getWord("Lista Zakupów"));
        mainBorderPane = new BorderPane();
        shoppingListStage.getIcons().add(new Image("file:data/icon.png"));

        noItemsLabel = new Label(LanguagePackage.getWord("Aby dodać nowe składniki do listy otwórz wybrany przepis i wybierz \"Dodaj do zakupów\""));
        noItemsLabel.setMaxHeight(Double.MAX_VALUE);
        noItemsLabel.setMaxWidth(Double.MAX_VALUE);
        noItemsLabel.setAlignment(Pos.CENTER);
        noItemsLabel.setTextAlignment(TextAlignment.CENTER);
        noItemsLabel.setWrapText(true);

        shoppingListList = new ListView<>();

        downButtons = new HBox();
        downButtons.setAlignment(Pos.CENTER);

        Button clearShoppingList = new Button(LanguagePackage.getWord("Wyczyść listę"));
        clearShoppingList.setOnAction(event -> {
            ToBuyIngredientsList.clear();
            refresh();
        });
        clearShoppingList.setMaxWidth(Double.MAX_VALUE);

        Button exportShoppingList = new Button(LanguagePackage.getWord("Eksportuj listę"));
        exportShoppingList.setOnAction(event -> {
            FileChooser chooseFile = new FileChooser();
            chooseFile.setTitle(LanguagePackage.getWord("Wybierz lokalizację zapisu"));
            chooseFile.setInitialFileName(LanguagePackage.getWord("Lista Zakupów")  + ".txt");
            chooseFile.setInitialDirectory(new File(System.getProperty("user.home")));
            File saveFile = chooseFile.showSaveDialog(primaryStage);
            if(saveFile!=null) {
                try {
                    PrintWriter in = new PrintWriter(saveFile);
                    for (Ingredient i : ToBuyIngredientsList.getSet()) {
                        in.println(i.getName());
                    }
                    in.close();
                } catch (FileNotFoundException e) {
                    System.out.println("File error");
                }
            }
        });

        exportShoppingList.setMaxWidth(Double.MAX_VALUE);
        downButtons.getChildren().add(clearShoppingList);
        downButtons.getChildren().add(exportShoppingList);

        Scene mainScene = new Scene(mainBorderPane,400,400);
        shoppingListStage.initModality(Modality.APPLICATION_MODAL);
        shoppingListStage.setScene(mainScene);
       shoppingListStage.initModality(Modality.APPLICATION_MODAL);
    }
    void refresh() {
        shoppingListList.setItems(ToBuyIngredientsList.getObservableList());
        if(ToBuyIngredientsList.getObservableList().size()>0)
        {
            mainBorderPane.setCenter(shoppingListList);
            mainBorderPane.setBottom(downButtons);
        }
        else {
            mainBorderPane.setCenter(noItemsLabel);
        }
        shoppingListStage.show();
    }
    private HBox downButtons;

    private Stage shoppingListStage;
    private ListView<String> shoppingListList;

    private Label noItemsLabel;

    private BorderPane mainBorderPane;

}
