package core;

import java.util.SortedSet;

/**
 * Created by Mateusz on 17.05.2016.
 * Project WhatToCook
 */
public class ToBuyIngredientsList {

    public void add(Ingredient ingredient) {
        toBuyList.add(ingredient);
    }

    public void add(String s) {
        toBuyList.add(new Ingredient(s));
    }
    public void clear() {
        toBuyList.clear();
    }
    public void exportToFile() {

    }

    private SortedSet<Ingredient> toBuyList;
}
