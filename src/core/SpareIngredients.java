package core;

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
class SpareIngredients {

    SpareIngredients(Ingredient ingredient) {
        this.ingredient = ingredient;
        spareIngredients = new TreeSet<>();
    }

    public String getName() {
        return ingredient.getName();
    }

    void addSpareIngredient(Ingredient ingredient) {
        spareIngredients.add(ingredient);
    }

    void removeSpareIngredient(Ingredient ingredient) {
        spareIngredients.remove(ingredient);
    }

    SortedSet<Ingredient> getSpareIngredients() {
        return spareIngredients;
    }
    public Ingredient ingredient;
    private SortedSet<Ingredient> spareIngredients;
}

