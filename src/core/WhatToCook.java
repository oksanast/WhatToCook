package core;

import gui.MainWindow;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by Mateusz on 09.03.2016.s
 */
public class WhatToCook implements Runnable {

    public static void main(String[] args) {
        new WhatToCook();
    }
    public WhatToCook() {
        selectedLanguagePack = new ArrayList<String>();
        buildPolishLanguage();
        buildEnglishLanguage();
        selectedLanguagePack = polishLanguagePack;

        RecipesList.initialize();
        IngredientsList.initialize();
        IngredientsList.loadIngredients();

        Scanner in;
        try
        {
            in = new Scanner(new File("src/cfg"));
            String language = in.next();
            if(language.equals("english"))
                selectedLanguagePack = englishLanguagePack;
            if(language.equals("polski"))
                selectedLanguagePack = polishLanguagePack;
            MainWindow.getToNewCard = in.nextBoolean();
            System.out.println(MainWindow.getToNewCard);

        }
        catch (FileNotFoundException e)
        {

        }
        catch (NoSuchElementException e)
        {

        }

        frame = new MainWindow();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        new Thread(this).start();
    }




    public void run()
    {

    }
    private void buildEnglishLanguage()
    {
        englishLanguagePack = new ArrayList<String>();
        englishLanguagePack.add("File");
        englishLanguagePack.add("Exit");
        englishLanguagePack.add("Edit");
        englishLanguagePack.add("Clear inserted ingredients");
        englishLanguagePack.add("Clear search results");
        englishLanguagePack.add("Help");
        englishLanguagePack.add("Options");
        englishLanguagePack.add("About");
        englishLanguagePack.add("Search");
        englishLanguagePack.add("Recipes Database");
        englishLanguagePack.add("Insert ingredients");
        englishLanguagePack.add("Choose ingredients:");
        englishLanguagePack.add("Add ingredients");
        englishLanguagePack.add("Remove ingredients");
        englishLanguagePack.add("Founds receipes");
        englishLanguagePack.add("Search for receipes");
        englishLanguagePack.add("Search");
        englishLanguagePack.add("New Recipe");
        englishLanguagePack.add("Edit Selected Recipe");
        englishLanguagePack.add("Insert Recipe Name");
        englishLanguagePack.add("Write making instrictions below");
        englishLanguagePack.add("Save and Exit");
        englishLanguagePack.add("Exit without saving");
        englishLanguagePack.add("Close the Recipe");
        englishLanguagePack.add("Auto-Move to the new tab");
        englishLanguagePack.add("Language");
        englishLanguagePack.add("General");
        englishLanguagePack.add("Ingredients");
        englishLanguagePack.add("Ingredient Name:");
        englishLanguagePack.add("Add");
        englishLanguagePack.add("Remove");
        englishLanguagePack.add("Remove recipe");
        englishLanguagePack.add("Check input ingredients, name and preparing instructions. Maybe the same recipe is already in the database");
        englishLanguagePack.add("Recipe Error");
        englishLanguagePack.add("Insert recipe name:");
    }

    private void buildPolishLanguage()
    {
        polishLanguagePack = new ArrayList<String>();
        polishLanguagePack.add("Plik");
        polishLanguagePack.add("Zakończ");
        polishLanguagePack.add("Edycja");
        polishLanguagePack.add("Wyczyść wprowadzone składniki");
        polishLanguagePack.add("Wyczyść wyniki wyszukiwania");
        polishLanguagePack.add("Pomoc");
        polishLanguagePack.add("Opcje");
        polishLanguagePack.add("O Programie");
        polishLanguagePack.add("Wyszukiwanie");
        polishLanguagePack.add("Baza Przepisów");
        polishLanguagePack.add("Wprowadź składniki");
        polishLanguagePack.add("Wybierz składniki:");
        polishLanguagePack.add("Dodaj składnik");
        polishLanguagePack.add("Usuń składnik");
        polishLanguagePack.add("Znalezione przepisy");
        polishLanguagePack.add("Szukaj przepisów");
        polishLanguagePack.add("Wyszukaj");
        polishLanguagePack.add("Nowy Przepis");
        polishLanguagePack.add("Edytuj Wybrany Przepis");
        polishLanguagePack.add("Podaj nazwę przepisu");
        polishLanguagePack.add("Napisz instrukcję przygotowania posiłku");
        polishLanguagePack.add("Zapisz i wyjdź");
        polishLanguagePack.add("Wyjdź bez Zapisywania");
        polishLanguagePack.add("Zamknij przepis");
        polishLanguagePack.add("Automatyczne przechodzenie do nowej karty");
        polishLanguagePack.add("Język");
        polishLanguagePack.add("Ogólne");
        polishLanguagePack.add("Składniki");
        polishLanguagePack.add("Nazwa Składnika:");
        polishLanguagePack.add("Dodaj");
        polishLanguagePack.add("Usuń");
        polishLanguagePack.add("Usuń przepis");
        polishLanguagePack.add("Sprawdź czy podałeś składniki, nazwę i instrukcję przygotowania. Być może taki przepis już jest w bazie");
        polishLanguagePack.add("Błąd Przepisu");
        polishLanguagePack.add("Podaj nazwę przepisu:");
    }
    public static ArrayList<String> polishLanguagePack;
    public static ArrayList<String> englishLanguagePack;
    public static ArrayList<String> selectedLanguagePack;
    public static MainWindow frame;

    //public static RecipesList recipesDatabase;
}
