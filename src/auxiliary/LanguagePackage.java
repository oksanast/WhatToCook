package auxiliary;

import java.util.ArrayList;

/**
 * Created by Mateusz on 23.04.2016.
 * Project WhatToCook
 */
public class LanguagePackage
    {
        public LanguagePackage()
        {
            ingredientsPath = "";
            recipesPath = "";
        }
        public LanguagePackage(String ingredientsPath, String recipesPath)
        {
            this.ingredientsPath = ingredientsPath;
            this.recipesPath = recipesPath;
        }

        public String GetIngredientsPath()
        {
            return ingredientsPath;
        }
        public String GetRecipesPath()
        {
            return recipesPath;
        }
        public void setIngredientsPath(String ingredientsPath)
        {
            this.ingredientsPath = ingredientsPath;
        }
        public void setRecipesPath(String recipesPath)
        {
            this.recipesPath = recipesPath;
        }
        private String ingredientsPath;
        private String recipesPath;
    }
