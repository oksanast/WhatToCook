package auxiliary;

import java.util.ArrayList;

/**
 * Created by WTC-Team on 23.04.2016.
 * Project WhatToCook
 */
/*
    ZARZĄDZA OBSŁUGĄ JĘZYKÓW W CAŁYM PROGRAMIE
    Łączy się z WhatToCook
 */
public class LanguagePackage
    {
        public LanguagePackage()
        {
            ingredientsPath = "";
            recipesPath = "";
            language = new ArrayList<String>();
        }
        public LanguagePackage(String name, int name_index,String ingredientsPath, String recipesPath, String ownedIngredientsPath, ArrayList<String> language)
        {
            this.name = name;
            this.name_index = name_index;
            this.ingredientsPath = ingredientsPath;
            this.recipesPath = recipesPath;
            this.ownedIngredientsPath = ownedIngredientsPath;
            this.language = language;
        }
        public String getName(){return name;}
        public String GetIngredientsPath()
        {
            return ingredientsPath;
        }
        public String get(int i)
        {
            return language.get(i);
        }
        public String GetRecipesPath()
        {
            return recipesPath;
        }
        public String GetOwnedIngredientsPath() {return ownedIngredientsPath;}
        public int GetSelectedLanguage(){return name_index;}

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            LanguagePackage that = (LanguagePackage) o;

            if (ingredientsPath != null ? !ingredientsPath.equals(that.ingredientsPath) : that.ingredientsPath != null)
                return false;
            if (recipesPath != null ? !recipesPath.equals(that.recipesPath) : that.recipesPath != null) return false;
            return language != null ? language.equals(that.language) : that.language == null;

        }

        @Override
        public int hashCode() {
            int result = ingredientsPath != null ? ingredientsPath.hashCode() : 0;
            result = 31 * result + (recipesPath != null ? recipesPath.hashCode() : 0);
            result = 31 * result + (language != null ? language.hashCode() : 0);
            return result;
        }

        private int name_index;
        private String ingredientsPath;
        private String recipesPath;
        private String ownedIngredientsPath;
        private String name;
        private ArrayList<String> language;
    }
