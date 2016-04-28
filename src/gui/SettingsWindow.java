package gui;

import core.WhatToCook;

import javax.annotation.processing.SupportedSourceVersion;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by Mateusz on 23.03.2016.
 * Project WhatToCook
 */
public class SettingsWindow extends JDialog {
    public SettingsWindow(MainWindow owner) {
        setSize(640, 130);
        setModal(true);
        setTitle(WhatToCook.SelectedPackage.get(6));
        setLocationRelativeTo(null);
        mainTable = new JTabbedPane();
        languageComboBox = new JComboBox<>();
        languageComboBox.addItem("Polski");
        languageComboBox.addItem("English");
        languageComboBox.setToolTipText(WhatToCook.SelectedPackage.get(67));
        mainGridLayout = new JPanel(new GridLayout(2 , 2));
        secondGridLayout = new JPanel(new GridLayout( 2 , 2));

        toNewCardCheckbox = new JCheckBox();
        if (MainWindow.getToNewCard) {
            toNewCardCheckbox.setSelected(true);
        }
        toNewCardCheckbox.addActionListener(e -> {
            MainWindow.getToNewCard = toNewCardCheckbox.isSelected();
            exportSettings();
        });
        mainGridLayout.add(new JLabel(WhatToCook.SelectedPackage.get(24), SwingConstants.CENTER));
        mainGridLayout.add(toNewCardCheckbox);
        mainGridLayout.add(new JLabel(WhatToCook.SelectedPackage.get(25), SwingConstants.CENTER));
        mainGridLayout.add(languageComboBox);

        secondGridLayout.add(new JLabel(WhatToCook.SelectedPackage.get(73)));
        autoImportIngredientsCheckbox = new JCheckBox();
        autoImportIngredientsCheckbox.addActionListener(e -> {
            MainWindow.autoLoadIngredients = autoImportIngredientsCheckbox.isSelected();
            exportSettings();
        });
        changePathButton = new JButton(WhatToCook.SelectedPackage.get(75));
        changePathButton.addActionListener(e -> {
            JFileChooser chooseFile = new JFileChooser();
            int save = chooseFile.showOpenDialog(null);
            if (save == JFileChooser.APPROVE_OPTION) {
                MainWindow.Path = chooseFile.getSelectedFile().getPath();
                exportSettings();
                pathLabel.setText(WhatToCook.SelectedPackage.get(74)+" "+MainWindow.Path);
            }

        });
        secondGridLayout.add(autoImportIngredientsCheckbox);
        pathLabel = new JLabel(WhatToCook.SelectedPackage.get(74)+" "+MainWindow.Path,SwingConstants.CENTER);
        pathLabel.setToolTipText(MainWindow.Path);
        secondGridLayout.add(pathLabel);
        secondGridLayout.add(changePathButton);
        autoImportIngredientsCheckbox.setSelected(MainWindow.autoLoadIngredients);

        mainTable.addTab(WhatToCook.SelectedPackage.get(26), mainGridLayout);
        mainTable.addTab(WhatToCook.SelectedPackage.get(72), secondGridLayout);

        if (WhatToCook.SelectedPackage.equals(WhatToCook.PolishPackage)) {
            languageComboBox.setSelectedIndex(0);
        }
        if (WhatToCook.SelectedPackage.equals(WhatToCook.EnglishPackage)) {
            languageComboBox.setSelectedIndex(1);
        }

        languageComboBox.addActionListener(e -> {
            if (languageComboBox.getSelectedItem() == "Polski") {
                int selection;
                selection = JOptionPane.showConfirmDialog(null, WhatToCook.SelectedPackage.get(42), WhatToCook.SelectedPackage.get(43), JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (selection == JOptionPane.OK_OPTION) {
                    // WhatToCook.SelectedPackage = WhatToCook.polishLanguagePack;
                    WhatToCook.SelectedPackage = WhatToCook.PolishPackage;
                    WhatToCook.frame.dispose();
                    WhatToCook.frame = new MainWindow();
                    WhatToCook.frame.setVisible(true);
                    WhatToCook.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    setTitle(WhatToCook.SelectedPackage.get(6));
                    repaint();
                    setVisible(false);
                    toFront();
                } else
                    languageComboBox.setSelectedIndex(1);
            }
            if (languageComboBox.getSelectedItem() == "English") {
                int selection;
                selection = JOptionPane.showConfirmDialog(null, WhatToCook.SelectedPackage.get(42), WhatToCook.SelectedPackage.get(43), JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (selection == JOptionPane.OK_OPTION) {
                    // WhatToCook.SelectedPackage = WhatToCook.englishLanguagePack;
                    WhatToCook.SelectedPackage = WhatToCook.EnglishPackage;
                    WhatToCook.frame.dispose();
                    WhatToCook.frame = new MainWindow();
                    WhatToCook.frame.setVisible(true);
                    WhatToCook.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    setTitle(WhatToCook.SelectedPackage.get(6));
                    repaint();
                    setVisible(false);
                    toFront();
                } else
                    languageComboBox.setSelectedIndex(0);

            }
            exportSettings();
        });
        add(mainTable);

    }

    public String getLanguage() {
        return (String) languageComboBox.getSelectedItem();
    }

    public boolean getToNewCardCheckbox() {
        return toNewCardCheckbox.isSelected();
    }

    private void exportSettings() {
        try {
            PrintWriter writer = new PrintWriter(new File("src/cfg"));
            if (languageComboBox.getSelectedItem() == "Polski") {
                writer.println("polish");
            }
            if (languageComboBox.getSelectedItem() == "English") {
                writer.println("english");
            }
            writer.println(toNewCardCheckbox.isSelected());
            writer.println(autoImportIngredientsCheckbox.isSelected());
            writer.print(MainWindow.Path);
            writer.close();

        } catch (FileNotFoundException e) {


        }

    }

    JComboBox<String> languageComboBox;
    JTabbedPane mainTable;
    JPanel mainGridLayout;
    JPanel secondGridLayout;
    JCheckBox toNewCardCheckbox;
    JCheckBox autoImportIngredientsCheckbox;
    JLabel pathLabel;
    JButton changePathButton;


}
