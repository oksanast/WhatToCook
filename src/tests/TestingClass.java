package tests;

import auxiliary.*;
import core.*;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

import static org.junit.Assert.*;

@SuppressWarnings ("ALL")
public class TestingClass {

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

    @Test
    public void testListHandler() throws Exception {
        String ingredient = "Ananasy";
        String amount = "2";
        String unit = "sztuki";
        ListHandler testListHandler = new ListHandler(ingredient,amount,unit);
        assertEquals(ingredient,testListHandler.getIngredient());
        assertEquals(amount,testListHandler.getAmmount());
        assertEquals(unit,testListHandler.getUnit());
    }

    @Test
    public void testIngredient() throws Exception {
        String ingredientName = "Ananasy";
        Ingredient ananasy = new Ingredient(ingredientName);
        assertEquals(ingredientName,ananasy.getName());
    }

    @Test
    public void testIngredientsList() throws Exception {
        Ingredient first = new Ingredient("Ananasy");
        Ingredient second = new Ingredient("Banany");
        Ingredient third = new Ingredient("Jabłka");

        IngredientsList.addIngredient(first);
        IngredientsList.addIngredient(second);
        IngredientsList.addIngredient(third);
        assertTrue(IngredientsList.contain(first));
        assertTrue(IngredientsList.contain(second));
        assertTrue(IngredientsList.contain(third));

        IngredientsList.removeIngredient(first);
        assertTrue(!IngredientsList.contain(first));
        assertTrue(IngredientsList.contain(second));
        assertTrue(IngredientsList.contain(third));
    }

    @Test
    public void testRecipeConstructor() throws Exception {
        String name = "Name";
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("ingr"));
        String instructions = "instructions";
        ArrayList<PairAmountUnit> PAU = new ArrayList<>();
        PAU.add(new PairAmountUnit("amount", "unit"));
        boolean[] params = {false, false, false, false, false};
        RecipeParameters parameters = new RecipeParameters(params, 0, 0);

        Recipe recipe = new Recipe(name, ingredients, PAU, instructions, parameters);

