package controller;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import model.Dish;
import model.MealRepository;
import view.DishView;

public class DishController {

    private String mealType;
    private int dishIndex;

    public DishController(String mealType, int dishIndex) {
        this.mealType = mealType;
        this.dishIndex = dishIndex;
    }

    public Scene getScene() {
        Dish existing = dishIndex >= 0 ? MealRepository.getDishes(mealType).get(dishIndex) : null;
        StackPane root = new DishView().build(this, existing);
        return new Scene(root, 800, 600);
    }

    public boolean handleSave(String name, String calories, boolean hasNutritional,
                              String protein, String carbs, String fats, String sugar) {
        if (name.isEmpty() || calories.isEmpty()) return false;

        try {
            Dish dish;
            if (hasNutritional) {
                if (protein.isEmpty() || carbs.isEmpty() || fats.isEmpty() || sugar.isEmpty()) return false;
                dish = new Dish(name,
                        Double.parseDouble(calories),
                        Double.parseDouble(protein),
                        Double.parseDouble(carbs),
                        Double.parseDouble(fats),
                        Double.parseDouble(sugar));
            } else {
                dish = new Dish(name, Double.parseDouble(calories));
            }

            if (dishIndex >= 0) {
                MealRepository.updateDish(mealType, dishIndex, dish);
            } else {
                MealRepository.addDish(mealType, dish);
            }

        } catch (NumberFormatException e) {
            return false;
        }

        SceneManager.switchTo("meal", mealType);
        return true;
    }

    public void handleBack() {
        SceneManager.switchTo("meal", mealType);
    }
}