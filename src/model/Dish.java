package model;

public class Dish {
    private String name;
    private double calories;
    private Double protein;
    private Double carbohydrates;
    private Double fats;
    private Double sugar;


    public Dish(String name, double calories) {
        this.name = name;
        this.calories = calories;
    }


    public Dish(String name, double calories, double protein, double carbohydrates, double fats, double sugar) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.sugar = sugar;
    }

    public boolean hasNutritionalValues() {
        return protein != null;
    }

    public String getName() {
        return name;
    }

    public double getCalories() {
        return calories;
    }

    public Double getProtein() {
        return protein;
    }

    public Double getCarbohydrates() {
        return carbohydrates;
    }

    public Double getFats() {
        return fats;
    }

    public Double getSugar() {
        return sugar;
    }
}
