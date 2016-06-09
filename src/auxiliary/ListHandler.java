package auxiliary;

/**
 * Created by WTC-Team on 24.04.2016.
 * Project WhatToCook
 */
/*
    ZAJMUJE SIĘ OBSŁUGA ILOŚCI I JEDNOSTEK, POŚREDNICZY MIĘDZY GUI A KLASĄ PRZEPISÓW
    Łączy się z MainWindow
 */
public class ListHandler {

    public ListHandler(String ingredient, String ammount, String unit) {
        this.ingredient = ingredient;
        this.ammount = ammount;
        this.unit = unit;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getAmmount() {
        return ammount;
    }

    public String getUnit() {
        return unit;
    }

    private String ingredient;
    private String ammount;
    private String unit;
}
