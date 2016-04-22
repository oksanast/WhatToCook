package gui;

import core.WhatToCook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Mateusz on 22.04.2016.
 * Project WhatToCook
 */
public class ErrorWindow extends JFrame {
    public ErrorWindow(MainWindow owner)
    {
        setSize(300,150);
        setTitle(WhatToCook.selectedLanguagePack.get(39));
        setLocationRelativeTo(null);
        mainBorderLayout = new JPanel(new BorderLayout());
        ErrorMessage = "";
        exit = new JButton(WhatToCook.selectedLanguagePack.get(36));
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        ErrorMessageLabel = new JLabel();
        mainBorderLayout.add(ErrorMessageLabel,BorderLayout.NORTH);
        mainBorderLayout.add(exit,BorderLayout.SOUTH);

        add(mainBorderLayout);
    }
    public void refresh(ArrayList<String> errorsList)
    {
        ErrorMessage = "<html><h4>" + WhatToCook.selectedLanguagePack.get(38) + "</h4>";
        for(int i = 0; i < errorsList.size();i++)
        {
            ErrorMessage+="<br>" + errorsList.get(i);
        }
        ErrorMessage+="</hmlt>";
        ErrorMessageLabel.setText(ErrorMessage);

        repaint();
    }
    private JLabel ErrorMessageLabel;
    private JButton exit;
    private JPanel mainBorderLayout;
    private String ErrorMessage;
}
