package gui;

import auxiliary.ListHandler;
import auxiliary.PairAmountUnit;
import auxiliary.RecipeParameters;
import core.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Mateusz on 22.05.2016.
 * Project InferenceEngine
 */
public class MainStage extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("WhatToCook");
        BorderPane mainLayout = new BorderPane();
        mainTable = new TabPane();
        MenuBar mainMenu = new MenuBar();

        Menu fileMenu = new Menu("Plik");
        Menu editMenu = new Menu("Edycja");
        Menu viewMenu = new Menu("Widok");
        Menu toolsMenu = new Menu("Narzędzia");
        Menu helpMenu = new Menu("Pomoc");


        //setUserAgentStylesheet(STYLESHEET_CASPIAN);

        //TWORZENIE OKIEN DIALOGOWYCH
        ShoppingListStage shoppinglist = new ShoppingListStage();
        SearchOptionsStage searchOptions = new SearchOptionsStage();
        shoppinglist.start(primaryStage);
        searchOptions.start(primaryStage);

        Menu newMenu = new Menu("Nowy");

        MenuItem newRecipe = new MenuItem("Przepis");
        newRecipe.setOnAction(event -> {
            if (!isEditionTurnOn) {
                isEditionTurnOn = true;
                showNewEditMenu(null);
            } else {
                Alert cantCreateRecipe = new Alert(Alert.AlertType.ERROR);
                cantCreateRecipe.setTitle("Błąd tworzenia przepisu");
                cantCreateRecipe.setHeaderText("Nie można utworzyć nowego przepisu.");
                cantCreateRecipe.setContentText("W jednym momencie może być tworzony albo edytowany tylko jeden przepis");
                cantCreateRecipe.showAndWait();
            }
        });
        MenuItem newIngredient = new MenuItem("Składnik");
        newIngredient.setOnAction(event -> mainTable.getSelectionModel().select(2));
        MenuItem exit = new MenuItem("Wyjście");
        exit.setOnAction(event -> System.exit(0));

        MenuItem exportAllIngredients = new MenuItem("Eksportuj Składniki");

        exportAllIngredients.setOnAction(event -> {
            FileChooser chooseFile = new FileChooser();
            chooseFile.setTitle("Wybierz lokalizacje zapisu");
            chooseFile.setInitialFileName("składniki.txt");
            File saveFile = chooseFile.showSaveDialog(primaryStage);
            if (saveFile != null) {
                IngredientsList.exportToFile(saveFile);
            }
        });

        MenuItem importAllIngredients = new MenuItem("Importuj Składniki");
        importAllIngredients.setOnAction(event -> {
            FileChooser chooseFile = new FileChooser();
            chooseFile.setTitle("Wybierz plik ze składnikami");
            File openFile = chooseFile.showOpenDialog(primaryStage);
            if (openFile != null) {
                try {
                    Scanner in = new Scanner(openFile);
                    String name;
                    while (in.hasNextLine()) {
                        name = in.nextLine();
                        Ingredient toAdd = new Ingredient(name);
                        IngredientsList.addIngredient(toAdd);
                    }
                    IngredientsList.rebuildModel(ingredientsInIngredientsDatabaseList);
                    chooseIngredientsInSearchComboBox.setItems(IngredientsList.getObservableCollection());
                    ingredientsInNewEditMenuComboBox.setItems(IngredientsList.getObservableCollection());
                    spareIngredientsInIngredientsDatabaseComboBox.setItems(IngredientsList.getObservableCollection());


                } catch (FileNotFoundException e) {
                    System.err.println("Ingredients file not found");
                }
            }
        });

        MenuItem clearInsertIngredients = new MenuItem("Wyczyść wprowadzone składniki");
        clearInsertIngredients.setOnAction(event -> {
            for (int i = ingredientsInSearchList.getItems().size() - 1; i >= 0; i--) {
                ingredientsInSearchList.getItems().remove(i);
            }
        });
        MenuItem clearFoundRecipes = new MenuItem("Wyczyść wyniki wyszukiwania");
        clearFoundRecipes.setOnAction(event -> foundRecipesInSearchList.setItems(FXCollections.emptyObservableList()));

        Menu cardsMenu = new Menu("Karty");

        CheckMenuItem searchingCard = new CheckMenuItem("Wyszukiwanie");
        CheckMenuItem recipesDatabaseCard = new CheckMenuItem("Baza Przepisów");
        CheckMenuItem ingredientsCard = new CheckMenuItem("Składniki");

        newMenu.getItems().add(newRecipe);
        newMenu.getItems().add(newIngredient);
        fileMenu.getItems().add(newMenu);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(exit);

        editMenu.getItems().add(exportAllIngredients);
        editMenu.getItems().add(importAllIngredients);
        editMenu.getItems().add(new SeparatorMenuItem());
        editMenu.getItems().add(clearInsertIngredients);
        editMenu.getItems().add(clearFoundRecipes);

        MenuItem closeAllRecipes = new MenuItem("Zamknij wszystkie przepisy");
        closeAllRecipes.setOnAction(event -> {
            if (isEditionTurnOn) {
                for (int i = mainTable.getTabs().size() - 1; i >= mainCardsCount + 1; i--)
                    mainTable.getTabs().remove(i);
            } else {
                for (int i = mainTable.getTabs().size() - 1; i >= mainCardsCount; i--)
                    mainTable.getTabs().remove(i);
            }
        });

        MenuItem teamMenuItem = new MenuItem("Autorzy");
        teamMenuItem.setOnAction(event -> {
            AboutWindow teamStage = new AboutWindow();
            try {
                teamStage.start(primaryStage);
            } catch (Exception e) {
                System.out.println("Internal error, please report it");

            }

        });

        searchingCard.setSelected(true);
        recipesDatabaseCard.setSelected(true);
        ingredientsCard.setSelected(true);

        searchingCard.setOnAction(event -> {
            if (!searchingCard.isSelected()) {
                mainTable.getTabs().remove(searchingTab);
                mainCardsCount--;
            } else {
                mainTable.getTabs().add(0, searchingTab);
                mainCardsCount++;
            }
        });
        recipesDatabaseCard.setOnAction(event -> {
            if (!recipesDatabaseCard.isSelected()) {
                mainTable.getTabs().remove(recipesDatabaseTab);
                mainCardsCount--;
            } else {
                if (mainCardsCount == 1 && ingredientsCard.isSelected())
                    mainTable.getTabs().add(0, recipesDatabaseTab);
                else if (mainCardsCount == 1 && !ingredientsCard.isSelected())
                    mainTable.getTabs().add(1, recipesDatabaseTab);
                else if (mainCardsCount >= 2)
                    mainTable.getTabs().add(1, recipesDatabaseTab);
                else
                    mainTable.getTabs().add(0, recipesDatabaseTab);
                mainCardsCount++;
            }
        });
        ingredientsCard.setOnAction(event -> {
            if (!ingredientsCard.isSelected()) {
                mainTable.getTabs().remove(ingredientsDatabaseTab);
                mainCardsCount--;
            } else {
                if (mainCardsCount >= 2)
                    mainTable.getTabs().add(2, ingredientsDatabaseTab);
                else if (mainCardsCount == 1)
                    mainTable.getTabs().add(1, ingredientsDatabaseTab);
                else
                    mainTable.getTabs().add(0, ingredientsDatabaseTab);
                mainCardsCount++;
            }
        });
        cardsMenu.getItems().add(searchingCard);
        cardsMenu.getItems().add(recipesDatabaseCard);
        cardsMenu.getItems().add(ingredientsCard);

        viewMenu.getItems().add(cardsMenu);
        viewMenu.getItems().add(new SeparatorMenuItem());
        viewMenu.getItems().add(closeAllRecipes);

        MenuItem shoppingListMenuItem = new MenuItem("Lista Zakupów");
        shoppingListMenuItem.setOnAction(event -> {
            try {
                shoppinglist.refresh();
            } catch (Exception e) {
                System.out.println("Unexpected error with shopping list, report that bug.");
            }
        });

        toolsMenu.getItems().add(shoppingListMenuItem);
        helpMenu.getItems().add(teamMenuItem);
        mainMenu.getMenus().addAll(fileMenu, editMenu, viewMenu, toolsMenu, helpMenu);

        SpareIngredientsList.initialize();
        RecipesList.initialize();
        IngredientsList.initialize();
        ToBuyIngredientsList.initialize();

        //INICJALIZACJA TEGO, CO MUSI BYC NA POCZATKU
        ingredientsInNewEditMenuComboBox = new ComboBox<>();
        ingredientsInNewEditMenuComboBox.setItems(IngredientsList.getObservableCollection());

        //TWORZENIE KARTY WYSZUKIWANIA PRZEPISOW///////////////////////////////////////////////////////////////////////
        searchingTab = new Tab();
        searchingTab.setText("Wyszukiwanie");
        searchingTab.setClosable(false);
        GridPane searchingGridPane = new GridPane();
        searchingGridPane.setVgap(5);
        searchingGridPane.setHgap(5);
        searchingGridPane.setPadding(new Insets(10,0,10,0));
        //searchingGridPane.setAlignment(Pos.CENTER);
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(25);
        column.setHgrow(Priority.ALWAYS);
        searchingGridPane.getColumnConstraints().add(column);
        searchingGridPane.getColumnConstraints().add(column);
        searchingGridPane.getColumnConstraints().add(column);
        searchingGridPane.getColumnConstraints().add(column);

        RowConstraints row = new RowConstraints();
        row.setPercentHeight(16);
        row.setVgrow(Priority.ALWAYS);
        searchingGridPane.getRowConstraints().add(row);
        searchingGridPane.getRowConstraints().add(row);
        searchingGridPane.getRowConstraints().add(row);
        searchingGridPane.getRowConstraints().add(row);
        searchingGridPane.getRowConstraints().add(row);
        searchingGridPane.getRowConstraints().add(row);
        searchingGridPane.getRowConstraints().add(row);
        searchingGridPane.getRowConstraints().add(row);
        searchingGridPane.getRowConstraints().add(row);
        searchingGridPane.getRowConstraints().add(row);
        searchingGridPane.getRowConstraints().add(row);
        searchingGridPane.getRowConstraints().add(row);
        searchingGridPane.getRowConstraints().add(row);
        searchingGridPane.getRowConstraints().add(row);
        searchingGridPane.getRowConstraints().add(row);
        searchingGridPane.getRowConstraints().add(row);
        searchingGridPane.getRowConstraints().add(row);


        Text insertIngredientsLabel = new Text("Wprowadź składniki");
        insertIngredientsLabel.setId("insertIngredientsText");
        HBox insertIngredientsLabelHBox = new HBox();
        insertIngredientsLabelHBox.setAlignment(Pos.CENTER);
        insertIngredientsLabelHBox.getChildren().add(insertIngredientsLabel);
        searchingGridPane.add(insertIngredientsLabelHBox, 0, 0, 4, 1);

        ingredientsInSearchList = new ListView<>();

        searchingGridPane.add(ingredientsInSearchList, 0, 1, 2, 5);
        ingredientsInSearchList.setMaxWidth(Double.MAX_VALUE);
        ingredientsInSearchList.setMaxHeight(Double.MAX_VALUE);

        Label chooseIngredientsInSearchLabel = new Label("Wybierz składniki");
        chooseIngredientsInSearchLabel.setMaxHeight(Double.MAX_VALUE);
        chooseIngredientsInSearchLabel.setMaxWidth(Double.MAX_VALUE);
        chooseIngredientsInSearchLabel.setAlignment(Pos.CENTER);

        chooseIngredientsInSearchComboBox = new ComboBox<>();
        chooseIngredientsInSearchComboBox.setMaxHeight(Double.MAX_VALUE);
        chooseIngredientsInSearchComboBox.setMaxWidth(Double.MAX_VALUE);

        Button addIngredientInSearchButton = new Button("Dodaj składnik");
        addIngredientInSearchButton.setMaxWidth(Double.MAX_VALUE);
       // addIngredientInSearchButton.setMaxHeight(Double.MAX_VALUE);

        try {
            Scanner in = new Scanner(new File("data/ownedIngredients/ownedIngredients"));
            while(in.hasNextLine()) {
                ingredientsInSearchList.getItems().add(in.nextLine());
            }
            in.close();
        } catch (FileNotFoundException e) {

        }

        addIngredientInSearchButton.setOnAction(event -> {
            if (chooseIngredientsInSearchComboBox.getSelectionModel().getSelectedItem() != null) {
                if (!ingredientsInSearchList.getItems().contains(chooseIngredientsInSearchComboBox.getSelectionModel().getSelectedItem()))
                    ingredientsInSearchList.getItems().add(chooseIngredientsInSearchComboBox.getSelectionModel().getSelectedItem());
                try {
                    PrintWriter writer = new PrintWriter(new File("data/ownedIngredients/ownedIngredients"));
                    for(String i : ingredientsInSearchList.getItems()) {
                        writer.println(i);
                    }
                    writer.close();
                } catch (FileNotFoundException e) {

                }
            }
        });

        Button removeIngredientInSearchButton = new Button("Usuń składnik");
        removeIngredientInSearchButton.setMaxWidth(Double.MAX_VALUE);
       // removeIngredientInSearchButton.setMaxHeight(Double.MAX_VALUE);

        removeIngredientInSearchButton.setOnAction(event -> {
            if (ingredientsInSearchList.getSelectionModel().getSelectedIndex() >= 0)
                ingredientsInSearchList.getItems().remove(ingredientsInSearchList.getSelectionModel().getSelectedIndex());
            try {
                PrintWriter writer = new PrintWriter(new File("data/ownedIngredients/ownedIngredients"));
                for(String i : ingredientsInSearchList.getItems()) {
                    writer.println(i);
                }
                writer.close();
            } catch (FileNotFoundException e) {

            }
        });

        Button importIngredientsInSearchButton = new Button("Importuj");
        //importIngredientsInSearchButton.setMaxHeight(Double.MAX_VALUE);
        importIngredientsInSearchButton.setMaxWidth(Double.MAX_VALUE);

        importIngredientsInSearchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser chooseFile = new FileChooser();
                chooseFile.setTitle("Wybierz plik z posiadanymi składnikami");
                File openFile = chooseFile.showOpenDialog(primaryStage);
                if (openFile != null) {
                    try {
                        Scanner in = new Scanner(openFile);
                        while(in.hasNextLine())
                            ingredientsInSearchList.getItems().add(in.nextLine());

                    } catch (FileNotFoundException e) {
                    }
                }
            }
        });

        Button exportIngredientsInSearchButoon = new Button("Eksportuj");
        exportIngredientsInSearchButoon.setMaxWidth(Double.MAX_VALUE);
        //exportIngredientsInSearchButoon.setMaxHeight(Double.MAX_VALUE);
        exportIngredientsInSearchButoon.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser chooseFile = new FileChooser();
                chooseFile.setTitle("Wybierz lokalizacje zapisu");
                chooseFile.setInitialFileName("Posiadane Składniki.txt");
                File saveFile = chooseFile.showSaveDialog(primaryStage);
                if (saveFile != null) {
                    try {
                        PrintWriter writer = new PrintWriter(saveFile);
                        for(String s : ingredientsInSearchList.getItems()) {
                            writer.println(s);
                        }
                        writer.close();
                    } catch (FileNotFoundException e) {

                    }
                }
            }
        });

        Label foundRecipesInSearchLabel = new Label("Znalezione przepisy");
        foundRecipesInSearchLabel.setAlignment(Pos.CENTER);
        foundRecipesInSearchLabel.setMaxHeight(Double.MAX_VALUE);
        foundRecipesInSearchLabel.setMaxWidth(Double.MAX_VALUE);
        Label mealForInSearchLabel = new Label("Danie na:");
        mealForInSearchLabel.setAlignment(Pos.CENTER);
        mealForInSearchLabel.setMaxHeight(Double.MAX_VALUE);
        mealForInSearchLabel.setMaxWidth(Double.MAX_VALUE);
        Label preparingTimeInSearchLabel = new Label("Czas przygotowania:");

        Label preparingEaseInSearchLabel = new Label("Latwość przygotowania:");


        foundRecipesInSearchList = new ListView<>();
        foundRecipesInSearchList.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                showRecipe(RecipesList.getRecipe(foundRecipesInSearchList.getSelectionModel().getSelectedItem()));
            }
        });

        Button searchForRecipesInSearchButton = new Button("Szukaj przepisów");
        //searchForRecipesInSearchButton.setMaxHeight(Double.MAX_VALUE);
        searchForRecipesInSearchButton.setMaxWidth(Double.MAX_VALUE);

        searchForRecipesInSearchButton.setOnAction(event -> {
            ArrayList<Ingredient> ownedIngredients = new ArrayList<>();
            for (int i = 0; i < ingredientsInSearchList.getItems().size(); i++) {
                ownedIngredients.add(new Ingredient(ingredientsInSearchList.getItems().get(i)));
            }
            boolean[] parameters = new boolean[5];
            parameters[0] = breakfestInSearchCheckBox.isSelected();
            parameters[1] = dessertInSearchCheckBox.isSelected();
            parameters[2] = dinerInSearchCheckBox.isSelected();
            parameters[3] = supperInSearchCheckBox.isSelected();
            parameters[4] = snackInSearchCheckBox.isSelected();
            if (isFalse(parameters, 5)) {
                for (int i = 0; i < 5; i++) {
                    parameters[i] = true;
                }
            }
            ObservableList<String> foundRecipesObservableList = FXCollections.observableArrayList();
            foundRecipesObservableList.removeAll();
            for (int i = 0; i < RecipesList.size(); i++) {
                if (RecipesList.checkWithIngredientsList(ownedIngredients, i, parameters, preparingEaseInSearchComboBox.getSelectionModel().getSelectedIndex(), preparingTimeInSearchComboBox.getSelectionModel().getSelectedIndex(), spareIngredientsCheckBox.isSelected())) {
                    foundRecipesObservableList.add(RecipesList.getRecipeNameAtIndex(i));
                }
            }
            foundRecipesInSearchList.setItems(foundRecipesObservableList);
        });

        preparingTimeInSearchComboBox = new ComboBox<>();
        preparingTimeInSearchComboBox.getItems().add("Szybko");
        preparingTimeInSearchComboBox.getItems().add("Srednio");
        preparingTimeInSearchComboBox.getItems().add("Wolno");
        preparingTimeInSearchComboBox.getSelectionModel().select(0);
        preparingEaseInSearchComboBox = new ComboBox<>();
        preparingEaseInSearchComboBox.getItems().add("Latwe");
        preparingEaseInSearchComboBox.getItems().add("Srednie");
        preparingEaseInSearchComboBox.getItems().add("Trudne");
        preparingEaseInSearchComboBox.getSelectionModel().select(0);

        breakfestInSearchCheckBox = new CheckBox("Sniadanie");
        dinerInSearchCheckBox = new CheckBox("Obiad");
        supperInSearchCheckBox = new CheckBox("Kolację");
        dessertInSearchCheckBox = new CheckBox("Deser");
        snackInSearchCheckBox = new CheckBox("Przekąskę");
        spareIngredientsCheckBox = new CheckBox("Składniki Alternatywne");

        searchingGridPane.add(chooseIngredientsInSearchComboBox, 2, 2, 2, 1);
        searchingGridPane.add(chooseIngredientsInSearchLabel, 2, 1, 2, 1);
        searchingGridPane.add(addIngredientInSearchButton, 2, 3, 2, 1);
        searchingGridPane.add(removeIngredientInSearchButton, 2, 4, 2, 1);
        searchingGridPane.add(importIngredientsInSearchButton, 2, 5);
        searchingGridPane.add(exportIngredientsInSearchButoon, 3, 5);
        searchingGridPane.add(foundRecipesInSearchLabel, 0, 6, 2, 1);
        searchingGridPane.add(foundRecipesInSearchList, 0, 7, 2, 9);
        searchingGridPane.add(searchForRecipesInSearchButton, 0, 16, 2, 1);
        searchingGridPane.add(mealForInSearchLabel, 2, 6, 2, 1);
        searchingGridPane.add(breakfestInSearchCheckBox, 2, 7, 2, 1);
        searchingGridPane.add(dinerInSearchCheckBox, 2, 8, 2, 1);
        searchingGridPane.add(supperInSearchCheckBox, 2, 9, 2, 1);
        searchingGridPane.add(dessertInSearchCheckBox, 2, 10, 2, 1);
        searchingGridPane.add(snackInSearchCheckBox, 2, 11, 2, 1);
        searchingGridPane.add(preparingTimeInSearchLabel, 2, 12, 2, 1);
        searchingGridPane.add(preparingTimeInSearchComboBox, 2, 13, 2, 1);
        searchingGridPane.add(preparingEaseInSearchLabel, 2, 14, 2, 1);
        searchingGridPane.add(preparingEaseInSearchComboBox, 2, 15, 2, 1);
        searchingGridPane.add(spareIngredientsCheckBox, 2, 16, 2, 1);


        searchingTab.setClosable(false);
        searchingTab.setContent(searchingGridPane);

        chooseIngredientsInSearchComboBox.setItems(IngredientsList.getObservableCollection());

        mainTable.getTabs().add(searchingTab);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //TWORZENIE KARTY BAZY PRZEPISOW
        recipesDatabaseTab = new Tab("Baza Przepisów");
        recipesDatabaseTab.setClosable(false);
        GridPane recipesDatabaseGridPane = new GridPane();
        recipesDatabaseGridPane.setHgap(5);
        recipesDatabaseGridPane.setVgap(5);

        ColumnConstraints columnInRecipesDatabase = new ColumnConstraints();
        columnInRecipesDatabase.setPercentWidth(25);
        recipesDatabaseGridPane.getColumnConstraints().add(columnInRecipesDatabase);
        recipesDatabaseGridPane.getColumnConstraints().add(columnInRecipesDatabase);
        recipesDatabaseGridPane.getColumnConstraints().add(columnInRecipesDatabase);
        recipesDatabaseGridPane.getColumnConstraints().add(columnInRecipesDatabase);

        RowConstraints rowInRecipesDatabase = new RowConstraints();
        rowInRecipesDatabase.setPercentHeight(12.5);
        for (int i = 0; i < 14; i++) {
            recipesDatabaseGridPane.getRowConstraints().add(rowInRecipesDatabase);
        }

        Label searchInRecipesDatabaseLabel = new Label("Wyszukaj");
        searchInRecipesDatabaseLabel.setMaxWidth(Double.MAX_VALUE);
        searchInRecipesDatabaseLabel.setMaxHeight(Double.MAX_VALUE);
        searchInRecipesDatabaseLabel.setAlignment(Pos.CENTER);

        searchInRecipesDatabase = new TextField();


        Button searchingOptionsInRecipesDatabaseButton = new Button("Opcje Wyszukiwania");
        //searchingOptionsInRecipesDatabaseButton.setMaxHeight(Double.MAX_VALUE);
        searchingOptionsInRecipesDatabaseButton.setMaxWidth(Double.MAX_VALUE);

        searchingOptionsInRecipesDatabaseButton.setOnAction(event -> searchOptions.refresh());

        recipesInRecipesDatabaseList = new ListView<>();
        searchInRecipesDatabase.textProperty().addListener((observable, oldValue, newValue) -> {
            String beg = searchInRecipesDatabase.getText();
            recipesInRecipesDatabaseList.setItems(RecipesList.getObservableList(beg, WhatToCook.caseSensitiveSearch, WhatToCook.searchInEveryWord));
        });
        recipesInRecipesDatabaseList.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                showRecipe(RecipesList.getRecipe(recipesInRecipesDatabaseList.getSelectionModel().getSelectedItem()));
            }
        });

        linkedRecipesAmmmount = new Label("0/6");
        linkedRecipesAmmmount.setAlignment(Pos.CENTER);
        linkedRecipesAmmmount.setTextAlignment(TextAlignment.CENTER);
        linkedRecipesAmmmount.setMaxWidth(Double.MAX_VALUE);


        linkedRecipesGridPane = new GridPane();
        RowConstraints linkedRecipesRow = new RowConstraints();
        linkedRecipesRow.setPercentHeight(14);
        for(int i = 0; i < 6; i++) {
            linkedRecipesGridPane.getRowConstraints().add(linkedRecipesRow);
        }
        ColumnConstraints linkedRecipesColumn = new ColumnConstraints();
        linkedRecipesColumn.setPercentWidth(100);
        linkedRecipesGridPane.getColumnConstraints().add(linkedRecipesColumn);
        recipesDatabaseGridPane.add(linkedRecipesGridPane,2,5,2,8);
        recipesDatabaseGridPane.add(linkedRecipesAmmmount,2,12,2,1);

        recipesInRecipesDatabaseList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            markedRecipe = recipesInRecipesDatabaseList.getSelectionModel().getSelectedItem();
            linkedRecipesToggleGroup.selectToggle(null);
            refreshLinkedRecipes();

        });
        Label linkedRecipesInRecipesDatabaseLabel = new Label("Przepisy powiązane:");
        linkedRecipesInRecipesDatabaseLabel.setMaxWidth(Double.MAX_VALUE);
        linkedRecipesInRecipesDatabaseLabel.setAlignment(Pos.CENTER);

        linkedRecipesInRecipesDatabaseComboBox = new ComboBox<>();
        linkedRecipesInRecipesDatabaseComboBox.setMaxWidth(Double.MAX_VALUE);
        linkedRecipesInRecipesDatabaseComboBox.setItems(RecipesList.getObservableList());

        Button addLinkedRecipeInRecipesDatabaseButton = new Button("Dodaj Powiązanie");
        addLinkedRecipeInRecipesDatabaseButton.setMaxWidth(Double.MAX_VALUE);
        addLinkedRecipeInRecipesDatabaseButton.setOnAction(event -> {
            if(markedRecipe!=null) {
                //noinspection ConstantConditions
                if (RecipesList.getRecipe(markedRecipe).getLinkedRecipes().size()<6 && !RecipesList.getRecipe
                        (markedRecipe).getName().equals(linkedRecipesInRecipesDatabaseComboBox.getSelectionModel().getSelectedItem())) {
                    LinkedRecipes.addLinking(markedRecipe,linkedRecipesInRecipesDatabaseComboBox.getSelectionModel().getSelectedItem());
                    refreshLinkedRecipes();
                    LinkedRecipes.saveLinkings();
                }
            }
        });
        Button removeLinkedRecipeInRecipesDatabaseButton = new Button("Usuń Powiązanie");
        removeLinkedRecipeInRecipesDatabaseButton.setMaxWidth(Double.MAX_VALUE);
        removeLinkedRecipeInRecipesDatabaseButton.setOnAction(event -> {
            if(linkedRecipesToggleGroup.getSelectedToggle()!=null) {
                RadioButton button = (RadioButton) linkedRecipesToggleGroup.getSelectedToggle();
                String recipeName = button.getText();
                LinkedRecipes.deleteLinking(markedRecipe,recipeName);
                refreshLinkedRecipes();
                LinkedRecipes.saveLinkings();
            }

        });

        Button addRecipeInRecipesDatabaseButton = new Button("Nowy Przepis");
        addRecipeInRecipesDatabaseButton.setMaxWidth(Double.MAX_VALUE);

        addRecipeInRecipesDatabaseButton.setOnAction(event -> {
            if (!isEditionTurnOn) {
                isEditionTurnOn = true;
                showNewEditMenu(null);
            } else {
                Alert cantCreateRecipe = new Alert(Alert.AlertType.ERROR);
                cantCreateRecipe.setTitle("Błąd tworzenia przepisu");
                cantCreateRecipe.setHeaderText("Nie można utworzyć nowego przepisu.");
                cantCreateRecipe.setContentText("W jednym momencie może być tworzony albo edytowany tylko jeden przepis");
                cantCreateRecipe.showAndWait();
            }
        });

        Button removeRecipeInRecipesDatabaseButton = new Button("Usuń Przepis");
        //removeRecipeInRecipesDatabaseButton.setMaxHeight(Double.MAX_VALUE);
        removeRecipeInRecipesDatabaseButton.setMaxWidth(Double.MAX_VALUE);
        removeRecipeInRecipesDatabaseButton.setOnAction(event -> {
            if (inEdit == null) {
                for(int j = 0; j < RecipesList.size();j++) {
                    for(int k = 0; k < RecipesList.recipesList.get(j).getLinkedRecipes().size();k++) {
                        if(RecipesList.recipesList.get(j).getLinkedRecipes().get(k).equals(recipesInRecipesDatabaseList.getSelectionModel().getSelectedItem())) {
                            RecipesList.recipesList.get(j).getLinkedRecipes().remove(k);
                        }
                    }
                }
                RecipesList.remove(recipesInRecipesDatabaseList.getSelectionModel().getSelectedItem());
                recipesInRecipesDatabaseList.setItems(RecipesList.getObservableList(searchInRecipesDatabase.getText(), WhatToCook.caseSensitiveSearch, WhatToCook.searchInEveryWord));
                linkedRecipesInRecipesDatabaseComboBox.setItems(RecipesList.getObservableList());
                LinkedRecipes.saveLinkings();
            } else {
                if (!inEdit.getName().equals(recipesInRecipesDatabaseList.getSelectionModel().getSelectedItem())) {
                    for(int j = 0; j < RecipesList.size();j++) {
                        for(int k = 0; k < RecipesList.recipesList.get(j).getLinkedRecipes().size();k++) {
                            if(RecipesList.recipesList.get(j).getLinkedRecipes().get(k).equals(recipesInRecipesDatabaseList.getSelectionModel().getSelectedItem())) {
                                RecipesList.recipesList.get(j).getLinkedRecipes().remove(k);
                            }
                        }
                    }
                    RecipesList.remove(recipesInRecipesDatabaseList.getSelectionModel().getSelectedItem());
                    recipesInRecipesDatabaseList.setItems(RecipesList.getObservableList(searchInRecipesDatabase.getText(), WhatToCook.caseSensitiveSearch, WhatToCook.searchInEveryWord));
                    linkedRecipesInRecipesDatabaseComboBox.setItems(RecipesList.getObservableList());
                    LinkedRecipes.saveLinkings();
                } else {
                    Alert cantDeleteRecipe = new Alert(Alert.AlertType.ERROR);
                    cantDeleteRecipe.setTitle("Błąd usuwania przepisu");
                    cantDeleteRecipe.setHeaderText("Nie można usunąć przepisu: " + recipesInRecipesDatabaseList.getSelectionModel().getSelectedItem());
                    cantDeleteRecipe.setContentText("Przepis podlega aktualnie edycji, a edytowane przepisy nie mogą być usuwane.");
                    cantDeleteRecipe.showAndWait();
                }
            }
        });

        Button editRecipeInRecipesDatabaseButton = new Button("Edytuj Przepis");
        editRecipeInRecipesDatabaseButton.setMaxWidth(Double.MAX_VALUE);

        editRecipeInRecipesDatabaseButton.setOnAction(event -> {
            if (!isEditionTurnOn && markedRecipe!=null) {
                isEditionTurnOn = true;
                inEdit = RecipesList.getRecipe(recipesInRecipesDatabaseList.getSelectionModel().getSelectedItem());
                showNewEditMenu(RecipesList.getRecipe(recipesInRecipesDatabaseList.getSelectionModel().getSelectedItem()));
            } else if (markedRecipe == null) {
                Alert chooseRecipeToEdit = new Alert(Alert.AlertType.INFORMATION);
                chooseRecipeToEdit.setTitle("Wybierz przepis do edycji");
                chooseRecipeToEdit.setHeaderText(null);
                chooseRecipeToEdit.setContentText("Przepis do edycji należy wybrać z listy powyżej.");
                chooseRecipeToEdit.showAndWait();
            } else {
                Alert cantEditRecipe = new Alert(Alert.AlertType.ERROR);
                cantEditRecipe.setTitle("Błąd edycji przepisu");
                cantEditRecipe.setHeaderText("Nie można edytować przepisu: " + recipesInRecipesDatabaseList.getSelectionModel().getSelectedItem());
                cantEditRecipe.setContentText("W jednym momencie może być tworzony albo edytowany tylko jeden przepis");
                cantEditRecipe.showAndWait();
            }
        });

        HBox downButtonsInRecipesDatabase = new HBox();
        downButtonsInRecipesDatabase.setAlignment(Pos.CENTER);
        HBox.setHgrow(addRecipeInRecipesDatabaseButton, Priority.ALWAYS);
        HBox.setHgrow(editRecipeInRecipesDatabaseButton, Priority.ALWAYS);
        HBox.setHgrow(removeRecipeInRecipesDatabaseButton, Priority.ALWAYS);
        downButtonsInRecipesDatabase.getChildren().add(addRecipeInRecipesDatabaseButton);
        downButtonsInRecipesDatabase.getChildren().add(editRecipeInRecipesDatabaseButton);
        downButtonsInRecipesDatabase.getChildren().add(removeRecipeInRecipesDatabaseButton);

        RecipesList.getObservableList(recipesInRecipesDatabaseList);

        recipesDatabaseGridPane.add(searchInRecipesDatabaseLabel, 0, 0, 4, 1);
        recipesDatabaseGridPane.add(searchInRecipesDatabase, 0, 1, 3, 1);
        recipesDatabaseGridPane.add(searchingOptionsInRecipesDatabaseButton, 3, 1, 1, 1);
        recipesDatabaseGridPane.add(recipesInRecipesDatabaseList, 0, 2, 2, 11);
        recipesDatabaseGridPane.add(downButtonsInRecipesDatabase, 0, 13, 4, 1);
        recipesDatabaseGridPane.add(linkedRecipesInRecipesDatabaseLabel, 2, 2, 2, 1);
        recipesDatabaseGridPane.add(linkedRecipesInRecipesDatabaseComboBox, 2, 3, 2, 1);
        recipesDatabaseGridPane.add(addLinkedRecipeInRecipesDatabaseButton, 2, 4, 1, 1);
        recipesDatabaseGridPane.add(removeLinkedRecipeInRecipesDatabaseButton, 3, 4, 1, 1);

        recipesDatabaseTab.setContent(recipesDatabaseGridPane);
        mainTable.getTabs().add(recipesDatabaseTab);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //TWORZENIE KARTY SKLADNIKOW
        ingredientsDatabaseTab = new Tab("Składniki");
        GridPane ingredientsDatabaseGridPane = new GridPane();
        ColumnConstraints columnInIgredientsDatabase = new ColumnConstraints();
        ingredientsDatabaseGridPane.setVgap(5);
        ingredientsDatabaseGridPane.setHgap(5);
        columnInIgredientsDatabase.setPercentWidth(25);
        ingredientsDatabaseGridPane.getColumnConstraints().add(columnInIgredientsDatabase);
        ingredientsDatabaseGridPane.getColumnConstraints().add(columnInIgredientsDatabase);
        ingredientsDatabaseGridPane.getColumnConstraints().add(columnInIgredientsDatabase);
        ingredientsDatabaseGridPane.getColumnConstraints().add(columnInIgredientsDatabase);

        RowConstraints rowInIngredientsDatabase = new RowConstraints();
        rowInIngredientsDatabase.setPercentHeight(7);
        for (int i = 0; i < 14; i++) {
            ingredientsDatabaseGridPane.getRowConstraints().add(rowInIngredientsDatabase);
        }

        ingredientsInIngredientsDatabaseList = new ListView<>();
        ingredientsInIngredientsDatabaseList.setMaxWidth(Double.MAX_VALUE);
        ingredientsInIngredientsDatabaseList.setMaxHeight(Double.MAX_VALUE);

        Label ingredientNameInIngredientsDatabaseLabel = new Label("Nazwa Składnika:");
        ingredientNameInIngredientsDatabaseLabel.setMaxHeight(Double.MAX_VALUE);
        ingredientNameInIngredientsDatabaseLabel.setMaxWidth(Double.MAX_VALUE);
        ingredientNameInIngredientsDatabaseLabel.setAlignment(Pos.CENTER);

        TextField ingredientNameInIngredientsDatabaseTextField = new TextField();
        ingredientNameInIngredientsDatabaseTextField.setMaxWidth(Double.MAX_VALUE);
        //ingredientNameInIngredientsDatabaseTextField.setMaxHeight(Double.MAX_VALUE);

        Button addIngredientInIngredientsDatabaseButton = new Button("Dodaj");
        //addIngredientInIngredientsDatabaseButton.setMaxHeight(Double.MAX_VALUE);
        addIngredientInIngredientsDatabaseButton.setMaxWidth(Double.MAX_VALUE);

        addIngredientInIngredientsDatabaseButton.setOnAction(event -> {
            if (!ingredientNameInIngredientsDatabaseTextField.getText().equals("")) {
                Ingredient toAdd = new Ingredient(ingredientNameInIngredientsDatabaseTextField.getText());
                if (toAdd.getName().charAt(toAdd.getName().length() - 1) != '/') {
                    IngredientsList.addIngredient(toAdd);

                    ingredientNameInIngredientsDatabaseTextField.setText("");
                    IngredientsList.rebuildModel(ingredientsInIngredientsDatabaseList);
                    IngredientsList.getObservableCollection();
                    chooseIngredientsInSearchComboBox.setItems(IngredientsList.getObservableCollection());
                    ingredientsInNewEditMenuComboBox.setItems(IngredientsList.getObservableCollection());
                    spareIngredientsInIngredientsDatabaseComboBox.setItems(IngredientsList.getObservableCollection());

                }
            }
        });

        Button removeIngredientInIngredientsDatabaseButton = new Button("Usuń");
        //removeIngredientInIngredientsDatabaseButton.setMaxHeight(Double.MAX_VALUE);
        removeIngredientInIngredientsDatabaseButton.setMaxWidth(Double.MAX_VALUE);

        removeIngredientInIngredientsDatabaseButton.setOnAction(event -> {
            boolean ifExist = false;
            ArrayList<String> recipesContainIngredient = new ArrayList<>();
            for (int i = 0; i < RecipesList.size(); i++) {
                for (int j = 0; j < RecipesList.recipesList.get(i).getSize(); j++) {
                    if(ingredientsInIngredientsDatabaseList.getSelectionModel().getSelectedItem().equals(RecipesList.recipesList.get(i).getIngredient(j).getName())) {
                        ifExist = true;
                        recipesContainIngredient.add(RecipesList.recipesList.get(i).getName());
                    }
                }
            }
            if(!ifExist) {
                IngredientsList.removeIngredient(ingredientsInIngredientsDatabaseList.getSelectionModel().getSelectedItem());
                IngredientsList.rebuildModel(ingredientsInIngredientsDatabaseList);
                ingredientsInNewEditMenuComboBox.setItems(IngredientsList.getObservableCollection());
                chooseIngredientsInSearchComboBox.setItems(IngredientsList.getObservableCollection());
                spareIngredientsInIngredientsDatabaseComboBox.setItems(IngredientsList.getObservableCollection());
            }
            else {
                Alert cantCreateRecipe = new Alert(Alert.AlertType.ERROR);
                cantCreateRecipe.setTitle("Błąd usuwania składnika");
                cantCreateRecipe.setHeaderText("Nie można usunąć składnika");
                String cantdelete = "";
                for(String i : recipesContainIngredient) {
                    cantdelete+= i;
                }
                cantCreateRecipe.setContentText("Składnik jest wykorzystywany w przepisach na:" + WhatToCook.endl + cantdelete);
                cantCreateRecipe.showAndWait();
            }
        });

        Label spareIngredientsInIngredientsDatabaseLabel = new Label("Składniki Alternatywne:");
        spareIngredientsInIngredientsDatabaseLabel.setMaxHeight(Double.MAX_VALUE);
        spareIngredientsInIngredientsDatabaseLabel.setMaxWidth(Double.MAX_VALUE);
        spareIngredientsInIngredientsDatabaseLabel.setAlignment(Pos.CENTER);

        ListView<String> spareIngredientsInIngredientsDatabaseList = new ListView<>();
        spareIngredientsInIngredientsDatabaseList.setMaxWidth(Double.MAX_VALUE);
        spareIngredientsInIngredientsDatabaseList.setMaxHeight(Double.MAX_VALUE);

        ingredientsInIngredientsDatabaseList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            SpareIngredientsList.rebuildListModel(spareIngredientsInIngredientsDatabaseList, new Ingredient(ingredientsInIngredientsDatabaseList.getSelectionModel().getSelectedItem()));
        });

        Button addSpareIngredientInIngredientsDatabaseButton = new Button("Dodaj");
        addSpareIngredientInIngredientsDatabaseButton.setMaxWidth(Double.MAX_VALUE);

        addSpareIngredientInIngredientsDatabaseButton.setOnAction(event -> {
            if (spareIngredientsInIngredientsDatabaseComboBox.getSelectionModel().getSelectedItem() != null) {
                boolean exist = false;
                for (int i = 0; i < spareIngredientsInIngredientsDatabaseList.getItems().size(); i++)
                    if (spareIngredientsInIngredientsDatabaseList.getItems().get(i).equals(spareIngredientsInIngredientsDatabaseComboBox.getSelectionModel().getSelectedItem()))
                        exist = true;
                if (!exist) {
                    //spareIngredientsInIngredientsDatabaseList.getItems().add(spareIngredientsInIngredientsDatabaseComboBox.getSelectionModel().getSelectedItem());
                    SpareIngredientsList.addSpareIngredient(new Ingredient(spareIngredientsInIngredientsDatabaseComboBox.getSelectionModel().getSelectedItem()), new Ingredient(ingredientsInIngredientsDatabaseList.getSelectionModel().getSelectedItem()));
                    SpareIngredientsList.rebuildListModel(spareIngredientsInIngredientsDatabaseList, new Ingredient(ingredientsInIngredientsDatabaseList.getSelectionModel().getSelectedItem()));
                    IngredientsList.rewriteFile();
                }
            }
        });

        Button removeSpareIngredientInIngredientsDatabaseButton = new Button("Usuń");
        removeSpareIngredientInIngredientsDatabaseButton.setMaxWidth(Double.MAX_VALUE);

        removeSpareIngredientInIngredientsDatabaseButton.setOnAction(event -> {
            if (spareIngredientsInIngredientsDatabaseList.getSelectionModel().getSelectedIndex() >= 0) {
                SpareIngredientsList.removeSpareIngredient(new Ingredient(spareIngredientsInIngredientsDatabaseList.getSelectionModel().getSelectedItem()), new Ingredient(ingredientsInIngredientsDatabaseList.getSelectionModel().getSelectedItem()));
                spareIngredientsInIngredientsDatabaseList.getItems().remove(spareIngredientsInIngredientsDatabaseList.getSelectionModel().getSelectedIndex());
                IngredientsList.rewriteFile();
            }
        });

        spareIngredientsInIngredientsDatabaseComboBox = new ComboBox<>();
        spareIngredientsInIngredientsDatabaseComboBox.setMaxWidth(Double.MAX_VALUE);

        spareIngredientsInIngredientsDatabaseComboBox.setItems(IngredientsList.getObservableCollection());

        IngredientsList.rebuildModel(ingredientsInIngredientsDatabaseList);


        ingredientsDatabaseGridPane.add(ingredientsInIngredientsDatabaseList, 0, 0, 2, 14);
        ingredientsDatabaseGridPane.add(ingredientNameInIngredientsDatabaseLabel, 2, 0, 2, 1);
        ingredientsDatabaseGridPane.add(ingredientNameInIngredientsDatabaseTextField, 2, 1, 2, 1);
        ingredientsDatabaseGridPane.add(addIngredientInIngredientsDatabaseButton, 2, 2, 1, 1);
        ingredientsDatabaseGridPane.add(removeIngredientInIngredientsDatabaseButton, 3, 2, 1, 1);
        ingredientsDatabaseGridPane.add(spareIngredientsInIngredientsDatabaseLabel, 2, 3, 2, 1);
        ingredientsDatabaseGridPane.add(spareIngredientsInIngredientsDatabaseComboBox, 2, 4, 1, 2);
        ingredientsDatabaseGridPane.add(addSpareIngredientInIngredientsDatabaseButton, 3, 4, 1, 1);
        ingredientsDatabaseGridPane.add(removeSpareIngredientInIngredientsDatabaseButton, 3, 5, 1, 1);
        ingredientsDatabaseGridPane.add(spareIngredientsInIngredientsDatabaseList, 2, 6, 2, 8);

        ingredientsDatabaseTab.setContent(ingredientsDatabaseGridPane);
        ingredientsDatabaseTab.setClosable(false);
        mainTable.getTabs().add(ingredientsDatabaseTab);
        mainLayout.setCenter(mainTable);
        mainLayout.setTop(mainMenu);
        Scene mainScene = new Scene(mainLayout, 500, 650);
        primaryStage.setMinHeight(650);
        primaryStage.setMinWidth(400);
        mainScene.getStylesheets().add(MainStage.class.getResource("css/style.css").toExternalForm());
        primaryStage.setScene(mainScene);
        primaryStage.show();

    }
