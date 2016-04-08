package core;

import java.util.ArrayList;

/**
 * Created by Mateusz on 23.03.2016.
 */
public class Recipe
{
    public Recipe(String name, ArrayList<Ingredient> ingredients, String instructions)
    {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }
    public String getName()
    {
        return name;
    }
    public String getRecipe()
    {
        return instructions;
    }
    public Ingredient getIngredient(int i)
    {
        return ingredients.get(i);
    }
    public int getSize(){return ingredients.size();}
    private String name;
    private ArrayList<Ingredient> ingredients;
    private String instructions;
}
