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
        String[] tmp;
        String line;
        IngredientsList.clear();
        try {
            in = new Scanner (new File (WhatToCook.SelectedPackage.GetIngredientsPath ()));
            while(in.hasNextLine()) {
                line = in.nextLine();
                tmp = line.split("/");
                Ingredient toAdd = new Ingredient(tmp[0]);
                SpareIngredientsList.add(toAdd);
                IngredientsList.add(toAdd);
                    for (int i = 1; i < tmp.length; i++) {
                        SpareIngredientsList.addSpareIngredient(new Ingredient(tmp[i]), toAdd);
                    }
            }
            in.close();
        } catch (FileNotFoundException e)
        {
            System.err.println("Error during loading ingredients");
        }
    }

    static public void addIngredient(Ingredient newIngredient)
    {
            if(IngredientsList.add(newIngredient))
                SpareIngredientsList.add(newIngredient);
            rewriteFile();
    }
    static public void rewriteFile()
    {
        PrintWriter writer;
        String toPrint;
        try
        {
            writer = new PrintWriter(new File(WhatToCook.SelectedPackage.GetIngredientsPath()));
            for(Ingredient ingredient : IngredientsList)
            {
                toPrint = ingredient.getName()+"/";
                toPrint += SpareIngredientsList.getAllSpareIngredients(ingredient);
                writer.println(toPrint);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    static public void removeIngredient(String name)
    {
        IngredientsList.remove(new Ingredient(name));
        rewriteFile();
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
    static public void reloadComboBox(JComboBox<String> comboBox,ArrayList<Ingredient> toHide)
    {
        comboBox.removeAllItems();
        for(Ingredient ingredient : IngredientsList)
        {
            if(!toHide.contains(ingredient))
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
