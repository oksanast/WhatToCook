package gui;

import auxiliary.ListHandler;
import auxiliary.PairAmountUnit;
import auxiliary.RecipeParameters;
import core.*;
import auxiliary.PairRecipeIndex;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import static gui.LinkedRecipesUI.showLinkedRecipes;
import static java.awt.event.ActionEvent.*;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by WTC-Team on 20.03.2016.
 * Project WhatToCook
 */
/*
    GŁÓWNA KLASA PROGRAMU, JEST BEZPOŚRENDIO ODPOWIEDZIALNA ZA TWORZENIE GŁÓWNEGO OKNA INTERFEJSU GRAFICZNEGO,
    ALE POŚREDNIO JEST RÓWNIEŻ "FASADĄ" DO KOMUNIKACJI MIĘDZY POZOSTAŁYMI KLASAMI W PROGRAMIE
 */
public class MainWindow extends JFrame {

    public MainWindow(boolean[] cards) {

        try {
            if(theme.equals("Platform")) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            if(theme.equals("Metal")) {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }
            if(theme.equals("Nimbus")) {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            }
            SwingUtilities.updateComponentTreeUI(MainWindow.this);
        } catch (Exception e) {}
        SpareIngredientsList.initialize();
        RecipesList.initialize();
        IngredientsList.initialize();
        ToBuyIngredientsList.initialize();
        getContentPane().setBackground(backgroundColor);
        try {
            getRootPane().putClientProperty("apple.awt.fullscreenable", true);
        }
        catch (Exception e) {}
        UIManager.put("Button.font",new Font(font,Font.PLAIN,size));
        UIManager.put("ToggleButton.font",new Font(font,Font.PLAIN,size));
        UIManager.put("RadioButton.font",new Font(font,Font.PLAIN,size));
        UIManager.put("CheckBox.font",new Font(font,Font.PLAIN,size));
        UIManager.put("ColorChooser.font",new Font(font,Font.PLAIN,size));
        UIManager.put("ComboBox.font",new Font(font,Font.PLAIN,size));
        if(biggerLabels)
        UIManager.put("Label.font",new Font(font,Font.PLAIN,(int) (size + size*0.5)));
        else
            UIManager.put("Label.font",new Font(font,Font.PLAIN,(int) (size)));
        UIManager.put("List.font",new Font(font,Font.PLAIN,size));
        UIManager.put("MenuBar.font",new Font(font,Font.PLAIN,size));
        UIManager.put("MenuItem.font",new Font(font,Font.PLAIN,size));
        UIManager.put("RadioButtonMenuItem.font",new Font(font,Font.PLAIN,size));
        UIManager.put("CheckBoxMenuItem.font",new Font(font,Font.PLAIN,size));
        UIManager.put("Menu.font",new Font(font,Font.PLAIN,size));
        UIManager.put("PopupMenu.font",new Font(font,Font.PLAIN,size));
        UIManager.put("OptionPane.font",new Font(font,Font.PLAIN,size));
        UIManager.put("Panel.font",new Font(font,Font.PLAIN,size));
        UIManager.put("ProgressBar.font",new Font(font,Font.PLAIN,size));
        UIManager.put("ScrollPane.font",new Font(font,Font.PLAIN,size));
        UIManager.put("Viewport.font",new Font(font,Font.PLAIN,size));
        UIManager.put("TabbedPane.font",new Font(font,Font.PLAIN,size));
        UIManager.put("Table.font",new Font(font,Font.PLAIN,size));
        UIManager.put("TableHeader.font",new Font(font,Font.PLAIN,size));
        UIManager.put("TextField.font",new Font(font,Font.PLAIN,size));
        UIManager.put("PasswordField.font",new Font(font,Font.PLAIN,size));
        UIManager.put("TextArea.font",new Font(font,Font.PLAIN,size));
        UIManager.put("TextPane.font",new Font(font,Font.PLAIN,size));
        UIManager.put("EditorPane.font",new Font(font,Font.PLAIN,size));
        UIManager.put("TitledBorder.font",new Font(font,Font.PLAIN,size));
        UIManager.put("ToolBar.font",new Font(font,Font.PLAIN,size));
        UIManager.put("ToolTip.font",new Font(font,Font.PLAIN,size));
        UIManager.put("Tree.font",new Font(font,Font.PLAIN,size));



        setSize(500, 600);
        setTitle("WhatToCook");
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(340, 400));

        mainCardsCount = getTruthsAmmount(cards);
        inEdit = null;
        //MENUBAR CREATING/////////////////////////////////////////////////////////////////////////////////////////////
        mainMenu = new JMenuBar();
        setJMenuBar(mainMenu);
        fileMenu = new JMenu(WhatToCook.SelectedPackage.get(0));
        editMenu = new JMenu(WhatToCook.SelectedPackage.get(2));
        helpMenu = new JMenu(WhatToCook.SelectedPackage.get(5));
        viewMenu = new JMenu(WhatToCook.SelectedPackage.get(83));
        newSubmenu = new JMenu(WhatToCook.SelectedPackage.get(45));
        cardsSubmenu = new JMenu(WhatToCook.SelectedPackage.get(84));
        toolsMenu = new JMenu(WhatToCook.SelectedPackage.get(106));

        mainMenu.add(fileMenu);
        mainMenu.add(editMenu);
        mainMenu.add(viewMenu);
        mainMenu.add(toolsMenu);
        mainMenu.add(helpMenu);
        fileMenu.add(newSubmenu);
        viewMenu.add(cardsSubmenu);
        showSearchMenu = new JCheckBoxMenuItem(WhatToCook.SelectedPackage.get(8));
        showRecipesMenu = new JCheckBoxMenuItem(WhatToCook.SelectedPackage.get(9));
        showIngredientsMenu = new JCheckBoxMenuItem(WhatToCook.SelectedPackage.get(27));

        showSearchMenu.setSelected(cards[0]);
        showRecipesMenu.setSelected(cards[1]);
        showIngredientsMenu.setSelected(cards[2]);
        showSearchMenu.addActionListener(e -> {
            if (!showSearchMenu.isSelected()) {
                mainTable.CloseTabByComponent(mainBorderLayout);
                mainCardsCount--;
            } else {
                mainTable.insertTabNoExit(WhatToCook.SelectedPackage.get(8), mainBorderLayout, 0);
                mainCardsCount++;
            }
            settingsDialog.exportSettings();
        });
        showRecipesMenu.addActionListener(e -> {
            System.out.println(mainCardsCount);
            if (!showRecipesMenu.isSelected()) {
                mainTable.CloseTabByComponent(manageRecipesMainPanel);
                mainCardsCount--;
            } else {
                if (mainCardsCount == 1 && showIngredientsMenu.isSelected())
                    mainTable.insertTabNoExit(WhatToCook.SelectedPackage.get(9), manageRecipesMainPanel, 0);
                else if (mainCardsCount == 1 && !showIngredientsMenu.isSelected())
                    mainTable.insertTabNoExit(WhatToCook.SelectedPackage.get(9), manageRecipesMainPanel, 1);
                else if (mainCardsCount >= 2)
                    mainTable.insertTabNoExit(WhatToCook.SelectedPackage.get(9), manageRecipesMainPanel, 1);
                else
                    mainTable.insertTabNoExit(WhatToCook.SelectedPackage.get(9), manageRecipesMainPanel, 0);
                mainCardsCount++;
            }
            settingsDialog.exportSettings();
        });
        showIngredientsMenu.addActionListener(e -> {
            if (!showIngredientsMenu.isSelected()) {
                mainTable.CloseTabByComponent(ingredientsMainGridLayout);
                mainCardsCount--;
            } else {
                if (mainCardsCount >= 2)
                    mainTable.insertTabNoExit(WhatToCook.SelectedPackage.get(27), ingredientsMainGridLayout, 2);
                else if (mainCardsCount == 1)
                    mainTable.insertTabNoExit(WhatToCook.SelectedPackage.get(27), ingredientsMainGridLayout, 1);
                else
                    mainTable.insertTabNoExit(WhatToCook.SelectedPackage.get(27), ingredientsMainGridLayout, 0);
                mainCardsCount++;
            }
            settingsDialog.exportSettings();
        });
        cardsSubmenu.add(showSearchMenu);
        cardsSubmenu.add(showRecipesMenu);
        cardsSubmenu.add(showIngredientsMenu);
        settingsDialog = new SettingsWindow();
        aboutDialog = new AboutWindow();
        errorDialog = new ErrorWindow();
        searchingDialog = new SearchingWindow();
        shoppingListDialog = new ToBuyListWindow();
        timerDialog = new TimerWindow();

        try {
            Scanner in = new Scanner(new File("src/searchSettingsConfig"));
            String line = in.nextLine();
            String splittedLine[] = line.split("=");
            if (splittedLine[1].equals("true")) {
                searchingDialog.wholeWords.setSelected(true);
            } else
                searchingDialog.wholeWords.setSelected(false);
            line = in.nextLine();
            splittedLine = line.split("=");
            if (splittedLine[1].equals("true")) {
                searchingDialog.caseSensitiveCheckBox.setSelected(true);
            } else
                searchingDialog.caseSensitiveCheckBox.setSelected(false);
        } catch (FileNotFoundException e) {
            System.out.println("Error during loading 'searchSettingsConfig', default settings will be used");
            searchingDialog.caseSensitiveCheckBox.setSelected(false);
            searchingDialog.wholeWords.setSelected(true);
        }


