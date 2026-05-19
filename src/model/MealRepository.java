package model;

import java.util.ArrayList;
import java.util.List;

public class MealRepository {
    private static List<Dish> morningDishes = new ArrayList<>();
    private static List<Dish> midDayDishes = new ArrayList<>();
    private static List<Dish> eveningDishes = new ArrayList<>();
    private static double water = 0;
    private static double activeCalories = 0;

    public static void addDish(String mealType, Dish dish) {
        switch (mealType) {
            case "morning" -> morningDishes.add(dish);
            case "midday" -> midDayDishes.add(dish);
            case "evening" -> eveningDishes.add(dish);
        }
    }

    public static void updateDish(String mealType, int index, Dish dish) {
        switch (mealType) {
            case "morning" -> morningDishes.set(index, dish);
            case "midday" -> midDayDishes.set(index, dish);
            case "evening" -> eveningDishes.set(index, dish);
        }
    }

    public static List<Dish> getDishes(String mealType) {
        return switch (mealType) {
            case "morning" -> morningDishes;
            case "midday" -> midDayDishes;
            case "evening" -> eveningDishes;
            default -> new ArrayList<>();
        };
    }

    public static void setWater(double w) {
        water = w;
    }

    public static void setActiveCalories(double c) {
        activeCalories = c;
    }

    public static double getWater() {
        return water;
    }

    public static double getActiveCalories() {
        return activeCalories;
    }

    public static void clear() {
        morningDishes.clear();
        midDayDishes.clear();
        eveningDishes.clear();
        water = 0;
        activeCalories = 0;
    }
}