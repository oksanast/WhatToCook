package tests;

import auxiliary.PairAmountUnit;
import auxiliary.RecipeParameters;
import core.Ingredient;
import core.IngredientsList;
import core.WhatToCook;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.Assert.*;

public class TestingClass {

    @Test
    public void testAddIngredient() throws Exception {
        ArrayList<String> collection = new ArrayList<>();
        collection.add("Banan");
        assertEquals(1, collection.size());
        collection.add("Ananas");
        assertEquals(2, collection.size());
    }

    @Test
    public void testInitialize() throws FileNotFoundException {
        TreeSet<Ingredient> IngredientsList = new TreeSet<>();
        String[] tmp;
        Scanner in = new Scanner(new File("data/Ingredients/ingredients"));
        for (int i = 0; i < 2 ; i++) {
            String line = in.nextLine();
            tmp = line.split("/");
            Ingredient toAdd = new Ingredient(tmp[0]);
            IngredientsList.add(toAdd);
            if (i == 0)
                assertEquals(new Ingredient("Ananasy"), toAdd);
            else if (i == 1)
                assertNotEquals(new Ingredient("Banany"), toAdd);
        }
    }

    @Test
    public void testRecipeParameters() throws Exception {
        int preparingEase = 0;
        int preparingTime = 0;
        boolean[] parameters = {true,true,false,false,false};
        RecipeParameters testParameters = new RecipeParameters(parameters,preparingEase,preparingTime);
        assertEquals(preparingEase,testParameters.getPreparingEase());
        assertEquals(preparingTime,testParameters.getPreparingTime());
        assertEquals(parameters,testParameters.getParameters());
    }

    @Test
    public void testPairAmountUnit() throws Exception {
        String amount = "2";
        String unit = "kg";
        PairAmountUnit testPairAmountUnit = new PairAmountUnit(amount,unit);
        assertEquals(amount,testPairAmountUnit.getAmount());
        assertEquals(unit,testPairAmountUnit.getUnit());
    }


}