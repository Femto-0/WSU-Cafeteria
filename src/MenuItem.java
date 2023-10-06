public class MenuItem {
    private String name;
    private int calories;
    private double price;

    public MenuItem(String name, int calories, double price) {
        this.name = name;
        this.calories = calories;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " - " + calories + " calories $- " + price;
    }
}