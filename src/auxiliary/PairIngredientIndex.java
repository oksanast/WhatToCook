package auxiliary;

import core.Recipe;

import java.util.ArrayList;

/**
 * Created by Mateusz on 23.04.2016.
 * Project WhatToCook
 */
public class PairIngredientIndex {

    class NumeratedRecipe
    {
        public NumeratedRecipe(Recipe recipe,int index)
        {
            this.recipe = recipe;
            this.index = index;
        }

        private Recipe recipe;
        private int index;

    }
    public PairIngredientIndex()
    {
        recipesList = new ArrayList<NumeratedRecipe>();
    }
    public void add(Recipe recipe, int index)
    {
        NumeratedRecipe newEntry = new NumeratedRecipe(recipe,index);
        recipesList.add(newEntry);
    }
    public void remove(int index)
    {
        for(int i = 0; i < recipesList.size();i++)
        {
            if(recipesList.get(i).index==index)
                recipesList.remove(i);
        }
    }
    public Recipe getRecipe(int index)
    {
        for(int i = 0; i < recipesList.size();i++)
        {
            if(recipesList.get(i).index==index)
                return recipesList.get(i).recipe;
        }
        return null;
    }


    private ArrayList<NumeratedRecipe> recipesList;
}
