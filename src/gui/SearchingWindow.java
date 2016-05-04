package gui;

import core.WhatToCook;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mateusz on 04.05.2016.
 */
public class SearchingWindow extends JDialog {
    public SearchingWindow(){
        setSize(200,100);
        setLocationRelativeTo(null);
        setModal(true);
        setTitle(WhatToCook.SelectedPackage.get(80));
        JPanel mainPanel = new JPanel(new GridLayout(2,1));
        wholeWords = new JCheckBox(WhatToCook.SelectedPackage.get(81));
        wholeWords.setSelected(true);
        caseSensitiveCheckBox = new JCheckBox(WhatToCook.SelectedPackage.get(82));
        mainPanel.add(wholeWords);
        mainPanel.add(caseSensitiveCheckBox);

        add(mainPanel);
    }
    public JCheckBox caseSensitiveCheckBox;
    public JCheckBox wholeWords;
}
