package auxiliary;

import java.util.Arrays;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecipeParameters that = (RecipeParameters) o;

        if (PreparingEase != that.PreparingEase) return false;
        if (PreparingTime != that.PreparingTime) return false;
        return Arrays.equals(Parameters, that.Parameters);

    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(Parameters);
        result = 31 * result + PreparingEase;
        result = 31 * result + PreparingTime;
        return result;
    }

    private boolean[] Parameters;
    private int PreparingEase;
    private int PreparingTime;
}
