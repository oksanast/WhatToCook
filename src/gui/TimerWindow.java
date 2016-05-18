package gui;

import core.WhatToCook;

import javax.swing.*;

/**
 * Created by Mateusz on 18.05.2016.
 * Project InferenceEngine
 */
public class TimerWindow extends JDialog {
    public TimerWindow(){
        setTitle(WhatToCook.SelectedPackage.get(113));
        setLocationRelativeTo(null);
        setSize(400,200);
        setModal(false);
    }
}