        assertEquals(recipe.getName(), name);
        assertTrue(ingredients.equals(recipe.getIngredients()));
        assertTrue(PAU.equals(recipe.getPairAmountUnitList()));
        assertEquals(recipe.getRecipe(), instructions);
        assertTrue(recipe.getParameters().getParameters() == params);
    }

    @Test
    public void testRecipesEquals() throws Exception {
        String name = "Name";
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("ingr"));
        String instructions = "instructions";
        ArrayList<PairAmountUnit> PAU = new ArrayList<>();
        PAU.add(new PairAmountUnit("amount", "unit"));
        boolean[] params = {false, false, false, false, false};
        RecipeParameters parameters = new RecipeParameters(params, 0, 0);

        Recipe recipe = new Recipe(name, ingredients, PAU, instructions, parameters);
        Recipe recipe2 = new Recipe(name, ingredients, PAU, instructions, parameters);
        assertTrue(recipe.equals(recipe2));

    }

    @Test
    public void testAddAndDeleteLinkedRecipes() throws Exception {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("ingr"));
        ArrayList<PairAmountUnit> PAU = new ArrayList<>();
        PAU.add(new PairAmountUnit("amount", "unit"));
        boolean[] params = {false, false, false, false, false};

        Recipe recipe1 = new Recipe("name1", ingredients, PAU, "instructions", new RecipeParameters(params, 0, 0));
        Recipe recipe2 = new Recipe("name2", ingredients, PAU, "instructions", new RecipeParameters(params, 0, 0));

        //Dodanie przepisów do listy głównej
        RecipesList.add(recipe1);
        RecipesList.add(recipe2);

        //Zakładam, że nie istnieją przepisy o nazwach name1 i name2
        ArrayList<String> expectedLinkedRecipes1 = new ArrayList<>();
        expectedLinkedRecipes1.add("name2");
        ArrayList<String> expectedLinkedRecipes2 = new ArrayList<>();
        expectedLinkedRecipes2.add("name1");

        LinkedRecipes.addLinking(recipe1.getName(), recipe2.getName());

        ArrayList<String> linkedRecipes1 = recipe1.getLinkedRecipes();
        ArrayList<String> linkedRecipes2 = recipe2.getLinkedRecipes();

        assertTrue(linkedRecipes1.equals(expectedLinkedRecipes1));
        assertTrue(linkedRecipes2.equals(expectedLinkedRecipes2));

        //Próba dodania drugi raz powiązań
        LinkedRecipes.addLinking(recipe1.getName(), recipe2.getName());
        assertTrue(linkedRecipes1.equals(expectedLinkedRecipes1));
        assertTrue(linkedRecipes2.equals(expectedLinkedRecipes2));

        //Usunięcie powiązań
        LinkedRecipes.deleteLinking(recipe1.getName(), recipe2.getName());
        assertTrue(recipe1.getLinkedRecipes().isEmpty());
        assertTrue(recipe2.getLinkedRecipes().isEmpty());

        //Próba ponownego usunięcia powiązań
        LinkedRecipes.deleteLinking(recipe1.getName(), recipe2.getName());
        assertTrue(recipe1.getLinkedRecipes().isEmpty());
        assertTrue(recipe2.getLinkedRecipes().isEmpty());

        //Usunięcie testowych przepisów z bazy przepisów
        RecipesList.remove(recipe1.getName());
        RecipesList.remove(recipe1.getName());
    }

    @Test
    public void testRecipesList() throws Exception {
        String name = "Pomidorowa";
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Pomidory"));
        String instructions = "instructions";
        ArrayList<PairAmountUnit> PAU = new ArrayList<>();
        PAU.add(new PairAmountUnit("2", "sztuki"));
        boolean[] params = {false, false, false, false, false};
        RecipeParameters parameters = new RecipeParameters(params, 0, 0);

        Recipe first = new Recipe(name, ingredients, PAU, instructions, parameters);

        name = "Ogórkowa";
        ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Ogórki"));
        instructions = "instructions";
        PAU = new ArrayList<>();
        PAU.add(new PairAmountUnit("3", "sztuki"));
        parameters = new RecipeParameters(params, 0, 0);

        Recipe second = new Recipe(name, ingredients, PAU, instructions, parameters);

        RecipesList.add(first);
        RecipesList.add(second);

        assertTrue(RecipesList.isRecipe(first));
        assertTrue(RecipesList.isRecipe(second));

        assertEquals(null,RecipesList.getRecipe("Rosół"));

        assertEquals(first.getName(),RecipesList.getRecipe("Pomidorowa").getName());
    }

    @Test
    public void testAddIngredientToBuyList() throws Exception {
        Ingredient testIngredient = new Ingredient("Ananasy");
        ToBuyIngredientsList.add(testIngredient);
        for(Ingredient i : ToBuyIngredientsList.getSet()) {
            assertEquals(testIngredient,i);
        }
    }

    @Test
    public void testAddSpareIngredient() throws Exception {
        Ingredient baseIngredient = new Ingredient("Ananasy");
        Ingredient spareIngredient = new Ingredient("Banany");

        SpareIngredients testSpareIngredients = new SpareIngredients(baseIngredient);
        testSpareIngredients.addSpareIngredient(spareIngredient);

        for(Ingredient i : testSpareIngredients.getSpareIngredients()) {
            assertEquals(spareIngredient,i);
        }
    }
    @Test
    public void testDictionary() throws Exception {
        auxiliary.Dictionary.initialize();
        assertEquals("",auxiliary.Dictionary.translate("braksłowa","Polski"));
        assertEquals("",auxiliary.Dictionary.translate("braksłowa",""));
        assertEquals("",auxiliary.Dictionary.translate("",""));
        assertEquals("",auxiliary.Dictionary.translate("","Polski"));
    }
    @Test
    public void testEqualsIngredients() {
        Ingredient ingredient = new Ingredient("jabłko");
        Ingredient ingredient3 = new Ingredient("jabłko2");
        Ingredient ingredient2 = new Ingredient("jabłko2");
        assertTrue(!ingredient.equals(ingredient2));
        assertTrue(ingredient3.equals(ingredient2));

    }
}