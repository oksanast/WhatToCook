package core;

import auxiliary.Dictionary;
import auxiliary.LanguagePackage;
import gui.MainStage;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by WTC-Team on 22.05.2016.
 * Project WhatToCook
 */
public class WhatToCook extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        whatToCookStage = new MainStage();
        whatToCookStage.start(primaryStage);
    }
    public static void main(String args[]) {
        path = System.getProperty("user.home") + "/Documents/WhatToCook";
        File resources = new File(path);
        try {
            if(!resources.exists()) {
                resources.mkdir();
            }
            if(!new File(path+"/data").exists()) {
                new File(path+"/data").mkdir();
            }
            if(!new File(path+"/data/Ingredients").exists()) {
                new File(path+"/data/Ingredients").mkdir();
            }
            if(!new File(path+"/data/Ingredients/ingredients").exists()) {
                new File(path+"/data/Ingredients/ingredients").createNewFile();
            }
            if(!new File(path+"/data/ownedIngredients").exists()) {
                new File(path+"/data/ownedIngredients").mkdir();
            }
            if(!new File(path+"/data/ownedIngredients/ownedIngredients").exists()) {
                new File(path+"/data/ownedIngredients/ownedIngredients").createNewFile();
            }
            if(!new File(path+"/data/recipes").exists()) {
                new File(path+"/data/recipes").mkdir();
            }
            if(!new File(path+"/data/recipes/linked").exists()) {
                new File(path+"/data/recipes/linked").mkdir();
            }
            if(!new File(path+"/data/recipes/linked/linkedRecipes").exists()) {
                new File(path+"/data/recipes/linked/linkedRecipes").createNewFile();
            }
            if(!new File(path+"/data/toBuyList").exists()) {
                new File(path+"/data/toBuyList").mkdir();
            }
            if(!new File(path+"/data/toBuyList/shoppingList").exists()) {
                new File(path+"/data/toBuyList/shoppingList").createNewFile();
            }
            if(!new File(path+"/data/cfg").exists()) {
                new File(path+"/data/cfg").createNewFile();
                PrintWriter createCfg = new PrintWriter(new File(path+"/data/cfg"));
                createCfg.println("searchInEveryWord=true");
                createCfg.println("caseSensitive=false");
                createCfg.println("language=Polski");
                createCfg.println("autoNewCard=true");
                createCfg.println("interfaceType=" + WhatToCook.interfaceType);
                createCfg.close();
            }


        } catch (SecurityException | java.io.IOException e) {
            Alert fatalError = new Alert(Alert.AlertType.ERROR);
            fatalError.setTitle("Błąd uruchamiania programu");
            fatalError.setHeaderText("Program nie może zostać uruchomiony z braku praw do zapisu w folderze domowym użytkownika");
            fatalError.setContentText("Przywróć sobie uprawnienia do zapisu w swoim folderze domowym, o ścieżce: " + path);
            System.exit(0);
        }
        try {
            Scanner in = new Scanner(new File(WhatToCook.path + "/data/cfg"));
            try {
                String line;
                String dividedLine[];
                line = in.nextLine();
                dividedLine = line.split("=");
                searchInEveryWord = dividedLine[1].equals("true");
                line = in.nextLine();
                dividedLine = line.split("=");
                caseSensitiveSearch = dividedLine[1].equals("true");
                line = in.nextLine();
                dividedLine = line.split("=");
                LanguagePackage.language = dividedLine[1];
                line = in.nextLine();
                dividedLine = line.split("=");
                autoNewCard = dividedLine[1].equals("true");
                line = in.nextLine();
                dividedLine = line.split("=");
                interfaceType = Integer.parseInt(dividedLine[1]);
                in.close();
            } catch (NoSuchElementException e) {
                PrintWriter out = new PrintWriter(new File(WhatToCook.path + "/data/cfg"));
                out.println("searchInEveryWord=" + searchInEveryWord);
                out.println("caseSensitive=" + caseSensitiveSearch);
                out.println("language=" + LanguagePackage.language);
                out.println("autoNewCard=" + autoNewCard);
                out.println("interfaceType=" + "Adaptacyjnie");
                out.close();

            }

        } catch (FileNotFoundException e) {
            System.out.println("Concig file not found, program will run with default settings");
        }
        Dictionary.initialize();
        launch(args);
    }

    public static String endl = System.lineSeparator();

    public static MainStage whatToCookStage;

    public static void exportSettings() {
        try {
            PrintWriter out = new PrintWriter(new File(WhatToCook.path + "/data/cfg"));
            out.println("searchInEveryWord=" + searchInEveryWord);
            out.println("caseSensitive=" + caseSensitiveSearch);
            out.println("language=" + LanguagePackage.language);
            out.println("autoNewCard=" + autoNewCard);
            out.println("interfaceType=" + WhatToCook.interfaceType);
            out.close();
        } catch (FileNotFoundException ignored) {

        }

    }
    public static void exportSettings(String nextLanguage) {
        try {
            PrintWriter out = new PrintWriter(new File(WhatToCook.path + "/data/cfg"));
            out.println("searchInEveryWord=" + searchInEveryWord);
            out.println("caseSensitive=" + caseSensitiveSearch);
            out.println("language=" + nextLanguage);
            out.println("autoNewCard=" + autoNewCard);
            out.println("interfaceType=" + WhatToCook.interfaceType);
            out.close();
        } catch (FileNotFoundException ignored) {

        }

    }

    //ZMIENNE KONFIGURACYJNE
    public static boolean caseSensitiveSearch = true;
    public static boolean searchInEveryWord = false;
    public static boolean autoNewCard = true;

    public static int interfaceType;

    public static String path = "";
}
