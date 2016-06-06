package core;

import javafx.scene.control.ListView;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by WTC-Team on 14.05.2016.
 * Project WhatToCook
 */
/*
    Klasa typu rozbudowana lista - Analogiczna do "IngredientsList" i "RecipesList"
    Służy obsłudze listy składników alternatywnych
    Nie zawiera odczytu/zapisu do pliku, jest to zintegrowane z klasą IngredientsList, jest to błąd
    pod względem podejścia obiektowego, ale inaczej trzeba by robić kolejne pliki ze składnikami
 */
public class SpareIngredientsList {

    public static void initialize() {
        spareIngredientslist=new ArrayList<>();
    }

    public static SpareIngredients get(Ingredient ingredient)
    {
        for(SpareIngredients i : spareIngredientslist)
        {
            if(i.getName().equals(ingredient.getName()))
                return i;
        }
        return null;
    }
    public static void add(Ingredient ingredient) {
        spareIngredientslist.add(new SpareIngredients(ingredient));
    }

    public static void rebuildListModel(ListView<String> model, Ingredient ingredient) {
        model.getItems().clear();
        SpareIngredients si = getElementByIngredient(ingredient);
        if (si != null) {
            for (Ingredient i : si.getSpareIngredients())
                model.getItems().add(i.getName());
        }
    }

    public static void rebuildComboBox(JComboBox<String> comboBox, Ingredient ingredient){
        comboBox.removeAllItems();
        for(Ingredient i : IngredientsList.getSet())
        {
            if(!i.getName().equals(ingredient.getName()))
            comboBox.addItem(i.getName());
        }
    }
    /*
        Pobiera element klasy "SpareIngredients" na podstawie składnika bazowego
     */
    private static SpareIngredients getElementByIngredient(Ingredient ingredient)
    {
        for(SpareIngredients s : spareIngredientslist)
        {
            if(s.getName().equals(ingredient.getName()))
                return s;
        }
        return null;
    }
    public static void addSpareIngredient(Ingredient spare, Ingredient main){
        SpareIngredients s = getElementByIngredient(main);
            if (s != null) {
                s.addSpareIngredient(spare);
            }
    }
    public static void removeSpareIngredient(Ingredient spare, Ingredient main) {
        SpareIngredients s = getElementByIngredient(main);
        if (s != null) {
            s.removeSpareIngredient(spare);
        }
    }
    /* Funkcja do wypisywania składników alternatywnych danego składnika do ciągu znaków, wykorzystywana przy wypisywaniu
        do pliku
     */
    static String getAllSpareIngredients(Ingredient ingredient)
    {
        String result = "";
        boolean atLeastOne=false;
        SpareIngredients s = getElementByIngredient(ingredient);
        if (s != null) {
            for(Ingredient i : s.getSpareIngredients()) {
                atLeastOne=true;
                result+=i.getName()+"/";
            }
        }
        if(atLeastOne)
            return result.substring(0,result.length()-1);
        return result;
    }
    /*  Najważniejsza funckja do wyszukiwania, sprawdza czy dany składnik "main" może być zastopiąny przez składnik
        "spare"
    */
    static boolean containSpareIngredient(Ingredient spare, Ingredient main) {
        SpareIngredients s = getElementByIngredient(main);
        if (s != null) {
            for (Ingredient i : s.getSpareIngredients()) {
                if (i.getName().equals(spare.getName()))
                    return true;
            }
        }
        return false;
    }
    public static void removeElement(Ingredient main) {
        for(SpareIngredients s : spareIngredientslist){
            if(s.getName().equals(main.getName()))
            {
                spareIngredientslist.remove(s);
                break;
            }
        }
    }
    public static void removeSpareIngredientFromEverywhere(Ingredient spare) {
        for(SpareIngredients s : spareIngredientslist) {
            s.getSpareIngredients().remove(spare);
        }
    }
    private static ArrayList<SpareIngredients> spareIngredientslist;
}
