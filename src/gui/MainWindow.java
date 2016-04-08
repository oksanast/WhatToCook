package gui;

import core.Ingredient;
import core.Recipe;
import core.WhatToCook;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static java.lang.String.valueOf;

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
        //PANELS INITIALIZATION

        mainTable = new JTabbedPane();
        mainBorderLayout = new JPanel(new BorderLayout());
        downBorderLayout = new JPanel(new BorderLayout());
        upBorderLayout = new JPanel(new BorderLayout());

        mainGridLayout = new JPanel(new GridLayout(2, 1));
        upGridLayout = new JPanel(new GridLayout(1, 2));
        upRightGridLayout = new JPanel(new GridLayout(5, 1));
        amountAndUnitGridLayout = new JPanel(new GridLayout(1, 2));

        isEditionTurnOn = false;

        //MENUBAR CREATING
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

            }
        };
        fileMenu.add(exitAction);
        editMenu.add(clearIngredientsAction);
        editMenu.add(clearReceipesAction);
        helpMenu.add(settingsAction);
        helpMenu.addSeparator();
        helpMenu.add(aboutAction);
        //OBJECTS INITIALIZATION
        //TEXT FIELDS
        newIngredientName = new JTextField();
        newIngredientAmmount = new JTextField();
        //BUTTONS
        execute = new JButton(WhatToCook.selectedLanguagePack.get(15));
        execute.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                receipesOutputListModel.addElement("Jajecznica");
            }
        });
        addIngredientButton = new JButton(WhatToCook.selectedLanguagePack.get(12));
        addIngredientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    if (Integer.valueOf(newIngredientAmmount.getText()) > 0) {
                        String newForm = "● " + newIngredientName.getText() + " " + newIngredientAmmount.getText() + " " + unitsComboBox.getSelectedItem();
                        ingredientsInputListModel.addElement(newForm);
                    }
                } catch (NumberFormatException e) {
                    if (!newIngredientName.getText().equals("")) {
                        String newForm = "● " + newIngredientName.getText();
                        ingredientsInputListModel.addElement(newForm);
                    }
                }
                newIngredientName.setText("");
                newIngredientAmmount.setText("");
            }
        });
        removeIngredientButton = new JButton(WhatToCook.selectedLanguagePack.get(13));
        removeIngredientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int index = ingredientsInputList.getSelectedIndex();
                if (index >= 0) {
                    ingredientsInputListModel.removeElementAt(index);
                    newIngredientName.setText("");
                    newIngredientAmmount.setText("");
                }
            }
        });
        //JLISTS
        receipesOutputListModel = new DefaultListModel<String>();
        receipesOutputList = new JList<String>();
        receipesOutputList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        receipesOutputList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        receipesOutputList.setVisibleRowCount(-1);
        receipesOutputList = new JList<String>(receipesOutputListModel);
        receipesOutputListScrollPane = new JScrollPane(receipesOutputList);
        receipesOutputList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showRecipe("Jajecznica", "Rozbij jajka na patelni i smaż");
                }
            }
        });
        ingredientsInputListModel = new DefaultListModel<String>();
        ingredientsInputList = new JList<String>();
        ingredientsInputList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        ingredientsInputList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        ingredientsInputList.setVisibleRowCount(-1);
        ingredientsInputList = new JList<String>(ingredientsInputListModel);
        ingredientsInputListScrollPane = new JScrollPane(ingredientsInputList);
        //MANAGE RECEIPES CREATING SECTION

        searchForReceipesTextArea = new JTextField();

        newRecipe = new JButton(WhatToCook.selectedLanguagePack.get(17));
        newRecipe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isEditionTurnOn == false) {

                    isEditionTurnOn = true;
                    showNewEditMenu();
                }
            }
        });
        editRecipe = new JButton(WhatToCook.selectedLanguagePack.get(18));
        editRecipe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String recipeName = receipesListModel.getElementAt(receipesList.getSelectedIndex());
                int index = WhatToCook.recipesDatabase.getIndex(recipeName);
                if (isEditionTurnOn == false) {

                    isEditionTurnOn = true;
                    showNewEditMenu(index);
                }
            }
        });

        manageReceipesMainPanel = new JPanel(new BorderLayout());
        manageReceipesGridPanel = new JPanel(new GridLayout(1, 2));
        manageReceipesLeftBorderLayout = new JPanel(new BorderLayout());
        manageReceipesLeftUpGridPanel = new JPanel(new GridLayout(2, 1));
        manageReceipesLeftDownGridPanel = new JPanel(new GridLayout(1, 2));


        receipesListModel = new DefaultListModel<String>();
        receipesList = new JList<String>();
        receipesList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        receipesList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        receipesList.setVisibleRowCount(-1);
        receipesList = new JList<String>(receipesListModel);
        receipesListScrollPane = new JScrollPane(receipesList);
        receipesList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = receipesList.getSelectedIndex();
                    int i;
                    for(i = 0; i < WhatToCook.recipesDatabase.recipesList.size();i++)
                    {
                        if(receipesListModel.get(index).equals(WhatToCook.recipesDatabase.recipesList.get(i).getName()))
                            break;
                    }
                    showRecipe(WhatToCook.recipesDatabase.recipesList.get(i));
                }
            }
        });

        manageReceipesLeftUpGridPanel.add(new JLabel(WhatToCook.selectedLanguagePack.get(16), SwingConstants.CENTER));
        manageReceipesLeftUpGridPanel.add(searchForReceipesTextArea);
        manageReceipesLeftBorderLayout.add(manageReceipesLeftUpGridPanel, BorderLayout.NORTH);
        manageReceipesLeftDownGridPanel.add(newRecipe);
        manageReceipesLeftDownGridPanel.add(editRecipe);
        manageReceipesLeftBorderLayout.add(manageReceipesLeftDownGridPanel, BorderLayout.SOUTH);

        manageReceipesLeftBorderLayout.add(receipesListScrollPane, BorderLayout.CENTER);

        manageReceipesGridPanel.add(manageReceipesLeftBorderLayout);

        manageReceipesMainPanel.add(manageReceipesGridPanel, BorderLayout.CENTER);

        //OTHERS
        unitsComboBox = new JComboBox<String>();
        unitsComboBox.addItem("sztuki");
        unitsComboBox.addItem("litry");
        unitsComboBox.addItem("łyżeczki");
        unitsComboBox.addItem("ml");
        unitsComboBox.addItem("gramy");
        unitsComboBox.addItem("inne");

        //PANELS AND OBJECTS LOCATION
        upRightGridLayout.add(new JLabel(WhatToCook.selectedLanguagePack.get(11), SwingConstants.CENTER));
        upRightGridLayout.add(newIngredientName);
        upRightGridLayout.add(amountAndUnitGridLayout);
        amountAndUnitGridLayout.add(newIngredientAmmount);
        amountAndUnitGridLayout.add(unitsComboBox);
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
        mainTable.addTab(WhatToCook.selectedLanguagePack.get(8), mainBorderLayout);
        mainTable.add(WhatToCook.selectedLanguagePack.get(9), manageReceipesMainPanel);
        add(mainTable);
        repaint();
    }

    //Tymczasowa metoda, jedynie do wyświetlenia czegokolwiek, prawdziwa metoda "showRecipe" (poniżej)
    //przyjmuje jako argument przepis do wyświetlenia (przepis - obiekt takiej klasy)
    private void showRecipe(String name, String instructions)
    {
        recipesBorderLayout = new JPanel(new BorderLayout());
        recipeTextArea = new JTextArea();
        recipeTextArea.setEditable(false);
        String toShow = new String();

        toShow+=name+="\n\n\n";
        toShow+=instructions;
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
        mainTable.addTab(name, recipesBorderLayout);
        if (settingsDialog.getToNewCardCheckbox() == true) {
            mainTable.setSelectedIndex(mainTable.getTabCount() - 1);
        }
    }

    private void showRecipe(Recipe recipeToShow) {
        recipesBorderLayout = new JPanel(new BorderLayout());
        recipeTextArea = new JTextArea();
        recipeTextArea.setEditable(false);
        String toShow = new String();
        toShow += "Przepis: " + recipeToShow.getName() + "\n\n\n";
        toShow += "Potrzebne składniki: "+"\n\n";
        for(int i = 0; i < recipeToShow.getSize();i++)
        {
            toShow+=recipeToShow.getIngredient(i).toString()+"\n";
        }
        toShow+="\n\n";
        toShow += "Wykonanie:" + "\n\n" +recipeToShow.getRecipe();
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
        if (settingsDialog.getToNewCardCheckbox() == true) {
            mainTable.setSelectedIndex(mainTable.getTabCount() - 1);
        }

    }
    private void refreshGUILists() {
        receipesListModel.clear();
        for(int i = 0; i < WhatToCook.recipesDatabase.recipesList.size();i++)
        {
            receipesListModel.addElement(WhatToCook.recipesDatabase.recipesList.get(i).getName());
        }
    }
    private void showNewEditMenu(int index)
    {
        newEditMainBorderLayout = new JPanel(new BorderLayout());
        newEditMainGridLayout = new JPanel(new GridLayout(2, 1));
        newEditUpGridLayout = new JPanel((new GridLayout(1, 2)));
        newEditDownGridLayout = new JPanel(new GridLayout(1, 2));
        newEditUpRightGridLayout = new JPanel(new GridLayout(5, 1));
        newEditAmmountUnitGridLayout = new JPanel(new GridLayout(1, 2));
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
                ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
                for(int i = 0; i < ingredientsInputInRecipeListModel.getSize();i++)
                {
                    oneIngredientFromList = ingredientsInputInRecipeListModel.getElementAt(i).split(" ");
                    Ingredient ingredient;
                    if (oneIngredientFromList.length==2)
                    {
                        ingredient = new Ingredient(oneIngredientFromList[1],0,"brak");
                    }
                    else
                    {
                        ingredient = new Ingredient(oneIngredientFromList[1],Integer.valueOf(oneIngredientFromList[2]),oneIngredientFromList[3]);
                    }

                    ingredients.add(ingredient);
                }
                Recipe newRecipe = new Recipe(name, ingredients, instructions);
                if(index<0)
                {
                    if ((!name.equals("")) && (!instructions.equals("")) && (ingredients.isEmpty() == false) && (WhatToCook.recipesDatabase.isRecipe(newRecipe) == false)) {
                        WhatToCook.recipesDatabase.add(newRecipe);
                        refreshGUILists();
                        isEditionTurnOn = false;
                        mainTable.removeTabAt(mainTable.getSelectedIndex());
                    } else {
                        JOptionPane.showConfirmDialog(null, "Sprawdź czy podałeś składniki, nazwę i instrukcję przygotowania. Być może taki przepis już jest w bazie", "Błąd Przepisu", JOptionPane.PLAIN_MESSAGE, JOptionPane.ERROR_MESSAGE);
                    }
                }
                if(index>=0)
                {
                    if ((!name.equals("")) && (!instructions.equals("")) && (ingredients.isEmpty() == false)) {
                        if(WhatToCook.recipesDatabase.recipesList.get(index).getName().equals(name) || (WhatToCook.recipesDatabase.isRecipe(newRecipe) == false))
                        {
                            WhatToCook.recipesDatabase.remove(WhatToCook.recipesDatabase.recipesList.get(index).getName());
                            WhatToCook.recipesDatabase.add(newRecipe);
                            refreshGUILists();
                            isEditionTurnOn = false;
                            mainTable.removeTabAt(mainTable.getSelectedIndex());
                        }
                    } else {
                        JOptionPane.showConfirmDialog(null, "Sprawdź czy podałeś składniki, nazwę i instrukcję przygotowania. Być może taki przepis już jest w bazie", "Błąd Przepisu", JOptionPane.PLAIN_MESSAGE, JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        newEditAddIngredientButton = new JButton(WhatToCook.selectedLanguagePack.get(12));
        newEditAddIngredientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    if (Integer.valueOf(newEditInsertAmmountTextField.getText()) > 0) {
                        String newForm = "● " + newEditInsertIngredientTextField.getText() + " " + newEditInsertAmmountTextField.getText() + " " + newEditUnitsComboBox.getSelectedItem();
                        ingredientsInputInRecipeListModel.addElement(newForm);
                    }
                } catch (NumberFormatException e) {
                    if (!newEditInsertIngredientTextField.getText().equals("")) {
                        String newForm = "● " + newEditInsertIngredientTextField.getText();
                        ingredientsInputInRecipeListModel.addElement(newForm);
                    }
                }
                newEditInsertIngredientTextField.setText("");
                newEditInsertAmmountTextField.setText("");
            }
        });
        newEditRemoveIngredientButton = new JButton(WhatToCook.selectedLanguagePack.get(13));
        newEditRemoveIngredientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int index = ingredientsInputinRecipeList.getSelectedIndex();
                if (index >= 0) {
                    ingredientsInputInRecipeListModel.removeElementAt(index);
                    newEditInsertIngredientTextField.setText("");
                    newEditInsertAmmountTextField.setText("");
                }
            }
        });

        ingredientsInputInRecipeListModel = new DefaultListModel<String>();
        ingredientsInputinRecipeList = new JList<String>();
        ingredientsInputinRecipeList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        ingredientsInputinRecipeList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        ingredientsInputinRecipeList.setVisibleRowCount(-1);
        ingredientsInputinRecipeList = new JList<String>(ingredientsInputInRecipeListModel);
        ingredientsInputinRecipeListScrollPane = new JScrollPane(ingredientsInputinRecipeList);

        newEditInsertIngredientTextField = new JTextField();
        newEditInsertAmmountTextField = new JTextField();

        instructionsInsertTextArea = new JTextArea();
        recipeNameTextField = new JTextField();

        newEditUnitsComboBox = new JComboBox<String>();
        newEditUnitsComboBox.addItem("sztuki");
        newEditUnitsComboBox.addItem("litry");
        newEditUnitsComboBox.addItem("łyżeczki");
        newEditUnitsComboBox.addItem("ml");
        newEditUnitsComboBox.addItem("gramy");
        newEditUnitsComboBox.addItem("inne");
        newEditTopGridLayout.add(new JLabel("Podaj nazwę przepisu:", SwingConstants.CENTER));
        newEditTopGridLayout.add(recipeNameTextField);
        newEditMainUpBorderLayout.add(newEditTopGridLayout, BorderLayout.NORTH);

        if(index>=0)
        {
            recipeNameTextField.setText(WhatToCook.recipesDatabase.recipesList.get(index).getName());
            instructionsInsertTextArea.setText(WhatToCook.recipesDatabase.recipesList.get(index).getRecipe());
            for(int i =0; i < WhatToCook.recipesDatabase.recipesList.get(index).getSize();i++)
            {
                String toAdd = new String();
                if(WhatToCook.recipesDatabase.recipesList.get(index).getIngredient(i).getAmmount()==0)
                {
                    toAdd = "● " + WhatToCook.recipesDatabase.recipesList.get(index).getIngredient(i).getName();
                }
                else
                {
                    toAdd = "● " + WhatToCook.recipesDatabase.recipesList.get(index).getIngredient(i).getName() + " ";
                    toAdd += WhatToCook.recipesDatabase.recipesList.get(index).getIngredient(i).getAmmount() + " ";
                    toAdd += WhatToCook.recipesDatabase.recipesList.get(index).getIngredient(i).getUnit();
                }

                ingredientsInputInRecipeListModel.addElement(toAdd);
            }
            repaint();
        }

        newEditUpRightGridLayout.add(ingredientsInputinRecipeListScrollPane);
        newEditUpRightGridLayout.add(new JLabel(WhatToCook.selectedLanguagePack.get(11), SwingConstants.CENTER));
        newEditUpRightGridLayout.add(newEditInsertIngredientTextField);
        newEditAmmountUnitGridLayout.add(newEditInsertAmmountTextField);
        newEditAmmountUnitGridLayout.add(newEditUnitsComboBox);
        newEditUpRightGridLayout.add(newEditAmmountUnitGridLayout);
        newEditUpRightGridLayout.add(newEditAddIngredientButton);
        newEditUpRightGridLayout.add(newEditRemoveIngredientButton);


        newEditDownGridLayout.add(editNewExitWithSaving);
        newEditDownGridLayout.add(editNewExitWithoutSaving);
        newEditMainDownBorderLayout.add(new JLabel(WhatToCook.selectedLanguagePack.get(20), SwingConstants.CENTER), BorderLayout.NORTH);
        newEditMainDownBorderLayout.add(instructionsInsertTextArea, BorderLayout.CENTER);
        newEditUpGridLayout.add(ingredientsInputinRecipeListScrollPane);
        newEditUpGridLayout.add(newEditUpRightGridLayout);
        newEditMainUpBorderLayout.add(newEditUpGridLayout, BorderLayout.CENTER);

        newEditMainDownBorderLayout.add(newEditDownGridLayout, BorderLayout.SOUTH);


        newEditMainGridLayout.add(newEditMainUpBorderLayout);
        newEditMainGridLayout.add(newEditMainDownBorderLayout);

        newEditMainBorderLayout.add(newEditMainGridLayout, BorderLayout.CENTER);

        mainTable.addTab(WhatToCook.selectedLanguagePack.get(17), newEditMainBorderLayout);
        if (settingsDialog.getToNewCardCheckbox() == true) {
            mainTable.setSelectedIndex(mainTable.getTabCount() - 1);
        }

    }
    private void showNewEditMenu()
    {
        showNewEditMenu(-1);
    }


    private JScrollPane recipeTextAreaScrollPane;
    private JPanel recipesBorderLayout;
    private JTextArea recipeTextArea;
    private JButton closeTab;

    private JPanel newEditMainBorderLayout;
    private JPanel newEditMainGridLayout;
    private JPanel newEditUpGridLayout;
    private JPanel newEditDownGridLayout;
    private JPanel newEditUpRightGridLayout;
    private JPanel newEditAmmountUnitGridLayout;
    private JPanel newEditMainDownBorderLayout;
    private JPanel newEditMainUpBorderLayout;
    private JPanel newEditTopGridLayout;

    private JButton editNewExitWithoutSaving;
    private JButton editNewExitWithSaving;

    private JButton newEditAddIngredientButton;
    private JButton newEditRemoveIngredientButton;

    private JComboBox<String> newEditUnitsComboBox;


    private JTextArea instructionsInsertTextArea;
    private JTextField recipeNameTextField;
    private JTextField newEditInsertIngredientTextField;
    private JTextField newEditInsertAmmountTextField;

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
    private JPanel amountAndUnitGridLayout;

    private JTextField searchForReceipesTextArea;
    private JButton execute;
    private JButton addIngredientButton;
    private JButton removeIngredientButton;
    private JButton newRecipe;
    private JButton editRecipe;


    private JTextField newIngredientName;
    private JTextField newIngredientAmmount;

    private JMenuBar mainMenu;
    public JMenu fileMenu;
    private JMenu editMenu;
    private JMenu helpMenu;

    private JComboBox<String> unitsComboBox;

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

    private JTabbedPane mainTable;

    private boolean isEditionTurnOn;

    private SettingsWindow settingsDialog;

}
