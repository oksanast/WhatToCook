package core;

import auxiliary.LanguagePackage;
import gui.MainWindow;
import gui.SearchingWindow;

import javax.swing.*;
import java.io.Closeable;
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
public class WhatToCook implements Runnable {

    public static void main(String[] args) {
        new WhatToCook();
    }
    public WhatToCook() {
        buildPolishLanguage();
        buildEnglishLanguage();
        buildUkrainianLanguage();
        PolishPackage = new LanguagePackage(0,"data/ingredientsPL","data/recipesPL",polishLanguagePack);
        EnglishPackage = new LanguagePackage(1,"data/ingredientsEN","data/recipesEN",englishLanguagePack);
        UkrainianPackage = new LanguagePackage(2,"data/ingredientsUKR","data/recipesUKR",ukrainianLanguagePack);
        SelectedPackage = new LanguagePackage();

        Scanner in;
        try
        {
            in = new Scanner(new File("src/mainSettingsConfig"));
            String line = in.next();
            String splittedLine[] = line.split("=");
            if(splittedLine[1].equals("English")) {
                SelectedPackage = EnglishPackage;
            }
            if(splittedLine[1].equals("Polish")) {
                SelectedPackage = PolishPackage;
            }
            if(splittedLine[1].equals("Ukrainian")) {
                SelectedPackage = UkrainianPackage;
            }
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
            SelectedPackage=PolishPackage;
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
        englishLanguagePack = new ArrayList<>();
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
        englishLanguagePack.add("Edit Recipe");
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
        englishLanguagePack.add("About");
        englishLanguagePack.add("OK");
        englishLanguagePack.add("Authors");
        englishLanguagePack.add("This ingredients can't be deleted, cause it is used in recipes:");
        englishLanguagePack.add("Deleting ingredients error");//39 index
        englishLanguagePack.add("Export Ingredients");
        englishLanguagePack.add("Inport Ingredients");
        englishLanguagePack.add("Are you sure? It will cause restart.");
        englishLanguagePack.add("Confirmation");
        englishLanguagePack.add("Export Recipe");
        englishLanguagePack.add("New");
        englishLanguagePack.add("Ingredient");
        englishLanguagePack.add("Recipe");
        englishLanguagePack.add("Ammount:");
        englishLanguagePack.add("Unit:");
        englishLanguagePack.add("Dish for:");//51
        englishLanguagePack.add("Breakfest");
        englishLanguagePack.add("Dessert");
        englishLanguagePack.add("Diner");
        englishLanguagePack.add("Supper");
        englishLanguagePack.add("Snack");
        englishLanguagePack.add("Preparing time:");
        englishLanguagePack.add("Preparing ease:");
        englishLanguagePack.add("Parameters");
        englishLanguagePack.add("Quick");//59
        englishLanguagePack.add("Average");
        englishLanguagePack.add("Long");
        englishLanguagePack.add("Easy");
        englishLanguagePack.add("Average");
        englishLanguagePack.add("Difficult");
        englishLanguagePack.add("Recipe: ");
        englishLanguagePack.add("Instructions: ");
        englishLanguagePack.add("The change of language will cause change of database.");
        englishLanguagePack.add("Import");
        englishLanguagePack.add("Export");
        englishLanguagePack.add("Importing ingredients error");
        englishLanguagePack.add("Following ingredients was not imported, cause they are not in the database");
        englishLanguagePack.add("Ingredients");
        englishLanguagePack.add("Save program state:");
        englishLanguagePack.add("Path: ");
        englishLanguagePack.add("Change path");
        englishLanguagePack.add("Importing error");
        englishLanguagePack.add("It was impossible to read the file with owned ingredients, check its path in the options");
        englishLanguagePack.add("Creating recipe error");
        englishLanguagePack.add("You can create/edit only one recipe in the time");
        englishLanguagePack.add("Searching options");
        englishLanguagePack.add("Search in every word");
        englishLanguagePack.add("Recognize letters size");
        englishLanguagePack.add("View");

    }

    private void buildPolishLanguage() {
        polishLanguagePack = new ArrayList<>();
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
        polishLanguagePack.add("Edytuj Przepis");
        polishLanguagePack.add("Podaj nazwę przepisu");
        polishLanguagePack.add("Napisz instrukcję przygotowania posiłku");
        polishLanguagePack.add("Zapisz i wyjdź");
        polishLanguagePack.add("Wyjdź bez Zapisywania");
        polishLanguagePack.add("Zamknij przepis");
        polishLanguagePack.add("Automatyczne przechodzenie do nowej karty");
        polishLanguagePack.add("Język");
        polishLanguagePack.add("Ogólne");
        polishLanguagePack.add("Składniki");//27
        polishLanguagePack.add("Nazwa Składnika:");
        polishLanguagePack.add("Dodaj");
        polishLanguagePack.add("Usuń");
        polishLanguagePack.add("Usuń przepis");
        polishLanguagePack.add("Sprawdź czy podałeś składniki, nazwę i instrukcję przygotowania. Być może taki przepis już jest w bazie");
        polishLanguagePack.add("Błąd Przepisu");
        polishLanguagePack.add("Podaj nazwę przepisu:");
        polishLanguagePack.add("O programie");
        polishLanguagePack.add("OK");
        polishLanguagePack.add("Autorzy");
        polishLanguagePack.add("Ten składnik nie może być usunięty ponieważ jest wykorzystywany w przepisach na:");
        polishLanguagePack.add("Błąd usuwania składnika");
        polishLanguagePack.add("Eksportuj składniki");
        polishLanguagePack.add("Importuj składniki");
        polishLanguagePack.add("Jesteś pewien? Spowoduje to restart programu.");
        polishLanguagePack.add("Potwierdzenie");
        polishLanguagePack.add("Eksportuj przepis");
        polishLanguagePack.add("Nowy");
        polishLanguagePack.add("Składnik");
        polishLanguagePack.add("Przepis");
        polishLanguagePack.add("Ilość:");
        polishLanguagePack.add("Jednostka:");
        polishLanguagePack.add("Danie na:");//50
        polishLanguagePack.add("Śniadanie");//51
        polishLanguagePack.add("Deser");
        polishLanguagePack.add("Obiad");
        polishLanguagePack.add("Kolację");
        polishLanguagePack.add("Przekąskę");
        polishLanguagePack.add("Czas przygotowywania:");
        polishLanguagePack.add("Łatwość przygotowania:");
        polishLanguagePack.add("Parametry");
        polishLanguagePack.add("Szybko");
        polishLanguagePack.add("Średnio");
        polishLanguagePack.add("Wolno");
        polishLanguagePack.add("Łatwe");
        polishLanguagePack.add("Średnie");
        polishLanguagePack.add("Trudne");
        polishLanguagePack.add("Przepis: ");//65
        polishLanguagePack.add("Wykonanie: ");
        polishLanguagePack.add("Zmiana przepisu spowoduje zmianę bazy danych przepisów.");
        polishLanguagePack.add("Importuj");
        polishLanguagePack.add("Eksportuj");
        polishLanguagePack.add("Błąd dodawania składników");
        polishLanguagePack.add("Poniższe składniki nie zostały zaimportowane, gdyż nie znajdują się w bazie danych");
        polishLanguagePack.add("Posiadane składniki");//72
        polishLanguagePack.add("Zapisuj stan programu:");
        polishLanguagePack.add("Ścieżka:");
        polishLanguagePack.add("Zmień ścieżkę");
        polishLanguagePack.add("Błąd importowania");
        polishLanguagePack.add("Nie można było odczytać pliku z posiadanymi składnikami, sprawdź jego śnieżkę w opcjach");
        polishLanguagePack.add("Błąd tworzenia przepisu");
        polishLanguagePack.add("W jednym momencie możesz tworzyć/edytować tylko jeden przepis");
        polishLanguagePack.add("Opcje Wyszukiwania");//80
        polishLanguagePack.add("Szukaj w każdym słowie");
        polishLanguagePack.add("Uzględniaj wielkość liter");
        polishLanguagePack.add("Widok");
    }

    private void buildUkrainianLanguage() {
        ukrainianLanguagePack = new ArrayList<>();
        ukrainianLanguagePack.add("Файл");
        ukrainianLanguagePack.add("Закрити");
        ukrainianLanguagePack.add("Змінити");
        ukrainianLanguagePack.add("Очистити вибрані інгредієнти");
        ukrainianLanguagePack.add("Очистити результати пошуку");
        ukrainianLanguagePack.add("Допомога");
        ukrainianLanguagePack.add("Опції");
        ukrainianLanguagePack.add("Про програму");
        ukrainianLanguagePack.add("Пошук");
        ukrainianLanguagePack.add("База рецептів");
        ukrainianLanguagePack.add("Вибери інгредієнти");
        ukrainianLanguagePack.add("Вибери інгредієнти:");
        ukrainianLanguagePack.add("Додай інгредієнт");
        ukrainianLanguagePack.add("Видали інгредієнт");
        ukrainianLanguagePack.add("Знайдені рецепти");
        ukrainianLanguagePack.add("Шукай рецепти");
        ukrainianLanguagePack.add("Пошук");
        ukrainianLanguagePack.add("Новий рецепт");
        ukrainianLanguagePack.add("Зміни рецепт");
        ukrainianLanguagePack.add("Подай назву рецепту");
        ukrainianLanguagePack.add("Напиши спосіб приготування");
        ukrainianLanguagePack.add("Зберегти і вийти");
        ukrainianLanguagePack.add("Вийти без зберігання");
        ukrainianLanguagePack.add("Закрити рецепт");
        ukrainianLanguagePack.add("Автоматичний перехід до нового вікна");
        ukrainianLanguagePack.add("Мова");
        ukrainianLanguagePack.add("Загальне");
        ukrainianLanguagePack.add("Інгредієнти");//27
        ukrainianLanguagePack.add("Назва інгредієнта:");
        ukrainianLanguagePack.add("Додати");
        ukrainianLanguagePack.add("Видалити");
        ukrainianLanguagePack.add("Видалити рецепт");
        ukrainianLanguagePack.add("Перевір чи добре записані назва, інгредієнти і спосіб приготування. Такий рецепт може вже існувати в базі.");
        ukrainianLanguagePack.add("Помилка рецепту");
        ukrainianLanguagePack.add("Подай назву рецепту:");
        ukrainianLanguagePack.add("Про програму");
        ukrainianLanguagePack.add("OK");
        ukrainianLanguagePack.add("Автори");
        ukrainianLanguagePack.add("Не можна видалити цей інгредієнт, оскільки він використовується у:");
        ukrainianLanguagePack.add("Помилка при видаленні інгредієнту");
        ukrainianLanguagePack.add("Експортуй інгредієнти");
        ukrainianLanguagePack.add("Імпортуй інгредієнти");
        ukrainianLanguagePack.add("Впевнені? Це призведе до рестарту програми.");
        ukrainianLanguagePack.add("Впевненийю");
        ukrainianLanguagePack.add("Експортуй рецепт");
        ukrainianLanguagePack.add("Новий");
        ukrainianLanguagePack.add("Інгредієнт");
        ukrainianLanguagePack.add("Рецепт");
        ukrainianLanguagePack.add("Кількість:");
        ukrainianLanguagePack.add("Одиниця вимірювання:");
        ukrainianLanguagePack.add("Страва на:");//50
        ukrainianLanguagePack.add("Сніданок");//51
        ukrainianLanguagePack.add("Десерт");
        ukrainianLanguagePack.add("Обід");
        ukrainianLanguagePack.add("Вечеря");
        ukrainianLanguagePack.add("Закуска");
        ukrainianLanguagePack.add("Час приготування:");
        ukrainianLanguagePack.add("Легкість приготування:");
        ukrainianLanguagePack.add("Параметри");
        ukrainianLanguagePack.add("Швидко");
        ukrainianLanguagePack.add("Середньо");
        ukrainianLanguagePack.add("Повільно");
        ukrainianLanguagePack.add("Легко");
        ukrainianLanguagePack.add("Середньо");
        ukrainianLanguagePack.add("Складно");
        ukrainianLanguagePack.add("Рецепт: ");//65
        ukrainianLanguagePack.add("Приготування: ");
        ukrainianLanguagePack.add("Зміна рецепту споводує зміну бази рецептів.");
        ukrainianLanguagePack.add("Імпортуй");
        ukrainianLanguagePack.add("Експортуй");
        ukrainianLanguagePack.add("Помилка при додаванню інгредієнту");
        ukrainianLanguagePack.add("Інгредієнти, зазначені нижче, не імпортовані, хоча знаходяться в базі даних");
        ukrainianLanguagePack.add("Інгредієнти, які маєш");//72
        ukrainianLanguagePack.add("Запиши стан програми:");
        ukrainianLanguagePack.add("Шлях:");
        ukrainianLanguagePack.add("Помилка створення рецепту");
        ukrainianLanguagePack.add("Зміни шлях");
        ukrainianLanguagePack.add("Помилка імпортування");
        ukrainianLanguagePack.add("Не можна прочитати файлу з цими інгредієнтами, перевір шлях в опціях.");
        ukrainianLanguagePack.add("Одночасно можеш створити/коригувати лише один рецепт");
        ukrainianLanguagePack.add("Опції пошуку");//80
        ukrainianLanguagePack.add("Шукати в кожному слові");
        ukrainianLanguagePack.add("Бери під увагу великі і малі літери");
        ukrainianLanguagePack.add("Widok");
    }
    private static ArrayList<String> polishLanguagePack;
    private static ArrayList<String> englishLanguagePack;
    private static ArrayList<String> ukrainianLanguagePack;
    public static MainWindow frame;
    public static String version = "1.7";

    public static LanguagePackage SelectedPackage;

    public static LanguagePackage PolishPackage;
    public static LanguagePackage EnglishPackage;
    public static LanguagePackage UkrainianPackage;

    public static final String endl = System.lineSeparator();

    //public static RecipesList recipesDatabase;
}