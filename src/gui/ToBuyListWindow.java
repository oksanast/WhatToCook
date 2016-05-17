package gui;

import core.Ingredient;
import core.ToBuyIngredientsList;
import core.WhatToCook;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by Mateusz on 17.05.2016.
 * Project WhatToCook
 */
public class ToBuyListWindow extends JDialog {
    public ToBuyListWindow(){
        setTitle(WhatToCook.SelectedPackage.get(107));
        setSize(270,400);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(270,200));
        setModal(true);

        downGridLayout = new JPanel(new GridLayout(1,2));

        ingredientsListModel = new DefaultListModel<>();
        ingredientsList = new JList<>();
        ingredientsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ingredientsList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        ingredientsList.setVisibleRowCount(-1);
        ingredientsList = new JList<>(ingredientsListModel);

        ingredientsList.setBackground(UIManager.getColor("Panel:background"));

        DefaultListCellRenderer renderer = (DefaultListCellRenderer)ingredientsList.getCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        ingredientsScrollPane = new JScrollPane(ingredientsList);
        ingredientsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainBorderLayout = new JPanel(new BorderLayout());
        mainBorderLayout.add(new JLabel(WhatToCook.SelectedPackage.get(107),SwingConstants.CENTER),BorderLayout.NORTH);
        mainBorderLayout.add(ingredientsScrollPane,BorderLayout.CENTER);

        clearListButton = new JButton(WhatToCook.SelectedPackage.get(109));
        clearListButton.addActionListener(e -> {
            ToBuyIngredientsList.clear();
            refresh();
        });

        exportListButton = new JButton(WhatToCook.SelectedPackage.get(110));
        exportListButton.addActionListener(e -> {
            JFileChooser chooseFile = new JFileChooser();
            int save = chooseFile.showSaveDialog(null);
            if (save == JFileChooser.APPROVE_OPTION) {
                String filename = chooseFile.getSelectedFile().getPath();
                PrintWriter writer;
                try {
                    writer = new PrintWriter(filename, "UTF-8");
                    writer.println(WhatToCook.SelectedPackage.get(107) + ":");
                    for (int i = 0; i < ingredientsListModel.size(); i++)
                        writer.println(ingredientsListModel.get(i));

                    writer.close();
                } catch (FileNotFoundException | UnsupportedEncodingException exception) {
                    System.err.println("Exporting ingredients list error.");
                }
            }
        });
        downGridLayout.add(clearListButton);
        downGridLayout.add(exportListButton);
        mainBorderLayout.add(downGridLayout,BorderLayout.SOUTH);
        add(mainBorderLayout);
    }
    public void refresh() {
        if(ingredientsListModel.getSize()>0) {
            ingredientsListModel.clear();
            mainBorderLayout.add(ingredientsScrollPane, BorderLayout.CENTER);
            for (Ingredient i : ToBuyIngredientsList.getSet()) {
                ingredientsListModel.addElement(i.getName());
            }
        }
        else {
            addNewIngredients = new JLabel("<html><center>" + WhatToCook.SelectedPackage.get(111)+ "</center></html>",SwingConstants.CENTER);
            addNewIngredients.setFont(new Font(MainWindow.font,Font.PLAIN,MainWindow.size));

            mainBorderLayout.add(addNewIngredients,BorderLayout.CENTER);
        }
        revalidate();
        repaint();
    }

    JButton clearListButton;
    JButton exportListButton;

    JLabel addNewIngredients;

    JPanel downGridLayout;
    JPanel mainBorderLayout;
    JList ingredientsList;
    final DefaultListModel<String> ingredientsListModel;

    JScrollPane ingredientsScrollPane;
}
