package core;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by WTC-Team on 20.04.2016.
 * Project WhatToCook
 */
/*
    KLASA "STATYCZNA", PONIEKĄD ABSKTRAKCYJNA, SLUŻY DO OBSŁUGI LISTY PRZEPISÓW, NIE TWORZY OBIEKTÓW
    BO POTRZEBNA JEST TYLKO JEDNA TAKA LISTA
 */
public class IngredientsList{


    static public void initialize()
    {
        IngredientsList = new TreeSet<>();
        Scanner in;
        String tmp;
        IngredientsList.clear();
        try {
            in = new Scanner (new File (WhatToCook.SelectedPackage.GetIngredientsPath ()));
            while(in.hasNextLine())
            {
                tmp = in.nextLine();
                Ingredient toAdd = new Ingredient(tmp);
                SpareIngredientsList.add(toAdd);
                addIngredient(toAdd);
            }
            in.close();
        } catch (FileNotFoundException e)
        {
            System.err.println("Error during loading ingredients");
        }
    }

    static public void addIngredient(Ingredient newIngredient)
    {
            IngredientsList.add(newIngredient);

            PrintWriter writer;

            try
            {
                writer = new PrintWriter(new File(WhatToCook.SelectedPackage.GetIngredientsPath()));
                for(Ingredient ingredient : IngredientsList)
                {
                    writer.println(ingredient.getName());
                }
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    }
    static public void removeIngredient(String name)
    {
        IngredientsList.remove(new Ingredient(name));
        PrintWriter writer;

        try
        {
            writer = new PrintWriter(new File(WhatToCook.SelectedPackage.GetIngredientsPath()));
            for(Ingredient ingredient : IngredientsList)
            {
                writer.println(ingredient.getName());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    static public void rebuildModel(DefaultListModel<String> toInsert)
    {
        toInsert.clear();
        for(Ingredient ingredient : IngredientsList)
        {
            toInsert.addElement(ingredient.getName());
        }
    }
    static public void reloadComboBox(JComboBox<String> comboBox)
    {
        comboBox.removeAllItems();
        for(Ingredient ingredient : IngredientsList)
        {
            comboBox.addItem(ingredient.getName());
        }
    }
    static public boolean contain(Ingredient toCheck)
    {
        return IngredientsList.contains(toCheck);
    }
    static public void exportToFile(String filename)
    {
        PrintWriter writer;
        try {
            writer = new PrintWriter(filename, "UTF-8");
            for (Ingredient toExport : IngredientsList)
                writer.println(toExport.getName());
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e)
        {

        }
    }
    static public SortedSet<Ingredient> getSet(){
        return IngredientsList;
    }
    static public int Size()
    {
        return IngredientsList.size();
    }
    private static  SortedSet<Ingredient> IngredientsList;
}
