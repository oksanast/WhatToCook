package auxiliary;

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
        private String ingredientsPath;
        private String recipesPath;
    }
