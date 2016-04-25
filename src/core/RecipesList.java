package core;

import auxiliary.PairAmountUnit;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by Mateusz on 23.03.2016.
 * Project WhatToCook
 */
public class RecipesList
{
    static public void initialize()
    {
        recipesList = new ArrayList<>();

        recipesList.clear();
        final File[] listOfFiles = new File(WhatToCook.SelectedPackage.GetRecipesPath()).listFiles();
        int i = 0;
        while (i < listOfFiles.length) {
            String name;
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            ArrayList<PairAmountUnit> ammountsAndUnits = new ArrayList<>();
            int ingredientsAmmount;
            String instructions = "";

            try{
                Scanner in = new Scanner(listOfFiles[i]);
                name = in.nextLine();
                ingredientsAmmount = in.nextInt();
                in.nextLine();
                for(int j = 0; j < ingredientsAmmount;j++)
                {
                    String nextName = in.nextLine();
                    String IngredientName = "";
                    String[] ingredient;
                    ingredient = nextName.split(" ");
                    for(int k = 0;k < ingredient.length-2;k++) {
                        IngredientName += ingredient[k];
                        if(k<ingredient.length-3)
                            IngredientName+=" ";
                    }
                    Ingredient newIngredient = new Ingredient(IngredientName);
                    ingredients.add(newIngredient);

                    String ammount = "";
                    String unit = "";
                    if(ingredient[ingredient.length-2].equals("true"))
                        ammount = in.nextLine();
                    if(ingredient[ingredient.length-1].equals("true"))
                        unit = in.nextLine();

                    ammountsAndUnits.add(new PairAmountUnit(ammount,unit));



                }
                while(in.hasNextLine())
                {
                    instructions+=in.nextLine();
                }

                recipesList.add(new Recipe(name,ingredients,ammountsAndUnits,instructions));
                Collections.sort(recipesList);
                in.close();
            }
            catch (FileNotFoundException e)
            {

            }
            i++;
        }


    }
    static public void add(Recipe recipe)
    {
        recipesList.add(recipe);
        Collections.sort(recipesList);
        String filename;
        filename = WhatToCook.SelectedPackage.GetRecipesPath() +"/"+ recipe.getName();
        try {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filename)));
           writer.println(recipe.getName());
            writer.println(recipe.getSize());
            for(int i = 0; i < recipe.getSize();i++)
            {
                String NameAndBooleans;
                NameAndBooleans = recipe.getIngredient(i).getName();
                if(recipe.getAmmount(i).equals(""))
                    NameAndBooleans+=" " + false;
                else
                    NameAndBooleans+=" " + true;
                if(recipe.getUnit(i).equals(""))
                    NameAndBooleans+=" " + false;
                else
                    NameAndBooleans+=" " + true;
                writer.println(NameAndBooleans);
                if(!recipe.getAmmount(i).equals(""))
                    writer.println(recipe.getAmmount(i));
                if(!recipe.getUnit(i).equals(""))
                    writer.println(recipe.getUnit(i));
            }
            writer.println((recipe.getRecipe()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    static public boolean isRecipe(Recipe toCheck)
    {

        for(Recipe recipe : recipesList)
        {
            if(toCheck.getName().equals(recipe.getName()))
                return true;
        }
        return false;
    }
    static public int getIndex(String toFind)
    {
        int i;
        for(i = 0; i < recipesList.size();i++)
        {
            if(recipesList.get(i).getName().equals(toFind))
                return i;
        }
        return i;
    }
    static public void remove(String toDelete)
    {
        for(int i = 0; i < recipesList.size();i++)
        {
            String path;
            if(toDelete.equals(recipesList.get(i).getName())) {
                path = WhatToCook.SelectedPackage.GetRecipesPath() + "/" + recipesList.get(i).getName();
                recipesList.remove(i);
                File fileToDelete = new File(path);
                fileToDelete.delete();
                return;
            }
        }
    }
    static public int size()
    {
        return recipesList.size();
    }
    static public String getRecipeNameAtIndex(int i)
    {
        return recipesList.get(i).getName();
    }
    static public boolean checkWithIngredientsList(ArrayList<Ingredient> aviableIngredients,int index)
    {
        boolean contains;
        for(int i = 0; i < recipesList.get(index).getSize();i++)
        {
            contains = false;
            for (Ingredient aviableIngredient : aviableIngredients)
                if (recipesList.get(index).getIngredient(i).equals(aviableIngredient))
                    contains = true;

            if(!contains)
                return false;
        }
        return true;

    }
    static public ArrayList<Recipe> recipesList;
}
