package gui;

import core.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by Mateusz on 20.03.2016.
 */
public class MainWindow extends JFrame {

    public MainWindow() {
        //MAIN WINDOW'S SETTINGS
        setSize(400, 600);
        setResizable(true);
        setTitle("WhatToCook");
        setMinimumSize(new Dimension(250, 300));
        //
        //PANELS INITIALIZATION

        mainTable = new JTabbedPane();
        mainBorderLayout = new JPanel(new BorderLayout());
        downBorderLayout = new JPanel(new BorderLayout());
        upBorderLayout = new JPanel(new BorderLayout());
        mainGridLayout = new JPanel(new GridLayout(2, 1));
        upGridLayout = new JPanel(new GridLayout(1, 2));
        upRightGridLayout = new JPanel(new GridLayout(4, 1));

        manageReceipesMainPanel = new JPanel(new BorderLayout());
        manageReceipesGridPanel = new JPanel(new GridLayout(1, 2));
        manageReceipesLeftBorderLayout = new JPanel(new BorderLayout());
        manageReceipesLeftUpGridPanel = new JPanel(new GridLayout(2, 1));
        manageReceipesLeftDownGridPanel = new JPanel(new GridLayout(1, 3));

        isEditionTurnOn = false;

        //MENUBAR CREATING/////////////////////////////////////////////////////////////////////////////////////////////
        mainMenu = new JMenuBar();
        setJMenuBar(mainMenu);
        fileMenu = new JMenu(WhatToCook.selectedLanguagePack.get(0));
        editMenu = new JMenu(WhatToCook.selectedLanguagePack.get(2));
        helpMenu = new JMenu(WhatToCook.selectedLanguagePack.get(5));
        mainMenu.add(fileMenu);
        mainMenu.add(editMenu);
        mainMenu.add(helpMenu);

        if (settingsDialog == null)
            settingsDialog = new SettingsWindow(MainWindow.this);
        if (aboutDialog == null)
            aboutDialog = new AboutWindow(MainWindow.this);
        if(errorDialog == null)
            errorDialog = new ErrorWindow(MainWindow.this);

        Action settingsAction = new AbstractAction(WhatToCook.selectedLanguagePack.get(6)) {
            public void actionPerformed(ActionEvent event) {
                settingsDialog.setVisible(true);
            }
        };

        Action exitAction = new AbstractAction(WhatToCook.selectedLanguagePack.get(1)) {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        };
        Action clearIngredientsAction = new AbstractAction(WhatToCook.selectedLanguagePack.get(3)) {
            public void actionPerformed(ActionEvent event) {
                int i = ingredientsInputListModel.getSize() - 1;
                for (; i >= 0; i--)
                    ingredientsInputListModel.removeElementAt(i);

            }
        };
        Action clearReceipesAction = new AbstractAction(WhatToCook.selectedLanguagePack.get(4)) {
            public void actionPerformed(ActionEvent event) {
                int i = receipesOutputListModel.getSize() - 1;
                for (; i >= 0; i--)
                    receipesOutputListModel.removeElementAt(i);

            }
        };
        Action aboutAction = new AbstractAction(WhatToCook.selectedLanguagePack.get(7)) {
            public void actionPerformed(ActionEvent event) {
                aboutDialog.setVisible(true);
            }
        };
        fileMenu.add(exitAction);
        editMenu.add(clearIngredientsAction);
        editMenu.add(clearReceipesAction);
        editMenu.addSeparator();
        editMenu.add(settingsAction);
        helpMenu.add(aboutAction);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //OBJECTS INITIALIZATION////////////////////////////////////////////////////////////////////////////////////////
        //TEXT FIELDS
        ingredientInSearchComboBox = new JComboBox<>();
        IngredientsList.reloadComboBox(ingredientInSearchComboBox);
        //BUTTONS
        execute = new JButton(WhatToCook.selectedLanguagePack.get(15));
        execute.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                receipesOutputListModel.clear();
                ArrayList<Ingredient> ingredients = new ArrayList<>();
                for (int i = 0; i < ingredientsInputListModel.size(); i++) {
                    String name[] = ingredientsInputListModel.getElementAt(i).split(" ");
                    ingredients.add(new Ingredient(name[1]));
                }
                for (int i = 0; i < RecipesList.size(); i++) {
                    if (RecipesList.checkWithIngredientsList(ingredients, i))
                        receipesOutputListModel.addElement(RecipesList.getRecipeNameAtIndex(i));
                }
            }
        });
        addIngredientButton = new JButton(WhatToCook.selectedLanguagePack.get(12));
        addIngredientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String newForm = "● " + ingredientInSearchComboBox.getSelectedItem();
                boolean exist = false;
                for (int i = 0; i < ingredientsInputListModel.getSize(); i++) {
                    if ((newForm.equals(ingredientsInputListModel.get(i)))) {
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    ingredientsInputListModel.addElement(newForm);
                }
            }
        });
        removeIngredientButton = new JButton(WhatToCook.selectedLanguagePack.get(13));
        removeIngredientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int index = ingredientsInputList.getSelectedIndex();
                if (index >= 0) {
                    ingredientsInputListModel.removeElementAt(index);
                }
            }
        });
        //JLISTS
        receipesOutputListModel = new DefaultListModel<>();
        receipesOutputList = new JList<>();
        receipesOutputList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        receipesOutputList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        receipesOutputList.setVisibleRowCount(-1);
        receipesOutputList = new JList<>(receipesOutputListModel);
        receipesOutputListScrollPane = new JScrollPane(receipesOutputList);
        receipesOutputList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = receipesOutputList.getSelectedIndex();
                    int i;
                    for (i = 0; i < RecipesList.size(); i++) {
                        if (receipesOutputListModel.get(index).equals(RecipesList.recipesList.get(i).getName()))
                            break;
                    }
                    showRecipe(RecipesList.recipesList.get(i));
                }
            }
        });
        ingredientsInputListModel = new DefaultListModel<>();
        ingredientsInputList = new JList<>();
        ingredientsInputList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        ingredientsInputList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        ingredientsInputList.setVisibleRowCount(-1);
        ingredientsInputList = new JList<>(ingredientsInputListModel);
        ingredientsInputListScrollPane = new JScrollPane(ingredientsInputList);
        //MANAGE RECEIPES CREATING SECTION

        searchForReceipesTextArea = new JTextField();

        newRecipe = new JButton(WhatToCook.selectedLanguagePack.get(17));
        newRecipe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isEditionTurnOn) {

                    isEditionTurnOn = true;
                    showNewEditMenu();
                }
            }
        });
        editRecipe = new JButton(WhatToCook.selectedLanguagePack.get(18));
        editRecipe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String recipeName = receipesListModel.getElementAt(receipesList.getSelectedIndex());
                int index = RecipesList.getIndex(recipeName);
                if (!isEditionTurnOn) {

                    isEditionTurnOn = true;
                    showNewEditMenu(index);
                }
            }
        });
        deleteRecipe = new JButton(WhatToCook.selectedLanguagePack.get(31));
        deleteRecipe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String recipeName = receipesListModel.getElementAt(receipesList.getSelectedIndex());
                RecipesList.remove(recipeName);
                refreshGUILists();
            }
        });

        receipesListModel = new DefaultListModel<>();
        receipesList = new JList<>();
        receipesList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        receipesList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        receipesList.setVisibleRowCount(-1);
        receipesList = new JList<>(receipesListModel);
        receipesListScrollPane = new JScrollPane(receipesList);
        receipesList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = receipesList.getSelectedIndex();
                    int i;
                    for (i = 0; i < RecipesList.recipesList.size(); i++) {
                        if (receipesListModel.get(index).equals(RecipesList.recipesList.get(i).getName()))
                            break;
                    }
                    showRecipe(RecipesList.recipesList.get(i));
                }
            }
        });

        manageReceipesLeftUpGridPanel.add(new JLabel(WhatToCook.selectedLanguagePack.get(16), SwingConstants.CENTER));
        manageReceipesLeftUpGridPanel.add(searchForReceipesTextArea);
        manageReceipesLeftBorderLayout.add(manageReceipesLeftUpGridPanel, BorderLayout.NORTH);
        manageReceipesLeftDownGridPanel.add(newRecipe);
        manageReceipesLeftDownGridPanel.add(editRecipe);
        manageReceipesLeftDownGridPanel.add(deleteRecipe);
        manageReceipesLeftBorderLayout.add(manageReceipesLeftDownGridPanel, BorderLayout.SOUTH);

        manageReceipesLeftBorderLayout.add(receipesListScrollPane, BorderLayout.CENTER);

        manageReceipesGridPanel.add(manageReceipesLeftBorderLayout);

        manageReceipesMainPanel.add(manageReceipesGridPanel, BorderLayout.CENTER);

        //CREATING INGREDIENTS MANAGE WINDOW
        ingredientsMainGridLayout = new JPanel(new GridLayout(1, 2));
        ingredientsRightBorderLayout = new JPanel(new BorderLayout());
        ingredientsRightGridLayout = new JPanel(new GridLayout(4, 1));
        manageIngredientsInputListModel = new DefaultListModel<>();
        manageIngredientsInputList = new JList<>();
        manageIngredientsInputList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        manageIngredientsInputList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        manageIngredientsInputList.setVisibleRowCount(-1);
        manageIngredientsInputList = new JList<>(manageIngredientsInputListModel);
        manageIngredientsListScrollPane = new JScrollPane(manageIngredientsInputList);
        IngredientsList.rebuildModel(manageIngredientsInputListModel);
        newIngredientButton = new JButton(WhatToCook.selectedLanguagePack.get(29));
        newIngredientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean exist = false;
                if (!newIngredientTextField.getText().equals("")) {


                    Ingredient toAdd = new Ingredient(newIngredientTextField.getText());
                    IngredientsList.addIngredient(toAdd);
                }
                newIngredientTextField.setText("");
                IngredientsList.rebuildModel(manageIngredientsInputListModel);
                IngredientsList.reloadComboBox(ingredientInSearchComboBox);
                IngredientsList.reloadComboBox(ingredientInCreatingRecipeComboBox);
            }
        });
        newIngredientTextField = new JTextField();
        removeIngredientsButton = new JButton(WhatToCook.selectedLanguagePack.get(30));
        removeIngredientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean ifExist = false;
                ArrayList<String> recipesContainIngredient = new ArrayList<String>();
                for(int i = 0; i < RecipesList.size();i++)
                {
                    for(int j = 0; j < RecipesList.recipesList.get(i).getSize();j++)
                    {
                        if(manageIngredientsInputListModel.get(manageIngredientsInputList.getSelectedIndex()).equals(RecipesList.recipesList.get(i).getIngredient(j).getName())) {
                            ifExist = true;
                            recipesContainIngredient.add(RecipesList.recipesList.get(i).getName());
                        }
                    }
                }
                if(!ifExist) {
                    if (manageIngredientsInputList.getSelectedIndex() >= 0) {
                        IngredientsList.removeIngredient(manageIngredientsInputList.getSelectedValue());
                    }
                }
                else
                {
                    //JOptionPane.showConfirmDialog(null, WhatToCook.selectedLanguagePack.get(38), WhatToCook.selectedLanguagePack.get(39), JOptionPane.PLAIN_MESSAGE, JOptionPane.ERROR_MESSAGE);
                    errorDialog.refresh(recipesContainIngredient);
                    errorDialog.setVisible(true);
                }
                IngredientsList.rebuildModel(manageIngredientsInputListModel);
                IngredientsList.reloadComboBox(ingredientInSearchComboBox);
                IngredientsList.reloadComboBox(ingredientInCreatingRecipeComboBox);
            }
        });
        //KONIEC SEKCJI
        ingredientsRightGridLayout.add(new JLabel(WhatToCook.selectedLanguagePack.get(28), SwingConstants.HORIZONTAL));
        ingredientsRightGridLayout.add(newIngredientTextField);
        ingredientsRightGridLayout.add(newIngredientButton);
        ingredientsRightGridLayout.add(removeIngredientsButton);

        ingredientsRightBorderLayout.add(ingredientsRightGridLayout, BorderLayout.NORTH);
        ingredientsMainGridLayout.add(manageIngredientsListScrollPane);
        ingredientsMainGridLayout.add(ingredientsRightBorderLayout);
        //PANELS AND OBJECTS LOCATION
        upRightGridLayout.add(new JLabel(WhatToCook.selectedLanguagePack.get(11), SwingConstants.CENTER));
        upRightGridLayout.add(ingredientInSearchComboBox);
        upRightGridLayout.add(addIngredientButton);
        upRightGridLayout.add(removeIngredientButton);
        upGridLayout.add(ingredientsInputListScrollPane);
        upGridLayout.add(upRightGridLayout);
        downBorderLayout.add(new JLabel(WhatToCook.selectedLanguagePack.get(14), SwingConstants.CENTER), BorderLayout.NORTH);
        downBorderLayout.add(receipesOutputListScrollPane, BorderLayout.CENTER);
        downBorderLayout.add(execute, BorderLayout.SOUTH);
        upBorderLayout.add(new JLabel(WhatToCook.selectedLanguagePack.get(10), SwingConstants.CENTER), BorderLayout.NORTH);
        upBorderLayout.add(upGridLayout, BorderLayout.CENTER);
        mainGridLayout.add(upBorderLayout);
        mainGridLayout.add(downBorderLayout);
        mainBorderLayout.add(mainGridLayout);
        add(mainBorderLayout);
        refreshGUILists();
        mainTable.addTab(WhatToCook.selectedLanguagePack.get(8), mainBorderLayout);
        mainTable.add(WhatToCook.selectedLanguagePack.get(9), manageReceipesMainPanel);
        mainTable.add(WhatToCook.selectedLanguagePack.get(27), ingredientsMainGridLayout);
        add(mainTable);
        repaint();
    }

    //FUNCKJA OTWIERA NOWĄ KARTĘ Z KONKRETNYM PRZEPISEM
    private void showRecipe(Recipe recipeToShow) {
        recipesBorderLayout = new JPanel(new BorderLayout());
        recipeTextArea = new JTextArea();
        recipeTextArea.setEditable(false);
        recipeTextArea.setLineWrap(true);
        String toShow = "";
        toShow += "Przepis: " + recipeToShow.getName() + "\n\n\n";
        toShow += "Potrzebne składniki: " + "\n\n";
        for (int i = 0; i < recipeToShow.getSize(); i++) {
            toShow += recipeToShow.getIngredient(i).toString() + "\n";
        }
        toShow += "\n\n";
        toShow += "Wykonanie:" + "\n\n" + recipeToShow.getRecipe();
        recipeTextArea.setText(toShow);
        recipeTextAreaScrollPane = new JScrollPane(recipeTextArea);
        recipesBorderLayout.add(recipeTextAreaScrollPane, BorderLayout.CENTER);
        closeTab = new JButton(WhatToCook.selectedLanguagePack.get(23));
        closeTab.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mainTable.removeTabAt(mainTable.getSelectedIndex());
            }
        });
        recipesBorderLayout.add(closeTab, BorderLayout.SOUTH);
        mainTable.addTab(recipeToShow.getName(), recipesBorderLayout);
        // if (settingsDialog.getToNewCardCheckbox()) {
        //     mainTable.setSelectedIndex(mainTable.getTabCount() - 1);
        // }
        if (MainWindow.getToNewCard) {
            mainTable.setSelectedIndex(mainTable.getTabCount() - 1);
        }

    }

    private void refreshGUILists() {
        receipesListModel.clear();
        for (int i = 0; i < RecipesList.recipesList.size(); i++) {
            receipesListModel.addElement(RecipesList.recipesList.get(i).getName());
        }
    }

    //FUNKCJA TWORZY KARTĘ DO EDYCJI PRZEPISU
    private void showNewEditMenu(int index) {
        newEditMainBorderLayout = new JPanel(new BorderLayout());
        newEditMainGridLayout = new JPanel(new GridLayout(2, 1));
        newEditUpGridLayout = new JPanel((new GridLayout(1, 2)));
        newEditDownGridLayout = new JPanel(new GridLayout(1, 2));
        newEditUpRightGridLayout = new JPanel(new GridLayout(4, 1));
        newEditMainDownBorderLayout = new JPanel(new BorderLayout());
        newEditMainUpBorderLayout = new JPanel(new BorderLayout());
        newEditTopGridLayout = new JPanel(new GridLayout(1, 2));
        editNewExitWithoutSaving = new JButton(WhatToCook.selectedLanguagePack.get(22));
        editNewExitWithoutSaving.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isEditionTurnOn = false;
                mainTable.removeTabAt(mainTable.getSelectedIndex());
            }
        });
        editNewExitWithSaving = new JButton(WhatToCook.selectedLanguagePack.get(21));
        editNewExitWithSaving.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = recipeNameTextField.getText();
                String instructions = instructionsInsertTextArea.getText();
                String[] oneIngredientFromList;
                ArrayList<Ingredient> ingredients = new ArrayList<>();
                for (int i = 0; i < ingredientsInputInRecipeListModel.getSize(); i++) {
                    oneIngredientFromList = ingredientsInputInRecipeListModel.getElementAt(i).split(" ");
                    Ingredient ingredient;
                    if (oneIngredientFromList.length == 2) {
                        ingredient = new Ingredient(oneIngredientFromList[1]);
                    } else {
                        ingredient = new Ingredient(oneIngredientFromList[1]);
                    }

                    ingredients.add(ingredient);
                }
                Recipe newRecipe = new Recipe(name, ingredients, instructions);
                if (index < 0) {
                    if ((!name.equals("")) && (!instructions.equals("")) && (!ingredients.isEmpty()) && (!RecipesList.isRecipe(newRecipe))) {
                        RecipesList.add(newRecipe);
                        refreshGUILists();
                        isEditionTurnOn = false;
                        mainTable.removeTabAt(mainTable.getSelectedIndex());
                    } else {
                        JOptionPane.showConfirmDialog(null, WhatToCook.selectedLanguagePack.get(32), WhatToCook.selectedLanguagePack.get(33), JOptionPane.PLAIN_MESSAGE, JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (index >= 0) {
                    if ((!name.equals("")) && (!instructions.equals("")) && (!ingredients.isEmpty())) {
                        if (RecipesList.recipesList.get(index).getName().equals(name) || (!RecipesList.isRecipe(newRecipe))) {
                            RecipesList.remove(RecipesList.recipesList.get(index).getName());
                            RecipesList.add(newRecipe);
                            refreshGUILists();
                            isEditionTurnOn = false;
                            mainTable.removeTabAt(mainTable.getSelectedIndex());
                        }
                    } else {
                        JOptionPane.showConfirmDialog(null, WhatToCook.selectedLanguagePack.get(32), WhatToCook.selectedLanguagePack.get(33), JOptionPane.PLAIN_MESSAGE, JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        newEditAddIngredientButton = new JButton(WhatToCook.selectedLanguagePack.get(12));
        newEditAddIngredientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (!ingredientInCreatingRecipeComboBox.getSelectedItem().equals("")) {
                    String newForm = "● " + ingredientInCreatingRecipeComboBox.getSelectedItem();
                    boolean exist = false;
                    for (int i = 0; i < ingredientsInputInRecipeListModel.getSize(); i++) {
                        if ((newForm.equals(ingredientsInputInRecipeListModel.get(i)))) {
                            exist = true;
                            break;
                        }
                    }
                    if (!exist) {
                        ingredientsInputInRecipeListModel.addElement(newForm);
                    }
                }
            }
        });
        newEditRemoveIngredientButton = new JButton(WhatToCook.selectedLanguagePack.get(13));
        newEditRemoveIngredientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int index = ingredientsInputinRecipeList.getSelectedIndex();
                if (index >= 0) {
                    ingredientsInputInRecipeListModel.removeElementAt(index);
                }
            }
        });

        ingredientsInputInRecipeListModel = new DefaultListModel<>();
        ingredientsInputinRecipeList = new JList<>();
        ingredientsInputinRecipeList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        ingredientsInputinRecipeList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        ingredientsInputinRecipeList.setVisibleRowCount(-1);
        ingredientsInputinRecipeList = new JList<>(ingredientsInputInRecipeListModel);
        ingredientsInputinRecipeListScrollPane = new JScrollPane(ingredientsInputinRecipeList);

        ingredientInCreatingRecipeComboBox = new JComboBox<>();
        IngredientsList.reloadComboBox(ingredientInCreatingRecipeComboBox);
        instructionsInsertTextArea = new JTextArea();
        instructionsInsertTextArea.setLineWrap(true);
        instructionAreaJScrollPane = new JScrollPane(instructionsInsertTextArea);
        recipeNameTextField = new JTextField();

        newEditTopGridLayout.add(new JLabel(WhatToCook.selectedLanguagePack.get(34), SwingConstants.CENTER));
        newEditTopGridLayout.add(recipeNameTextField);
        newEditMainUpBorderLayout.add(newEditTopGridLayout, BorderLayout.NORTH);

        if (index >= 0) {
            recipeNameTextField.setText(RecipesList.recipesList.get(index).getName());
            instructionsInsertTextArea.setText(RecipesList.recipesList.get(index).getRecipe());
            for (int i = 0; i < RecipesList.recipesList.get(index).getSize(); i++) {
                String toAdd;
                toAdd = "● " + RecipesList.recipesList.get(index).getIngredient(i).getName();

                ingredientsInputInRecipeListModel.addElement(toAdd);
            }
            repaint();
        }

        newEditUpRightGridLayout.add(ingredientsInputinRecipeListScrollPane);
        newEditUpRightGridLayout.add(new JLabel(WhatToCook.selectedLanguagePack.get(11), SwingConstants.CENTER));
        newEditUpRightGridLayout.add(ingredientInCreatingRecipeComboBox);
        newEditUpRightGridLayout.add(newEditAddIngredientButton);
        newEditUpRightGridLayout.add(newEditRemoveIngredientButton);


        newEditDownGridLayout.add(editNewExitWithSaving);
        newEditDownGridLayout.add(editNewExitWithoutSaving);
        newEditMainDownBorderLayout.add(new JLabel(WhatToCook.selectedLanguagePack.get(20), SwingConstants.CENTER), BorderLayout.NORTH);
        newEditMainDownBorderLayout.add(instructionAreaJScrollPane, BorderLayout.CENTER);
        newEditUpGridLayout.add(ingredientsInputinRecipeListScrollPane);
        newEditUpGridLayout.add(newEditUpRightGridLayout);
        newEditMainUpBorderLayout.add(newEditUpGridLayout, BorderLayout.CENTER);

        newEditMainDownBorderLayout.add(newEditDownGridLayout, BorderLayout.SOUTH);


        newEditMainGridLayout.add(newEditMainUpBorderLayout);
        newEditMainGridLayout.add(newEditMainDownBorderLayout);

        newEditMainBorderLayout.add(newEditMainGridLayout, BorderLayout.CENTER);
        if (index == -1) {
            mainTable.addTab(WhatToCook.selectedLanguagePack.get(17), newEditMainBorderLayout);
        } else {
            mainTable.addTab(RecipesList.recipesList.get(index).getName(), newEditMainBorderLayout);
        }
        if (MainWindow.getToNewCard) {
            mainTable.setSelectedIndex(mainTable.getTabCount() - 1);
        }

    }

    private void showNewEditMenu() {
        showNewEditMenu(-1);
    }

    //ELEMENTY GUI BAZY SKŁADNIKÓW
    private JPanel ingredientsMainGridLayout;
    private JPanel ingredientsRightBorderLayout;
    private JPanel ingredientsRightGridLayout;

    private JList<String> manageIngredientsInputList;
    private final DefaultListModel<String> manageIngredientsInputListModel;

    private JTextField newIngredientTextField;
    private JButton newIngredientButton;
    private JButton removeIngredientsButton;

    private JScrollPane recipeTextAreaScrollPane;
    private JPanel recipesBorderLayout;
    private JTextArea recipeTextArea;
    private JButton closeTab;

    private JPanel newEditMainBorderLayout;
    private JPanel newEditMainGridLayout;
    private JPanel newEditUpGridLayout;
    private JPanel newEditDownGridLayout;
    private JPanel newEditUpRightGridLayout;
    private JPanel newEditMainDownBorderLayout;
    private JPanel newEditMainUpBorderLayout;
    private JPanel newEditTopGridLayout;

    private JButton editNewExitWithoutSaving;
    private JButton editNewExitWithSaving;

    private JButton newEditAddIngredientButton;
    private JButton newEditRemoveIngredientButton;

    private JTextArea instructionsInsertTextArea;
    private JTextField recipeNameTextField;
    // private JTextField newEditInsertIngredientTextField;

    private JPanel manageReceipesMainPanel;
    private JPanel manageReceipesGridPanel;
    private JPanel manageReceipesLeftBorderLayout;
    private JPanel manageReceipesLeftUpGridPanel;
    private JPanel manageReceipesLeftDownGridPanel;


    private JPanel mainBorderLayout;
    private JPanel downBorderLayout;
    private JPanel upBorderLayout;

    private JPanel mainGridLayout;
    private JPanel upGridLayout;
    private JPanel upRightGridLayout;

    private JTextField searchForReceipesTextArea;
    private JButton execute;
    private JButton addIngredientButton;
    private JButton removeIngredientButton;
    private JButton newRecipe;
    private JButton editRecipe;
    private JButton deleteRecipe;

    private JMenuBar mainMenu;
    public JMenu fileMenu;
    private JMenu editMenu;
    private JMenu helpMenu;

    private JList<String> ingredientsInputList;
    private final DefaultListModel<String> ingredientsInputListModel;

    private JList<String> receipesOutputList;
    private final DefaultListModel<String> receipesOutputListModel;

    private JList<String> receipesList;
    private final DefaultListModel<String> receipesListModel;

    private JList<String> ingredientsInputinRecipeList;
    private DefaultListModel<String> ingredientsInputInRecipeListModel;


    private JScrollPane ingredientsInputListScrollPane;
    private JScrollPane receipesOutputListScrollPane;
    private JScrollPane receipesListScrollPane;
    private JScrollPane ingredientsInputinRecipeListScrollPane;
    private JScrollPane manageIngredientsListScrollPane;

    private JTabbedPane mainTable;

    private boolean isEditionTurnOn;

    private JComboBox<String> ingredientInSearchComboBox;
    private JComboBox<String> ingredientInCreatingRecipeComboBox;

    private JScrollPane instructionAreaJScrollPane;

    private SettingsWindow settingsDialog;
    private AboutWindow aboutDialog;
    private ErrorWindow errorDialog;

    public static boolean getToNewCard;
}
