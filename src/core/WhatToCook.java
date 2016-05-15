package core;

import auxiliary.LanguagePackage;
import auxiliary.LanguagePackageList;
import gui.MainWindow;
import sun.applet.Main;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by WTC-Team on 09.03.2016.
 * Project WhatToCook
 */
/*
    KLASA PODSTAWOWA, URUCHOMIENIOWA, SŁUŻY DO UTWORZENIA GŁÓWNEGO OKNA PROGRAMU I PACZEK JĘZYKOWYCH
    WCZYTUJE PLIK KONFIGURACYJNY "mainSettingsConfig"
 */
public class WhatToCook {

    public static void main(String[] args) {
        new WhatToCook();
    }

    public WhatToCook() {
        Scanner languageListFile;
        Scanner languagePathsFile;
        Scanner languageDataFile;
        LanguagesPackages = new LanguagePackageList();
        try {
            languageListFile = new Scanner(new File("data/Languages/LanguagesList"));
            languagePathsFile = new Scanner(new File("data/Languages/LanguagesPaths"));
            ArrayList<String> languagesNames = new ArrayList<>();
            ArrayList<String[]> languagesPaths = new ArrayList<>();
            while (languageListFile.hasNextLine()) {
                languagesNames.add(languageListFile.nextLine());
                languagesPaths.add(languagePathsFile.nextLine().split(" "));
            }
            for (int i = 0; i < languagesNames.size(); i++) {
                ArrayList<String> languageData = new ArrayList<>();
                languageDataFile = new Scanner(new File("data/Languages/Recourses/" + languagesNames.get(i)));
                int counter = 0;
                final File[] listOfFiles = new File(languagesPaths.get(i)[1]).listFiles();
                if (listOfFiles == null)
                    continue;
                try {
                    Scanner in = new Scanner(new File(languagesPaths.get(i)[0]));
                } catch (FileNotFoundException e) {
                    continue;
                }
                while (languageDataFile.hasNextLine()) {
                    languageData.add(languageDataFile.nextLine());
                    counter++;
                }
                if (counter == phrasesCount) {
                    LanguagesPackages.add(new LanguagePackage(languagesNames.get(i), i, languagesPaths.get(i)[0], languagesPaths.get(i)[1],languagesPaths.get(i)[2], languageData));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Language files not found");
        }
        if (LanguagesPackages.size() == 0) {
            JOptionPane.showMessageDialog(null,"It was impossbile to load any language.","Languages loading error - Critical", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        SelectedPackage = new LanguagePackage();
        Scanner in;
        cards = new boolean[3];
        try {
            in = new Scanner(new File("src/mainSettingsConfig"));
            String line = in.nextLine();
            String splittedLine[] = line.split("=");
            SelectedPackage = LanguagesPackages.get(splittedLine[1]);
            line = in.nextLine();
            splittedLine = line.split("=");
            MainWindow.getToNewCard = splittedLine[1].equals("true");
            line = in.nextLine();
            splittedLine = line.split("=");
            MainWindow.autoLoadIngredients = splittedLine[1].equals("true");
            line = in.nextLine();
            splittedLine = line.split("=");
            cards[0] = splittedLine[1].equals("true");
            line = in.nextLine();
            splittedLine = line.split("=");
            cards[1] = splittedLine[1].equals("true");
            line = in.nextLine();
            splittedLine = line.split("=");
            cards[2] = splittedLine[1].equals("true");
            line = in.nextLine();
            splittedLine = line.split("=");
            MainWindow.font = splittedLine[1];
            line = in.nextLine();
            splittedLine = line.split("=");
            MainWindow.size = Integer.parseInt(splittedLine[1]);
            line = in.nextLine();
            splittedLine = line.split("[=,/]");
            int r = Integer.parseInt(splittedLine[2]);
            int g = Integer.parseInt(splittedLine[4]);
            int b = Integer.parseInt(splittedLine[6].substring(0,splittedLine[6].length()-1));
            MainWindow.backgroundColor = new Color(r,g,b);
            line = in.nextLine();
            splittedLine = line.split("=");
            MainWindow.theme = splittedLine[1];

        } catch (FileNotFoundException | NoSuchElementException e) {
            System.err.println("Error during loading config files file, program will load with default settings");
            MainWindow.font = "Comic Sans MS";
            MainWindow.size = 12;
            MainWindow.theme = "Platform";
            MainWindow.backgroundColor = new Color(255,255,255);
        }
        if (SelectedPackage == null)
            SelectedPackage = LanguagesPackages.get(0);
        frame = new MainWindow(cards);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
    //TODO ZMIANA PRZY KAZDYM DODANIU SLOWA
    private static final int phrasesCount = 105;

    private static boolean cards[];

    public static boolean[] getCards() {return cards;}

    public static MainWindow frame;
    public static String version = "1.9";

    public static LanguagePackage SelectedPackage;

    public static final String endl = System.lineSeparator();

    public static LanguagePackageList LanguagesPackages;

}
