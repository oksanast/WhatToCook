package auxiliary;

import core.Recipe;

import java.util.ArrayList;

/**
 * Created by WTC-Team on 23.04.2016.
 * Project WhatToCook
 */
/*
    UŻYWANA DO NUMEROWANIA PRZEPISÓW PRZY WYŚWIETLANIU (TAK ABY DAŁO SIĘ EKSPORTOWAC PRZEPIS Z DANEJ KARTY
    STARTPAGE - KARTA Z KTOREJ OTWARTO DANY PRZEPIS (A ZARAZEM KARTA POWROTNA)
    Łączy się z MainWindow
 */
public class PairRecipeIndex {

    private class NumeratedRecipe
    {
        public NumeratedRecipe(Recipe recipe,int index,int startPage)
        {
            this.recipe = recipe;
            this.index = index;
            this.startPage = startPage;
        }
        private int startPage;
        private Recipe recipe;
        private int index;

    }
    public PairRecipeIndex()
    {
        recipesList = new ArrayList<>();
    }
    public void add(Recipe recipe, int index,int StartPage)
    {
        NumeratedRecipe newEntry = new NumeratedRecipe(recipe,index,StartPage);
        recipesList.add(newEntry);
    }
    public void remove(int index)
    {
        int i = 0;
        for(;i < recipesList.size();i++)
        {
            if(recipesList.get(i).index==index)
                recipesList.remove(i);

        }
    }
    public Recipe getRecipe(int index)
    {
        for (NumeratedRecipe aRecipesList : recipesList) {
            if (aRecipesList.index == index)
                return aRecipesList.recipe;
        }
        return null;
    }
    public int getStartPage(int index)
    {
        for (NumeratedRecipe aRecipesList : recipesList) {
            if (aRecipesList.index == index)
                return aRecipesList.startPage;
        }
        return 1;
    }
    private ArrayList<NumeratedRecipe> recipesList;
}
