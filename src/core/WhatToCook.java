package core;

import gui.MainStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by WTC-Team on 22.05.2016.
 * Project WhatToCook
 */
public class WhatToCook extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(System.getProperty("user.home"));
        whatToCookStage = new MainStage();
        whatToCookStage.start(primaryStage);
    }
    public static void main(String args[]) {
        try {
            Scanner in = new Scanner(new File("data/cfg"));
            String line;
            String dividedLine[];
            line = in.nextLine();
            dividedLine = line.split("=");
            searchInEveryWord = dividedLine[1].equals("true");
            line = in.nextLine();
            dividedLine = line.split("=");
            caseSensitiveSearch = dividedLine[1].equals("true");
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("Concig file not found, program will run with default settings");
        }
        launch(args);
    }

    public static String endl = System.lineSeparator();

    public static MainStage whatToCookStage;

    public static void exportSettings() {
        try {
            PrintWriter out = new PrintWriter(new File("data/cfg"));
            out.println("searchInEveryWord=" + searchInEveryWord);
            out.println("caseSensitive=" + caseSensitiveSearch);
            out.close();
        } catch (FileNotFoundException e) {

        }

    }
    //ZMIENNE KONFIGURACYJNE
    public static boolean caseSensitiveSearch = true;
    public static boolean searchInEveryWord = false;
}
