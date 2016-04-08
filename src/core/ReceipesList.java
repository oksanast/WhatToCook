package core;

import java.util.ArrayList;

/**
 * Created by Mateusz on 23.03.2016.
 */
public class ReceipesList
{
    public ReceipesList()
    {
        recipesList = new ArrayList<Recipe>();
    }
    public void add(Recipe recipe)
    {
        recipesList.add(recipe);
    }
    public ArrayList<Recipe> recipesList;
    public boolean isRecipe(Recipe toCheck)
    {
        for(int i = 0; i < recipesList.size();i++)
        {
            if(toCheck.getName().equals(recipesList.get(i).getName()))
                return true;
        }
        return false;
    }
    public int getIndex(String toFind)
    {
        int i;
        for(i = 0; i < recipesList.size();i++)
        {
            if(recipesList.get(i).getName().equals(toFind))
                return i;
        }
        return i;
    }
    public void remove(String toDelete)
    {
        for(int i = 0; i < recipesList.size();i++)
        {
            if(toDelete.equals(recipesList.get(i).getName()))
                recipesList.remove(i);
            return;
        }
    }
}
