package auxiliary;

import java.util.ArrayList;

/**
 * Created by WTC-Team on 25.04.2016.
 * Project WhatToCook
 */
//PRZECHOWUJE DODATKOWE TAG'I PRZEPISOW
//Łączy się z Recipe
public class RecipeParameters {

    public RecipeParameters(boolean[] parameters, int preparingEase, int preparingTime) {
        Parameters = parameters;
        PreparingEase = preparingEase;
        PreparingTime = preparingTime;
    }

    public boolean[] getParameters() {
        return Parameters;
    }

    public int getPreparingEase() {
        return PreparingEase;
    }

    public int getPreparingTime() {
        return PreparingTime;
    }

    private boolean[] Parameters;
    private int PreparingEase;
    private int PreparingTime;
}
