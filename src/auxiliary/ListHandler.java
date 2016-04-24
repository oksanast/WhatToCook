package auxiliary;

/**
 * Created by Mateusz on 24.04.2016.
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

    String ingredient;
    String ammount;
    String unit;
}
