package gui;

import core.WhatToCook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by WTC-Team on 18.05.2016.
 * Project InferenceEngine
 */
public class TimerWindow extends JDialog {
    public TimerWindow(){
        setTitle(WhatToCook.SelectedPackage.get(113));
        setLocationRelativeTo(null);
        setSize(400,200);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(timer!=null) {
                    timer.interrupt();
                }
                super.windowClosing(e);
            }
        });

        value = 0;
        mainBorderLayout = new JPanel(new BorderLayout());
        plusMinusGridLayout = new JPanel(new GridLayout(2,1));
        startGridLayout = new JPanel(new GridLayout(1,2));

        plusButton = new JButton("+");
        plusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    value+=60;
                    refresh();
            }
        });


        minusButton = new JButton("-");
        minusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(value>0) {
                    value-=60;
                    if(value<0) {
                        value = 0;
                        timer.interrupt();
                    }
                    refresh();
                }
            }
        });

        startButton = new JButton(WhatToCook.SelectedPackage.get(114));
        startButton.addActionListener(e -> {
            timer = new Timer();
            timer.start();

        });

        resetButton = new JButton(WhatToCook.SelectedPackage.get(115));
        resetButton.addActionListener(e -> timer.interrupt());

        mainScreen = new JLabel("<html><center>" + "0:00" + "</center></html>",SwingConstants.CENTER);
        mainScreen.setFont(new Font(MainWindow.font,Font.PLAIN,96));
        plusMinusGridLayout.add(plusButton);
        plusMinusGridLayout.add(minusButton);

        startGridLayout.add(startButton);
        startGridLayout.add(resetButton);
        mainBorderLayout.add(mainScreen,BorderLayout.CENTER);
        mainBorderLayout.add(plusMinusGridLayout,BorderLayout.EAST);
        mainBorderLayout.add(startGridLayout,BorderLayout.SOUTH);
        add(mainBorderLayout);
    }
    public void refresh() {
        String toShow = value/60 +":" + value%60;
        if(toShow.length()<4) {
            mainScreen.setText("<html><center>" + value / 60 + ":" + value % 60 +"0"+ "</center></html>");
        }
        else
            mainScreen.setText("<html><center>" + value / 60 + ":" + value % 60 + "</center></html>");
        repaint();
    }

    class Timer extends Thread {
        public void run() {
            while(value>0) {
                try {
                    sleep(1000);
                    value -= 1;
                    refresh();
                } catch (InterruptedException e) {
                    value=0;
                    mainScreen.setText("<html><center>"+"0:00"+"</center></html>");
                    break;
                }
            }
        }
    }
    private JLabel mainScreen;

    private JButton plusButton;
    private JButton minusButton;
    private JButton startButton;
    private JButton resetButton;

    private JPanel startGridLayout;
    private JPanel plusMinusGridLayout;
    private JPanel mainBorderLayout;


    public int value;

    public Timer timer;
}