/*
    private void showRecipe(Recipe recipeToShow) {
        Tab showRecipeTab = new Tab(recipeToShow.getName());
        BorderPane showRecipeMainBorderPane = new BorderPane();
        TextArea recipeTextArea = new TextArea();
        String toShow = "";
        toShow += "Przepis: " + recipeToShow.getName() + WhatToCook.endl + WhatToCook.endl + WhatToCook.endl;
        toShow += "Łatwość przygotowania: ";
        if (recipeToShow.getParameters().getPreparingEase() == 0)
            toShow += "Łatwo";
        if (recipeToShow.getParameters().getPreparingEase() == 1)
            toShow += "Średnio";
        if (recipeToShow.getParameters().getPreparingEase() == 2)
            toShow += "Trudno";
        toShow += WhatToCook.endl + WhatToCook.endl;
        toShow += "Czas przygotowania: ";
        if (recipeToShow.getParameters().getPreparingTime() == 0)
            toShow += "Krótko";
        if (recipeToShow.getParameters().getPreparingTime() == 1)
            toShow += "Średnio";
        if (recipeToShow.getParameters().getPreparingTime() == 2)
            toShow += "Długo";
        toShow += WhatToCook.endl + WhatToCook.endl;
        toShow += "Danie na: ";
        if (recipeToShow.getParameters().getParameters()[0])
            toShow += " Śniadanie";
        if (recipeToShow.getParameters().getParameters()[1])
            toShow += " Deser";
        if (recipeToShow.getParameters().getParameters()[2])
            toShow += " Obiad";
        if (recipeToShow.getParameters().getParameters()[3])
            toShow += " Kolację";
        if (recipeToShow.getParameters().getParameters()[4])
            toShow += " Przekąskę";
        toShow += WhatToCook.endl + WhatToCook.endl;
        toShow += "Składniki: " + WhatToCook.endl;
        for (int i = 0; i < recipeToShow.getSize(); i++) {
            toShow += recipeToShow.getIngredient(i).toString() + " " + recipeToShow.getAmount(i) + " " + recipeToShow.getUnit(i) + WhatToCook.endl;
        }
        toShow += WhatToCook.endl + WhatToCook.endl;
        toShow += "Instrukcja przygotowania: " + WhatToCook.endl;
        toShow += recipeToShow.getRecipe();


        ContextMenu showRecipeContexMenu = new ContextMenu();
        MenuItem addToShoppingList = new MenuItem("Dodaj do zakupów");
        addToShoppingList.setOnAction(event -> {
            for (int i = 0; i < recipeToShow.getSize(); i++) {
                Ingredient ingredient = recipeToShow.getIngredient(i);
                boolean contain = false;
                for (int j = 0; j < ingredientsInSearchList.getItems().size(); j++) {
                    if (ingredientsInSearchList.getItems().get(j).equals(ingredient.getName()))
                        contain = true;
                }
                if (!contain)
                    ToBuyIngredientsList.add(recipeToShow.getIngredient(i));
            }
        });
        MenuItem closeRecipeTab = new MenuItem("Zamknij");
        closeRecipeTab.setOnAction(event -> mainTable.getTabs().remove(showRecipeTab));
        showRecipeContexMenu.getItems().add(addToShoppingList);
        showRecipeContexMenu.getItems().add(new SeparatorMenuItem());
        showRecipeContexMenu.getItems().add(closeRecipeTab);


        recipeTextArea.setContextMenu(showRecipeContexMenu);
        recipeTextArea.setWrapText(true);
        recipeTextArea.setText(toShow);
        recipeTextArea.setEditable(false);
        showRecipeMainBorderPane.setCenter(recipeTextArea);
        showRecipeTab.setContent(showRecipeMainBorderPane);
        mainTable.getTabs().add(showRecipeTab);
        mainTable.getSelectionModel().select(showRecipeTab);
    }*/

    public void showRecipe(Recipe toShow) {
        GridPane showRecipeGridPane = new GridPane();
        //showRecipeGridPane.setGridLinesVisible(true);
        ColumnConstraints columnInShowRecipe = new ColumnConstraints();
        columnInShowRecipe.setPercentWidth(25);
        RowConstraints rowInShowRecipe = new RowConstraints();
        rowInShowRecipe.setPercentHeight(10);
        for(int i = 0; i < 4; i++)
            showRecipeGridPane.getColumnConstraints().add(columnInShowRecipe);
        for(int i = 0; i < 10;i++)
            showRecipeGridPane.getRowConstraints().add(rowInShowRecipe);

        TextArea instructionsInRecipe = new TextArea();
        instructionsInRecipe.setEditable(false);
        instructionsInRecipe.setWrapText(true);
        instructionsInRecipe.setText(toShow.getRecipe());

        showRecipeGridPane.add(instructionsInRecipe,0,6,4,6);

        Label youWillNeedLabel = new Label("Będziesz potrzebować:");
        youWillNeedLabel.setMaxWidth(Double.MAX_VALUE);
        youWillNeedLabel.setAlignment(Pos.CENTER);
        youWillNeedLabel.setTextAlignment(TextAlignment.CENTER);
        String tmp = "";
        if (toShow.getParameters().getPreparingEase() == 0)
            tmp = "Łatwo";
        if (toShow.getParameters().getPreparingEase() == 1)
            tmp = "Średnio";
        if (toShow.getParameters().getPreparingEase() == 2)
            tmp = "Trudno";
        Label preparingEaseLabel = new Label("Łatwość przygotowywania: "+ tmp);
        preparingEaseLabel.setMaxWidth(Double.MAX_VALUE);
        preparingEaseLabel.setAlignment(Pos.CENTER);
        preparingEaseLabel.setTextAlignment(TextAlignment.CENTER);
        tmp = "";
        if (toShow.getParameters().getPreparingTime() == 0)
            tmp = "Krótko";
        if (toShow.getParameters().getPreparingTime() == 1)
            tmp = "Średnio";
        if (toShow.getParameters().getPreparingTime() == 2)
            tmp = "Długo";
        Label preparingTimeLabel = new Label("Czas przygotowywania: " + tmp);
        preparingTimeLabel.setMaxWidth(Double.MAX_VALUE);
        preparingTimeLabel.setAlignment(Pos.CENTER);
        preparingTimeLabel.setTextAlignment(TextAlignment.CENTER);

        Label howToPrepareLabel = new Label("Przygotowanie:");
        howToPrepareLabel.setMaxWidth(Double.MAX_VALUE);
        howToPrepareLabel.setAlignment(Pos.CENTER);
        howToPrepareLabel.setTextAlignment(TextAlignment.CENTER);

        Label seeAlso = new Label("Zobacz również:");
        seeAlso.setMaxWidth(Double.MAX_VALUE);
        seeAlso.setAlignment(Pos.CENTER);
        seeAlso.setTextAlignment(TextAlignment.CENTER);

        ComboBox<String> linkedRecipesInShowRecipe = new ComboBox<>();

        ObservableList<String> linkedRecipesList = FXCollections.observableArrayList();

        for(int i = 0; i < toShow.getLinkedRecipes().size();i++) {
            linkedRecipesList.add(toShow.getLinkedRecipes().get(i));
        }
        linkedRecipesInShowRecipe.setItems(linkedRecipesList);

        linkedRecipesInShowRecipe.setMaxWidth(Double.MAX_VALUE);
        Button openLinkedRecipe = new Button("Otwórz");
        openLinkedRecipe.setMaxWidth(Double.MAX_VALUE);
        openLinkedRecipe.setAlignment(Pos.CENTER);
        openLinkedRecipe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(linkedRecipesInShowRecipe.getSelectionModel().getSelectedItem()!=null) {
                    showRecipe(RecipesList.getRecipe(linkedRecipesInShowRecipe.getSelectionModel().getSelectedItem()));
                }
            }
        });
        showRecipeGridPane.add(youWillNeedLabel,0,0,2,1);
        showRecipeGridPane.add(preparingTimeLabel,2,1,2,1);
        showRecipeGridPane.add(preparingEaseLabel,2,2,2,1);
        if(toShow.getLinkedRecipes().size()>0) {
            showRecipeGridPane.add(seeAlso, 2, 3, 2, 1);
            showRecipeGridPane.add(linkedRecipesInShowRecipe, 2, 4, 1, 1);
            showRecipeGridPane.add(openLinkedRecipe, 3, 4, 1, 1);
        }
        showRecipeGridPane.add(howToPrepareLabel,0,5,2,1);

        ListView<String> ingredientsInShowRecipeList = new ListView<>();

        ObservableList<String> ingredientsInShowRecipeObservableList = FXCollections.observableArrayList();

        for(int i = 0; i < toShow.getSize();i++)
            ingredientsInShowRecipeObservableList.add(toShow.getIngredient(i).toString());
        ingredientsInShowRecipeList.setItems(ingredientsInShowRecipeObservableList);

        showRecipeGridPane.add(ingredientsInShowRecipeList,0,1,2,3);

        Button addToShoppingList = new Button("Dodaj brakujące do zakupów");
        addToShoppingList.setMaxWidth(Double.MAX_VALUE);
        addToShoppingList.setOnAction(event -> {
            for (int i = 0; i < toShow.getSize(); i++) {
                Ingredient ingredient = toShow.getIngredient(i);
                boolean contain = false;
                for (int j = 0; j < ingredientsInSearchList.getItems().size(); j++) {
                    if (ingredientsInSearchList.getItems().get(j).equals(ingredient.getName()))
                        contain = true;
                }
                if (!contain)
                    ToBuyIngredientsList.add(toShow.getIngredient(i));
            }
        });

        showRecipeGridPane.add(addToShoppingList,0,4,2,1);

        Tab showRecipeTab = new Tab(toShow.getName());
        showRecipeTab.setClosable(true);
        showRecipeTab.setContent(showRecipeGridPane);
        mainTable.getTabs().add(showRecipeTab);
        mainTable.getSelectionModel().select(showRecipeTab);
    }
    private boolean isFalse(boolean parameters[], int n) {
        for (int i = 0; i < n; i++) {
            if (parameters[i]) {
                return false;
            }
        }

        return true;
    }

    private void showNewEditMenu(Recipe recipe) {
        Tab newEditMenuTab;
        if (recipe != null) {
            newEditMenuTab = new Tab(recipe.getName());
        } else {
            newEditMenuTab = new Tab("Nowy Przepis");
        }
        GridPane mainGridNewEditMenuTab = new GridPane();
        ColumnConstraints columnInNewEditMenu = new ColumnConstraints();
        columnInNewEditMenu.setPercentWidth(25);
        mainGridNewEditMenuTab.getColumnConstraints().add(columnInNewEditMenu);
        mainGridNewEditMenuTab.getColumnConstraints().add(columnInNewEditMenu);
        mainGridNewEditMenuTab.getColumnConstraints().add(columnInNewEditMenu);
        mainGridNewEditMenuTab.getColumnConstraints().add(columnInNewEditMenu);

        RowConstraints rowInIngredientsDatabase = new RowConstraints();
        rowInIngredientsDatabase.setPercentHeight(7);
        for (int i = 0; i < 16; i++) {
            mainGridNewEditMenuTab.getRowConstraints().add(rowInIngredientsDatabase);
        }

        Label recipeNameLabel = new Label("Podaj nazwę przepisu: ");
        recipeNameLabel.setMaxWidth(Double.MAX_VALUE);
        recipeNameLabel.setAlignment(Pos.CENTER);

        ListView<String> ingredientsInNewEditMenuList = new ListView<>();

        Label instructionsNewEditMenuLabel = new Label("Napisz instrukcję przygotowywania posiłku");
        instructionsNewEditMenuLabel.setMaxWidth(Double.MAX_VALUE);
        instructionsNewEditMenuLabel.setAlignment(Pos.CENTER);
        TextArea instructionsNewEditMenuTextArea = new TextArea();
        instructionsNewEditMenuTextArea.setMaxHeight(Double.MAX_VALUE);
        instructionsNewEditMenuTextArea.setMaxWidth(Double.MAX_VALUE);
        instructionsNewEditMenuTextArea.setWrapText(true);

        Button saveAndExitButton = new Button("Zapisz i wyjdź");
        saveAndExitButton.setMaxWidth(Double.MAX_VALUE);

        Button exitWithoutSavingButton = new Button("Wyjdź bez zapisywania");
        exitWithoutSavingButton.setMaxWidth(Double.MAX_VALUE);

        exitWithoutSavingButton.setOnAction(event -> {
            isEditionTurnOn = false;
            mainTable.getTabs().remove(newEditMenuTab);
            inEdit = null;
        });

        TextField recipeNameTextField = new TextField();

        //PANEL Z DWIEMA ZAKLADKAMI
        TabPane newEditMenuTabPane = new TabPane();
        Tab tabGeneral = new Tab("Składniki");
        tabGeneral.setClosable(false);
        Tab tabParameters = new Tab("Parametry");
        tabParameters.setClosable(false);

        GridPane ingredientsGridPane = new GridPane();
        GridPane parametersGridPane = new GridPane();

        ColumnConstraints columnInIngredientsGridPane = new ColumnConstraints();
        columnInIngredientsGridPane.setPercentWidth(50);
        ingredientsGridPane.getColumnConstraints().add(columnInIngredientsGridPane);
        ingredientsGridPane.getColumnConstraints().add(columnInIngredientsGridPane);

        RowConstraints rowInIngredientsGridPane = new RowConstraints();
        rowInIngredientsGridPane.setPercentHeight(16.6);
        ingredientsGridPane.getRowConstraints().add(rowInIngredientsGridPane);
        ingredientsGridPane.getRowConstraints().add(rowInIngredientsGridPane);
        ingredientsGridPane.getRowConstraints().add(rowInIngredientsGridPane);
        ingredientsGridPane.getRowConstraints().add(rowInIngredientsGridPane);
        ingredientsGridPane.getRowConstraints().add(rowInIngredientsGridPane);
        ingredientsGridPane.getRowConstraints().add(rowInIngredientsGridPane);

        Label chooseIngredientsLabel = new Label("Wybierz składniki");
        chooseIngredientsLabel.setMaxWidth(Double.MAX_VALUE);
        chooseIngredientsLabel.setAlignment(Pos.CENTER);
        Label ammountInNewEditMenuLabel = new Label("Ilość:");
        Label unitInNewEditMenuLabel = new Label("Jednostka:");

        TextField ammountInNewEditMenuTextField = new TextField();
        TextField unitInNewEditMenuTextField = new TextField();

        Button addIngredientInNewEditMenuButton = new Button("Dodaj składnik");
        addIngredientInNewEditMenuButton.setMaxWidth(Double.MAX_VALUE);
        Button removeIngredientInNewEditMenuButton = new Button("Usuń składnik");
        removeIngredientInNewEditMenuButton.setMaxWidth(Double.MAX_VALUE);
        ingredientsInNewEditMenuComboBox.setMaxWidth(Double.MAX_VALUE);
        ingredientsGridPane.add(chooseIngredientsLabel, 0, 0, 2, 1);
        ingredientsGridPane.add(ingredientsInNewEditMenuComboBox, 0, 1, 2, 1);
        ingredientsGridPane.add(ammountInNewEditMenuLabel, 0, 2, 1, 1);
        ingredientsGridPane.add(unitInNewEditMenuLabel, 0, 3, 1, 1);
        ingredientsGridPane.add(ammountInNewEditMenuTextField, 1, 2, 1, 1);
        ingredientsGridPane.add(unitInNewEditMenuTextField, 1, 3, 1, 1);
        ingredientsGridPane.add(addIngredientInNewEditMenuButton, 0, 4, 2, 1);
        ingredientsGridPane.add(removeIngredientInNewEditMenuButton, 0, 5, 2, 1);
        tabGeneral.setContent(ingredientsGridPane);

        parametersGridPane.getColumnConstraints().add(columnInIngredientsGridPane);
        parametersGridPane.getColumnConstraints().add(columnInIngredientsGridPane);

        RowConstraints rowInParametersGridPane = new RowConstraints();
        rowInParametersGridPane.setPercentHeight(10);
        for (int i = 0; i < 10; i++) {
            parametersGridPane.getRowConstraints().add(rowInParametersGridPane);
        }

        Label mealForLabel = new Label("Danie na:");
        mealForLabel.setMaxWidth(Double.MAX_VALUE);
        mealForLabel.setAlignment(Pos.CENTER);

        Label preparingTimeInNewEditMenuLabel = new Label("Czas przygotowania:");
        preparingTimeInNewEditMenuLabel.setMinHeight(0);
        preparingTimeInNewEditMenuLabel.setMaxWidth(Double.MAX_VALUE);
        preparingTimeInNewEditMenuLabel.setAlignment(Pos.CENTER);

        Label preparingEaseInNewEditMenuLabel = new Label("Latwość przygotowania:");
        preparingEaseInNewEditMenuLabel.setMinHeight(0);
        preparingEaseInNewEditMenuLabel.setMaxWidth(Double.MAX_VALUE);
        preparingEaseInNewEditMenuLabel.setAlignment(Pos.CENTER);

        CheckBox breakfestInNewEditCheckBox = new CheckBox("Sniadanie");
        breakfestInNewEditCheckBox.setMinHeight(0);
        CheckBox dinerInNewEditCheckBox = new CheckBox("Obiad");
        dinerInNewEditCheckBox.setMinHeight(0);
        CheckBox supperInNewEditCheckBox = new CheckBox("Kolację");
        supperInNewEditCheckBox.setMinHeight(0);
        CheckBox dessertInNewEdithCheckBox = new CheckBox("Deser");
        dessertInNewEdithCheckBox.setMinHeight(0);
        CheckBox snackInNewEditCheckBox = new CheckBox("Przekąskę");
        snackInNewEditCheckBox.setMinHeight(0);

        ComboBox<String> preparingTimeInNewEditComboBox = new ComboBox<>();
        preparingTimeInNewEditComboBox.setMinHeight(0);
        preparingTimeInNewEditComboBox.setMaxWidth(Double.MAX_VALUE);
        preparingTimeInNewEditComboBox.getItems().add("Szybko");
        preparingTimeInNewEditComboBox.getItems().add("Srednio");
        preparingTimeInNewEditComboBox.getItems().add("Wolno");
        preparingTimeInNewEditComboBox.getSelectionModel().select(0);
        ComboBox<String> preparingEaseInNewEditComboBox = new ComboBox<>();
        preparingEaseInNewEditComboBox.setMinHeight(0);
        preparingEaseInNewEditComboBox.setMaxWidth(Double.MAX_VALUE);
        preparingEaseInNewEditComboBox.getItems().add("Latwe");
        preparingEaseInNewEditComboBox.getItems().add("Srednie");
        preparingEaseInNewEditComboBox.getItems().add("Trudne");
        preparingEaseInNewEditComboBox.getSelectionModel().select(0);

        parametersGridPane.add(mealForLabel, 0, 0, 2, 1);
        parametersGridPane.add(breakfestInNewEditCheckBox, 0, 1, 2, 1);
        parametersGridPane.add(dinerInNewEditCheckBox, 0, 2, 2, 1);
        parametersGridPane.add(supperInNewEditCheckBox, 0, 3, 2, 1);
        parametersGridPane.add(dessertInNewEdithCheckBox, 0, 4, 2, 1);
        parametersGridPane.add(snackInNewEditCheckBox, 0, 5, 2, 1);
        parametersGridPane.add(preparingTimeInNewEditMenuLabel, 0, 6, 2, 1);
        parametersGridPane.add(preparingTimeInNewEditComboBox, 0, 7, 2, 1);
        parametersGridPane.add(preparingEaseInNewEditMenuLabel, 0, 8, 2, 1);
        parametersGridPane.add(preparingEaseInNewEditComboBox, 0, 9, 2, 1);

        tabParameters.setContent(parametersGridPane);
        newEditMenuTabPane.getTabs().add(tabGeneral);
        newEditMenuTabPane.getTabs().add(tabParameters);
        //KONIEC PANELU

        //TWORZENIE WIEKSZOSCI AKCJI
        ArrayList<ListHandler> ingredientsListInput = new ArrayList<>();
        addIngredientInNewEditMenuButton.setOnAction(event -> {
            if (ingredientsInNewEditMenuComboBox.getSelectionModel().getSelectedItem() != null) {
                String newForm = ingredientsInNewEditMenuComboBox.getSelectionModel().getSelectedItem();
                newForm += " " + ammountInNewEditMenuTextField.getText() + " " + unitInNewEditMenuTextField.getText();
                boolean exist = false;
                for (int i = 0; i < ingredientsInNewEditMenuList.getItems().size(); i++) {
                    if (newForm.equals(ingredientsInNewEditMenuList.getItems().get(i))) {
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    ingredientsInNewEditMenuList.getItems().add(newForm);
                    ingredientsListInput.add(new ListHandler(ingredientsInNewEditMenuComboBox.getSelectionModel().getSelectedItem(), ammountInNewEditMenuTextField.getText(), unitInNewEditMenuTextField.getText()));
                    ammountInNewEditMenuTextField.setText("");
                    unitInNewEditMenuTextField.setText("");
                }
            }
        });

        removeIngredientInNewEditMenuButton.setOnAction(event -> {
            ingredientsListInput.remove(ingredientsInNewEditMenuList.getSelectionModel().getSelectedIndex());
            ingredientsInNewEditMenuList.getItems().remove(ingredientsInNewEditMenuList.getSelectionModel().getSelectedIndex());
        });

        if (recipe != null) {
            recipeNameTextField.setText(recipe.getName());
            instructionsNewEditMenuTextArea.setText(recipe.getRecipe());
            for (int i = 0; i < recipe.getSize(); i++) {
                String toAdd;
                toAdd = recipe.getIngredient(i).getName() + " " + recipe.getAmount(i) + " " + recipe.getUnit(i);
                ingredientsInNewEditMenuList.getItems().add(toAdd);
                ingredientsListInput.add(new ListHandler(recipe.getIngredient(i).getName(), recipe.getAmount(i), recipe.getUnit(i)));
            }
            if (recipe.getParameters().getParameters()[0])
                breakfestInNewEditCheckBox.setSelected(true);
            if (recipe.getParameters().getParameters()[1])
                dessertInNewEdithCheckBox.setSelected(true);
            if (recipe.getParameters().getParameters()[2])
                dinerInNewEditCheckBox.setSelected(true);
            if (recipe.getParameters().getParameters()[3])
                supperInNewEditCheckBox.setSelected(true);
            if (recipe.getParameters().getParameters()[4])
                snackInNewEditCheckBox.setSelected(true);

            preparingEaseInNewEditComboBox.getSelectionModel().select(recipe.getParameters().getPreparingEase());
            preparingTimeInNewEditComboBox.getSelectionModel().select(recipe.getParameters().getPreparingTime());
        }

        saveAndExitButton.setOnAction(event -> {
            String name1 = recipeNameTextField.getText();
            String instructions = instructionsNewEditMenuTextArea.getText();
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            ArrayList<PairAmountUnit> ammountsAndUnits = new ArrayList<>();
            for (ListHandler handler : ingredientsListInput) {
                Ingredient ingredient;
                ingredient = new Ingredient(handler.getIngredient());
                ammountsAndUnits.add(new PairAmountUnit(handler.getAmmount(), handler.getUnit()));
                ingredients.add(ingredient);
            }
            boolean parameters[] = new boolean[5];
            parameters[0] = breakfestInNewEditCheckBox.isSelected();
            parameters[1] = dessertInNewEdithCheckBox.isSelected();
            parameters[2] = dinerInNewEditCheckBox.isSelected();
            parameters[3] = supperInNewEditCheckBox.isSelected();
            parameters[4] = snackInNewEditCheckBox.isSelected();
            Recipe newRecipe1 = new Recipe(name1, ingredients, ammountsAndUnits, instructions, new RecipeParameters(parameters, preparingEaseInNewEditComboBox.getSelectionModel().getSelectedIndex(), preparingTimeInNewEditComboBox.getSelectionModel().getSelectedIndex()));
            if (recipe == null) {
                if ((!name1.equals("")) && (!instructions.equals("")) && (!ingredients.isEmpty()) && (!RecipesList.isRecipe(newRecipe1))) {
                    RecipesList.add(newRecipe1);
                    recipesInRecipesDatabaseList.setItems(RecipesList.getObservableList(searchInRecipesDatabase.getText(), WhatToCook.caseSensitiveSearch, WhatToCook.searchInEveryWord));
                    isEditionTurnOn = false;
                    mainTable.getTabs().remove(newEditMenuTab);
                    mainTable.getSelectionModel().select(1);
                    linkedRecipesInRecipesDatabaseComboBox.setItems(RecipesList.getObservableList());
                    inEdit = null;
                } else {
                    Alert cantCreateRecipe = new Alert(Alert.AlertType.ERROR);
                    cantCreateRecipe.setTitle("Błąd tworzenia przepisu");
                    cantCreateRecipe.setHeaderText("Nie można utworzyć nowego przepisu.");
                    cantCreateRecipe.setContentText("Przepis musi zawierać nazwę, opis i conajmniej jeden składnik");
                    cantCreateRecipe.showAndWait();
                }
            } else if ((!name1.equals("")) && (!instructions.equals("")) && (!ingredients.isEmpty())) {
                if (recipe.getName().equals(name1) || (!RecipesList.isRecipe(newRecipe1))) {
                    RecipesList.remove(recipe.getName());
                    RecipesList.add(newRecipe1);
                    recipesInRecipesDatabaseList.setItems(RecipesList.getObservableList(searchInRecipesDatabase.getText(), WhatToCook.caseSensitiveSearch, WhatToCook.searchInEveryWord));
                    isEditionTurnOn = false;
                    mainTable.getTabs().remove(newEditMenuTab);
                    mainTable.getSelectionModel().select(1);
                    linkedRecipesInRecipesDatabaseComboBox.setItems(RecipesList.getObservableList());
                    inEdit = null;
                } else {
                    Alert cantCreateRecipe = new Alert(Alert.AlertType.ERROR);
                    cantCreateRecipe.setTitle("Błąd tworzenia przepisu");
                    cantCreateRecipe.setHeaderText("Nie można utworzyć nowego przepisu.");
                    cantCreateRecipe.setContentText("Przepis musi zawierać nazwę, opis i conajmniej jeden składnik");
                    cantCreateRecipe.showAndWait();
                }
            }


        });

        //DODAWANIE DO GLOWNEGO LAYOUTU
        mainGridNewEditMenuTab.add(recipeNameLabel, 0, 0, 2, 1);
        mainGridNewEditMenuTab.add(recipeNameTextField, 2, 0, 2, 1);
        mainGridNewEditMenuTab.add(ingredientsInNewEditMenuList, 0, 1, 2, 7);
        mainGridNewEditMenuTab.add(instructionsNewEditMenuLabel, 0, 8, 4, 1);
        mainGridNewEditMenuTab.add(instructionsNewEditMenuTextArea, 0, 9, 4, 6);
        mainGridNewEditMenuTab.add(saveAndExitButton, 0, 15, 2, 1);
        mainGridNewEditMenuTab.add(exitWithoutSavingButton, 2, 15, 2, 1);
        mainGridNewEditMenuTab.add(newEditMenuTabPane, 2, 1, 2, 7);

        newEditMenuTab.setContent(mainGridNewEditMenuTab);
        newEditMenuTab.setClosable(false);
        mainTable.getTabs().add(mainCardsCount, newEditMenuTab);
        mainTable.getSelectionModel().select(newEditMenuTab);
    }

    @SuppressWarnings ("ConstantConditions")
    private void refreshLinkedRecipes() {
        linkedRecipesToggleGroup.getToggles().removeAll();
        RadioButton linkedRecipeButton;
        linkedRecipesGridPane.getChildren().clear();
        String temp;
        if(markedRecipe!=null) {
            for (int i = 0; i < RecipesList.getRecipe(markedRecipe).getLinkedRecipes().size(); i++) {
                temp = RecipesList.getRecipe(markedRecipe).getLinkedRecipes().get(i);
                linkedRecipeButton = new RadioButton(temp);
                linkedRecipesToggleGroup.getToggles().add(linkedRecipeButton);
                linkedRecipesGridPane.add(linkedRecipeButton, 0, i, 2, 1);
                linkedRecipesAmmmount.setText(RecipesList.getRecipe(markedRecipe).getLinkedRecipes().size() + "/6");
            }
        }
    }

    private GridPane linkedRecipesGridPane;

    private TabPane mainTable;

    private Tab searchingTab;
    private Tab recipesDatabaseTab;
    private Tab ingredientsDatabaseTab;

    private ListView<String> ingredientsInSearchList;
    private ListView<String> foundRecipesInSearchList;
    private ListView<String> recipesInRecipesDatabaseList;
    private ListView<String> ingredientsInIngredientsDatabaseList;

    private ComboBox<String> chooseIngredientsInSearchComboBox;
    private ComboBox<String> spareIngredientsInIngredientsDatabaseComboBox;
    private ComboBox<String> ingredientsInNewEditMenuComboBox;
    private ComboBox<String> preparingTimeInSearchComboBox;
    private ComboBox<String> preparingEaseInSearchComboBox;
    private ComboBox<String> linkedRecipesInRecipesDatabaseComboBox;

    private CheckBox breakfestInSearchCheckBox;
    private CheckBox dinerInSearchCheckBox;
    private CheckBox supperInSearchCheckBox;
    private CheckBox snackInSearchCheckBox;
    private CheckBox dessertInSearchCheckBox;
    private CheckBox spareIngredientsCheckBox;

    private Recipe inEdit = null;

    private boolean isEditionTurnOn = false;

    private TextField searchInRecipesDatabase;

    private int mainCardsCount = 3;

    private String markedRecipe = null;

    private ToggleGroup linkedRecipesToggleGroup = new ToggleGroup();

    private Label linkedRecipesAmmmount;

}
