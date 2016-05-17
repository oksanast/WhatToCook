package core;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Mateusz on 17.05.2016.
 * Project WhatToCook
 */
public class ToBuyIngredientsList {
    public static void initialize() {
        toBuyList = new TreeSet<>();
    }
    public static void add(Ingredient ingredient) {
        toBuyList.add(ingredient);
    }

    public static void add(String s) {
        toBuyList.add(new Ingredient(s));
    }
    public static void clear() {
        toBuyList.clear();
    }
    public static void exportToFile() {

    }
    public static void toStringAll() {
        for(Ingredient i : toBuyList) {
            System.out.println(i.getName());
        }
    }
    public static int size() {
        return toBuyList.size();
    }
    public static SortedSet<Ingredient> getSet() {
        return toBuyList;
    }

    private static SortedSet<Ingredient> toBuyList;
}
