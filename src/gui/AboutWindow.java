package gui;

import core.WhatToCook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mateusz on 21.04.2016.
 */
public class AboutWindow extends JDialog{
    public AboutWindow(MainWindow owner)
    {
        setSize(250,250);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle(WhatToCook.selectedLanguagePack.get(35));
        exit = new JButton(WhatToCook.selectedLanguagePack.get(36));
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        JLabel name = new JLabel("<html><h1>WhatToCook 2016</h1></html>");
        JLabel description = new JLabel("<html>" + WhatToCook.selectedLanguagePack.get(37) +":<br>Radosław Churski<br>Robert Górnicki" +
                "<br>Mateusz Kalinowski<br>Paweł Kurbiel<br>Oksana Stechkevych<br><br>" + WhatToCook.version +"</html>");
        JPanel mainBorderLayout = new JPanel(new BorderLayout());
        mainBorderLayout.add(exit,BorderLayout.SOUTH);
        mainBorderLayout.add(description,BorderLayout.CENTER);
        mainBorderLayout.add(name,BorderLayout.NORTH);
        add(mainBorderLayout);
    }
    private JButton exit;
}
