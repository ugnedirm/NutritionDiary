package view;

import controller.MealController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Dish;
import model.MealRepository;
import java.util.List;

public class MealView {

    private static final String COLOR_BTN_DEFAULT = "#A9A9A9";
    private static final String COLOR_BTN_FILLED = "#4CAF50";
    private static final String COLOR_TEXT = "black";
    private static final int SIZE_TITLE = 32;
    private static final int SIZE_TEXT = 14;
    private static final int MAX_DISHES = 5;

    public StackPane build(MealController ctrl, String mealType) {

        String title = mealType.substring(0, 1).toUpperCase() + mealType.substring(1);
        Label titleLabel = new Label(title + " meals");
        titleLabel.setStyle(
                "-fx-font-size: " + SIZE_TITLE + "px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_TEXT + ";");

        VBox dishButtons = new VBox(10);
        dishButtons.setAlignment(Pos.CENTER);

        List<Dish> existing = MealRepository.getDishes(mealType);
        for (int i = 0; i < existing.size(); i++) {
            final int index = i;
            Button dishBtn = new Button(existing.get(i).getName());
            dishBtn.setStyle("-fx-background-color: " + COLOR_BTN_FILLED + "; -fx-text-fill: " + COLOR_TEXT
                    + "; -fx-font-size: " + SIZE_TEXT + "px; -fx-background-radius: 8;");
            dishBtn.setOnAction(e -> ctrl.handleDishSelected(mealType, index));
            dishButtons.getChildren().add(dishBtn);
        }

        Button addDishBtn = new Button("+ Add dish");
        addDishBtn.setStyle("-fx-background-color: " + COLOR_BTN_DEFAULT + "; -fx-text-fill: " + COLOR_TEXT
                + "; -fx-font-size: " + SIZE_TEXT + "px; -fx-background-radius: 8;");
        if (existing.size() >= MAX_DISHES) {
            addDishBtn.setDisable(true);
        }

        addDishBtn.setOnAction(e -> {
            int currentSize = MealRepository.getDishes(mealType).size();
            if (currentSize < MAX_DISHES) {
                Button dishBtn = new Button("Dish " + (currentSize + 1));
                dishBtn.setStyle("-fx-background-color: " + COLOR_BTN_DEFAULT + "; -fx-text-fill: " + COLOR_TEXT
                        + "; -fx-font-size: " + SIZE_TEXT + "px; -fx-background-radius: 8;");
                dishBtn.setOnAction(ev -> ctrl.handleDishSelected(mealType, -1));
                dishButtons.getChildren().add(dishBtn);
            }
            if (dishButtons.getChildren().size() >= MAX_DISHES) {
                addDishBtn.setDisable(true);
            }
        });

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: " + COLOR_BTN_DEFAULT + "; -fx-text-fill: " + COLOR_TEXT
                + "; -fx-font-size: " + SIZE_TEXT + "px; -fx-background-radius: 8;");
        backBtn.setOnAction(e -> ctrl.handleBack());

        VBox content = new VBox(20, titleLabel, dishButtons, addDishBtn, backBtn);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));

        StackPane root = new StackPane();
        root.getChildren().add(content);
        root.setStyle(
                "-fx-background-image: url('/images/background.jpg'); -fx-background-size: cover; -fx-background-position: center;");

        addDishBtn.prefWidthProperty().bind(root.widthProperty().multiply(0.25));
        backBtn.prefWidthProperty().bind(root.widthProperty().multiply(0.25));

        for (int i = 0; i < dishButtons.getChildren().size(); i++) {
            Button btn = (Button) dishButtons.getChildren().get(i);
            btn.prefWidthProperty().bind(root.widthProperty().multiply(0.25));
        }

        dishButtons.getChildren().addListener((javafx.collections.ListChangeListener<javafx.scene.Node>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(node -> {
                        if (node instanceof Button btn) {
                            btn.prefWidthProperty().bind(root.widthProperty().multiply(0.25));
                        }
                    });
                }
            }
        });

        return root;
    }
}