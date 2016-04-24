package auxiliary;

/**
 * Created by Mateusz on 23.04.2016.
 * Project WhatToCook
 */
public class PairAmountUnit {
    public PairAmountUnit(String ammount,String unit)
    {
       this.ammount = ammount;
        this.unit = unit;
    }
    public String getAmmount()
    {
        return ammount;
    }
    public String getUnit()
    {
        return unit;
    }
    String ammount;
    String unit;
}
