package gui;

import core.RecipesList;
import core.LinkedRecipes;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.StyleContext;
import javax.swing.JComboBox;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import static core.LinkedRecipes.addLinking;
import static core.LinkedRecipes.deleteLinking;
import static core.RecipesList.recipesList;
import static gui.MainWindow.markedRecipe;
import static javax.swing.SwingConstants.NORTH;

/**
 * Created by Radek on 2016-05-17.
 */
public class LinkedRecipesPanel {

    public static void manageLinkedRecipes(JPanel manageRecipesAndLinkedPanel) {
        JPanel linkedRecipesPanel = new JPanel();
        linkedRecipesPanel.setLayout(new BorderLayout());
        manageRecipesAndLinkedPanel.add(linkedRecipesPanel);

        //Cześć z tytułem, listą dostępnych przepisów i przyciskami do dodawania i usuwania
        JPanel manage = new JPanel(new GridLayout(3, 1));
            //Tytuł
        JLabel panelName = new JLabel("Przepisy powiązane", JLabel.CENTER);
        manage.add(panelName);
            //Lista dost. przepisów
        allRecipes = new JComboBox();
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
        JScrollPane linkedRecipes = new JScrollPane();
        linkedRecipesPanel.add(linkedRecipes, BorderLayout.CENTER);
    }

    private static void linkedRecipesButtons(JPanel panel) {
        JButton addLinkingButton = new JButton("Dodaj");
        addLinkingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addLinking(markedRecipe, allRecipes.getSelectedIndex());
            }
        });
        panel.add(addLinkingButton);

        JButton delLinkingButton = new JButton("Usuń");
        delLinkingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteLinking(markedRecipe, allRecipes.getSelectedIndex());
            }
        });
        panel.add(delLinkingButton);
    }

    private static JComboBox allRecipes;
}
