package core;

/**
 * Created by Mateusz on 23.03.2016.
 */
public class Ingredient implements Comparable<Ingredient> {
    public Ingredient(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() { return name;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingredient that = (Ingredient) o;

        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    private String name;

    @Override
    public int compareTo(Ingredient o) {
        return this.getName().compareTo(o.getName());
    }
}
