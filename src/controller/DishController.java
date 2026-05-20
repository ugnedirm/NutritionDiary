package controller;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import model.Dish;
import model.MealRepository;
import view.DishView;
import java.util.HashSet;
import java.util.Set;

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

    public Set<String> handleSave(String name, String calories, boolean hasNutritional,
                                  String protein, String carbs, String fats) {
        Set<String> errors = new HashSet<>();

        if (name.isEmpty())
            errors.add("name");

        try {
            double v = Double.parseDouble(calories);
            if (v <= 0 || v > 5000)
                errors.add("calories");
        } catch (NumberFormatException e) {
            errors.add("calories");
        }

        if (hasNutritional) {
            try {
                double v = Double.parseDouble(protein);
                if (v < 0 || v > 300)
                    errors.add("protein");
            } catch (NumberFormatException e) {
                errors.add("protein");
            }

            try {
                double v = Double.parseDouble(carbs);
                if (v < 0 || v > 500)
                    errors.add("carbs");
            } catch (NumberFormatException e) {
                errors.add("carbs");
            }

            try {
                double v = Double.parseDouble(fats);
                if (v < 0 || v > 300)
                    errors.add("fats");
            } catch (NumberFormatException e) {
                errors.add("fats");
            }
        }

        if (!errors.isEmpty())
            return errors;

        Dish dish;
        if (hasNutritional) {
            dish = new Dish(name,
                    Double.parseDouble(calories),
                    Double.parseDouble(protein),
                    Double.parseDouble(carbs),
                    Double.parseDouble(fats));
        } else {
            dish = new Dish(name, Double.parseDouble(calories));
        }

        if (dishIndex >= 0) {
            MealRepository.updateDish(mealType, dishIndex, dish);
        } else {
            MealRepository.addDish(mealType, dish);
        }

        SceneManager.switchTo("meal", mealType);
        return errors;
    }

    public void handleBack() {
        SceneManager.switchTo("meal", mealType);
    }
}