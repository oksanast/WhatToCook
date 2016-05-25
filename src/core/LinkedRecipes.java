package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import static core.RecipesList.getRecipe;
import static core.RecipesList.recipesList;
import static core.WhatToCook.endl;

/**
 * Created by WTC-Team on 2016-05-18.
 */
public class LinkedRecipes {

    static void readLinkedRecipes() {
        ArrayList<String> temp_list;
        String source = "data/recipesPL"+"/linked/linkedRecipesPL";
        String curr_line;
        int number_of_recipes;
        Recipe temp_recipe;
        try {
            Scanner in = new Scanner(new File(source));
            while(in.hasNextLine()) {
                curr_line = in.nextLine();
                temp_recipe = getRecipe(curr_line);
                number_of_recipes = Integer.parseInt(in.nextLine());
                temp_list = new ArrayList<String>();
                for (int i = 0; i < number_of_recipes; i++) {
                    temp_list.add(in.nextLine());
                }
                temp_recipe.setLinkedRecipes(temp_list);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Linkings file not found");
        }
    }

    public static void addLinking(String nameRecipe1, String nameRecipe2) {
        //if (recipe1index >= 0) {
            Recipe recipe1 = RecipesList.getRecipe(nameRecipe1);
            Recipe recipe2 = RecipesList.getRecipe(nameRecipe2);

            if (!recipe1.getLinkedRecipes().contains(recipe2.getName())) {
                recipe1.getLinkedRecipes().add(recipe2.getName());
                recipe2.getLinkedRecipes().add(recipe1.getName());
            }
            saveLinkings();
        //}
    }

    //Drugi arg jest indexem na liście przepisów linkowanych do przepisu pod pierwszym argumentem!
    public static void deleteLinking(String nameRecipe1, int recipe2index) {
        if (recipesList.size() == 0) System.out.println("Nie ma żadnych przepisów?!");
        ArrayList<String> tmp = new ArrayList<String>();

        Recipe recipe1 = RecipesList.getRecipe(nameRecipe1);

        int i = 0;
        Recipe recipe2 = recipesList.get(0);
        Boolean stop = false;
        while ((i < recipesList.size()) && !stop) {
            recipe2 = recipesList.get(i);
            stop = recipe2.getName().equals(recipe1.getLinkedRecipes().get(recipe2index));
            i++;
        }

        recipe1.getLinkedRecipes().remove(recipe2index);
        recipe2.getLinkedRecipes().remove(recipe1.getName());

        saveLinkings();
    }
    public static void deleteLinking(String nameRecipe1, String recipe2name) {
        if (recipesList.size() == 0) System.out.println("Nie ma żadnych przepisów?!");

        Recipe recipe1 = RecipesList.getRecipe(nameRecipe1);

        int i = 0;
        Recipe recipe2 = recipesList.get(0);
        Boolean stop = false;
        while ((i < recipesList.size()) && !stop) {
            recipe2 = recipesList.get(i);
            stop = recipe2.getName().equals(recipe2name);
            i++;
        }

        recipe1.getLinkedRecipes().remove(recipe2name);
        recipe2.getLinkedRecipes().remove(recipe1.getName());

        saveLinkings();
    }

    public static void saveLinkings() {
        String out = "data/recipesPL"+"/linked/linkedRecipesPL";
        //String out = "data/recipesPL/linked/linkedRecipes";
        String content = "";
        String linkRecipe;
        Recipe recipe;
        int i, j;
        for (j = 0; j < recipesList.size(); j++) {
            recipe = recipesList.get(j);
            if (recipe.getLinkedRecipes().size() > 0) {
                if (!content.equals(""))
                    content += endl;
                content += recipe.getName();
                content += endl;
                content += recipe.getLinkedRecipes().size();
                content += endl;
                for (i = 0; i< recipe.getLinkedRecipes().size(); i++) {
                    linkRecipe = recipe.getLinkedRecipes().get(i);
                    content += linkRecipe;
                    if (i < recipe.getLinkedRecipes().size() - 1)
                        content += endl;
                }
            }
        }
        try {
            PrintWriter save = new PrintWriter(out, "UTF-8");
            save.write(content);
            save.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("Linkings file not found");
        }
    }
}
