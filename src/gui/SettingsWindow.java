package gui;

import core.WhatToCook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Mateusz on 23.03.2016.
 */
public class SettingsWindow extends JDialog
{
    public SettingsWindow(MainWindow owner)
    {
        setSize(640,130);
        setTitle(WhatToCook.selectedLanguagePack.get(6));
        setLocationRelativeTo(null);
        mainTable = new JTabbedPane();
        languageComboBox = new JComboBox<String>();
        languageComboBox.addItem("Polski");
        languageComboBox.addItem("English");
        mainGridLayout = new JPanel(new GridLayout(2,2));
        toNewCardCheckbox = new JCheckBox();
        if(MainWindow.getToNewCard)
        {
            toNewCardCheckbox.setSelected(true);
        }
        toNewCardCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(toNewCardCheckbox.isSelected()) MainWindow.getToNewCard = true;
                else MainWindow.getToNewCard = false;
                exportSettings();
            }
        });
        mainGridLayout.add(new JLabel(WhatToCook.selectedLanguagePack.get(24),SwingConstants.CENTER));
        mainGridLayout.add(toNewCardCheckbox);
        mainGridLayout.add(new JLabel(WhatToCook.selectedLanguagePack.get(25),SwingConstants.CENTER));
        mainGridLayout.add(languageComboBox);
        mainTable.addTab(WhatToCook.selectedLanguagePack.get(26),mainGridLayout);

        if(WhatToCook.selectedLanguagePack == WhatToCook.polishLanguagePack)
        {
            languageComboBox.setSelectedIndex(0);
        }
        if(WhatToCook.selectedLanguagePack == WhatToCook.englishLanguagePack)
        {
            languageComboBox.setSelectedIndex(1);
        }

        languageComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(languageComboBox.getSelectedItem() == "Polski")
                {
                    int selection;
                    selection = JOptionPane.showConfirmDialog(null, WhatToCook.selectedLanguagePack.get(42), WhatToCook.selectedLanguagePack.get(43),JOptionPane.OK_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(selection == JOptionPane.OK_OPTION)
                    {
                        WhatToCook.selectedLanguagePack = WhatToCook.polishLanguagePack;
                        WhatToCook.SelectedPackage = WhatToCook.PolishPackage;
                        WhatToCook.frame.dispose();
                        WhatToCook.frame = new MainWindow();
                        WhatToCook.frame.setVisible(true);
                        WhatToCook.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        setTitle(WhatToCook.selectedLanguagePack.get(6));
                        repaint();
                        setVisible(false);
                        toFront();
                    }
                    else
                        languageComboBox.setSelectedIndex(1);
                }
                if(languageComboBox.getSelectedItem() == "English")
                {
                    int selection;
                    selection = JOptionPane.showConfirmDialog(null, WhatToCook.selectedLanguagePack.get(42), WhatToCook.selectedLanguagePack.get(43),JOptionPane.OK_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(selection == JOptionPane.OK_OPTION)
                    {
                        WhatToCook.selectedLanguagePack = WhatToCook.englishLanguagePack;
                        WhatToCook.SelectedPackage = WhatToCook.EnglishPackage;
                        WhatToCook.frame.dispose();
                        WhatToCook.frame = new MainWindow();
                        WhatToCook.frame.setVisible(true);
                        WhatToCook.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        setTitle(WhatToCook.selectedLanguagePack.get(6));
                        repaint();
                        setVisible(false);
                        toFront();
                    }
                    else
                        languageComboBox.setSelectedIndex(0);

                }
                exportSettings();
            }
        });
        add(mainTable);

    }

    public String getLanguage()
    {
        return (String) languageComboBox.getSelectedItem();
    }
    public boolean getToNewCardCheckbox()
    {
        if (toNewCardCheckbox.isSelected()) return true;
        else return false;
    }
    private void exportSettings()
    {
        try {
            PrintWriter writer = new PrintWriter(new File("src/cfg"));
            if(languageComboBox.getSelectedItem() == "Polski")
            {
                writer.println("polish");
            }
            if(languageComboBox.getSelectedItem() == "English")
            {
                writer.println("english");
            }
            writer.println(toNewCardCheckbox.isSelected());
            writer.close();

        } catch (FileNotFoundException e) {

        }

    }
    JComboBox<String> languageComboBox;
    JTabbedPane mainTable;
    JPanel mainGridLayout;
    JCheckBox toNewCardCheckbox;
}
