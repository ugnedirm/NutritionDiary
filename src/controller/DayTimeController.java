package controller;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import model.MealRepository;
import model.User;
import model.UserRepository;
import view.DayTimeView;

public class DayTimeController {

    public Scene getScene() {
        User user = UserRepository.getCurrentUser();
        StackPane root = new DayTimeView().build(this, user);
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

    public void handleResults() {
        SceneManager.switchTo("result", UserRepository.getCurrentUser());
    }
}