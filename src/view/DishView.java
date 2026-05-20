package view;

import controller.DishController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Dish;
import java.util.Set;

public class DishView {

    private static final String COLOR_BTN_DEFAULT = "#A9A9A9";
    private static final String COLOR_BTN_SAVE = "#4CAF50";
    private static final String COLOR_TEXT = "black";
    private static final String COLOR_ERROR = "#ff4444";
    private static final int SIZE_TITLE = 32;
    private static final int SIZE_TEXT = 14;
    private static final int SIZE_ERROR = 12;

    private static final String FIELD_NORMAL = "-fx-background-radius: 8; -fx-font-size: " + SIZE_TEXT + "px;";
    private static final String FIELD_ERROR = "-fx-background-radius: 8; -fx-font-size: " + SIZE_TEXT
            + "px; -fx-border-color: " + COLOR_ERROR + "; -fx-border-radius: 8;";

    public StackPane build(DishController ctrl, Dish existing) {

        Label titleLabel = new Label(existing != null ? "Edit dish" : "Add new dish");
        titleLabel.setStyle(
                "-fx-font-size: " + SIZE_TITLE + "px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_TEXT + ";");

        TextField nameField = new TextField();
        nameField.setPromptText("Dish name");
        nameField.setStyle(FIELD_NORMAL);

        TextField caloriesField = new TextField();
        caloriesField.setPromptText("Calories");
        caloriesField.setStyle(FIELD_NORMAL);

        TextField proteinField = new TextField();
        proteinField.setPromptText("Protein (g)");
        proteinField.setStyle(FIELD_NORMAL);

        TextField carbsField = new TextField();
        carbsField.setPromptText("Carbohydrates (g)");
        carbsField.setStyle(FIELD_NORMAL);

        TextField fatsField = new TextField();
        fatsField.setPromptText("Fats (g)");
        fatsField.setStyle(FIELD_NORMAL);

        CheckBox nutritionalCheckBox = new CheckBox("Add nutritional values");
        nutritionalCheckBox.setStyle("-fx-text-fill: " + COLOR_TEXT + "; -fx-font-size: " + SIZE_TEXT + "px;");

        proteinField.setVisible(false);
        proteinField.setManaged(false);
        carbsField.setVisible(false);
        carbsField.setManaged(false);
        fatsField.setVisible(false);
        fatsField.setManaged(false);

        nutritionalCheckBox.setOnAction(e -> {
            boolean checked = nutritionalCheckBox.isSelected();
            proteinField.setVisible(checked);
            proteinField.setManaged(checked);
            carbsField.setVisible(checked);
            carbsField.setManaged(checked);
            fatsField.setVisible(checked);
            fatsField.setManaged(checked);
        });

        if (existing != null) {
            nameField.setText(existing.getName());
            caloriesField.setText(String.valueOf(existing.getCalories()));
            if (existing.hasNutritionalValues()) {
                nutritionalCheckBox.setSelected(true);
                proteinField.setText(String.valueOf(existing.getProtein()));
                carbsField.setText(String.valueOf(existing.getCarbohydrates()));
                fatsField.setText(String.valueOf(existing.getFats()));
                proteinField.setVisible(true);
                proteinField.setManaged(true);
                carbsField.setVisible(true);
                carbsField.setManaged(true);
                fatsField.setVisible(true);
                fatsField.setManaged(true);
            }
        }

        Label errorLabel = new Label("Data is invalid. Please try again.");
        errorLabel.setStyle("-fx-text-fill: " + COLOR_ERROR + "; -fx-font-size: " + SIZE_ERROR + "px;");
        errorLabel.setVisible(false);

        Button saveBtn = new Button("Save");
        saveBtn.setStyle("-fx-background-color: " + COLOR_BTN_SAVE + "; -fx-text-fill: " + COLOR_TEXT
                + "; -fx-font-size: " + SIZE_TEXT + "px; -fx-background-radius: 8;");

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: " + COLOR_BTN_DEFAULT + "; -fx-text-fill: " + COLOR_TEXT
                + "; -fx-font-size: " + SIZE_TEXT + "px; -fx-background-radius: 8;");

        saveBtn.setOnAction(e -> {
            nameField.setStyle(FIELD_NORMAL);
            caloriesField.setStyle(FIELD_NORMAL);
            proteinField.setStyle(FIELD_NORMAL);
            carbsField.setStyle(FIELD_NORMAL);
            fatsField.setStyle(FIELD_NORMAL);
            errorLabel.setVisible(false);

            Set<String> errors = ctrl.handleSave(
                    nameField.getText(),
                    caloriesField.getText(),
                    nutritionalCheckBox.isSelected(),
                    proteinField.getText(),
                    carbsField.getText(),
                    fatsField.getText());

            if (!errors.isEmpty()) {
                errorLabel.setVisible(true);
                if (errors.contains("name")) {
                    nameField.setStyle(FIELD_ERROR);
                }
                if (errors.contains("calories")) {
                    caloriesField.setStyle(FIELD_ERROR);
                }
                if (errors.contains("protein")) {
                    proteinField.setStyle(FIELD_ERROR);
                }
                if (errors.contains("carbs")) {
                    carbsField.setStyle(FIELD_ERROR);
                }
                if (errors.contains("fats")) {
                    fatsField.setStyle(FIELD_ERROR);
                }
            }
        });

        backBtn.setOnAction(e -> ctrl.handleBack());

        VBox content = new VBox(15, titleLabel, nameField, caloriesField,
                nutritionalCheckBox, proteinField, carbsField, fatsField,
                errorLabel, saveBtn, backBtn);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));

        StackPane root = new StackPane();
        root.getChildren().add(content);
        root.setStyle(
                "-fx-background-image: url('/images/background.jpg'); -fx-background-size: cover; -fx-background-position: center;");

        nameField.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        caloriesField.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        proteinField.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        carbsField.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        fatsField.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        saveBtn.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        backBtn.prefWidthProperty().bind(root.widthProperty().multiply(0.3));

        return root;
    }
}