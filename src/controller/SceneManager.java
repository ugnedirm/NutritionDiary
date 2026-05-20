package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

public class SceneManager {
    private static Stage stage;

    public static void init(Stage s) {
        stage = s;
    }

    public static void switchTo(String screen, Object data) {
        Scene scene = switch (screen) {
            case "main" -> new MainMenuController().getScene();
            case "login" -> new LoginController().getScene();
            case "signup" -> new SignUpController().getScene();
            case "daytime" -> new DayTimeController().getScene();
            case "meal" -> new MealController((String) data).getScene();
            case "dish" -> {
                String[] parts = ((String) data).split(":");
                String mealType = parts[0];
                int index = Integer.parseInt(parts[1]);
                yield new DishController(mealType, index).getScene();
            }
            case "result" -> new ResultController((User) data).getScene();
            case "graph" -> new GraphController((User) data).getScene();
            default -> throw new IllegalArgumentException("Scene ERROR: " + screen);
        };
        stage.setScene(scene);
    }
}