package core;

import auxiliary.PairAmountUnit;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Mateusz on 23.03.2016.
 */
public class Recipe implements Comparable<Recipe>
{
    public Recipe(String name, ArrayList<Ingredient> ingredients, ArrayList<PairAmountUnit> ingredientsAmountAndUnits, String instructions)
   // public Recipe(String name, ArrayList<Ingredient> ingredients, String instructions)
    {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.ingredientsAmountAndUnits = ingredientsAmountAndUnits;
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
    public String getAmmount(int i){return ingredientsAmountAndUnits.get(i).getAmmount();}
    public String getUnit(int i){return ingredientsAmountAndUnits.get(i).getUnit();}
    public int getSize(){return ingredients.size();}
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<PairAmountUnit> ingredientsAmountAndUnits;
    private String instructions;

    @Override
    public int compareTo(Recipe o) {
        Collator c = Collator.getInstance(Locale.getDefault());
        return c.compare(this.getName(),o.getName());

    }
}
