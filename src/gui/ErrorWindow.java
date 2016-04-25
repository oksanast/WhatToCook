package gui;

import core.WhatToCook;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Mateusz on 22.04.2016.
 * Project WhatToCook
 */
public class ErrorWindow extends JDialog {
    public ErrorWindow(MainWindow owner)
    {
        setSize(300,150);
        setModal(true);
        setTitle(WhatToCook.selectedLanguagePack.get(39));
        setLocationRelativeTo(null);
        JPanel mainBorderLayout = new JPanel(new BorderLayout());
        ErrorMessage = "";
        JButton exit = new JButton(WhatToCook.selectedLanguagePack.get(36));
        exit.addActionListener(e -> setVisible(false));
        ErrorMessageLabel = new JLabel();
        mainBorderLayout.add(ErrorMessageLabel,BorderLayout.NORTH);
        mainBorderLayout.add(exit,BorderLayout.SOUTH);

        add(mainBorderLayout);
    }
    public void refresh(ArrayList<String> errorsList)
    {
        ErrorMessage = "<html><h4>" + WhatToCook.selectedLanguagePack.get(38) + "</h4>";
        for(String toPrint : errorsList)
        {
            ErrorMessage+="<br>" + toPrint;
        }
        ErrorMessage+="</hmlt>";
        ErrorMessageLabel.setText(ErrorMessage);

        repaint();
    }
    private JLabel ErrorMessageLabel;
    private String ErrorMessage;
}
