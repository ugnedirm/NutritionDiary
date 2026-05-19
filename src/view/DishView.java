package view;

import controller.DishController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Dish;

public class DishView {

    public StackPane build(DishController ctrl, Dish existing) {

        Label titleLabel = new Label(existing != null ? "Edit dish" : "Add new dish");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: black;");

        TextField nameField = new TextField();
        nameField.setPromptText("Dish name");
        nameField.setStyle("-fx-background-radius: 8; -fx-font-size: 14px;");

        TextField caloriesField = new TextField();
        caloriesField.setPromptText("Calories");
        caloriesField.setStyle("-fx-background-radius: 8; -fx-font-size: 14px;");

        TextField proteinField = new TextField();
        proteinField.setPromptText("Protein (g)");
        proteinField.setStyle("-fx-background-radius: 8; -fx-font-size: 14px;");

        TextField carbsField = new TextField();
        carbsField.setPromptText("Carbohydrates (g)");
        carbsField.setStyle("-fx-background-radius: 8; -fx-font-size: 14px;");

        TextField fatsField = new TextField();
        fatsField.setPromptText("Fats (g)");
        fatsField.setStyle("-fx-background-radius: 8; -fx-font-size: 14px;");

        TextField sugarField = new TextField();
        sugarField.setPromptText("Sugar (g)");
        sugarField.setStyle("-fx-background-radius: 8; -fx-font-size: 14px;");

        CheckBox nutritionalCheckBox = new CheckBox("Add nutritional values");
        nutritionalCheckBox.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");

        proteinField.setVisible(false); proteinField.setManaged(false);
        carbsField.setVisible(false);   carbsField.setManaged(false);
        fatsField.setVisible(false);    fatsField.setManaged(false);
        sugarField.setVisible(false);   sugarField.setManaged(false);

        nutritionalCheckBox.setOnAction(e -> {
            boolean checked = nutritionalCheckBox.isSelected();
            proteinField.setVisible(checked);  proteinField.setManaged(checked);
            carbsField.setVisible(checked);    carbsField.setManaged(checked);
            fatsField.setVisible(checked);     fatsField.setManaged(checked);
            sugarField.setVisible(checked);    sugarField.setManaged(checked);
        });

        if (existing != null) {
            nameField.setText(existing.getName());
            caloriesField.setText(String.valueOf(existing.getCalories()));
            if (existing.hasNutritionalValues()) {
                nutritionalCheckBox.setSelected(true);
                proteinField.setText(String.valueOf(existing.getProtein()));
                carbsField.setText(String.valueOf(existing.getCarbohydrates()));
                fatsField.setText(String.valueOf(existing.getFats()));
                sugarField.setText(String.valueOf(existing.getSugar()));
                proteinField.setVisible(true); proteinField.setManaged(true);
                carbsField.setVisible(true);   carbsField.setManaged(true);
                fatsField.setVisible(true);    fatsField.setManaged(true);
                sugarField.setVisible(true);   sugarField.setManaged(true);
            }
        }

        Label errorLabel = new Label("Data is invalid. Please try again.");
        errorLabel.setStyle("-fx-text-fill: #ff4444; -fx-font-size: 12px;");
        errorLabel.setVisible(false);

        Button saveBtn = new Button("Save");
        saveBtn.setStyle("-fx-background-color: #4CAF50; " +
                "-fx-text-fill: black; " +
                "-fx-font-size: 14px; " +
                "-fx-background-radius: 8;");

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: #A9A9A9; " +
                "-fx-text-fill: black; " +
                "-fx-font-size: 14px; " +
                "-fx-border-radius: 8;");

        saveBtn.setOnAction(e -> {
            boolean success = ctrl.handleSave(
                    nameField.getText(),
                    caloriesField.getText(),
                    nutritionalCheckBox.isSelected(),
                    proteinField.getText(),
                    carbsField.getText(),
                    fatsField.getText(),
                    sugarField.getText()
            );
            errorLabel.setVisible(!success);
        });
        backBtn.setOnAction(e -> ctrl.handleBack());

        VBox content = new VBox(15, titleLabel, nameField, caloriesField,
                nutritionalCheckBox, proteinField, carbsField, fatsField, sugarField,
                errorLabel, saveBtn, backBtn);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));

        StackPane root = new StackPane();
        root.getChildren().add(content);
        root.setStyle("-fx-background-image: url('/images/background.jpg'); " +
                "-fx-background-size: cover; " +
                "-fx-background-position: center;");

        nameField.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        caloriesField.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        proteinField.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        carbsField.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        fatsField.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        sugarField.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        saveBtn.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        backBtn.prefWidthProperty().bind(root.widthProperty().multiply(0.3));

        return root;
    }
}