package gui;

import core.WhatToCook;
import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by WTC-Team on 23.03.2016.
 * Project WhatToCook
 */
/*
    OKNO Z USTAWIENIAMI
    UŻYTKOWNIK MOŻE WYBRAĆ
        -AUTOMATYCZNE PRZECHODZENIE DO NOWO OTWARTEJ KARTY
        -JĘZYK INTERFACE'U PROGRAMU I ZARAZEM PACZKĘ JĘZYKOWĄ
        -AUTOMATYCZNE WCZYTYWANIE LISTY POSIADANYCH SKŁADNIKÓW
        -ŚCIEŻKĘ DO PLIKU ZE SKŁADNIKAMI
 */
class SettingsWindow extends JDialog {
     SettingsWindow() {
        setSize(640, 150);
        setModal(true);
         setResizable(false);
        setTitle(WhatToCook.SelectedPackage.get(6));
        setLocationRelativeTo(null);
        mainTable = new JTabbedPane();
        languageComboBox = new JComboBox<>();
        for(int i = 0; i < WhatToCook.LanguagesPackages.getLanguageNameSize();i++)
        {
            languageComboBox.addItem(WhatToCook.LanguagesPackages.getLanguageName(i));
        }
        languageComboBox.setToolTipText(WhatToCook.SelectedPackage.get(67));
        mainGridLayout = new JPanel(new GridLayout(3 , 2));

        toNewCardCheckbox = new JCheckBox();
        if (MainWindow.getToNewCard) {
            toNewCardCheckbox.setSelected(true);
        }
        toNewCardCheckbox.addActionListener(e -> {
            MainWindow.getToNewCard = toNewCardCheckbox.isSelected();
            exportSettings();
        });

        autoImportIngredientsCheckbox = new JCheckBox();
        autoImportIngredientsCheckbox.addActionListener(e -> {
            MainWindow.autoLoadIngredients = autoImportIngredientsCheckbox.isSelected();
            if(!autoImportIngredientsCheckbox.isSelected())
            {
                PrintWriter writer;
                try {
                    writer = new PrintWriter(new File(WhatToCook.SelectedPackage.GetOwnedIngredientsPath()));
                    writer.close();
                } catch (FileNotFoundException exception)
                {
                    System.err.println("File 'ownedIngredients' not found, it is internal jar error, download program once again");
                }

            }
            if(autoImportIngredientsCheckbox.isSelected())
            {
                WhatToCook.frame.exportOwnedIngredients();
            }
            exportSettings();

        });
        autoImportIngredientsCheckbox.setSelected(MainWindow.autoLoadIngredients);
        JLabel newCardLabel = new JLabel(WhatToCook.SelectedPackage.get(24), SwingConstants.CENTER);
         newCardLabel.setFont(new Font(MainWindow.font,Font.PLAIN,MainWindow.size));
        mainGridLayout.add(newCardLabel);
        mainGridLayout.add(toNewCardCheckbox);
         JLabel saveStateLabel = new JLabel(WhatToCook.SelectedPackage.get(73),SwingConstants.CENTER);
         saveStateLabel.setFont(new Font(MainWindow.font,Font.PLAIN,MainWindow.size));
        mainGridLayout.add(saveStateLabel);
        mainGridLayout.add(autoImportIngredientsCheckbox);
         JLabel languageLabel = new JLabel(WhatToCook.SelectedPackage.get(25), SwingConstants.CENTER);
         languageLabel.setFont(new Font(MainWindow.font,Font.PLAIN,MainWindow.size));
        mainGridLayout.add(languageLabel);
        mainGridLayout.add(languageComboBox);

         lookGridLayout = new JPanel(new GridLayout(3,1));
         JButton colorChooseButton = new JButton(WhatToCook.SelectedPackage.get(95));
         colorChooseButton.addActionListener(e ->{
             MainWindow.backgroundColor = JColorChooser.showDialog(null,WhatToCook.SelectedPackage.get(95),MainWindow.backgroundColor);
             WhatToCook.frame.dispose();
             WhatToCook.frame = new MainWindow(WhatToCook.getCards());
             WhatToCook.frame.setVisible(true);
             WhatToCook.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
             setTitle(WhatToCook.SelectedPackage.get(6));
             repaint();
             setVisible(false);
             toFront();
             exportSettings();
         });
         lookGridLayout.add(colorChooseButton);

         fontGridLayout = new JPanel(new GridLayout(1,2));
         sizeGridLayout = new JPanel(new GridLayout(1,2));
         JLabel fontSizeLabel = new JLabel(WhatToCook.SelectedPackage.get(100),SwingConstants.CENTER);
         fontSizeLabel.setFont(new Font(MainWindow.font,Font.PLAIN,MainWindow.size));
         fontGridLayout.add(fontSizeLabel);
         JLabel sizeGridLabel = new JLabel(WhatToCook.SelectedPackage.get(99),SwingConstants.CENTER);
         sizeGridLabel.setFont(new Font(MainWindow.font,Font.PLAIN,MainWindow.size));
         sizeGridLayout.add(sizeGridLabel);

         fontComboBox = new JComboBox<>();
         fontComboBox.addItem("Arial");
         fontComboBox.addItem("Comic Sans MS");
         for(int i = 0; i < fontComboBox.getItemCount();i++){
             if(MainWindow.font.equals(fontComboBox.getItemAt(i)))
                 fontComboBox.setSelectedIndex(i);
         }
         fontComboBox.addActionListener(e -> {
             if(!MainWindow.font.equals(fontComboBox.getSelectedItem())) {
                 MainWindow.font = fontComboBox.getSelectedItem().toString();
                 WhatToCook.frame.dispose();
                 WhatToCook.frame = new MainWindow(WhatToCook.getCards());
                 WhatToCook.frame.setVisible(true);
                 WhatToCook.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                 setTitle(WhatToCook.SelectedPackage.get(6));
                 repaint();
                 setVisible(false);
                 toFront();
                 exportSettings();
             }
         });
         sizeComboBox = new JComboBox<>();
         sizeComboBox.addItem(WhatToCook.SelectedPackage.get(96));
         sizeComboBox.addItem(WhatToCook.SelectedPackage.get(97));
         sizeComboBox.addItem(WhatToCook.SelectedPackage.get(98));

         if(MainWindow.size==8)
             sizeComboBox.setSelectedIndex(0);
         if(MainWindow.size==12)
             sizeComboBox.setSelectedIndex(1);
         if(MainWindow.size==16)
             sizeComboBox.setSelectedIndex(2);

         sizeComboBox.addActionListener(e -> {
             if(sizeComboBox.getSelectedIndex()==0)
                 MainWindow.size = 8;
             else if(sizeComboBox.getSelectedIndex()==1)
                 MainWindow.size = 12;
             else if(sizeComboBox.getSelectedIndex()==2)
                 MainWindow.size = 16;
             WhatToCook.frame.dispose();
             WhatToCook.frame = new MainWindow(WhatToCook.getCards());
             WhatToCook.frame.setVisible(true);
             WhatToCook.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
             setTitle(WhatToCook.SelectedPackage.get(6));
             repaint();
             setVisible(false);
             toFront();
             exportSettings();
         });

         fontGridLayout.add(fontComboBox);
         sizeGridLayout.add(sizeComboBox);

         lookGridLayout.add(fontGridLayout);
         lookGridLayout.add(sizeGridLayout);

        mainTable.addTab(WhatToCook.SelectedPackage.get(26), mainGridLayout);
         mainTable.addTab(WhatToCook.SelectedPackage.get(94),lookGridLayout);
        for(int i = 0; i < WhatToCook.LanguagesPackages.getLanguageNameSize();i++)
        {
            if(WhatToCook.SelectedPackage.getName().equals(WhatToCook.LanguagesPackages.getLanguageName(i)))
                languageComboBox.setSelectedIndex(i);
        }
        languageComboBox.addActionListener(e -> {
            if(languageComboBox.getSelectedIndex()!=WhatToCook.SelectedPackage.GetSelectedLanguage())
            {
                int selection;
                selection = JOptionPane.showConfirmDialog(null, WhatToCook.SelectedPackage.get(42), WhatToCook.SelectedPackage.get(43), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(selection == JOptionPane.OK_OPTION) {
                    WhatToCook.SelectedPackage = WhatToCook.LanguagesPackages.get(languageComboBox.getSelectedIndex());
                    WhatToCook.frame.dispose();
                    WhatToCook.frame = new MainWindow(WhatToCook.getCards());
                    WhatToCook.frame.setVisible(true);
                    WhatToCook.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    setTitle(WhatToCook.SelectedPackage.get(6));
                    repaint();
                    setVisible(false);
                    toFront();
                }
                else
                    languageComboBox.setSelectedIndex(WhatToCook.SelectedPackage.GetSelectedLanguage());

            }
            exportSettings();
        });
         add(mainTable);
         pack();

    }

    public String getLanguage() {
        return (String) languageComboBox.getSelectedItem();
    }

    public boolean getToNewCardCheckbox() {
        return toNewCardCheckbox.isSelected();
    }

    public void exportSettings() {
        try {
            PrintWriter writer = new PrintWriter(new File("src/mainSettingsConfig"));
            writer.println("Language=" + languageComboBox.getSelectedItem().toString());
            writer.println("AutoNewCard=" + toNewCardCheckbox.isSelected());
            writer.println("SaveState=" + autoImportIngredientsCheckbox.isSelected());
            writer.println("SearchCard=" + WhatToCook.frame.getShowSearchMenuStatus());
            writer.println("RecipesCard=" + WhatToCook.frame.getShowRecipesMenuStatus());
            writer.println("IngredientsCard=" + WhatToCook.frame.getShowIngredientsMenu());
            writer.println("Font=" + MainWindow.font);
            writer.println("Size=" + MainWindow.size);
            writer.println("Color="+MainWindow.backgroundColor.toString());
            writer.close();

        } catch (FileNotFoundException e) {
            System.err.println("It was impossible to export settings, because is was impossible to find " +
                    "'mainSettingsConfig'");
        }

    }
    JPanel lookGridLayout;

    JComboBox<String> fontComboBox;
    JComboBox<String> sizeComboBox;

    private JPanel fontGridLayout;
    private JPanel sizeGridLayout;

    JComboBox<String> languageComboBox;
    JTabbedPane mainTable;
    JPanel mainGridLayout;
    JCheckBox toNewCardCheckbox;
    JCheckBox autoImportIngredientsCheckbox;


}
