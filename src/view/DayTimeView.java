package view;

import controller.DayTimeController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.User;

public class DayTimeView {

    public StackPane build(DayTimeController ctrl, User user) {

        Label titleLabel = new Label("Please select the part of the day:");
        titleLabel.setStyle("-fx-font-size: 32px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: black;");

        Button morningBtn = new Button("Morning");
        morningBtn.setStyle("-fx-background-color: #A9A9A9; " +
                "-fx-text-fill: black; " +
                "-fx-font-size: 14px; " +
                "-fx-background-radius: 8;");

        Button midDayBtn = new Button("Mid-day");
        midDayBtn.setStyle("-fx-background-color: #A9A9A9; " +
                "-fx-text-fill: black; " +
                "-fx-font-size: 14px; " +
                "-fx-background-radius: 8;");

        Button eveningBtn = new Button("Evening");
        eveningBtn.setStyle("-fx-background-color: #A9A9A9; " +
                "-fx-text-fill: black; " +
                "-fx-font-size: 14px; " +
                "-fx-background-radius: 8;");

        Button resultsBtn = new Button("View Results");
        resultsBtn.setStyle("-fx-background-color: #4CAF50; " +
                "-fx-text-fill: black; " +
                "-fx-font-size: 14px; " +
                "-fx-background-radius: 8;");

        morningBtn.setOnAction(e -> ctrl.handleMorning());
        midDayBtn.setOnAction(e -> ctrl.handleMidDay());
        eveningBtn.setOnAction(e -> ctrl.handleEvening());
        resultsBtn.setOnAction(e -> ctrl.handleResults());

        Label waterLabel = new Label("Today's water intake:");
        waterLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");

        Label waterValueLabel = new Label("Not set");
        waterValueLabel.setStyle("-fx-font-size: 14px; " +
                "-fx-text-fill: #A9A9A9; " +
                "-fx-font-style: italic;");

        HBox waterDrops = new HBox(8);
        waterDrops.setAlignment(Pos.CENTER);

        double[] waterLevels = {0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0};
        for (double level : waterLevels) {
            Label drop = new Label("💧");
            drop.setStyle("-fx-font-size: 28px; " +
                    "-fx-cursor: hand; " +
                    "-fx-opacity: 0.4;");
            drop.setOnMouseClicked(e -> {
                ctrl.handleWater(level);
                waterValueLabel.setText("Selected: " + level + "L");
                waterDrops.getChildren().forEach(node ->
                        ((Label) node).setStyle("-fx-font-size: 28px; " +
                                "-fx-cursor: hand; " +
                                "-fx-opacity: 0.4;")
                );
                for (int i = 0; i < waterLevels.length; i++) {
                    if (waterLevels[i] <= level) {
                        ((Label) waterDrops.getChildren().get(i))
                                .setStyle("-fx-font-size: 28px; -fx-cursor: hand; -fx-opacity: 1.0;");
                    }
                }
            });
            waterDrops.getChildren().add(drop);
        }

        // Active calories
        Label caloriesLabel = new Label("Active calories burned today:");
        caloriesLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");

        TextField caloriesField = new TextField();
        caloriesField.setPromptText("Enter active calories");
        caloriesField.setStyle("-fx-background-radius: 8; -fx-font-size: 14px;");
        caloriesField.textProperty().addListener((obs, oldVal, newVal) -> {
            try {
                if (!newVal.isEmpty()) ctrl.handleActiveCalories(Double.parseDouble(newVal));
            } catch (NumberFormatException e) {
                caloriesField.setText(oldVal);
            }
        });

        VBox content = new VBox(15, titleLabel,
                morningBtn, midDayBtn, eveningBtn,
                waterLabel, waterDrops, waterValueLabel,
                caloriesLabel, caloriesField, resultsBtn);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));

        StackPane root = new StackPane();
        root.getChildren().add(content);
        root.setStyle("-fx-background-image: url('/images/background.jpg'); " +
                "-fx-background-size: cover; " +
                "-fx-background-position: center;");

        morningBtn.prefWidthProperty().bind(root.widthProperty().multiply(0.25));
        midDayBtn.prefWidthProperty().bind(root.widthProperty().multiply(0.25));
        eveningBtn.prefWidthProperty().bind(root.widthProperty().multiply(0.25));
        caloriesField.prefWidthProperty().bind(root.widthProperty().multiply(0.25));
        resultsBtn.prefWidthProperty().bind(root.widthProperty().multiply(0.25));

        return root;
    }
}