        JMenuItem newIngredientAction = new JMenuItem(WhatToCook.SelectedPackage.get(46));
        newIngredientAction.addActionListener(e -> {
            if (!showIngredientsMenu.isSelected()) {
                if (mainTable.getTabCount() >= 2) {
                    mainTable.insertTabNoExit(WhatToCook.SelectedPackage.get(27), ingredientsMainGridLayout, 2);
                    mainTable.setSelectedIndex(2);
                } else if (mainTable.getTabCount() == 1) {
                    mainTable.insertTabNoExit(WhatToCook.SelectedPackage.get(27), ingredientsMainGridLayout, 1);
                    mainTable.setSelectedIndex(1);
                } else {
                    mainTable.insertTabNoExit(WhatToCook.SelectedPackage.get(27), ingredientsMainGridLayout, 0);
                    mainTable.setSelectedIndex(0);
                }
            } else {
                if (showSearchMenu.isSelected() && showRecipesMenu.isSelected()) {
                    mainTable.setSelectedIndex(2);
                } else if (showSearchMenu.isSelected() || showRecipesMenu.isSelected()) {
                    mainTable.setSelectedIndex(1);
                } else
                    mainTable.setSelectedIndex(0);
            }
            newIngredientTextField.requestFocus();
        });

        JMenuItem timerOpenAction = new JMenuItem(WhatToCook.SelectedPackage.get(113));
        timerOpenAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerDialog.setVisible(true);
            }
        });
        newIngredientAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        JMenuItem newRecipeAction = new JMenuItem(WhatToCook.SelectedPackage.get(47));
        newRecipeAction.addActionListener(e -> {
            if (!isEditionTurnOn) {

                isEditionTurnOn = true;
                showNewEditMenu(null);
            } else {
                showMessageDialog(new JFrame(), WhatToCook.SelectedPackage.get(79), WhatToCook.SelectedPackage.get(78), JOptionPane.ERROR_MESSAGE);
            }
        });
        newRecipeAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        JMenuItem settingsAction = new JMenuItem(WhatToCook.SelectedPackage.get(6));
        settingsAction.addActionListener(event -> settingsDialog.setVisible(true));
        settingsAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        JMenuItem exitAction = new JMenuItem(WhatToCook.SelectedPackage.get(1));
        exitAction.addActionListener(e -> System.exit(0));
        exitAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        Action clearIngredientsAction = new AbstractAction(WhatToCook.SelectedPackage.get(3)) {
            public void actionPerformed(ActionEvent event) {
                int i = ingredientsInputListModel.getSize() - 1;
                for (; i >= 0; i--)
                    ingredientsInputListModel.removeElementAt(i);
                try {
                    PrintWriter writer = new PrintWriter(new File(WhatToCook.SelectedPackage.GetOwnedIngredientsPath()));
                    writer.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        };
        Action clearReceipesAction = new AbstractAction(WhatToCook.SelectedPackage.get(4)) {
            public void actionPerformed(ActionEvent event) {
                int i = recipesOutputListModel.getSize() - 1;
                for (; i >= 0; i--)
                    recipesOutputListModel.removeElementAt(i);


            }

        };
        Action aboutAction = new AbstractAction(WhatToCook.SelectedPackage.get(7)) {
            public void actionPerformed(ActionEvent event) {
                aboutDialog.setVisible(true);
            }
        };

        Action exportIngredientsAction = new AbstractAction(WhatToCook.SelectedPackage.get(40)) {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportIngredientsList();
            }
        };
        Action importIngredientsAction = new AbstractAction(WhatToCook.SelectedPackage.get(41)) {
            @Override
            public void actionPerformed(ActionEvent e) {
                importIngredientsList();
            }
        };
        Action closeAllRecipes = new AbstractAction(WhatToCook.SelectedPackage.get(85)) {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = mainTable.getTabCount() - 1; i >= mainCardsCount; i--)
                    mainTable.removeTabAt(i);
            }
        };
        JMenuItem ToBuyListAction = new JMenuItem(WhatToCook.SelectedPackage.get(107));
        ToBuyListAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
                shoppingListDialog.refresh();
                shoppingListDialog.setVisible(true);
            }
        });
        fileMenu.addSeparator();
        fileMenu.add(settingsAction);
        fileMenu.addSeparator();
        fileMenu.add(exitAction);
        newSubmenu.add(newIngredientAction);
        newSubmenu.add(newRecipeAction);
        viewMenu.addSeparator();
        viewMenu.add(closeAllRecipes);
        toolsMenu.add(ToBuyListAction);
        toolsMenu.add(timerOpenAction);
        editMenu.add(exportIngredientsAction);
        editMenu.add(importIngredientsAction);
        editMenu.addSeparator();
        editMenu.add(clearIngredientsAction);
        editMenu.add(clearReceipesAction);
        helpMenu.add(aboutAction);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //KARTA WYSZUKIWANIA////////////////////////////////////////////////////////////////////////////////////////////
        ingredientsDownGridLayout = new JPanel(new GridLayout(1, 2));
        ingredientsRightDownGridLayout = new JPanel(new GridLayout(11, 1));


        mainBorderLayout = new JPanel(new BorderLayout());
        downBorderLayout = new JPanel(new BorderLayout());
        upBorderLayout = new JPanel(new BorderLayout());
        mainGridLayout = new JPanel(new GridLayout(2, 1));
        upGridLayout = new JPanel(new GridLayout(1, 2));
        upRightGridLayout = new JPanel(new GridLayout(5, 1));

        importExportInSearchGrid = new JPanel(new GridLayout(1, 2));

        isEditionTurnOn = false;

        shownRecipesList = new PairRecipeIndex();

        ingredientInSearchComboBox = new JComboBox<>();
        IngredientsList.reloadComboBox(ingredientInSearchComboBox);
        ingredientInSearchComboBox.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
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
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        ingredientsInputListModel = new DefaultListModel<>();
        ingredientsInputList = new JList<>();
        ingredientsInputList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ingredientsInputList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        ingredientsInputList.setVisibleRowCount(-1);
        ingredientsInputList = new JList<>(ingredientsInputListModel);
        ingredientsInputListScrollPane = new JScrollPane(ingredientsInputList);
        //WCZYTYWANIE SKLADNIKOW POSIADANYCH NA POCZATKU PROGRAMU
        if (MainWindow.autoLoadIngredients) {
            String name;
            try {
                Scanner in = new Scanner(new File(WhatToCook.SelectedPackage.GetOwnedIngredientsPath()));
                while (in.hasNextLine()) {
                    name = in.nextLine();
                    Ingredient toAdd = new Ingredient(name);

                    if (IngredientsList.contain(toAdd))
                        ingredientsInputListModel.addElement("● " + name);
                }
                in.close();

            } catch (FileNotFoundException | NullPointerException exception) {
                showMessageDialog(new JFrame(), WhatToCook.SelectedPackage.get(77), WhatToCook.SelectedPackage.get(76), JOptionPane.ERROR_MESSAGE);
            }
        }


        importIngredientsInSearch = new JButton(WhatToCook.SelectedPackage.get(68));
        importIngredientsInSearch.addActionListener(e ->
        {
            JFileChooser chooseFile = new JFileChooser();
            int save = chooseFile.showOpenDialog(null);
            if (save == JFileChooser.APPROVE_OPTION) {
                String filename = chooseFile.getSelectedFile().getPath();
                String name;
                try {
                    Scanner in = new Scanner(new File(filename));
                    ArrayList<String> notAdded = new ArrayList<>();
                    while (in.hasNextLine()) {
                        name = in.nextLine();
                        Ingredient toAdd = new Ingredient(name);

                        if (IngredientsList.contain(toAdd))
                            ingredientsInputListModel.addElement("● " + name);
                        else
                            notAdded.add(name);
                    }
                    if (notAdded.size() > 0) {
                        errorDialog.refresh(notAdded, WhatToCook.SelectedPackage.get(71), WhatToCook.SelectedPackage.get(70));
                        errorDialog.setVisible(true);
                    }
                    in.close();

                } catch (FileNotFoundException exception) {
                    System.err.println("Internal program error, 'ownedIngredients' not found");
                }

            }
        });
        exportIngredientsInSearch = new JButton(WhatToCook.SelectedPackage.get(69));
        exportIngredientsInSearch.addActionListener(e ->
        {
            JFileChooser chooseFile = new JFileChooser();
            int save = chooseFile.showSaveDialog(null);
            if (save == JFileChooser.APPROVE_OPTION) {
                String filename = chooseFile.getSelectedFile().getPath();
                PrintWriter writer;
                try {
                    writer = new PrintWriter(filename, "UTF-8");
                    for (int i = 0; i < ingredientsInputListModel.size(); i++)
                        writer.println(ingredientsInputListModel.get(i).substring(2));

                    writer.close();
                } catch (FileNotFoundException | UnsupportedEncodingException exception) {
                    System.err.println("Exporting ingredients list error.");
                }
            }
        });

        importExportInSearchGrid.add(importIngredientsInSearch);
        importExportInSearchGrid.add(exportIngredientsInSearch);
        ingredientInCreatingRecipeComboBox = new JComboBox<>();
        IngredientsList.reloadComboBox(ingredientInCreatingRecipeComboBox);
        execute = new JButton(WhatToCook.SelectedPackage.get(15));
        execute.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                recipesOutputListModel.clear();
                ArrayList<Ingredient> ingredients = new ArrayList<>();
                boolean[] parameters = new boolean[5];
                parameters[0] = breakfestCheckBox.isSelected();
                parameters[1] = dessertCheckBox.isSelected();
                parameters[2] = dinerCheckBox.isSelected();
                parameters[3] = supperCheckBox.isSelected();
                parameters[4] = snackCheckBox.isSelected();
                if (isFalse(parameters, 5)) {
                    for (int i = 0; i < 5; i++) {
                        parameters[i] = true;
                    }
                }
                for (int i = 0; i < ingredientsInputListModel.size(); i++) {
                    ingredients.add(new Ingredient(ingredientsInputListModel.getElementAt(i).substring(2)));
                }
                for (int i = 0; i < RecipesList.size(); i++) {
                    if (RecipesList.checkWithIngredientsList(ingredients, i, parameters, EaseToPrepare.getSelectedIndex(), PreparingTimeComboBox.getSelectedIndex()))
                        recipesOutputListModel.addElement(RecipesList.getRecipeNameAtIndex(i));
                }
            }
        });
        addIngredientButton = new JButton(WhatToCook.SelectedPackage.get(12));
        addIngredientButton.addActionListener(e -> {
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
            PrintWriter writer;
            try {
                writer = new PrintWriter(WhatToCook.SelectedPackage.GetOwnedIngredientsPath(), "UTF-8");
                for (int i = 0; i < ingredientsInputListModel.size(); i++)
                    writer.println(ingredientsInputListModel.get(i).substring(2));

                writer.close();
            } catch (FileNotFoundException | UnsupportedEncodingException exception) {
                System.err.println("Exporting ingredients list error.");
            }
        });
        removeIngredientButton = new JButton(WhatToCook.SelectedPackage.get(13));
        removeIngredientButton.addActionListener(e -> {
            for (int i = ingredientsInputList.getSelectedIndices().length - 1; i >= 0; i--) {
                ingredientsInputListModel.removeElementAt(ingredientsInputList.getSelectedIndices()[i]);
            }
            PrintWriter writer;
            try {
                writer = new PrintWriter(WhatToCook.SelectedPackage.GetOwnedIngredientsPath(), "UTF-8");
                for (int i = 0; i < ingredientsInputListModel.size(); i++)
                    writer.println(ingredientsInputListModel.get(i).substring(2));

                writer.close();
            } catch (FileNotFoundException | UnsupportedEncodingException exception) {
                System.err.println("Exporting ingredients list error.");
            }
        });
        //JLISTS
        recipesOutputListModel = new DefaultListModel<>();
        recipesOutputList = new JList<>();
        recipesOutputList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recipesOutputList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        recipesOutputList.setVisibleRowCount(-1);
        recipesOutputList = new JList<>(recipesOutputListModel);
        recipesOutputListScrollPane = new JScrollPane(recipesOutputList);
        recipesOutputList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = recipesOutputList.getSelectedIndex();
                    int i;
                    for (i = 0; i < RecipesList.size(); i++) {
                        if (recipesOutputListModel.get(index).equals(RecipesList.recipesList.get(i).getName()))
                            break;
                    }
                    shownRecipesList.add(RecipesList.recipesList.get(i), mainTable.getTabCount(), mainTable.getSelectedIndex());
                    showRecipe(RecipesList.recipesList.get(i));
                }
            }
        });

        spareIngredientsCheckBox = new JCheckBox(WhatToCook.SelectedPackage.get(91));

        ingredientsRightDownGridLayout.add(new JLabel(WhatToCook.SelectedPackage.get(50), SwingConstants.CENTER));
        breakfestCheckBox = new JCheckBox(WhatToCook.SelectedPackage.get(51));
        dessertCheckBox = new JCheckBox(WhatToCook.SelectedPackage.get(52));
        dinerCheckBox = new JCheckBox(WhatToCook.SelectedPackage.get(53));
        supperCheckBox = new JCheckBox(WhatToCook.SelectedPackage.get(54));
        snackCheckBox = new JCheckBox(WhatToCook.SelectedPackage.get(55));
        ingredientsRightDownGridLayout.add(breakfestCheckBox);
        ingredientsRightDownGridLayout.add(dinerCheckBox);
        ingredientsRightDownGridLayout.add(supperCheckBox);
        ingredientsRightDownGridLayout.add(dessertCheckBox);
        ingredientsRightDownGridLayout.add(snackCheckBox);
        ingredientsRightDownGridLayout.add(new JLabel(WhatToCook.SelectedPackage.get(56), SwingConstants.CENTER));
        PreparingTimeComboBox = new JComboBox<>();
        PreparingTimeComboBox.addItem(WhatToCook.SelectedPackage.get(59));
        PreparingTimeComboBox.addItem(WhatToCook.SelectedPackage.get(60));
        PreparingTimeComboBox.addItem(WhatToCook.SelectedPackage.get(61));
        ingredientsRightDownGridLayout.add(PreparingTimeComboBox);
        ingredientsRightDownGridLayout.add(new JLabel(WhatToCook.SelectedPackage.get(57), SwingConstants.CENTER));
        EaseToPrepare = new JComboBox<>();
        EaseToPrepare.addItem(WhatToCook.SelectedPackage.get(62));
        EaseToPrepare.addItem(WhatToCook.SelectedPackage.get(63));
        EaseToPrepare.addItem(WhatToCook.SelectedPackage.get(64));
        ingredientsRightDownGridLayout.add(EaseToPrepare);
        ingredientsRightDownGridLayout.add(spareIngredientsCheckBox);
        //PANELS AND OBJECTS LOCATION
        upRightGridLayout.add(new JLabel(WhatToCook.SelectedPackage.get(11), SwingConstants.CENTER));
        upRightGridLayout.add(ingredientInSearchComboBox);
        upRightGridLayout.add(addIngredientButton);
        upRightGridLayout.add(removeIngredientButton);
        upRightGridLayout.add(importExportInSearchGrid);
        upGridLayout.add(ingredientsInputListScrollPane);
        upGridLayout.add(upRightGridLayout);
        downBorderLayout.add(new JLabel(WhatToCook.SelectedPackage.get(14), SwingConstants.CENTER), BorderLayout.NORTH);
        downBorderLayout.add(recipesOutputListScrollPane, BorderLayout.CENTER);
        downBorderLayout.add(execute, BorderLayout.SOUTH);
        ingredientsDownGridLayout.add(downBorderLayout);
        ingredientsDownGridLayout.add(ingredientsRightDownGridLayout);
        upBorderLayout.add(new JLabel(WhatToCook.SelectedPackage.get(10), SwingConstants.CENTER), BorderLayout.NORTH);
        upBorderLayout.add(upGridLayout, BorderLayout.CENTER);
        mainGridLayout.add(upBorderLayout);
        mainGridLayout.add(ingredientsDownGridLayout);
        mainBorderLayout.add(mainGridLayout);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //KARTA ZARZĄDZANIA PRZEPISAMI//////////////////////////////////////////////////////////////////////////////////
        manageRecipesMainPanel = new JPanel(new BorderLayout());
        manageRecipesGridPanel = new JPanel(new GridLayout(1, 2));
        manageRecipesLeftBorderLayout = new JPanel(new BorderLayout());
        manageRecipesLeftUpGridPanel = new JPanel(new GridLayout(2, 1));
        manageRecipesLeftDownGridPanel = new JPanel(new GridLayout(1, 3));
        searchingOptionsBorderLayout = new JPanel(new BorderLayout());

        manageRecipesAndLinkedPanel = new JPanel(new GridLayout(1, 2));

        searchForRecipesTextArea = new JTextField();
        searchingOptionsButton = new JButton(WhatToCook.SelectedPackage.get(80));
        searchingOptionsButton.addActionListener(e -> searchingDialog.setVisible(true));
        searchForRecipesTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                refreshGUILists(searchForRecipesTextArea.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                refreshGUILists(searchForRecipesTextArea.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                refreshGUILists(searchForRecipesTextArea.getText());
            }
        });

        newRecipe = new JButton(WhatToCook.SelectedPackage.get(17));
        newRecipe.addActionListener(e -> {
            if (!isEditionTurnOn) {

                isEditionTurnOn = true;
                showNewEditMenu(null);
            } else
                showMessageDialog(new JFrame(), WhatToCook.SelectedPackage.get(79), WhatToCook.SelectedPackage.get(78), JOptionPane.ERROR_MESSAGE);
        });
        editRecipe = new JButton(WhatToCook.SelectedPackage.get(18));
        editRecipe.addActionListener(e -> {
            if (recipesList.getSelectedIndex() >= 0) {
                if (!isEditionTurnOn) {

                    isEditionTurnOn = true;
                    inEdit = RecipesList.getRecipe(recipesList.getSelectedValue());
                    showNewEditMenu(RecipesList.getRecipe(recipesList.getSelectedValue()));
                }
            }
        });
        deleteRecipe = new JButton(WhatToCook.SelectedPackage.get(31));
        deleteRecipe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = recipesList.getSelectedIndices().length - 1; i >= 0; i--) {
                    String recipeName = recipesListModel.getElementAt(recipesList.getSelectedIndices()[i]);
                    if ((inEdit == null) || (inEdit != null && !inEdit.getName().equals(recipeName))) {
                        RecipesList.remove(recipeName);
                    } else
                        JOptionPane.showMessageDialog(null, WhatToCook.SelectedPackage.get(86), WhatToCook.SelectedPackage.get(87), JOptionPane.ERROR_MESSAGE);
                }
                refreshGUILists(searchForRecipesTextArea.getText());
            }
        });

        recipesListModel = new DefaultListModel<>();
        recipesList = new JList<>();
        recipesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recipesList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        recipesList.setVisibleRowCount(-1);
        recipesList = new JList<>(recipesListModel);
        recipesListScrollPane = new JScrollPane(recipesList);
        //recipesListScrollPane.setBorder(BorderFactory.createMatteBorder(5,5,5,5,backgroundColor));
        recipesList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                markedRecipe = recipesList.getSelectedIndex();
                showLinkedRecipes();
                if (e.getClickCount() == 2) {
                    int index = recipesList.getSelectedIndex();
                    int i;
                    for (i = 0; i < RecipesList.recipesList.size(); i++) {
                        if (recipesListModel.get(index).equals(RecipesList.recipesList.get(i).getName()))
                            break;
                    }
                    shownRecipesList.add(RecipesList.recipesList.get(i), mainTable.getTabCount(), mainTable.getSelectedIndex());
                    showRecipe(RecipesList.recipesList.get(i));
                }
            }
        });

        recipesList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                markedRecipe = recipesList.getSelectedIndex();
                showLinkedRecipes();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                markedRecipe = recipesList.getSelectedIndex();
                showLinkedRecipes();
            }
        });

        manageRecipesLeftUpGridPanel.add(new JLabel(WhatToCook.SelectedPackage.get(16), SwingConstants.CENTER));
        searchingOptionsBorderLayout.add(searchForRecipesTextArea, BorderLayout.CENTER);
        searchingOptionsBorderLayout.add(searchingOptionsButton, BorderLayout.EAST);
        manageRecipesLeftUpGridPanel.add(searchingOptionsBorderLayout);
        manageRecipesLeftBorderLayout.add(manageRecipesLeftUpGridPanel, BorderLayout.NORTH);
        manageRecipesLeftDownGridPanel.add(newRecipe);
        manageRecipesLeftDownGridPanel.add(editRecipe);
        manageRecipesLeftDownGridPanel.add(deleteRecipe);
        manageRecipesLeftBorderLayout.add(manageRecipesLeftDownGridPanel, BorderLayout.SOUTH);

        manageRecipesAndLinkedPanel.add(recipesListScrollPane);
        LinkedRecipesUI.manageLinkedRecipes(manageRecipesAndLinkedPanel);

        manageRecipesLeftBorderLayout.add(manageRecipesAndLinkedPanel, BorderLayout.CENTER);
        manageRecipesGridPanel.add(manageRecipesLeftBorderLayout);
        manageRecipesMainPanel.add(manageRecipesGridPanel, BorderLayout.CENTER);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //KARTA DO ZARZĄDZANIA SKŁADNIKAMI//////////////////////////////////////////////////////////////////////////////
        ingredientsMainGridLayout = new JPanel(new GridLayout(1, 2));
        ingredientsRightBorderLayout = new JPanel(new BorderLayout());
        ingredientsRightGridLayout = new JPanel(new GridLayout(5, 1));
        manageIngredientsInputListModel = new DefaultListModel<>();
        manageIngredientsInputList = new JList<>();
        manageIngredientsInputList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        manageIngredientsInputList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        manageIngredientsInputList.setVisibleRowCount(-1);
        manageIngredientsInputList = new JList<>(manageIngredientsInputListModel);
        manageIngredientsListScrollPane = new JScrollPane(manageIngredientsInputList);

        IngredientsList.rebuildModel(manageIngredientsInputListModel);

        newIngredientButton = new JButton(WhatToCook.SelectedPackage.get(29));
        newIngredientButton.addActionListener(e -> {
            if (!newIngredientTextField.getText().equals("")) {
                Ingredient toAdd = new Ingredient(newIngredientTextField.getText());
                if(toAdd.getName().charAt(toAdd.getName().length()-1)!='/') {
                    IngredientsList.addIngredient(toAdd);
                }
                else
                    showMessageDialog(new JFrame(), WhatToCook.SelectedPackage.get(92), WhatToCook.SelectedPackage.get(93), JOptionPane.ERROR_MESSAGE);
            }
            newIngredientTextField.setText("");
            IngredientsList.rebuildModel(manageIngredientsInputListModel);
            IngredientsList.reloadComboBox(ingredientInSearchComboBox);
            IngredientsList.reloadComboBox(ingredientInCreatingRecipeComboBox);
        });
        newIngredientTextField = new JTextField();
        newIngredientTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (!newIngredientTextField.getText().equals("")) {
                        Ingredient toAdd = new Ingredient(newIngredientTextField.getText());
                        if(toAdd.getName().charAt(toAdd.getName().length()-1)!='/') {
                            IngredientsList.addIngredient(toAdd);
                        }
                        else
                            showMessageDialog(new JFrame(), WhatToCook.SelectedPackage.get(92), WhatToCook.SelectedPackage.get(93), JOptionPane.ERROR_MESSAGE);

                    }
                    newIngredientTextField.setText("");
                    IngredientsList.rebuildModel(manageIngredientsInputListModel);
                    IngredientsList.reloadComboBox(ingredientInSearchComboBox);
                    IngredientsList.reloadComboBox(ingredientInCreatingRecipeComboBox);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        spareIngredientsInputListModel = new DefaultListModel<>();
        spareIngredientsInputList = new JList<>();
        spareIngredientsInputList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        spareIngredientsInputList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        spareIngredientsInputList.setVisibleRowCount(-1);
        spareIngredientsInputList = new JList<>(spareIngredientsInputListModel);

        removeIngredientsButton = new JButton(WhatToCook.SelectedPackage.get(30));
        removeIngredientsButton.addActionListener(e -> {
            boolean ifExist = false;
            ArrayList<String> recipesContainIngredient = new ArrayList<>();
            for (int i = 0; i < RecipesList.size(); i++) {
                for (int j = 0; j < RecipesList.recipesList.get(i).getSize(); j++) {
                    if (manageIngredientsInputListModel.get(manageIngredientsInputList.getSelectedIndex()).equals(RecipesList.recipesList.get(i).getIngredient(j).getName())) {
                        ifExist = true;
                        recipesContainIngredient.add(RecipesList.recipesList.get(i).getName());
                    }
                }
            }
            if (!ifExist) {
                if (manageIngredientsInputList.getSelectedIndex() >= 0) {
                    SpareIngredientsList.removeSpareIngredientFromEverywhere(new Ingredient(manageIngredientsInputList.getSelectedValue()));
                    SpareIngredientsList.removeElement(new Ingredient(manageIngredientsInputList.getSelectedValue()));
                    IngredientsList.removeIngredient(manageIngredientsInputList.getSelectedValue());
                }
                IngredientsList.rebuildModel(manageIngredientsInputListModel);
                IngredientsList.reloadComboBox(ingredientInSearchComboBox);
                IngredientsList.reloadComboBox(ingredientInCreatingRecipeComboBox);
                spareIngredientsInputListModel.clear();
                spareIngredientsComboBox.removeAllItems();
            } else {
                errorDialog.refresh(recipesContainIngredient, WhatToCook.SelectedPackage.get(38), WhatToCook.SelectedPackage.get(39));
                errorDialog.setVisible(true);
            }

        });
        spareIngredientsMainBorderLayout = new JPanel(new BorderLayout());
        spareIngredientsMainGridLayout = new JPanel(new GridLayout(1,2));
        spareIngredientsLeftGridLayout = new JPanel(new GridLayout(2,1));
        spareIngredientsUpBorderLayout = new JPanel(new BorderLayout());

        spareIngredientsAddButton=new JButton(WhatToCook.SelectedPackage.get(88));
        spareIngredientsRemoveButton = new JButton(WhatToCook.SelectedPackage.get(89));


        spareIngredientsComboBox = new JComboBox<>();


        spareIngredientsListScrollPane = new JScrollPane(spareIngredientsInputList);


        manageIngredientsInputList.addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()) {
                if (manageIngredientsInputList.getSelectedIndices().length >= 1) {
                    SpareIngredientsList.rebuildListModel(spareIngredientsInputListModel, new Ingredient(manageIngredientsInputListModel.getElementAt(manageIngredientsInputList.getSelectedIndices()[manageIngredientsInputList.getSelectedIndices().length - 1])));
                    SpareIngredientsList.rebuildComboBox(spareIngredientsComboBox, new Ingredient(manageIngredientsInputListModel.getElementAt(manageIngredientsInputList.getSelectedIndices()[manageIngredientsInputList.getSelectedIndices().length - 1])));
                }
            }
        });

        spareIngredientsAddButton.addActionListener(e ->{
            boolean exist = false;
            for(int i = 0;i<spareIngredientsInputListModel.getSize();i++)
                if(spareIngredientsInputListModel.getElementAt(i).equals(spareIngredientsComboBox.getSelectedItem().toString()))
                    exist=true;
            if(!exist)
            {
                spareIngredientsInputListModel.addElement(spareIngredientsComboBox.getSelectedItem().toString());
                SpareIngredientsList.addSpareIngredient(new Ingredient(spareIngredientsComboBox.getSelectedItem().toString()),new Ingredient(manageIngredientsInputListModel.getElementAt(manageIngredientsInputList.getSelectedIndices()[manageIngredientsInputList.getSelectedIndices().length-1])));
                IngredientsList.rewriteFile();
            }

        });

        spareIngredientsRemoveButton.addActionListener(e ->{
            for(int i = spareIngredientsInputList.getSelectedIndices().length - 1;i>=0;i--) {
                SpareIngredientsList.removeSpareIngredient(new Ingredient(spareIngredientsInputListModel.getElementAt(spareIngredientsInputList.getSelectedIndices()[i])),new Ingredient(manageIngredientsInputListModel.getElementAt(manageIngredientsInputList.getSelectedIndices()[manageIngredientsInputList.getSelectedIndices().length-1])));
                spareIngredientsInputListModel.removeElementAt(spareIngredientsInputList.getSelectedIndices()[i]);
                IngredientsList.rewriteFile();
            }
        });

        //spareIngredientsListScrollPane.setBorder(new EmptyBorder(5,5,5,5));
        //spareIngredientsListScrollPane.setBorder(BorderFactory.createMatteBorder(5,5,5,5,backgroundColor));

        spareIngredientsUpBorderLayout.add(new JLabel(WhatToCook.SelectedPackage.get(90),SwingConstants.CENTER),BorderLayout.NORTH);

        spareIngredientsMainGridLayout.add(spareIngredientsComboBox);
        spareIngredientsLeftGridLayout.add(spareIngredientsAddButton);
        spareIngredientsLeftGridLayout.add(spareIngredientsRemoveButton);

        ingredientsRightGridLayout.add(new JLabel(WhatToCook.SelectedPackage.get(28), SwingConstants.CENTER));
        ingredientsRightGridLayout.add(newIngredientTextField);
        ingredientsRightGridLayout.add(newIngredientButton);
        ingredientsRightGridLayout.add(removeIngredientsButton);

        spareIngredientsMainBorderLayout.add(spareIngredientsListScrollPane,BorderLayout.CENTER);

        spareIngredientsMainGridLayout.add(spareIngredientsLeftGridLayout);
        spareIngredientsUpBorderLayout.add(spareIngredientsMainGridLayout,BorderLayout.CENTER);
        spareIngredientsMainBorderLayout.add(spareIngredientsUpBorderLayout, BorderLayout.NORTH);
        ingredientsRightBorderLayout.add(spareIngredientsMainBorderLayout,BorderLayout.CENTER);
        ingredientsRightBorderLayout.add(ingredientsRightGridLayout, BorderLayout.NORTH);
        ingredientsMainGridLayout.add(manageIngredientsListScrollPane);
        ingredientsMainGridLayout.add(ingredientsRightBorderLayout);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //ODŚWIEŻENIE INTERFEJSU

        refreshGUILists(searchForRecipesTextArea.getText());

        //MENU "ZAKŁADKOWE"

        mainTable = new JTabbedPaneCloseButton();
        mainTable.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        if (cards[0])
            mainTable.addTabNoExit(WhatToCook.SelectedPackage.get(8), mainBorderLayout);
        if (cards[1])
            mainTable.addTabNoExit(WhatToCook.SelectedPackage.get(9), manageRecipesMainPanel);
        if (cards[2])
            mainTable.addTabNoExit(WhatToCook.SelectedPackage.get(27), ingredientsMainGridLayout);
        add(mainTable);
        if(size==16) {
            pack();
        }
        setResizable(true);
        if(size==8) {
            setSize(250,300);
        }
        if(size==12) {
            setSize(450,600);
        }
        repaint();

    }

    //FUNCKJA OTWIERA NOWĄ KARTĘ Z KONKRETNYM PRZEPISEM
    private void showRecipe(Recipe recipeToShow) {
        recipesBorderLayout = new JPanel(new BorderLayout());
        recipeTextArea = new JTextArea();
        recipeTextArea.setFont(new Font("monospaced", Font.PLAIN, 12));
        recipeTextArea.setEditable(false);
        recipeTextArea.setLineWrap(true);

        String toShow = "";
        toShow += WhatToCook.SelectedPackage.get(65) +" "+recipeToShow.getName() + WhatToCook.endl + WhatToCook.endl + WhatToCook.endl;
        toShow += WhatToCook.SelectedPackage.get(57) + " ";
        if (recipeToShow.getParameters().getPreparingEase() == 0)
            toShow += WhatToCook.SelectedPackage.get(62);
        if (recipeToShow.getParameters().getPreparingEase() == 1)
            toShow += WhatToCook.SelectedPackage.get(63);
        if (recipeToShow.getParameters().getPreparingEase() == 2)
            toShow += WhatToCook.SelectedPackage.get(64);
        toShow += WhatToCook.endl + WhatToCook.endl;
        toShow += WhatToCook.SelectedPackage.get(56) + " ";
        if (recipeToShow.getParameters().getPreparingTime() == 0)
            toShow += WhatToCook.SelectedPackage.get(59);
        if (recipeToShow.getParameters().getPreparingTime() == 1)
            toShow += WhatToCook.SelectedPackage.get(60);
        if (recipeToShow.getParameters().getPreparingTime() == 2)
            toShow += WhatToCook.SelectedPackage.get(61);
        toShow += WhatToCook.endl + WhatToCook.endl;
        toShow += WhatToCook.SelectedPackage.get(50);
        if (recipeToShow.getParameters().getParameters()[0])
            toShow += " " + WhatToCook.SelectedPackage.get(51);
        if (recipeToShow.getParameters().getParameters()[1])
            toShow += " " + WhatToCook.SelectedPackage.get(52);
        if (recipeToShow.getParameters().getParameters()[2])
            toShow += " " + WhatToCook.SelectedPackage.get(53);
        if (recipeToShow.getParameters().getParameters()[3])
            toShow += " " + WhatToCook.SelectedPackage.get(54);
        if (recipeToShow.getParameters().getParameters()[4])
            toShow += " " + WhatToCook.SelectedPackage.get(55);
        toShow += WhatToCook.endl + WhatToCook.endl;
        toShow += WhatToCook.SelectedPackage.get(27) + WhatToCook.endl + WhatToCook.endl;
        for (int i = 0; i < recipeToShow.getSize(); i++) {
            toShow += recipeToShow.getIngredient(i).toString() + " " + recipeToShow.getAmount(i) + " " + recipeToShow.getUnit(i) + WhatToCook.endl;

        }
        toShow += WhatToCook.endl + WhatToCook.endl;
        toShow += WhatToCook.SelectedPackage.get(66) + WhatToCook.endl + WhatToCook.endl + recipeToShow.getRecipe();

        recipeTextArea.setText(toShow);
        final String toExport = toShow;
        recipeTextAreaScrollPane = new JScrollPane(recipeTextArea);
        recipesBorderLayout.add(recipeTextAreaScrollPane, BorderLayout.CENTER);
        recipeTextAreaScrollPane.setBorder(BorderFactory.createMatteBorder(5,5,5,5,backgroundColor));
        mainTable.addTab(recipeToShow.getName(), recipesBorderLayout);
        if (MainWindow.getToNewCard) {
            mainTable.setSelectedIndex(mainTable.getTabCount() - 1);
        }

        JPopupMenu showRecipePopup;
        showRecipePopup = new JPopupMenu();
        Action addToBuyListAction = new AbstractAction(WhatToCook.SelectedPackage.get(108)) {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < recipeToShow.getSize();i++)
                {
                    Ingredient ingredient = recipeToShow.getIngredient(i);
                    boolean contain = false;
                    for(int j = 0; j < ingredientsInputListModel.getSize();j++) {
                        if(ingredientsInputListModel.get(j).equals(ingredient.getName()))
                            contain = true;
                    }
                    if(!contain)
                        ToBuyIngredientsList.add(recipeToShow.getIngredient(i));
                }
            }
        };
        Action exportRecipeAction = new AbstractAction(WhatToCook.SelectedPackage.get(44)) {
            public void actionPerformed(ActionEvent event) {
                exportTab(recipeToShow, toExport);
            }
        };
        Action closeRecipeAction = new AbstractAction(WhatToCook.SelectedPackage.get(23)) {
            public void actionPerformed(ActionEvent event) {
                closeTab();
            }
        };
        showRecipePopup.add(addToBuyListAction);
        showRecipePopup.add(exportRecipeAction);
        showRecipePopup.add(closeRecipeAction);
        recipeTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showRecipePopup.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showRecipePopup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    /*
        *Funkcja nieużywana, zastąpiona tą poniżej
        private void refreshGUILists() {
        recipesListModel.clear();
        for (int i = 0; i < RecipesList.recipesList.size(); i++) {
            recipesListModel.addElement(RecipesList.recipesList.get(i).getName());
        }
    }*/

    private void refreshGUILists(String StartWith) {
        recipesListModel.clear();
        for (int i = 0; i < RecipesList.recipesList.size(); i++) {
            if (searchingDialog.wholeWords.isSelected() && searchingDialog.caseSensitiveCheckBox.isSelected()) {
                if (extendedStartsWith(RecipesList.recipesList.get(i).getName().split(" "), StartWith, true)) {
                    recipesListModel.addElement(RecipesList.recipesList.get(i).getName());
                }
            }
            if (searchingDialog.wholeWords.isSelected() && !searchingDialog.caseSensitiveCheckBox.isSelected()) {
                if (extendedStartsWith(RecipesList.recipesList.get(i).getName().split(" "), StartWith, false)) {
                    recipesListModel.addElement(RecipesList.recipesList.get(i).getName());
                }
            }
            if (!searchingDialog.wholeWords.isSelected() && searchingDialog.caseSensitiveCheckBox.isSelected()) {
                if (RecipesList.recipesList.get(i).getName().startsWith(StartWith)) {
                    recipesListModel.addElement(RecipesList.recipesList.get(i).getName());
                }
            }
            if (!searchingDialog.wholeWords.isSelected() && !searchingDialog.caseSensitiveCheckBox.isSelected()) {
                if (RecipesList.recipesList.get(i).getName().toLowerCase().startsWith(StartWith.toLowerCase())) {
                    recipesListModel.addElement(RecipesList.recipesList.get(i).getName());
                }
            }
        }
    }

    private void closeTab() {
        int SelectedIndex = mainTable.getSelectedIndex();
        mainTable.setSelectedIndex(shownRecipesList.getStartPage(mainTable.getSelectedIndex()));
        shownRecipesList.remove(SelectedIndex);
        mainTable.removeTabAt(SelectedIndex);
    }

    private boolean extendedStartsWith(String[] words, String start, boolean caseSensitive) {
        for (String word : words) {
            if (!caseSensitive) {
                if (word.toLowerCase().startsWith(start.toLowerCase()))
                    return true;
            }
            if (caseSensitive) {
                if (word.startsWith(start))
                    return true;
            }
        }
        return false;
    }

    //FUNKCJA TWORZY KARTĘ DO EDYCJI PRZEPISU
    private void showNewEditMenu(Recipe recipe) {
        ArrayList<ListHandler> ingredientsListInput = new ArrayList<>();
        class addIngredientToRecipe implements KeyListener {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (!ingredientInCreatingRecipeComboBox.getSelectedItem().equals("")) {
                        String newForm = "● " + ingredientInCreatingRecipeComboBox.getSelectedItem();
                        newForm += " " + newEditAmmountTextArea.getText() + " " + newEditUnitTextArea.getText();
                        boolean exist = false;
                        for (int i = 0; i < ingredientsInputInRecipeListModel.getSize(); i++) {
                            if ((newForm.equals(ingredientsInputInRecipeListModel.get(i)))) {
                                exist = true;
                                break;
                            }
                        }
                        if (!exist) {
                            ingredientsInputInRecipeListModel.addElement(newForm);
                            ingredientsListInput.add(new ListHandler(ingredientInCreatingRecipeComboBox.getSelectedItem().toString(), newEditAmmountTextArea.getText(), newEditUnitTextArea.getText()));
                            newEditAmmountTextArea.setText("");
                            newEditUnitTextArea.setText("");
                        }
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        }
        addIngredientToRecipe addIngredientKeyListener = new addIngredientToRecipe();
        creatingRecipeTable = new JTabbedPane();
        newEditMainBorderLayout = new JPanel(new BorderLayout());
        newEditMainGridLayout = new JPanel(new GridLayout(2, 1));
        newEditUpGridLayout = new JPanel((new GridLayout(1, 2)));
        newEditDownGridLayout = new JPanel(new GridLayout(1, 2));
        newEditUpRightGridLayout = new JPanel(new GridLayout(6, 1));
        newEditParametersGrid = new JPanel(new GridLayout(10, 1));
        newEditMainDownBorderLayout = new JPanel(new BorderLayout());
        newEditMainUpBorderLayout = new JPanel(new BorderLayout());
        newEditTopGridLayout = new JPanel(new GridLayout(1, 2));
        newEditAmmountAndUnitGridLayoutUp = new JPanel(new GridLayout(1, 2));
        newEditAmmountAndUnitGridLayoutDown = new JPanel(new GridLayout(1, 2));

        newEditAmmountTextArea = new JTextField();
        newEditUnitTextArea = new JTextField();
        newEditAmmountTextArea.addKeyListener(addIngredientKeyListener);
        newEditUnitTextArea.addKeyListener(addIngredientKeyListener);

        newEditAmmountAndUnitGridLayoutUp.add(new JLabel(WhatToCook.SelectedPackage.get(48)));
        newEditAmmountAndUnitGridLayoutUp.add(newEditAmmountTextArea);

        newEditAmmountAndUnitGridLayoutDown.add(new JLabel(WhatToCook.SelectedPackage.get(49)));
        newEditAmmountAndUnitGridLayoutDown.add(newEditUnitTextArea);

        newEditAmmountAndUnitGridLayoutUp.setBorder(new EmptyBorder(2, 2, 2, 2));
        newEditAmmountAndUnitGridLayoutDown.setBorder(new EmptyBorder(2, 2, 2, 2));

        newEditParametersGrid.add(new JLabel(WhatToCook.SelectedPackage.get(50), SwingConstants.CENTER));

        JCheckBox NewEditbreakfestCheckBox = new JCheckBox(WhatToCook.SelectedPackage.get(51));
        JCheckBox NewEditdessertCheckBox = new JCheckBox(WhatToCook.SelectedPackage.get(52));
        JCheckBox NewEditdinerCheckBox = new JCheckBox(WhatToCook.SelectedPackage.get(53));
        JCheckBox NewEditsupperCheckBox = new JCheckBox(WhatToCook.SelectedPackage.get(54));
        JCheckBox NewEditsnackCheckBox = new JCheckBox(WhatToCook.SelectedPackage.get(55));
        newEditParametersGrid.add(NewEditbreakfestCheckBox);
        newEditParametersGrid.add(NewEditdinerCheckBox);
        newEditParametersGrid.add(NewEditsupperCheckBox);
        newEditParametersGrid.add(NewEditdessertCheckBox);
        newEditParametersGrid.add(NewEditsnackCheckBox);

        newEditParametersGrid.add(new JLabel(WhatToCook.SelectedPackage.get(56), SwingConstants.CENTER));

        JComboBox<String> NewEditPreparingTimeComboBox = new JComboBox<>();
        NewEditPreparingTimeComboBox.addItem(WhatToCook.SelectedPackage.get(59));
        NewEditPreparingTimeComboBox.addItem(WhatToCook.SelectedPackage.get(60));
        NewEditPreparingTimeComboBox.addItem(WhatToCook.SelectedPackage.get(61));

        newEditParametersGrid.add(NewEditPreparingTimeComboBox);

        newEditParametersGrid.add(new JLabel(WhatToCook.SelectedPackage.get(57), SwingConstants.CENTER));

        JComboBox<String> NewEditEaseToPrepare = new JComboBox<>();
        NewEditEaseToPrepare.addItem(WhatToCook.SelectedPackage.get(62));
        NewEditEaseToPrepare.addItem(WhatToCook.SelectedPackage.get(63));
        NewEditEaseToPrepare.addItem(WhatToCook.SelectedPackage.get(64));

        newEditParametersGrid.add(NewEditEaseToPrepare);

        editNewExitWithoutSaving = new JButton(WhatToCook.SelectedPackage.get(22));
        editNewExitWithoutSaving.addActionListener(e -> {
            isEditionTurnOn = false;
            mainTable.removeTabAt(mainTable.getSelectedIndex());
            mainTable.setSelectedIndex(1);
            inEdit = null;
        });
        editNewExitWithSaving = new JButton(WhatToCook.SelectedPackage.get(21));
        editNewExitWithSaving.addActionListener(e -> {
            String name1 = recipeNameTextField.getText();
            String instructions = instructionsInsertTextArea.getText();
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            ArrayList<PairAmountUnit> ammountsAndUnits = new ArrayList<>();
            for (ListHandler handler : ingredientsListInput) {
                Ingredient ingredient;
                ingredient = new Ingredient(handler.getIngredient());
                ammountsAndUnits.add(new PairAmountUnit(handler.getAmmount(), handler.getUnit()));
                ingredients.add(ingredient);
            }
            boolean parameters[] = new boolean[5];
            parameters[0] = NewEditbreakfestCheckBox.isSelected();
            parameters[1] = NewEditdessertCheckBox.isSelected();
            parameters[2] = NewEditdinerCheckBox.isSelected();
            parameters[3] = NewEditsupperCheckBox.isSelected();
            parameters[4] = NewEditsnackCheckBox.isSelected();
            Recipe newRecipe1 = new Recipe(name1, ingredients, ammountsAndUnits, instructions, new RecipeParameters(parameters, NewEditEaseToPrepare.getSelectedIndex(), NewEditPreparingTimeComboBox.getSelectedIndex()));
            if (recipe == null) {
                if ((!name1.equals("")) && (!instructions.equals("")) && (!ingredients.isEmpty()) && (!RecipesList.isRecipe(newRecipe1))) {
                    RecipesList.add(newRecipe1);
                    refreshGUILists(searchForRecipesTextArea.getText());
                    isEditionTurnOn = false;
                    mainTable.removeTabAt(mainTable.getSelectedIndex());
                    inEdit = null;
                    mainTable.setSelectedIndex(1);
                } else
                    JOptionPane.showConfirmDialog(null, WhatToCook.SelectedPackage.get(32), WhatToCook.SelectedPackage.get(33), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            } else if ((!name1.equals("")) && (!instructions.equals("")) && (!ingredients.isEmpty())) {
                if (recipe.getName().equals(name1) || (!RecipesList.isRecipe(newRecipe1))) {
                    RecipesList.remove(recipe.getName());
                    RecipesList.add(newRecipe1);
                    refreshGUILists(searchForRecipesTextArea.getText());
                    isEditionTurnOn = false;
                    mainTable.removeTabAt(mainTable.getSelectedIndex());
                    mainTable.setSelectedIndex(1);
                    inEdit = null;
                }
            } else
                JOptionPane.showConfirmDialog(null, WhatToCook.SelectedPackage.get(32), WhatToCook.SelectedPackage.get(33), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        });

        newEditAddIngredientButton = new JButton(WhatToCook.SelectedPackage.get(12));
        newEditAddIngredientButton.addActionListener(event -> {
            if (!ingredientInCreatingRecipeComboBox.getSelectedItem().equals("")) {
                String newForm = "● " + ingredientInCreatingRecipeComboBox.getSelectedItem();
                newForm += " " + newEditAmmountTextArea.getText() + " " + newEditUnitTextArea.getText();
                boolean exist = false;
                for (int i = 0; i < ingredientsInputInRecipeListModel.getSize(); i++) {
                    if ((newForm.equals(ingredientsInputInRecipeListModel.get(i)))) {
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    ingredientsInputInRecipeListModel.addElement(newForm);
                    ingredientsListInput.add(new ListHandler(ingredientInCreatingRecipeComboBox.getSelectedItem().toString(), newEditAmmountTextArea.getText(), newEditUnitTextArea.getText()));
                    newEditAmmountTextArea.setText("");
                    newEditUnitTextArea.setText("");
                }
            }
        });
        ingredientsInputInRecipeListModel = new DefaultListModel<>();
        ingredientsInputinRecipeList = new JList<>();
        ingredientsInputinRecipeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ingredientsInputinRecipeList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        ingredientsInputinRecipeList.setVisibleRowCount(-1);
        ingredientsInputinRecipeList = new JList<>(ingredientsInputInRecipeListModel);
        ingredientsInputinRecipeListScrollPane = new JScrollPane(ingredientsInputinRecipeList);

        newEditRemoveIngredientButton = new JButton(WhatToCook.SelectedPackage.get(13));
        newEditRemoveIngredientButton.addActionListener(event -> {
            for (int i = ingredientsInputinRecipeList.getSelectedIndices().length - 1; i >= 0; i--) {
                ingredientsListInput.remove(ingredientsInputinRecipeList.getSelectedIndices()[i]);
                ingredientsInputInRecipeListModel.removeElementAt(ingredientsInputinRecipeList.getSelectedIndices()[i]);
            }
        });


        instructionsInsertTextArea = new JTextArea();
        instructionsInsertTextArea.setFont(new Font("monospaced", Font.PLAIN, 12));
        instructionsInsertTextArea.setLineWrap(true);
        instructionAreaJScrollPane = new JScrollPane(instructionsInsertTextArea);
        recipeNameTextField = new JTextField();

        newEditTopGridLayout.add(new JLabel(WhatToCook.SelectedPackage.get(34), SwingConstants.CENTER));
        newEditTopGridLayout.add(recipeNameTextField);
        newEditMainUpBorderLayout.add(newEditTopGridLayout, BorderLayout.NORTH);

        if (recipe != null) {
            recipeNameTextField.setText(recipe.getName());
            instructionsInsertTextArea.setText(recipe.getRecipe());
            for (int i = 0; i < recipe.getSize(); i++) {
                String toAdd;
                toAdd = "● " + recipe.getIngredient(i).getName();
                toAdd += " " + recipe.getAmount(i);
                toAdd += " " + recipe.getUnit(i);

                ingredientsInputInRecipeListModel.addElement(toAdd);
                ingredientsListInput.add(new ListHandler(recipe.getIngredient(i).getName(), recipe.getAmount(i), recipe.getUnit(i)));
            }
            if (recipe.getParameters().getParameters()[0])
                NewEditbreakfestCheckBox.setSelected(true);
            if (recipe.getParameters().getParameters()[1])
                NewEditdessertCheckBox.setSelected(true);
            if (recipe.getParameters().getParameters()[2])
                NewEditdinerCheckBox.setSelected(true);
            if (recipe.getParameters().getParameters()[3])
                NewEditsupperCheckBox.setSelected(true);
            if (recipe.getParameters().getParameters()[4])
                NewEditsnackCheckBox.setSelected(true);
            NewEditEaseToPrepare.setSelectedIndex(recipe.getParameters().getPreparingEase());
            NewEditPreparingTimeComboBox.setSelectedIndex(recipe.getParameters().getPreparingTime());
            repaint();
        }

        newEditUpRightGridLayout.add(ingredientsInputinRecipeListScrollPane);
        newEditUpRightGridLayout.add(new JLabel(WhatToCook.SelectedPackage.get(11), SwingConstants.CENTER));
        newEditUpRightGridLayout.add(ingredientInCreatingRecipeComboBox);
        newEditUpRightGridLayout.add(newEditAmmountAndUnitGridLayoutUp);
        newEditUpRightGridLayout.add(newEditAmmountAndUnitGridLayoutDown);
        newEditUpRightGridLayout.add(newEditAddIngredientButton);
        newEditUpRightGridLayout.add(newEditRemoveIngredientButton);


        newEditDownGridLayout.add(editNewExitWithSaving);
        newEditDownGridLayout.add(editNewExitWithoutSaving);
        newEditMainDownBorderLayout.add(new JLabel(WhatToCook.SelectedPackage.get(20), SwingConstants.CENTER), BorderLayout.NORTH);
        newEditMainDownBorderLayout.add(instructionAreaJScrollPane, BorderLayout.CENTER);
        newEditUpGridLayout.add(ingredientsInputinRecipeListScrollPane);
        creatingRecipeTable.addTab(WhatToCook.SelectedPackage.get(27), newEditUpRightGridLayout);
        creatingRecipeTable.addTab(WhatToCook.SelectedPackage.get(58), newEditParametersGrid);
        newEditUpGridLayout.add(creatingRecipeTable);
        newEditMainUpBorderLayout.add(newEditUpGridLayout, BorderLayout.CENTER);

        newEditMainDownBorderLayout.add(newEditDownGridLayout, BorderLayout.SOUTH);


        newEditMainGridLayout.add(newEditMainUpBorderLayout);
        newEditMainGridLayout.add(newEditMainDownBorderLayout);

        newEditMainBorderLayout.add(newEditMainGridLayout, BorderLayout.CENTER);
        if (recipe == null) {
            mainTable.addTabNoExit(WhatToCook.SelectedPackage.get(17), newEditMainBorderLayout);
        } else {
            mainTable.addTabNoExit(recipe.getName(), newEditMainBorderLayout);
        }
        if (MainWindow.getToNewCard) {
            mainTable.setSelectedIndex(mainTable.getTabCount() - 1);
        }

    }

    private void exportIngredientsList() {
        JFileChooser chooseFile = new JFileChooser();
        int save = chooseFile.showSaveDialog(null);
        if (save == JFileChooser.APPROVE_OPTION) {
            String filename = chooseFile.getSelectedFile().getPath();
            IngredientsList.exportToFile(filename);
        }
    }

    private void importIngredientsList() {
        JFileChooser chooseFile = new JFileChooser();
        int save = chooseFile.showOpenDialog(null);
        if (save == JFileChooser.APPROVE_OPTION) {
            String filename = chooseFile.getSelectedFile().getPath();
            String name;
            try {
                Scanner in = new Scanner(new File(filename));
                while (in.hasNextLine()) {
                    name = in.nextLine();
                    Ingredient toAdd = new Ingredient(name);
                    IngredientsList.addIngredient(toAdd);
                }
                IngredientsList.rebuildModel(manageIngredientsInputListModel);
                IngredientsList.reloadComboBox(ingredientInSearchComboBox);
                IngredientsList.reloadComboBox(ingredientInCreatingRecipeComboBox);
                in.close();

            } catch (FileNotFoundException e) {
                System.err.println("File " + filename + "don't exist.");
            }

        }
    }

    private boolean isFalse(boolean parameters[], int n) {
        for (int i = 0; i < n; i++) {
            if (parameters[i]) {
                return false;
            }
        }

        return true;
    }

    void exportOwnedIngredients() {
        PrintWriter writer;
        try {
            writer = new PrintWriter("src/ownedIngredients", "UTF-8");
            for (int i = 0; i < ingredientsInputListModel.size(); i++)
                writer.println(ingredientsInputListModel.get(i).substring(2));

            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException exception) {
            System.err.println("Exporting ingredients list error.");
        }
    }

    private class JTabbedPaneCloseButton extends JTabbedPane {

        private JTabbedPaneCloseButton() {
            super();
        }

        /* Override Addtab in order to add the close Button everytime */
        @Override
        public void addTab(String title, Icon icon, Component component, String tip) {
            //Component titledComponent = component;
            component.setName(title);
            super.addTab(title, icon, component, tip);
            int count = this.getTabCount() - 1;
            setTabComponentAt(count, new CloseButtonTab(component, title, icon));
        }

        @Override
        public void addTab(String title, Icon icon, Component component) {
            addTab(title, icon, component, null);
        }

        @Override
        public void addTab(String title, Component component) {
            addTab(title, null, component);
        }

        /* addTabNoExit */
        void addTabNoExit(String title, Icon icon, Component component, String tip) {
            super.addTab(title, icon, component, tip);
        }

        void addTabNoExit(String title, Icon icon, Component component) {
            addTabNoExit(title, icon, component, null);
        }

        void insertTabNoExit(String title, Component component, int index) {
            super.insertTab(title, null, component, null, index);
        }

        void addTabNoExit(String title, Component component) {
            addTabNoExit(title, null, component);
        }

        /* Button */
        class CloseButtonTab extends JPanel {
            private Component tab;

            CloseButtonTab(final Component tab, String title, Icon icon) {
                this.tab = tab;
                setOpaque(false);
                BorderLayout borderLayout = new BorderLayout();
                JLabel jLabel = new JLabel(title + " ", SwingConstants.CENTER);
                jLabel.setFont(new Font(font,Font.PLAIN,12));
                jLabel.setIcon(icon);
                ImageIcon imageIcon = new ImageIcon(new ImageIcon("data/graphics/X_icon.png").getImage().getScaledInstance(10, 10, Image.SCALE_DEFAULT));
                JButton button = new JButton(imageIcon);
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                button.setMargin(new Insets(0, 0, 0, 0));
                button.addMouseListener(new CloseListener(tab));
                setLayout(borderLayout);
                add(jLabel, BorderLayout.CENTER);
                add(button, BorderLayout.EAST);
            }
        }

        /* ClickListener */
        void CloseTabByComponent(Component component) {
            mainTable.remove(component);
        }

        class CloseListener implements MouseListener {
            private Component tab;

            CloseListener(Component tab) {
                this.tab = tab;
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() instanceof JButton) {
                    JButton clickedButton = (JButton) e.getSource();
                    mainTable.remove(tab);
                    mainTable.setSelectedIndex(1);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        }
    }

    private void exportTab(Recipe recipeToShow, String toExport) {
        JFileChooser chooseFile = new JFileChooser();
        chooseFile.setSelectedFile(new File(recipeToShow.getName() + ".txt"));
        int save = chooseFile.showSaveDialog(null);
        if (save == JFileChooser.APPROVE_OPTION) {
            String filename = chooseFile.getSelectedFile().getPath();
            PrintWriter writer;
            try {
                writer = new PrintWriter(filename, "UTF-8");

                writer.println(toExport);

                writer.close();
            } catch (FileNotFoundException | UnsupportedEncodingException exception) {
                System.err.println("Exporting recipe error.");
            }
        }
    }


    boolean getShowSearchMenuStatus() {
        return showSearchMenu.isSelected();
    }

    boolean getShowRecipesMenuStatus() {
        return showRecipesMenu.isSelected();
    }

    boolean getShowIngredientsMenu() {
        return showIngredientsMenu.isSelected();
    }

    private int getTruthsAmmount(boolean[] toCount) {
        int counter = 0;
        for (boolean b : toCount) {
            if (b)
                counter++;
        }
        return counter;
    }

    public boolean getSpareIngredientsCheckButtonValue()
    {
        return spareIngredientsCheckBox.isSelected();
    }
    public void openSettings(int i) {
        settingsDialog.mainTable.setSelectedIndex(i);
        settingsDialog.setVisible(true);
    }
    //ELEMENTY GUI BAZY SKŁADNIKÓW
    private JTabbedPaneCloseButton mainTable;
    private JTabbedPane creatingRecipeTable;

    private JScrollPane ingredientsInputListScrollPane;
    private JScrollPane recipesOutputListScrollPane;
    private JScrollPane recipesListScrollPane;
    private JScrollPane ingredientsInputinRecipeListScrollPane;
    private JScrollPane manageIngredientsListScrollPane;
    private JScrollPane recipeTextAreaScrollPane;
    private JScrollPane instructionAreaJScrollPane;
    private JScrollPane spareIngredientsListScrollPane;

    private JCheckBox breakfestCheckBox;
    private JCheckBox dessertCheckBox;
    private JCheckBox dinerCheckBox;
    private JCheckBox supperCheckBox;
    private JCheckBox snackCheckBox;
    private JCheckBox spareIngredientsCheckBox;

    private JComboBox<String> ingredientInSearchComboBox;
    private JComboBox<String> ingredientInCreatingRecipeComboBox;
    private JComboBox<String> PreparingTimeComboBox;
    private JComboBox<String> EaseToPrepare;
    private JComboBox<String> spareIngredientsComboBox;

    private JButton newIngredientButton;
    private JButton removeIngredientsButton;
    private JButton importIngredientsInSearch;
    private JButton exportIngredientsInSearch;
    private JButton editNewExitWithoutSaving;
    private JButton editNewExitWithSaving;
    private JButton execute;
    private JButton addIngredientButton;
    private JButton removeIngredientButton;
    private JButton newRecipe;
    private JButton editRecipe;
    private JButton deleteRecipe;
    private JButton newEditAddIngredientButton;
    private JButton newEditRemoveIngredientButton;
    private JButton searchingOptionsButton;
    private JButton spareIngredientsAddButton;
    private JButton spareIngredientsRemoveButton;

    private JPanel ingredientsMainGridLayout;
    private JPanel ingredientsRightBorderLayout;
    private JPanel ingredientsRightGridLayout;
    private JPanel ingredientsDownGridLayout;
    private JPanel ingredientsRightDownGridLayout;
    private JPanel recipesBorderLayout;
    private JPanel recipesGridLayout;
    private JPanel newEditMainBorderLayout;
    private JPanel newEditMainGridLayout;
    private JPanel newEditUpGridLayout;
    private JPanel newEditDownGridLayout;
    private JPanel newEditUpRightGridLayout;
    private JPanel newEditMainDownBorderLayout;
    private JPanel newEditMainUpBorderLayout;
    private JPanel newEditTopGridLayout;
    private JPanel newEditAmmountAndUnitGridLayoutUp;
    private JPanel newEditAmmountAndUnitGridLayoutDown;
    private JPanel newEditParametersGrid;
    private JPanel manageRecipesMainPanel;
    private JPanel manageRecipesGridPanel;
    private JPanel manageRecipesAndLinkedPanel;
    private JPanel manageRecipesLeftBorderLayout;
    private JPanel manageRecipesLeftUpGridPanel;
    private JPanel manageRecipesLeftDownGridPanel;
    private JPanel mainBorderLayout;
    private JPanel downBorderLayout;
    private JPanel upBorderLayout;
    private JPanel importExportInSearchGrid;
    private JPanel searchingOptionsBorderLayout;

    private JPanel spareIngredientsMainBorderLayout;
    private JPanel spareIngredientsUpBorderLayout;
    private JPanel spareIngredientsMainGridLayout;
    private JPanel spareIngredientsLeftGridLayout;

    private JTextArea recipeTextArea;
    private JTextArea instructionsInsertTextArea;

    private JTextField newIngredientTextField;
    private JTextField newEditAmmountTextArea;
    private JTextField newEditUnitTextArea;
    private JTextField recipeNameTextField;
    private JTextField searchForRecipesTextArea;

    private JPanel mainGridLayout;
    private JPanel upGridLayout;
    private JPanel upRightGridLayout;

    private JMenuBar mainMenu;

    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu helpMenu;
    private JMenu newSubmenu;
    private JMenu cardsSubmenu;
    private JMenu viewMenu;
    private JMenu toolsMenu;

    private JCheckBoxMenuItem showSearchMenu;
    private JCheckBoxMenuItem showRecipesMenu;
    private JCheckBoxMenuItem showIngredientsMenu;

    private JList<String> ingredientsInputList;
    private final DefaultListModel<String> ingredientsInputListModel;

    private JList<String> recipesOutputList;
    private final DefaultListModel<String> recipesOutputListModel;

    private JList<String> recipesList;
    private final DefaultListModel<String> recipesListModel;

    private JList<String> ingredientsInputinRecipeList;
    private DefaultListModel<String> ingredientsInputInRecipeListModel;

    private JList<String> manageIngredientsInputList;
    private final DefaultListModel<String> manageIngredientsInputListModel;

    private JList<String> spareIngredientsInputList;
    private final DefaultListModel<String> spareIngredientsInputListModel;

    private SettingsWindow settingsDialog;
    private AboutWindow aboutDialog;
    private ErrorWindow errorDialog;
    private SearchingWindow searchingDialog;
    private ToBuyListWindow shoppingListDialog;
    private TimerWindow timerDialog;

    private PairRecipeIndex shownRecipesList;

    public static int markedRecipe = -1;

    public static boolean getToNewCard;
    public static boolean autoLoadIngredients;
    public static boolean searchInEveryWord;
    public static boolean searchCaseSensitive;
    private boolean isEditionTurnOn;

    int mainCardsCount;

    private Recipe inEdit;

    static public Color backgroundColor;

    static public String font;

    static public int size;

    static public String theme;

    public static boolean biggerLabels;
    public static boolean bordersAroundLists;

}
