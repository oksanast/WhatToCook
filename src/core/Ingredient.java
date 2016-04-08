package core;

/**
 * Created by Mateusz on 23.03.2016.
 */
public class Ingredient {
    public Ingredient(String name, double ammount, String unit) {
        this.name = name;
        this.ammount = ammount;
        this.unit = unit;

    }

    public String getName() {
        return name;
    }

    public double getAmmount() {
        return ammount;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return name + " " + ammount + " " + unit;
    }

    private String name;
    private double ammount;
    private String unit;
}
