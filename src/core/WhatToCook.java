package core;

import auxiliary.LanguagePackage;
import auxiliary.LanguagePackageList;
import gui.MainWindow;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
            languagesNames = new ArrayList<>();
            ArrayList<String[]> languagesPaths = new ArrayList<>();
            while(languageListFile.hasNextLine())
            {
                languagesNames.add(languageListFile.nextLine());
                languagesPaths.add(languagePathsFile.nextLine().split(" "));
            }
            for(int i = 0; i  < languagesNames.size(); i++)
            {
                ArrayList<String> languageData = new ArrayList<>();
                languageDataFile = new Scanner(new File("data/Languages/Recourses/" + languagesNames.get(i)));
                while(languageDataFile.hasNextLine())
                {
                    languageData.add(languageDataFile.nextLine());
                }
                LanguagesPackages.add(new LanguagePackage(languagesNames.get(i),i,languagesPaths.get(i)[0],languagesPaths.get(i)[1],languageData));
            }
            System.out.println(LanguagesPackages.get(1).GetRecipesPath());
        } catch (FileNotFoundException e)
        {
            //TODO WINDOW WITH INFORMATON ABOUT ERROR DURING LOADING LANGUAGE PACKAGES (ONLY CONFIRM)
            System.out.println("Language files not found");
        }

        SelectedPackage = new LanguagePackage();
        Scanner in;
        try
        {
            in = new Scanner(new File("src/mainSettingsConfig"));
            String line = in.next();
            String splittedLine[] = line.split("=");
            SelectedPackage = LanguagesPackages.get(splittedLine[1]);
            line = in.next();
            splittedLine = line.split("=");
            if(splittedLine[1].equals("true"))
            MainWindow.getToNewCard = true;
            else
            MainWindow.getToNewCard = false;

            line = in.next();
            splittedLine = line.split("=");
            if(splittedLine[1].equals("true"))
                MainWindow.autoLoadIngredients = true;
            else
                MainWindow.autoLoadIngredients = false;
        }
        catch (FileNotFoundException | NoSuchElementException e)
        {
            System.err.println("Error during loading config files file, program will load with default settings");
        }

        frame = new MainWindow();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public static ArrayList<String> languagesNames;

    public static MainWindow frame;
    public static String version = "1.9";

    public static LanguagePackage SelectedPackage;

    public static final String endl = System.lineSeparator();

    public static LanguagePackageList LanguagesPackages;

}