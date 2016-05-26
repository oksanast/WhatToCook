package core;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by WTC-Team on 17.05.2016.
 * Project WhatToCook
 */
public class ToBuyIngredientsList {
    public static void initialize() {
        toBuyList = new TreeSet<>();
        try {
            Scanner in = new Scanner(new File(WhatToCook.path + "/data/toBuyList/shoppingList"));
            while(in.hasNextLine()) {
                add(new Ingredient(in.nextLine()));
            }
            in.close();
        } catch (FileNotFoundException e) {

        }

    }
    public static void add(Ingredient ingredient) {
        toBuyList.add(ingredient);
        export();
    }

    public static void add(String s) {
        toBuyList.add(new Ingredient(s));
    }
    public static void clear() {
        toBuyList.clear();
    }

    public static int size() {
        return toBuyList.size();
    }
    public static SortedSet<Ingredient> getSet() {
        return toBuyList;
    }

    public static ObservableList<String> getObservableList() {
        ObservableList<String> list = FXCollections.observableArrayList();
        for(Ingredient i : toBuyList) {
            list.add(i.getName());
        }
        return list;
    }

    private static void export() {
        try {
            PrintWriter writer = new PrintWriter(new File(WhatToCook.path + "/data/toBuyList/shoppingList"));
            for(Ingredient i : toBuyList) {
                writer.println(i.getName());
            }
            writer.close();

        } catch (FileNotFoundException e) {
        }
    }

    private static SortedSet<Ingredient> toBuyList;
}
