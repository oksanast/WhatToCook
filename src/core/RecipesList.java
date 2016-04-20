package core;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Mateusz on 23.03.2016.
 */
public class RecipesList
{
    static public void initialize()
    {
            recipesList = new ArrayList<Recipe>();
            final File[] listOfFiles = new File("src/recipes").listFiles();
            for(int i = 0; i < listOfFiles.length;i++)
            {
                String name;
                ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
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
                        Ingredient newIngredient = new Ingredient(nextName);
                        ingredients.add(newIngredient);
                    }
                    while(in.hasNextLine())
                    {
                        instructions+=in.nextLine();
                    }
                    recipesList.add(new Recipe(name,ingredients,instructions));
                    in.close();
                }
                catch (FileNotFoundException e)
                {

                }
            }


    }
    static public void add(Recipe recipe)
    {
        recipesList.add(recipe);
        String filename;
        filename = "src/recipes/" + recipe.getName();
        try {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filename)));
           writer.println(recipe.getName());
            writer.println(recipe.getSize());
            for(int i = 0; i < recipe.getSize();i++)
            {
                writer.println(recipe.getIngredient(i).getName());
            }
            writer.println((recipe.getRecipe()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    static public boolean isRecipe(Recipe toCheck)
    {
        for(int i = 0; i < recipesList.size();i++)
        {
            if(toCheck.getName().equals(recipesList.get(i).getName()))
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
            String path = "";
            if(toDelete.equals(recipesList.get(i).getName())) {
                path = "src/recipes/" + recipesList.get(i).getName();
                recipesList.remove(i);
            }
                File fileToDelete = new File(path);
                System.out.println(fileToDelete.exists());
                fileToDelete.delete();

            return;
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
        boolean contains = true;
        for(int i = 0; i < recipesList.get(index).getSize();i++)
        {
            contains = false;
            for(int j = 0; j < aviableIngredients.size();j++)
            {
                if(recipesList.get(index).getIngredient(i).equals(aviableIngredients.get(j)))
                    contains = true;
            }
            if(!contains)
                return false;
        }
        return true;

    }
    static public ArrayList<Recipe> recipesList;
}
