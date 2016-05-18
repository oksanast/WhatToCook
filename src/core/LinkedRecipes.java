package core;

import java.io.File;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static core.RecipesList.getRecipe;
import static core.RecipesList.recipesList;

/**
 * Created by Radek on 2016-05-18.
 */
public class LinkedRecipes {

    public static void readLinkedRecipes() {
        ArrayList<String> temp_list;
        String source = "data/recipesPL/linked/linkedRecipesPL";
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

    public static void addLinking(int recipe1index, int recipe2index) {
        Recipe recipe1 = recipesList.get(recipe1index);
        Recipe recipe2 = recipesList.get(recipe2index);

        recipe1.getLinkedRecipes().add(recipe2.getName());
        recipe2.getLinkedRecipes().add(recipe1.getName());
    }

    public static void deleteLinking(int recipe1index, int recipe2index) {
        ArrayList<String> tmp = new ArrayList<String>();

        Recipe recipe1 = recipesList.get(recipe1index);
        Recipe recipe2 = recipesList.get(recipe2index);

        recipe1.getLinkedRecipes().remove(recipe2.getName());
        recipe2.getLinkedRecipes().remove(recipe1.getName());
    }
}
