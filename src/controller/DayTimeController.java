package controller;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import model.MealRepository;
import view.DayTimeView;

public class DayTimeController {

    public Scene getScene() {
        StackPane root = new DayTimeView().build(this);
        return new Scene(root, 800, 600);
    }

    public void handleWater(double liters) {
        MealRepository.setWater(liters);
    }

    public void handleActiveCalories(double calories) {
        MealRepository.setActiveCalories(calories);
    }

    public void handleMorning() {
        SceneManager.switchTo("meal", "morning");
    }

    public void handleMidDay() {
        SceneManager.switchTo("meal", "midday");
    }

    public void handleEvening() {
        SceneManager.switchTo("meal", "evening");
    }
}