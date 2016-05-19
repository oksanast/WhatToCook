package gui;

import core.RecipesList;
import core.LinkedRecipes;
import core.WhatToCook;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.StyleContext;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;


import static core.LinkedRecipes.addLinking;
import static core.LinkedRecipes.deleteLinking;
import static core.RecipesList.recipesList;
import static gui.MainWindow.markedRecipe;
import static javax.swing.SwingConstants.NORTH;

/**
 * Created by WTC-Team on 2016-05-17.
 * Project WhatToCook
 */
public class LinkedRecipesUI {

    public static void manageLinkedRecipes(JPanel manageRecipesAndLinkedPanel) {
        linkedRecipesPanel = new JPanel();
        linkedRecipesPanel.setLayout(new BorderLayout());
        manageRecipesAndLinkedPanel.add(linkedRecipesPanel);

        //Cześć z tytułem, listą dostępnych przepisów i przyciskami do dodawania i usuwania
        JPanel manage = new JPanel(new GridLayout(3, 1));
            //Tytuł
        JLabel panelName = new JLabel(WhatToCook.SelectedPackage.get(116), JLabel.CENTER);
        manage.add(panelName);
            //Lista dost. przepisów
        allRecipes = new JComboBox<>();
        for (int i = 0; i < recipesList.size(); i++)
            allRecipes.addItem(recipesList.get(i).getName());
        manage.add(allRecipes);
            //Przyciski
        JPanel manageLinkingButtons = new JPanel(new GridLayout(1, 2));
        linkedRecipesButtons(manageLinkingButtons);
        manage.add(manageLinkingButtons);

        //Dodanie "panelu zarządzania" do panelu ogólnie do przepisów powiązanych
        linkedRecipesPanel.add(manage, BorderLayout.NORTH);
        amountLabel = new JLabel();
        amountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        linkedRecipesPanel.add(amountLabel,BorderLayout.SOUTH);

        //Dodanie przestrzeni na przepisy powiązane
        linkedRecipesArea = new JPanel();
        linkedRecipesArea.setLayout(new GridLayout(16, 1));

        linkedRecipesPanel.add(linkedRecipesArea, BorderLayout.CENTER);
    }

    public static void refreshComboBox() {
        allRecipes.removeAllItems();
        for (int i = 0; i < recipesList.size(); i++)
            allRecipes.addItem(recipesList.get(i).getName());
    }

    public static void showLinkedRecipes() {
        String temp;
        JRadioButton button;
        linkedRecipesArea.removeAll();
        for (int i = 0; i < RecipesList.getRecipe(markedRecipe).getLinkedRecipes().size(); i++) {
            temp = RecipesList.getRecipe(markedRecipe).getLinkedRecipes().get(i);
            button = new JRadioButton(temp);
            button.setMnemonic(i);
            linkedRecipesArea.add(button);
            linkedRecipesButtonGroup.add(button);
        }
        linkedRecipesArea.updateUI();
        amountLabel.setText(RecipesList.getRecipe(markedRecipe).getLinkedRecipes().size() + "/16");
    }

    private static void linkedRecipesButtons(JPanel panel) {
        JButton addLinkingButton = new JButton(WhatToCook.SelectedPackage.get(88));

        addLinkingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(markedRecipe!=null) {
                    if (RecipesList.getRecipe(markedRecipe).getLinkedRecipes().size() < 16 && !RecipesList.getRecipe
                            (markedRecipe).getName().equals(allRecipes.getSelectedItem())) {
                        addLinking(markedRecipe, allRecipes.getSelectedItem().toString());
                        showLinkedRecipes();
                    }
                }
            }
        });
        panel.add(addLinkingButton);

        JButton delLinkingButton = new JButton(WhatToCook.SelectedPackage.get(89));
        delLinkingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ButtonModel button = linkedRecipesButtonGroup.getSelection();
                linkedRecipesButtonGroup.clearSelection();
                if(button!=null) {
                    deleteLinking(markedRecipe, button.getMnemonic());
                    showLinkedRecipes();
                }
            }
        });
        panel.add(delLinkingButton);
    }

    private static JLabel amountLabel;
    private static JComboBox<String> allRecipes;
    private static JPanel linkedRecipesArea;
    private static JPanel linkedRecipesPanel;
    private static ButtonGroup linkedRecipesButtonGroup = new ButtonGroup();
}
