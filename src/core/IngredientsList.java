package core;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Mateusz on 20.04.2016.
 */
public class IngredientsList {

    static public void initialize()
    {
        IngredientsList = new ArrayList<Ingredient>();
    }
    static public void loadIngredients()
    {
        Scanner in;
        String tmp;
        try {
            in = new Scanner(new File("src/ingredients"));
            while(in.hasNextLine())
            {
                tmp = in.nextLine();
                Ingredient toAdd = new Ingredient(tmp);
                addIngredient(toAdd);
                Collections.sort(IngredientsList);
            }
            in.close();
        } catch (FileNotFoundException e)
        {

        }
    }

    static public void addIngredient(Ingredient newIngredient)
    {
        boolean exist = false;

        for(int i = 0; i < IngredientsList.size(); i++)
        {
            if(newIngredient.equals(IngredientsList.get(i)))
            {
                exist = true;
                break;
            }
        }
        if(!exist) {
            IngredientsList.add(newIngredient);
            Collections.sort(IngredientsList);

            PrintWriter writer;

            try
            {
                writer = new PrintWriter(new File("src/ingredients"));
                for(int i = 0; i < IngredientsList.size();i++)
                {
                    writer.println(IngredientsList.get(i).getName());
                }
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
    static public void removeIngredient(String name)
    {
        for(int i = 0; i < IngredientsList.size();i++)
        {
            if(IngredientsList.get(i).getName().equals(name))
            {
                IngredientsList.remove(i);
                PrintWriter writer;

                try
                {
                    writer = new PrintWriter(new File("src/ingredients"));
                    for(int j = 0; j < IngredientsList.size();j++)
                    {
                        writer.println(IngredientsList.get(j).getName());
                    }
                    writer.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    static public void rebuildModel(DefaultListModel<String> toInsert)
    {
        toInsert.clear();
        for(int i = 0; i < IngredientsList.size();i++)
        {
            toInsert.addElement(IngredientsList.get(i).getName());
        }
    }
    static public void reloadComboBox(JComboBox<String> comboBox)
    {
        comboBox.removeAllItems();
        for(int i = 0; i < IngredientsList.size();i++)
        {
            comboBox.addItem(IngredientsList.get(i).getName());
        }
    }
    private static  List<Ingredient> IngredientsList;
}
