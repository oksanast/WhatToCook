package core;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by WTC-Team on 14.05.2016.
 * Project WhatToCook
 */
/*  KLASA "REKODROWA", przechowuje rekordy typu składnik - lista składników dla niego alternatywnych
    Każdy składnik może pojawić się tylko raz więc przechowywane są one w zbiorze, posortowanym alfabetycznie
    dzięki implementacji comparable w "ingredient"

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

