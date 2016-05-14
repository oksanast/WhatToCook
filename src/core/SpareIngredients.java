package core;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by WTC-Team on 14.05.2016.
 * Project WhatToCook
 */
public class SpareIngredients {
    public SpareIngredients(Ingredient ingredient) {
        this.ingredient = ingredient;
        spareIngredients = new TreeSet<>();
    }
    public String getName() {
        return ingredient.getName();
    }
    public void addSpareIngredient(Ingredient ingredient) {
        spareIngredients.add(ingredient);
    }

    public void removeSpareIngredient(Ingredient ingredient) {
        spareIngredients.remove(ingredient);
    }
    public SortedSet<Ingredient> getSpareIngredients() {
        return spareIngredients;
    }
    public Ingredient ingredient;
    public SortedSet<Ingredient> spareIngredients;
}

