package core;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WTC-Team on 14.05.2016.
 * Project WhatToCook
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

    public static void rebuildListModel(DefaultListModel<String> model,Ingredient ingredient) {
        model.clear();
        SpareIngredients si = getElementByIngredient(ingredient);
        for(Ingredient i : si.getSpareIngredients())
            model.addElement(i.getName());
    }

    public static void rebuildComboBox(JComboBox<String> comboBox,Ingredient ingredient){
        comboBox.removeAllItems();
        for(Ingredient i : IngredientsList.getSet())
        {
            if(!i.getName().equals(ingredient.getName()))
            comboBox.addItem(i.getName());
        }
    }
    private static SpareIngredients getElementByIngredient(Ingredient ingredient)
    {
        for(SpareIngredients s : spareIngredientslist)
        {
            if(s.getName().equals(ingredient.getName()))
                return s;
        }
        return null;
    }
    public static void addSpareIngredient(Ingredient spare,Ingredient main){
        SpareIngredients s = getElementByIngredient(main);
        s.addSpareIngredient(spare);
    }
    public static void removeSpareIngredient(Ingredient spare, Ingredient main) {
        SpareIngredients s = getElementByIngredient(main);
        s.removeSpareIngredient(spare);
    }
    public static ArrayList<SpareIngredients> spareIngredientslist;
}
