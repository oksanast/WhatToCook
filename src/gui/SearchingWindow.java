package gui;

import core.WhatToCook;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by WTC-Team on 04.05.2016.
 * Project WhatToCook
 */
class SearchingWindow extends JDialog {
    SearchingWindow(){
        setSize(200,100);
        setLocationRelativeTo(null);
        setModal(true);
        setTitle(WhatToCook.SelectedPackage.get(80));
        JPanel mainPanel = new JPanel(new GridLayout(2,1));
        wholeWords = new JCheckBox(WhatToCook.SelectedPackage.get(81));
        wholeWords.addActionListener(e -> exportSettings());
        wholeWords.setSelected(true);
        caseSensitiveCheckBox = new JCheckBox(WhatToCook.SelectedPackage.get(82));
        caseSensitiveCheckBox.addActionListener(e -> exportSettings());
        mainPanel.add(wholeWords);
        mainPanel.add(caseSensitiveCheckBox);

        add(mainPanel);
    }
    private void exportSettings()
    {
        try {
            PrintWriter writer = new PrintWriter(new File("src/searchSettingsConfig"));
            writer.println("SearchInEveryWord=" + wholeWords.isSelected());
            writer.println("CaseSensitive=" + caseSensitiveCheckBox.isSelected());
            writer.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

    }
    JCheckBox caseSensitiveCheckBox;
    JCheckBox wholeWords;
}
