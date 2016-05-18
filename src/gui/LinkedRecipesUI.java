package gui;

import core.RecipesList;
import core.LinkedRecipes;

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
 */
public class LinkedRecipesUI {

    public static void manageLinkedRecipes(JPanel manageRecipesAndLinkedPanel) {
        linkedRecipesPanel = new JPanel();
        linkedRecipesPanel.setLayout(new BorderLayout());
        manageRecipesAndLinkedPanel.add(linkedRecipesPanel);

        //Cześć z tytułem, listą dostępnych przepisów i przyciskami do dodawania i usuwania
        JPanel manage = new JPanel(new GridLayout(3, 1));
            //Tytuł
        JLabel panelName = new JLabel("Przepisy powiązane", JLabel.CENTER);
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

        //Dodanie przestrzeni na przepisy powiązane
        linkedRecipesArea = new JPanel();
        linkedRecipesArea.setLayout(new GridLayout(16, 1));

        linkedRecipesPanel.add(linkedRecipesArea, BorderLayout.CENTER);
    }

    public static void showLinkedRecipes() {
        String temp;
        JRadioButton button;
        linkedRecipesArea.removeAll();
        for (int i = 0; i < recipesList.get(markedRecipe).getLinkedRecipes().size(); i++) {
            temp = recipesList.get(markedRecipe).getLinkedRecipes().get(i);
            button = new JRadioButton(temp);
            button.setMnemonic(i);
            linkedRecipesArea.add(button);
            linkedRecipesButtonGroup.add(button);
        }
        linkedRecipesArea.updateUI();
    }

    private static void linkedRecipesButtons(JPanel panel) {
        JButton addLinkingButton = new JButton("Dodaj");

        addLinkingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(markedRecipe>=0) {
                    if (recipesList.get(markedRecipe).getLinkedRecipes().size() < 16 && !recipesList.get(markedRecipe).getName().equals(allRecipes.getSelectedItem())) {
                        addLinking(markedRecipe, allRecipes.getSelectedIndex());
                        showLinkedRecipes();
                    }
                }
            }
        });
        panel.add(addLinkingButton);

        JButton delLinkingButton = new JButton("Usuń");
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

    private static JComboBox<String> allRecipes;
    private static JPanel linkedRecipesArea;
    private static JPanel linkedRecipesPanel;
    private static ButtonGroup linkedRecipesButtonGroup = new ButtonGroup();
}
