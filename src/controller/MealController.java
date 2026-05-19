package controller;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import view.MealView;

public class MealController {

    private String mealType;

    public MealController(String mealType) {
        this.mealType = mealType;
    }

    public Scene getScene() {
        StackPane root = new MealView().build(this, mealType);
        return new Scene(root, 800, 600);
    }

    public void handleDishSelected(String mealType, int index) {
        SceneManager.switchTo("dish", mealType + ":" + index);
    }

    public void handleBack() {
        SceneManager.switchTo("daytime", null);
    }
}
