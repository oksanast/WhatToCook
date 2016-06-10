package auxiliary;

/**
 * Created by WTC-Team on 23.04.2016.
 * Project WhatToCook
 */
/*
    UŻYWAN DO PRZECHOWYWANIA ILOŚCI I JEDNOSTKI SŁADNIKÓW W PRZEPISACH
    Łączy się z Recipe
 */
public class PairAmountUnit {
    public PairAmountUnit(String ammount,String unit)
    {
       this.amount = ammount;
        this.unit = unit;
    }
    public String getAmount()
    {
        return amount;
    }
    public String getUnit()
    {
        return unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PairAmountUnit that = (PairAmountUnit) o;

        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        return unit != null ? unit.equals(that.unit) : that.unit == null;

    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        return result;
    }

    String amount;
    String unit;
}
